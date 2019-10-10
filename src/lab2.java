import java.io.*;
import java.util.*;

public class lab2 {
    private static float randomOS(Scanner input, int U) {
        String X = input.nextLine();
        return 1 + (Integer.parseInt(X) % U);
    }

    private static float fcfs(List<List> original, List<List> input) throws Exception {
        printIntro(original, input);


        int a = 1241;
        return 0;
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

    private static float rr(List original, List input) throws Exception {


        String input_line = "";

        int a = 1234;
        return 0;
    }

    private static float sjf(List original, List input) throws Exception {

        String input_line = "";

        return 0;
    }

    private static float hprn(List original, List input) throws Exception {


        String input_line = "";

        return 0;
    }

    public static void main(String[] args) throws Exception {
        String randomFileName = "random-numbers.txt";
        String inputFileName = "input5";
        File randomFile = new File(randomFileName);
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
