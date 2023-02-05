#include <iostream>

#include "GameManager.h"

int main(int argc, char** argv) {
    GameManager gameManager = GameManager();
    gameManager.readFile(argv[1]);
    gameManager.writeFile(argv[2]);
}
