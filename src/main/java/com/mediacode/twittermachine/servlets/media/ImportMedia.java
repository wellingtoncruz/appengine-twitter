package com.mediacode.twittermachine.servlets.media;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import com.googlecode.objectify.Key;
import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.entities.Media;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

public class ImportMedia extends HttpServlet {
	private String jsonURL = "http://noticiagostosa.web2121.uni5.net/importmedia.php?dir=";
       
    public ImportMedia() { }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		jsonURL = jsonURL + request.getParameter("dir").toString();
		JSONArray jsonObj = new JSONArray(readJson(jsonURL));

		Long userKey = (long) request.getSession().getAttribute("USER_KEY");

		if(!userKey.equals(null)){
		Account account = ofy().load().key(Key.create(Account.class, userKey)).now();
		
		for(int i=0; i<jsonObj.length();i++){
				Media img = new Media(jsonObj.getString(i), account);
				ofy().save().entity(img).now();
			}
		}
	}

	protected String readJson(String jsonURL) throws IOException{
		URL url = new URL(jsonURL);
		
        BufferedReader in = new BufferedReader(
                                     new InputStreamReader(
                                     url.openStream()));
            String jsonFile = new String();
            String json;
            while ((json = in.readLine()) != null) jsonFile = jsonFile.concat(json);
            in.close();
            
        return jsonFile;
		
	}

}
