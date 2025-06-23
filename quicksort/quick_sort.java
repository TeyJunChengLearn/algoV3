import java.io.*;
import java.util.*;

public class quick_sort {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java QuickSortInt <inputCSV>");
            return;
        }

        String inputFile = args[0];
        List<Integer> numberList = new ArrayList<>();
        List<String> labelList = new ArrayList<>();

        // Read CSV
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    try {
                        int value = Integer.parseInt(parts[0]);
                        numberList.add(value);
                        labelList.add(parts[1]);
                    } catch (NumberFormatException e) {
                        // Ignore invalid lines
                    }
                }
            }
        }

        int size = numberList.size();
        int[] numbers = new int[size];
        String[] labels = new String[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = numberList.get(i);
            labels[i] = labelList.get(i);
        }

        long startTime = System.nanoTime();
        quickSort(numbers, labels, 0, size - 1);
        long endTime = System.nanoTime();

        // Write to output
        String outputFile = "quick_sort_" + size + ".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 0; i < size; i++) {
                writer.write(numbers[i] + "," + labels[i]);
                writer.newLine();
            }
        }

        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Quick Sort completed in %.3f ms%n", durationMs);
        System.out.println("Sorted output saved to: " + outputFile);
    }

    // Tail-recursive quick sort
    public static void quickSort(int[] nums, String[] labels, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(nums, labels, low, high);
            // Recurse into smaller partition to limit stack depth
            if (pivotIndex - low < high - pivotIndex) {
                quickSort(nums, labels, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSort(nums, labels, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] nums, String[] labels, int low, int high) {
        int pivot = nums[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (nums[j] <= pivot) {
                ++i;
                swap(nums, labels, i, j);
            }
        }
        swap(nums, labels, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] nums, String[] labels, int i, int j) {
        int tmpNum = nums[i];
        nums[i] = nums[j];
        nums[j] = tmpNum;

        String tmpLabel = labels[i];
        labels[i] = labels[j];
        labels[j] = tmpLabel;
    }
}
