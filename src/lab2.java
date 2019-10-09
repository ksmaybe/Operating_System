import java.io.*;
import java.util.*;

public class lab2 {
    private static float randomOS(Scanner input,int U){
        String X=input.nextLine();
        return 1+(Integer.parseInt(X)%U);
    }
    public static void main(String[] args) throws Exception {

        String randomFile = "random-numbers.txt";
        File file= new File(randomFile);
        Scanner input = new Scanner(file);

        System.out.println(randomOS(input,5));
        System.out.println(randomOS(input,5));
        System.out.println(randomOS(input,5));


    }
}
