package com.mediacode.twittermachine.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

@Entity
public class UserConfig {
	@Id @Index private Long twitterId;
	@Parent Ref<Account> account;
	@Serialize Class messageMachine;
	@Serialize Class friendMachine;
	@Serialize Class postingMachine;

	public UserConfig(){}

	public UserConfig(Class messageMachine, Class friendMachine, Class postingMachine) {
		this.setMessageMachine(messageMachine).setFriendMachine(friendMachine).setPostingMachine(postingMachine);
	}

	public Long getTwitterId() {
		return twitterId;
	}

	public Class<?> getMessageMachine() {
		return messageMachine;
	}

	public Class<?> getFriendMachine() {
		return friendMachine;
	}

	public Class<?> getPostingMachine() {
		return postingMachine;
	}

	public UserConfig setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
		return this;
	}

	public UserConfig setMessageMachine(Class<?> messageMachine) {
		this.messageMachine = messageMachine;
		return this;
	}

	public UserConfig setFriendMachine(Class<?> friendMachine) {
		this.friendMachine = friendMachine;
		return this;
	}

	public UserConfig setPostingMachine(Class<?> statusMachine) {
		this.postingMachine = statusMachine;
		return this;
	}

}