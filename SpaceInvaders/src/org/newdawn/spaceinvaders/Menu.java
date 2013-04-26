/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.spaceinvaders;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
* Displays menu. With one player, two player option
* @author Ayush Sanyal, Chirayu Garg
*/

public class Menu extends JPanel
{
    /**
	 * @uml.property  name="onePlayer"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    JButton onePlayer;
    /**
	 * @uml.property  name="twoPlayer"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    JButton twoPlayer;
    /**
	 * @uml.property  name="welcome"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    JLabel welcome;
    public static  Clip clip;
    @Override 
    public void paintComponent(Graphics g)
    {
    	Image bg = new ImageIcon("master_yoda.jpg").getImage();
    	g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
    public Menu()
    {
    	this.setLayout(new BorderLayout());
    	this.setBackground(Color.BLACK);
    	
    	onePlayer = new JButton("Single Player");
    	twoPlayer = new JButton("Two Player");
    	welcome = new JLabel("Welcome to Space Invaders!",JLabel.CENTER);
    	welcome.setForeground(Color.WHITE);
    	welcome.setFont(new Font(Font.SERIF,Font.BOLD,50));
    	JPanel buttons = new JPanel();
    	this.add(BorderLayout.NORTH,welcome);
    	this.add(BorderLayout.SOUTH,buttons);
    	onePlayer.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent ev)
    		{  
    			removeAll();
    			SinglePanel p = new SinglePanel();
    			p.setClip(clip);
    			add(p);
    			p.setVisible(true);
    			validate();
    			repaint();
    		}
    	});
    	twoPlayer.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent ev)
    		{
    			removeAll();
    			DoublePanel p = new DoublePanel();
    			p.setClip(clip);
    			add(p);
    			p.setVisible(true);
    			validate();
    			repaint();
    		}
    	});
    	buttons.setBackground(Color.BLACK);
    	buttons.add(onePlayer);
    	buttons.add(twoPlayer);
    	JLabel name2 = new JLabel("Please Enter your name");
    }


    public synchronized void playInit() 
    {
    	new Thread(new Runnable() 
    	{
    		// The wrapper thread is unnecessary, unless it blocks on the
    		// Clip finishing; see comments.
    		public void run() {
    			try 
    			{
    				clip = AudioSystem.getClip();
    				AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("StarWars.wav"));
    				clip.open(inputStream);
    				clip.start();
    				while (!clip.isRunning())
    					Thread.sleep(10);
    				while (clip.isRunning())
    					Thread.sleep(10);
    				clip.close();
    			} 
    			catch (Exception e) 
    			{
    				System.err.println(e.getMessage());
    			}
    		}
	  }).start();
    } 
    
}


