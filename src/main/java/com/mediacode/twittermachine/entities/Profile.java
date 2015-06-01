package com.mediacode.twittermachine.entities;

import java.sql.Timestamp;
import java.util.Date;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import static com.mediacode.twittermachine.statics.OfyService.ofy;

@Entity
public class Profile {
	@Id @Index private Long twitterId;
	@Parent Ref<Account> account;
	private String profilePicture;
	private String bannerPicture;
	private String name;
	private String screenName;
	private String description;
	private int statuses;
	private int friends;
	private int followers;
	private Date updated;
	//@Ignore private static final Long expires = 86400000L;
	@Ignore private static final Long expires = 10L;

	public Profile(){}

	public Profile(Long id, String profilePicture, String bannerPicture, String name, String screenName, String description, int statuses, int friends, int followers) {
		this.setTwitterId(id).setProfilePicture(profilePicture).setBannerPicture(bannerPicture).setName(name).setScreenName(screenName).setDescription(description).setStatuses(statuses).setFriends(friends).setFollowers(followers);
	}

	@OnSave public void onSave(){
		this.updated = new Date();
	}
	
	public Long getTwitterId() {
		return twitterId;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public String getBannerPicture() {
		return bannerPicture;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getDescription() {
		return description;
	}

	public int getStatuses() {
		return statuses;
	}

	public int getFriends() {
		return friends;
	}

	public int getFollowers() {
		return followers;
	}
	
	public Date getUpdated(){
		return updated;
	}
	
	public Long getExpires(){
		return expires;
	}

	public Profile setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
		return this;
	}

	public Profile setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
		return this;
	}

	public Profile setBannerPicture(String bannerPicture) {
		this.bannerPicture = bannerPicture;
		return this;
	}

	public Profile setName(String name) {
		this.name = name;
		return this;
	}

	public Profile setScreenName(String screenName) {
		this.screenName = screenName;
		return this;
	}

	public Profile setDescription(String description) {
		this.description = description;
		return this;
	}

	public Profile setStatuses(int statuses) {
		this.statuses = statuses;
		return this;
	}

	public Profile setFriends(int friends) {
		this.friends = friends;
		return this;
	}

	public Profile setFollowers(int followers) {
		this.followers = followers;
		return this;
	}
	
	@OnLoad public void update(){
		Date now = new Date();
		if(getUpdated().getTime() < (now.getTime() - getExpires())){
			Twitter twitter = TwitterFactory.getSingleton();
			User user;
			try {
				user = twitter.verifyCredentials();
				this.setProfilePicture(user.getBiggerProfileImageURL()).setBannerPicture(user.getProfileBannerURL()).setName(user.getName()).setScreenName(user.getScreenName()).setDescription(user.getDescription()).setStatuses(user.getStatusesCount()).setFriends(user.getFriendsCount()).setFollowers(user.getFollowersCount());
				ofy().save().entity(this).now();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}