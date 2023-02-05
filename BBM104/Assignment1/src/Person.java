public class Person extends  InheritanceClass{
    String name;
    String gender;
    int weight;
    int height;
    int dateOfBirth;
    int age;
    long dailyCalorie;
    int calorie;
    int gainedCalorie;
    int burnedCalorie;

    public Person(String personID, String name, String gender, int weight, int height, int dateOfBirth) {
        this.ID = personID;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        age = 2022 - dateOfBirth;

    }
    public void calculatingDailyCalorie(){
        if (gender.equals("male")) {
            dailyCalorie = Math.round(66 + (13.75*weight) + (5*height) - (6.8*age));
        }
        else {
            dailyCalorie = Math.round(665 + (9.6*weight) + (1.7*height) - (4.7*age));
        }
    }

    public void peopleCheckForPrinting(Person[] array) { //This method adds people to the usedPeople array.
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = this;
                break;
            }
            else if (array[i].name.equals(this.name)) {
                break;
            }
        }
    }
}



