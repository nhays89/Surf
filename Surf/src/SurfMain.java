/**
 * 
 * @author Nicholas A. Hays
 */
public class SurfMain {

	public static void main(String[] args) {
		
	}
	
	private class Wave {
		private int myStartTime;
		private int myFunPoints;
		private int myWaitTime;
		private int myEndTime;
		
		public Wave(int startTime, int funPoints, int waitTime) {
			myStartTime = startTime;
			myFunPoints = funPoints;
			myWaitTime = waitTime;
			myEndTime = startTime + waitTime;
		}
		
		public boolean isOverlapping(Wave theOtherWave) {
			
			return true;
		}

		
		
		
		public int getMyStartTime() {
			return myStartTime;
		}

		public int getMyFunPoints() {
			return myFunPoints;
		}

		public int getMyWaitTime() {
			return myWaitTime;
		}

		public int getMyEndTime() {
			return myEndTime;
		}
		
		
	}
}
