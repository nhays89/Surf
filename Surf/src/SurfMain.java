import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Nicholas A. Hays
 */
public class SurfMain {
	static List<Wave> myWaves = new ArrayList<Wave>();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			System.setIn(new FileInputStream("input3.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner scanIn = new Scanner(System.in);
		System.out.println("Before nextInt");
		int numOfWaves = scanIn.nextInt();
		System.out.println("Num waves: " + numOfWaves);
		for (int i = 0; i < numOfWaves; i++) {
			Wave wave = new Wave(scanIn.nextInt(), scanIn.nextInt(), scanIn.nextInt());
			System.out.println("Wave: " + wave.myStartTime + ", " + wave.myFunPts + ", " + wave.myWaitTime);
			myWaves.add(wave);
		}
		Collections.sort(myWaves);
		scanIn.close();
		
		System.out.println("Sorted...");
		for(Wave wave : myWaves) {
			System.out.println("Wave: " + wave.myStartTime + ", " + wave.myFunPts + ", " + wave.myWaitTime + ", " + wave.myEndTime);
		}
		

		for (int i = 0; i < numOfWaves; i++) {
			Wave currentWave = myWaves.get(i);
			if (i == 0) {
				currentWave.setTotMaxFunPts(myWaves.get(i).myFunPts);
				currentWave.setActualMaxFunPoints(myWaves.get(i).myFunPts);
			} else {
				
				Wave nonOverlappingWave;
				
				if ((nonOverlappingWave = largestNonOverlapping(i)) != null) {
					currentWave.setActualMaxFunPoints(currentWave.myFunPts + nonOverlappingWave.myActualMaxFunPts);
					if (currentWave.myActualMaxFunPts > myWaves.get(i - 1).myTotMaxFunPts) {
						currentWave.setTotMaxFunPts(currentWave.myActualMaxFunPts);
					} else {
						currentWave.setTotMaxFunPts(myWaves.get(i - 1).myTotMaxFunPts);
					}
				} else {
					
					currentWave.setActualMaxFunPoints(currentWave.myFunPts);
					if (currentWave.myActualMaxFunPts > myWaves.get(i - 1).myTotMaxFunPts) {
						currentWave.setTotMaxFunPts(currentWave.myActualMaxFunPts);
					} else {
						currentWave.setTotMaxFunPts(myWaves.get(i - 1).myTotMaxFunPts);
					}
				}
				
				
			}
		}
		for(Wave wave : myWaves) {
			System.out.println("my actual " + wave.myActualMaxFunPts + ", mytotal: " + wave.myTotMaxFunPts);
		}
		System.out.println(myWaves.get(myWaves.size() - 1).myTotMaxFunPts
				);
		

	}
	public static Wave largestNonOverlapping(int theWave) {
		Wave currWave = myWaves.get(theWave);
		int largestActual = 0, indexOfActual = -1;
		for( int i = theWave - 1; i >= 0; i--) {
			if (myWaves.get(i).myEndTime <= currWave.myStartTime && myWaves.get(i).myActualMaxFunPts > largestActual) {
				largestActual = myWaves.get(i).myActualMaxFunPts;
				indexOfActual = i;
			}
		}
		if (indexOfActual != -1) return myWaves.get(indexOfActual); 
		else return null;
	}
	
	private static class Wave implements Comparable {
		private int myStartTime;
		private int myFunPts;
		private int myWaitTime;
		private int myEndTime;
		private int myActualMaxFunPts;
		private int myTotMaxFunPts;

		public Wave(int startTime, int funPoints, int waitTime) {
			myStartTime = startTime;
			myFunPts = funPoints;
			myWaitTime = waitTime;
			myEndTime = startTime + waitTime;
		}

		public boolean isOverlapping(Wave theOtherWave) {
			if (this.myStartTime < theOtherWave.myEndTime) {
				return true;
			}
			return false;
		}
		
		public void setActualMaxFunPoints(int funPoints) {
			myActualMaxFunPts = funPoints;
		}

		public void setTotMaxFunPts(int funPoints) {
			myTotMaxFunPts = funPoints;
		}

		public int getMyStartTime() {
			return myStartTime;
		}

		public int getMyFunPoints() {
			return myFunPts;
		}

		public int getMyWaitTime() {
			return myWaitTime;
		}

		public int getMyEndTime() {
			return myEndTime;
		}

		@Override
		public int compareTo(Object theOtherWave) {
			if (this.myStartTime < ((Wave) theOtherWave).myStartTime) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
