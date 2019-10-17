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
        System.out.println("Random: "+X);
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
            time.put(i, Arrays.asList(0,0, 0, 0)); //State (unstart=0,ready=1,blocked=2,running=3,terminated=4), state time, I/O time, Waiting time
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
        int p=1;
        HashMap<Integer,Integer> in =get_in(input);
        HashMap<Integer,List> time =get_time(input);
        List<List<Integer>> runblock=new ArrayList<>();
        List<Integer> runtime=new ArrayList<>();
        for(int i=0;i<input.size();i++){
            List<Integer> l=new ArrayList<>();
            l.add(0);
            l.add(0);
            runblock.add(l);
            runtime.add((int)input.get(i).get(2));
        }
        Deque<Integer> q=new LinkedList<>();
        List<Integer> finish=new ArrayList<>();
        int cpuBurst;
        int ioBurst;
        int cpuSum=0;
        for(int i=0;i<in.size();i++){
            cpuSum+=in.get(i);
            finish.add(0);
        }
        int cpuTime=cpuSum;
        int blockTime=0;
        int prev;
        int n=input.size();
        q.add(0);
        if(!detail){System.out.print("Before cycle   0:");
            for (int k=0;k<input.size();k++){
                System.out.print("  "+state.get(0)+"  "+0);
            }
            System.out.println(".");}
        int curr=-1;
        while (true) {
            prev=curr;
            curr = q.poll();
            cpuBurst = randomOS((int) input.get(curr).get(1));
            //System.out.println(cpuBurst);
            ioBurst = cpuBurst * (int) input.get(curr).get(3);
            //System.out.println("runblock:   "+cpuBurst+"    "+ioBurst);
            if (cpuBurst > cpuSum) cpuBurst = cpuSum;
            runblock.get(curr).set(0, cpuBurst);
            runblock.get(curr).set(1, ioBurst);
            //System.out.println("runblock curr:   "+runblock.get(curr).get(0)+"  "+runblock.get(curr).get(1));
            int cycle = cpuBurst;
            int z = 0;

            while (z < cycle) {

                if (z == 0) {
                    time.get(curr).set(0, 3);
                    time.get(curr).set(1, cpuBurst);
                    if (prev != -1 && prev != curr && (int)time.get(prev).get(0)==3) {
//                        time.get(prev).set(0, 2);
//                        time.get(prev).set(1, runblock.get(prev).get(1));
                        //System.out.println("runblock prev:   "+runblock.get(prev).get(0)+"  "+runblock.get(prev).get(1));
                    }
                    //System.out.println("curr prev:   "+curr+"    "+prev);
                }
                if (prev == curr && (int) time.get(curr).get(0) == 3 && (int) time.get(curr).get(1) == 0) {
                    time.get(curr).set(0, 2);
                    time.get(curr).set(1, runblock.get(curr).get(1));
                }

                for (int j = 0; j < n; j++) {
                    if (j != curr && j != prev) {
                        if ((int) input.get(j).get(0) < p && (int) time.get(j).get(0) == 0 || (int) input.get(j).get(0) < p && (int) time.get(j).get(0) == 2 && (int) time.get(j).get(1) == 0) {
                            time.get(j).set(0, 1);
                            q.add(j);
                        }
                    }
                }
                if (!detail) {
                    System.out.print("Before cycle   " + p + ":");
                    for (int k = 0; k < n; k++) {
                        System.out.print("  " + state.get((int) time.get(k).get(0)) + "      " + time.get(k).get(1));
                    }
                    System.out.println(".");
                }
                int block=0;
                int readys=0;
                boolean allBlocked=false;
                int blocks=0;
                for (int j = 0; j < n; j++) {
                    int state = (int) time.get(j).get(0);
                    int num = (int) time.get(j).get(1);
                    if (j != curr && j != prev) {
                        //if(state==0 && p==(int)input.get(j).get(0)) time.get(j).set(0,1);
                    }

                    if (num > 0) {
                        time.get(j).set(1, num - 1);
                    }
                    //System.out.println(p+": "+state+"   "+(int)time.get(j).get(1));
                    if (state == 3) {
                        cpuSum -= 1;
                        runtime.set(j,runtime.get(j)-1);
                        if(runtime.get(j)==0){
                            time.get(j).set(0,4);
                            time.get(j).set(1,0);
                            z+=999;
                            finish.set(j,p);}

                        else if(num-1==0){
                            blocks+=1;
                            time.get(j).set(0, 2);
                            time.get(j).set(1, runblock.get(j).get(1));
                        }

                    }
                    if (state == 2) {
                        block=1;
                        blocks+=1;
                        time.get(j).set(2,(int)time.get(j).get(2)+1); //TODO fix cycle 11
                        if((int)time.get(j).get(1)==0) {
                            time.get(j).set(0,1);
                            readys+=1;
                            q.add(j);
                        }
                    }
                    if (state == 1) {
                        time.get(j).set(3,(int)time.get(j).get(3)+1);}
                    if(state==0) blocks+=1;
                }
                if(blocks==n && readys==0) allBlocked=true;

                blockTime+=block;
                System.out.println(blocks+" "+readys);
                p += 1;
                z++;

                if(blocks==n && readys==1){
                    for(int k=0;k<n;k++){
                        if((int)time.get(k).get(0)==1){

                            q.remove(k);
                            q.addFirst(k);
                            break;
                        }
                    }
                }

                //System.out.println("curr:   "+z+"    prev:   "+cycle);

                if (cpuSum <= 0) break;

                if (z == cycle) {

//                    for (int i = 0; i < n; i++) {
//                        curr += 1;
//
//                        if (curr >= n) curr = 0;
//                        //if (prev == curr && (int) time.get(curr).get(0) == 3) cycle += 1;
//                        if ((int) input.get(curr).get(0) < p && (int) time.get(curr).get(1) == 0 && (int)time.get(curr).get(0)==1) {
//                            q.add(curr);
//                            break;
//                        }//change curr
                        if(allBlocked) cycle+=1;

                }


            }
                if (cpuSum <= 0) break;
                //if(p>200) break;

        }
        System.out.println();
        int turnaroundTime=0;
        int waitTime=0;
        for(int i =0;i<n;i++){
            System.out.println("Process "+i+":");
            System.out.println("(A,B,C,M) = ("+input.get(i).get(0)+","+input.get(i).get(1)+","+input.get(i).get(2)+","+input.get(i).get(3)+")");
            System.out.println("Finishing time: "+finish.get(i));
            System.out.println("Turnaround time: "+(finish.get(i)-(int)input.get(i).get(0)));
            turnaroundTime+=finish.get(i)-(int)input.get(i).get(0);
            System.out.println("I/O time: "+(int)time.get(i).get(2));
            System.out.println("Waiting time: "+(int)time.get(i).get(3));
            waitTime+=(int)time.get(i).get(3);
            System.out.println();

            }
        p-=1;
        System.out.println("Summary Data:");
        System.out.println("Finishing time: "+p);
        System.out.println("CPU Utilization: "+(float)cpuTime/p);
        System.out.println("I/O Utilization: "+(float)blockTime/p);
        System.out.println("Throughput: "+(float)n/p*100+" processes per hundred cycles");
        System.out.println("Average turnaround time: "+(float)turnaroundTime/n);
        System.out.println("Average turnaround time: "+(float)waitTime/n);
        System.out.println();
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

        String inputFileName = "input5";

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
