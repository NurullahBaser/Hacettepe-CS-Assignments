#include "BinarySearchTree.h"

BinarySearchTree::BinarySearchTree() {
    root = nullptr;
    outputFileManager = new OutputFileManager();
}

BSTNode *BinarySearchTree::insert(string category, string name,int price) {
    root = insertNode(root,category,name,price);
}

//This function adds the given node to the tree where it should be. In doing so, it uses alphabetical order. And it also adds AVLTree's node and LLRBTree's node.
BSTNode *BinarySearchTree::insertNode(BSTNode *node, string category, string name, int price) {
    if (node == nullptr) {
        BSTNode* new_node = new BSTNode(category);
        avlTreeManager.insertCall(new_node->avlNode,price,name);
        llrbTreeManager.insertCall(new_node->llrbtNode,price,name);
        return new_node;
    }
    if (category < node->name) {
        node->left = insertNode(node->left,category,name,price);
    } else if (category > node->name) {
        node->right = insertNode(node->right,category,name,price);
    } else {
        avlTreeManager.insertCall(node->avlNode,price,name);
        llrbTreeManager.insertCall(node->llrbtNode,price,name);
    }
    return node;
}

//This function finds and returns the node with the given name.
BSTNode *BinarySearchTree::findNode(string category) {
    BSTNode* current = root;
    while (current != nullptr) {
        if (category == current->name) {
            return current;
        } else if (category < current->name) {
            current = current->left;
        } else {
            current = current->right;
        }
    }
    return nullptr;

}

//This function deletes the given node in the avl and llrbt trees in the given category.
void BinarySearchTree::deletion(string category, string name) {
    // Find the node in the binary search tree that corresponds to the given category
    BSTNode* node = findNode(category);

    // If the node was found
    if(node != nullptr) {
        // Delete the given name from the AVL tree associated with this node
        avlTreeManager.deleteCall(node->avlNode,name);
        // Delete the given name from the LLRB tree associated with this node
        llrbTreeManager.deleteCall(node->llrbtNode,name);
    }
}


//This function prints the values of the given node.
void BinarySearchTree::printItem(string command, string category, string name) {
    find("printItem",category,name);
}

//This function prints the values of the given node.
void BinarySearchTree::find(string command, string category, string name) {
    BSTNode* node = findNode(category);
    output1 += ("command:"+command+"\t"+category+"\t"+name+"\n");
    output2 += ("command:"+command+"\t"+category+"\t"+name+"\n");
    if(node != nullptr) {
        AVLNode* avlNode = avlTreeManager.findNode(node->avlNode,name);
        if(avlNode != nullptr) {
            output1 += ("{\n\"" + node->name + "\":\n\t\"" + avlNode->name + "\":\"" + to_string(avlNode->price) + "\"\n}\n");
            output2 += ("{\n\"" + node->name + "\":\n\t\"" + avlNode->name + "\":\"" + to_string(avlNode->price) + "\"\n}\n");
        } else {
            output1 += ("{}\n");
            output2 += ("{}\n");
        }
        return;
    }
    output1 += ("{}\n");
    output2 += ("{}\n");
}

//This function prints the information of all nodes in the given category.
void BinarySearchTree::printAllItemsInCategory(string category) {
    BSTNode* node = findNode(category);
    output1 += ("command:printAllItemsInCategory\t"+category+"\n");
    output2 += ("command:printAllItemsInCategory\t"+category+"\n");
    if(node != nullptr) {
        output1 += ("{\n\"" + node->name + "\":");
        output2 += ("{\n\"" + node->name + "\":");
        avlTreeManager.printLevelOrder(output1,node->avlNode);
        llrbTreeManager.printLevelOrder(output2,node->llrbtNode);
        output1 += ("}\n");
        output2 += ("}\n");
        return;
    }
    output1 += ("{}\n");
    output2 += ("{}\n");
}

//This function updates the price of the given node.
void BinarySearchTree::update(string category, string name, int price) {
    BSTNode* node = findNode(category);

    if(node != nullptr) {
        AVLNode* avlNode = avlTreeManager.findNode(node->avlNode,name);
        avlNode->price = price;
        LLRBTNode* llrbtNode = llrbTreeManager.findNode(node->llrbtNode,name);
        llrbtNode->price = price;
    }
}

//This function prints all nodes in the binary search tree and the nodes of all avl trees and llbrt trees within them.
void BinarySearchTree::printAllItems() {
    output1 += "command:printAllItems\n";
    output2 += "command:printAllItems\n";
    if (root == nullptr) {
        output1 += "{}\n";
        output2 += "{}\n";
        return;
    }
    queue<BSTNode*> q;

    q.push(root);
    output1 += "{\n";
    output2 += "{\n";

    while (!q.empty()) {
        BSTNode* node = q.front();
        q.pop();

        output1 += "\""+node->name+"\":";
        output2 += "\""+node->name+"\":";
        avlTreeManager.printLevelOrder(output1,node->avlNode);
        llrbTreeManager.printLevelOrder(output2,node->llrbtNode);

        if (node->left) {
            q.push(node->left);
        }
        if (node->right) {
            q.push(node->right);
        }
    }
    output1 += "}\n";
    output2 += "}\n";
}


void BinarySearchTree::callOutputFile() {
    outputFileManager->printOutputFiles(output1,output2);
}




