#include <iostream>
#include <sstream>
#include <string>
#include <fstream>

using namespace std;

//Node for Flat
struct Flat {
    int id;
    int initialBW;
    int isEmpty;
    Flat* next;
    Flat* prev;
};

//Node for Apartment
struct Apartment {
    string name;
    int maxBW;
    int sumOfBW;
    Flat* flatList;
    Apartment* next;
};


class Street {
private:
    Apartment* head;

    //This method removes all flats in the given apartment.
    void removeFlats(Apartment* ap) {
        Flat* first = ap->flatList;

        while(first != nullptr) {
            Flat* second = first->next;
            delete first;
            first = second;
        }
    }

    //This method finds flat by the given id. It needs the first flat in the apartment.
    Flat* findFlatByID(int id, Flat* curr) {
        if (curr == nullptr) {
            return nullptr;
        }
        do {
            if(curr->id == id) {
                return curr;
            }
            curr = curr->next;
        } while(curr != nullptr);
        return nullptr;
    }

public:

    ~Street() {
        Apartment* current = head;
        if(current != nullptr) {
            do {
                removeApartment(current->name);
                current = head;
            } while (current != nullptr);
        }
    }

    //This method adds apartment to given position. It can be head or after an apartment or before an apartment.
    void addApartment(string name, string position, int maxBW) {
        Apartment* temp = new Apartment;
        temp->name = name;
        temp->maxBW = maxBW;
        temp->sumOfBW = 0;
        temp->flatList = nullptr;

        if(position == "head") {
            head = temp;
            head->next = head;
        }else {
            string pos = position.substr(0, position.find('_'));
            string aName = position.substr(position.find('_')+1, position.size()+1);
            if(pos == "after") {
                Apartment* current = head;
                while (current->name != aName) current = current->next;
                temp->next = current->next;
                current->next = temp;

            } else if (pos == "before") {
                if(aName == head->name) { //Change the head because given position is before the head.
                    Apartment* last = head;
                    while((last->next)->name != head->name) last = last->next;
                    last->next = temp;
                    temp->next = head;
                    head = temp;
                }
                else {
                    Apartment* current = head;
                    while((current->next)->name != aName) current = current->next;
                    temp->next = current->next;
                    current->next = temp;
                }
            }
        }
    }

    //This method adds flat to given apartment and index.
    void addFlat(string apartmentName, int index, int initialBW, int id) {
        Apartment* currAp = head;
        while (currAp->name != apartmentName) {
            currAp = currAp->next;
        }
        Flat* newFlat = new Flat;
        newFlat->id = id;
        newFlat->initialBW = min(currAp->maxBW - currAp->sumOfBW, initialBW);
        newFlat->isEmpty = (newFlat->initialBW == 0) ? 1 : 0;
        currAp->sumOfBW += newFlat->initialBW;

        if (currAp->flatList == nullptr && index == 0) { //For the first flat to given apartment.
            currAp->flatList = newFlat;
            newFlat->next = nullptr;
            newFlat->prev = nullptr;
        } else if (index == 0) { //It is not the first flat for the given apartment.
            (currAp->flatList)->prev = newFlat;
            newFlat->next = currAp->flatList;
            newFlat->prev = nullptr;
            currAp->flatList = newFlat;
        } else { //If index is bigger than 0.
            Flat* currFlat = currAp->flatList;
            for(int i = 0; i < index-1;i++) {
                currFlat = currFlat->next;
            }
            if(currFlat->next== nullptr) { //For the last index.
                currFlat->next = newFlat;
                newFlat->prev = currFlat;
                newFlat->next = nullptr;
            } else { // For the middle of the flatlist.
                (currFlat->next)->prev = newFlat;
                newFlat->next = currFlat->next;
                newFlat->prev = currFlat;
                currFlat->next = newFlat;
            }
        }
    }

    //This method removes the given apartment and all flats of the apartment.
    Apartment* removeApartment(string name) {
        Apartment* beforeDel = head;
        while((beforeDel->next)->name != name) beforeDel = beforeDel->next;

        Apartment* current = beforeDel->next;

        if(beforeDel==current) {
            removeFlats(current);
            delete current;
            head = nullptr;
            return head;
        }

        if(current == head) {
            head = head->next;
        }
        removeFlats(current);
        beforeDel->next = current->next;
        delete current;
        return head;
    }

    //This method makes the value of the given flat 0.
    void makeFlatEmpty(string name, int id) {
        Apartment* currAp = head;
        while(currAp->name!=name) {
            currAp = currAp->next;
        }
        Flat* currFlat = currAp->flatList;
        while (currFlat->id != id) {
            currFlat = currFlat->next;
        }
        currAp->sumOfBW -= currFlat->initialBW;
        currFlat->initialBW = 0;
        currFlat->isEmpty = 1;
    }

    //This method calculates the sum of the max BW in the whole street.
    void findSumOfMaxBW() {
        Apartment* current = head;
        if(current != nullptr) {
            int result = current->maxBW;
            while (current->next!=head) {
                current = current->next;
                result += current->maxBW;
            }
            cout << "sum of bandwidth: " << result << "\n\n";
        }else {
            cout << "sum of bandwidth: " << 0 << "\n\n";
        }
    }

    //This method merges two apartments.
    void mergeTwoApartments(string name1, string name2) {
        Apartment* first = head;
        Apartment* second = head;
        while (first->name != name2) {
            first = first->next;
        }
        while (second->name != name1) {
            second = second->next;
        }

        if (first->flatList != nullptr && second->flatList != nullptr) {
            Flat* last = second->flatList;
            while(last->next != nullptr) last = last->next;
            last->next = first->flatList;
            (first->flatList)->prev = last;
        } else if (first->flatList != nullptr && second->flatList == nullptr) {
            second->flatList = first->flatList;
        }
        first->flatList = nullptr;
        second->maxBW += first->maxBW;
        second->sumOfBW += first->sumOfBW;
        removeApartment(name2);
    }

    //This method lists apartments and their flats with max_bandwidth value and initial_bandwidth values, respectively.
    void listApartment() {
        Apartment* current = head;
        if(current != nullptr) {
            do {
                cout << current->name << '(' << current->maxBW << ')' << '\t';
                Flat* currentFlat = current->flatList;
                while (currentFlat != nullptr) {
                    cout << "Flat" << currentFlat->id << '(' << currentFlat->initialBW << ')' << '\t';
                    currentFlat = currentFlat->next;
                }
                cout << '\n';
                current = current->next;
            } while (current != head);
        }
        cout << "\n";
    }

    //This method relocates the different flats in different apartments to a specific place at the same apartment consecutively
    void relocateFlats(string name, int id, string list) {
        Apartment* currentApartment = head;
        while (currentApartment->name != name) currentApartment = currentApartment->next;
        Flat* currentFlat = findFlatByID(id, currentApartment->flatList);

        list = list.substr(1,list.length()-2);
        stringstream stream(list);
        while(stream.good()) {
            string num;
            getline(stream,num,',');
            int firstID = stoi(num);
            Apartment* apartmentToAdd = head;
            if(head != nullptr) {
                do {
                    Flat* flatToAdd = findFlatByID(firstID, apartmentToAdd->flatList);
                    if(flatToAdd != nullptr) {
                        if(flatToAdd == apartmentToAdd->flatList) {
                            apartmentToAdd->flatList = flatToAdd->next;
                            (apartmentToAdd->flatList)->prev = nullptr;
                        } else if (flatToAdd->next != nullptr) {
                            flatToAdd->next->prev = flatToAdd->prev;
                            flatToAdd->prev->next = flatToAdd->next;
                        } else {
                            flatToAdd->prev->next = nullptr;
                        }

                        apartmentToAdd->maxBW -= flatToAdd->initialBW;
                        apartmentToAdd->sumOfBW -= flatToAdd->initialBW;
                        currentApartment->maxBW += flatToAdd->initialBW;
                        currentApartment->sumOfBW += flatToAdd->initialBW;

                        flatToAdd->prev = currentFlat->prev;
                        currentFlat->prev = flatToAdd;
                        flatToAdd->next = currentFlat;

                        if(currentFlat == currentApartment->flatList) {
                            currentApartment->flatList = flatToAdd;
                        } else {
                            ((currentFlat->prev)->prev)->next = flatToAdd;
                        }
                        break;
                    }else {
                        apartmentToAdd = apartmentToAdd->next;
                    }

                } while(apartmentToAdd != head);
            }
        }
    }
};


class GameManager {
public:

    ifstream input;
    Street* street;

    GameManager() {
        street = new Street();
    }

    ~GameManager() {
        delete street;
    }

    //This method starts the reading argument.
    void gameStart(char** argv) {
        freopen(argv[2],"w",stdout);
        input.open(argv[1],ios::in);
        if(input.is_open()) {
            string command;
            while(input >> command) {
                readCommandLine(command);
            }
            input.close();
        }
    }

private:
    //This method looks the given command and calls the necessary method.
    void readCommandLine(string command) {
        if(command == "add_apartment") {
            call_add_apartment();
        } else if (command == "add_flat") {
            call_add_flat();
        } else if (command == "remove_apartment") {
            call_remove_apartment();
        } else if (command == "merge_two_apartments") {
            call_merge_two_apartments();
        } else if (command == "find_sum_of_max_bandwidths") {
            call_find_sum_of_max_bandwidths();
        } else if (command == "list_apartments") {
            call_list_apartments();
        } else if (command == "make_flat_empty") {
            call_make_flat_empty();
        } else if (command == "relocate_flats_to_same_apartment") {
            call_relocate_flats_to_same_apartment();
        }
    }

    //These methods take the data in the input file and execute the necessary functions.
    void call_add_apartment() {
        string name;
        string position;
        int maxWB;
        input >> name >> position >> maxWB;
        street->addApartment(name,position,maxWB);
    }
    void call_add_flat() {
        string apartmentName;
        int index;
        int initialBW;
        int id;
        input >> apartmentName >> index >> initialBW >> id;
        street->addFlat(apartmentName,index,initialBW,id);
    }
    void call_remove_apartment() {
        string name;
        input >> name;
        street->removeApartment(name);
    }
    void call_merge_two_apartments() {
        string name1;
        string name2;
        input >> name1 >> name2;
        street->mergeTwoApartments(name1,name2);
    }
    void call_find_sum_of_max_bandwidths() const {
        street->findSumOfMaxBW();
    }
    void call_list_apartments() const {
        street->listApartment();
    }
    void call_make_flat_empty() {
        string name;
        int id;
        input >> name >> id;
        street->makeFlatEmpty(name,id);
    }
    void call_relocate_flats_to_same_apartment() {
        string name;
        int id;
        string list;
        input >> name >> id >> list;
        street->relocateFlats(name,id,list);
    }
};


int main(int argc, char** argv) {
    GameManager* gameManager = new GameManager();
    gameManager->gameStart(argv);
    delete gameManager;
}