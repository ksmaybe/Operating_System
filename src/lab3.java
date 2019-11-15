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
    public static void fifo(List resources,List<List<List>> input){
        List<Integer> resReturn=new ArrayList<Integer>();
        for(int i=0;i<resources.size();i++){
            resReturn.add(0);
        }
        List<Integer> need=new ArrayList<Integer>();
        List<Integer> finish=new ArrayList<Integer>();
        List<Integer> wait=new ArrayList<Integer>();
        List<List<Integer>> claimed= new ArrayList<List<Integer>>();
        int unfinished=input.size();

        for(int j=0;j<input.size();j++){
            //need.add((int)input.get(j).get(0).get(3));
            finish.add(0);
            wait.add(0);
            List<Integer> res=new ArrayList<Integer>();
            for (int k=0;k<resources.size();k++){
                res.add(0);
            }
            claimed.add(res);
        }
        int i=1;
        int runtime=0;
        while(i<input.get(0).size()){
            int block=0;
            List<Integer> blocked = new ArrayList<Integer>();
            boolean returner=false;
            for(int j=0;j<input.size();j++){
                blocked.add(0);
                int p=(int)input.get(j).get(i).get(0);
                int t=(int)input.get(j).get(i).get(1);
                int r=(int)input.get(j).get(i).get(2);
                int n=(int)input.get(j).get(i).get(3);
                if(p==2 && finish.get(j)==0) {//1-initiate, 2-request,  3-release, 4- terminate
                    if((int)resources.get(r-1)>=n){
                        resources.set(r-1,(int)resources.get(r-1)-n);

                        claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)+n);
                    }
                    else {
                        block+=1;
                        blocked.set(t-1,1);
                    }
                }else if(p==3&& finish.get(j)==0){
                    resReturn.set(r-1,n);
                    returner=true;
                    claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)-n);
                }else if(p==4&& finish.get(j)==0){
                    finish.set(j,runtime+1);
                    unfinished-=1;
                }

            }
            if(returner){
                for(int j=0;j<resources.size();j++){
                    resources.set(j,(int)resReturn.get(j)+(int)resources.get(j));
                    resReturn.set(j,0);

            }}
            System.out.println("block");
            //deadlock abort
            if(block==unfinished && unfinished>=1){
                boolean blocking=true;
                while(blocking){
                    System.out.println("blocking");
                    boolean b=false;
                    for(int j=0;j<input.size();j++){
                        if(blocked.get(j)==1){
                            finish.set(j,-1);
                            for(int k=0;k<resources.size();k++){
                                resources.set(k,(int)claimed.get(j).get(k)+(int)resources.get(k));
                                b=true;
                                break;
                        }}
                        if(b) break;
                    };//abort lowest number deadlocked task
                    blocked=new ArrayList<Integer>();
                    System.out.println(Arrays.toString(finish.toArray()));
                    for(int j=0;j<input.size();j++){
                        blocked.add(0);
                        int p=(int)input.get(j).get(i).get(0);
                        int t=(int)input.get(j).get(i).get(1);
                        int r=(int)input.get(j).get(i).get(2);
                        int n=(int)input.get(j).get(i).get(3);
                        if(p==2 && finish.get(j)==0) {//1-initiate, 2-request,  3-release, 4- terminate
                            if ((int) resources.get(r - 1) >= n) {
                                blocking=false;
                                resources.set(r - 1, (int) resources.get(r - 1) - n);
                                claimed.get(t - 1).set(r - 1, claimed.get(t - 1).get(r - 1) + n);
                            } else {
                                block += 1;
                                blocked.set(t - 1, 1);
                            }
                        }}
                    runtime+=1;
            }}
            System.out.println("unblock");
            i++;
            runtime+=1;}
    for(int z=0;z<input.size();z++){
        if(finish.get(z)>0) System.out.println(finish.get(z));
        else System.out.println(String.format(finish.get(z)+"   abort"));
    }
    }
    public static void djkstra(List resources, List<List> instruction){

    }
    public static void banker(List resources,List<List<List>> input){
        List<Integer> resReturn=new ArrayList<Integer>();
        for(int i=0;i<resources.size();i++){
            resReturn.add(0);
        }
        List<Integer> need=new ArrayList<Integer>();
        List<Integer> finish=new ArrayList<Integer>();
        List<Integer> wait=new ArrayList<Integer>();
        List<List<Integer>> claimed= new ArrayList<List<Integer>>();
        int unfinished=input.size();

        for(int j=0;j<input.size();j++){
            need.add((int)input.get(j).get(0).get(3));
            finish.add(0);
            wait.add(0);
            List<Integer> res=new ArrayList<Integer>();
            for (int k=0;k<resources.size();k++){
                res.add(0);
            }
            claimed.add(res);
        }
        int i=1;
        int runtime=0;
        while(i<input.get(0).size()){
            int block=0;
            List<Integer> blocked = new ArrayList<Integer>();
            boolean returner=false;
            for(int j=0;j<input.size();j++){
                blocked.add(0);
                int p=(int)input.get(j).get(i).get(0);
                int t=(int)input.get(j).get(i).get(1);
                int r=(int)input.get(j).get(i).get(2);
                int n=(int)input.get(j).get(i).get(3);
                if(p==2 && finish.get(j)==0) {//1-initiate, 2-request,  3-release, 4- terminate
                    if((int)resources.get(r-1)>=n){
                        resources.set(r-1,(int)resources.get(r-1)-n);

                        claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)+n);
                    }
                    else {
                        block+=1;
                        blocked.set(t-1,1);
                    }
                }else if(p==3&& finish.get(j)==0){
                    resReturn.set(r-1,n);
                    returner=true;
                    claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)-n);
                }else if(p==4&& finish.get(j)==0){
                    finish.set(j,runtime+1);
                    unfinished-=1;
                }

            }
            if(returner){
                for(int j=0;j<resources.size();j++){
                    resources.set(j,(int)resReturn.get(j)+(int)resources.get(j));
                    resReturn.set(j,0);

                }}
            System.out.println("block");
            //deadlock abort
            if(block==unfinished && unfinished>=1){
                boolean blocking=true;
                while(blocking){
                    System.out.println("blocking");
                    boolean b=false;
                    for(int j=0;j<input.size();j++){
                        if(blocked.get(j)==1){
                            finish.set(j,-1);
                            for(int k=0;k<resources.size();k++){
                                resources.set(k,(int)claimed.get(j).get(k)+(int)resources.get(k));
                                b=true;
                                break;
                            }}
                        if(b) break;
                    };//abort lowest number deadlocked task
                    blocked=new ArrayList<Integer>();
                    System.out.println(Arrays.toString(finish.toArray()));
                    for(int j=0;j<input.size();j++){
                        blocked.add(0);
                        int p=(int)input.get(j).get(i).get(0);
                        int t=(int)input.get(j).get(i).get(1);
                        int r=(int)input.get(j).get(i).get(2);
                        int n=(int)input.get(j).get(i).get(3);
                        if(p==2 && finish.get(j)==0) {//1-initiate, 2-request,  3-release, 4- terminate
                            if ((int) resources.get(r - 1) >= n) {
                                blocking=false;
                                resources.set(r - 1, (int) resources.get(r - 1) - n);
                                claimed.get(t - 1).set(r - 1, claimed.get(t - 1).get(r - 1) + n);
                            } else {
                                block += 1;
                                blocked.set(t - 1, 1);
                            }
                        }}
                    runtime+=1;
                }}
            System.out.println("unblock");
            i++;
            runtime+=1;}
        for(int z=0;z<input.size();z++){
            if(finish.get(z)>0) System.out.println(finish.get(z));
            else System.out.println(String.format(finish.get(z)+"   abort"));
    }}
    public static void main(String[] args) throws Exception {
        //System.out.println(args.length);0
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
        List<List<List>> input = new ArrayList<List<List>>();
        int n=in.nextInt();
        int r=in.nextInt();
        int a=in.nextInt();
        for(int i=0;i<r;i++){
            resources.add(a);
        }
        for(int i=0;i<n;i++) {
            List<List> l=new ArrayList<List>();
            input.add(l);
        } //1-initiate, 2-request,  3-release, 4- terminate
        while(in.hasNext()){
            List<Integer> l=new ArrayList<Integer>();
            String t=in.next();
            //System.out.println(t);
            if(t.equals("initiate")){
                l.add(1);
            }else if(t.equals("request")){
                l.add(2);
            }else if(t.equals("release")){
                l.add(3);
            }else if(t.equals("terminate")){
                l.add(4);
            }
            int s=in.nextInt();
            l.add(s);
            for(int j=0;j<2;j++){
                l.add(in.nextInt());
            }
            input.get(s-1).add(l);
        }
        System.out.println("start fifo");
        fifo(resources, input);


//        for (List<Integer> l1 : input) {
//            for (Integer inte : l1) {
//                System.out.print(inte);
//            }
//            System.out.println();
//        }


    }
}
