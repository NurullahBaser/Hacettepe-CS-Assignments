print("""<-----RULES----->
1. BRUSH DOWN
2. BRUSH UP
3. VEHICLE ROTATES RIGHT
4. VEHICLE ROTATES LEFT
5. MOVE UP TO X
6. JUMP
7. REVERSE DIRECTION
8. VIEW MATRIX
0. EXIT""")

question = input("Please enter the commands with a plus sign (+) between them.\n").split("+")

def correct(list):
    for i in list[1:]:
        if i[0] == "5":
            if len(i) == 1 or len(i) == 2:
                return 0
            else:
                if i[1] == "_":
                    continue
                else:
                    return 0
        elif 0 <= int(i[0]) <=8 and len(i) == 1:
            continue
        else:
            return 0
    else:
        return 1

if correct(question) == 0:
    while True:
        question = input("You entered incorrect command. Please try again.\n").split("+")
        if correct(question) == 1:
            break

n = int(question[0])
matrix=[[' ' for i in range(n)] for j in range(n)]
x,y,head,brush = 0,0,0,"up"

def brushdown():
    global brush,x,y
    brush = "down"
    matrix[y][x] = "*"

def brushup():
    global brush
    brush = "up"

def turnright():
    global head
    head = (head-90) % 360

def turnleft():
    global head
    head = (head+90) % 360

def moveup(i):
    global brush,x,y,head
    l = i.split("_")
    a = int(l[-1])
    while a > 0:
        if head == 0:
            x += 1
            if x > n-1:
                x = 0
            if brush == "down":
                matrix[y][x] = "*"
        elif head == 90:
            y -= 1
            if y < 0:
                y = n-1
            if brush == "down":
                matrix[y][x] = "*"
        elif head == 180:
            x -= 1
            if x < 0:
                x = n-1
            if brush == "down":
                matrix[y][x] = "*"
        elif head == 270:
            y += 1
            if y > n-1:
                y = 0
            if brush == "down":
                matrix[y][x] = "*"
        a -= 1

def jump():
    global brush,x,y,head
    brush = "up"
    a = 3
    while a > 0:
        if head == 0:
            x += 1
            if x > n-1:
                x = 0
        elif head == 90:
            y -= 1
            if y < 0:
                y = n-1
        elif head == 180:
            x -= 1
            if x < 0:
                x = n-1
        elif head == 270:
            y += 1
            if y > n-1:
                y = 0
        a -= 1

def reverse():
    global head
    head = (head+180) % 360

def view_matrix():
    frame = ""
    for i in range(n+2):
        frame += "+"
    print(frame)
    for i in range(n):
        print("+"+"".join(matrix[i])+"+")
    print(frame)

def main():
    for i in question[1:]:
        if i == "1":
            brushdown()
        elif i == "2":
            brushup()
        elif i == "3":
            turnright()
        elif i == "4":
            turnleft()
        elif i[0] == "5":
            moveup(i)
        elif i == "6":
            jump()
        elif i == "7":
            reverse()
        elif i == "8":
            view_matrix()
        elif i == "0":
            break

main()