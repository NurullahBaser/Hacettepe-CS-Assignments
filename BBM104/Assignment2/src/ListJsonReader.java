import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class ListJsonReader{
    public static String[] changeArray = new String[6];  // to keep change items.
    public static String[] communityChestArray = new String[11]; // to keep community chest items.

    public ListJsonReader(){
        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("list.json")){
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray chanceList = (JSONArray) jsonfile.get("chanceList");
            int chanceArrayIndex = 0;
            for(Object i:chanceList){
                changeArray[chanceArrayIndex] = ((String)((JSONObject)i).get("item"));
                chanceArrayIndex += 1;
            }
            JSONArray communityChestList = (JSONArray) jsonfile.get("communityChestList");
            int communityChestArrayIndex = 0;
            for(Object i:communityChestList){
				communityChestArray[communityChestArrayIndex] = ((String)((JSONObject)i).get("item"));
                communityChestArrayIndex += 1;
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
     }
}

