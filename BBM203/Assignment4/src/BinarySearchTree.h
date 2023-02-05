//
// Created by 90533 on 21.12.2022.
//

#ifndef ASSIGNMENT4_BINARYSEARCHTREE_H
#define ASSIGNMENT4_BINARYSEARCHTREE_H

#include "BSTNode.h"
#include "AVLTree.h"
#include "LLRBTree.h"
#include "OutputFileManager.h"
#include "queue"


class BinarySearchTree {
public:
    AVLTree avlTreeManager;
    LLRBTree llrbTreeManager;
    OutputFileManager* outputFileManager;
    string output1;
    string output2;

    BSTNode* root;
    BinarySearchTree();

    BSTNode* insert(string category,string name,int price);
    BSTNode* insertNode(BSTNode* node, string category, string name, int price); //This function adds the given node to the tree where it should be. In doing so, it uses alphabetical order. And it also adds AVLTree's node and LLRBTree's node.
    BSTNode* findNode(string category); //This function finds and returns the node with the given name.
    void deletion(string category, string name); //This function deletes the given node in the avl and llrbt trees in the given category.
    void printItem(string command, string category,string name); //This function prints the values of the given node.
    void find(string command, string category, string name); //This function prints the values of the given node.
    void printAllItemsInCategory(string category); //This function prints the information of all nodes in the given category.
    void update(string category, string name, int price); //This function updates the price of the given node.
    void printAllItems(); //This function prints all nodes in the binary search tree and the nodes of all avl trees and llbrt trees within them.
    void callOutputFile();

};


#endif //ASSIGNMENT4_BINARYSEARCHTREE_H
