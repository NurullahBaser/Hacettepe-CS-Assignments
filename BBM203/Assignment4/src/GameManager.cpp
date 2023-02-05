#include <sstream>
#include "GameManager.h"

//This function reads the input file and calls the necessary functions to be processed.
void GameManager::readInput(const string &file) {
    ifstream inputFile(file);
    string line;
    while(getline(inputFile,line,'\n')) {
        istringstream lineStream(line);
        string token;
        string arr[4];
        int i = 0;
        while(getline(lineStream,token,'\t')) {
            arr[i] = token;
            i++;
        }
        if(arr[0] == "insert") {
            binarySearchTree->insert(arr[1], arr[2], stoi(arr[3]));
        } else if (arr[0] == "remove") {
            binarySearchTree->deletion(arr[1],arr[2]);
        } else if (arr[0] == "printAllItems") {
            binarySearchTree->printAllItems();
        } else if (arr[0] == "printAllItemsInCategory") {
            binarySearchTree->printAllItemsInCategory(arr[1]);
        } else if (arr[0] == "printItem") {
            binarySearchTree->printItem("printItem",arr[1],arr[2]);
        } else if (arr[0] == "updateData") {
            binarySearchTree->update(arr[1],arr[2], stoi(arr[3]));
        } else if (arr[0] == "find") {
            binarySearchTree->find("find",arr[1],arr[2]);
        }
    }
    inputFile.close();
    binarySearchTree->callOutputFile();
}

GameManager::GameManager(char** argv) {
    binarySearchTree = new BinarySearchTree();
    binarySearchTree->outputFileManager->creatFile(argv[2], argv[3]);
}

GameManager::~GameManager() {
    delete binarySearchTree;
}
