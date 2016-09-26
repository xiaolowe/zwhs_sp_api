package cn.org.citycloud.zwhs.service;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.citycloud.zwhs.bean.Pay;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.constants.ErrorCodes;
import cn.org.citycloud.zwhs.entity.CashRecoder;
import cn.org.citycloud.zwhs.entity.FinAcc;
import cn.org.citycloud.zwhs.entity.ProviderUser;
import cn.org.citycloud.zwhs.entity.ServiceProvider;
import cn.org.citycloud.zwhs.exception.BusinessErrorException;
import cn.org.citycloud.zwhs.repository.CashRecoderDao;
import cn.org.citycloud.zwhs.repository.FinAccDao;
import cn.org.citycloud.zwhs.repository.ProviderUserDao;
import cn.org.citycloud.zwhs.repository.ServiceProviderDao;

@Component
public class PayService
{
    
    @Autowired
    private CashRecoderDao cashRecoderDao;
    
    @Autowired
    private ServiceProviderDao providerDao;
    
    @Autowired
    private FinAccDao finAccDao;
    
    @Autowired
    private ProviderUserDao providerUserDao;
    
    @Transactional(rollbackFor = DataAccessException.class)
    public void applyPay(Pay pay)
        throws Exception
    {
        int id = pay.getStoreId();
        ServiceProvider provider = providerDao.findByProviderId(id);
        FinAcc finAcc = finAccDao.findByAccNoAndAccType(id, Constants.ACC_TYPE_PROVIDER);
        ProviderUser providerUser = providerUserDao.findOne(pay.getApplyUserId());
        BigDecimal accBal = finAcc.getAccBal();
        if (accBal.compareTo(pay.getApplyMoney()) == -1)
        {
            throw new BusinessErrorException(ErrorCodes.CASH_APPLY_ERROR, "您当前只能提现" + accBal + "元");
        }
        CashRecoder cashRecoder = new CashRecoder();
        BeanUtils.copyProperties(provider, cashRecoder);
        BeanUtils.copyProperties(pay, cashRecoder);
        if (StringUtils.isNotEmpty(providerUser.getUserTruename()))
        {
            
            cashRecoder.setApplyUserName(providerUser.getUserTruename());
        }
        cashRecoder.setStoreUserId(pay.getStoreId());
        cashRecoder.setProviderUserId(pay.getStoreId());
        // 服务商账号
        cashRecoder.setAccType(3);
        
        BigDecimal sum = finAcc.getAccBal();
        sum = sum.subtract(pay.getApplyMoney());
        finAcc.setAccBal(sum);
        cashRecoder.setAccSurplus(sum);
        Date now = new Date();
        cashRecoder.setApplyTime(now);
        cashRecoder.setInsDate(now);
        cashRecoder.setUpdDate(now);
        cashRecoderDao.save(cashRecoder);
        finAccDao.save(finAcc);
        
    }
}
