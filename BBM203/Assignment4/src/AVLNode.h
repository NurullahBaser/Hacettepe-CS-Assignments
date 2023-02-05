#ifndef ASSIGNMENT4_AVLNODE_H
#define ASSIGNMENT4_AVLNODE_H

#include "iostream"
using namespace std;

struct AVLNode {
    int height;
    int price;
    string name;

    AVLNode* left;
    AVLNode* right;

    AVLNode(int price, string name) : price(price) , name(name) , height(1) , left(nullptr) , right(nullptr) {}

};




#endif //ASSIGNMENT4_AVLNODE_H
