package edu.gonzaga;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

/**
 * This class acts as a scorecard class by keeping the scores in a list and outputting that array to a .txt file
 *
 * @author Ariana Jansma
 * @version v4.0
 */

public class ScoreCard {
    /**
     * scoreLabels hold the ScoreCardLabels that will be used as visual representation of scorecard
     */
    private ArrayList<ScoreCardLabel> scoreLabels = new ArrayList<>();
    /**
     * cardList acts as the score card for the game
     */
    private List<ArrayList<String>> cardList = new ArrayList<ArrayList<String>>();
    /**
     * number of sides the die in the game has
     */
    private int sideCount;

    /**
     * Constructor, assigns numSides to sideCount
     *
     * @param numSides number of sides in the dice
     */
    public ScoreCard(int numSides){
        sideCount = numSides;
    }

    /**
     * Constructor, assigns numSides to 0
     *
     *
     */
    public ScoreCard(){
        sideCount = 0;
    }


    /**
     * This method create a score card file and writes empty values to it
     *
     * No returns
     * No parameters
     */
    public void createScoreCard(){
        try {
            PrintStream scoreFile = new PrintStream(new File("scorecard.txt"));
            for(int i = 1; i < sideCount + 1; i++){
                scoreFile.print(Integer.toString(i));
                scoreFile.print(",n,u,0\n");
                scoreLabels.add(new ScoreCardLabel(Integer.toString(0), Integer.toString(i)));
            }
            //Lower score section
            scoreFile.print("3K,n,l,0\n");
            scoreLabels.add(new ScoreCardLabel(Integer.toString(0), "3K"));
            scoreFile.print("4K,n,l,0\n");
            scoreLabels.add(new ScoreCardLabel(Integer.toString(0), "4K"));
            scoreFile.print("FH,n,l,0\n");
            scoreLabels.add(new ScoreCardLabel(Integer.toString(0), "FH"));
            scoreFile.print("SS,n,l,0\n");
            scoreLabels.add(new ScoreCardLabel(Integer.toString(0), "SS"));
            scoreFile.print("LS,n,l,0\n");
            scoreLabels.add(new ScoreCardLabel(Integer.toString(0), "LS"));
            scoreFile.print("Y,n,l,0\n");
            scoreLabels.add(new ScoreCardLabel(Integer.toString(0), "Y"));
            scoreFile.print("C,n,l,0\n");
            scoreLabels.add(new ScoreCardLabel(Integer.toString(0), "C"));

            scoreFile.close(); //close file
        } catch(FileNotFoundException e){
            System.out.println("Could not open file for writing");
        }
    }

    /**
     * This method reads in the scorecard txt and updates the cardList
     *
     * No inputs
     * No returns
     */
    public void readScoreCard(){
        cardList.clear();
        try {
            Scanner inFile = null;
            inFile = new Scanner(new File("scorecard.txt"));

            int lineNum = 0;
            while(inFile.hasNextLine() == true){
                lineNum++;
                String line = inFile.nextLine();
                String name = "";
                int pointer = 0;
                while (line.charAt(pointer) != ','){
                    name = (name + line.charAt(pointer));
                    pointer = pointer + 1;
                }
                //System.out.println(name);
                pointer = pointer + 1;
                String used = "";
                while(line.charAt(pointer) != ','){
                    used = used + line.charAt(pointer);
                    pointer = pointer + 1;
                }
                pointer = pointer + 1;
                //System.out.println(used);

                String section = "";
                while(line.charAt(pointer) != ','){

                    section = section + line.charAt(pointer);
                    pointer = pointer + 1;
                }

                pointer = pointer + 1;
                String score = "";
                while(pointer < line.length()){
                    score = score + line.charAt(pointer);
                    pointer = pointer + 1;
                }

                ArrayList<String> lineList = new ArrayList<>();
                lineList.add(name);
                lineList.add(used);
                lineList.add(section);
                lineList.add(score);
                cardList.add(lineList);

            }
        } catch(FileNotFoundException e){
            System.out.println("Could not open file for writing");
        }
    }

    /**
     * This method writes new information to the scorecard.txt file
     *
     * No parameters
     * No returns
     */
    public void writeScorecard(){
        try{
            PrintStream scoreFile = new PrintStream(new File("scorecard.txt"));
            for(int i = 0; i < cardList.size(); i++){
                ArrayList<String> tempArray = cardList.get(i);
                scoreFile.println(tempArray.get(0) + "," + tempArray.get(1) + "," + tempArray.get(2) + "," + tempArray.get(3));
                scoreLabels.get(i).updateScore(tempArray.get(3));
            }
            scoreFile.close();

        } catch(FileNotFoundException e){
            System.out.println("Could not open file for writing");
        }

    }

    /**
     * This method prints the Scorecard to the console
     *
     * No parameters
     * No returns
     */
    public void printScorecard(){
        int total = this.upperScorecard();
        if(sideCount >= 5) {
            this.lowerScoreCard(total);
        }
    }

    /**
     * This method prints the upperScorecard portion of the scorecard
     *
     * @return totalUpper the total score of the upper scorecard
     */
    private int upperScorecard(){
        System.out.println("Line\t\tScore");
        System.out.println("-----------------");
        int upperScore = 0;
        int bonus = 0;
        int totalUpper = 0;
        //Go through score card and find upper score card
        for (ArrayList<String> list: cardList) {
            //If in the upper score card
            if (list.get(2).charAt(0) == 'u') {
                //add to total
                upperScore += Integer.valueOf(list.get(3));
                //print
                System.out.println(list.get(0) + "\t\t\t\t" + list.get(3));
            }
        }
        if(upperScore >= 63){
            bonus = 35;
        }
        //print subtotal, bonus, and upper total
        totalUpper = upperScore + bonus;
        System.out.println("-----------------");
        System.out.println("Sub total\t\t" + upperScore);
        System.out.println("Bonus\t\t\t" + bonus);
        System.out.println("-----------------");
        System.out.println("Upper Total\t\t" + totalUpper);
        return totalUpper;
    }

    /**
     * This method prints the lower scorecard to the console
     *
     * @param total the upperScoreCard total score
     */
    private void lowerScoreCard(int total){
        int totalScoreCard = total;
        int lowerTotal = 0;
        System.out.println();
        //look for lower section
        for(ArrayList<String> list: cardList) {
            //print each lower section
            if (list.get(2).charAt(0) == 'l') {
                lowerTotal += Integer.valueOf(list.get(3));
                System.out.println(list.get(0) + "\t\t\t\t" + list.get(3));
            }
        }
        //Print grand totals
        totalScoreCard += lowerTotal;
        System.out.println("-----------------");
        System.out.println("Lower Total\t\t" + lowerTotal);
        System.out.println("-----------------");
        System.out.println("Grand Total\t\t" + totalScoreCard);
        System.out.println();
    }

    /**
     * This method updates the cardList arraylist and writes the new scores to the scorecard.txt file.
     *
     * @param sectionCode code for the section of the score card to update
     * @param sectionScore new score for the section code
     */
    public void updateScoreCard(String sectionCode, String sectionScore){

        for(ArrayList<String> list: cardList){
            if(list.get(0).equals(sectionCode)){
                //set new section score
                list.set(3, sectionScore);
                //set scorecard to used
                list.set(1, "y");
            }
        }
        writeScorecard(); //update score card
    }

    /**
     * This method returns the array holding the score card.
     *
     * @return cardList array
     */
    public List<ArrayList<String>> returnCardList(){
        return cardList;
    }

    /**
     * This method determines if there are any unused score lines left in the score card
     *
     * @return count, the number of score card lines left
     */
    public int scoreCardOpen(){
        int count = 0;
        for(ArrayList<String> list: cardList){
            if(list.get(1).charAt(0) == 'n'){
                count++;
            }
        }
        return count;
    }

    /**
     * This method sets the side count of the scoreCard
     *
     * @param sides number of sides on each die
     */
    public void setSides(int sides){
        sideCount = sides;
    }

    /**
     * This method adds up the upper totals as well as the bonus
     *
     * @return totalUpper total score of the upper scorecard
     */
    public int getUpperTotal(){
        int upperScore = 0;
        int bonus = 0;
        int totalUpper = 0;
        //Go through score card and find upper score card
        for (ArrayList<String> list: cardList) {
            //If in the upper score card
            if (list.get(2).charAt(0) == 'u') {
                //add to total
                upperScore += Integer.valueOf(list.get(3));
            }
        }
        if(upperScore >= 63){
            bonus = 35;
        }
        //print subtotal, bonus, and upper total
        totalUpper = upperScore + bonus;
        return totalUpper;
    }

    /**
     * This method returns the total score of the lower score card
     *
     * @return lowerTotal sum of lower scorecard scores
     */
    public int getLowerScore(){
        int lowerTotal = 0;
        System.out.println();
        //look for lower section
        for(ArrayList<String> list: cardList) {
            //print each lower section
            if (list.get(2).charAt(0) == 'l') {
                lowerTotal += Integer.valueOf(list.get(3));
            }
        }
        return lowerTotal;
    }

    /**
     * This method returns the arrayList of ScoreCardLabels representing the score card
     *
     * @return scoreLabels the labels that represent the scorecard
     */
    public ArrayList<ScoreCardLabel> getLabels(){
        return scoreLabels;
    }



}
