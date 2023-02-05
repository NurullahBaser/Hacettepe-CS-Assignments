#include "BaristaQueue.h"

BaristaQueue::BaristaQueue() {
    maxSize = 0;
}

int BaristaQueue::size() const {
    return (int) set.size();
}

bool BaristaQueue::isEmpty() {
    return size() == 0;
}

// This function return the first element of the queue
Customer BaristaQueue::peek() {
    return *set.begin();
}

//This function adds element to queue
void BaristaQueue::enqueue(Customer customer) {
    set.insert(customer);
    maxSize = max(maxSize,size());
}

//This function deletes element from queue
Customer BaristaQueue::dequeue() {
    Customer current = peek();
    set.erase(set.begin());
    return current;
}


