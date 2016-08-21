package com.duke;

public class SystemTimeManager implements TimeManager {
	
	public long getCurrentMilliseconds(){
		return System.currentTimeMillis();
	}

}
