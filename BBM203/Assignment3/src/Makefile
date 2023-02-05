OBJS	= Barista.o BaristaQueue.o Cashier.o CashierQueue.o Comparator.o Customer.o GameManager.o main.o
SOURCE	= Barista.cpp BaristaQueue.cpp Cashier.cpp CashierQueue.cpp Comparator.cpp Customer.cpp GameManager.cpp main.cpp
HEADER	= Barista.h BaristaQueue.h Cashier.h CashierQueue.h Customer.h GameManager.h
OUT	= Assignment3
CC	 = g++
FLAGS	 = -g -c -Wall
LFLAGS	 =

all: $(OBJS)
	$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)

Barista.o: Barista.cpp
	$(CC) $(FLAGS) Barista.cpp -std=c++11

BaristaQueue.o: BaristaQueue.cpp
	$(CC) $(FLAGS) BaristaQueue.cpp -std=c++11

Cashier.o: Cashier.cpp
	$(CC) $(FLAGS) Cashier.cpp -std=c++11

CashierQueue.o: CashierQueue.cpp
	$(CC) $(FLAGS) CashierQueue.cpp -std=c++11

Comparator.o: Comparator.cpp
	$(CC) $(FLAGS) Comparator.cpp -std=c++11

Customer.o: Customer.cpp
	$(CC) $(FLAGS) Customer.cpp -std=c++11

GameManager.o: GameManager.cpp
	$(CC) $(FLAGS) GameManager.cpp -std=c++11

main.o: main.cpp
	$(CC) $(FLAGS) main.cpp -std=c++11


clean:
	rm -f $(OBJS) $(OUT)