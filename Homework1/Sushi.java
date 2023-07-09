import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Sushi {
    static int n, m, x;
    static int[] prices;
    static int[][] grades;

    Sushi(){}

    static int max(int x,int y){
        if(x > y)
            return x;
        else
            return y;
    }

    static int notesOfSushi(int plateSushi){
        int notes = 0;
        for(int person = 0;person<n;person++)
            notes += grades[person][plateSushi];
        return notes;
    }
    static int task1() {
        // TODO solve task 1
//        This is a matrix which lines mean all elements could count on bill until them and columns mean maximum price could be spend that moment
        int[][] dp = new int[prices.length+1][x*n+1];
        for(int i = 0; i<= prices.length;i++){
            for(int j = 0; j <= x*n;j++){

//                First line and column of matrix will be 0
                if(i==0 || j==0){
                    dp[i][j] = 0;
                    continue;
                }

//                If price of element is greater than the maximum value 'j' I'm going to select previous case
                if(prices[i-1] > j){
                    dp[i][j] = dp[i-1][j];
                    continue;
                }

//                In the last choice I'm going to select between previous elements of matrix and element in case I wanted to put plateau on the bill
                dp[i][j] = max(dp[i-1][j],dp[i-1][j-prices[i-1]]+notesOfSushi(i-1));

            }
        }

        return dp[prices.length][n*x];
    }

    static int task2() {
        // TODO solve task 2
        int[] newPrices = new int[2*m];
        int[][] newGrades = new int[n][2*m];

//        Create a new prices array with the same elements but duplicated elements
        for(int i = 0 ;i<2*m;i++){
            if(i<m)
                newPrices[i] = prices[i];
            else
                newPrices[i] = prices[i-m];
        }

//        Create a new grades matrix with the same elements but duplicated elements
        for(int j = 0 ;j<2*m;j++){

            if(j<m) {
                for(int i =0;i<n;i++)
                    newGrades[i][j] = grades[i][j];
            }else {
                for(int i =0;i<n;i++)
                    newGrades[i][j] = grades[i][j-m];
            }

        }

        prices = newPrices;
        grades = newGrades;


        return task1();
    }

    static int task3() {
        // TODO solve task 3

        int[] newPrices = new int[2*m];
        int[][] newGrades = new int[n][2*m];

//         Create a new prices array with the same elements but duplicated elements
        for(int i = 0 ;i<2*m;i++){
            if(i<m)
                newPrices[i] = prices[i];
            else
                newPrices[i] = prices[i-m];
        }

//        Create a new grades matrix with the same elements but duplicated elements
        for(int j = 0 ;j<2*m;j++){

            if(j<m) {
                for(int i =0;i<n;i++)
                    newGrades[i][j] = grades[i][j];
            }else {
                for(int i =0;i<n;i++)
                    newGrades[i][j] = grades[i][j-m];
            }

        }

        prices = newPrices;
        grades = newGrades;

        int[][][] dp = new int[prices.length+1][x*n+1][n+1];
        for(int i = 0; i<= prices.length;i++){
            for(int j = 0; j <= x*n;j++){
                for(int k=0;k<=n;k++) {

//                    First line and column of matrix will be 0
                    if (i == 0 || j == 0 || k==0) {
                        dp[i][j][k] = 0;
                        continue;
                    }

//                    If price of element is greater than the maximum value 'j' I'm going to select previous case
                    if (prices[i - 1] > j) {
                        dp[i][j][k] = dp[i - 1][j][k];
                        continue;
                    }
//                    In the last choice I'm going to select between previous elements of matrix and element in case I wanted to put plateau on the bill
                    dp[i][j][k] = max(dp[i - 1][j][k], dp[i - 1][j - prices[i - 1]][k-1] + notesOfSushi(i - 1));
                }
            }
        }


        return dp[prices.length][n*x][n];
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("sushi.in"));

            final int task = sc.nextInt(); // task number

            n = sc.nextInt(); // number of friends
            m = sc.nextInt(); // number of sushi types
            x = sc.nextInt(); // how much each of you is willing to spend

            prices = new int[m]; // prices of each sushi type
            grades = new int[n][m]; // the grades you and your friends gave to each sushi type

            // price of each sushi
            for (int i = 0; i < m; ++i) {
                prices[i] = sc.nextInt();
            }

            // each friends rankings of sushi types
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    grades[i][j] = sc.nextInt();
                }
            }

            int ans;
            switch (task) {
                case 1:
                    ans = Sushi.task1();
                    break;
                case 2:
                    ans = Sushi.task2();
                    break;
                case 3:
                    ans = Sushi.task3();
                    break;
                default:
                    ans = -1;
                    System.out.println("wrong task number");
            }

            try {
                FileWriter fw = new FileWriter("sushi.out");
                fw.write(Integer.toString(ans) + '\n');
                fw.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
