import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;


public class Checker {
	final static int CHECK_TOTAL = 10;
	final static int MAX_RANGE = 1000;
	private static long[] insertion_result = new long[MAX_RANGE+1], merge_result = new long[MAX_RANGE+1], combine_result = new long[MAX_RANGE+1];
	private static long[] time_of_diff_k = new long[MAX_RANGE];
	

	private static String[] init(int n) { // generate n random data
		String[] data = new String[n];
		boolean[] use = new boolean[n];
		// generate serial of [0..n)
		for (int i = 0;i < n;i++)
			use[i] = false;
		for (int i = 0;i < n;i++) {
			int ran_num = (int)(Math.random()*n);
			while (use[ran_num])
				ran_num = (ran_num + 1) % n;
			use[ran_num] = true;
			data[i] = "" + ran_num;
		}
		return data;
	}

	private static void run(int tag, int n, int loop) { // check sort time(average); tag 0:I 1:M 2:C;
		long s_time, f_time, d_time;
		String[] data = new String[n];
		
		switch (tag) {
		case 0:// do insertion sort
			d_time = 0;
			for (int i = 0;i < loop;i++) {
				data = init(n);
				s_time = System.nanoTime();
				Sort.doInsertionSort(data);
				f_time = System.nanoTime();
				d_time = d_time + f_time - s_time;
			}
			d_time = d_time / loop;
			insertion_result[n] = d_time;
			break;
		case 1:// do merge sort
			d_time = 0;
			for (int i = 0;i < loop;i++) {
				data = init(n);
				s_time = System.nanoTime();
				Sort.doMergesort(data);
				f_time = System.nanoTime();
				d_time = d_time + f_time - s_time;
			}
			d_time = d_time / loop;
			merge_result[n] = d_time;
			break;
		case 2:// do combine sort
			final int K = 150;
			d_time = 0;
			for (int i = 0;i < loop;i++) {
				data = init(n);
				s_time = System.nanoTime();
				Sort.doMergeInsert(data, K);
				f_time = System.nanoTime();
				d_time = d_time + f_time - s_time;
			}
			d_time = d_time / loop;
			combine_result[n] = d_time;
			break;
		default:// do insertion sort
			d_time = 0;
			for (int i = 0;i < loop;i++) {
				data = init(n);
				s_time = System.nanoTime();
				Sort.doInsertionSort(data);
				f_time = System.nanoTime();
				d_time = d_time + f_time - s_time;
			}
			d_time = d_time / loop;
			insertion_result[n] = d_time;
			break;
		}
		
	}

	private static void check(int range) { // n belongs to [1,range]
		for (int n = 1;n <= range;n++) {
			int tag, loop = CHECK_TOTAL;
			// check insertion sort
			tag = 0;
			run(tag, n, loop);
			// check merge sort
			tag = 1;
			run(tag, n, loop);
			// check combine sort
			tag = 2;
			run(tag, n, loop);
		}
	}
	
	public static void checkK(int range) {
		int loop = CHECK_TOTAL;
		for (int k = 1;k < range;k++) {
			String[] data = init(range);
			long s_time, f_time, d_time = 0;
			for (int i = 0;i < loop;i++) {
				s_time = System.nanoTime();
				Sort.doMergeInsert(data, k);
				f_time = System.nanoTime();
				d_time += (f_time - s_time);
			}
			d_time = d_time / loop;
			time_of_diff_k[k] = d_time;
		}
	}

	public static int print_tab_k(int range) {
		long min = Long.MAX_VALUE;
		int min_of_k = -1;
		System.out.println("K\t\t\ttime\n");
		for (int k = 1;k < range;k++) {
			if (time_of_diff_k[k] < min) {
				min = time_of_diff_k[k];
				min_of_k = k;
			}
//			System.out.println(k + "\t\t\t" + time_of_diff_k[k] + "\n");
		}
		System.out.println("When k = " + min_of_k + ", min time is " + min);
		return min_of_k;
	}

	private static void print() {
		System.out.println("Result:\nn\tInsertion\t\tMerge\t\t\tCombine\t\t\t\n");
		for (int i = 2;i <= MAX_RANGE;i++) { // TODO when i == 1 time is longer
			System.out.println(i+"\t"+insertion_result[i]+"\t\t\t"+merge_result[i]+"\t\t\t"+combine_result[i]);
		}
	}
	
	private static void print_ms() {
		System.out.println("Result:\nn\tInsertion\t\tMerge\t\t\tCombine\t\t\t\n");
		for (int i = 2;i <= MAX_RANGE;i++) { // TODO when i == 1 time is longer
			System.out.println(i+"\t"+insertion_result[i] / 1000+"\t\t\t"+merge_result[i] / 1000+"\t\t\t"+combine_result[i] / 1000);
		}
	}

	private static void print_to_doc() {
		String filename = "./result.bin";
		int start = 2;
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));
			out.writeInt(start);
			out.writeInt(MAX_RANGE);
			for (int i = start;i <= MAX_RANGE;i++)
				out.writeLong(insertion_result[i]);
			for (int i = start;i <= MAX_RANGE;i++)
				out.writeLong(merge_result[i]);
			for (int i = start;i <= MAX_RANGE;i++)
				out.writeLong(combine_result[i]);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void doCheck(byte b) {
		check(MAX_RANGE);
		if (b==0) 
			print();
		else 
			print_to_doc();
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("type 0 to print on screen");
		byte b = input.nextByte();
		input.close();
		int average_k = 0;
		for (int i = 0;i < 10;i++) {
			checkK(MAX_RANGE);
			average_k += print_tab_k(MAX_RANGE);
		}
		average_k /= CHECK_TOTAL;
		System.out.println("average of k is " + average_k);
		System.out.println("\n\nNow check two algorithm separately...");
		check(MAX_RANGE);
		if (b==0) 
			print_ms();
		else 
			print_to_doc();
		
	}

}
