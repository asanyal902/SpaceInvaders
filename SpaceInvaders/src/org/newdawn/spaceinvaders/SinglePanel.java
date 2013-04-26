package org.newdawn.spaceinvaders;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import javax.sound.sampled.Clip;
/**
 *	Displays Option pane for entering name and Level
 * @author Ayush Sanyal, Chirayu Garg
 */
public class SinglePanel extends JPanel 
{
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
	 * @uml.property  name="name"
	 */
    protected String name;
    private final static String newline = "\n";
    /**
	 * @uml.property  name="player1_name"
	 */
    String player1_name;
    public void paintComponent(Graphics g)
    {
    	Image bg = new ImageIcon("yoda_master.jpg").getImage();
    	g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
    public SinglePanel() 
    {
        super(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();
        JLabel welcome = new JLabel("Please Enter Your name and select Level");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font(Font.SERIF,Font.BOLD,30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 10;
        c.gridy = 0;
        add(welcome,c);
        textField = new JTextField(20);
        textField.setToolTipText("Enter your Name here");
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font(Font.SERIF,Font.BOLD,20));
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0.2;
        c.gridx = 10;
        c.gridy=20;
        add(textField,c);
        final JComboBox list = new JComboBox(levels);
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0.1;
        c.gridx = 10;
        c.gridy=40;
        add(list,c);
        JButton play = new JButton("Click to play");
        play.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent evt)
        	{
        		clip.stop();	 
        		setVisible(false);
        		 Game.twoPlayer = false;
        		player1_name = textField.getText();
        		String player_level = levels[list.getSelectedIndex()];
        		Game.player1 = new JLabel(player1_name);
        		Game.level =Integer.parseInt(player_level.charAt(6)+"") -1;
        		System.out.println(player1_name+" "+player_level);
        		synchronized(Game.lock) 
        		{
        			Game.lock.notify();
        		}
        	}
         });
        c.weighty = 0.1;
        c.gridx = 10;
        c.gridy = 60;
        add(play,c);
      }

   /**
 * @param clip
 * @uml.property  name="clip"
 */
public void setClip(Clip clip)
   {
	   this.clip = clip;
   }

    /**
	 * @return
	 * @uml.property  name="name"
	 */
    @Override
    public String getName()
    {
    	return this.name;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */

}
