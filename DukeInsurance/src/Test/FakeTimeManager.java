package Test;

import com.duke.TimeManager;

public class FakeTimeManager implements TimeManager {
	
	long simulatedMilliseconds = 0;
	public long getCurrentMilliseconds(){
		return System.currentTimeMillis() + simulatedMilliseconds;
	}
	
	public void AddSimulatedElapsedMinutes(long pSimulatedTime){
		simulatedMilliseconds = pSimulatedTime*60*1000;
	}
}
