import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Badgpt {

    static HashMap<Integer,Long> fibonacciTable = new HashMap<Integer,Long>();
    static final int  mod = 1000000007;
    static Long fibonacci(Integer nr){

//        Check if I have number in table
        if(fibonacciTable.containsKey(nr)) {
            return fibonacciTable.get(nr);
        }

//        Check if I have 'nr-1' in table
        if(!fibonacciTable.containsKey(nr-1))
            fibonacci(nr-1);

//        Check if I have 'nr-1' in table
        if(!fibonacciTable.containsKey(nr-2))
            fibonacci(nr-2);

//        Add element 'nr' in table and return it
        fibonacciTable.put(nr,(fibonacciTable.get(nr-1) + fibonacciTable.get(nr-2))% mod);
        return fibonacciTable.get(nr);
    }
    static long findBadGpt(ArrayList<Integer> listOfNumbers){

        long rez = 1;
        for(Integer elem : listOfNumbers){
            rez = (rez * fibonacci(elem)) % mod;
        }

        return rez;
    }

    public static void main(String[] args) {
        try {
            Scanner fileTests = new Scanner(new File("badgpt.in"));

//            Read variable
            String[] str = fileTests.nextLine().split("[nu-]");
            ArrayList<Integer> listOfNumbers = new ArrayList<>();
            for(int i=1;i< str.length;i++)
                listOfNumbers.add(Integer.valueOf(str[i].split("[a-z]")[0]));

//            Set the first two elements of hashTable
            fibonacciTable.put(0,1L);
            fibonacciTable.put(1,1L);

//            Call function
            Long rez = findBadGpt(listOfNumbers);

            try {
                FileWriter fw = new FileWriter("badgpt.out");
                fw.write(Long.toString(rez));
                fw.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            fileTests.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
