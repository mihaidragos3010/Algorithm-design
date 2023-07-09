/*
 * Acest schelet citește datele de intrare și scrie răspunsul generat de voi,
 * astfel că e suficient să completați cele două metode.
 *
 * Scheletul este doar un punct de plecare, îl puteți modifica oricum doriți.
 *
 * Dacă păstrați scheletul, nu uitați să redenumiți clasa și fișierul.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.StringTokenizer;

public class Nostory {
    public static void main(final String[] args) throws IOException {
        var scanner = new MyScanner(new FileReader("nostory.in"));

        var task = scanner.nextInt();
        var n = scanner.nextInt();
        var moves = task == 2 ? scanner.nextInt() : 0;

        var a = new int[n];
        for (var i = 0; i < n; i += 1) {
            a[i] = scanner.nextInt();
        }

        var b = new int[n];
        for (var i = 0; i < n; i += 1) {
            b[i] = scanner.nextInt();
        }

        try (var printer = new PrintStream("nostory.out")) {
            if (task == 1) {
                printer.println(solveTask1(a, b));
            } else {
                printer.println(solveTask2(a, b, moves));
            }
        }
    }

    static long mergesort(int arr[], int left, int right)
    {
        long sum = 0L;
        if (left >= right)
            return arr[left];

        // Find the middle point
        int mid = left + (right - left) / 2;

        // Sort first and second halves
        sum += mergesort(arr, left, mid);
        sum += mergesort(arr, mid + 1, right);

        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[left + i];

        for (int j = 0; j < n2; ++j)
            R[j] = arr[mid + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] > R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
        return sum;
    }

    private static long solveTask1(int[] a, int[] b) {

//        Sort both arrays in descending order with mergesort
        mergesort(a,0,a.length-1);
        mergesort(b,0,b.length-1);

        int i=0,j=0;
        long sum = 0L;

//        'i' index of first array (a) and 'j' index of second array (b)
//        I checked the biggest number of both arrays and increased 'sum' only with first 'n' of these numbers
        while(i+j<a.length){
            if(a[i]>=b[j]){
                sum += a[i];
                i++;
            }else{
                sum += b[j];
                j++;
            }
        }
        return sum;
    }

    private static long solveTask2(int[] a, int[] b, int moves) {

//        Arrays which contain either the biggest or the lowest numbers from both arrays
        int[] maxValue = new int[a.length];
        int[] minValue = new int[a.length];

        for(int index = 0; index < a.length; index++){
            if(a[index] > b[index]){
                maxValue[index] = a[index];
                minValue[index] = b[index];
            }else{
                maxValue[index] = b[index];
                minValue[index] = a[index];
            }
        }

//        Sort the arrays in descending order with mergesort
        mergesort(maxValue,0,maxValue.length-1);
        mergesort(minValue,0,minValue.length-1);

//        Switch first 'k' numbers of minArray with 'k' numbers at the end of maxArray, only it was necessary
        int aux;
        long sum = 0L;
        for(int start = 0, end = a.length-1;start<moves;start++,end--) {
            if (maxValue[end] < minValue[start]) {
                aux = maxValue[end];
                maxValue[end] = minValue[start];
                minValue[start] = aux;
            }
        }

//        Add all elements of maxArray of 'sum'
        for(int i=0;i<maxValue.length;i++)
            sum += maxValue[i];

        return sum;

    }

    /**
     * A class for buffering read operations, inspired from here:
     * https://pastebin.com/XGUjEyMN.
     */
    private static class MyScanner {
        private BufferedReader br;
        private StringTokenizer st;

        public MyScanner(Reader reader) {
            br = new BufferedReader(reader);
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}

