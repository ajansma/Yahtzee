package edu.gonzaga;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class find the configurations that the game will be played under.
 *
 * @author Ariana Jansma
 * @version v4.0
 */

public class GameConfig {
    /**
     * arraylist of the configuration for the game
     */
    private ArrayList<Integer> configs = new ArrayList<>();

    /**
     * Gets input from the user to finalize the configurations to play with
     * If user choses to change the configurations, it writes the new configs to
     * yahtzeeConfigs.txt
     *
     *
     * @param fileName the file to write to if necessary
     * @return currConfigs an array of the configurations to play with
     */
    public List<Integer> finalizeConfigurations(String fileName){
        Scanner input = new Scanner(System.in);
        System.out.println("You are playing with " + configs.get(1) + " " + configs.get(0) + "-sided dice");
        System.out.println("You get " + configs.get(2) + " rolls per hand");

        System.out.print("Enter 'y' if you would like to change the configuration: ");
        char changeConfig = input.next().charAt(0);

        if(changeConfig == 'y'){ //change the configurations
            System.out.print("Enter the number of sides on each die ");
            int newNum = input.nextInt();
            //validate number is positive
            while(newNum <= 0){
                System.out.print("Invalid Entry, Please try a different number: ");
                newNum = input.nextInt();
            }
            configs.set(0, newNum);


            System.out.print("Enter the number of dice in play ");
            newNum = input.nextInt();
            //validate number is positive
            while(newNum <= 0){
                System.out.print("Invalid Entry, Please try a different number: ");
                newNum = input.nextInt();
            }
            configs.set(1, newNum);

            System.out.print("Enter the number of rolls per hand ");
            newNum = input.nextInt();
            //validate number is positive
            while(newNum <= 0){
                System.out.print("Invalid Entry, Please try a different number: ");
                newNum = input.nextInt();
            }
            configs.set(2, newNum);

            //player.gameConfigs(configs.get(0), configs.get(1), configs.get(2));
            //writeToFile(configs,  fileName); //rewrite the file
        }

        return configs; //return the configurations
    }

    /**
     * Opens and reads the file to get the configurations for the game
     * Reads them into an array
     *
     * @param fileName file to read in from
     * @return gameConfigs a list of the configurations read in from the file
     */
    public List<Integer> openFile(String fileName){
        Scanner inFile = null;
        //ArrayList<Integer> gameConfigs = new ArrayList<>();
        try{
            inFile = new Scanner(new File(fileName));

            configs.add(inFile.nextInt());
            configs.add(inFile.nextInt());
            configs.add(inFile.nextInt());

            inFile.close(); //close file
        } catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        //return configs from file
        return configs;
    }

    /**
     * writes the new game configurations to the file stated in the parameters
     *
     * @param sides the new number of sides on each die
     * @param dice the number of die in play
     * @param turn the number of die in play
     * @param fileName the name of the file to write to
     */
    public void writeToFile(int sides, int dice, int turn,  String fileName){
        try {
            PrintStream outFile = new PrintStream(new File(fileName));
            outFile.println(sides);
            outFile.println(dice);
            outFile.println(turn);
            outFile.close(); //close file
        } catch(FileNotFoundException e){
            System.out.println("Could not open file for writing");
        }
    }

    /**
     * Returns the number of sides on the dice being played with in the game
     *
     * @return configs.get(0) number of sides
     */
    public int getSides(){
        return configs.get(0);
    }

    /**
     * Returns the number of dice being played with in the game
     *
     * @return configs.get(1) number of dice
     */
    public int getDice(){
        return configs.get(1);
    }

    /**
     * Returns the number of rolls that the player will have each turn
     *
     * @return configs.get(2) num rolls
     */
    public int getRolls(){
        return configs.get(2);
    }




}