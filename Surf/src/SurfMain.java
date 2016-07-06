
/**
 * Authors: Nicholas A. Hays & Ethan Rowell
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;


/**
 * Kicks off main program. Input comes from piped input file in the form of
 * space seperated integers. Refer to github for syntax. The program determines
 * the best possible waves to surf given a list of waves. Principle of dynamic
 * programming.
 * 
 * @author Nicholas A. Hays & Ethan Rowell
 */
public class SurfMain {
	/** 
	 * List of input waves.
	 */
	static List<Wave> myWaves = new ArrayList<Wave>();
	/**
	 * Queue that maintains a list of waves sorted by end time.
	 */
	static PriorityQueue<Wave> myQueue;
	/**
	 * Class integers.
	 */
	static int myGlobalMax, myLocalMax, myNumOfWaves;

	/**
	 * Main method to kick off program. Accepts piped file input. Refer to
	 * github for input syntax.
	 * 
	 * @param args
	 *           cmd line args not accpeted, only piped input from file.
	 */
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Scanner scan = new Scanner(System.in);
		myNumOfWaves = scan.nextInt();
		for (int i = 0; i < myNumOfWaves; i++) {
			Wave startWave = new Wave(scan.nextInt(), scan.nextInt(), scan.nextInt());
			myWaves.add(startWave);
		}
		scan.close();
		myQueue = new PriorityQueue<Wave>(10, Wave.compareEndTime());
		Collections.sort(myWaves, Wave.compareStartTime());
		myWaves.get(0).setMaximumPoints(myWaves.get(0).myFunPoints);
		myWaves.get(0).setActualPoints(myWaves.get(0).myFunPoints);
		myQueue.add(myWaves.get(0));
		for (int i = 1; i < myNumOfWaves; i++) {
			Wave currentWave = myWaves.get(i);
			myQueue.add(currentWave);
			getLargestWave(currentWave.myStartTime);
			currentWave.setActualPoints(currentWave.myFunPoints + myGlobalMax);
			if (currentWave.myActualPoints > myWaves.get(i - 1).myRealPoints) {
				currentWave.setMaximumPoints(currentWave.myActualPoints);
			} else {
				currentWave.setMaximumPoints(myWaves.get(i - 1).myRealPoints);
			}
		}
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("elapsed time: " + elapsedTime  + " seconds");
		System.out.println(myWaves.get(myWaves.size() - 1).myRealPoints);
	}

	/**
	 * This method will maintain the largest actual fun points that can be made
	 * up until a given waves start time. For instance, if a wave starts at 100
	 * seconds, this method will use that start time to find the wave that ends
	 * before 100 seconds and has the greatest actual fun points. 
	 * 
	 * @param waveStartTime the start to compare against
	 */
	static void getLargestWave(int waveStartTime) {
		while (myQueue.peek() != null) {
			if (myQueue.peek().myEndTime <= waveStartTime) {
				Wave wave = myQueue.poll();
				if (wave.myActualPoints > myLocalMax) {
					myLocalMax = wave.myActualPoints;
				}
			} else {
				break;
			}
		}
		myGlobalMax = myLocalMax;
	}

	private static class Wave {
		/**
		 * Wave object that holds data about each wave in the set of waves.
		 * 
		 * @author Nicholas A. Hays & Ethan Rowell
		 */
		private int myStartTime;
		private int myFunPoints;
		private int myEndTime;
		private int myActualPoints;
		private int myRealPoints;

		/**
		 * Constructs the wave, and assigns values to each wave.
		 * 
		 * @param startTime
		 *            the start time the wave will crash on the shore.
		 * @param funPoints
		 *            the value assigned to each wave (i.e a number representing
		 *            height, power, etc.)
		 * @param waitTime
		 *            the duration of the wave plus the time it takes to swim
		 *            back out to shore.
		 */
		public Wave(int startTime, int funPoints, int waitTime) {
			myStartTime = startTime;
			myFunPoints = funPoints;
			myEndTime = startTime + waitTime;
		}

		/**
		 * Sets the combined total fun points that can acutally be realized by
		 * taking this particular wave.
		 * 
		 * @param the
		 *            actual combined maximum points that can be made taking
		 *            this wave.
		 */
		public void setActualPoints(int points) {
			myActualPoints = points;
		}

		/**
		 * Sets the combined total fun points that can be made up to this wave.
		 * This method may take into account previous wave selections that do
		 * not include this wave if they produce larger totals.
		 * 
		 * @param funPoints
		 *            the value assigned to each wave.
		 */
		public void setMaximumPoints(int points) {
			myRealPoints = points;
		}

		/**
		 * 
		 * @return Comparator object that sorts waves by start times.
		 */
		static Comparator<Wave> compareStartTime() {
			return new Comparator<Wave>() {
				public int compare(Wave w1, Wave w2) {
					if (w1.myStartTime < w2.myStartTime) {
						return -1;
					} else {
						return 1;
					}
				}
			};
		}

		/**
		 * 
		 * @return Comparator object that sorts Waves by ending time.
		 */
		static Comparator<Wave> compareEndTime() {
			return new Comparator<Wave>() {
				public int compare(Wave w1, Wave w2) {
					if (w1.myEndTime < w2.myEndTime) {
						return -1;
					} else if (w1.myEndTime == w2.myEndTime) {
						return 0;
					} else {
						return 1;
					}
				}
			};
		}
	}
}