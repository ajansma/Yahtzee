package edu.gonzaga;

import javax.swing.*;
import java.awt.*;

/**
 * This class holds a JLabel that keeps track of the image of the die
 *
 * @author Ariana Jansma
 * @author v4.0
 */
public class DieLabel extends JLabel {
    /**
     * die is the JLabel that represents a single die
     */
    private JLabel die = new JLabel();
    /**
     * image is the ImageIconn that shows the numeric value of the die
     */
    private ImageIcon image;
    /**
     * thisDie is the Die object associated with the dice
     */
    private Die thisDie;
    /**
     * value holds the numeric value of the die
     */
    private int value;

    /**
     * Constructor takes in the currentDie and sets the value and gets the image
     *
     * @param currentDie the Die object to represent
     */
    public DieLabel(Die currentDie){
        thisDie = currentDie;
        value = currentDie.getValue();
        this.getImage();
        die.setIcon(image);
    }

    /**
     * This object returns the image
     *
     * @return image that represents the die
     */
    public ImageIcon getIcon(){
        return image;
    }

    /**
     * This method sets a new Icon for the image
     *
     * @param a new int to set die to
     */
    public void setIcon(int a){
        value = a;
        image = this.getImage();
        //return image;
    }

    /**
     * This method finds the correct image from the image choices
     *
     * @return image that accurately represents die
     */
    public ImageIcon getImage(){
        if(value == 1){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-01.png");
        }
        else if(value == 2){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-02.png");
        }
        else if(value == 3){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-03.png");
        }
        else if(value == 4){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-04.png");
        }
        else if(value == 5){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-05.png");
        }
        else if(value == 6){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-06.png");
        }
        else if(value == 7){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-07.png");
        }
        else if(value == 8){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-08.png");
        }
        else if(value == 9){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-09.png");
        }
        else if(value == 10){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-10.png");
        }
        else if(value == 11){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-11.png");
        }
        else if(value == 12){
            image = new ImageIcon("YahtzeeMedia-2/Dice/D6-12.png");
        }
        setImageSize(image);
        return image;

    }

    /**
     * This method resizes the image to fit the screen better
     *
     * @param icon the image to resize
     */
    private void setImageSize(ImageIcon icon){
        Image thisImage = icon.getImage();
        Image imgResized = thisImage.getScaledInstance(70,70,Image.SCALE_SMOOTH);
        image = new ImageIcon(imgResized);
    }


}
