import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class hashtagcounter {
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			System.err.println("Please enter file name as argument");
			System.exit(0);
		}
		Scanner s = new Scanner(new File(args[0]));
		PrintWriter out = new PrintWriter(new File("output_file.txt"));
		FibonacciHeap heap = new FibonacciHeap();
		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (line.toLowerCase().equals("stop")) {
				break;
			}
			if (line.charAt(0) == '#') {
				String[] entry = line.split(" ");
				heap.insert(entry[0].substring(1), Integer.parseInt(entry[1]));
			} else {
				int query = Integer.parseInt(line);
				String[] result = heap.query(query);
				String str = "";
				for (String r : result) {
					str += "," + r;
				}
				out.println(str.substring(1));
			}
			// heap.printHeap();
		}
		s.close();
		out.close();
	}
}
