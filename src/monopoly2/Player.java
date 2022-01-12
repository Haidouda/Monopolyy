
package monopoly2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
/**
 *
 * @author Huda Amr
 */
public class Player extends JPanel {
private int position;
        private ArrayList<Property> properties = new ArrayList<Property>();
        public int outOfJailCards = 0;
        public int turnsInJail = 0;
        public boolean inJail = false;
        private int money = 1500;
	private int playerNumber;
	JLabel lblPlayerNumber;
	static int totalPlayers = 0; // we might need this number later on
	static HashMap<Integer, Integer> ledger= new HashMap<>();

	private int currentSquareNumber = 0; // where player is currently located on (0 - 19). initially zero
	private ArrayList<Integer> titleDeeds = new ArrayList<Integer>(); // squares that the player has
	private int wallet = 3200; // initial money


	public ArrayList<Integer> getTitleDeeds() {
		return titleDeeds;
	}
	
	public int getWallet() {
		return wallet;
	}

	public void withdrawFromWallet(int withdrawAmount) {
		if(withdrawAmount > wallet) {
			setVisible(false);
			System.out.println("Player "+ playerNumber + " went bankrupt!");
		} else {
			wallet -= withdrawAmount;
		}
	}

	public void depositToWallet(int depositAmount) {
		wallet += depositAmount;
		System.out.println("Payday for player "+getPlayerNumber()+". You earned $200!");
	}

	public int getCurrentSquareNumber() {
		return currentSquareNumber;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public boolean hasTitleDeed(int squareNumber) {
		return titleDeeds.contains(squareNumber) ? true : false;
	}

	public void buyTitleDeed(int squareNumber) {
		if(ledger.containsKey(squareNumber)) {
			System.out.println("It's already bought by someone. You cannot by here.");
		} else {
			titleDeeds.add(this.getCurrentSquareNumber());
			ledger.put(squareNumber, this.getPlayerNumber()); // everytime a player buys a title deed, it is written in ledger, for example square 1 belongs to player 2
		
		}
	}

	public Player(int xCoord, int yCoord, int width, int height) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBounds(xCoord, yCoord, 20, 20);
		this.setLayout(null);
	}

	public Player(int playerNumber, Color color) {
		// TODO Auto-generated constructor stub
		this.playerNumber = playerNumber;
		this.setBackground(color);
		lblPlayerNumber = new JLabel(""+playerNumber);
		lblPlayerNumber.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblPlayerNumber.setForeground(Color.WHITE);
		this.add(lblPlayerNumber); 
		this.setBounds(playerNumber*30, 33, 20, 28); // need to fix here for adjustable player numbers
		totalPlayers++;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}


	int[] xLocationsOfPlayer1 = {31, 131, 231, 331, 431, 531,
			761, 761, 761, 761, 761,
			431, 331, 231, 131, 31,
			31, 31, 31, 31};

	int[] yLocationsOfPlayer1 = {33, 33, 33, 33, 33, 33,
			133, 233, 333, 433, 533,
			633, 633, 633, 633, 633,
			433, 333, 233, 133};
	
	int[] xLocationsOfPlayer2 = {61, 191, 291, 361, 461, 561,
			781, 781, 781, 781, 781,
			461, 361, 261, 161, 61,
			61, 61, 61, 61};

	int[] yLocationsOfPlayer2 = {33, 33, 33, 33, 33, 33,
			133, 233, 333, 433, 533,
			633, 633, 633, 633, 633,
			433, 333, 233, 133};
	

	public void move(int dicesTotal) {
		if(currentSquareNumber + dicesTotal > 19) {
			depositToWallet(200);
		}
		int targetSquare = (currentSquareNumber + dicesTotal) % 20;
		currentSquareNumber = targetSquare;
		
		if(Monopoly2.nowPlaying == 0) {
			this.setLocation(xLocationsOfPlayer1[targetSquare], yLocationsOfPlayer1[targetSquare]);
		} else {
			this.setLocation(xLocationsOfPlayer2[targetSquare], yLocationsOfPlayer2[targetSquare]);
		}
		
		if(ledger.containsKey(this.getCurrentSquareNumber())) {
			Monopoly2.infoConsole.setText("This property belongs to player "+ledger.get(this.getCurrentSquareNumber()));
		}
		//ledger.put(this.getCurrentSquareNumber(), this.getPlayerNumber());
	}



	// by comparing player's coordinates according to the board, we will get it's
	// current square number
	// currently unused, found a better way
	public int getCurrentSquareNumberByCoordinates() {

		int x = this.getX();
		int y = this.getY();

		if(x < 100) {
			if(y < 100) {
				return 0;
			} else if(y > 100 && y < 200) {
				return 19;
			} else if(y > 200 && y < 300) {
				return 18;
			} else if(y > 300 && y < 400) {
				return 17;
			} else if(y > 400 && y < 500) {
				return 16;
			} else {
				return 15;
			}
		} else if(x > 100 && x < 200) {
			if(y < 100) {
				return 1;
			} else {
				return 14;
			}
		} else if(x > 200 && x < 300) {
			if(y < 100) {
				return 2;
			} else {
				return 13;
			}
		} else if(x > 300 && x < 400) {
			if(y < 100) {
				return 3;
			} else {
				return 12;
			}
		}else if(x > 400 && x < 500) {
			if(y < 100) {
				return 4;
			} else {
				return 11;
			}
		} else {
			if(y < 100) {
				return 5;
			} else if(y > 100 && y < 200) {
				return 6;
			} else if(y > 200 && y < 300) {
				return 7;
			} else if(y > 300 && y < 400) {
				return 8;
			} else if(y > 300 && y < 500) {
				return 9;
			} else {
				return 10;
			}
		}
	}
public int getNumUtilities(){
        int numUtilities = 0;
        for(Property p : properties){
            if(p instanceof Utility){
                numUtilities++;
            }
        }

        return numUtilities;
    }
private void sortPropertiesByGroup(ArrayList<Property> properties){
        ArrayList<Utility> utilities = new ArrayList<>();
        ArrayList<Railroad> railroads = new ArrayList<>();
        ArrayList<Property> sorted = new ArrayList<>();

        for(Property property : properties){
            if(property instanceof Utility){
                utilities.add((Utility) property);
            } else if(property instanceof Railroad){
                railroads.add((Railroad) property);
            } else {
                sorted.add(property);
            }
        }

        sorted.addAll(railroads);
        sorted.addAll(utilities);

        this.properties = sorted;
    }
    public void buy(Property property){
        depositToWallet(-property.getPrice());
        properties.add(property);
        sortPropertiesByGroup(properties);
    }
    public int getNumRailroads(){
        int numRailroads = 0;
        for(Property p : properties){
            if(p instanceof Railroad){
                numRailroads++;
            }
        }

        return numRailroads;
    }
    

        
	
public void listProperties(){
        if(properties.isEmpty()){
            System.out.println("You do not own any properties");
        }
        for(Property property : properties){
            System.out.println(property);
        }
    }
public void mortgage(Property property){
        property.mortgaged = true;
        depositToWallet(property.getPrice() / 2);
    }

    public void payMortgage(Property property){
        property.mortgaged = false;
        depositToWallet( (int) (-property.getPrice() * 0.55) );
    }
    public ArrayList<Property> getUnimprovedProperties(){
        ArrayList<Property> unimproved = new ArrayList<>();
        for(Property property : properties){
            if(property instanceof ColorProperty && ((ColorProperty) property).getNumHouses() != 0);
            else {
                unimproved.add(property);
            }
        }

        return unimproved;
    }
    public ArrayList<Property> getMortgagedProperties(){
        ArrayList<Property> mortgaged = new ArrayList<>();
        for(Property property : properties){
            if(property.mortgaged){
                mortgaged.add(property);
            }
        }

        return mortgaged;
    }
    public ArrayList<ColorProperty> getHouseableProperties(){
        ArrayList<ColorProperty> houseable = new ArrayList<>();
        for(ColorProperty i : getOwnColorGroupList()){
            boolean lowestHouses = true;

            for(ColorProperty j : getOwnColorGroupList()){
                if(i.getGroup() == j.getGroup() && i.getNumHouses() > j.getNumHouses()){
                    lowestHouses = false;
                }
            }

            if(lowestHouses && i.getNumHouses() != 5){
                houseable.add(i);
            }
        }

        return houseable;
    }
    public ArrayList<ColorProperty> getOwnColorGroupList(){
        ArrayList<ColorProperty> list = new ArrayList<>();
        for(Property property: properties){
            if(property instanceof ColorProperty && ownsGroup(((ColorProperty) property).getGroup())){
                list.add((ColorProperty) property);
            }
        }
        return list;
    }
    public boolean ownsGroup(ColorProperty.Group group){
        int count = 0;

        for(Property property : properties){
            if(property instanceof ColorProperty && ((ColorProperty) property).getGroup() == group){
                count++;
            }
        }

        return (count == group.maxInGroup);
    }
    public void sell(Property property){
        depositToWallet(property.getPrice() / 2);
        property.setOwner(null);
    }
    public void move(int numSquares, Board board){
        position += numSquares;

        //if pass GO
        if(position >= 40){
            //System.out.println(name + " passed GO and collected $200");
            money += 200;
            position %= 40;
        }

//        System.out.println("Landed on " + board.getCurrentSquare(this));
//        board.getCurrentSquare(this).doAction(this);
    }
    
    
    
}