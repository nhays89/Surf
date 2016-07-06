import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FileCreator {

	public static void main(String[] args) {
		try {
			FileWriter fw = new FileWriter("input_long.txt");
			fw.write(300000 + "" + '\n');
			Random r = new Random();
			ArrayList<int[]> array = new ArrayList<int[]>();
			for (int i = 0; i < 300000; i++) {
				int[] arr = new int[] {i, r.nextInt(1000000), r.nextInt(100) };
				//System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2]);
				array.add(arr);
			}
			shuffle(array);
			
			for (int i = 0; i < array.size(); i++) {
				// System.out.println(array.get(i)[0] + " " + array.get(i)[1] + " " + array.get(i)[2]);
				String stuff = array.get(i)[0] + " " + array.get(i)[1] + " " + array.get(i)[2] + '\n';
				fw.write(stuff);
			}
			
			/*for (int[] wave : array) {
				fw.write(wave[0] + " " + wave[1] + " " + wave[2]);
			}*/
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void shuffle(ArrayList<int[]> theList) {
		Random r = new Random();
		for (int i = theList.size() - 1; i > 0; i--) {
			int index = r.nextInt(i + 1);
			int[] temp = new int[]{theList.get(index)[0], theList.get(index)[1], theList.get(index)[2]};
			theList.set(index, theList.get(i));
			theList.set(i, temp);
		}
	}
}
