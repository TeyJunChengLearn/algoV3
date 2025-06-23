import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DatasetGenerator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java DatasetGenerator <size>");
            return;
        }

        int size = Integer.parseInt(args[0]);
        String filename = "dataset_" + size + ".csv";

        generateDataset(size, filename);
    }

    public static void generateDataset(int size, String filename) {
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();

        // Generate unique positive integers (1 to Integer.MAX_VALUE)
        while (uniqueNumbers.size() < size) {
            int num = random.nextInt(Integer.MAX_VALUE - 1) + 1; // avoids 0
            uniqueNumbers.add(num);
        }

        try (FileWriter writer = new FileWriter(filename)) {
            for (int num : uniqueNumbers) {
                String str = generateRandomString(random, 5 + random.nextInt(2)); // 5–6 characters
                writer.write(num + "," + str + "\n");
            }
            System.out.println("Generated dataset saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static String generateRandomString(Random random, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + random.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }
}
