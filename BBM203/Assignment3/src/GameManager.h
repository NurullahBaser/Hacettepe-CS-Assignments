#ifndef ASSIGNMENT3_GAMEMANAGER_H
#define ASSIGNMENT3_GAMEMANAGER_H

#include <set>
#include "Cashier.h"
#include "CashierQueue.h"
#include "Customer.h"
#include "BaristaQueue.h"
#include "Barista.h"


using namespace std;

class GameManager {
public:
    int customerCount;
    int cashierCount;
    int baristaCount;

    Cashier *cashierArray;
    Customer *customer;
    CashierQueue cashierQueue;

    set<pair<double,Customer>,CompareByTimeAndPrice> baristaQueueSet;
    BaristaQueue baristaQueue;
    Barista *baristaArray1;
    Barista *baristaArray2;
    BaristaQueue *arrayOfBaristaQueue;

    set<Customer,CompareByID> outputSet1;
    set<Customer,CompareByID> outputSet2;
    double totalRunningTime1;
    double totalRunningTime2;

    ~GameManager();
    void readFile(const char *file); //This function reads the given input file, creates the necessary data according to the input file and starts the application.
    void writeFile(const char *file); //This function takes all the necessary data and prints it to the output file.
    void addCustomers(); //This function sends the newly arrived customer to the cashier's queue. It also calls the function that places the customers who need to enter the cashiers in the cashiers.
    void makeCoffee(); //This function allows you to send customers who have finished work at the cashier to the necessary functions for model 1 and model 2.
    Cashier* findFreeCashier(double); //This function finds the first cashier that will be free
    Barista* findFreeBarista(double); //This function finds the first barista that will be free
    void lookCustomersForOrder(bool isItEnd); //This function sends the customers in the cashier queue to the cashiers who are free.
    void lookCustomersForCoffeeModel1(double); //(FOR MODEL 1) This function sends the customers in the barista queue to the baristas who are free.
    void lookCustomersForCoffeeModel2(double, int); //(FOR MODEL 2) This function sends the customers in the barista queue to the baristas who are free.
    void makeCoffeeWithModel1(Customer); //(FOR MODEL 1) This function sends the customer from the cashier to the barista queue.It also calls the function that places customers who need to go to the barista to the barista.
    void makeCoffeeWithModel2(Customer); //(FOR MODEL 2) This function sends the customer from the cashier to the barista queue.It also calls the function that places customers who need to go to the barista to the barista.
    double findTotalRunningTime(set<Customer, CompareByID>); //This function calculates the total running time for both model.
};


#endif //ASSIGNMENT3_GAMEMANAGER_H
