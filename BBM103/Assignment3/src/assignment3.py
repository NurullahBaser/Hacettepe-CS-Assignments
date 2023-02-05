import sys

with open(sys.argv[1],"r",encoding= "utf-8") as f:
    dic = {line.split(":")[0].strip(" "):line.split(":")[1].strip(" \n").split(" ") for line in f}
    user_list = [i for i in dic.keys()]

with open(sys.argv[2], "r",encoding= "utf-8") as f:
    command_list = [line.strip(" \n").split(" ") for line in f]

def ANU(name):
    if name in user_list:
        output_file.write("ERROR: Wrong input type! for 'ANU'! -- This user already exists!!\n")
    else:
        dic[name] = []
        user_list.append(name)
        output_file.write(f"User '{name}' has been added to the social network successfully\n")

def DEU(name):
    if name in user_list:
        del dic[name]
        user_list.remove(name)
        for key in dic.keys():
            if name in dic[key]:
                dic[key].remove(name)
        output_file.write(f"User '{name}' and his/her all relations have been deleted successfully\n")
    else:
        output_file.write(f"ERROR: Wrong input type! for 'DEU'! -- There is no user named '{name}'!!\n")

def ANF(name1,name2):
    if name1 not in user_list and name2 in user_list:
        output_file.write(f"ERROR: Wrong input type! for 'ANF'! -- No user named '{name1}' found!!\n")
    elif name2 not in user_list and name1 in user_list:
        output_file.write(f"ERROR: Wrong input type! for 'ANF'! -- No user named '{name2}' found!!\n")
    elif name1 not in user_list and name2 not in user_list:
        output_file.write(f"ERROR: Wrong input type! for 'ANF'! -- No user named '{name1}' and '{name2}' found!\n")
    elif name2 in dic[name1]:
        output_file.write(f"ERROR: A relation between '{name1}' and '{name2}' already exists!!\n")
    else:
        dic[name2].append(name1)
        dic[name1].append(name2)
        output_file.write(f"Relation between '{name1}' and '{name2}' has been added successfully\n")

def DEF(name1,name2):
    if name1 not in user_list and name2 in user_list:
        output_file.write(f"ERROR: Wrong input type! for 'DEF'! -- No user named '{name1}' found!!\n")
    elif name2 not in user_list and name1 in user_list:
        output_file.write(f"ERROR: Wrong input type! for 'DEF'! -- No user named '{name2}' found!!\n")
    elif name1 not in user_list and name2 not in user_list:
        output_file.write(f"ERROR: Wrong input type! for 'DEF'! -- No user named '{name1}' and '{name2}' found!\n")
    elif name2 not in dic[name1]:
        output_file.write(f"ERROR: No relation between '{name1}' and '{name2}' found!!\n")
    else:
        dic[name2].remove(name1)
        dic[name1].remove(name2)
        output_file.write(f"Relation between '{name1}' and '{name2}' has been deleted successfully\n")

def CF(name):
    if name not in user_list:
        output_file.write(f"ERROR: Wrong input type! for 'CF'! -- No user named '{name}' found!\n")
    else:
        output_file.write(f"User '{name}' has {len(dic[name])} friends\n")

def FPF(name,MD):
    possible_friend_list = []
    possible_friend_list2 = []
    possible_friend_list3 = []
    if name not in user_list:
        output_file.write(f"ERROR: Wrong input type! for 'FPF'! -- No user named '{name}' found!\n")
    elif int(MD) > 3 or int(MD) < 1:
        output_file.write(f"ERROR: Maximum distance is out of range!!\n")
    elif dic[name] == []:
        output_file.write(f"User '{name}' has no possible friends!!\n")
    else:
        if int(MD) >= 1:
            possible_friend_list.extend(dic[name])
            possible_friend_list = list(set(possible_friend_list))
        if int(MD) >= 2:
            for i in possible_friend_list:
                possible_friend_list2.extend(dic[i])
            possible_friend_list.extend(possible_friend_list2)
            possible_friend_list = list(set(possible_friend_list))
        if int(MD) == 3:
            for i in possible_friend_list2: 
                possible_friend_list3.extend(dic[i])
            possible_friend_list.extend(possible_friend_list3)
            possible_friend_list = list(set(possible_friend_list))
        for i in possible_friend_list:
            if i == name:
                possible_friend_list.remove(name)
        possible_friend_list.sort()
        output_file.write(f"User '{name}' has {len(possible_friend_list)} possible friends when maximum distance is {MD}\n")
        output_file.write("These possible friends: {'%s'}\n" % "','".join(possible_friend_list))

def SF(name,MD):
    if name not in user_list:
        output_file.write(f"Error: Wrong input type! for 'SF'! -- No user named '{name}' found!!\n")
    elif int(MD) < 2 or int(MD) > 3:
        output_file.write(f"Error: Mutually Degree cannot be less than 2 or greater than 3\n")
    elif name not in user_list and (int(MD) < 2 or int(MD)) > 3:
        output_file.write(f"Error: Wrong input type! for 'SF'! -- No user named '{name}' found and Mutually Degree cannot be less than 2 or greater than 3!!\n")
    else:
        friends = [i for i in dic[name]]
        friends_friends = [j for i in friends for j in dic[i]]
        for i in range(len(friends)):
            friends_friends.remove(name)
        md2 = []
        md3 = []
        for i in friends_friends:
            if friends_friends.count(i) == 2:
                md2.append(i)
            if friends_friends.count(i) == 3:
                md3.append(i)
        md2 = list(set(md2))
        md3 = list(set(md3))
        for i in friends:
            if i in md2:
                md2.remove(i)
            if i in md3:
                md3.remove(i)
        output_file.write(f"Suggestion List for '{name}' (when MD is {MD}):\n")
        if int(MD) == 2:
            if len(md2) > 0:
                output_file.write("'%s' has 2 mutual friends with '%s'\n" % (name,"','".join(sorted(md2))))
            else:
                output_file.write(f"'{name}' has no 2 mutual friends.\n")
            if len(md3) > 0:
                output_file.write("'%s' has 3 mutual friends with '%s'\n" % (name,"','".join(sorted(md3))))
            else:
                output_file.write(f"'{name}' has no 3 mutual friends.\n")
            if len(md2+md3) > 0:
                output_file.write("The suggested friends for '%s':'%s'\n" % (name,"','".join(sorted(md2+md3))))
            else:
                output_file.write(f"There is no suggested friends for '{name}'!!\n")
        elif int(MD) == 3:
            if len(md3) > 0:
                output_file.write("'%s' has 3 mutual friends with '%s'\n" % (name,"','".join(sorted(md3))))
                output_file.write("The suggested friends for '%s':'%s'\n" % (name,"','".join(sorted(md3))))
            else:
                output_file.write(f"'{name}' has no 3 mutual friends.\n")
                output_file.write(f"There is no suggested friends for '{name}'!!\n")

output_file = open("output.txt","w",encoding= "utf-8")
output_file.write("Welcome to Assignment 3\n-------------------------------\n")
def main():
    for command in command_list:
        try:
            if command[0] == "ANU":
                ANU(command[1])
            elif command[0] == "DEU":
                DEU(command[1])
            elif command[0] == "ANF":
                ANF(command[1],command[2])
            elif command[0] == "DEF":
                DEF(command[1],command[2])
            elif command[0] == "CF":
                CF(command[1])
            elif command[0] == "FPF":
                FPF(command[1],command[2])
            elif command[0] == "SF":
                SF(command[1],command[2])
            else:
                output_file.write(f"Error: there is no such command as {command[0]}\n")
        except IndexError:
            output_file.write("Error: The command style is not correct!!\n")
main()
output_file.close()