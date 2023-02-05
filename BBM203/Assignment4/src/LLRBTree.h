//
// Created by 90533 on 22.12.2022.
//

#ifndef ASSIGNMENT4_LLRBTREE_H
#define ASSIGNMENT4_LLRBTREE_H

#include "LLRBTNode.h"
#include "OutputFileManager.h"

class LLRBTree {
public:
    void insertCall(LLRBTNode*& root, int price, string name);
    void deleteCall(LLRBTNode*& root, string name);
    LLRBTNode* findNode(LLRBTNode* root, string name); //This function finds and returns the node with the given name.
    void printLevelOrder(string& output2, LLRBTNode* root); //This function allows to print the nodes of the tree in order of level.
private:
    bool isRed(LLRBTNode* node);
    LLRBTNode* rotateLeft(LLRBTNode* node); //This function rotates the tree to the left side at the required time.
    LLRBTNode* rotateRight(LLRBTNode* node); //This function rotates the tree to the right side at the required time.
    void flipColors(LLRBTNode* node); //This function changes the color of the node and its children.
    LLRBTNode* insertNode(LLRBTNode* node, int price, string name); //This function adds the given node to the tree where it should be. In doing so, it uses alphabetical order. After inserting, it performs operations that will maintain the balance.
    LLRBTNode* deleteNode(LLRBTNode* node, string name); //This function deletes a node with the given name in the tree. After deleting, it performs operations that will maintain the balance.
    LLRBTNode* balance(LLRBTNode* node); //This function does the necessary operations for the tree to be balanced.
    LLRBTNode* min(LLRBTNode* node); //This function finds the least valuable node after the given node.
    LLRBTNode* deleteMin(LLRBTNode* node); //This function deletes the least valuable node.
    LLRBTNode* moveRedLeft(LLRBTNode* node); //This function changes the color of the node and rotates it to the left.
    LLRBTNode* moveRedRight(LLRBTNode* node); //This function changes the color of the node and rotates it to the right.
};


#endif //ASSIGNMENT4_LLRBTREE_H
