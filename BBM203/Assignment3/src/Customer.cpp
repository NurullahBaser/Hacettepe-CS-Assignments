#include "Customer.h"

Customer::Customer(double arrival, double order, double brew, double price, int id) {
    this->arrivalTime = arrival;
    this->orderTime = order;
    this->brewTime = brew;
    this->price = price;
    this->customerID = id;
}

Customer::Customer() {}