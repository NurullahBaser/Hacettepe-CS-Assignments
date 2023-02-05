#ifndef ASSIGNMENT3_CASHIERQUEUE_H
#define ASSIGNMENT3_CASHIERQUEUE_H

#include "Customer.h"

using namespace std;

class CashierQueue {
public:
    Customer *customerQueue;
    int front;
    int rear;
    int count;
    int maxSize;

    CashierQueue(int size);
    CashierQueue();
    ~CashierQueue();

    void enqueue(Customer *customer); //This function adds element to queue
    Customer dequeue(); //This function deletes element from queue
    int size();
    bool isEmpty();
    Customer peek(); // This function return the first element of the queue

};


#endif //ASSIGNMENT3_CASHIERQUEUE_H
