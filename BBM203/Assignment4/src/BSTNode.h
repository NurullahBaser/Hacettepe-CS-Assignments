//
// Created by 90533 on 21.12.2022.
//

#ifndef ASSIGNMENT4_BSTNODE_H
#define ASSIGNMENT4_BSTNODE_H

#include "iostream"
#include "AVLNode.h"
#include "LLRBTNode.h"


using namespace std;

struct BSTNode {
    string name;
    AVLNode* avlNode;
    LLRBTNode* llrbtNode;
    BSTNode* left;
    BSTNode* right;

    BSTNode(string name) : name(name), avlNode(nullptr), llrbtNode(nullptr), left(nullptr), right(nullptr) {}
};



#endif //ASSIGNMENT4_BSTNODE_H
