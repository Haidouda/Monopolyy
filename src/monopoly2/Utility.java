
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monopoly2;
import java.io.PrintWriter;
public class Utility extends Property {
    private  Dice dice;

    public Utility(String name, Dice dice){
        super(name, 150, 0);
        this.dice = dice;
    }

   
    public int getRent() {
        int rent;
        int roll = dice.getFaceValue();

        switch(owner.getNumUtilities()){
            case 1:
                rent = 4 * roll;
                break;
            case 2:
                rent = 10 * roll;
                break;
            default:
                rent = -1;
                break;
        }

        return rent;
    }
}