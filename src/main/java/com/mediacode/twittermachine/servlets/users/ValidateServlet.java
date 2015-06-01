package com.mediacode.twittermachine.servlets.users;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mediacode.twittermachine.statics.URLS;
import com.mediacode.twittermachine.entities.Account;

public class ValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ValidateServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Account userDb = Account.findBy("username", username);
		Account userPost = new Account(username, password);
		
		if(Account.verify(userDb, userPost)){
			request.getSession().setAttribute("LOGIN_OK", userDb.getUsername());
			request.getSession().setAttribute("USER_KEY", userDb.getId());
			response.sendRedirect(URLS.HOME_URL);
		} else {
			response.sendRedirect(URLS.LOGIN_URL);
		}

	}

}
