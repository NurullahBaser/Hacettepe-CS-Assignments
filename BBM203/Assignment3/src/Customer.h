#ifndef ASSIGNMENT3_CUSTOMER_H
#define ASSIGNMENT3_CUSTOMER_H


class Customer {
public:
    int customerID;
    double arrivalTime;
    double orderTime;
    double brewTime;
    double price;
    double orderIsDoneTime;
    int cashierID;
    double endTime;

    Customer(double ,double ,double ,double,int);
    Customer();
};


#endif //ASSIGNMENT3_CUSTOMER_H
