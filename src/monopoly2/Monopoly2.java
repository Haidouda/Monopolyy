
package monopoly2;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import monopoly2.Dice;
import monopoly2.Player;
import monopoly2.Board;


public class Monopoly2 extends JFrame {

    private JPanel contentIncluder;
    static JTextArea infoConsole;
    JPanel playerAssetsPanel;
    CardLayout c1 = new CardLayout();
    ArrayList<Player> players = new ArrayList<Player>();
    static int turnCounter = 0;
    JButton btnNextTurn;
    JButton btnRollDice;
    JButton btnPayRent;
    JButton btnBuy;
    JTextArea panelPlayer1TextArea;
    JTextArea panelPlayer2TextArea;
    Board gameBoard;
    Player player1;
    Player player2;
    Boolean doubleDiceForPlayer1 = false;
    Boolean doubleDiceForPlayer2 = false;
    static int nowPlaying = 0;

    public Monopoly2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setSize(2080, 1020);
        contentIncluder = new JPanel();
        contentIncluder.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentIncluder);
        contentIncluder.setLayout(null);
//BACKGROUND OF BOARD
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        layeredPane.setBounds(6, 6, 950, 740);
        contentIncluder.add(layeredPane);

        gameBoard = new Board(6, 6, 950, 740);
        gameBoard.setBackground(new Color(202, 255, 202));
        layeredPane.add(gameBoard, new Integer(0));
//THE TOKENS
        player1 = new Player(1, Color.RED);
        players.add(player1);
        layeredPane.add(player1, new Integer(1));

        player2 = new Player(2, new Color(102, 0, 153));
        players.add(player2);
        layeredPane.add(player2, new Integer(1));
//BACKGROUND OF THE CARD 
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        rightPanel.setBounds(984, 10, 550, 700);
        contentIncluder.add(rightPanel);
        rightPanel.setLayout(null);

        btnBuy = new JButton("Buy");
        btnBuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //turnCounter--; // decrease because we increased at the end of the rolldice
                Player currentPlayer = players.get(nowPlaying);
                infoConsole.setText("You bought " + gameBoard.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).getName());
                currentPlayer.buyTitleDeed(currentPlayer.getCurrentSquareNumber());
                int withdrawAmount = gameBoard.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).getPrice();
                currentPlayer.withdrawFromWallet(withdrawAmount);
                btnBuy.setEnabled(false);
                updatePanelPlayer1TextArea();
                updatePanelPlayer2TextArea();
                //turnCounter++;
            }
        });
        btnBuy.setBounds(81, 478, 117, 29);
        rightPanel.add(btnBuy);
        btnBuy.setEnabled(false);

        btnPayRent = new JButton("Pay Rent");
        btnPayRent.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // turnCounter--;
                Player currentPlayer = players.get(nowPlaying);
                Player ownerOfTheSquare = players.get((Player.ledger.get(currentPlayer.getCurrentSquareNumber())) == 1 ? 0 : 1);
                infoConsole.setText("You paid to the player " + ownerOfTheSquare.getPlayerNumber());

                int withdrawAmount = gameBoard.getAllSquares().get(currentPlayer.getCurrentSquareNumber()).getRentPrice();
                System.out.println(withdrawAmount);
                currentPlayer.withdrawFromWallet(withdrawAmount);
                ownerOfTheSquare.depositToWallet(withdrawAmount);

                btnNextTurn.setEnabled(true);
                btnPayRent.setEnabled(false);
                //currentPlayer.withdrawFromWallet(withdrawAmount);
                updatePanelPlayer1TextArea();
                updatePanelPlayer2TextArea();
                //turnCounter++;
                //gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()).setRentPaid(true);
            }

        });
        //PAY RENT BUTTON
        btnPayRent.setBounds(210, 478, 117, 29);
        rightPanel.add(btnPayRent);
        btnPayRent.setEnabled(false);
// DICE DICE DICE DICE DICE
        Dice dice1 = new Dice(244, 550, 40, 40);
        layeredPane.add(dice1, new Integer(1));

        Dice dice2 = new Dice(333, 550, 40, 40);
        layeredPane.add(dice2, new Integer(1));

        btnRollDice = new JButton("Roll Dice");
        btnRollDice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (nowPlaying == 0) {
                    // player1's turn
                    int dice1OldValue = dice1.getFaceValue();
                    int dice2OldValue = dice2.getFaceValue();
                    dice1.rollDice();
                    dice2.rollDice();
                    int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
                    if (dice1.getFaceValue() == dice2.getFaceValue()) {
                        doubleDiceForPlayer1 = true;
                    } else {
                        doubleDiceForPlayer1 = false;
                    }
                    player1.move(dicesTotal);
                    if (Player.ledger.containsKey(player1.getCurrentSquareNumber()) // if bought by someone
                            && Player.ledger.get(player1.getCurrentSquareNumber()) != player1.getPlayerNumber() // not by itself
                            ) {
                        btnBuy.setEnabled(false);
                        btnRollDice.setEnabled(false);
                        btnNextTurn.setEnabled(false);
                        btnPayRent.setEnabled(true);
                    }
                    if (Player.ledger.containsKey(player1.getCurrentSquareNumber()) // if bought by someone 
                            && Player.ledger.get(player1.getCurrentSquareNumber()) == player1.getPlayerNumber()) { // and by itself
                        btnBuy.setEnabled(false);
                        btnPayRent.setEnabled(false);
                        btnNextTurn.setEnabled(true);
                    }
                    if (gameBoard.getUnbuyableSquares().contains(gameBoard.getAllSquares().get(player1.getCurrentSquareNumber()))) {
                        btnBuy.setEnabled(false);
                        btnNextTurn.setEnabled(true);
                    } else if (!Player.ledger.containsKey(player1.getCurrentSquareNumber())) { // if not bought by someone
                        btnBuy.setEnabled(true);
                        btnNextTurn.setEnabled(true);
                        btnPayRent.setEnabled(false);
                    }

                } else {
                    // player2's turn
                    int dice1OldValue = dice1.getFaceValue();
                    int dice2OldValue = dice2.getFaceValue();
                    dice1.rollDice();
                    dice2.rollDice();
                    int dicesTotal = dice1.getFaceValue() + dice2.getFaceValue();
                    if (dice1.getFaceValue() == dice2.getFaceValue()) {
                        doubleDiceForPlayer2 = true;
                    } else {
                        doubleDiceForPlayer2 = false;
                    }
                    player2.move(dicesTotal);
                    if (Player.ledger.containsKey(player2.getCurrentSquareNumber()) // if bought by someone
                            && Player.ledger.get(player2.getCurrentSquareNumber()) != player2.getPlayerNumber() // not by itself
                            ) {
                        btnBuy.setEnabled(false);
                        btnRollDice.setEnabled(false);
                        btnNextTurn.setEnabled(false);
                        btnPayRent.setEnabled(true);
                    }
                    if (Player.ledger.containsKey(player2.getCurrentSquareNumber()) // if bought by someone 
                            && Player.ledger.get(player2.getCurrentSquareNumber()) == player2.getPlayerNumber()) { // and by itself
                        btnBuy.setEnabled(false);
                        btnPayRent.setEnabled(false);

                    }
                    if (gameBoard.getUnbuyableSquares().contains(gameBoard.getAllSquares().get(player2.getCurrentSquareNumber()))) {
                        btnBuy.setEnabled(false);
                        btnNextTurn.setEnabled(true);
                    } else if (!Player.ledger.containsKey(player2.getCurrentSquareNumber())) { // if not bought by someone
                        btnBuy.setEnabled(true);
                        btnNextTurn.setEnabled(true);
                        btnPayRent.setEnabled(false);
                    }

                }

                btnRollDice.setEnabled(false);
                if (doubleDiceForPlayer1 || doubleDiceForPlayer2) {
                    infoConsole.setText("Click Next Turn to allow player " + (nowPlaying == 0 ? 1 : 2) + " to Roll Dice!");
                } else {
                    infoConsole.setText("Click Next Turn to allow player " + (nowPlaying == 0 ? 2 : 1) + " to Roll Dice!");
                }

                // we have to add below 2 lines to avoid some GUI breakdowns.
                layeredPane.remove(gameBoard);
                layeredPane.add(gameBoard, new Integer(0));

                updatePanelPlayer1TextArea();
                updatePanelPlayer2TextArea();

            }
        });
        // ROLL DICE BUTTON
        btnRollDice.setBounds(81, 413, 246, 53);
        rightPanel.add(btnRollDice);
        // NEXT TURN BUTTON

        btnNextTurn = new JButton("Next Turn");
        btnNextTurn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                btnRollDice.setEnabled(true);
                btnBuy.setEnabled(false);
                btnPayRent.setEnabled(false);
                btnNextTurn.setEnabled(false);

                if (nowPlaying == 0 && doubleDiceForPlayer1) {
                    nowPlaying = 0;
                    doubleDiceForPlayer1 = false;
                } else if (nowPlaying == 1 && doubleDiceForPlayer2) {
                    nowPlaying = 1;
                    doubleDiceForPlayer2 = false;
                } else if (!doubleDiceForPlayer1 && !doubleDiceForPlayer2) {
                    nowPlaying = (nowPlaying + 1) % 2;
                }

                c1.show(playerAssetsPanel, "" + (nowPlaying == 0 ? 1 : 2)); // maps 0 to 1 and 1 to 2
                updatePanelPlayer1TextArea();
                updatePanelPlayer2TextArea();
                infoConsole.setText("It's now player " + (nowPlaying == 0 ? 1 : 2) + "'s turn!");
            }

        });

        btnNextTurn.setBounds(81, 519, 246, 53);
        rightPanel.add(btnNextTurn);
        btnNextTurn.setEnabled(false);
// PANEL PANEL PANEL PANEL PANEL
        JPanel test = new JPanel();
        test.setBounds(90, 312, 300, 68);
        rightPanel.add(test);
        test.setBackground(Color.MAGENTA);
        test.setLayout(null);
        
// player wealth panel
        playerAssetsPanel = new JPanel();
        playerAssetsPanel.setBounds(81, 28, 300, 189);
        rightPanel.add(playerAssetsPanel);
        playerAssetsPanel.setLayout(c1);
//PLAYER WEALTH label
        JPanel panelPlayer1 = new JPanel();
        panelPlayer1.setBackground(Color.RED);
        playerAssetsPanel.add(panelPlayer1, "1");
        panelPlayer1.setLayout(null);
        
       
        JLabel panelPlayer1Title = new JLabel("PLAYER 1 INFO");
        panelPlayer1Title.setForeground(Color.BLACK);
        panelPlayer1Title.setHorizontalAlignment(SwingConstants.CENTER);
        panelPlayer1Title.setBounds(0, 6, 240, 16);
        panelPlayer1.add(panelPlayer1Title);

        panelPlayer1TextArea = new JTextArea();
        panelPlayer1TextArea.setBounds(10, 34, 280, 149);
        panelPlayer1.add(panelPlayer1TextArea);
        panelPlayer1TextArea .setEditable(false);
        
        JPanel panelPlayer2 = new JPanel();
        panelPlayer2.setBackground(new Color(102,0,153));
        playerAssetsPanel.add(panelPlayer2, "2");
        panelPlayer2.setLayout(null);
        c1.show(playerAssetsPanel, "" + nowPlaying);

        JLabel panelPlayer2Title = new JLabel("PLAYER 2 INFO");
        panelPlayer2Title.setForeground(Color.WHITE);
        panelPlayer2Title.setHorizontalAlignment(SwingConstants.CENTER);
        panelPlayer2Title.setBounds(0, 6, 240, 16);
        panelPlayer2.add(panelPlayer2Title);

        panelPlayer2TextArea = new JTextArea();
        panelPlayer2TextArea.setBounds(10, 34, 280, 149);
        panelPlayer2.add(panelPlayer2TextArea);
        panelPlayer2TextArea.setEditable(false);
        
        updatePanelPlayer1TextArea();
        updatePanelPlayer2TextArea();

        infoConsole = new JTextArea();
        infoConsole.setColumns(20);
        infoConsole.setRows(5);
        infoConsole.setBounds(6, 6, 280, 56);
        test.add(infoConsole);
        infoConsole.setLineWrap(true);
        infoConsole.setText("PLayer 1 starts the game by clicking Roll Dice!");
        infoConsole.setEditable(false);
    }

    public void updatePanelPlayer2TextArea() {
        // TODO Auto-generated method stub
        String result = "";
        result += "Current Balance: " + player2.getWallet() + "\n";

        result += "Title Deeds: \n";
        for (int i = 0; i < player2.getTitleDeeds().size(); i++) {
            result += " - " + gameBoard.getAllSquares().get(player2.getTitleDeeds().get(i)).getName() + "\n";
        }
      try
        {
            File data = new File("C:\\Users\\Lenovo\\Desktop\\mondata.dat");
            FileOutputStream fos=new FileOutputStream(data);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            
            oos.writeObject(player2.getWallet());
            oos.writeObject(player2.getTitleDeeds());
            oos.flush();
            oos.close();
            fos.close();

        }
        catch(Exception e)
        {

        }

        panelPlayer2TextArea.setText(result);
    }

    public void updatePanelPlayer1TextArea() {
        // TODO Auto-generated method stub
        String result = "";
        result += "Current Balance: " + player1.getWallet() + "\n";

        result += "Title Deeds: \n";
        for (int i = 0; i < player1.getTitleDeeds().size(); i++) {
            result += " - " + gameBoard.getAllSquares().get(player1.getTitleDeeds().get(i)).getName() + "\n";
        }
      try
        {
            File data = new File("C:\\Users\\Lenovo\\Desktop\\mondata.dat");
            FileOutputStream fos=new FileOutputStream(data);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            
            oos.writeObject(player1.getWallet());
            oos.writeObject(player1.getTitleDeeds());
            oos.flush();
            oos.close();
            fos.close();

        }
        catch(Exception e)
        {

        }

        panelPlayer1TextArea.setText(result);
    }

    public static void errorBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Monopoly2 frame = new Monopoly2();
        frame.setVisible(true);
    }
    
    
    

}