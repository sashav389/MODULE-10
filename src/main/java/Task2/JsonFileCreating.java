package Task2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//головна проблема виникла на завданні з Json
//не зміг розробити запис в правильному форматі в json файл
//разрахунок йшов на те, що у вхідному файлі буде невідома кількість полів у об'єкта
//виводиться у консоль правильному вигляді, але у файл записується з усіма спецсимловами

public   class JsonFileCreating {
    public static void readAndCreateJson(File file3) {

        StringBuilder text = new StringBuilder();

        try (FileReader reader = new FileReader(file3)) { // тут просто зчитуємо весь файл в одну строку
            char[] buf = new char[256];
            int c;
            while ((c = reader.read(buf)) > 0) {
                if (c < 256) {
                    buf = Arrays.copyOf(buf, c);
                }
                text.append(buf);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        byte[] byteText = text.toString().getBytes();
        List<Byte> names = new ArrayList<>();
        List<Byte> objects = new ArrayList<>();
        int i;
        for (i = 0; i < byteText.length; i++) { // рахуємо кількість слів до першого переносу, тобто кількість полів
            if (byteText[i] == 13) break;
            names.add(byteText[i]);
        }

        for (int j = i + 2; j < byteText.length; j++) { //зчитуємо усі інші симоли
            objects.add(byteText[j]);
        }
        byte[] namesBytes = new byte[i];
        for(int x = 0; x < i; x++){ // створємо масив байтів
            namesBytes[x] = names.get(x);
        }
        byte[] objectBytesWithError = new byte[byteText.length - i -2];
        for(int x = 0; x < objectBytesWithError.length; x++){ // створємо масив байтів
            objectBytesWithError[x] = objects.get(x);
        }
        byte[] objectsBytes = replaceSlashN(objectBytesWithError); // замінюємо усі переноси строк на пробіли
        String[] allNames = new String(namesBytes).split(" ");
        String[] allObjects = new String(objectsBytes).split((" "));

//        JSONObject jsonObject = new JSONObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = "";
        String temp = "[\n";
        for(int j = 0; j < allObjects.length;) { // записужмо у строку у форматі JSON файлу
            temp += "\t{\n";
            for (int x = 0; x < allNames.length; x++) {
                temp += "\t\t\"" + allNames[x] + "\": \"" + allObjects[j] + "\"\n";
                j++;
            }
            temp += "\t}\n";

        }
        temp += "]";
        json = gson.toJson(temp);

        System.out.println(temp); // виводить красиво все як треба
        System.out.println(json); // виводить і вписує у файл не правильно
        try {
            FileWriter file = new FileWriter("output.json");
            file.write(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static byte[] replaceSlashN ( byte[] temp){
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

