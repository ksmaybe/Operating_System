import java.io.*;
import java.util.*;
public class Main {
    //check if Numerical
    public static boolean isNumeric(String str){ return str.chars().allMatch(Character::isDigit);}



    public static void main(String[] args) throws Exception {
        boolean keyboard=false;
        Integer max_limit_character=8;
        if (args.length!=1) keyboard=true;
        String[] error=new String[]{
                "Warning: %s is defined but not used.", //0
                "Error: %s is multiply defined. First definition used.", //1
                "Error: %s is not defined. Given value zero.", //2
                "Error: Multiple symbols listed. All but last usage ignored.", //3
                "Error: Address exceed size of module. Address treated as zero.", //4
                "Error: Immediate address in use list. Treated as external.", //5
                "Error: External address not in use list. Treated as immediate address.", //6
                "Error: Absolute address exceeds size of machine. Largest legal value used." //7
        };
        if (!keyboard){

        }
        else{

            //File file=new File(args[0]);
            File file=new File("input-3");
            Scanner input = new Scanner(file);
            int mod_address=0;
            String s="";   //string scanned
            int totalMods=input.nextInt(); //Number of modules
            int NT=0; //Number of times used
            int num=0;  //integer scanned
            HashMap<String,Integer> defList=new HashMap<String, Integer>();
            int sizeMachine=0;
            HashMap<String,Integer> symbolUsed= new HashMap<String, Integer>();
            HashMap<Integer,String> useList = new HashMap<Integer,String>();
            HashMap<Integer,Integer> moduleSize=new HashMap<Integer,Integer>();
            HashMap<String,Integer> location=new HashMap<String,Integer>();
            ArrayList<String> definition_error= new ArrayList<String>();
            //first pass
            for (int i=0;i<totalMods;i++) {
                //definition list

                NT = input.nextInt();
                for (int k = 0; k < NT; k++) {
                    s = input.next();
                    num=input.nextInt();

                    if(defList.containsKey(s)){
                        System.out.println(String.format(error[1],s));
                    } else{
                        defList.put(s,num+mod_address);
                    }
                    location.put(s,i);

                    }
                //use list
                NT = input.nextInt();

                for (int k = 0; k < NT; k++) {
                    s = input.next();
                    num=input.nextInt();
                    if(defList.containsKey(s)){
                        symbolUsed.put(s,(num+mod_address));
                        useList.put(num+mod_address,s);

                    } else{
                        symbolUsed.put(s,mod_address);
                        useList.put(mod_address,s);
                    }


                }
                //program text
                NT=input.nextInt();
                for(int k =0;k<NT;k++){
                    num=input.nextInt();
                    moduleSize.put(i,mod_address+NT);
                }
                mod_address+=NT;
            }// end of first pass
            sizeMachine=mod_address;
            System.out.println("Symbol Table");
            //check if symbol defined but not used
            for(String key: defList.keySet()){
                if(!symbolUsed.containsKey(key)) definition_error.add(String.format(error[0],key));
                //checks if address exceeds module size, if so becomes 0/relative
                if(defList.get(key)>moduleSize.get(location.get(key))){
                    defList.put(key,moduleSize.get(location.get(key)));
                    System.out.println(String.format(error[4],s));
                }
                System.out.println(key+"="+defList.get(key));
            }
            //check if symbol used but not defined
            for(String key: symbolUsed.keySet()){
                if(!defList.containsKey(key)) System.out.println(String.format(error[2],key));
            }
            //start second pass
            input=new Scanner(file);
            System.out.println("Memory Map");
            mod_address=0;
            totalMods=input.nextInt();
            for (int i=0;i<totalMods;i++) {
                //definition list
                NT = input.nextInt();
                for (int k = 0; k < NT; k++) {
                    input.next();
                    input.nextInt();
                }
                //use list
                NT = input.nextInt();
                for (int k = 0; k < NT; k++) {
                    input.next();
                    input.nextInt();
                }
                //program text
                NT=input.nextInt();
                for(int k =0;k<NT;k++){
                    Integer val=input.nextInt();
                    Integer type=val%10;
                    val=val/10;
                    Integer op=val/1000;
                    Integer v=0;
                    if(type==1 || type==2){
                        v=val;
                    }else if(type==3){
                        v=val+mod_address;
                    }else if(type==4){
                        v=(op*1000);//+defList.get(useList.get(mod_address+k));
                    }
                    System.out.println((mod_address+k)+":   "+v);

                }
                mod_address+=NT;
            }// end of second pass
            for(int i=0;i<definition_error.size();i++){
                System.out.println(definition_error.get(i));
            }

        }
    }
}
