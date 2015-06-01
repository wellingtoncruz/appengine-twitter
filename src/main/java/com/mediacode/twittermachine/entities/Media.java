package com.mediacode.twittermachine.entities;

import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

@Entity
public final class Media {

	@Id
	private Long key;
	private String url;
	@Index private Date lastused;
	@Index private Ref<Account> user;
	
	public Media(){}
	
	public Media(String url,Account user){
		this.setURL(url).setAccount(user);
		this.setAsNew();
	}
	
	public Long getKey(){
		return key;
	}
	
	public String getURL(){
		return url;
	}
	
	public Media setURL(String url){
		this.url = url;
		return this;
	}
	
	public Media setUsedNow(){
		this.lastused = new Date();
		return this;
	}

	public Media setAsNew(){
		this.lastused = new Date(1388534400L);
		return this;
	}
	
	public Media setAccount(Account user){
		this.user = Ref.create(user);
		return this;
	}


	static public Media getReadyForUse(Account user){
		Media media = ofy().load().type(Media.class).filter("user", user).order("lastused").first().now();
		media.setUsedNow();
		ofy().save().entity(media).now();
		return media;
	}
	
}
