import java.io.*;
import java.util.*;

public class lab2 {
    private static float randomOS(Scanner input,int U){
        String X=input.nextLine();
        return 1+(Integer.parseInt(X)%U);
    }
    private static float fcfs(int A,int B, int C, int M){
        return 0;
    }
    private static float rr(int A,int B, int C, int M){
        return 0;
    }
    private static float sjf(int A,int B, int C, int M){
        return 0;
    }
    private static float hprn(int A,int B, int C, int M){
        return 0;
    }

    public static void main(String[] args) throws Exception {

        String randomFileName = "random-numbers.txt";
        File randomFile= new File(randomFileName);
        Scanner random = new Scanner(randomFile);
        String inputFileName= "input1";
        File inputFile=new File(inputFileName);
        Scanner input = new Scanner(inputFile);


        int N = input.nextInt();
        for(int i=0;i<N;i++){
            System.out.println(input.nextInt());
            System.out.println(input.nextInt());
            System.out.println(input.nextInt());
            System.out.println(input.nextInt());
        }


    }
}
