package Task1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NumberChecker {
    public static void checkNumbers(File file){
        ArrayList<Byte> numbers = new ArrayList<>();
        try (FileReader reader = new FileReader(file)) {
            int c;
            while ((c = reader.read()) != -1 ) {
                ArrayList<Byte> number = new ArrayList<>();
                while( c != 10 && c != -1  && c != 13){
                    number.add((byte) c);
                    c = reader.read();
                }
                int[] arr = number.stream().mapToInt(i -> i).toArray();
                check(arr);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    static void check(int[] temp){
        if(temp.length == 12 || temp.length == 14){
            if(checkOne(temp)){
                if(checkTwo(temp)){
                    byte[] number = new byte[temp.length];
                    for(int i = 0; i< temp.length; i++){
                        number[i] = (byte) temp[i];
                    }
                    System.out.println(new String(number));
                }
            }
        }
    }
    static boolean checkOne(int[] temp){ // перевірка на формат номеру
        return temp[0] == 40 && temp[4] == 41 && temp[5] == 32 && temp[9] == 45 || temp[3] == 45 && temp[7] == 45;
    }
    static boolean checkTwo(int[] temp){ //перевірка чи всі цифри, окрім спецсимволів
        if(temp[0] == 40){
            for(int i = 1; i<temp.length; i++){
                if(i == 4 || i == 5 || i == 9) continue;
                if(!isNumber(temp[i])) return false;
            }
        }
        else{
            for(int i = 0; i<temp.length; i++){
                if(i == 3 || i == 7) continue;
                if(!isNumber(temp[i])) return false;
            }
        }
        return true;
    }
    static boolean isNumber(int x){ // чи не буква
        if((x >= 48 && x <= 57)) return true;
        return false;
    }
}
