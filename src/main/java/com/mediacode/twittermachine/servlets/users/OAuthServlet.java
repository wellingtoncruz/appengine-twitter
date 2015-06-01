package com.mediacode.twittermachine.servlets.users;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.entities.OAuth;
import com.mediacode.twittermachine.statics.Configuration;
import com.mediacode.twittermachine.statics.URLS;

import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.conf.ConfigurationBuilder;
import static com.mediacode.twittermachine.statics.OfyService.ofy;

public class OAuthServlet extends HttpServlet {
       
    private static final long serialVersionUID = -5758891870341696717L;


	public OAuthServlet() {  super();   }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Twitter twitter = TwitterFactory.getSingleton();
		Account userDb = Account.findBy("username", (String) request.getSession().getAttribute("LOGIN_OK"));
		RequestToken requestToken = null;
		AccessToken accessToken = null;
        User user = null;

        try {
			OAuth oauth = userDb.getOAuth();
            twitter.setOAuthAccessToken(new AccessToken(oauth.getToken(), oauth.getTokenSecret()));
            request.getSession().setAttribute("OAUTH_OK", oauth.getTwitterId().toString());
            response.sendRedirect(URLS.HOME_URL);
            
		} catch (Exception pe){
			
			//pe.printStackTrace(response.getWriter());

	    	try {
			    requestToken = twitter.getOAuthRequestToken();
				request.getSession().setAttribute("requestToken", requestToken);
			} catch (TwitterException e2) {
				requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
			}
	    	
	
		    if(request.getParameter("oauth_verifier") != null){
		        String verifier = request.getParameter("oauth_verifier");
		        
	            try {
	            	accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
				} catch (TwitterException te) {
					te.printStackTrace(response.getWriter());
				}

	            try {
	            	user = twitter.verifyCredentials();
				} catch (Exception e) {
					e.printStackTrace(response.getWriter());;
				}

            	userDb.setup(accessToken, user);
            	
            	ofy().save().entity(userDb).now();
	            request.getSession().setAttribute("OAUTH_OK", userDb.getOAuth().getTwitterId());
	            
	            response.sendRedirect(URLS.HOME_URL);
	            
		    } else {
	
		    	response.sendRedirect(requestToken.getAuthorizationURL());
		    	
		    }
		}
	}
}

