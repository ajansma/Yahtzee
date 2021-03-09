package edu.gonzaga;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *This class contains an arrayList that holds and compute the players
 * hand.
 *
 * @author Ariana Jansma
 * @version v4.0
 */

public class PlayerHand {

    /**
     * hand represents an array list of dice that acts as the player's hand.
     */
    private ArrayList<Die> hand = new ArrayList<>();
    /**
     * possScores represents the class that computes and holds the possible scores for each turn
     */
    private PossibleScores possScores = new PossibleScores();
    /**
     * score represents the scoreCard object that holds the players scores
     */
    private ScoreCard score = new ScoreCard();


    /**
     * Default constructor, sets number of dice to 5 and
     * name to whitespace.
     */
    public PlayerHand(){
        //fill hand with dice
        for(int i = 0; i < 5; i++) {
            hand.add(new Die());
        }
    }

    /**
     * Explicit Constructor, sets the player name to whitespace and
     * assigns the number of dice to the array.
     *
     * @param numDice the number of dice being used to play the game
     * @param numSides the number of sides on the dice
     */
    public PlayerHand(int numDice, int numSides){

        for(int i = 0; i < numDice; i++) {
            hand.add(new Die(numSides));
        }
    }


    /**
     * Clears the current hand and creates a new hand based on the parameters.
     *
     * Side effects: the hand is completely changes to make the parameters
     *
     * @param numSides new number of sides on each dice
     * @param numDice new number of dice in the hand
     */
    public void resetPlayerHand(int numSides, int numDice){
        hand.clear();
        for(int i = 0; i < numDice; i++){
            hand.add(new Die(numSides));
        }
    }

    /**
     * Sorts the ArrayList of dice into numerical order.
     * Bubble Sort derived from Gaddis chapter 8
     * side effects: the arrayList is changed to be in order
     */
    public void sortHand(){
        boolean swap;
        Die diceSwap;
        do{
            swap = false;
            for(int i = 0; i < (hand.size() - 1); i++){
                if(hand.get(i).getValue() > hand.get(i + 1).getValue()){
                    diceSwap = hand.get(i);
                    hand.set(i, hand.get(i + 1));
                    hand.set((i+1), diceSwap);
                    swap = true;
                }
            }
        } while(swap);
    }

    /**
     * This method prints the Hand to the console.
     * This is accomplished using a for loop.
     */
    public void outputHand(){

        for(int i = 0; i < hand.size(); i++){
            System.out.print(hand.get(i).getValue() + " ");
        }
        System.out.println();
    }

    /**
     * Returns the arrayList of dice.
     *
     * @return an arrayList of dice
     */
    public ArrayList<Die> getHand() {
        return hand;
    }

    /**
     * Return the die value at the index parameter.
     *
     * @param index index of die wanted
     * @return the Die at the index requested
     */
    public Die returnDie(int index){
        return hand.get(index);
    }

    /**
     * Computes the sum of the dice in the playersHand.
     *
     * @return total of the dice in the hand
     */
    public int totalHand(){
        int total = 0;
        for(int i = 0; i < hand.size(); i++){
            total += hand.get(i).getValue();
        }
        return total;
    }

    /**
     * Returns the number of dice in the hand.
     *
     * @return int size of the Hand array
     */
    public int returnNumDice(){

        return hand.size();
    }

    /**
     * Rolls the selected die and returns the new value
     *
     * @param i integer location of the die to be rolled
     * @return new die value
     */
    public int rollDie(int i){
        return hand.get(i).rollDie();
    }

    /**
     * This method finds the possible scores for the current hand
     *
     * @param sides number of sides on each die
     * @return an arrayList representing the possScores that were found
     */
    public ArrayList<PossibleScoreButton> possScore(int sides){

        possScores.setUpperScoreCard(this, sides);
        //setup lower if possible
        if (this.returnNumDice() >= 5) {
            possScores.setLowerScoreCard(this);
        }

        //print potential scores
        return possScores.createPossButtons(this.returnNumDice(), score);
    }

    /**
     * This method updates the scoreCard based on the scoringCode and new num that was inputted by the user
     *
     * @param scoringCode the scoreline code
     * @param scoreNum the new scoreline value
     */
    public void updateScore(String scoringCode, String scoreNum){

        //update ScoreCard
        List<ArrayList<String>> list = possScores.possiblePlaces(score);
        for (ArrayList<String> loop : list) {
            if (loop.get(0).equals(scoringCode)) {
                scoreNum = loop.get(1);
                break;
            }
        }
        score.updateScoreCard(scoringCode, scoreNum);
    }

    /**
     * This method returns the scoreCard object
     *
     * @return score the scoreCard object
     */
    public ScoreCard getScore(){
        return score;
    }

    /**
     * This value returns the PossibleScores object
     *
     * @return possScores the possibleScores object
     */
    public PossibleScores getPossScores(){
        return possScores;
    }
}
