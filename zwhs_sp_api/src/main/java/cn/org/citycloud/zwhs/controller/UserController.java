package cn.org.citycloud.zwhs.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.validation.Valid;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.zwhs.bean.Identify;
import cn.org.citycloud.zwhs.bean.Login;
import cn.org.citycloud.zwhs.bean.Register;
import cn.org.citycloud.zwhs.bean.UserToken;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.constants.ErrorCodes;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.FinAcc;
import cn.org.citycloud.zwhs.entity.ProviderUser;
import cn.org.citycloud.zwhs.entity.RegionInfo;
import cn.org.citycloud.zwhs.entity.ServiceProvider;
import cn.org.citycloud.zwhs.exception.BusinessErrorException;
import cn.org.citycloud.zwhs.repository.FinAccDao;
import cn.org.citycloud.zwhs.repository.ProviderUserDao;
import cn.org.citycloud.zwhs.repository.RegionInfoDao;
import cn.org.citycloud.zwhs.repository.ServiceProviderDao;
import cn.org.citycloud.zwhs.utils.Sms;

import com.auth0.jwt.JWTSigner;

/**
 * 
 * 登录注册相关控制器
 * 
 * @author lanbo
 * @version [V1.0, 2016年1月26日]
 * @since [B2C/V1.0]
 */
@RestController
public class UserController extends BaseController
{
    
    @Autowired
    private ProviderUserDao providerUserDao;
    
    @Autowired
    private ServiceProviderDao serviceProviderDao;
    
    @Autowired
    private RegionInfoDao regionInfoDao;
    
    @Autowired
    private MemcachedClient cacheClient;
    
    @Autowired
    private FinAccDao finAccDao;
    
    private static String smsInfo = "888888";
    
    /**
     * 服务商登录
     * 
     * @param login
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Object login(@RequestBody @Valid Login login)
        throws Exception
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ProviderUser user = providerUserDao.findByUserPhoneAndUserPwd(login.getPhone(), login.getPassword());
        
        if (user == null)
        {
            throw new BusinessErrorException(ErrorCodes.NON_EXIST_MEMBER, "用户名密码不正确");
        }
        
        String token = generateToken(user, Constants.TOKEN_SECRET);
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setCreateTs(System.currentTimeMillis());
        userToken.setExpiresIn(Constants.TOKEN_EXPIRES_IN);
        userToken.setProviderId(user.getProviderId());
        userToken.setUserId(user.getUserId());
        userToken.setUserName(user.getUserTruename());
        cacheClient.add(token, (int)Constants.TOKEN_EXPIRES_IN, userToken);
        
        String companyName = "";
        int providerState = -1;
        
        ServiceProvider provider = serviceProviderDao.findOne(user.getProviderId());
        
        if (provider != null)
        {
            companyName = provider.getCompanyName();
            providerState = provider.getProviderState();
        }
        resultMap.put("userId", user.getUserId());
        resultMap.put("providerId", user.getProviderId());
        resultMap.put("token", token);
        resultMap.put("companyName", companyName);
        resultMap.put("providerState", providerState);
        
        return resultMap;
    }
    
    /**
     * 服务商注册
     * 
     * @param register
     * @return
     * @throws BusinessErrorException
     * @throws MemcachedException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public Object register(@RequestBody @Valid Register register)
        throws BusinessErrorException, TimeoutException, InterruptedException, MemcachedException
    {
        ProviderUser user = providerUserDao.findByUserPhone(register.getUserPhone());
        if (user != null)
        {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "该手机号已存在");
        }
        String smsMsg = cacheClient.get(register.getUserPhone());
        if (!smsInfo.equalsIgnoreCase(register.getSmsinfo()))
        {
            if (smsMsg == null || !smsMsg.equalsIgnoreCase(register.getSmsinfo()))
            {
                throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "验证码错误");
            }
        }
        
        user = new ProviderUser();
        BeanUtils.copyProperties(register, user);
        Date now = new Date();
        user.setInsDate(now);
        user.setUpdDate(now);
        user = providerUserDao.save(user);
        return user;
    }
    
    /**
     * 发送短信验证码
     * 
     * @param paramMap
     * @return
     * @throws MemcachedException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    @RequestMapping(value = "/user/sms/{phone}", method = RequestMethod.POST)
    public Object sendSms(@PathVariable String phone)
        throws TimeoutException, InterruptedException, MemcachedException
    {
        Sms sms = new Sms();
        String code = sms.createRandom(true, 6);
        
        String httpResponse = sms.sendSms(phone, code);
        cacheClient.set(phone, 180, code);
        JSONObject jsonObj = new JSONObject(httpResponse);
        int error_code = jsonObj.getInt("error");
        String error_msg = jsonObj.getString("msg");
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", error_code);
        resultMap.put("msg", error_msg);
        return resultMap;
    }
    
    /**
     * 服务商信息认证
     * 
     * @param identify
     * @return
     * @throws MemcachedException
     * @throws InterruptedException
     * @throws TimeoutException
     * @throws BusinessErrorException
     */
    @RequestMapping(value = "/user/identify", method = RequestMethod.POST)
    public void authentication(@RequestBody @Valid Identify identify)
        throws TimeoutException, InterruptedException, MemcachedException, BusinessErrorException
    {
        ProviderUser user = providerUserDao.findOne(identify.getUserId());
        if (user.getProviderId() == 0)
        {
            ServiceProvider provider = new ServiceProvider();
            // bean copy
            BeanUtils.copyProperties(identify, provider);
            Date date = new Date();
            provider.setCreateTime(date);
            provider.setInsDate(date);
            provider.setUpdDate(date);
            provider.setUserPhone(user.getUserPhone());
            // 设置省区县
            RegionInfo regionInfo = regionInfoDao.findByRegionCode(identify.getRegionProv());
            if (regionInfo == null)
            {
                throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "所在省填写有误");
            }
            provider.setRegionProvName(regionInfo.getRegionName());
            regionInfo = regionInfoDao.findByRegionCode(identify.getRegionCity());
            if (regionInfo == null)
            {
                throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "所在市填写有误");
            }
            provider.setRegionCityName(regionInfo.getRegionName());
            regionInfo = regionInfoDao.findByRegionCode(identify.getCompanyRegion());
            if (regionInfo == null)
            {
                throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "所在区县填写有误");
            }
            provider.setRegionAreaName(regionInfo.getRegionName());
            
            provider = serviceProviderDao.save(provider);
            user.setProviderId(provider.getProviderId());
            providerUserDao.save(user);
            // 修改token,带上storeId
            UserToken userToken = getUserToken();
            userToken.setProviderId(provider.getProviderId());
            cacheClient.set(getToken(), (int)Constants.TOKEN_EXPIRES_IN, userToken);
            // 设置账号信息
            setFinAcc(provider);
        }
        else
        {
            ServiceProvider provider = serviceProviderDao.findOne(user.getProviderId());
            // bean copy
            BeanUtils.copyProperties(identify, provider);
            Date date = new Date();
            provider.setUpdDate(date);
            provider.setUserPhone(user.getUserPhone());
            // 设置省区县
            RegionInfo regionInfo = regionInfoDao.findByRegionCode(identify.getRegionProv());
            if (regionInfo == null)
            {
                throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "所在省填写有误");
            }
            provider.setRegionProvName(regionInfo.getRegionName());
            regionInfo = regionInfoDao.findByRegionCode(identify.getRegionCity());
            if (regionInfo == null)
            {
                throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "所在市填写有误");
            }
            provider.setRegionCityName(regionInfo.getRegionName());
            regionInfo = regionInfoDao.findByRegionCode(identify.getCompanyRegion());
            if (regionInfo == null)
            {
                throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "所在区县填写有误");
            }
            provider.setRegionAreaName(regionInfo.getRegionName());
            
            serviceProviderDao.save(provider);
        }
        
    }
    
    /**
     * 获取省级信息
     * 
     * @param level
     * @return
     */
    @RequestMapping(value = "/user/prov/{level}", method = RequestMethod.GET)
    public Object getProv(@PathVariable int level)
    {
        return regionInfoDao.findByRegionLevel(level);
    }
    
    /**
     * 获取市级信息
     * 
     * @param level
     * @return
     */
    @RequestMapping(value = "/user/city/{level}", method = RequestMethod.GET)
    public Object getCity(@PathVariable int level, @RequestParam int code)
    {
        String regionCode = code / 10000 + "";
        return regionInfoDao.find(level, regionCode);
    }
    
    /**
     * 获取区级信息
     * 
     * @param level
     * @return
     */
    @RequestMapping(value = "/user/region/{level}", method = RequestMethod.GET)
    public Object getRegion(@PathVariable int level, @RequestParam int code)
    {
        String regionCode = code / 100 + "";
        return regionInfoDao.find(level, regionCode);
        
    }
    
    /**
     * 生成token值
     * 
     * @param user
     * @return
     */
    private String generateToken(ProviderUser user, String secret)
    {
        JWTSigner jwtSigner = new JWTSigner(secret);
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userId", user.getUserId());
        claims.put("crtime", System.currentTimeMillis());
        String token = jwtSigner.sign(claims);
        return token;
    }
    
    /**
     * 设置门店账号
     * 
     * @param store
     */
    private void setFinAcc(ServiceProvider provider)
    {
        FinAcc finAcc = finAccDao.findByAccNoAndAccType(provider.getProviderId(), Constants.ACC_TYPE_PROVIDER);
        if (finAcc == null)
        {
            finAcc = new FinAcc();
            finAcc.setAccBal(new BigDecimal(0));
            finAcc.setAccIncome(new BigDecimal(0));
            finAcc.setAccNo(provider.getProviderId());
            finAcc.setAccType(Constants.ACC_TYPE_PROVIDER);
            finAcc.setAccPay(new BigDecimal(0));
            finAcc.setUpdDate(new Date());
            finAccDao.save(finAcc);
        }
    }
}
