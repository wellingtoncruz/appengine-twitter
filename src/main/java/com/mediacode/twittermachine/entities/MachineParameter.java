package com.mediacode.twittermachine.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

@Entity
public class MachineParameter {
	@Id @Index private Long id;
	@Index private Long userId;
	@Serialize Class machine;
	@Index String parameter;
	String data; 

	public MachineParameter(){}

	public MachineParameter(Long userId, Class machine) {
		this.setMachine(machine).setUserId(userId);
	}

	public MachineParameter(Long userId, Class machine, String parameter, String data) {
		this.setMachine(machine).setUserId(userId).setParameter(parameter).setData(data);
	}

	public Long getUserId() {
		return userId;
	}

	public Class getMachine() {
		return machine;
	}

	public String getParameter() {
		return parameter;
	}

	public String getData() {
		return data;
	}

	private MachineParameter setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	public MachineParameter setMachine(Class machine) {
		this.machine = machine;
		return this;
	}

	public MachineParameter setParameter(String parameter) {
		this.parameter = parameter;
		return this;
	}

	public MachineParameter setData(String data) {
		this.data = data;
		return this;
	}


}