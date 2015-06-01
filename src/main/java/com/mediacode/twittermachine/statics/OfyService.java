package com.mediacode.twittermachine.statics;

import com.mediacode.twittermachine.entities.*;
import com.googlecode.objectify.*; 

public class OfyService {

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}