package com.mediacode.twittermachine.servlets;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import com.googlecode.objectify.Key;
import com.mediacode.twittermachine.entities.*;
import com.mediacode.twittermachine.machines.messages.FeedAndMediaMachine;
import com.mediacode.twittermachine.machines.messages.JsonAndMediaIncluded;
import com.mediacode.twittermachine.machines.messages.MessageMachine;
import com.mediacode.twittermachine.statics.Machines;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HomeServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Long userKey = 5464572790046720L;
		Long userKey = (long) request.getSession().getAttribute("USER_KEY");

		Account account = ofy().load().key(Key.create(Account.class, userKey)).now();
		Profile profile = account.getProfile();
		UserConfig settings = account.getSettings();

		if(request.getParameter("message_machine")!=null && request.getParameter("posting_machine")!=null ){
			Integer messageMachineHash = Integer.parseInt(request.getParameter("message_machine"));
			Integer postingMachineHash = Integer.parseInt(request.getParameter("posting_machine"));

            for (Map.Entry<Integer, Class> entry : Machines.messagesMachines.entrySet()) {
            	
                if(messageMachineHash.equals(entry.getKey())){
                	Class messageMachine = entry.getValue();
                    settings.setMessageMachine(messageMachine);
                	break;
                }
            }

            for (Map.Entry<Integer, Class> entry : Machines.postingMachines.entrySet()) {

            	if(postingMachineHash.equals(entry.getKey())){
                	Class postingMachine = entry.getValue();
                	settings.setPostingMachine(postingMachine);
                	break;
                }
            }
            
            settings.setFriendMachine(null);
            ofy().save().entity(settings).now();     
            
		}

		request.setAttribute("profile", profile);
		request.setAttribute("settings", settings);
		request.getRequestDispatcher("/home.jsp").forward(request, response);

		/*
		try {
			
			if(settings.getMessageMachine() != null){
				Constructor constructor = settings.getMessageMachine().getConstructor(Account.class);
				MessageMachine messageMachine = (MessageMachine) constructor.newInstance(account);
				messageMachine.ParseMessages();
				messageMachine.SaveMessages();
			}
			
			request.setAttribute("profile", profile);
			request.setAttribute("settings", settings);
			request.getRequestDispatcher("/home.jsp").forward(request, response);

		} catch (Exception e){ e.printStackTrace(response.getWriter()); }
		 */
	}
	
	

}
