package org.example;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientLog {

    List<String[]> list = new ArrayList<>();
    String[] attributes = "productNum,amount".split(",");


    public void log(int productNum, int amount) {
        list.add(new String[]{String.valueOf(productNum), String.valueOf(amount)});
    }


    public void exportAsCSV(File csvFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            writer.writeNext(attributes);
            writer.writeAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
