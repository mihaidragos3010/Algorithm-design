import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Semnale {

    static int sig_type, x, y;
    static final int  mod = 1000000007;

    Semnale(){}

    static long type1() {
        //TODO Compute the number of type 1 signals.
        int[][] dp = new int[x+1][y+1];

        for(int i=0;i<x+1;i++){
            for(int j=0;j<y+1;j++){

                if(i==0 && j==0){
                    dp[i][j] = 0;
                    continue;
                }

                if(j==0) {
                    dp[i][j] = 1;
                    continue;
                }

                if(i==0 && j==1) {
                    dp[i][j] = 1;
                    continue;
                }

                if(j>i+1) {
                    dp[i][j] = 0;
                    continue;
                }

                if(j==1) {
                    dp[i][j] = (dp[i-1][j]+1)%mod;
                    continue;
                }
                dp[i][j] = (dp[i-1][j]+dp[i-1][j-1])%mod;
            }
        }

        return dp[x][y];
    }

    static long type2() {
        //TODO Compute the number of type 2 signals.

        long[][] dp = new long[x+1][y+1];

        for(int i=0;i<x+1;i++){
            for(int j=0;j<y+1;j++){

                if(i==0 && j==0){
                    dp[i][j] = 0;
                    continue;
                }

                if(j==0){
                    dp[i][j] = 1;
                    continue;
                }

                if(j > (i+1)*2){
                    dp[i][j] = 0;
                    continue;
                }

                if(i==0 && (j==1 || j==2)){
                    dp[i][j] = 1;
                    continue;
                }

                if(j == 1 || j == 2 ){
                    dp[i][j] = (dp[i-1][j]%mod + dp[i][j-1]%mod)%mod;
                    continue;
                }


                dp[i][j] = (dp[i-1][j]%mod +  dp[i-1][j-1]%mod +  dp[i-1][j-2]%mod)%mod;

            }
        }

        return dp[x][y];
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("semnale.in"));

            sig_type = sc.nextInt();
            x = sc.nextInt();
            y = sc.nextInt();


            long ans;
            switch (sig_type) {
                case 1:
                    ans = Semnale.type1();
                    break;
                case 2:
                    ans = Semnale.type2();
                    break;
                default:
                    ans = -1;
                    System.out.println("wrong task number");
            }


            try {
                FileWriter fw = new FileWriter("semnale.out");
                fw.write(Long.toString(ans));
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
