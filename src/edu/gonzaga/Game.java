package edu.gonzaga;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class drives the turn for the player and adjusts the player's hand.
 *
 * @author Ariana Jansma
 * @version v4.0
 */

public class Game {
    private int numSides;
    /**
     * gameHand represents a player's hand of dice throughout the game
     */
    private PlayerHand gameHand;
    /**
     * numRolls is the number of rolls in the turn
     */
    private int numRolls;

    /**
     * toKeepGoal array that reperesent the goal to keep all die
     */
    ArrayList<Character> toKeepGoal = new ArrayList<>();

    /**
     * Explicit constructor that sets numRolls and creates a PlayerHand object.
     *
     * @param sides number of sides on dice
     * @param numDice number of dice in play
     * @param rollCount number of rolls in game
     */
    public Game(int sides, int numDice, int rollCount){
        numRolls = rollCount;
        numSides = sides;
        gameHand = new PlayerHand(numDice, numSides);
        gameHand.getScore().setSides(numSides);
        gameHand.getScore().createScoreCard();
    }

    /**
     * This method plays a single turn of a player
     *
     * @param toKeep the arraylist of die to keep
     * @param turnNum which turn it is for the player
     * @return an arrayList of the new Die
     */
    public ArrayList<Die> playTurn(ArrayList<Character> toKeep, int turnNum){
        int turn = turnNum;
        //read in scorecard
        gameHand.getScore().readScoreCard();
        //clear cards in case player is playing again
        gameHand.getPossScores().clear();

        //begin turn
        Scanner input = new Scanner(System.in);

        toKeepGoal.clear();
        for(int i = 0; i < gameHand.returnNumDice(); i++){
            toKeepGoal.add('n');
        }

        //run 3 turns or until User wants to keep all of their dice

        if(turn <= numRolls && !(toKeep.equals(toKeepGoal))) {
            for (int i = 0; i < gameHand.returnNumDice(); i++) {
                if (toKeep.get(i).equals('y')) {
                    gameHand.rollDie(i);
                }
            }
        }


        //System.out.print("Your roll was: ");
        //gameHand.outputHand();

            turn++;

        //System.out.println(gameHand.getHand());
        return gameHand.getHand();
    }

    /**
     * Shows player their current scoring options and has them choose a score for this hand of dice
     * Side effects: changes the score card and the score array to show used scores and current scores
     *
     * No params
     * @return buttonArray the array of buttons to represent the possible scores
     */
    public ArrayList<PossibleScoreButton> scoreHand() {
        Scanner input = new Scanner(System.in);
        //update scorecard
        ArrayList<PossibleScoreButton> buttonArray = gameHand.possScore(this.returnSides());

        return buttonArray;
    }

    /**
     * Checks if there are still scoring lines open for the user to choose
     *
     * @return true or false based on if there are lines still open
     */
    public boolean gameOver(){
        //check if more lines are available
        int numOpen= gameHand.getScore().scoreCardOpen();
        if(numOpen > 0){
            //System.out.println(numOpen + " scorecard lines are still available");
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Prints final score and thank you to the console when the game has finished
     */
    public void endGame(){
        //gameHand.getScore().printScorecard();
        //System.out.println("Thank you for playing!");
    }


    /**
     * Returns the number of sides on each die
     *
     * @return num sides from the config object
     */
    public int returnSides(){
        return numSides;
    }

    /**
     * This method returns the gameHand being used in the game
     *
     * @return playerHand that represents the game
     */
    public PlayerHand getGameHand() {
        return gameHand;
    }

    /**
     * This method returns the sorted hand of die
     *
     * @return gameHand.getHand() after the hand has been sorted
     */
    public ArrayList<Die> getSortedGameHand(){
        gameHand.sortHand();
        return gameHand.getHand();
    }

    /**
     * This method returns the toKeepGoal
     *
     * @return toKeepGoal an arraylist of characters
     */
    public ArrayList<Character> getToKeepGoal() {
        return toKeepGoal;
    }
}
