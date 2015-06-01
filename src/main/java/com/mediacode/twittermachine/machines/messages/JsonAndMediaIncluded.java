package com.mediacode.twittermachine.machines.messages;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.googlecode.objectify.Key;
import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.entities.MachineParameter;
import com.mediacode.twittermachine.entities.Media;
import com.mediacode.twittermachine.entities.Message;
import com.mediacode.twittermachine.entities.SystemConfig;

import org.json.*;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

public class JsonAndMediaIncluded implements MessageMachine {
	
	protected ArrayList<Message> messages;
	protected ArrayList<Media> medias;
	protected Account user;
	protected Boolean postMedia = true;
	protected  static final Integer twitterMaxChars = 140;
	
	public JsonAndMediaIncluded(){}
	
	public JsonAndMediaIncluded(Account user){
		setAccount(user);
	}
	
	@Override
	public void ParseMessages() {
		messages = new ArrayList<Message>();
		medias = new ArrayList<Media>();
		
		MachineParameter machineParameter =  (MachineParameter) ofy().load().type(MachineParameter.class).filter("parameter", "json_url").filter("userId", user.getProfile().getTwitterId()).first().now();
		SystemConfig systemConfig = (SystemConfig) ofy().load().type(SystemConfig.class).first().now();

		JSONArray jsonData = null;
		try {
			jsonData = new JSONArray(getText(machineParameter.getData()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(int i=0; i<jsonData.length(); i++){
			JSONObject obj = (JSONObject) jsonData.get(i);
			System.out.println(obj.get("image").toString());
			String code = obj.getString("code");
			String link = obj.getString("url");
			String phrase = obj.getString("phrase");
			String author = obj.getString("author");
			Date saved = new Date();
			Message message = new Message(code, phrase + " " + author + " " + link , user, saved);
			messages.add(message);
			Media media = new Media(obj.getString("image"), user);
			medias.add(media);
		}
		
	} 

	@Override
	public void SaveMessages() {
		for(int i=0; i<messages.size(); i++){
			Message message = messages.get(i); 
			List<Message> messagesDb = ofy().load().type(Message.class).filter("code",message.getCode()).list();
			if(messagesDb.size() <= 0){
				Media media = medias.get(i);
				ofy().save().entity(media).now();
				message.setMedia(media);
				ofy().save().entity(message).now();
			}
		}
	}
	
	public JsonAndMediaIncluded setAccount(Account user){
		this.user = user;
		return this;
	}

	@Override
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.append(inputLine);

        in.close();

        return response.toString();
    }

}
