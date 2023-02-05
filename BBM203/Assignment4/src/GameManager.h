//
// Created by 90533 on 22.12.2022.
//

#ifndef ASSIGNMENT4_GAMEMANAGER_H
#define ASSIGNMENT4_GAMEMANAGER_H

#include <fstream>
#include <string>
#include <iostream>
#include "BinarySearchTree.h"

using namespace std;

class GameManager {
private:
    BinarySearchTree* binarySearchTree;

public:
    void readInput(const string &file); //This function reads the input file and calls the necessary functions to be processed.
    GameManager(char** argv);
    ~GameManager();
};


#endif //ASSIGNMENT4_GAMEMANAGER_H
