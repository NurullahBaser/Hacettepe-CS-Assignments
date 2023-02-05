#include <iostream>
#include "GameManager.h"

int main(int argc, char** argv) {
    GameManager gameManager(argv);
    gameManager.readInput(argv[1]);
}
