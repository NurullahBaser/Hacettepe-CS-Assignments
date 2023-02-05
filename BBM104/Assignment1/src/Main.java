import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws  IOException{

        //For taking people's information
        File filePerson = new File("people.txt");
        Scanner scanPerson = new Scanner(filePerson);
        Person[] people = new Person[48];
        int personIndex = 0;
        while (scanPerson.hasNext()) {
            String[] personArray = scanPerson.nextLine().split("[\t,\n]+");
            people[personIndex] = new Person(personArray[0], personArray[1], personArray[2], Integer.parseInt(personArray[3]), Integer.parseInt(personArray[4]), Integer.parseInt(personArray[5]));
            personIndex += 1;
        }

        //For taking food's information
        File fileFood = new File("food.txt");
        Scanner scanFood = new Scanner(fileFood);
        int foodIndex = 0;
        Food[] foods = new Food[83];
        while (scanFood.hasNext()) {
            String[] foodArray = scanFood.nextLine().split("[\t,\n]+");
            foods[foodIndex] = new Food(foodArray[0],foodArray[1],Integer.parseInt(foodArray[2]));
            foodIndex += 1;
        }

        //For taking sport's information
        File fileSport = new File("sport.txt");
        Scanner scanSport = new Scanner(fileSport);
        int sportIndex = 0;
        Sport[] sports = new Sport[45];
        while (scanSport.hasNext()) {
            String[] sportArray = scanSport.nextLine().split("[\t,\n]+");
            sports[sportIndex] = new Sport(sportArray[0],sportArray[1],Integer.parseInt(sportArray[2]));
            sportIndex += 1;
        }

        //For input and output
        File fileCommand = new File(args[0]);
        Scanner scanCommand = new Scanner(fileCommand);
        File fileOutput = new File("monitoring.txt");
        FileWriter writer = new FileWriter(fileOutput);

        Person[] usedPeople = new Person[48]; // This array holds used people.

        //The loop that reads the command.txt and does its operations.
        boolean printStar = false;
        while (scanCommand.hasNext()) {
            if (!printStar) { // This if statement avoid printing last *****.
                printStar = true;
            }
            else {
                writer.write("***************\n");
            }
            String[] commandArray = scanCommand.nextLine().split("[\t,\n]+");
            if (commandArray[0].charAt(0) == 'p') { // This if statement separates the print commands and others.
                if(commandArray[0].equals("printWarn")) { // This if statement for printWarn.
                    boolean warn = false;
                    for (int i = 0; i < 48; i++) {
                        if(usedPeople[i] != null) {
                            usedPeople[i].calculatingDailyCalorie();
                            double result = (usedPeople[i].gainedCalorie - usedPeople[i].burnedCalorie) - usedPeople[i].dailyCalorie;
                             if (result > 0) {
                                 warn = true;
                                 writer.write(usedPeople[i].name + "\t" + usedPeople[i].age + "\t" + usedPeople[i].dailyCalorie + "kcal\t" + usedPeople[i].gainedCalorie + "kcal\t" + usedPeople[i].burnedCalorie + "kcal\t+" + Math.round(result) + "kcal\n" );
                             }
                        }
                    }
                    if (!warn) {
                        writer.write("there\tis\tno\tsuch\tperson\n");
                    }
                }
                else if (commandArray[0].equals("printList")){ // This if statement for printList.
                    for (int i = 0; i < 48; i++) {
                        if(usedPeople[i] != null) {
                            usedPeople[i].calculatingDailyCalorie();
                            double result = (usedPeople[i].gainedCalorie - usedPeople[i].burnedCalorie) - usedPeople[i].dailyCalorie;
                            String sign = (result>0) ? "+" : "";
                            writer.write(usedPeople[i].name + "\t" + usedPeople[i].age + "\t" + usedPeople[i].dailyCalorie + "kcal\t" + usedPeople[i].gainedCalorie + "kcal\t" + usedPeople[i].burnedCalorie + "kcal\t" + sign + Math.round(result) + "kcal\n" );
                        }
                    }
                }
                else { // This statement for print(personID)
                    String subChosenPersonID = commandArray[0].substring(6,11);
                    int chosenPersonID = Managers.findPosition(people,subChosenPersonID);
                    people[chosenPersonID].calculatingDailyCalorie();
                    double result = (people[chosenPersonID].gainedCalorie - people[chosenPersonID].burnedCalorie) - people[chosenPersonID].dailyCalorie;
                    String sign = (result>0) ? "+" : "";
                    writer.write(people[chosenPersonID].name + "\t" + people[chosenPersonID].age + "\t" + people[chosenPersonID].dailyCalorie + "kcal\t" + people[chosenPersonID].gainedCalorie + "kcal\t" + people[chosenPersonID].burnedCalorie + "kcal\t" + sign + Math.round(result) + "kcal\n" );
                }
            }
            else { //This statement is for eating foods and doing sports.
                int chosenPersonID = Managers.findPosition(people,commandArray[0]);
                people[chosenPersonID].peopleCheckForPrinting(usedPeople);
                if (commandArray[1].charAt(0) == '1') { //This statement is for eating foods.
                    int chosenFoodID = Managers.findPosition(foods,commandArray[1]);
                    int gainedCalorie = Managers.gainCalorie(foods[chosenFoodID],Integer.parseInt(commandArray[2]));
                    people[chosenPersonID].gainedCalorie += gainedCalorie;
                    people[chosenPersonID].calorie += gainedCalorie ;
                    writer.write(people[chosenPersonID].ID +"\thas\ttaken\t"+gainedCalorie+"kcal\tfrom\t"+foods[chosenFoodID].nameOfFood+"\n");

                }
                else { //This statement is for doing sports.
                    int chosenSportID = Managers.findPosition(sports,commandArray[1]);
                    long burnedCalorie = Managers.burnCalorie(sports[chosenSportID],Integer.parseInt(commandArray[2]));
                    people[chosenPersonID].burnedCalorie += burnedCalorie;
                    people[chosenPersonID].calorie -= burnedCalorie;
                    writer.write(people[chosenPersonID].ID +"\thas\tburned\t"+burnedCalorie+"kcal\tthanks\tto\t"+sports[chosenSportID].nameOfSport+"\n");
                }
            }
        }
        writer.close();
    }
}
