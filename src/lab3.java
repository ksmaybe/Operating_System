import java.io.*;
import java.lang.reflect.Array;
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
        List<Deque<List>> go=new ArrayList<Deque<List>>();
        List<Integer> finish=new ArrayList<Integer>();
        List<Integer> wait=new ArrayList<Integer>();
        List<List<Integer>> claimed= new ArrayList<List<Integer>>();
        Deque<List> first=new ArrayDeque<>();
        Deque<List> claimQ=new ArrayDeque<>();
        List<Boolean> prevBlock =new ArrayList<Boolean>();
        int unfinished=input.size();
        for(int j=0;j<input.size();j++){
            //need.add((int)input.get(j).get(0).get(3));
            finish.add(0);
            wait.add(0);
            prevBlock.add(false);
            List<Integer> res=new ArrayList<Integer>();
            for (int k=0;k<resources.size();k++){
                res.add(0);
            }
            claimed.add(res);
            Deque<List> q=new ArrayDeque();
            for(int i=0;i<input.get(j).size();i++){
                q.add(input.get(j).get(i));
            }
            go.add(q);
        }
        Boolean stop=false;

        int runtime=0;
        int s=0;
        int g=0;
        while(!stop){
            System.out.println("runtime "+runtime);
            int block=0;
            List<Integer> blocked = new ArrayList<Integer>();
            boolean returner=false;
            s=first.size();
            for(int j=0;j<s;j++){
                claimQ.add(first.pop());
            }

            for(int j=0;j<input.size();j++){
                System.out.println(Arrays.toString(go.get(j).peek().toArray()));
                blocked.add(0);
                if (go.get(j).size()>0){
                int p=(int)go.get(j).peek().get(0);
                int t=(int)go.get(j).peek().get(1);
                int r=(int)go.get(j).peek().get(2);
                int n=(int)go.get(j).peek().get(3);
                if(p==2 && finish.get(j)==0) {//1-initiate, 2-request,  3-release, 4- terminate
                    if(!prevBlock.get(j)) claimQ.add(go.get(j).peek());
//                    else {List<Integer> l=new ArrayList <Integer>();
//                        go.get(j).addFirst(l);}
                }else if(p==3&& finish.get(j)==0){
                    resReturn.set(r-1,n);
                    returner=true;
                    claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)-n);
                }else if(p==4&& finish.get(j)==0){
                    finish.set(j,runtime);
                    unfinished-=1;
                }else if(p==5&& finish.get(j)==0){
                    if(r>1){
                        go.get(j).peek().set(2,r-1);
                        List<Integer> l=new ArrayList <Integer>();
                        go.get(j).addFirst(l);
                    }
                }
                }}
            System.out.println("Q:    "+Arrays.toString(claimQ.toArray()));
            System.out.println("res bef:    "+Arrays.toString(resources.toArray()));
            s=claimQ.size();
            for (int j=0;j<s;j++){
                int p=(int)claimQ.peek().get(0);
                int t=(int)claimQ.peek().get(1);
                int r=(int)claimQ.peek().get(2);
                int n=(int)claimQ.peek().get(3);
                //System.out.println(Arrays.toString(claimQ.peek().toArray()));
                if((int)resources.get(r-1)>=n){
                    resources.set(r-1,(int)resources.get(r-1)-n);
                    claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)+n);
                    prevBlock.set(t-1,false);
                    claimQ.pop();
                }
                else {
                    wait.set(t-1,wait.get(t-1)+1);
                    List<Integer> l=new ArrayList <Integer>();
                    go.get(t-1).addFirst(l);
                    block+=1;
                    blocked.set(t-1,1);
                    first.add(claimQ.pop());
                    prevBlock.set(t-1,true);
                }
            }
            System.out.println("res aft:    "+Arrays.toString(resources.toArray()));
            System.out.println("blocked:    "+Arrays.toString(blocked.toArray()));
            if(returner){
                for(int j=0;j<resources.size();j++){
                    resources.set(j,resReturn.get(j)+(int)resources.get(j));
                    resReturn.set(j,0);
            }}

            //deadlock abort
            System.out.println("first:  "+Arrays.toString(first.toArray()));
            if(block==unfinished && unfinished>=1){
                boolean blocking=true;
                int ac=0;

                while(blocking && ac<5){
                    System.out.println("blocking");
                    int breaker=-1;
                    boolean b=false;
                    System.out.println("first blocked:  "+Arrays.toString(blocked.toArray()));
                    for(int j=0;j<input.size();j++){
                        if(blocked.get(j)==1){
                            breaker=j+1;
                            finish.set(j,-1);
                            go.get(j).pop();
                            for(int k=0;k<resources.size();k++){
                                resources.set(k,claimed.get(j).get(k)+(int)resources.get(k));
                                b=true;
                                break;
                        }}
                        if(b) break;
                    };//abort lowest number deadlocked task
                    blocked=new ArrayList<Integer>();

                    for(int j=0;j<input.size();j++){
                        blocked.add(0);
                    }
                    s=first.size();
                    for(int j=0;j<s;j++){
                        System.out.println("run:  "+Arrays.toString(first.peek().toArray()));
                        System.out.println("resources:  "+Arrays.toString(resources.toArray()));
                        if((int)first.peek().get(1)==breaker){
                            first.pop();
                            System.out.println("ow");
                        }
                        else{
                        int p=(int)first.peek().get(0);
                        int t=(int)first.peek().get(1);
                        int r=(int)first.peek().get(2);
                        int n=(int)first.peek().get(3);
                        if(p==2 && finish.get(t-1)==0) {//1-initiate, 2-request,  3-release, 4- terminate
                            if ((int) resources.get(r - 1) >= n) {
                                go.get(t-1).pop();
                                blocking=false;
                                resources.set(r - 1, (int) resources.get(r - 1) - n);
                                claimed.get(t - 1).set(r - 1, claimed.get(t - 1).get(r - 1) + n);
                                first.pop();
                            } else {
                                System.out.println("haha");
                                blocked.set(t-1, 1);
                                first.add(first.pop());
                            }
                          }
                        }}
                          System.out.println("blocked:    "+Arrays.toString(blocked.toArray()));
                    ac+=1;
            }
                runtime+=1;}
//            else if(block>0){
//                for(int j=0;j<input.size();j++){
//                    int p=(int)go.get(j).peek().get(0);
//                    int t=(int)go.get(j).peek().get(1);
//                    int r=(int)go.get(j).peek().get(2);
//                    int n=(int)go.get(j).peek().get(3);
//                    if(p==2 && finish.get(j)==0){
//                        List<Integer> l=new ArrayList <Integer>();
//                        go.get(j).addFirst(l);
//                    }
//                }
//            }                                System.out.println("runtime "+runtime);
            System.out.println("");
            runtime+=1;
            int ac=0;
            for(int j=0;j<input.size();j++) {
                if (go.get(j).size() > 1) go.get(j).removeFirst();
                else ac+=1;
            }    g+=1;
            if(ac==input.size()) stop=true;

        }
    System.out.println("FIFO");
        int total=0;
        int w=0;
    for(int z=0;z<input.size();z++){
        System.out.print("Task    "+(z+1));

        if(finish.get(z)>0) {System.out.println("\t"+finish.get(z)+"\t"+wait.get(z)+"\t"+(float)wait.get(z)/finish.get(z)*100+"%");
        total+=finish.get(z); w+=wait.get(z);}
        else System.out.println(String.format("\taborted"));
    }System.out.println("total:\t\t"+total+"\t"+w+"\t"+(float)w/total*100+"%");
    }
    public static void fifo1(List resources,List<List<List>> input){
        List<Integer> resReturn=new ArrayList<Integer>();
        for(int i=0;i<resources.size();i++){
            resReturn.add(0);
        }
        List<Deque<List>> go=new ArrayList<Deque<List>>();
        List<Integer> finish=new ArrayList<Integer>();
        List<Integer> wait=new ArrayList<Integer>();
        List<List<Integer>> claimed= new ArrayList<List<Integer>>();
        Deque<List> first=new ArrayDeque<>();
        Deque<List> claimQ=new ArrayDeque<>();
        List<Boolean> prevBlock =new ArrayList<Boolean>();
        int unfinished=input.size();
        for(int j=0;j<input.size();j++){
            //need.add((int)input.get(j).get(0).get(3));
            finish.add(0);
            wait.add(0);
            prevBlock.add(false);
            List<Integer> res=new ArrayList<Integer>();
            for (int k=0;k<resources.size();k++){
                res.add(0);
            }
            claimed.add(res);
            Deque<List> q=new ArrayDeque();
            for(int i=0;i<input.get(j).size();i++){
                q.add(input.get(j).get(i));
            }
            go.add(q);
        }
        Boolean stop=false;

        int runtime=0;
        int s=0;
        int g=0;
        while(!stop){
            //System.out.println("runtime "+runtime);
            int block=0;
            List<Integer> blocked = new ArrayList<Integer>();
            boolean returner=false;
            s=first.size();
            for(int j=0;j<s;j++){
                claimQ.add(first.pop());
            }

            for(int j=0;j<input.size();j++){
                //System.out.println(Arrays.toString(go.get(j).peek().toArray()));
                blocked.add(0);
                if (go.get(j).size()>0){
                    int p=(int)go.get(j).peek().get(0);
                    int t=(int)go.get(j).peek().get(1);
                    int r=(int)go.get(j).peek().get(2);
                    int n=(int)go.get(j).peek().get(3);
                    if(p==2 && finish.get(j)==0) {//1-initiate, 2-request,  3-release, 4- terminate
                        if(!prevBlock.get(j)) claimQ.add(go.get(j).peek());
//                    else {List<Integer> l=new ArrayList <Integer>();
//                        go.get(j).addFirst(l);}
                    }else if(p==3&& finish.get(j)==0){
                        resReturn.set(r-1,n);
                        returner=true;
                        claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)-n);
                    }else if(p==4&& finish.get(j)==0){
                        finish.set(j,runtime);
                        unfinished-=1;
                    }else if(p==5&& finish.get(j)==0){
                        if(r>1){
                            go.get(j).peek().set(2,r-1);
                            List<Integer> l=new ArrayList <Integer>();
                            go.get(j).addFirst(l);
                        }
                    }
                }}
//            System.out.println("Q:    "+Arrays.toString(claimQ.toArray()));
//            System.out.println("res bef:    "+Arrays.toString(resources.toArray()));
            s=claimQ.size();
            for (int j=0;j<s;j++){
                int p=(int)claimQ.peek().get(0);
                int t=(int)claimQ.peek().get(1);
                int r=(int)claimQ.peek().get(2);
                int n=(int)claimQ.peek().get(3);
                //System.out.println(Arrays.toString(claimQ.peek().toArray()));
                if((int)resources.get(r-1)>=n){
                    resources.set(r-1,(int)resources.get(r-1)-n);
                    claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)+n);
                    prevBlock.set(t-1,false);
                    claimQ.pop();
                }
                else {
                    wait.set(t-1,wait.get(t-1)+1);
                    List<Integer> l=new ArrayList <Integer>();
                    go.get(t-1).addFirst(l);
                    block+=1;
                    blocked.set(t-1,1);
                    first.add(claimQ.pop());
                    prevBlock.set(t-1,true);
                }
            }
//            System.out.println("res aft:    "+Arrays.toString(resources.toArray()));
//            System.out.println("blocked:    "+Arrays.toString(blocked.toArray()));
            if(returner){
                for(int j=0;j<resources.size();j++){
                    resources.set(j,resReturn.get(j)+(int)resources.get(j));
                    resReturn.set(j,0);
                }}

            //deadlock abort
//            System.out.println("first:  "+Arrays.toString(first.toArray()));
            if(block==unfinished && unfinished>=1){
                boolean blocking=true;
                int ac=0;

                while(blocking && ac<5){
//                    System.out.println("blocking");
                    int breaker=-1;
                    boolean b=false;
//                    System.out.println("first blocked:  "+Arrays.toString(blocked.toArray()));
                    for(int j=0;j<input.size();j++){
                        if(blocked.get(j)==1){
                            breaker=j+1;
                            finish.set(j,-1);
                            go.get(j).pop();
                            for(int k=0;k<resources.size();k++){
                                resources.set(k,claimed.get(j).get(k)+(int)resources.get(k));
                                b=true;
                                break;
                            }}
                        if(b) break;
                    };//abort lowest number deadlocked task
                    blocked=new ArrayList<Integer>();

                    for(int j=0;j<input.size();j++){
                        blocked.add(0);
                    }
                    s=first.size();
                    for(int j=0;j<s;j++){
//                        System.out.println("run:  "+Arrays.toString(first.peek().toArray()));
//                        System.out.println("resources:  "+Arrays.toString(resources.toArray()));
                        if((int)first.peek().get(1)==breaker){
                            first.pop();
//                            System.out.println("ow");
                        }
                        else{
                            int p=(int)first.peek().get(0);
                            int t=(int)first.peek().get(1);
                            int r=(int)first.peek().get(2);
                            int n=(int)first.peek().get(3);
                            if(p==2 && finish.get(t-1)==0) {//1-initiate, 2-request,  3-release, 4- terminate
                                if ((int) resources.get(r - 1) >= n) {
                                    go.get(t-1).pop();
                                    blocking=false;
                                    resources.set(r - 1, (int) resources.get(r - 1) - n);
                                    claimed.get(t - 1).set(r - 1, claimed.get(t - 1).get(r - 1) + n);
                                    first.pop();
                                } else {
//                                    System.out.println("haha");
                                    blocked.set(t-1, 1);
                                    first.add(first.pop());
                                }
                            }
                        }}
//                    System.out.println("blocked:    "+Arrays.toString(blocked.toArray()));
                    ac+=1;
                }
                runtime+=1;}
//            else if(block>0){
//                for(int j=0;j<input.size();j++){
//                    int p=(int)go.get(j).peek().get(0);
//                    int t=(int)go.get(j).peek().get(1);
//                    int r=(int)go.get(j).peek().get(2);
//                    int n=(int)go.get(j).peek().get(3);
//                    if(p==2 && finish.get(j)==0){
//                        List<Integer> l=new ArrayList <Integer>();
//                        go.get(j).addFirst(l);
//                    }
//                }
//            }                                System.out.println("runtime "+runtime);
            //System.out.println("");
            runtime+=1;
            int ac=0;
            for(int j=0;j<input.size();j++) {
                if (go.get(j).size() > 1) go.get(j).removeFirst();
                else ac+=1;
            }    g+=1;
            if(ac==input.size()) stop=true;

        }
        System.out.println("FIFO");
        int total=0;
        int w=0;
        for(int z=0;z<input.size();z++){
            System.out.print("Task    "+(z+1));

            if(finish.get(z)>0) {System.out.println("\t"+finish.get(z)+"\t"+wait.get(z)+"\t"+(float)wait.get(z)/finish.get(z)*100+"%");
                total+=finish.get(z); w+=wait.get(z);}
            else System.out.println(String.format("\taborted"));
        }System.out.println("total:\t\t"+total+"\t"+w+"\t"+(float)w/total*100+"%");
    }


    //check  if safe
    private static boolean check(int i,List<Integer> resources,int[][] n){

        for(int j=0;j<resources.size();j++) {
            if(resources.get(j)<n[i][j]) {
                return false;
            }
        }
        return true;}

    public static boolean bank(List<Integer> resources, List<Integer> task,List<List<Integer>> claim,List<List<Integer>> needed,List<Integer> finish){
        System.out.println("start bank");
        boolean visit[]=new boolean[claim.size()];
        int n[][]=new int[claim.size()][resources.size()];  //need to complete
        int need[][]=new int[claim.size()][resources.size()]; //initial claim
        int given[][]=new int[claim.size()][resources.size()]; //claimed resources
        int r[][]=new int[1][resources.size()];             //resource available

        int done=0;
        for(int i=0;i<finish.size();i++){
            for(int j=0;j<resources.size();j++){
                need[i][j]=needed.get(i).get(j);
                given[i][j]=claim.get(i).get(j);
                n[i][j]=need[i][j]-given[i][j];
                r[0][j]=resources.get(j);
            }
            if(finish.get(i)>0){
                done+=1;
                visit[i]=true;
            }
            else if(finish.get(i)==0) {visit[i]=false;visit[i]=false;}
        }
        while(done<claim.size()){
            System.out.println("start done");
            boolean solve=false;
            for(int i=0;i<claim.size();i++){
                if(!visit[i] && check(i,resources,n)){
                    for(int j=0;j<resources.size();j++){
                        r[0][j]=r[0][j]-n[i][j]+need[i][j];
                    }

                    solve=visit[i]=true;
                    done++;
            }
        }if(!solve) break;}
        if(done==finish.size()) return true;
        else return false;
    }
    public static void banker(List resources,List<List<List>> input){
        List<Integer> resReturn=new ArrayList<Integer>();
        for(int i=0;i<resources.size();i++){
            resReturn.add(0);
        }
        List<Deque<List>> go=new ArrayList<Deque<List>>();
        List<List<Integer>> need=new ArrayList<List<Integer>>();
        List<Integer> finish=new ArrayList<Integer>();
        List<Integer> wait=new ArrayList<Integer>();
        List<List<Integer>> claimed= new ArrayList<List<Integer>>();
        Deque<List> Q=new ArrayDeque<List>();
        List<Boolean> prevBlock =new ArrayList<Boolean>();
        int unfinished=input.size();

        for(int j=0;j<input.size();j++){
            finish.add(0);
            wait.add(0);
            prevBlock.add(false);
            List<Integer> res=new ArrayList<Integer>(); //resources of each type claimed
            List<Integer> res2=new ArrayList<Integer>(); //resources of each type claimed
            for (int k=0;k<resources.size();k++){
                res2.add(0);
                res.add(0);
            }
            need.add(res2);
            claimed.add(res);
            Deque<List> q=new ArrayDeque();
            for(int i=0;i<input.get(j).size();i++){
                q.add(input.get(j).get(i));
            }
            go.add(q);
        }

        Boolean stop=false;
        int runtime=0;
        int s=0;
        int q=0;
        while(!stop && q<20){
            System.out.println("runtime "+runtime);
            System.out.println("finish "+Arrays.toString(finish.toArray()));
            int block=0;
            List<Integer> blocked = new ArrayList<Integer>();
            boolean returner=false;
            s=Q.size();
            for(int j=0;j<s;j++){
                Q.add(Q.pop());
            }

            for(int j=0;j<input.size();j++){
                System.out.println(Arrays.toString(go.get(j).peek().toArray()));
                blocked.add(0);
                if (go.get(j).size()>0){
                    int p=(int)go.get(j).peek().get(0);
                    int t=(int)go.get(j).peek().get(1);
                    int r=(int)go.get(j).peek().get(2);
                    int n=(int)go.get(j).peek().get(3);
                    if(p==1){
                        if(n>(int)resources.get(r-1)){ //initial claim more than max available
                            finish.set(t-1,-1);
                        }
                        else need.get(t-1).set(r-1,n);
                    }
                    else if(p==2 && finish.get(j)==0) {//1-initiate, 2-request,  3-release, 4- terminate
                        if(n>need.get(t-1).get(r-1)) { //exceed initial claim, abort
                            finish.set(t-1,-1);
                            returner=true;
                            for(int z=0;z<resources.size();z++){
                                resReturn.set(r-1,claimed.get(t-1).get(z));
                                claimed.get(t-1).set(z,0);
                            }
                        }
                        if(!prevBlock.get(j)) Q.add(go.get(j).peek());
                    }else if(p==3&& finish.get(j)==0){
                        resReturn.set(r-1,n);
                        returner=true;
                        claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)-n);
                    }else if(p==4&& finish.get(j)==0){
                        finish.set(j,runtime);
                        unfinished-=1;
                    }else if(p==5&& finish.get(j)==0){
                        if(r>1){
                            go.get(j).peek().set(2,r-1);
                            List<Integer> l=new ArrayList <Integer>();
                            go.get(j).addFirst(l);
                        }
                    }
                }}
            System.out.println("Q:    "+Arrays.toString(Q.toArray()));
            System.out.println("res bef:    "+Arrays.toString(resources.toArray()));
            s=Q.size();
            for (int j=0;j<s;j++){
                int p=(int)Q.peek().get(0);
                int t=(int)Q.peek().get(1);
                int r=(int)Q.peek().get(2);
                int n=(int)Q.peek().get(3);
                //System.out.println(Arrays.toString(claimQ.peek().toArray()));

                resources.set(r-1,(int)resources.get(r-1)-n);
                claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)+n);
                if(bank(resources,Q.peek(),claimed,need,finish)){
                    prevBlock.set(t-1,false);
                    Q.pop();
                }
                else {
                    resources.set(r-1,(int)resources.get(r-1)+n);
                    claimed.get(t-1).set(r-1,claimed.get(t-1).get(r-1)-n);
                    wait.set(t-1,wait.get(t-1)+1);
                    List<Integer> l=new ArrayList <Integer>();
                    go.get(t-1).addFirst(l);
                    block+=1;
                    blocked.set(t-1,1);
                    Q.add(Q.pop());
                    prevBlock.set(t-1,true);
                }
            }
            System.out.println("res aft:    "+Arrays.toString(resources.toArray()));
            System.out.println("blocked:    "+Arrays.toString(blocked.toArray()));
            if(returner){
                for(int j=0;j<resources.size();j++){
                    resources.set(j,resReturn.get(j)+(int)resources.get(j));
                    resReturn.set(j,0);
                }}

                runtime+=1;
//            else if(block>0){
//                for(int j=0;j<input.size();j++){
//                    int p=(int)go.get(j).peek().get(0);
//                    int t=(int)go.get(j).peek().get(1);
//                    int r=(int)go.get(j).peek().get(2);
//                    int n=(int)go.get(j).peek().get(3);
//                    if(p==2 && finish.get(j)==0){
//                        List<Integer> l=new ArrayList <Integer>();
//                        go.get(j).addFirst(l);
//                    }
//                }
//            }                                System.out.println("runtime "+runtime);
            System.out.println("");
            int ac=0;
            for(int j=0;j<input.size();j++) {
                if (go.get(j).size() > 1) go.get(j).pop();
                else ac+=1;
            }    q+=1;
            if(ac==input.size()) stop=true;}


        System.out.println("Banker");
        int total=0;
        int w=0;
        for(int z=0;z<input.size();z++){
            System.out.print("Task    "+(z+1));

            if(finish.get(z)>0) {System.out.println("\t"+finish.get(z)+"\t"+wait.get(z)+"\t"+(float)wait.get(z)/finish.get(z)*100+"%");
                total+=finish.get(z); w+=wait.get(z);}
            else System.out.println(String.format("\taborted"));
        }System.out.println("total:\t\t"+total+"\t"+w+"\t"+(float)w/total*100+"%");
    }
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


//        for(int pp=1;pp<14;pp++){
//        inputFileName="input-";
//        if(pp<10) inputFileName+='0';
//        inputFileName+=Integer.toString(pp);
//        File inputFile = new File(inputFileName);
//        Scanner in = new Scanner(inputFile);
//        List<Integer> resources = new ArrayList<Integer>();
//        List<List<List>> input = new ArrayList<List<List>>();
//        int n=in.nextInt();
//        int r=in.nextInt();
//        for(int i=0;i<r;i++){
//            resources.add(in.nextInt());
//        }
//        for(int i=0;i<n;i++) {
//            List<List> l=new ArrayList<List>();
//            input.add(l);
//        } //1-initiate, 2-request,  3-release, 4- terminate, 5- compute
//        while(in.hasNext()){
//            List<Integer> l=new ArrayList<Integer>();
//            String t=in.next();
//            //System.out.println(t);
//            if(t.equals("initiate")){
//                l.add(1);
//            }else if(t.equals("request")){
//                l.add(2);
//            }else if(t.equals("release")){
//                l.add(3);
//            }else if(t.equals("terminate")){
//                l.add(4);
//            }else if(t.equals("compute")){
//                l.add(5);
//            }
//            int s=in.nextInt();
//            l.add(s);
//            for(int j=0;j<2;j++){
//                l.add(in.nextInt());
//            }
//            input.get(s-1).add(l);
//        }
//        System.out.println(pp+"     start fifo");
//        fifo1(resources, input);}


        {inputFileName="input-11";
        File inputFile = new File(inputFileName);
        Scanner in = new Scanner(inputFile);
        List<Integer> resources = new ArrayList<Integer>();
        List<List<List>> input = new ArrayList<List<List>>();
        int n=in.nextInt();
        int r=in.nextInt();
        for(int i=0;i<r;i++){
            resources.add(in.nextInt());
        }
        for(int i=0;i<n;i++) {
            List<List> l=new ArrayList<List>();
            input.add(l);
        } //1-initiate, 2-request,  3-release, 4- terminate, 5- compute
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
            }else if(t.equals("compute")){
                l.add(5);
            }
            int s=in.nextInt();
            l.add(s);
            for(int j=0;j<2;j++){
                l.add(in.nextInt());
            }
            input.get(s-1).add(l);
        }
        System.out.println("start banker");
        banker(resources, input);}


    }
}
