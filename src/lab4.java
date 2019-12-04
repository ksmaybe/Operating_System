import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class lab4{
    String randomFileName = "random-numbers.txt";
    File randomFile = new File(randomFileName);
    Scanner random= new Scanner(randomFile);

    public lab4() throws Exception {
    }


    public void lifo(){
        Stack s= new Stack();

    }
    public void wp1(int S) {

    }

    public void wm5(int S) {
    }

    public void wp4(int S) {
    }

    public void main(String[] args) throws Exception {
        //System.out.println(args.length);0
        int M = Integer.getInteger(args[2]);
        int P = Integer.getInteger(args[3]);
        int S = Integer.getInteger(args[4]);
        int J = Integer.getInteger(args[5]);
        int N = Integer.getInteger(args[6]);
        String alg = args[7];

        System.out.println("The machine size is "+M);
        System.out.println("The page size is "+P);
        System.out.println("The process size is "+S);
        System.out.println("The job mix number is "+J);
        System.out.println("The number of references per process is "+N);
        System.out.println("The replacement algorithm is "+alg);
        System.out.println();
    }
}
