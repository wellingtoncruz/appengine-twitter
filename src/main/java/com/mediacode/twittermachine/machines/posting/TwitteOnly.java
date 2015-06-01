package com.mediacode.twittermachine.machines.posting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.googlecode.objectify.Key;
import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.entities.Media;
import com.mediacode.twittermachine.entities.Message;
import com.mediacode.twittermachine.machines.messages.FeedAndMediaMachine;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

public class TwitteOnly implements PostingMachine {

	protected Account user;
	protected Message lastMessage;
	protected Message lastPosted;

	public TwitteOnly(Account user){
		setAccount(user);
	}
	
	public Message getLastMessage(){
		return lastMessage;
	}

	@Override
	public void PostNextMessage(PrintWriter response) throws IOException, TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		
		lastMessage = ofy().load().type(Message.class).filter("user", user).filter("posted", null).order("-saved").first().now();
		//lastPosted = ofy().load().type(Message.class).filter("user", user).filter("posted !=", null).order("-posted").first().now();
		//int toBePosted = ofy().load().type(Message.class).filter("user", user).filter("posted", null).count();
		//response.print(lastPosted.getMessage());
		
		if(lastMessage != null){/*
			Date now = new Date();
			long timeFromLastPost = (now.getTime() - lastPosted.getSaved().getTime()) / 1000 / 60;
			response.println("time: "+timeFromLastPost);
			response.println("count: "+toBePosted);
			
			if(toBePosted <=2){
				if(timeFromLastPost <=30) return;
			} else if (toBePosted <=5){
				if(timeFromLastPost <=20) return;
			} else if (toBePosted <=10){
				if(timeFromLastPost <=15) return;
			} else if (toBePosted > 10){
				if(timeFromLastPost <=10) return;
			}
			*/
			Media media = lastMessage.getMedia();
			twitter.setOAuthAccessToken(new AccessToken(user.getOAuth().getToken(), user.getOAuth().getTokenSecret()));
			StatusUpdate statusUpdate = new StatusUpdate(lastMessage.getMessage());
			URL mediaUrl = new URL(media.getURL());
			URLConnection urlconfig = mediaUrl.openConnection();
			urlconfig.setConnectTimeout(60000);
			urlconfig.setReadTimeout(60000);
			InputStream is = urlconfig.getInputStream();
			statusUpdate.setMedia("Image", is);
			Status status = twitter.updateStatus(statusUpdate);
			
			if(status.getId() > 0L){
				lastMessage.setPosted();
				ofy().save().entity(lastMessage).now();
			}
		}
	}

	public TwitteOnly setAccount(Account user){
		this.user = user;
		return this;
	}


}
