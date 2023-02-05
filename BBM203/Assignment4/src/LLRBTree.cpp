#include <queue>
#include "LLRBTree.h"

bool LLRBTree::isRed(LLRBTNode *node) {
    return node != nullptr && node->is_red;
}

//This function rotates the tree to the left side at the required time.
LLRBTNode *LLRBTree::rotateLeft(LLRBTNode *node) {
    LLRBTNode* node2 = node->right;
    node->right = node2->left;
    node2->left = node;
    node2->is_red = node->is_red;
    node->is_red = true;
    return node2;
}

//This function rotates the tree to the right side at the required time.
LLRBTNode *LLRBTree::rotateRight(LLRBTNode *node) {
    LLRBTNode* node2 = node->left;
    node->left = node2->right;
    node2->right = node;
    node2->is_red = node->is_red;
    node->is_red = true;
    return node2;
}

//This function changes the color of the node and rotates it to the left.
LLRBTNode *LLRBTree::moveRedLeft(LLRBTNode* node) {
    flipColors(node);
    if (isRed(node->right->left)) {
        node->right = rotateRight(node->right);
        node = rotateLeft(node);
        flipColors(node);
    }
    return node;
}

//This function changes the color of the node and rotates it to the right.
LLRBTNode *LLRBTree::moveRedRight(LLRBTNode *node) {
    flipColors(node);
    if (isRed(node->left->left)) {
        node = rotateRight(node);
        flipColors(node);
    }
    return node;
}

//This function changes the color of the node and its children.
void LLRBTree::flipColors(LLRBTNode *node) {
    node->is_red = !node->is_red;
    node->left->is_red = !node->left->is_red;
    node->right->is_red = !node->right->is_red;
}

//This function adds the given node to the tree where it should be. In doing so, it uses alphabetical order. After inserting, it performs operations that will maintain the balance.
LLRBTNode *LLRBTree::insertNode(LLRBTNode *node, int price, string name) {
    if(node == nullptr) return new LLRBTNode(price, name);
    if(name < node->name) node-> left = insertNode(node->left,price,name);
    else if(name > node->name) node->right = insertNode(node->right,price,name);
    else {
        node->name = name;
        node->price = price;
    }
    if(isRed(node->right) && !isRed(node->left)) node = rotateLeft(node);
    if(isRed(node->left) && isRed(node->left->left)) node = rotateRight(node);
    if(isRed(node->right) && isRed(node->left)) flipColors(node);

    return node;
}

void LLRBTree::insertCall(LLRBTNode*& root, int price, string name) {
    root = insertNode(root,price,name);
    root->is_red = false;
}

void LLRBTree::deleteCall(LLRBTNode *&root, string name) {
    if(!isRed(root->left) && !isRed(root->right)) root->is_red = true;
    root = deleteNode(root,name);
    if(root!= nullptr) root->is_red = false;
}
//This function deletes a node with the given name in the tree. After deleting, it performs operations that will maintain the balance.
LLRBTNode *LLRBTree::deleteNode(LLRBTNode *node, string name) {
    if (name < node->name) {
        if (!isRed(node->left) && !isRed(node->left->left)) node = moveRedLeft(node);
        node->left = deleteNode(node->left, name);
    } else {
        if (isRed(node->left)) node = rotateRight(node);
        if (name == node->name && node->right == nullptr) return nullptr;
        if (!isRed(node->right) && !isRed(node->right->left)) node = moveRedRight(node);
        if (name == node->name) {
            LLRBTNode* x = min(node->right);
            node->name = x->name;
            node->right = deleteMin(node->right);
        } else {
            node->right = deleteNode(node->right, name);
        }
    }
    return balance(node);
}

//This function does the necessary operations for the tree to be balanced.
LLRBTNode *LLRBTree::balance(LLRBTNode *node) {
    if(isRed(node->right)) node = rotateLeft(node);
    if(isRed(node->left) && isRed(node->left->left)) node = rotateRight(node);
    if(isRed(node->left) && isRed(node->right)) flipColors(node);
    return node;
}

//This function finds the least valuable node after the given node.
LLRBTNode *LLRBTree::min(LLRBTNode *node) {
    if(node->left == nullptr) return node;
    return min(node->left);
}

//This function deletes the least valuable node.
LLRBTNode *LLRBTree::deleteMin(LLRBTNode *node) {
    if(node->left == nullptr) return nullptr;
    if(!isRed(node->left) && !isRed(node->left->left)) node = moveRedLeft(node);
    node->left = deleteMin(node->left);
    return balance(node);
}

//This function finds and returns the node with the given name.
LLRBTNode* LLRBTree::findNode(LLRBTNode* root, string name) {
    if (root == nullptr || name == root->name) {
        return root;
    }
    if (name < root->name) {
        return findNode(root->left, name);
    } else {
        return findNode(root->right, name);
    }
}

//This function allows to print the nodes of the tree in order of level.
void LLRBTree::printLevelOrder(string& output2 ,LLRBTNode *root) {
    if(root == nullptr) {
        output2 += "{}\n";
        return;
    }
    // Create a queue to hold nodes at each level
    std::queue<LLRBTNode*> q;
    q.push(root);
    int level = 1;

    output2 += "\n";


    // Continue until the queue is empty
    while (!q.empty()) {
        LLRBTNode* current = q.front();
        q.pop();

        // Print the value of the node
        output2 += "\t\""+current->name+"\":\"" + to_string(current->price) + "\"";

        // Push the left and right child nodes onto the queue, if they exist
        if (current->left != nullptr) {
            q.push(current->left);
        }
        if (current->right != nullptr) {
            q.push(current->right);
        }

        level--;

        // If the level count reaches 0, it means we have finished printing the current level
        if (level == 0) {
            // Go to the next line
            output2 += "\n";

            // Set the level count to the number of nodes in the queue (the next level)
            level = q.size();
        }
    }
}

