package edu.gonzaga;

import javax.swing.*;

/**
 * This class is a label that contains text for a scorecard line
 *
 * @author Ariana Jansma
 * @version v4.0
 */

public class ScoreCardLabel extends JLabel {
    /**
     * scoreNum keeps track of the score for the label
     */
    private String scoreNum;
    /**
     * scoreCord holds the string code of the score line
     */
    private String scoreCode;
    /**
     * String text hold the text that the JLabel should display
     */
    private String text;
    /**
     * scoreLabel is the actual label object of the class
     */
    private JLabel scoreLabel = new JLabel();

    /**
     * This constructor sets the text for the JLabel using the num and code provided
     *
     * @param num The num that the score line has
     * @param code The code for the score line
     */
    public ScoreCardLabel(String num, String code){
        scoreNum = num;
        scoreCode = code;
        if(scoreCode == "Y" || scoreCode == "C") {
            text = scoreCode + "                         " + scoreNum;
        }
        else{
            text = scoreCode + "                        " + scoreNum;
        }
        //scoreLabel.setBounds(0,100,100,30);
        scoreLabel.setText("Running a test");
    }


    /**
     * This method returns the score code of the class
     *
     * @return scoreCode String code of the score line
     */
    public String returnScoreCode(){
        return scoreCode;
    }


    /**
     * This method returns the text for the ScoreCardLabel
     *
     * @return text the String of the label
     */
    public String getText(){
        return text;
    }

    /**
     * This method updates the text of the JLabel with the new score provided in the parameter
     *
     * @param newScore the new score for the score line
     */
    public void updateScore(String newScore){
        scoreNum = newScore;
        if(scoreCode == "Y" || scoreCode == "C") { //if Y or C it needs an extra space for alignment
            text = scoreCode + "                         " + scoreNum;
        }
        else{
            text = scoreCode + "                        " + scoreNum;
        }
    }
}
