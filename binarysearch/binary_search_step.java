import java.io.*;
import java.util.*;

public class binary_search_step {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java BinarySearchWithTrace <sortedCSV> <targetNumber>");
            return;
        }

        String csvFile = args[0];
        int targetNumber = Integer.parseInt(args[1]);
        String outputFile = "binary_search_step_" + targetNumber + ".txt";

        try {
            List<String[]> entries = readCsvIntoList(csvFile);
            traceBinarySearch(entries, targetNumber, outputFile);
        } catch (IOException e) {
            System.out.println("Failed to process file: " + e.getMessage());
        }
    }

    /**
     * Loads the sorted CSV into a list of String arrays: [number, label]
     */
    public static List<String[]> readCsvIntoList(String filePath) throws IOException {
        List<String[]> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String row;

        while ((row = reader.readLine()) != null) {
            String[] parts = row.split(",", 2); // expect two columns
            if (parts.length == 2) {
                lines.add(parts);
            }
        }

        reader.close();
        return lines;
    }

    /**
     * Performs binary search and logs each inspected index to a file.
     */
    public static void traceBinarySearch(List<String[]> data, int target, String outputPath) throws IOException {
        BufferedWriter logger = new BufferedWriter(new FileWriter(outputPath));
        int left = 0;
        int right = data.size() - 1;
        boolean found = false;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String[] row = data.get(mid);
            int current = Integer.parseInt(row[0]);

            // Write current check to log
            logger.write(mid+1 + ": " + row[0] + "/" + row[1]);
            logger.newLine();

            if (current == target) {
                found = true;
                break;
            } else if (current < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if (!found) {
            logger.write("-1");
            logger.newLine();
        }

        logger.close();
        System.out.println("Search log written to " + outputPath);
    }
}
