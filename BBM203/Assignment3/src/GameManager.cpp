#include <iostream>
#include "GameManager.h"
#include "Customer.h"

//This function reads the given input file, creates the necessary data according to the input file and starts the application.
void GameManager::readFile(const char *file) {
    freopen(file,"r",stdin);
    cin >> cashierCount;
    cashierArray = new Cashier[cashierCount];
    for(int i = 0; i < cashierCount; i++) {
        cashierArray[i] = Cashier(i);
    }
    baristaCount = cashierCount/3;
    cin >> customerCount;
    cashierQueue = CashierQueue(customerCount);
    baristaQueue = BaristaQueue();
    baristaArray1 = new Barista[baristaCount];
    baristaArray2 = new Barista[baristaCount];
    arrayOfBaristaQueue = new BaristaQueue[baristaCount];
    for(int i = 0; i < baristaCount;i++) {
        baristaArray1[i] = Barista(i);
        baristaArray2[i] = Barista(i);
        arrayOfBaristaQueue[i] = BaristaQueue();
    }
    for(int i = 0; i < customerCount ; i++) {
        double arrivalTime,orderTime,brewTime,price;
        cin >> arrivalTime >> orderTime >> brewTime >> price;
        customer = new Customer(arrivalTime,orderTime,brewTime,price,i);
        addCustomers();
    }
    lookCustomersForOrder(true); // Although the new customer will not arrive, this function is called again to terminate the customers waiting in the queue.
    makeCoffee(); //After all the cashier operations are over, the barista operations begin.
}


//This function sends the newly arrived customer to the cashier's queue. It also calls the function that places the customers who need to enter the cashiers in the cashiers.
void GameManager::addCustomers() {
    lookCustomersForOrder(false); // Before the new customer arrived.
    cashierQueue.enqueue(customer);
    lookCustomersForOrder(false); // After the new customer arrived.
}


//This function sends the customers in the cashier queue to the cashiers who are free.
void GameManager::lookCustomersForOrder(bool isItEnd) {
    while(!cashierQueue.isEmpty()) {
        Customer current = cashierQueue.peek();
        Cashier *freeCashier = findFreeCashier(current.arrivalTime);

        if(!isItEnd && customer->arrivalTime < freeCashier->freeTime) { //If no more customer can go to any cashier, the cycle ends.
            break;
        }
        if (customer->arrivalTime >= freeCashier->freeTime) {
            freeCashier->freeTime = max(freeCashier->freeTime,current.arrivalTime) + current.orderTime;
            freeCashier->busyTime += current.orderTime;
            current.cashierID = freeCashier->id;
            baristaQueueSet.insert(make_pair(freeCashier->freeTime,current)); //The customer who has finished work at the cashier is thrown into this set to get into the barista's queue.
            cashierQueue.dequeue();
        }
    }
}


//This function allows you to send customers who have finished work at the cashier to the necessary functions for model 1 and model 2.
void GameManager::makeCoffee() {
    while (!baristaQueueSet.empty()) {
        pair<double,Customer> current = *baristaQueueSet.begin();
        baristaQueueSet.erase(baristaQueueSet.begin());
        current.second.orderIsDoneTime = current.first;
        makeCoffeeWithModel1(current.second); //FOR MODEL 1
        makeCoffeeWithModel2(current.second); // FOR MODEL 2
    }
    lookCustomersForCoffeeModel1(-1); //Although the new customer will not arrive, this function is called again to terminate the customers waiting in the queue.
    for(int i = 0; i < baristaCount; i++) {
        lookCustomersForCoffeeModel2(-1,i); //Although the new customer will not arrive, this function is called again to terminate the customers waiting in the queues.
    }
    totalRunningTime1 = findTotalRunningTime(outputSet1);
    totalRunningTime2 = findTotalRunningTime(outputSet2);
}


//(FOR MODEL 1) This function sends the customer from the cashier to the barista queue.It also calls the function that places customers who need to go to the barista to the barista.
void GameManager::makeCoffeeWithModel1(Customer newCustomer) {
    lookCustomersForCoffeeModel1(newCustomer.orderIsDoneTime);// Before the new customer arrived.
    baristaQueue.enqueue(newCustomer);
    lookCustomersForCoffeeModel1(newCustomer.orderIsDoneTime);// After the new customer arrived.
}


//(FOR MODEL 1) This function sends the customers in the barista queue to the baristas who are free.
void GameManager::lookCustomersForCoffeeModel1(double orderIsDoneTime) {
    while(!baristaQueue.isEmpty()) {
        Customer currentCustomer = baristaQueue.peek();
        Barista *freeBarista = findFreeBarista(currentCustomer.orderIsDoneTime);
        if (orderIsDoneTime != -1 && orderIsDoneTime < freeBarista->freeTime) {
            break;
        }
        freeBarista->freeTime = max(freeBarista->freeTime,currentCustomer.orderIsDoneTime) + currentCustomer.brewTime;
        freeBarista->busyTime += currentCustomer.brewTime;
        currentCustomer.endTime = freeBarista->freeTime;
        outputSet1.insert(currentCustomer);
        baristaQueue.dequeue();
    }
}


//(FOR MODEL 2) This function sends the customer from the cashier to the barista queue.It also calls the function that places customers who need to go to the barista to the barista.
void GameManager::makeCoffeeWithModel2(Customer newCustomer) {
    lookCustomersForCoffeeModel2(newCustomer.orderIsDoneTime, newCustomer.cashierID/3); // Before the new customer arrived.
    arrayOfBaristaQueue[newCustomer.cashierID/3].enqueue(newCustomer);
    lookCustomersForCoffeeModel2(newCustomer.orderIsDoneTime, newCustomer.cashierID/3); // After the new customer arrived.

}


//(FOR MODEL 2) This function sends the customers in the barista queue to the baristas who are free.
void GameManager::lookCustomersForCoffeeModel2(double orderIsDoneTime, int cashierID) {
    while(!arrayOfBaristaQueue[cashierID].isEmpty()) {
        Barista *freeBarista =  &baristaArray2[cashierID];
        Customer currentCustomer = arrayOfBaristaQueue[cashierID].peek();
        if(orderIsDoneTime != -1 && orderIsDoneTime < freeBarista->freeTime) {
            break;
        }
        freeBarista->freeTime = max(freeBarista->freeTime, currentCustomer.orderIsDoneTime) + currentCustomer.brewTime;
        freeBarista->busyTime += currentCustomer.brewTime;
        currentCustomer.endTime = freeBarista->freeTime;
        outputSet2.insert(currentCustomer);
        arrayOfBaristaQueue[cashierID].dequeue();
    }
}


//This function finds the first cashier that will be free
Cashier* GameManager::findFreeCashier(double time) {
    double freeTime = cashierArray[0].freeTime;
    int index = 0;

    if (time >= freeTime) {
        return &cashierArray[0];
    }

    for(int i = 1; i < cashierCount; i++) {
        if(cashierArray[i].freeTime < freeTime) {
            freeTime = cashierArray[i].freeTime;
            index = i;
        }
        if (time >= freeTime) {
            return &cashierArray[index];
        }
    }
    return &cashierArray[index];
}


//This function finds the first barista that will be free
Barista* GameManager::findFreeBarista(double time) {
    double freeTime = baristaArray1[0].freeTime;
    int index = 0;

    if(time >=freeTime) {
        return &baristaArray1[0];
    }

    for(int i = 1; i < baristaCount; i++) {
        if(baristaArray1[i].freeTime < freeTime) {
            freeTime = baristaArray1[i].freeTime;
            index = i;
        }

        if (time >= freeTime) {
            return &baristaArray1[index];
        }
    }
    return &baristaArray1[index];
}


//This function takes all the necessary data and prints it to the output file.
void GameManager::writeFile(const char *file) {
    freopen(file,"w",stdout);
    printf("%.2lf\n", totalRunningTime1);
    printf("%d\n", cashierQueue.maxSize);
    printf("%d\n", baristaQueue.maxSize);
    for(int i = 0; i < cashierCount ; i++) {
         printf("%.2lf\n", cashierArray[i].busyTime/totalRunningTime1);
    }
    for(int i = 0; i < baristaCount ; i++) {
        printf("%.2lf\n", baristaArray1[i].busyTime/totalRunningTime1);
    }
    for(const auto & a : outputSet1) {
        printf("%.2lf\n", a.endTime-a.arrivalTime);
    }

    printf("\n%.2lf\n", totalRunningTime2);
    printf("%d\n", cashierQueue.maxSize);
    for(int i = 0; i < baristaCount; i++) {
        printf("%d\n", arrayOfBaristaQueue[i].maxSize);
    }
    for(int i = 0; i < cashierCount ; i++) {
        printf("%.2lf\n", cashierArray[i].busyTime/totalRunningTime2);
    }
    for(int i = 0; i < baristaCount ; i++) {
        printf("%.2lf\n", baristaArray2[i].busyTime/totalRunningTime2);
    }
    for(const auto & a : outputSet2) {
        printf("%.2lf\n", a.endTime-a.arrivalTime);
    }
}


//This function calculates the total running time for both model.
double GameManager::findTotalRunningTime(set<Customer, CompareByID> set) {
    double result = 0;
    for(const auto & a : set) {
        result = max(result,a.endTime);
    }
    return result;
}


GameManager::~GameManager() {
    delete[] cashierArray;
    delete customer;
    delete[] baristaArray1;
    delete[] baristaArray2;
    delete[] arrayOfBaristaQueue;
}