/**
 * Authors: Nicholas A. Hays & Ethan Rowell
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Kicks off main program. Input comes from piped input file. 
 * 
 * @author Nicholas A. Hays & Ethan Rowell
 */
public class SurfMain {
	static List<Wave> myWaves = new ArrayList<Wave>();
	static int numOfWaves;

	/**
	 * Main method to kick off program. Accepts piped file input.
	 * Refer to github for input syntax. 
	 * 
	 * @param cmd line args not accpeted
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		numOfWaves = scan.nextInt();
		for (int i = 0; i < numOfWaves; i++) {
			Wave wave = new Wave(scan.nextInt(), scan.nextInt(), scan.nextInt());
			myWaves.add(i, wave);
			//System.out.println(
				//	"start time: " + wave.myStartTime + " points: " + wave.myFunPts + " duration " + wave.myEndTime);
		}
		scan.close();
		Collections.sort(myWaves);
		
		System.out.println("sorted");

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
		System.out.println(myWaves.get(myWaves.size() - 1).myTotMaxFunPts);

	}
	
	/**
	 * Finds the largest non overlapping wave that ends before this wave begins. 
	 * 
	 * @param theWave the wave index in the set of waves.  
	 * @return the largest non overlapping wave that ends before the  
	 */
	public static Wave largestNonOverlapping(int theWave) {
		Wave currWave = myWaves.get(theWave);
		int largestActual = 0, indexOfActual = -1;
		for (int i = theWave - 1; i >= 0; i--) {
			if (myWaves.get(i).myEndTime <= currWave.myStartTime && myWaves.get(i).myActualMaxFunPts > largestActual) {
				largestActual = myWaves.get(i).myActualMaxFunPts;
				indexOfActual = i;
			}
		}
		if (indexOfActual != -1)
			return myWaves.get(indexOfActual);
		else
			return null;
	}
	
	/**
	 * Wave object that holds data about each wave in the set of waves. 
	 * 
	 * @author Nicholas A. Hays & Ethan Rowell
	 */
	private static class Wave implements Comparable<Wave> {
		private int myStartTime;
		private int myFunPts;
		private int myEndTime;
		private int myActualMaxFunPts;
		private int myTotMaxFunPts;
		
		/**
		 * Constructs the wave, and assigns values to each wave. 
		 * @param startTime the start time the wave will crash on the shore.
		 * @param funPoints the value assigned to each wave (i.e number representing height, power, etc.)
		 * @param waitTime the duration of the wave plus the time it takes to swim back out to shore. 
		 */
		public Wave(int startTime, int funPoints, int waitTime) {
			myStartTime = startTime;
			myFunPts = funPoints;
			myEndTime = startTime + waitTime;
		}
		
		/**
		 * Sets the combined total fun points that can acutally be realized by taking this particular
		 * wave.  
		 * @param the actual combined maximum points that can be made taking this wave. 
		 */
		public void setActualMaxFunPoints(int points) {
			myActualMaxFunPts = points;
		}
		
		/**
		 * Sets the combined total fun points that can be made up to this wave. This 
		 * method may take into account previous wave selections that do not include this wave 
		 * if they produce larger totals. 
		 * 
		 * @param funPoints the value assigned to each wave. 
		 */
		public void setTotMaxFunPts(int points) {
			myTotMaxFunPts = points;
		}

		@Override
		public int compareTo(Wave theOtherWave) {
			if (this.myStartTime < (theOtherWave).myStartTime) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
