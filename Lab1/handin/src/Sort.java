
public class Sort {
	final static int MAX_LEN = 1024;

	public static void doNorecursionMergesort(String[] data) {
		norecursion_mergesort(data);
	}
	
	public static void doMergeInsert(String[] data, int k) {
		mergeinsertsort(data, 0, data.length, k);
	}

	public static void doMergesort(String[] data) {
		mergesort(data, 0, data.length);
	}
	
	public static void doInsertionSort(String[] data) {
		insertionsort(data, 0, data.length);
	}
	
	// insertion sort
	private static void insertionsort(String[] arr, int s, int e) {
		int len = e - s;
		for (int i = s;i < e;i++) {
			String key = arr[i];
			int j = i - 1;
			while (j >= s && arr[j].compareTo(key) > 0) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = key;
		}
	}

	// merge sort
	private static void merge(String[] arr, int p, int q, int r) {
		int len = r - p, mid = q-p;
		String[] tmp = new String[len];
		// copy array
		for (int i = p;i < r;i++) {
			tmp[i-p] = arr[i];
		}
		// merge
		int i = 0, j = mid, k = p;
		while (i < mid && j < len) {
			if (tmp[i].compareTo(tmp[j])<0) {
				arr[k] = tmp[i];
				i++;
			} else {
				arr[k] = tmp[j];
				j++;
			}
			k++;
		}
		if (i < mid) 
			for (int m = i;m < mid;m++) {
				arr[k] = tmp[m];
				k++;
			}
		else if (j < len)
			for (int m = j;m < len;m++) {
				arr[k] = tmp[m];
				k++;
			}
	}
	
	private static void mergesort(String[] arr, int s, int e) { // s : start of arr ; e : end of arr + 1
		int len = e - s;
		if (len > 1) {
			int mid = (s + e) / 2;
			mergesort(arr, s, mid);
			mergesort(arr, mid, e);
			merge(arr, s, mid, e);
		}
	}
	
	// merge sort no recursion
	private static void norecursion_mergesort(String[] arr) {
		int n = arr.length, k = 2;
		while (k <= n) {
			for (int i = 0;i < n;i++) {
				if ((i+1) % k == 0) {
					merge(arr, i+1-k, i+1-k/2, i+1);
					if (n-(i+1) < k) {
						merge(arr, (i+1)-k, i+1, n);
						break;
					}
				}
				
			}
			k *= 2;
		}
	}
	
	// TODO complete merge
	public static void lessspace_merge(int[] arr, int p, int q, int r) {
		int key = arr[p];
		int i = p, j = q;
		while (i < q) {
			if (arr[j] < key) {
				arr[i] = arr[j];
				j++;
			} else {
				
			}
		}
	}
	
	// combine sort
	private static void mergeinsertsort(String[] arr, int s, int e, int k) {
		int len = e - s;
		if (len > k) {
			int mid = (s + e) / 2;
			mergeinsertsort(arr, s, mid, k);
			mergeinsertsort(arr, mid, e, k);
			merge(arr, s, mid, e);
		} else {
			insertionsort(arr, s, e);
		}
	}
	
	private static void print(String[] arr) {
		for (int i = 0;i < arr.length-1;i++) {
			System.out.print(arr[i] + ",");
		}
		System.out.println(arr[arr.length-1]);
	}
	
//	public static void main(String[] args) {
//		final String[] arr = {"4","3","2","5","1","7","9","8","6"};
//		norecursion_mergesort(arr);
//		print(arr);
//	}

}

