#include "OutputFileManager.h"

void OutputFileManager::creatFile(const string& outputName1, const string& outputName2) {
    output_name1 = outputName1;
    output_name2 = outputName2;
}

void OutputFileManager::close() {
    output1File.close();
    output2File.close();
}

void OutputFileManager::printOutputFiles(string output1, string output2) {
    output1File.open(output_name1,ios::out);
    output2File.open(output_name2,ios::out);
    output1File << output1;
    output2File << output2;
    close();

}
