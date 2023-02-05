//
// Created by 90533 on 21.12.2022.
//

#ifndef ASSIGNMENT4_LLRBTNODE_H
#define ASSIGNMENT4_LLRBTNODE_H

#include "iostream"
using namespace std;

struct LLRBTNode {
    int price;
    string name;
    bool is_red;
    LLRBTNode* left;
    LLRBTNode* right;

    LLRBTNode(int price, string name) : price(price) , is_red(true) , name(name) , left(nullptr) , right(nullptr) {}
};


#endif //ASSIGNMENT4_LLRBTNODE_H
