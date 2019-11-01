package com.liaoin.demo.security;

import com.github.surpassm.security.exception.SurpassmAuthenticationException;
import com.liaoin.demo.entity.user.UserInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author mc
 * Create date 2019/3/4 15:53
 * Version 1.0
 * Description
 */
@Configuration
public class BeanConfig {

	@Resource
	private TokenStore tokenStore;

	public UserInfo getAccessToken(String accessToken){
		OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
		if (oAuth2Authentication == null){
			throw new SurpassmAuthenticationException("token已失效");
		}
		UserInfo object = (UserInfo)oAuth2Authentication.getPrincipal();
		try {
			if (object != null) {
				return object;
			}
		} catch (Exception e) {
			throw new SurpassmAuthenticationException("请登陆");
		}
		throw new SurpassmAuthenticationException("请登陆");
	}
}
