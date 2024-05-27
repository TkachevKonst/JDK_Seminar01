package ru.gb.jdk.server;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataFile implements DataBase{

    public  final String LOG_PATH = "src/main/java/ru/gb/jdk/log.txt";

    public void saveInfo(String text) {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)) {
            writer.write(text);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH)) {
            int c;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
