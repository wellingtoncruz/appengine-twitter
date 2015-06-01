package com.mediacode.twittermachine.machines.posting;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import twitter4j.TwitterException;

import com.mediacode.twittermachine.entities.Message;

public interface PostingMachine {

	public void PostNextMessage(PrintWriter response) throws MalformedURLException, IOException, TwitterException;
	public Message getLastMessage();
	
}
