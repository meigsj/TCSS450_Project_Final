package bnz8.uw.tacoma.edu.caloriemeter.food;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.List;



/**
 * Created by birhanunega on 2/16/17.
 */

public class Food implements Serializable{


    public static final String NAME = "name";
    public static final String CAL_COUNT = "calorie_count";


    private String mName;

    public String getCalorieCount() {
        return mCalorieCount;
    }

    public void setCalorieCount(String CalorieCount) {
        this.mCalorieCount = CalorieCount;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = Name;
    }

    private String mCalorieCount;


    public Food(String name, String calcount) {
        this.mName = name;
        this.mCalorieCount = calcount;
    }


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param courseJSON
     * @return reason or null if successful.
     */
    public static String parseFoodJSON(String courseJSON, List<Food> foodList) {
        String reason = null;
        Log.i("TAG", courseJSON);
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);

                for (int i = 0; i < arr.length(); i++) {
                    Log.e("JSONN", arr.getJSONObject(i).toString());
                    JSONObject obj = arr.getJSONObject(i);
                    Food food = new Food(obj.getString(Food.NAME),obj.getString(Food.CAL_COUNT));
                    foodList.add(food);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

}
