package org.example;

import Task1.NumberChecker;
import Task3.WordsCounter;
import Task2.JsonFileCreating;

import java.io.File;


public class Main {
    public static void main(String[] args) {
        File file = new File("file.txt");
        NumberChecker.checkNumbers(file);
        File file3 = new File("file3.txt");
        WordsCounter.countWords(file3);
        File file2 = new File("file2.txt");
        System.out.println("\n\n");
        JsonFileCreating.readAndCreateJson(file2);

    }

}