package com.mediacode.twittermachine.statics;

import java.util.HashMap;

import com.mediacode.twittermachine.machines.messages.*;
import com.mediacode.twittermachine.machines.posting.*;

public final class Machines {

	public static final HashMap<Integer, Class> messagesMachines;
    static
    {
    	messagesMachines = new HashMap<Integer, Class>();
    	messagesMachines.put(101, FeedAndMediaMachine.class);
    	messagesMachines.put(102, FeedOnlyMachine.class);
    	messagesMachines.put(103, JsonAndMediaIncluded.class);
    }

	public static final HashMap<Integer, Class> postingMachines;
    static
    {
    	postingMachines = new HashMap<Integer, Class>();
    	postingMachines.put(201, TwitteOnly.class);
    }

	
}
