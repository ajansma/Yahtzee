package edu.gonzaga;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is primarily responsible for the GUI aspect of the Yahtzee game.
 *
 * @author Ariana Jansma
 * @version v4.0
 */

public class YahtzeeGUI extends JFrame implements ItemListener {
    /**
     * game represents the game object that controls how the game is played
     */
    private Game game;
    /**
     * dieImages is an array of DieLabels that hold the images corresponding to the die values
     */
    private ArrayList<DieLabel> dieImages = new ArrayList<>();
    /**
     * checkBoxed is an array of check boxes used to figure out which die to roll and keep
     */
    private ArrayList<JCheckBox> checkBoxed = new ArrayList<>();
    /**
     * keepDieArray is an array of characters that keep track of whether or not a checkbox is clicked
     */
    private ArrayList<Character> keepDieArray = new ArrayList<>();
    /**
     * scoreLabels is an arraylist of scorecardlabels that hold the score card values
     */
    private ArrayList<ScoreCardLabel> scoreLabels = new ArrayList<>();
    /**
     * numSides is the number of sides on each die
     */
    private int numSides;
    /**
     * numDice is the number of dice in play
     */
    private int numDice;
    /**
     * turn holds the value of the turn the player is on
     */
    private int turn = 1;
    /**
     * turnOver keeps track of whether or not the player's 3 turns are up
     */
    private boolean turnOver;
    /**
     * f is the frame that contains the GUI
     */
    JFrame f = new JFrame();
    /**
     * Game object that keeps track of the configurations of the game
     */
    private GameConfig configs = new GameConfig();

    /**
     * Constructor for the YahtzeeGUI class, sets frame title, size, and default close settings
     *
     *
     */
    public YahtzeeGUI() {
        f.setTitle("Yahtzee");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 700);

    }

    /**
     * This method begins running the Yahtzee game by getting the game configs setup, constructing the game object, and calling
     * playDiceTurn() to begin the game for the user.
     *
     * No params
     * No returns
     */
    public void runYahtzee(){
        this.setGameConfigs(); //setup game configs dropdowns
        //ImageIcon image = new ImageIcon("YahtzeeMedia-2/Logos/oldskool.jpeg");
        //setup frame
        f.setSize(700, 700);
        f.setLayout(new FlowLayout());

        //add button to finish configs
        JButton setConfigs = new JButton("Set Configurations");
        setConfigs.setBounds(50, 100, 200, 30);
        setConfigs.addActionListener(e -> {
            game = new Game(numSides, numDice, 3); //create game object with configs from dropdowns
            this.updateScoreCard(); //create the scoreLabels
            //clear the frame
            f.getContentPane().removeAll();
            f.setLayout(new BorderLayout(5,5)); //change to a BorderLayout for rest of game
            f.repaint();
            turnOver = false;
            //begin turn
            playDiceTurn();
        });
        //add button to frame
        f.add(setConfigs);
        f.setVisible(true);
    }

    /**
     * This method updates the scoreLabels array by pulling the most recent version of the array from the player's scorecard.
     *
     * No params
     * No returns
     */
    public void updateScoreCard(){
        scoreLabels = game.getGameHand().getScore().getLabels(); //get newest score card
        for(int i = 0; i < scoreLabels.size(); i++){
            scoreLabels.get(i).setText(scoreLabels.get(i).getText());
        }
    }

    /**
     * This method outputs the scorecard to the frame including the section totals and bonuses.
     *
     * No params
     * No returns
     */
    public void showScoreCard(){
        //create panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel header = new JPanel();

        //add header
        JLabel label = new JLabel("Line             Score");
        panel.add(label);

        //calculate totals and bonus:
        int upperTotal = game.getGameHand().getScore().getUpperTotal();
        int bonus = 0;
        if(Integer.valueOf(upperTotal) >= 63){
            bonus = 35;
        }
        int lowerTotal = game.getGameHand().getScore().getLowerScore();
        int grandTotal = upperTotal + lowerTotal + bonus;

        //add each score to a JLabel
        for(int i = 0; i < scoreLabels.size(); i++){
            if(scoreLabels.get(i).returnScoreCode() == "3K"){ //add totals before lower section
                panel.add(new JLabel("----------------"));
                panel.add(new JLabel("Sub Total:            " + upperTotal));
                panel.add(new JLabel("Bonus                  " + bonus));
                panel.add(new JLabel("----------------"));
                panel.add(new JLabel("Upper Total         " + (upperTotal + bonus)));
                panel.add(scoreLabels.get(i));
            }
            else if (scoreLabels.get(i).returnScoreCode() == "C"){ //add totals after lower section
                panel.add(scoreLabels.get(i));
                panel.add(new JLabel("----------------"));
                panel.add(new JLabel("Lower total           " + lowerTotal));
                panel.add(new JLabel("----------------"));
                panel.add(new JLabel("Grand Total          " + grandTotal));
            }
            else {
                panel.add(scoreLabels.get(i));
            }
        }
        //add to frame
        mainPanel.add(header);
        mainPanel.add(panel);
        mainPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
        f.add(mainPanel, BorderLayout.WEST);
        f.repaint();
        f.setVisible(true);
    }

    /**
     * This method runs a player's turn by allowing the player to roll three times or until they want to keep all of their die
     *
     * No params
     * No returns
     */
    public void playDiceTurn(){
            this.createDieImages(); //create starting die labels and add to frame
            this.rollNewDie(); //reset die inbetween turns

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            turn++;

            JButton rollDie = new JButton("Roll checked die"); //create button
            rollDie.setBounds(50, 100, 200, 30);

            JButton showScore = new JButton("Show ScoreCard");
            showScore.setBounds(50,100,200,30);

            showScore.addActionListener(e -> {
                this.updateScoreCard();
                this.showScoreCard();
            });

            //when roll die is clicked
            rollDie.addActionListener(new ActionListener() { //listener
                @Override
                public void actionPerformed(ActionEvent e) {
                    //send keepDieArray to game to update and roll the hand
                    ArrayList<Die> newDieList = game.playTurn(keepDieArray, turn);
                    if (turn < 3 && !(game.getToKeepGoal().equals(keepDieArray))) { //while less than 3 turns
                        for (int i = 0; i < dieImages.size(); i++) {
                            dieImages.get(i).setIcon(newDieList.get(i).getValue()); //update images
                            checkBoxed.get(i).setSelected(false); //reset checkboxes
                            keepDieArray.set(i, 'n'); //reset checkbox tracker
                        }
                        turn++; //increase turn
                        f.repaint();
                    }
                    else {
                        turnOver = true;
                    }

                    if(turnOver == true){ //if turn over, score Hand
                        scoreTurn();
                    }
                }
            });
            //add to frame
            panel.add(rollDie);
            panel.add(showScore);
            f.add(panel, BorderLayout.EAST);
            //f.pack();
            f.setVisible(true);
    }

    /**
     * This method rolls each die in the dieImages array. Created specifically for use after scoring and before creating a new hand.
     *
     * No params
     * No returns
     */
    public void rollNewDie(){
        //set keepDieArray to all 'y' so all die are rolled
        for(int i = 0; i < dieImages.size(); i++){
            keepDieArray.set(i, 'y');
        }
        //get rolled die
        ArrayList<Die> newDieList = game.playTurn(keepDieArray, turn);

        if (turn < 3 && !(game.getToKeepGoal().equals(keepDieArray))) {
            for (int i = 0; i < dieImages.size(); i++) {
                dieImages.get(i).setIcon(newDieList.get(i).getValue()); //update dice
                keepDieArray.set(i, 'n');
            }
            //print to console
            f.repaint();
        }
    }

    /**
     * This method outputs buttons representing the possible scores of the players hand to the GUI.
     *
     * No params
     * No returns
     */
    public void scoreTurn(){
        //show player's sorted hand
        printSortedDice();

        //GUI Layout Setup
        JPanel mainPanel = new JPanel();
        JPanel heading = new JPanel();
        JPanel scores = new JPanel();
        scores.setLayout(new BoxLayout(scores, BoxLayout.Y_AXIS));
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JLabel header = new JLabel("Above is your sorted hand of dice");
        JLabel header2 = new JLabel("");
        JLabel header3 = new JLabel("Click on the score you would like to add to your scorecard:          ");
        heading.add(header);
        heading.add(header2);
        heading.add(header3);

        //setup possible score card buttons
        ArrayList<PossibleScoreButton> possScoreButtons = game.scoreHand();
        for(int i = 0; i < possScoreButtons.size(); i++){
            int val = i;
            possScoreButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.getGameHand().updateScore(possScoreButtons.get(val).getScoreCode(), possScoreButtons.get(val).getScoreNum());
                    updateScoreCard();
                    if(game.gameOver() == true){
                        f.getContentPane().removeAll();
                        f.repaint();
                        //reset turn variables
                        turn = 1;
                        turnOver = false;
                        //show remaining lines
                        JLabel label = new JLabel("Lines remaining: " + game.getGameHand().getScore().scoreCardOpen());
                        f.add(label, BorderLayout.SOUTH);
                        //f.pack();
                        playDiceTurn();
                    }
                    else{
                        //show closing remarks and final score
                        f.getContentPane().removeAll();
                        f.repaint();
                        JLabel closingRemarks = new JLabel("Thank You for playing! Here's your final score");
                        f.add(closingRemarks, BorderLayout.NORTH);
                        f.setVisible(true);
                        showScoreCard();

                    }
                }
            });

            //set the test and add to panel
            possScoreButtons.get(i).setText(possScoreButtons.get(i).returnButtonString());
            scores.add(possScoreButtons.get(i));
        }
        //add to frame
        mainPanel.add(heading);
        mainPanel.add(scores);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        f.add(mainPanel, BorderLayout.EAST);
        f.setVisible(true);
    }

    /**
     * This method outputs to the frame the player's dice hand in sorted order.
     *
     * Side effects: the dieImages order and images are changed.
     *
     * No params
     * No returns
     */
    private void printSortedDice(){
        //clear frame
        f.getContentPane().removeAll();
        //create new panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(new EmptyBorder(10,10,10,10));

        ArrayList<Die> sortedList = game.getSortedGameHand(); //sorted list from the final turn
        for (int i = 0; i < dieImages.size(); i++) {
            dieImages.get(i).setIcon(sortedList.get(i).getValue()); //change dieImages at i to the sorted value at i
            panel.add(dieImages.get(i));
        }
        //add to panel
        f.add(panel, BorderLayout.NORTH);
        f.repaint();
        f.setVisible(true);
    }

    /**
     * This method creates the dieImages and checkBoxes before adding them to the Frame for user use. In addition to this, it creates
     * a keepDieArray to keep track of which checkBoxes are clicked.
     *
     * No params
     * No returns
     */
    public void createDieImages(){
        dieImages.clear(); //clear die array
        checkBoxed.clear(); //clear checkboxes
        keepDieArray.clear(); //clear checkbox tracking array

        //panel setup
        JPanel mainPanel = new JPanel();
        JPanel panel = new JPanel();
        JPanel checkBoxPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); //Box layout to stack die and checkboxes

        for(int i = 0; i < game.getGameHand().returnNumDice(); i++) {
            String text = "Dice " + (i + 1);
            keepDieArray.add('n'); //add all 'n' to array
            dieImages.add(new DieLabel(game.getGameHand().getHand().get(i))); //add Die Labels to array
            checkBoxed.add(new JCheckBox(text)); //add checkboxes

            panel.add(dieImages.get(i)); // add die images to frame
        }

        for(int i = 0; i < checkBoxed.size(); i++){
            checkBoxed.get(i).addItemListener(this); //create action listener
            checkBoxPanel.add(checkBoxed.get(i)); //add to frame
        }

        //add to panel
        mainPanel.add(panel);
        mainPanel.add(checkBoxPanel);

        f.add(mainPanel, BorderLayout.NORTH);
    }

    /**
     * This method sets up the parameters for the game by giving the user two combo boxes to select the
     * number of die and the number of sides on each die for that game.
     *
     * No params
     * No returns
     */
    public void setGameConfigs(){
        String fileName = "yahtzeeConfig.txt";
        configs.openFile(fileName);

        // set up panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel sideChoice = new JPanel();
        JPanel diceChoice = new JPanel();

        //create an explanation
        JLabel explanation = new JLabel("Please Select the number of sides on each dice");

        //create a combo box
        Integer [] numSide = {5, 6, 7, 8, 9, 10, 11, 12};
        JComboBox sidesList = new JComboBox(numSide);
        numSides = configs.getSides();
        for(int i = 5; i <= 12; i++) {
            if(i == configs.getSides()) {
                sidesList.setSelectedIndex(i - 5); // preselect to last used num
            }
        }

        sidesList.addActionListener(new ActionListener() { //check which drop down was selected
            public void actionPerformed(ActionEvent e){
                JComboBox<Integer> sidesList = (JComboBox<Integer>) e.getSource();
                Integer selectedSideNum = (Integer) sidesList.getSelectedItem();
                numSides = selectedSideNum; //update numSides
                configs.writeToFile(numSides, numDice, 3, fileName); //update configs
            }
        });

        //JLabel for explanation
        JLabel dieChoice = new JLabel("Please Select the number of dice to play with");
        Integer [] numberOfDice = {5, 6, 7, 8, 9, 10};
        JComboBox diceChoices = new JComboBox(numberOfDice);
        numDice = configs.getDice();
        for(int i = 5; i <= 10; i++) {
            if(i == configs.getDice()) {
                diceChoices.setSelectedIndex(i - 5); // preselect to last used num
            }
        }
        diceChoices.addActionListener(new ActionListener() { //check which drop down was selected
            public void actionPerformed(ActionEvent e){
                JComboBox<Integer>  diceChoices = (JComboBox<Integer>) e.getSource();
                Integer selectedDiceNum = (Integer) diceChoices.getSelectedItem();
                numDice = selectedDiceNum; //update number of dice
                configs.writeToFile(numSides, numDice, 3, fileName); //update configs
            }
        });

        //add combo boxes and explanations to mainPanel
        sideChoice.add(explanation);
        sideChoice.add(sidesList);
        diceChoice.add(dieChoice);
        diceChoice.add(diceChoices);

        mainPanel.add(sideChoice);
        mainPanel.add(diceChoice);

        f.add(mainPanel); //add to frame
        f.setVisible(true);

        turn = 1;
    }

    /**
     * This method overrides the itemStateChanged method and updates the keepDieArray when a checkBox
     * is clicked
     *
     * @param e ItemEvent: the click that happened on the GUI
     */
    @Override
    public void itemStateChanged(ItemEvent e){
        //go through each checkbox and check if it was the one clicked
        for(int i = 0; i < checkBoxed.size(); i++){
            if(e.getSource() == checkBoxed.get(i)){
                if(keepDieArray.get(i).equals('y')) {
                    keepDieArray.set(i, 'n'); //if checked change to 'n'
                }
                else{
                    keepDieArray.set(i, 'y'); //if checked change to 'y
                }
            }
        }
    }
}
