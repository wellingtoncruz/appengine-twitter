package com.mediacode.twittermachine.servlets.users;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.entities.MachineParameter;
import com.mediacode.twittermachine.entities.Media;
import com.mediacode.twittermachine.machines.messages.FeedAndMediaMachine;
import com.mediacode.twittermachine.machines.messages.JsonAndMediaIncluded;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Account acc = new Account("frases", "crow1515");
//		ofy().save().entity(acc).now();

		Long userKey = 5812356424663040L;
		Account account = ofy().load().key(Key.create(Account.class, userKey)).now();

		MachineParameter machineParameter = new MachineParameter(account.getProfile().getTwitterId(), JsonAndMediaIncluded.class, "json_url", "http://frases.art.br/phrases/twitter/3");
		ofy().save().entity(machineParameter).now();
	}

}
