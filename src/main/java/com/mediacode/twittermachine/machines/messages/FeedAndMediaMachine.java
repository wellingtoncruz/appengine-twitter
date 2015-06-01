package com.mediacode.twittermachine.machines.messages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Key;
import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.entities.MachineParameter;
import com.mediacode.twittermachine.entities.Media;
import com.mediacode.twittermachine.entities.Message;
import com.mediacode.twittermachine.entities.SystemConfig;
import com.mediacode.twittermachine.feed.RssParser;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

public class FeedAndMediaMachine implements MessageMachine {
	
	protected ArrayList<Message> messages;
	protected Account user;
	protected Boolean postMedia = true;
	protected  static final Integer twitterMaxChars = 140;
	
	public FeedAndMediaMachine(){}
	
	public FeedAndMediaMachine(Boolean postMedia){
		this.postMedia = postMedia;
	}
	
	public FeedAndMediaMachine(Boolean postMedia, Account user){
		this.postMedia = postMedia;
		setAccount(user);
	}
	
	public FeedAndMediaMachine(Account user){
		setAccount(user);
	}
	
	@Override
	public void ParseMessages() {
		messages = new ArrayList<Message>();
		MachineParameter machineParameter =  (MachineParameter) ofy().load().type(MachineParameter.class).filter("parameter", "feed_url").filter("userId", user.getProfile().getTwitterId()).first().now();
		SystemConfig systemConfig =  (SystemConfig) ofy().load().type(SystemConfig.class).first().now();
		Integer shortUrlMax = systemConfig.getShortUrlMax()+3;
		if(postMedia) shortUrlMax = shortUrlMax*2;
		Integer charsLeft = (FeedAndMediaMachine.twitterMaxChars - shortUrlMax);
		
		RssParser rss = new RssParser(machineParameter.getData());
		rss.parse();
		
		RssParser.RssFeed feed = rss.getFeed();
		
		for (int i = 0; i < feed.items.size(); i++){
			try {
				String[] titles = feed.items.get(i).title.split("-");
				String code = feed.items.get(i).code;
				String source = titles[titles.length-1].trim();
				String sourceParts[] = source.split("\\.");
				
				if(sourceParts.length > 1){
					String source2 = "";
					for(String part : sourceParts){
						if(part.equals("com")) continue;
						if(part.equals("br")) continue;
						if(part.equals("pt")) continue;
						if(part.contains("(")) continue;
						source2 = source2 + part + " ";
					}
					source = source2;
				}
				
				String title = source + ": ";
				
				for(int j=0; j<titles.length-1; j++){
					if(j==titles.length-2)
						title = title + titles[j];
					else 
						title = title + titles[j] + "-";
				}
				
				if(title.length() > charsLeft){
					title = title.substring(0, charsLeft-7) + "...";
				}
				String[] links = feed.items.get(i).link.split("url=");
				String link = links[links.length-1];
				if(link.contains("(")) continue;
				@SuppressWarnings("deprecation")
				Date saved = new Date(feed.items.get(i).pubDate);
				Message message = new Message(code, title + " " + link, user, saved);
				messages.add(message);
				
			} catch (Exception e) {e.printStackTrace();}
		}
	} 	

	@Override
	public void SaveMessages() {
		for(Message message:messages){
			List<Message> messagesDb = ofy().load().type(Message.class).filter("code",message.getCode()).list();
			if(messagesDb.size() <= 0){
				Media media;
				media = (postMedia) ? Media.getReadyForUse(this.user) : null; 
				message.setMedia(media);
				ofy().save().entity(message).now();
			}
		}
	}
	
	public FeedAndMediaMachine setAccount(Account user){
		this.user = user;
		return this;
	}

	@Override
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	

}
