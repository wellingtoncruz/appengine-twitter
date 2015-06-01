package com.mediacode.twittermachine.servlets.users;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.entities.Profile;
import com.mediacode.twittermachine.entities.UserConfig;
import com.mediacode.twittermachine.machines.messages.MessageMachine;
import com.mediacode.twittermachine.statics.Machines;

public class LoadMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoadMessages() {
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
			
			try {
				
				if(settings.getMessageMachine() != null){
					Constructor constructor = settings.getMessageMachine().getConstructor(Account.class);
					MessageMachine messageMachine = (MessageMachine) constructor.newInstance(account);
					messageMachine.ParseMessages();
					messageMachine.SaveMessages();
				}

			} catch (Exception e){ e.printStackTrace(response.getWriter()); }
			
			
		}

	}

}
