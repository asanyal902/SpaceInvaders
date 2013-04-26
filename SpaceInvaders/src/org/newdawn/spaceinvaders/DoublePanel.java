package org.newdawn.spaceinvaders;

import java.awt.*;

import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.event.*;
/**
* Makes Panel for 2 player interface. Taking Player Names
* @author Ayush Sanyal, Chirayu Garg
*/
public class DoublePanel extends JPanel {
    /**
	 * @uml.property  name="clip"
	 * @uml.associationEnd  
	 */
    private Clip clip;
	/**
	 * @uml.property  name="levels" multiplicity="(0 -1)" dimension="1"
	 */
	String [] levels = {"Level 1","Level 2","Level 3","Level 4"};
    /**
	 * @uml.property  name="textField"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    protected JTextField textField;
    /**
	 * @uml.property  name="textField2"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    protected JTextField textField2;
    /**
	 * @uml.property  name="name"
	 */
    protected String name;
    private final static String newline = "\n";
    /**
	 * @uml.property  name="player1_name"
	 */
    String player1_name;
    /**
	 * @uml.property  name="player2_name"
	 */
    String player2_name;
    public void paintComponent(Graphics g)
    {
    	Image bg = new ImageIcon("yoda_master.jpg").getImage();
    	g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
    public DoublePanel() {
    	
        super(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();
        JLabel welcome = new JLabel("Please Enter Your names and preferred level below");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font(Font.SERIF,Font.BOLD,30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 10;
        c.gridy = 0;
        add(welcome,c);
        textField = new JTextField(20);
        textField.setToolTipText("Player 1: Enter your Name here");
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font(Font.SERIF,Font.BOLD,20));
        c.fill = GridBagConstraints.NONE;
       c.weighty = 0.2;
        c.gridx = 10;
        c.gridy=10;
        add(textField,c);
        textField2 = new JTextField(20);
        textField2.setToolTipText("Player 2: Enter your Name here");
        textField2.setBackground(Color.WHITE);
        textField2.setForeground(Color.BLACK);
        textField2.setFont(new Font(Font.SERIF,Font.BOLD,20));
        c.fill = GridBagConstraints.NONE;
       c.weighty = 0.2;
        c.gridx = 10;
        c.gridy=15;
        add(textField2,c);
        final JComboBox list = new JComboBox(levels);
        c.fill = GridBagConstraints.NONE;
       c.weighty = 0.1;
        c.gridx = 10;
        c.gridy=30;
        add(list,c);
        JButton play = new JButton("Click to play");
        play.addActionListener(new ActionListener()
        {public void actionPerformed(ActionEvent evt){
        clip.stop();
         setVisible(false);
         player1_name = textField.getText();
         player2_name = textField2.getText();
         Game.twoPlayer = true;
         String player_level = levels[list.getSelectedIndex()];
         Game.player1 = new JLabel(player1_name);
         Game.player2 = new JLabel(player2_name);
         Game.level =Integer.parseInt(player_level.charAt(6)+"")-1;
          System.out.println(player1_name+" "+player_level);
          synchronized(Game.lock) {
         	    Game.lock.notify();
         	}
         System.out.println(player1_name+" "+player2_name+"  "+player_level);
        }});
        c.weighty = 0.1;
        c.gridx = 10;
        c.gridy = 40;
        add(play,c);
      

    }

    /**
	 * @param clip
	 * @uml.property  name="clip"
	 */
    public void setClip(Clip clip){
 	   this.clip = clip;
    }

    /**
	 * @return
	 * @uml.property  name="name"
	 */
    @Override
    public String getName(){
    return this.name;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
   
   
}