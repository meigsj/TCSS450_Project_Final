package bnz8.uw.tacoma.edu.caloriemeter;

import org.junit.Test;
import bnz8.uw.tacoma.edu.caloriemeter.food.Food;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


/**
 * A test class to test methods in Food.class
 * Created by birhanunega on 3/10/17.
 */

public class FoodClassTest {

    Food cusFood = new Food("10","Butter","150","30","60","15");

    /**
     * A method to test Food constructor.
     */
    @Test
    public void testFoodConstructor(){
        assertNotNull(new Food("10","Butter","150","30","60","15"));
    }
    @Test
    public void testFoodConstructorNullFoodName() {
        Food food = new Food("10","","150","30","60","15");

        assertNotNull(food.getName());
    }

    /**
     * A method to test the getter method getID.
     */
    @Test
    public void testGetID(){

        assertEquals("10", cusFood.getID());
    }
    /**
     * A method to test the getter method getSugarAmount.
     */
    @Test
    public void testSugarAmount(){

        assertEquals("30", cusFood.getmSugerAmount());
    }
    /**
     * A method to test the getter method getProtienAmount.
     */
    @Test
    public void testGetProteinAmount(){

        assertEquals("60", cusFood.getmProtienAmount());
    }
    /**
     * A method to test the getter method getSodiumAmount.
     */
    @Test
    public void testSodiumAmount(){

        assertEquals("15", cusFood.getmSodiumAmount());
    }
    /**
     * A method to test the getter method getCalorieCount.
     */
    @Test
    public void testGetCalorieCount(){

        assertEquals("150", cusFood.getCalorieCount());
    }

}
