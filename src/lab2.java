import java.io.*;
import java.util.*;

public class lab2 {
    private static float randomOS(Scanner input,int U){
        String X=input.nextLine();
        return 1+(Integer.parseInt(X)%U);
    }
    private static float fcfs (File randomFile,File inputFile) throws Exception {

        Scanner random = new Scanner(randomFile);

        Scanner input = new Scanner(inputFile);

        String input_line="";
        int N = input.nextInt();
        input_line+=N;
        for(int i=0;i<N;i++){
            int A=input.nextInt();
            int B=input.nextInt();
            int C=input.nextInt();
            int M=input.nextInt();
        }
        int a=1241;
        return 0;
    }
    private static float rr(File randomFile, File inputFile) throws Exception {


        Scanner random = new Scanner(randomFile);
        Scanner input = new Scanner(inputFile);

        String input_line="";
        int N = input.nextInt();
        input_line+=N;
        for(int i=0;i<N;i++){
            int A=input.nextInt();
            int B=input.nextInt();
            int C=input.nextInt();
            int M=input.nextInt();
        }
        int a=1234;
        return 0;
    }
    private static float sjf(File randomFile,File inputFile) throws Exception {
        String randomFileName = "random-numbers.txt";

        Scanner random = new Scanner(randomFile);
        String inputFileName= "input1";

        Scanner input = new Scanner(inputFile);

        String input_line="";
        int N = input.nextInt();
        input_line+=N;
        for(int i=0;i<N;i++){
            int A=input.nextInt();
            int B=input.nextInt();
            int C=input.nextInt();
            int M=input.nextInt();
        }
        return 0;
    }
    private static float hprn(File randomFile,File inputFile) throws Exception {

        Scanner random = new Scanner(randomFile);
        Scanner input = new Scanner(inputFile);

        String input_line="";
        int N = input.nextInt();
        input_line+=N;
        for(int i=0;i<N;i++){
            int A=input.nextInt();
            int B=input.nextInt();
            int C=input.nextInt();
            int M=input.nextInt();
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        String randomFileName = "random-numbers.txt";
        String inputFileName= "input1";
        File randomFile= new File(randomFileName);
        File inputFile=new File(inputFileName);


    }
}
