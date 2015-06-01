package com.mediacode.twittermachine.servlets.users;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.TwitterException;

import com.googlecode.objectify.Key;
import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.entities.Profile;
import com.mediacode.twittermachine.entities.UserConfig;
import com.mediacode.twittermachine.machines.messages.MessageMachine;
import com.mediacode.twittermachine.machines.posting.PostingMachine;
import com.mediacode.twittermachine.statics.Machines;

public class PostMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PostMessages() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Account> users;
		String user = request.getParameter("user");
		
		if(user == null)
			users = ofy().load().type(Account.class).list();
		else {
			users = new ArrayList<Account>();
			Account acc = ofy().load().key(Key.create(Account.class, new Long(request.getParameter("user")))).now();
			users.add(acc);
		}
		
		for(Account account : users){
			UserConfig settings = account.getSettings();
			
			if(settings.getPostingMachine() != null){
				Constructor constructor;
				PostingMachine postingMachine=null;
				try {
					constructor = settings.getPostingMachine().getConstructor(Account.class);
					postingMachine = (PostingMachine) constructor.newInstance(account);
					postingMachine.PostNextMessage(response.getWriter());
				} catch (Exception e) {
					response.getWriter().print(postingMachine.getLastMessage().getMessage());
					e.printStackTrace(response.getWriter());
				}
			}

			
		}

	}

}
