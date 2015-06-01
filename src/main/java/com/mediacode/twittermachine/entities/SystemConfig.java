package com.mediacode.twittermachine.entities;

import com.googlecode.objectify.annotation.*;

@Entity
public class SystemConfig {
	@Id @Index private Long id;
	Integer shortUrlMax;

	public SystemConfig(){
		id = 1L; // sempre o mesmo
	}

	public Integer getShortUrlMax() {
		return shortUrlMax;
	}

	public SystemConfig setShortUrlMax(Integer shortUrlMax) {
		this.shortUrlMax = shortUrlMax;
		return this;
	}

}