package com.mediacode.twittermachine.machines.messages;

import java.util.ArrayList;

import com.mediacode.twittermachine.entities.Account;
import com.mediacode.twittermachine.entities.Message;
import com.mediacode.twittermachine.feed.RssParser;

public class FeedOnlyMachine extends FeedAndMediaMachine implements MessageMachine {
	
	public FeedOnlyMachine(){
		super(false);
	}
	
	public FeedOnlyMachine(Account user){
		super(false, user);
	}
	

}
