import sys

with open(sys.argv[1],"r", encoding="utf-8") as f:
    matrix = [line.strip(" \n").split(" ")for line in f]
    score = 0
    balls_score = {"B":9,"G":8,"W":7,"Y":6,"R":5,"P":4,"O":3,"D":2,"F":1,"X":0," ":0}

def print_board():
    global score
    for row in matrix:
        print(" ".join(row))
    print(f"\nYour score is: {score}\n")

def delete_row():
    for row in matrix[::-1]:
        delete = True
        for element in row:
            if element != " ":
                delete = False
        if delete == True:
            matrix.remove(row)

def delete_column():
    column_list = []
    for column_index in range(0,len(matrix[-1])):
        delete = True
        for row_index in range(0,len(matrix)):
            if matrix[row_index][column_index] != " ":
                delete = False
        if delete == True:
            column_list.append(column_index)
    column_list = sorted(column_list, reverse=True)
    for j in column_list:
        for i in range(0,len(matrix)):
            del matrix[i][j]

def gravity():
    while True:
        change = False
        for column_index in range(0,len(matrix[-1])):
            for row_index in range(1,len(matrix)):
                if matrix[row_index][column_index] == " ":
                    if matrix[row_index-1][column_index] != " ":
                        matrix[row_index][column_index] = matrix[row_index-1][column_index]
                        matrix[row_index-1][column_index] = " "
                        change = True
        if change == False:
            break

def control_ball_pop(y,x):
    if matrix[y][x] == " ":
        return 0
    if y < len(matrix)-1:
        if (matrix[y+1][x] == matrix[y][x]):
            return 1
    if y > 0:
        if (matrix[y-1][x] == matrix[y][x]):
            return 1
    if x < len(matrix[-1])-1:
        if (matrix[y][x+1] == matrix[y][x]):
            return 1
    if x > 0:
        if (matrix[y][x-1] == matrix[y][x]):
            return 1

def ball_pop(y,x):
    global score
    chosen_color = matrix[y][x]
    while True:
        finish = True
        if y < len(matrix)-1:
            if matrix[y+1][x] == chosen_color:
                matrix[y][x] = " "
                ball_pop(y+1,x)
                finish = False
        if y > 0:
            if matrix[y-1][x] == chosen_color:
                matrix[y][x] = " "
                ball_pop(y-1,x)
                finish = False
        if x < len(matrix[-1])-1:
            if matrix[y][x+1] == chosen_color:
                matrix[y][x] = " "
                ball_pop(y,x+1)
                finish = False
        if x > 0:
            if matrix[y][x-1] == chosen_color:
                matrix[y][x] = " "
                ball_pop(y,x-1)
                finish = False
        if finish == True:
            matrix[y][x] = " "
            score += balls_score[chosen_color]
            break

def bomb_pop(y,x):
    global score
    popped_list = []
    matrix[y][x] = " "
    for column_index in range(len(matrix[y])):
        popped_list.append(matrix[y][column_index])
        if matrix[y][column_index] == "X":
            bomb_pop(y,column_index)
        matrix[y][column_index] = " "
    for row_index in range(len(matrix)):
        popped_list.append(matrix[row_index][x])
        if matrix[row_index][x] == "X":
            bomb_pop(row_index,x)
        matrix[row_index][x] = " "
    for element in popped_list:
        score += balls_score[element]

def gameover():
    if len(matrix) == 0:
        return 1
    for column_index in range(len(matrix[-1])):
        for row_index in range(len(matrix)):
            if matrix[row_index][column_index] == "X":
                return 0
            if control_ball_pop(row_index,column_index) == 1:
                return 0
    return 1

def main():
    global score
    print_board()
    while True:
        y,x = input("Please enter a row and column number: ").split(" ") # y=row , x=column
        print()
        y,x = int(y),int(x)
        try:
            if matrix[y][x] == "X":
                bomb_pop(y,x)
                gravity()
                delete_column()
                delete_row()
                print_board()
            elif matrix[y][x] == " ":
                print("Please enter a valid size!\n")
            else:
                if control_ball_pop(y,x) == 1:
                    ball_pop(y,x)
                gravity()
                delete_column()
                delete_row()
                print_board()
        except:
            print("Please enter a valid size!\n")
        if gameover() == 1:
            print("Game over!")
            break
main()