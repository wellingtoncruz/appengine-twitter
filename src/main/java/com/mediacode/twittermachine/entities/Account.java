package com.mediacode.twittermachine.entities;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import com.mediacode.twittermachine.machines.messages.*;

import org.apache.commons.codec.digest.*;

import twitter4j.User;
import twitter4j.auth.AccessToken;

@Entity
public final class Account {
	@Id
	private Long id;
	@Index private String username;
	private String password;
	@Index private Ref<OAuth> oauth;
	@Index private Ref<Profile> profile;
	@Index private Ref<UserConfig> settings;
	
	public Account(){}
	public Account(String username, String password, OAuth oauth){
		this.setUsername(username).setPassword(password).setOAuth(oauth);
	}
	
	public Account(String username, String password){
		this.setUsername(username).setPassword(password);
	}

	public Long getId(){
		return id;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public OAuth getOAuth(){
		return oauth.get();
	}

	public Profile getProfile(){
		return profile.get(); 
	}

	public UserConfig getSettings(){
		return settings.get(); 
	}

	public Account setPassword(String password){
		this.password = DigestUtils.md5Hex(password);
		return this;
	}
	
	public Account setUsername(String username){
		this.username = username;
		return this;
	}
	
	public Account setOAuth(OAuth oauth){
		this.oauth = Ref.create(oauth);
		return this;
	}

	public Account setProfile(Profile profile){
		this.profile = Ref.create(profile);
		return this;
	}
	
	public Account setSettings(UserConfig settings){
		this.settings = Ref.create(settings);
		return this;
	}
	
	public void setup(AccessToken accessToken, User user){
        OAuth oauth = new OAuth(user.getId(), user.getScreenName(), accessToken.getToken(), accessToken.getTokenSecret());
    	ofy().save().entity(oauth).now();

		Profile profile = new Profile(user.getId(), user.getBiggerProfileImageURL(), user.getProfileBannerURL(), user.getName(), user.getScreenName(), user.getDescription(), user.getStatusesCount(), user.getFriendsCount(), user.getFollowersCount());
		ofy().save().entity(profile).now();
		
		UserConfig settings = new UserConfig(null,null,null);
		ofy().save().entity(settings).now();
		
		this.setOAuth(oauth).setProfile(profile).setSettings(settings);
		
		// Temporário
		MachineParameter machineParameter = new MachineParameter(user.getId(), FeedAndMediaMachine.class, "feed_url", "https://news.google.com/?edchanged=1&ned=pt-BR_br&output=rss");
		ofy().save().entity(machineParameter).now();
	}
	
	public static boolean verify(Account acc1, Account acc2){
		if(acc1 == null || acc2==null) return false;
		if(acc1.getUsername().equals(acc2.getUsername())) if(acc1.getPassword().equals(acc2.getPassword())) return true;
		return false;
	}
	
	public static Account findBy(String field, String value){
		return ofy().load().type(Account.class).filter(field, value).first().now();
	}

	
}
