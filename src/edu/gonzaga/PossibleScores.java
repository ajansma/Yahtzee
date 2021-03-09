package edu.gonzaga;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * This class calculates the possible scores for the lower and upper score set.
 *
 * @author Ariana Jansma
 * @version v4.0
 */

public class PossibleScores extends JButton{
    /**
     * upperScoreSet represents the upper set of possible scores
     */
    private ArrayList <Integer> upperScoreSet = new ArrayList();
    /**
     * lowerScoreCard represents the lowerset of possible scores.
     */
    private ArrayList<Integer> lowerScoreCard = new ArrayList<>();
    /**
     * keeps track of if there are enough dice to compute the score card
     */
    private Boolean computeLowerScore;
    /**
     * scoreButtons holds buttons to represent the possible score in the GUI
     */
    private ArrayList<PossibleScoreButton> scoreButtons = new ArrayList<>();

    /**
     * Calculates the scores possible in the upperScoreCard by calling other functions.
     * Fills upperScore card with those possible scores.
     *
     * side effects: upperScoreCard is filled with integers
     *
     * @param diceSides number of sides on the dice
     * @param playHand an arrayList of Dice
     */
    public void setUpperScoreCard(PlayerHand playHand, int diceSides){
        scoreButtons.clear();
        for(int section = 1; section <= diceSides; section++){
            int currCount = 0;
            for(int position = 0; position < playHand.getHand().size(); position++){
                if(playHand.getHand().get(position).getValue() == section){
                    currCount++;
                }
            }
            int score = currCount * section;
            upperScoreSet.add(section - 1, score);
            //scoreButtons.add(new PossibleScoreButton(Integer.toString(section), Integer.toString(score)));

        }
    }

    /**
     * Clears the upperScoreSet between turns.
     *
     */
    public void clear(){
        upperScoreSet.clear();
        lowerScoreCard.clear();
    }

    /**
     * This method calls the print methods in order to print the score options to the console.
     *
     * @param numDice number of dice in play
     * @param cardList arraylist of the scorecard scores
     * @return scoreButton the arrayList of buttons
     */
    public ArrayList<PossibleScoreButton> createPossButtons(int numDice, ScoreCard cardList){
        if(numDice < 5){
            computeLowerScore = false;
        }
        else{
            computeLowerScore = true;
        }
        List<ArrayList<String>> listPossible= new ArrayList<>();
        listPossible = possiblePlaces(cardList); // get possible scores from the scorecards not used
        createUpperButtons(listPossible);
        createLowerButtons(numDice, listPossible);

        return scoreButtons;

    }

    /**
     * Prints the upper part of the score card to the console using a for loop.
     *
     * @param possPlaces arrayList of lines open for the user to choose
     */
     public void createUpperButtons(List<ArrayList<String>> possPlaces){
            //ArrayList<JLabel> labels = new ArrayList<>();
            for(ArrayList<String> list: possPlaces) {
               // System.out.println(list.get(0));
                if(list.get(2).charAt(0) == 'u') {
                        String scoreWords = "Score " + upperScoreSet.get(Integer.valueOf(list.get(0)) - 1) + " on the " + list.get(0) + " line";
                        //System.out.println(scoreWords);
                        scoreButtons.add(new PossibleScoreButton(list.get(0), Integer.toString(upperScoreSet.get(Integer.valueOf(list.get(0)) - 1))));
                }
            }
           // return labels;
    }

    /**
     * Calculates the scores in the lowerScoreCard by calling other methods and
     * adds them to the array lower score card.
     *
     * side effect: fills lowerScoreCard with integers
     *
     * @param playHand the ArrayList of Dice
     */
    public void setLowerScoreCard(PlayerHand playHand){
        //3 of a kind

        if(maxOfAKind(playHand) >= 3){
            int score = playHand.totalHand();
            lowerScoreCard.add(0, score);
            //scoreButtons.add(new PossibleScoreButton("3K", Integer.toString(score)));
        }
        else{
            lowerScoreCard.add(0, 0);
            //scoreButtons.add(new PossibleScoreButton("3K", Integer.toString(0)));
        }
        //4 of a kind
        if(maxOfAKind(playHand) >= 4){
            int score = playHand.totalHand();
            lowerScoreCard.add(1, score);
            //scoreButtons.add(new PossibleScoreButton("4K", Integer.toString(score), playHand));
        }
        else {
            lowerScoreCard.add(1, 0);
            //scoreButtons.add(new PossibleScoreButton("4K", Integer.toString(0), playHand));
        }
        //fullHouse
        if(fullHouseFound(playHand)){
            lowerScoreCard.add(2, 25);
            //scoreButtons.add(new PossibleScoreButton("FH", Integer.toString(25), playHand));
        }
        else{
            lowerScoreCard.add(2, 0);
            //scoreButtons.add(new PossibleScoreButton("FH", Integer.toString(0), playHand));
        }
        //Short Straight
        if(maxStraightFound(playHand) >= 4){
            lowerScoreCard.add(3, 30);
            //scoreButtons.add(new PossibleScoreButton("SS", Integer.toString(30), playHand));
        }
        else{
            lowerScoreCard.add(3, 0);
            //scoreButtons.add(new PossibleScoreButton("SS", Integer.toString(0), playHand));
        }
        //Long straight
        if(maxStraightFound(playHand) >= 5){
            lowerScoreCard.add(4, 40);
            //scoreButtons.add(new PossibleScoreButton("LS", Integer.toString(40), playHand));
        }
        else{
            lowerScoreCard.add(4, 0);
            //scoreButtons.add(new PossibleScoreButton("LS", Integer.toString(0), playHand));
        }
        //Yahtzee
        if(maxOfAKind(playHand) >= 5){
            lowerScoreCard.add(5, 50);
            //scoreButtons.add(new PossibleScoreButton("Y", Integer.toString(50), playHand));
        }
        else{
            lowerScoreCard.add(5, 0);
            //scoreButtons.add(new PossibleScoreButton("Y", Integer.toString(0), playHand));
        }

        //chance
        int total = playHand.totalHand();
        lowerScoreCard.add(6, total);
        //scoreButtons.add(new PossibleScoreButton("C", Integer.toString(total), playHand));
    }

    /**
     * Returns the array to the console.
     *
     * @return the lower score card array
     */
    public ArrayList<Integer> returnArray(){

        return lowerScoreCard;
    }


    /**
     * Computes which number occurs the most, and the amount of times it occurs.
     *
     * @param playHand the PlayerHand object with the arraylist of dice
     * @return the max amount of times a number occurs in the hand
     */
    private int maxOfAKind(PlayerHand playHand){
        int maxCount = 0;
        int currCount;
        for(int dieValue = 1; dieValue <= 6; dieValue++){
            currCount = 0;
            for(int pos = 0; pos < playHand.getHand().size(); pos++){
                if(playHand.getHand().get(pos).getValue() == dieValue){
                    currCount++;
                }
                if(currCount > maxCount){
                    maxCount = currCount;
                }
            }
        }
        return maxCount;
    }


    /**
     * Computes how long a sequence of consecutive numbers occurs.
     *
     * @param playHand ArrayList of dice
     * @return number of numbers in the straight
     */
    private int maxStraightFound(PlayerHand playHand){
        int maxLength = 1;
        int currLength = 1;
        for(int count = 0; count < 4; count++){
            if(playHand.getHand().get(count).getValue() + 1 == playHand.getHand().get(count + 1).getValue()){
                currLength++;
            }
            else if((playHand.getHand().get(count).getValue() + 1) < (playHand.getHand().get(count + 1).getValue())){
                currLength = 1;
            }
            if(currLength > maxLength) {
                maxLength = currLength;
            }
        }
        return maxLength;
    }

    /**
     * Computes if there is a fullHouse in the list of dice, if there is a set of 2 and a set of 3
     * numbers in the hand.
     *
     * @param playHand Arraylist of Dice
     * @return True/False based on whether or not a full house is found
     */
    private static boolean fullHouseFound(PlayerHand playHand){
        boolean foundFH = false;
        boolean found3K = false;
        boolean found2K = false;
        int currCount;

        for(int val = 1; val <= 6; val++){
            currCount = 0;
            for(int pos = 0; pos < 5; pos++){
                if(playHand.getHand().get(pos).getValue() == val){
                    currCount++;
                }
            }
            //found 2 of a kind
            if(currCount == 2) {
                found2K = true;
            }
            //found 3 of a kind
            if(currCount == 3){
                found3K = true;
            }
        }
        //full house
        if(found2K && found3K){
            foundFH = true;
        }

        return foundFH;
    }

    /**
     * Prints the lower part of the score card to the console.
     *
     * @param numDice number of dice in players good
     * @param possPlaces arrayList of open lines left in score card
     */
    public void createLowerButtons(int numDice, List<ArrayList<String>> possPlaces) {
        if(numDice >= 5) {
            for(ArrayList<String> list: possPlaces) {

                if(list.get(2).charAt(0) != 'u') {
                    if(list.get(0).charAt(0) == '3') {
                        //System.out.println("Score " + lowerScoreCard.get(0) + " on the 3K line");
                        scoreButtons.add(new PossibleScoreButton("3K", Integer.toString(lowerScoreCard.get(0))));
                    }
                    else if(list.get(0).charAt(0) == '4') {
                        //System.out.println("Score " + lowerScoreCard.get(1) + " on the 4K line");
                        scoreButtons.add(new PossibleScoreButton("4K", Integer.toString(lowerScoreCard.get(1))));
                    }
                    else if(list.get(0).charAt(0) == 'F') {
                        //System.out.println("Score " + lowerScoreCard.get(2) + " on the FH line");
                        scoreButtons.add(new PossibleScoreButton("FH", Integer.toString(lowerScoreCard.get(2))));
                    }
                    else if(list.get(0).charAt(0) == 'S') {
                        //System.out.println("Score " + lowerScoreCard.get(3) + " on the SS line");
                        scoreButtons.add(new PossibleScoreButton("SS", Integer.toString(lowerScoreCard.get(3))));
                    }
                    else if(list.get(0).charAt(0) == 'L') {
                        //System.out.println("Score " + lowerScoreCard.get(4) + " on the LS line");
                        scoreButtons.add(new PossibleScoreButton("LS", Integer.toString(lowerScoreCard.get(4))));
                    }
                    else if(list.get(0).charAt(0) == 'Y') {
                        //System.out.println("Score " + lowerScoreCard.get(5) + " on the Y line");
                        scoreButtons.add(new PossibleScoreButton("Y", Integer.toString(lowerScoreCard.get(5))));
                    }
                    else if(list.get(0).charAt(0) == 'C') {
                        //System.out.println("Score " + lowerScoreCard.get(6) + " on the C line");
                        scoreButtons.add(new PossibleScoreButton("C", Integer.toString(lowerScoreCard.get(6))));
                    }
                }
            }
        }
        else{
            System.out.println("Not enough dice to compute lower scorecard");
        }

    }

    /**
     * This method determines the lines that are unused in the scorecard
     *
     * @param scoreList list of scoreCard values
     * @return list of possible scoring opportunities
     */
    public List<ArrayList<String>> possiblePlaces(ScoreCard scoreList){
        int i = 0;
        List<ArrayList<String>> returnList = new ArrayList<>();
        for(ArrayList<String>list : scoreList.returnCardList()){
            if(list.get(1).charAt(0) == 'n'){
                ArrayList<String> addList = new ArrayList<>();
                addList.add(list.get(0));
                addList.add(this.getScore(list.get(0)));
                addList.add(list.get(2));
                returnList.add(addList);
            }
        }
        return returnList;
    }

    /**
     * This method computes the possible score for the code given in the parameter.
     *
     * @param code the code for a scorecard line
     * @return string score that goes with the parameter code
     */
    public String getScore(String code){
        //upper score card

        for(int i = 1; i <= upperScoreSet.size(); i++){
            if(code.equals(Integer.toString(i))){
                return Integer.toString(upperScoreSet.get(i - 1));
            }
        }
        //lower score card

        if(computeLowerScore) {
            if (code.charAt(0) == '3') {
                return Integer.toString(lowerScoreCard.get(0));
            } else if (code.charAt(0) == '4') {
                return Integer.toString(lowerScoreCard.get(1));
            } else if (code.charAt(0) == 'F') {
                return Integer.toString(lowerScoreCard.get(2));
            } else if (code.charAt(0) == 'S') {
                return Integer.toString(lowerScoreCard.get(3));
            } else if (code.charAt(0) == 'L') {
                return Integer.toString(lowerScoreCard.get(4));
            } else if (code.charAt(0) == 'Y') {
                return Integer.toString(lowerScoreCard.get(5));
            } else if (code.charAt(0) == 'C') {
                return Integer.toString(lowerScoreCard.get(6));
            }

        }
        return "0";
    }

    /**
     * This method returns the arrayList of PossibleScoreButtons
     *
     * @return scoreButtons buttons that represent the possible score card
     */
    public ArrayList<PossibleScoreButton> getPossButtons(){
        return scoreButtons;
    }
}
