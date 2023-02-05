#include "Customer.h"
#include "utility"

using namespace std;

//This struct sorts customers by time, if their time is equal, by price. (From small to large in time, from large to small in price)
struct CompareByTimeAndPrice {
    bool operator() (pair<double,Customer> a, pair<double,Customer> b) const {
        if (a.first != b.first) {
            return a.first < b.first;
        } else {
            return a.second.price > b.second.price;
        }
    }
};

//This struct sorts customers by price.
struct CompareByPrice {
    bool operator() (Customer a, Customer b) const {
        return a.price > b.price;
    }
};

//This struct sorts customers by ID.
struct CompareByID {
    bool operator() (Customer a, Customer b) const {
        return a.customerID < b.customerID;
    }
};