import java.io.*;
import java.util.*;
public class Main {
    //check if Numerical
    public static boolean isNumeric(String str){ return str.chars().allMatch(Character::isDigit);}


    public static void main(String[] args) throws Exception {
        String currentDirectory=System.getProperty("user.dir");
        System.out.println(currentDirectory);
        File file=new File("input-1");
        BufferedReader br=new BufferedReader(new FileReader(file));
        String st;
        while((st=br.readLine())!=null) System.out.println(st);
        System.out.println("Hello World!");
    }
}
