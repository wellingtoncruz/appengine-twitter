package com.mediacode.twittermachine.entities;

import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

@Entity
public final class Message {
	@Id
	private Long key;
	@Index private String code;
	private String message;
	private Ref<Media> image;
	@Index private Ref<Account> user;
	@Index private Date saved;
	@Index private Date posted;
	
	public Message(){}
	public Message(String code, String message, Media image, Account user, Date saved){
		this.setCode(code).setMessage(message).setPosted(null).setAccount(user).setSaved(saved).setMedia(image);
	}

	public Message(String code, String message, Account user, Date saved){
		this.setCode(code).setMessage(message).setPosted(null).setAccount(user).setSaved(saved);
	}

	public Long getKey(){
		return key;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getMessage(){
		return message;
	}
	
	public Media getMedia(){
		return image.get();
	}
	
	public Account getAccount(){
		return user.get();
	}
	
	public Date getSaved(){
		return saved;
	}

	public Date getPosted(){
		return posted;
	}
	
	public Message setCode(String code){
		this.code = code;
		return this;
	}
	
	public Message setMessage(String message){
		this.message = message;
		return this;
	}
	
	public Message setMedia(Media image){
		this.image = Ref.create(image);
		return this;
	}

	public Message setAccount(Account user){
		this.user = Ref.create(user);
		return this;
	}

	public Message setPosted(Date posted){
		this.posted = posted;
		return this;
	}
	
	public Message setPosted(){
		this.posted = new Date();
		return this;
	}

	public Message setSaved(Date date){
		this.saved = date;
		return this;
	}

	public Message setSaved(){
		this.saved = new Date();
		return this;
	}

	
}
