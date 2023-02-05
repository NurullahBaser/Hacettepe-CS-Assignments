import javafx.scene.image.Image;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class DataManager {
    public static HashMap<String,User> users = new HashMap<String,User>();
    public static HashMap<String,Film> films = new HashMap<String,Film>();
    public static HashMap<String,Hall> halls = new HashMap<String,Hall>();

    public static Properties properties = new Properties();

    static String logo = new File("assets\\icons\\logo.png").toURI().toString(); //todo src
    public static Image logoImage = new Image(logo);


    /**
     * This function reads all the data in the backup file and assigns them to the required collections and maps.
     */
    public static void backUpReader() {
        try {
            File data = new File("assets\\data\\backup.dat"); //todo src
            Scanner scan = new Scanner(data);
            while (scan.hasNext()) {
                String[] arr = scan.nextLine().split("\t");
                if (arr[0].equals("user")) {
                    users.put(arr[1], new User(arr[1], arr[2], Boolean.parseBoolean(arr[3]), Boolean.parseBoolean(arr[4])));
                } else if (arr[0].equals("film")) {
                    Film film = new Film(arr[1], arr[2], Integer.parseInt(arr[3]));
                    films.put(film.getName(), film);
                } else if (arr[0].equals("hall")) {
                    Hall hall = new Hall(arr[2], Integer.parseInt(arr[4]), Integer.parseInt(arr[5]), Integer.parseInt(arr[3]), arr[1]);
                    halls.put(hall.getName(), hall);
                    films.get(arr[1]).filmHalls.put(hall.getName(), hall);
                } else if (arr[0].equals("seat")) {
                    Seat seat = new Seat(Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), users.get(arr[5]), Integer.parseInt(arr[6]));
                    halls.get(arr[2]).seats.add(seat);
                }
            }
        } catch (FileNotFoundException e) {
            User admin = new User("admin", hashPassword("password"), true, true);
            users.put("admin", admin);
        }
    }

    /**
     * This function reads the properties file.
     */
    public static void propertiesReader(){
        try {
            FileInputStream fileInputStream = new FileInputStream("assets\\data\\properties.dat"); //todo src
            properties.load(fileInputStream);
        }
        catch (Exception ignored){}
    }


    /**
     * Returns Base64 encoded version of MD5 hashed version of the given password.
     *
     * @param password Password to be hashed.
     * @return Base64 encoded version of MD5 hashed version of password
     */
    public static String hashPassword(String password) {
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest = new byte[0];
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }


    /**
     * This function saves the changes that occur in the system to the backup file.
     * @throws IOException
     */
    public static void backUpWriter() throws IOException {
        File data = new File("assets\\data\\backup.dat"); //todo src
        FileWriter writer = new FileWriter(data);
        try {
            for (User user : users.values()) {
                writer.write("user\t" + user.getName() + "\t" + user.getPassword() + "\t" + user.isClubMember() + "\t" + user.isAdmin() + "\n");
            }
            for (Film film : films.values()) {
                writer.write("film\t" + film.getName() + "\t" + film.getPath() + "\t" + film.getDuration() + "\n");
                for (Hall hall : film.filmHalls.values()) {
                    writer.write("hall\t" + hall.shownFilm + "\t" + hall.getName() + "\t" + hall.getPrice() + "\t" + hall.getRow() + "\t" + hall.getColumn() + "\n");
                    for (Seat seat : hall.seats) {
                        String owner = (seat.getOwner()==null) ? ("null") : (seat.getOwner().getName());
                        writer.write("seat\t" + hall.shownFilm + "\t" + hall.getName() + "\t" + seat.getRow_index() + "\t" + seat.getColumn_index() + "\t" + owner + "\t" + seat.getPrice() + "\n");
                    }
                }
            }
        }
        catch (Exception ignored) {}
        writer.close();
    }
}
