import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;


public class Analysis {
	final static int MAX_RANGE = 100000;
	private static long[] insertion_result = new long[MAX_RANGE], merge_result = new long[MAX_RANGE];
	
	public static void readData(String filename) {
		if (filename == null || filename == "")
			filename = "./result.bin";
		try {
			DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
			int start = in.readInt(), end = in.readInt();
			for (int i = start;i <= end;i++)
				insertion_result[i] = in.readLong();
			for (int i = start;i <= end;i++)
				merge_result[i] = in.readLong();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int countInversion(int[] arr, int p, int q, int r) {
		int k = 0;
		for (int i = p;i < q;i++)
			for (int j = q;j < r;j++)
				if (arr[i]>arr[j])
					k++;
		return k;
	}
	
	public static int count(int[] arr, int s, int e) {
		int l, r, t = 0;
		if (s < e-1) {
			l = count(arr, s, (s+e)/2);
			r = count(arr, (s+e)/2, e);
			t = countInversion(arr, s, (s+e)/2, e) + l + r;
		}
		
		return t;
	}

	public static void main(String[] args) {
		int[] data = {5,4,1,2,3};
		System.out.println(count(data, 0, 5));
	}

}
