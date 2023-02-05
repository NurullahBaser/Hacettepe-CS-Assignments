#ifndef ASSIGNMENT3_BARISTAQUEUE_H
#define ASSIGNMENT3_BARISTAQUEUE_H


#include <set>
#include "Customer.h"
#include "Comparator.cpp"

using namespace std;

class BaristaQueue {
public:
    set<Customer,CompareByPrice> set;
    int maxSize;

    BaristaQueue();
    void enqueue(Customer); //This function adds element to queue
    Customer dequeue(); //This function deletes element from queue
    int size() const;
    bool isEmpty();
    Customer peek(); // This function return the first element of the queue

};


#endif //ASSIGNMENT3_BARISTAQUEUE_H
