#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <chrono>
#include <random>

using namespace std;
using namespace chrono;

struct RowData {
    long int number;
    string label;
};

// Reads the CSV file into a dynamic array
int readCSV(const string& filename, RowData*& data) {
    ifstream file(filename);
    string line;
    int size = 0;

    // First, count rows
    while (getline(file, line)) {
        ++size;
    }
    file.clear();
    file.seekg(0);

    data = new RowData[size];
    int index = 0;

    while (getline(file, line)) {
        stringstream ss(line);
        string numStr, word;
        getline(ss, numStr, ',');
        getline(ss, word);
        data[index++] = { stol(numStr), word };
    }
    file.close();
    return size;
}

// Standard binary search
int binarySearch(RowData* arr, long int target, int size) {
    int left = 0, right = size - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid].number == target) return mid;
        if (arr[mid].number < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        cout << "Usage: binary_search <sortedCSV>\n";
        return 1;
    }

    string inputFile = argv[1];
    RowData* data = nullptr;
    int size = readCSV(inputFile, data);

    if (size == 0) {
        cerr << "Failed to load data from: " << inputFile << endl;
        return 1;
    }

    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dis(0, size - 1);

    long long bestTime = 1e9, worstTime = 0, totalTime = 0;

    for (int i = 0; i < size; ++i) {
        long int target = data[dis(gen)].number;

        auto start = high_resolution_clock::now();
        binarySearch(data, target, size);
        auto end = high_resolution_clock::now();

        auto duration = duration_cast<nanoseconds>(end - start).count();
        totalTime += duration;
        if (duration < bestTime) bestTime = duration;
        if (duration > worstTime) worstTime = duration;
    }

    ofstream out("binary_search_" + to_string(size) + ".txt");
    out << fixed;
    out << "Best: " << bestTime / 1000.0 << " µs\n";
    out << "Average: " << (totalTime / (double)size) / 1000.0 << " µs\n";
    out << "Worst: " << worstTime / 1000.0 << " µs\n";
    out.close();

    cout << "Timing results written to binary_search_" << size << ".txt\n";

    delete[] data;
    return 0;
}
