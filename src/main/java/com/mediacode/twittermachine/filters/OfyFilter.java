package com.mediacode.twittermachine.filters;
import com.mediacode.twittermachine.entities.*;
import com.mediacode.twittermachine.statics.OfyService;

public class OfyFilter extends com.googlecode.objectify.ObjectifyFilter {

	public OfyFilter(){
		super();
		OfyService.factory().register(Message.class);
		OfyService.factory().register(Media.class);
		OfyService.factory().register(OAuth.class);
		OfyService.factory().register(Account.class);
		OfyService.factory().register(Profile.class);
		OfyService.factory().register(UserConfig.class);
		OfyService.factory().register(SystemConfig.class);
		OfyService.factory().register(MachineParameter.class);
	}
	
}

	