#include <iostream>
#include <fstream>
#include <sstream>
#include <string>

using namespace std;

struct RowData {
    long int number;
    string label;
};

// Counts total lines in file
int countRows(const string& filename) {
    ifstream file(filename);
    int count = 0;
    string line;
    while (getline(file, line)) {
        if (!line.empty()) ++count;
    }
    return count;
}

// Loads CSV data into a dynamic array
RowData* loadCSV(const string& filename, int& outSize) {
    int total = countRows(filename);
    ifstream file(filename);
    RowData* data = new RowData[total];
    string line;
    int i = 0;

    while (getline(file, line) && i < total) {
        stringstream ss(line);
        string numStr, label;
        getline(ss, numStr, ',');
        getline(ss, label);
        if (!numStr.empty() && !label.empty()) {
            data[i++] = { stol(numStr), label };
        }
    }

    outSize = i;
    return data;
}

// Logs each comparison during binary search
void binarySearchWithSteps(RowData* data, int size, long target, const string& logFile) {
    ofstream out(logFile);
    int left = 0, right = size - 1;
    bool found = false;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        out << mid+1 << ": " << data[mid].number << "/" << data[mid].label << "\n";

        if (data[mid].number == target) {
            found = true;
            break;
        } else if (data[mid].number < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    if (!found) out << "-1\n";
    out.close();
    cout << "Binary search log saved to " << logFile << endl;
}

int main(int argc, char* argv[]) {
    if (argc != 3) {
        cout << "Usage: binary_search_step <inputCSV> <targetNumber>\n";
        return 1;
    }

    string filename = argv[1];
    long target = stoll(argv[2]);
    int totalRows = 0;

    RowData* entries = loadCSV(filename, totalRows);
    if (totalRows == 0) {
        cerr << "No valid entries loaded from file.\n";
        return 1;
    }

    string logFilename = "binary_search_step_" + to_string(target) + ".txt";
    binarySearchWithSteps(entries, totalRows, target, logFilename);

    delete[] entries;
    return 0;
}
