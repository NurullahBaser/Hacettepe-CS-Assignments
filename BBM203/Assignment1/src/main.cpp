#include <iostream>
#include <fstream>

using namespace std;


//global variables
int keyLength;
int mapXLength;
int mapYLength;


class Matrix {
public:
    int row;
    int column;
    int** grid;

    //constructor
    Matrix(int _row, int _column){
        row = _row;
        column = _column;
        grid = (int**) new int*[row];
        for(int i = 0; i < row; i++) {
            grid[i] = (int*) new int*[column];
        }
    }
    //destructor
    ~Matrix() {
        for(int i = 0; i < row; i++) {
            delete[] grid[i];
        }
        delete[] grid;
    }
    
    //this method creates the grid
    void createMatrix(char* fileName) {
        fstream file(fileName,std::ios_base::in);
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column ; j++) {
                file >> grid[i][j];
            }
        }
    }
};

//This function calculate the x and y for mapMatrix
void findSize(string str) {
    string delimiter = "x";
    string row = str.substr(0, str.find(delimiter));
    string column = str.substr(str.find(delimiter)+1, str.size()+1);
    ::mapXLength = stoi(row);
    ::mapYLength = stoi(column);
}

//This function is the search function. It calls its self and finds the treasure.
void findTreasure(Matrix* treasureMatrix, Matrix* keyMatrix, int row, int column) {
    int dotProduct = 0;
    int rowIndex = 0;
    int columnIndex = 0;

    for(int i = row-(keyLength / 2); i < row - (keyLength / 2) + keyLength; i++) {
        for(int j = column-(keyLength / 2); j < column - (keyLength / 2) + keyLength; j++) {
            dotProduct += treasureMatrix->grid[i][j] * keyMatrix->grid[rowIndex][columnIndex];
            columnIndex++;
        }
        rowIndex++;
        columnIndex = 0;
    }
    int modResult = dotProduct%5;
    modResult = (modResult<0) ? modResult+5 : modResult;

    if (dotProduct<0) {
        cout << row << "," << column << ":" << modResult << endl;
    } else {
        cout << row << "," << column << ":" << dotProduct << endl;
    }
    if(modResult==1) {
        if(row - keyLength < 0) findTreasure(treasureMatrix, keyMatrix, row + keyLength, column);
        else findTreasure(treasureMatrix, keyMatrix, row - keyLength, column);
    }else if(modResult==2) {
        if(row + keyLength > treasureMatrix->row) findTreasure(treasureMatrix, keyMatrix, row - keyLength, column);
        else findTreasure(treasureMatrix, keyMatrix, row + keyLength, column);
    }else if(modResult==3) {
        if(column + keyLength > treasureMatrix->column) findTreasure(treasureMatrix, keyMatrix, row, column - keyLength);
        else findTreasure(treasureMatrix, keyMatrix, row, column + keyLength);
    }else if (modResult==4) {
        if(column - keyLength < 0) findTreasure(treasureMatrix, keyMatrix, row, column + keyLength);
        else findTreasure(treasureMatrix, keyMatrix, row, column - keyLength);
    }

}

//This function starts the game and when it's over it deletes the keyMatrix and mapMatrix to avoid memory leaks.
void gameManager(char** argv) {
    findSize(argv[1]);
    Matrix* treasure = new Matrix(::mapXLength, ::mapYLength);
    ::keyLength = stoi(argv[2]);
    Matrix* keyMatrix = new Matrix(::keyLength, ::keyLength);
    treasure->createMatrix(argv[3]);
    keyMatrix->createMatrix(argv[4]);
    int startLoc = keyLength / 2;
    freopen(argv[5],"w",stdout);
    findTreasure(treasure,keyMatrix,startLoc,startLoc);
    delete treasure;
    delete keyMatrix;
}


int main(int argc, char** argv) {
    gameManager(argv);
    return 0;
}
