package cn.org.citycloud.zwhs.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWTSigner;

@Component
public class TokenManager {
	private static TokenManager me = new TokenManager();

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static TokenManager getMe() {
		return me;
	}

	/**
	 * 生成token值
	 * 
	 * @param user
	 * @return
	 */
	public String generateToken(String secret) {
		JWTSigner jwtSigner = new JWTSigner(secret);
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("exptime", new Date().getTime());
		String token = jwtSigner.sign(claims);
		return token;
	}
}
