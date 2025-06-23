import java.io.*;
import java.util.*;

public class binary_search {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java BinarySearchTimer <inputCSV>");
            return;
        }

        String filename = args[0];
        List<String[]> dataList = loadAndSortCSV(filename);
        int size = dataList.size();

        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = Integer.parseInt(dataList.get(i)[0]);
        }

        Random rand = new Random();
        long bestTime = Long.MAX_VALUE;
        long worstTime = Long.MIN_VALUE;
        long totalTime = 0;

        for (int i = 0; i < size; i++) {
            int target = values[rand.nextInt(size)];

            long start = System.nanoTime();
            binarySearch(values, target);
            long end = System.nanoTime();

            long duration = end - start;
            totalTime += duration;
            if (duration < bestTime) bestTime = duration;
            if (duration > worstTime) worstTime = duration;
        }

        double averageTime = totalTime / (double) size;

        String outputName = "binary_search_" + size + ".txt";
        try (PrintWriter out = new PrintWriter(outputName)) {
            out.printf("Best: %.3f µs\n", bestTime / 1000.0);
            out.printf("Average: %.3f µs\n", averageTime / 1000.0);
            out.printf("Worst: %.3f µs\n", worstTime / 1000.0);
        }

        System.out.println("Timing results written to " + outputName);
    }

    public static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    public static List<String[]> loadAndSortCSV(String fileName) throws IOException {
        List<String[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) lines.add(parts);
            }
        }
        lines.sort(Comparator.comparingLong(a -> Long.parseLong(a[0])));
        return lines;
    }
}
