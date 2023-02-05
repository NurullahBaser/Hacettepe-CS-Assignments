OBJS	= AVLTree.o BinarySearchTree.o GameManager.o LLRBTree.o main.o OutputFileManager.o
SOURCE	= AVLTree.cpp BinarySearchTree.cpp GameManager.cpp LLRBTree.cpp main.cpp OutputFileManager.cpp
HEADER	= AVLNode.h AVLTree.h BinarySearcTree.h BSTNode.h GameManager.h LLRBTNode.h LLRBTree.h OutputFileManager.h
OUT	= webstore
CC	 = g++
FLAGS	 = -g -c -Wall
LFLAGS	 =

all: $(OBJS)
	$(CC) -g $(OBJS) -o $(OUT) $(LFLAGS)

AVLTree.o: AVLTree.cpp
	$(CC) $(FLAGS) AVLTree.cpp -std=c++11

BinarySearchTree.o: BinarySearchTree.cpp
	$(CC) $(FLAGS) BinarySearchTree.cpp -std=c++11

GameManager.o: GameManager.cpp
	$(CC) $(FLAGS) GameManager.cpp -std=c++11

LLRBTree.o: LLRBTree.cpp
	$(CC) $(FLAGS) LLRBTree.cpp -std=c++11

main.o: main.cpp
	$(CC) $(FLAGS) main.cpp -std=c++11

OutputFileManager.o: OutputFileManager.cpp
	$(CC) $(FLAGS) OutputFileManager.cpp -std=c++11


clean:
	rm -f $(OBJS) $(OUT)