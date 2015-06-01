package com.mediacode.twittermachine.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

@Entity
public class OAuth {
	@Id @Index private Long twitterId;
	@Parent Ref<Account> account;
	private String name;
	private String token;
	private String tokenSecret;

	public OAuth(){}

	public OAuth(Long id, String name, String token, String tokenSecret) {
		this.setTwitterId(id).setName(name).setToken(token).setTokenSecret(tokenSecret);
	}

	public Long getTwitterId() {
		return twitterId;
	}

	public String getName() {
		return name;
	}

	public String getToken() {
		return token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public OAuth setTwitterId(Long id) {
		this.twitterId = id;
		return this;
	}
	
	public OAuth setName(String name) {
		this.name = name;
		return this;
	}

	public OAuth setToken(String token) {
		this.token = token;
		return this;
	}

	public OAuth setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
		return this;
	}

}