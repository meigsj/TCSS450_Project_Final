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

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CAL_COUNT = "cal_count";
    public static final String SUGER_AMOUNT = "suger_grams";
    public static final String PROTIEN_AMOUNT = "protein";
    public static final String SODIUM_AMOUNT = "sodium";




    private String mID;
    private String mName;
    private String mCalorieCount;
    private String mSugerAmount;
    private String mProtienAmount;
    private String mSodiumAmount;

    public String getID() {return mID;}
    public void setID(String mID) {this.mID = mID;}
    public String getmSugerAmount() {return mSugerAmount;}
    public void setmSugerAmount(String mSugerAmount) {this.mSugerAmount = mSugerAmount;}
    public String getmProtienAmount() {return mProtienAmount;}
    public void setmProtienAmount(String mProtienAmount) {this.mProtienAmount = mProtienAmount;}
    public String getmSodiumAmount() {return mSodiumAmount;}
    public void setmSodiumAmount(String mSodiumAmount) {this.mSodiumAmount = mSodiumAmount;}
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

    //Should make some checks for name length, checking if they are all
    public Food(String id,String name, String calCount, String sugerAmount, String protienAmount, String sodiumAmount) {
        this.mID = id;
        this.mName = name;
        this.mCalorieCount = calCount;
        this.mSugerAmount = sugerAmount;
        this.mProtienAmount = protienAmount;
        this.mSodiumAmount = sodiumAmount;
    }


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns food list if success.
     * @param foodJSON
     * @return reason or null if successful.
     */
    public static String parseFoodJSON(String foodJSON, List<Food> foodList) {
        String reason = null;
        JSONObject obj;
        if (foodJSON != null) {
            try {
                JSONArray arr = new JSONArray(foodJSON);
                for (int i = 0; i < arr.length(); i++) {
                    obj = arr.getJSONObject(i);
                    Food food = new Food(obj.getString(Food.ID),obj.getString(Food.NAME),obj.getString(Food.CAL_COUNT),
                            obj.getString(Food.SUGER_AMOUNT),obj.getString(Food.PROTIEN_AMOUNT),
                            obj.getString(Food.SODIUM_AMOUNT));
                    foodList.add(food);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse the data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

}
