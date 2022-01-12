
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monopoly2;

/**
 *
 * @author habibahefnyy
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
/**
 *
 * @author Huda Amr
 */
public class Dice extends JPanel {
	private int roll1;
    private int roll2;
	Random rnd = new Random();
	int faceValue = 1;
	
	public Dice(int xCoord, int yCoord, int width, int height) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(xCoord, yCoord, width, height);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(faceValue == 1) {
			g.fillOval(getWidth()/2 - 5/2, getHeight()/2 - 5/2, 5, 5);
		} else if(faceValue == 2) {
			g.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
			g.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
		} else if(faceValue == 3) {
			g.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
			g.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
			g.fillOval(getWidth()/2 - 5/2, getHeight()/2 - 5/2, 5, 5);
		} else if(faceValue == 4) {
			g.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
			g.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
			g.fillOval(getWidth()/2 - 15, getHeight()/2 - 15, 5, 5);
			g.fillOval(getWidth()/2 + 10, getHeight()/2 + 10, 5, 5);
		} else if(faceValue == 5) {
			g.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
			g.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
			g.fillOval(getWidth()/2 - 15, getHeight()/2 - 15, 5, 5);
			g.fillOval(getWidth()/2 + 10, getHeight()/2 + 10, 5, 5);
			g.fillOval(getWidth()/2 - 5/2, getHeight()/2 - 5/2, 5, 5);
		} else {
			g.fillOval(getWidth()/2 - 15, getHeight()/2 + 10, 5, 5);
			g.fillOval(getWidth()/2 + 10, getHeight()/2 - 15, 5, 5);
			g.fillOval(getWidth()/2 - 15, getHeight()/2 - 15, 5, 5);
			g.fillOval(getWidth()/2 + 10, getHeight()/2 + 10, 5, 5);
			g.fillOval(getWidth()/2 - 15, getHeight()/2 - 5/2, 5, 5);
			g.fillOval(getWidth()/2 + 10, getHeight()/2 - 5/2, 5, 5);
		}
		
	}
	

	public void rollDice(){
		faceValue = rnd.nextInt(6) + 1;
		repaint();
	}
	public boolean isDouble(){
        return roll1 == roll2;
    }
	public int getFaceValue(){
		return faceValue;
	}
	
	public Dice(int xCoord, int yCoord, int width, int height, String labelString) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(xCoord, yCoord, width, height);
		
	}
	
	

}