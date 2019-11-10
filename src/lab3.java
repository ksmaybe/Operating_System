import java.io.*;
import java.util.*;

public class lab3 {
    static String randomFileName = "random-numbers.txt";
    static File randomFile = new File(randomFileName);
    static Scanner random;
    static boolean detail=false;

    static {
        try {
            random = new Scanner(randomFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void fifo(List resources,List input){

    }
    public static void banker(List resources,List input){

    }
    public static void main(String[] args) throws Exception {
        //System.out.println(args.length);
        String inputFileName;
        //System.out.println(args[0]);
//        if(args.length==1) inputFileName = args[0];
//        else{
//            //System.out.println(args[1].compareTo("--verbose"));
//            inputFileName=args[1];
//
//            if(args[1].compareTo("--verbose")== 60) { //System.out.println("true");
//                detail=true;}
//        }
        inputFileName="input-01";
        File inputFile = new File(inputFileName);
        Scanner in = new Scanner(inputFile);
        List<Integer> resources = new ArrayList<Integer>();
        List<List> input = new ArrayList<List>();
        int n=in.nextInt();
        int r=in.nextInt();
        int a=in.nextInt();
        for(int i=0;i<r;i++){
            resources.add(a);
        }
        //1-initiate, 2-request,  3-release, 4- terminate

//        for (List<Integer> l1 : input) {
//            for (Integer inte : l1) {
//                System.out.print(inte);
//            }
//            System.out.println();
//        }


    }
}
