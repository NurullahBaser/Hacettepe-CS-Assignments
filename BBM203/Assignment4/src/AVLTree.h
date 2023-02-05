//
// Created by 90533 on 21.12.2022.
//

#ifndef ASSIGNMENT4_AVLTREE_H
#define ASSIGNMENT4_AVLTREE_H


#include "AVLNode.h"
#include "OutputFileManager.h"

class AVLTree {
public:
    void insertCall(AVLNode*& root, int price, string name);
    void deleteCall(AVLNode*& root, string name);
    AVLNode* findNode(AVLNode* root, string name); //This function finds and returns the node with the given name.
    void printLevelOrder(string& output1, AVLNode* root); //This function allows to print the nodes of the tree in order of level.
private:
    int height(AVLNode* node);
    AVLNode* rightRotate(AVLNode* node); //This function rotates the tree to the right side at the required time.
    AVLNode* leftRotate(AVLNode* node); //This function rotates the tree to the left side at the required time.
    int getBalanceFactor(AVLNode* node); //This function calculates the balance of the given node.
    AVLNode* insertNode(AVLNode* node, string name, int price); //This function adds the given node to the tree where it should be. In doing so, it uses alphabetical order.After inserting, it performs operations that will maintain the balance.
    AVLNode* minNode(AVLNode* node); //This function finds the least valuable node after the given node.
    AVLNode* deleteNode(AVLNode* node, string name);//This function deletes a node with the given name in the tree. After deleting, it performs operations that will maintain the balance.
};


#endif //ASSIGNMENT4_AVLTREE_H
