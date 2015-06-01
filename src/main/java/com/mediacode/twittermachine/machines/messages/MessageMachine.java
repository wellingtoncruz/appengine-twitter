package com.mediacode.twittermachine.machines.messages;

import java.util.ArrayList;

import com.mediacode.twittermachine.entities.Message;

public interface MessageMachine {

	public void ParseMessages();
	public void SaveMessages();
	public ArrayList<Message> getMessages();
	
}
