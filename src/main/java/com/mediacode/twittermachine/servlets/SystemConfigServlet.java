package com.mediacode.twittermachine.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mediacode.twittermachine.statics.Configuration;
import com.mediacode.twittermachine.entities.SystemConfig;

import twitter4j.Twitter;
import twitter4j.TwitterAPIConfiguration;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

public class SystemConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SystemConfigServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setApplicationOnlyAuthEnabled(true);
		try {		
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			OAuth2Token token = twitter.getOAuth2Token();
			TwitterAPIConfiguration twitterConfig = twitter.getAPIConfiguration();
			SystemConfig systemConfig = new SystemConfig();
			systemConfig.setShortUrlMax(twitterConfig.getShortURLLength());
			ofy().save().entity(systemConfig).now();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
