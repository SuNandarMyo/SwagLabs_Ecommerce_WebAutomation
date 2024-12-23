package CSVFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming CSV fields are separated by commas
                String[] fields = line.split(",");
                data.add(fields);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
