import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Feribot {


    static Long findBestAdditionFerriesBinarySearch(Long[] carWeightList,Integer nrFerries,Long low,Long high){

//        'nrFerriesUsed is variable which count how many ferry I used with 'medium' weight
//        'currentFerry' is variable which count weight on current ferry used
        Long medium = (low+high)/2;
        Integer nrFerriesUsed = 0;
        Long currentFerry = 0L;

        for(Long carWeight : carWeightList){
            if(currentFerry + carWeight > medium){
                nrFerriesUsed++;
                currentFerry = carWeight;
                continue;
            }
            currentFerry += carWeight;
        }

//        exit of function
        if(low.equals(high)){
            return medium;
        }

//        recursive call which mean I used too few ferries and have to decrease 'high'
        if(nrFerriesUsed < nrFerries){
            return findBestAdditionFerriesBinarySearch(carWeightList,nrFerries,low,medium);
        }

//        recursive call which mean I used too many ferries and have to increase 'low;
        return findBestAdditionFerriesBinarySearch(carWeightList,nrFerries,medium+1,high);

    }

    static Long findBestAdditionFerries(Long[] carWeightList,Integer nrFerries) {

//        Set 'low' with the lowest number of array and 'high' with sum of every number of array
        Long low = 0L, high = 0L;
        for (Long carWeight : carWeightList) {
            if (carWeight > low)
                low = carWeight;
            high += carWeight;
        }

        return findBestAdditionFerriesBinarySearch(carWeightList, nrFerries, low, high);
    }


    public static void main(String[] args) {
        try {
            Scanner fileTests = new Scanner(new File("feribot.in"));

//            Read variables
            Integer nrCars = fileTests.nextInt();
            Integer nrFerries = fileTests.nextInt();
            fileTests.nextLine();
            Long[] carWeightList = new Long[nrCars];
            for(int index=0;index<nrCars;index++){
                carWeightList[index] = fileTests.nextLong();
            }
//            Call function
            Long rez = findBestAdditionFerries(carWeightList,nrFerries);

            try {
                FileWriter fw = new FileWriter("feribot.out");
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
