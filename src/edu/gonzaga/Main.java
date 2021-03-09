package edu.gonzaga;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *This program plays a single round of Yahtzee, it allows the player to decide the number of dice,
 * the number of dice, and the number of rolls. During the rolls, the player can enter 'y' or 'n'
 * to decide which dice to keep and which dice to roll again. After the turn is complete, the possible
 * scores of the final hand are calculated and printed to the console. The lower score set is only
 * calculated if there are 5 or more dice.
 * CPSC 224-01 Fall 2020
 * Programming Assignment #4
 * Program derived partly from Bruce Worobec's c++ and python Yahtzee demo
 *
 * @author Ariana Jansma
 * @version v4.0 11/13/20
 */

public class Main {
    /**
     * This is the main method for the driver program.
     *
     * @param arg stores the incoming command line arguments
     */
    public static void main(String [] arg){

        YahtzeeGUI gui = new YahtzeeGUI();
        gui.runYahtzee();

    }


}
