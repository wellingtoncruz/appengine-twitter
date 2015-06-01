package com.mediacode.twittermachine.filters;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.statics.URLS;

public class OAuthFilter implements Filter {

    public OAuthFilter(){}

	public void destroy(){}
	
	public void init(FilterConfig fConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		
		if(httprequest.getRequestURI().contains(URLS.SYSTEM_ADMIN)){	
			chain.doFilter(httprequest, httpresponse);
			return;
		}
		
		String[] allowedURI = {
				URLS.OAUTH_URL,
				URLS.LOGIN_URL,
				URLS.LOGIN_VALIDATE_URL,
				URLS.LOGIN_ADMIN,
				URLS.FRIENDS_URL,
				URLS.MESSAGES_URL,
				URLS.POSTING_URL,
				URLS.IMPORT_URL
		};
		
		if(Arrays.asList(allowedURI).contains(httprequest.getRequestURI())){
			chain.doFilter(httprequest, httpresponse);
			return;
		}

		String usernameSession = null;

		try {
			usernameSession = (String) httprequest.getSession().getAttribute("OAUTH_OK");
			if(usernameSession.length() > 2){
				chain.doFilter(httprequest, httpresponse);
			} else throw new Exception();
		} catch (Exception e){
			httpresponse.sendRedirect(URLS.OAUTH_URL);
		}
		
	
		

	}

}
