import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;




class process{
    double A;
    double B;
    double C;
    int pNum;
    int numRef; //Number of references
    int refNum; //curently referencing number
    boolean done=false;
    int faults=0;
    int evicts=0; //times evicted
    int resTime =0; //times residing




    process(double A, double B, double C, int p, int ref){
        this.A=A;
        this.B=B;
        this.C=C;
        this.pNum=p;
        this.numRef=ref;
    }
}
//PLEASE NOTE PS REFERS TO PAGE SIZE
//P REFERS TO LIST OF PROCESSES

public class lab4{
    static int M;   //Machine size in words
    static int PS;  //Page size in words
    static int S;    //Size of each process
    static int J;    //Job mix, determines A,B,C
    static int N;    //Number of references for each process
    static String R; //Replacement Algo
    static int q=3; //quantum size
    static String randomFileName = "random-numbers.txt";
    static File randomFile = new File(randomFileName);
    static Scanner random;
    static int frames;
    static {
        try {
            random = new Scanner(randomFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void run(List<process>P){
        ArrayList<Integer> frameTable=new ArrayList<Integer>();
        HashMap<Integer, Integer> timeReside=new HashMap<Integer, Integer>();
        process curr;
        Stack s= new Stack();
        int unfinished=P.size();
        int remove;
        int time=1;
        int ref;
        boolean hit=false;
        int randomRemove=0;
        System.out.println("framesize   "+frames);
        while(unfinished>0){ //while unfinished processes more than one
            for(int i=0;i<P.size();i++){
                curr=P.get(i);
                if(!curr.done){
                    for(int j=0;j<q;j++){
                        if(!curr.done){
                        //System.out.println();
                        System.out.print("time: "+time+"\t");
                        ref=(curr.refNum/PS)*PS+curr.pNum-1;
                        System.out.print("ref: "+curr.refNum+'\t');
                        if(curr.numRef>0){
                            for(int k=0;k<frameTable.size();k++){
                                if(ref==frameTable.get(k)){
                                    hit=true;
                                    if(R.equals("lru")){
                                        frameTable.remove(k);
                                        frameTable.add(ref);
                                    }
                                }
                            }
                            if(!hit){
                                System.out.print("    Fault    ");
                                curr.faults+=1;
                                int tableSize=frameTable.size();

                                if(R.equals("lifo")) {
                                    if(frames==tableSize){


                                    remove=frameTable.remove(tableSize-1);

                                    System.out.print("   evicting "+remove%10 +" of "+tableSize);
                                    P.get((remove%10)).evicts+=1;
                                    P.get((remove%10)).resTime+=(time-timeReside.get(remove));}
                                    frameTable.add(ref);

                                }
                                else if (R.equals("random")){
                                    if(frames==tableSize){

                                    randomRemove=random.nextInt()%tableSize;
                                    remove=frameTable.remove(randomRemove);

                                    System.out.print("   evicting "+remove +" of "+tableSize);
                                    P.get((remove%PS)).evicts+=1;
                                    P.get((remove%PS)).resTime+=(time-timeReside.get(remove));}
                                    frameTable.add(randomRemove,ref);
                                } else if (R.equals("lru")){
                                    if(frames==tableSize){
                                    remove=frameTable.remove(0);
                                    System.out.print("    evicting "+0 +" of "+P.get(remove%10).pNum);
                                    P.get((remove%10)).evicts+=1;
                                    P.get((remove%10)).resTime+=(time-timeReside.get(remove));}
                                    frameTable.add(ref);
                                }
                                timeReside.put(ref,time);
                            }
                            int r=random.nextInt();
                            System.out.print('\n');
                            System.out.println(curr.pNum+"  uses "+r);
                            double y=(double)r/(Integer.MAX_VALUE+1d);
                            if(y<curr.A){
                                curr.refNum=(curr.refNum+1)%S;
                            }else if(y<curr.A+curr.B){
                                curr.refNum=(curr.refNum-5+S)%S;
                            }else if(y<curr.A+curr.B+curr.C){
                                curr.refNum=(curr.refNum+4)%S;
                            }else{
                                r=random.nextInt();
                                System.out.println(curr.pNum+"  uses "+r);
                                curr.refNum=r%S;
                            }
                            time+=1;
                            curr.numRef-=1;
                            hit=false;
                            if(curr.numRef<=0){
                                curr.done=true;
                                unfinished-=1;
                            }
                        }  }
                        }
                    }


            }
        }
        int faults=0;
        int evicts=0;
        int timeResidency=0;
        for(int i=0;i<P.size();i++){
            curr=P.get(i);
            faults+=curr.faults;
            evicts+=curr.evicts;
            timeResidency+=curr.resTime;
            if(P.get(i).evicts==0) System.out.println("Process "+curr.pNum+ " had "+curr.faults+" faults. \n Since there are no evictions, average residency cannot be defined");
            else System.out.println("Process "+curr.pNum+ " had "+curr.faults+" faults. \n overall average resdiency is "+(double)curr.resTime/(double)curr.evicts);
        }
        if(evicts>0)System.out.println("Total faults is "+ faults+" and overal average residency is "+ (double)timeResidency/(double)evicts);
        else System.out.println("Total faults is "+ faults+" and overal average residency is unable to be defined");
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(args.length);0
        if(args.length!=6){
            int lol=16;              //
            String gogoname = "gogo.txt";
            File goFile = new File(gogoname);
            Scanner go=new Scanner(goFile);
            for(int i=0;i<lol-1;i++)go.nextLine();
            M=go.nextInt();
            PS=go.nextInt();
            S=go.nextInt();
            J=go.nextInt();
            N=go.nextInt();
            R=go.next();
            //                                       System.out.println(curr.pNum+"  uses "+r);
            //System.out.println(args.length);
            //System.err.println("Incorrect number of command arguments. 6 Required.");
            //System.exit(0);
        }else {
            M = Integer.getInteger(args[0]);    //Machine size in words
            PS = Integer.getInteger(args[1]);    //Page size in words
            S = Integer.getInteger(args[2]);    //Size of each process
            J = Integer.getInteger(args[3]);    //Job mix, determines A,B,C
            N = Integer.getInteger(args[4]);    //Number of references for each process
            R = args[5];                     //Replacement algorithm, LIFO, RANDOM, or LRU
        }
        List<process> P=new ArrayList<process>();
        System.out.println("The machine size is "+M);
        System.out.println("The page size is "+ PS);
        System.out.println("The process size is "+S);
        System.out.println("The job mix number is "+J);
        System.out.println("The number of references per process is "+N);
        System.out.println("The replacement algorithm is "+R);
        System.out.println();
        if(J==1) P.add(new process(1,0,0,1,N));
        else if(J==2){
            for(int i=0;i<4;i++){
                P.add(new process(1,0,0,i+1,N));
            }
        }else if(J==3){
            for(int i=0;i<4;i++){
                P.add(new process(0,0,0,i+1,N));
            }
        }else if(J==4){
            P.add(new process(0.75, 0.25,0,1,N));
            P.add(new process(0.75,0,0.25,2,N));
            P.add(new process(0.75,0.125,0.125,3,N));
            P.add(new process(0.5,0.125,0.125,4,N));
        }
        frames=M/PS;
        for(int i=0;i<P.size();i++) P.get(i).refNum=(111*(i+1))%S;

        run(P);
    }


}
