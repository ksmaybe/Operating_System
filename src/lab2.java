import java.io.*;
import java.util.*;

public class lab2 {
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

    private static int randomOS(int U) {
        String X = random.nextLine();
        return 1 + (Integer.parseInt(X) % U);
    }
    private static void printIntro(List<List> original, List<List> input) {
        System.out.print("The original input was: ");
        System.out.print(original.size());
        for (List<Integer> l1 : original) {
            System.out.print(" (");
            System.out.print(l1.get(0));
            System.out.print(" ");
            System.out.print(l1.get(1));
            System.out.print(" ");
            System.out.print(l1.get(2));
            System.out.print(" ");
            System.out.print(l1.get(3));
            System.out.print(")");
        }
        System.out.println();
        System.out.print("The (sorted) input was: ");
        System.out.print(original.size());
        for (List<Integer> l : input) {
            System.out.print(" (");
            System.out.print(l.get(0));
            System.out.print(" ");
            System.out.print(l.get(1));
            System.out.print(" ");
            System.out.print(l.get(2));
            System.out.print(" ");
            System.out.print(l.get(3));
            System.out.print(")");
        }
    }
    static List<String> state=Arrays.asList("unstarted","ready","blocked","running","terminated");

    private static HashMap<Integer,Integer> get_in(List<List> input){
        HashMap<Integer,Integer> in = new HashMap<Integer,Integer>();
        for(int i=0;i<input.size();i++){
            in.put(i,(int)input.get(i).get(2));
        }
        return in;
    }
    private static HashMap<Integer,List> get_time(List<List> input){
        HashMap<Integer,List> time=new HashMap<Integer,List>();
        for(int i=0;i<input.size();i++) {
            time.put(i, Arrays.asList(0,0,0, 0, 0)); //State (unstart=0,ready=1,blocked=2,running=3), state time,Turnaround time, I/O time, Waiting time
        }
        return time;
    }
    private static void fcfs(List<List> original, List<List> input) throws Exception {
        random= new Scanner(randomFile);
        printIntro(original, input);
        System.out.println();
        System.out.println("The scheduling algorithm used was First Come First Served");
        System.out.println();
        int count=original.size();
        int p=0;
        HashMap<Integer,Integer> in =get_in(input);
        HashMap<Integer,List> time =get_time(input);
        Queue<Integer> q=new LinkedList<>();
        int B;
        q.add(0);
        while (true){
            if(!detail){System.out.print("Before cycle   "+p+":");
            for (int i=0;i<input.size();i++){
                System.out.print("  "+state.get((int)time.get(i).get(0))+" "+(int)time.get(i).get(1));
            }

            System.out.println(".");}
            int curr=q.poll();
            B=randomOS((int)input.get(curr).get(1));
            if(count==0)break;
            if (p==9)break;
            p+=1;
        }
        for(int i =0;i<original.size();i++){
            System.out.println("Process "+i+":");
            B = (int) input.get(i).get(1);

            }
        }




    private static void rr(List<List> original, List<List> input) throws Exception {


        String input_line = "";

        int a = 1234;

    }

    private static void sjf(List<List> original, List<List> input) throws Exception {

    }
    private static void hprn(List<List> original, List<List> input) throws Exception {


    }


    public static void main(String[] args) throws Exception {

        String inputFileName = "input3";

        File inputFile = new File(inputFileName);
        Scanner in = new Scanner(inputFile);
        List<List> original = new ArrayList<List>();
        List<List> input = new ArrayList<List>();
        int N = in.nextInt();

        for (int i = 0; i < N; i++) {
            int A = in.nextInt();
            int B = in.nextInt();
            int C = in.nextInt();
            int M = in.nextInt();
            List<Integer> l = new ArrayList<Integer>();
            l.add(A);
            l.add(B);
            l.add(C);
            l.add(M);
            original.add(l);
            if (A == 0 && input.size() > 0) {
                Boolean yes = false;
                for (int j = 0; j < input.size(); j++) {
                    int oo = (int) input.get(j).get(0);
                    if (A < oo) {
                        input.add(oo - 1, l);
                        yes = true;
                        break;
                    }
                }
                if (!yes) input.add(l);
            } else input.add(l);
        }
        for (List<Integer> l1 : input) {
            for (Integer inte : l1) {
                System.out.print(inte);
            }
            System.out.println();
        }
        fcfs(original,input);

    }
}
