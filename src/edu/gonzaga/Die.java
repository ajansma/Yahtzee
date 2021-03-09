package edu.gonzaga;

import java.util.Random;

/**
 * This class acts as a single Dice object.
 *
 * @author Ariana Jansma
 *  @version v4.0
 */
public class Die {

    /**
     * numSides represents the number of sides on the dice
     */
    private int numSides;
    /**
     * Value is the current value of the dice
     */
    private int value;


    /**
     * Explicit constructor, assigns the numSide and value of dice.
     *
     * @param sides number of sides on the dice
     */

    public Die (int sides){
        numSides = sides;
        value = rollDie();
    }


    /**
     * Default constructor, assigns numSides to 6 and rolls the die.
     * No inputs or returns
     */
    public Die (){
        numSides = 6;
        value = rollDie();
    }

    /**
     * This method rolls the die and assigns a new value to the dice.
     *
     * @return the new value the dice rolled
     */
    public int rollDie(){
        Random randRef = new Random();
        int newSide = randRef.nextInt(numSides) + 1; //[0, 6]
        value = newSide; //remember to set value
        return newSide;
    }

    /**
     * This method returns the value of the dice.
     *
     * @return dice value
     */
    public int getValue() {

        return value;
    }
}
