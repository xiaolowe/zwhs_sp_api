package cn.org.citycloud.zwhs.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.jwt.JWTSigner;

import cn.org.citycloud.zwhs.bean.UserToken;
import cn.org.citycloud.zwhs.bean.thirdparty.UserInfo;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.StoreUser;
import cn.org.citycloud.zwhs.repository.StoreDao;
import cn.org.citycloud.zwhs.repository.StoreUserDao;

@Controller
@ResponseBody
public class ThirdController extends BaseController {
	
	@Autowired
	private StoreUserDao storeUserDao;
	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private MemcachedClient cacheClient;
	
	/**
	 * 母婴店登录
	 * @param login
	 * @return
	 */
	@RequestMapping(value="/data/login",method=RequestMethod.POST)
	public void login(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(!request.getParameterMap().containsKey("type")){
			resultMap.put("err_code", "0");
			resultMap.put("pass", "0");
			resultMap.put("err_desc", "type参数缺失");
			response.setStatus(200);
			responseOutWithJson(response, resultMap);
		}
		int type = Integer.parseInt(request.getParameter("type"));
		if(type==1){
			identify(request, response);
		}else{
			userLogin(request, response);
		}
	}
	
	
	public void userLogin(HttpServletRequest request,
			HttpServletResponse response)throws Exception{		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(!request.getParameterMap().containsKey("username")||!request.getParameterMap().containsKey("password")){
			resultMap.put("err_code", "0");
			resultMap.put("pass", "0");
			resultMap.put("err_desc", "用户名或密码参数缺失");
			response.setStatus(200);
			responseOutWithJson(response, resultMap);
		}
		String phone = request.getParameter("username");
		String password = request.getParameter("password");
		StoreUser user = storeUserDao.findByUserPhoneAndUserPwd(phone, password);
		if(user!=null){
			String token = generateToken(user, Constants.TOKEN_SECRET);
			UserToken userToken = new UserToken();
			userToken.setToken(token);
			userToken.setCreateTs(System.currentTimeMillis());
			userToken.setExpiresIn(Constants.TOKEN_EXPIRES_IN);
//			userToken.setStoreId(user.getStoreId());
			userToken.setUserId(user.getUserId());
			userToken.setUserName(user.getUserTruename());
			cacheClient.add(token, (int)Constants.TOKEN_EXPIRES_IN, userToken);
			UserInfo userInfo = new UserInfo();
			userInfo.setUser_id(user.getUserId());
			userInfo.setUser_name(user.getUserTruename());
			resultMap.put("err_code", "0");
			resultMap.put("pass", "1");
			resultMap.put("err_desc", "");
			resultMap.put("user_info", userInfo);
			
		}else{
			resultMap.put("err_code", "0");
			resultMap.put("pass", "0");
			resultMap.put("err_desc", "用户名/密码不正确");
			
		}	
		response.setStatus(200);
		responseOutWithJson(response, resultMap);
	}
	
	/**
	 * token验证
	 * @return
	 */
	public void identify(HttpServletRequest request,
			HttpServletResponse response)throws Exception{		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(!request.getParameterMap().containsKey("token")){
			resultMap.put("err_code", "0");
			resultMap.put("pass", "0");
			resultMap.put("err_desc", "token参数缺失");
			response.setStatus(200);
			responseOutWithJson(response, resultMap);
		}
		String token = request.getParameter("token");
		if(token == null||"".equals(token.trim())){
			resultMap.put("err_code", "0");
			resultMap.put("pass", "0");
			resultMap.put("err_desc", "token无效");
			// 获取OutputStream输出流
			response.setStatus(200);
			responseOutWithJson(response, resultMap);
		
		}
		
		Object tokenFromCached = cacheClient.get(token);
		UserToken userToken = (UserToken)tokenFromCached;
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_id(userToken.getUserId());
		userInfo.setUser_name(userToken.getUserName());
		if(tokenFromCached == null) {
			// 获取OutputStream输出流
			resultMap.put("err_code", "0");
			resultMap.put("pass", "0");
			resultMap.put("err_desc", "token不存在");
			response.setStatus(200);
			responseOutWithJson(response, resultMap);
			
		}
		resultMap.put("err_code", "0");
		resultMap.put("pass", "1");
		resultMap.put("err_desc", "");
		resultMap.put("user_info", userInfo);
		response.setStatus(200);
		responseOutWithJson(response, resultMap);
	}
	
	
	/**
	 * 生成token值
	 * 
	 * @param user
	 * @return
	 */
	private String generateToken(StoreUser user, String secret) {
		JWTSigner jwtSigner = new JWTSigner(secret);
		Map<String, Object> claims = new HashMap<String, Object>();		
		claims.put("userId", user.getUserId());;
		claims.put("crtime", System.currentTimeMillis());
		String token = jwtSigner.sign(claims);		
		return token;
	}
	
	/**
	 * 以JSON格式输出
	 * @param response
	 */
	protected void responseOutWithJson(HttpServletResponse response,
			Object responseObject) {
		//将实体对象转换为JSON Object转换
		JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
