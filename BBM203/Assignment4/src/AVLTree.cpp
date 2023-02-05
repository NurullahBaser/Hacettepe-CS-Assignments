#include <queue>
#include "AVLTree.h"

int AVLTree::height(AVLNode* node) {
    if(node == nullptr) {
        return 0;
    }
    return node->height;
}

//This function rotates the tree to the right side at the required time.
AVLNode *AVLTree::rightRotate(AVLNode *node) {
    AVLNode *first = node->left;
    AVLNode *second = first->right;
    first->right = node;
    node->left = second;
    node->height = max(height(node->left), height(node->right)) + 1;
    first->height = max(height(first->left), height(first->right)) + 1;
    return first;
}

//This function rotates the tree to the left side at the required time.
AVLNode *AVLTree::leftRotate(AVLNode *node) {
    AVLNode *first = node->right;
    AVLNode *second = first->left;
    first->left = node;
    node->right = second;
    node->height = max(height(node->left), height(node->right)) + 1;
    first->height = max(height(first->right), height(first->left)) + 1;
    return first;
}

//This function calculates the balance of the given node.
int AVLTree::getBalanceFactor(AVLNode *node) {
    if (node == nullptr) {
        return 0;
    }
    return height(node->left) - height(node->right);
}

//This function adds the given node to the tree where it should be. In doing so, it uses alphabetical order. After inserting, it performs operations that will maintain the balance.
AVLNode *AVLTree::insertNode(AVLNode *node, string name, int price) {
    if(node == nullptr) {
        return new AVLNode(price,name);
    }
    if(name < node->name) {
        node->left = insertNode(node->left, name, price);
    } else if (name > node->name) {
        node->right = insertNode(node->right, name, price);
    } else {
        return node;
    }

    node->height = 1 + max(height(node->left), height(node->right));
    int balanceFactor = getBalanceFactor(node);
    if(balanceFactor>1) {
        if(name < node->left->name) {
            return rightRotate(node);
        } else if (name > node->left->name) {
            node->left = leftRotate(node->left);
            return rightRotate(node);
        }
    }

    if (balanceFactor < -1) {
        if (name > node->right->name) {
            return leftRotate(node);
        } else if (name < node->right->name) {
            node->right = rightRotate(node->right);
            return leftRotate(node);
        }
    }
    return node;
}


//This function finds the least valuable node after the given node.
AVLNode *AVLTree::minNode(AVLNode* node) {
    AVLNode *current = node;
    while (current->left != nullptr)
        current = current->left;
    return current;
}

//This function deletes a node with the given name in the tree. After deleting, it performs operations that will maintain the balance.
AVLNode *AVLTree::deleteNode(AVLNode* node, string name) {
    if (node == nullptr)
        return node;
    if (name < node->name)
        node->left = deleteNode(node->left, name);
    else if (name > node->name)
        node->right = deleteNode(node->right, name);
    else {
        if ((node->left == nullptr) ||
            (node->right == nullptr)) {
            AVLNode *temp = node->left ? node->left : node->right;
            if (temp == nullptr) {
                temp = node;
                node = nullptr;
            } else
                *node = *temp;
            free(temp);
        } else {
            AVLNode *temp = minNode(node->right);
            node->name = temp->name;
            node->right = deleteNode(node->right,temp->name);
        }
    }

    if (node == nullptr)
        return node;

    node->height = 1 + max(height(node->left),height(node->right));
    int balanceFactor = getBalanceFactor(node);
    if (balanceFactor > 1) {
        if (getBalanceFactor(node->left) >= 0) {
            return rightRotate(node);
        } else {
            node->left = leftRotate(node->left);
            return rightRotate(node);
        }
    }
    if (balanceFactor < -1) {
        if (getBalanceFactor(node->right) <= 0) {
            return leftRotate(node);
        } else {
            node->right = rightRotate(node->right);
            return leftRotate(node);
        }
    }
    return node;
}


void AVLTree::insertCall(AVLNode *&root, int price, string name) {
    root = insertNode(root,name,price);
}

void AVLTree::deleteCall(AVLNode *&root, string name) {
    root = deleteNode(root,name);
}


//This function finds and returns the node with the given name.
AVLNode* AVLTree::findNode(AVLNode* root, string name) {
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
void AVLTree::printLevelOrder(string& output1, AVLNode* root) {
    if(root == nullptr) {
        output1 += "{}\n";
        return;
    }
    // Create a queue to hold nodes at each level
    std::queue<AVLNode*> q;
    q.push(root);
    int level = 1;

    output1 += "\n";

    // Continue until the queue is empty
    while (!q.empty()) {
        AVLNode* current = q.front();
        q.pop();

        // Print the value of the node
        output1 += "\t\""+current->name+"\":\"" + to_string(current->price) + "\"";

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
            output1 += "\n";

            // Set the level count to the number of nodes in the queue (the next level)
            level = q.size();
        }
    }
}

