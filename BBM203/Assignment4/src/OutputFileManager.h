#ifndef ASSIGNMENT4_OUTPUTFILEMANAGER_H
#define ASSIGNMENT4_OUTPUTFILEMANAGER_H

#include "iostream"
#include "fstream"
using namespace std;

class OutputFileManager {
public:
    ofstream output1File;
    ofstream output2File;
    string output_name1;
    string output_name2;
    void creatFile(const string& outputName1, const string& outputName2);
    void close();
    void printOutputFiles(string output1, string output2);
};


#endif //ASSIGNMENT4_OUTPUTFILEMANAGER_H
