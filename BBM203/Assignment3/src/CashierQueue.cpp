#include "CashierQueue.h"
#include "iostream"

CashierQueue::CashierQueue(int size){
    customerQueue = new Customer[size];
    front = 0;
    rear = 0;
    count = 0;
    maxSize = 0;
}

CashierQueue::~CashierQueue() {
    delete[] customerQueue;
}

void CashierQueue::enqueue(Customer *customer) { //This function adds element to queue
    customerQueue[rear] = *customer;
    count++;
    rear++;
    maxSize = max(maxSize, count);
}

Customer CashierQueue::dequeue() { //This function deletes element from queue
    front++;
    count--;
    return customerQueue[front - 1];
}

int CashierQueue::size() {
    return count;
}

bool CashierQueue::isEmpty() {
    if (count == 0) return true;
    else return false;
}

CashierQueue::CashierQueue() {}


Customer CashierQueue::peek() { // This function return the first element of the queue
    return customerQueue[front];
}
