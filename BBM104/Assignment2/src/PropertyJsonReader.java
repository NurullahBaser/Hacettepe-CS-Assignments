import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class PropertyJsonReader {

    public static Square[] squares = new Square[41]; // length of this array is 41 because I want to match id's with indexes. So id 2 = index 2.
    // squares[0] = null but this is not a problem because location never be 0.

    public PropertyJsonReader(){
         JSONParser processor = new JSONParser();
         try (Reader file = new FileReader("property.json")){
             JSONObject jsonfile = (JSONObject) processor.parse(file);
             JSONArray Land = (JSONArray) jsonfile.get("1");
             for(Object i:Land){

                 squares[Integer.parseInt((String)((JSONObject)i).get("id"))] = new Land((String)((JSONObject)i).get("name"),Integer.parseInt((String)((JSONObject)i).get("cost")));

             }
             JSONArray RailRoad = (JSONArray) jsonfile.get("2");
             for(Object i:RailRoad){

                 squares[Integer.parseInt((String)((JSONObject)i).get("id"))] = new Railroads((String)((JSONObject)i).get("name"),Integer.parseInt((String)((JSONObject)i).get("cost")));
             }
			 
             JSONArray Company = (JSONArray) jsonfile.get("3");
             for(Object i:Company){

                 squares[Integer.parseInt((String)((JSONObject)i).get("id"))] = new Company((String)((JSONObject)i).get("name"),Integer.parseInt((String)((JSONObject)i).get("cost")));

             }

             squares[1] = new Go();//s1 = go
             squares[3] = new CommunityChest();//s3 = chest
             squares[5] = new Tax();//s5 = income tax
             squares[8] = new Chance();//s8 = change
             squares[11] = new Jail();//s11 = jail
             squares[18] = new CommunityChest();//s18 = chest
             squares[21] = new FreeParking();//s21 = free parking
             squares[23] = new Chance();//s23 chance
             squares[31] = new GoToJail();//s31 = go to jail
             squares[34] = new CommunityChest();//s34 = chest
             squares[37] = new Chance();//s37 = chance
             squares[39] = new Tax();//s39 = super tax

         } catch (IOException e){
             e.printStackTrace();
         } catch (ParseException e){
             e.printStackTrace();
         }
    }
     //You can add function(s) if you want
}