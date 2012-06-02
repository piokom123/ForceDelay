package com.gmail.wolinskip.forcedelay.data;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ForcePlayer {
	private String 					name;
	@SuppressWarnings("unchecked")
	private	Map<Integer, Long>[]	actions = (Map<Integer, Long>[]) new HashMap<?, ?>[2];
	
	public ForcePlayer() {
		for(int i=0;i<actions.length;i++) {
			actions[i]	= new HashMap<Integer, Long>();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getAction(Integer id, Integer actionId) {
		if(actionId > actions.length) {
			return 0L;
		}
		if(actions[actionId-1].containsKey(id)) {
			return actions[actionId-1].get(id);
		} else {
			return 0L;
		}
	}
	
	public void setAction(Integer id, Integer actionId) {
		if(actionId > actions.length) {
			return;
		}
		actions[actionId-1].put(id, Calendar.getInstance().getTimeInMillis());
	}
}
