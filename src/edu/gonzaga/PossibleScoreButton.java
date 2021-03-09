package edu.gonzaga;

import javax.swing.*;

/**
 * This class is a label that contains text for a scorecard line
 *
 * @author Ariana Jansma
 * @version v4.0
 */

public class PossibleScoreButton extends JButton {
    /**
     * scoreNum keeps track of the score to be displayed
     */
    private String scoreNum;
    /**
     * scoreCode keeps track of the identifying scoreCode to be displayed to the GUI
     */
    private String scoreCode;
    /**
     * thisButton is the button that will be displayed to the GUI
     */
    private JButton thisButton = new JButton();

    /**
     * Constructor method that sets the text, bounds, and assigns scoreNum and scoreCode
     *
     * @param code the scoreline identifier
     * @param score the score corresponding with the scoreCode
     */
    public PossibleScoreButton(String code, String score){
        scoreNum = score;
        scoreCode = code;
        thisButton.setText(scoreCode);
        this.setBounds(0, 100, 100, 30);

    }

    /**
     * This method returns the string to be displayed on the button
     *
     * @return show String holding the scoreNum and the scoreCode
     */
    public String returnButtonString(){
        String show = "Score " + scoreNum + " on the " + scoreCode + " line";
        return show;
    }

    /**
     * This method returns the scoreCode
     *
     * @return scoreCode String of the score code
     */
    public String getScoreCode(){
        return scoreCode;
    }

    /**
     * This method returns the scoreNum
     *
     * @return scoreNum String version of the score held in this button
     */
    public String getScoreNum(){
        return scoreNum;
    }
}
