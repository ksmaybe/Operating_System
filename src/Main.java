import java.io.*;
import java.util.*;
public class Main {
    //check if Numerical
    public static boolean isNumeric(String str){ return str.chars().allMatch(Character::isDigit);}


    public static void main(String[] args) throws Exception {
        boolean keyboard=false;
        if (args.length!=1) keyboard=true;
        String[] error=new String[]{
                "Warning: %s is defined but not used.",
                "Error: %s is multiply defined. First definition used.",
                "Error: %s is not defined. Given value zero.",
                "Error: Multiple symbols listed. All but last usage ignored.",
                "Error: Address exceed size of module. Address treated as zero.",
                "Error: Immediate address in use list. Treated as external.",
                "Error: External address not in use list. Treated as immediate address.",
                "Error: Absolute address exceeds size of machine. Largest legal value used."
        };
        if (!keyboard){}
        else{

            //File file=new File(args[0]);
            File file=new File("input-1");
            Scanner first = new Scanner(file);
            int mod_address=0;
            String s="";   //symbol scanned
            int totalMods=first.nextInt();
            int numSymbols=0;
            int defSymbol=0;
            HashMap<String,Integer> defList=new HashMap<String, Integer>();
            int totalAddresses=0;
            int totalUses=0;
            HashMap<String,Integer> symbolUsed= new HashMap<String, Integer>();
            HashMap<String,List<Integer>> useList = new HashMap<String,List<Integer>>();
            //first pass
            for (int i=0;i<totalMods;i++) {
                //definition list
                numSymbols = first.nextInt();
                for (int k = 0; k < numSymbols; k++) {
                    s = first.next();
                    defSymbol=first.nextInt();
                    if(defList.containsKey(s)){
                        defList.put(s,(defSymbol+mod_address));
                        System.out.println(error[0]);
                    } else{
                        defList.put(s,defSymbol+mod_address);
                        symbolUsed.put(s,i);
                    }
                    defSymbol = first.nextInt();
                    System.out.print(s);
                    System.out.print("  ");
                    System.out.println(defSymbol);

                    }
                //use list
                numSymbols = first.nextInt();
                for (int k = 0; k < numSymbols; k++) {
                    s = first.next();
                    defSymbol=first.nextInt();
                    if(defList.containsKey(s)){
                        defList.put(s,(defSymbol+mod_address));
                        System.out.println(error[0]);
                    } else{
                        defList.put(s,defSymbol+mod_address);
                        symbolUsed.put(s,i);
                    }
                    defSymbol = first.nextInt();
                    System.out.print(s);
                    System.out.print("  ");
                    System.out.println(defSymbol);

                }
                //program text
                numSymbols=first.nextInt();
                for(int k =0;k<numSymbols;k++){
                    defSymbol=first.nextInt();
                    System.out.println(defSymbol);

                }
            }
            System.out.println("Hello World!");}
    }
}
