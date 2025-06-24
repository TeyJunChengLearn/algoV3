import java.io.*;

public class CheckCSVSorted {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java CheckCSVSorted <filename>");
            return;
        }

        String file = args[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            long prev = Long.MIN_VALUE;
            long lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                long current = Long.parseLong(parts[0]);

                if (current < prev) {
                    System.out.println("Not sorted at line " + lineNumber + ": " + current + " < " + prev);
                    return;
                }

                prev = current;
                lineNumber++;
            }

            System.out.println("File is sorted by the first column.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
