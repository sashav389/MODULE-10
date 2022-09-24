package Task3;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordsCounter {
    public static void countWords(File file2) {
        StringBuilder text = new StringBuilder();
        try (FileReader reader = new FileReader(file2)) {
            char[] buf = new char[256];
            int c;
            while ((c = reader.read(buf)) > 0) {
                if (c < 256) {
                    buf = Arrays.copyOf(buf, c);
                }
                text.append(new String(buf));
            }
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

        String y = new String(replaceSlashN(text.toString().getBytes())); // формуємо строку з пробілами без переносів строки
        String[] arr = y.split(" "); // масив усіх слів

        Arrays.sort(arr);// сортуємо
        HashMap<String, Integer> count = new HashMap<>();
        int x = 0;
        System.out.println("\n");
        for (int i = 0; i < arr.length - 1; i++) { // оскільки однакові слова разом - то рахуємо їч і записуємо в ХешМапу
            x++;
            if (!(arr[i].equals(arr[i + 1]))) {
                if (i + 2 == arr.length) {
                    count.put(arr[i + 1], x + 1);
                    break;
                }
                count.put(arr[i], x);
                x = 0;
            }
            if (arr.length - i == 2 && arr[i].equals(arr[arr.length - 1])) {
                // System.out.println(i + " " + x);
                count.put(arr[i + 1], x + 1);
                break;
            }

        }
        count.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(System.out::println); // сортуємо мапу

    }
    static byte[] replaceSlashN ( byte[] temp){ //заміна переносів на пробіли
        int count = 0;
        for (byte b : temp) {
            if (b == (byte) 10) count++;
        }
        byte[] res = new byte[temp.length - count];
        int j = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == (byte) 13) {
                res[j] = 32;
                i++;
                j++;
                continue;
            }
            res[j] = temp[i];
            j++;
        }
        return res;
    }
}
