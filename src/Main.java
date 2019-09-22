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
        File file;
        if (keyboard){
            Scanner kb=new Scanner(System.in);
            File kb_input= new File("kb_input");
            kb_input.createNewFile();
            FileWriter writer=new FileWriter(kb_input);
            String quit="";
            while(true){
                quit=kb.nextLine();
                System.out.println(quit);
                if(quit.length()==0) break;
                writer.write(quit);
                writer.write("\n");

            } writer.close();
            file=kb_input;
        }
        else{
            //File file=new File(args[0]);
            file=new File("input-8");}
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
                //check number of characters in a symbol
                if(s.length()>max_limit_character){
                    System.out.println(s+" is more than character limit of "+max_limit_character+". Program terminated.");
                    return;
                }

                if(defList.containsKey(s)){
                    System.out.println(String.format(error[1],s));
                } else{
                    if(num>=NT){defList.put(s,mod_address);
                    System.out.println("The definition of "+s+" is outside of module "+k+" . Zero (relative) used.");}
                    else defList.put(s,num+mod_address);
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
        HashMap<Integer,String> symbolVal=new HashMap<Integer,String>();
        int valSymbol[]=new int[defList.size()];
        int o=0;
        for(String key: defList.keySet()){
            if(!symbolUsed.containsKey(key)) definition_error.add(String.format(error[0],key));
            //checks if address exceeds module size, if so becomes 0/relative
            if(defList.get(key)>moduleSize.get(location.get(key))){
                defList.put(key,moduleSize.get(location.get(key)));
                System.out.println(String.format(error[4],s));
            }
            symbolVal.put(defList.get(key),key);
            valSymbol[o]=defList.get(key);
            o++;

        }
        Arrays.sort(valSymbol);
        for(int oo=0;oo<valSymbol.length;oo++){
            System.out.println(symbolVal.get(valSymbol[oo])+"="+valSymbol[oo]);}
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
            HashMap<Integer,String> use=new HashMap<Integer,String>();
            NT = input.nextInt();
            for (int k = 0; k < NT; k++) {
                s=input.next();
                String next_input=input.next();
                while(true){
                if(isNumeric(next_input)){
                    num=Integer.parseInt(next_input);
                    break;
                }
                else {
                    s=next_input;
                    next_input=input.next();
                    System.out.println("Multiple symbols in same use instruction, last usage used only.");
                }
                }
                use.put(num,s);
            }
            //program text
            HashMap<Integer,Integer> operations = new HashMap<Integer,Integer>();
            HashMap<Integer,Integer> locate = new HashMap<Integer,Integer>();
            NT=input.nextInt();
            for(int k =0;k<NT;k++){
                Integer val=input.nextInt();
                operations.put(k,val);
                locate.put(val,k);
            }
//            for(Integer key:use.keySet()){
//                System.out.println(key+"    "+use.get(key));
//            }
//            for(Integer key:operations.keySet()){
//                System.out.println(key+"    "+operations.get(key));
//            }
            //parse through use list operations
            int[] values=new int[NT];
            for(Integer key: use.keySet()){
                int sVal=0;
                //symbol used but not defined
                if(!defList.containsKey(use.get(key))){
                    sVal=0;
                }
                else sVal=defList.get(use.get(key)); //symbol def
                int val=operations.get(key);
                while(val!=-1) {
                    int type = val % 10;
                    int address = (val / 10) % 1000;
                    int v = val / 10000;
                    if (type == 1) {
                        values[key] = v * 1000 + sVal;
                        operations.remove(key);
                        System.out.println("Error: " + val + " is immediate type appears on use list, treated as external type.");
                    } else if (type == 2) {
                        values[key] = val / 10;
                        operations.remove(key);
                        break;
                    } else if (type == 3) {
                        values[key] = val / 10 + mod_address;
                        operations.remove(key);
                        break;
                    } else {
                        values[key] = v * 1000 + sVal;
                        operations.remove(key);
                    }
                    key = address;
                    if (key == 777) val = -1;
                    else val = operations.get(key);
                    if (key >= NT && key!=777) {
                        System.out.println("Error: " + address + " is out of range. Program terminated.");
                        return;
                    }
                }
            }
            for(Integer key:operations.keySet()){
                int val=operations.get(key);
                int type = val % 10;
                if (type==4){
                    values[key]=val/10;
                    System.out.println("Error: "+val+" is external type not in use list, treated as immediate type.");
                }else if(type==1){
                    values[key]=val/10;}
                else if(type==2){
                    if((val%10000)/10>=200){
                        values[key]=(val/10000)*1000+199;
                        System.out.println(val+" is absolute address more than 200 word limit. Using largest legal value");
                    }
                    else values[key]=val/10;

                }else if(type==3){
                    values[key]=val/10+mod_address;
                }
            }
            for(int z=0;z<values.length;z++){
                System.out.println(mod_address+z+": "+values[z]);
            }

            mod_address+=NT;
        }// end of second pass
        for(int i=0;i<definition_error.size();i++){
            System.out.println(definition_error.get(i));
        }

    }
}

