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

public class Menu extends JPanel
{
    JButton onePlayer;
    JButton twoPlayer;
    JLabel welcome;
    public static  Clip clip;
public Menu(){

this.setLayout(new BorderLayout());
this.setBackground(Color.BLACK);
onePlayer = new JButton("Single Player");
twoPlayer = new JButton("Two Player");
ImageIcon icon = createImageIcon("jetsons.gif","Image in Top Label");
ImageIcon jet = new ImageIcon("");
welcome = new JLabel("Welcome to Space Invaders!",jet,JLabel.CENTER);
welcome.setForeground(Color.WHITE);
welcome.setFont(new Font(Font.SERIF,Font.BOLD,50));
JPanel buttons = new JPanel();
//buttons.setLayout(new BorderLayout());
this.add(BorderLayout.CENTER,welcome);
this.add(BorderLayout.SOUTH,buttons);
onePlayer.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev){  
   removeAll();
    SinglePanel p = new SinglePanel();
    p.setClip(clip);
    add(p);
      p.setVisible(true);
      validate();
      repaint();
 //   name1 = p.getName();
    //  System.out.println(name1);
}
});
twoPlayer.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev){
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
//this.add(BorderLayout.CENTER,name2);
}

protected ImageIcon createImageIcon(String path,
                                           String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
}

public static synchronized void playInit() 
{
	  new Thread(new Runnable() 
	  {
	  // The wrapper thread is unnecessary, unless it blocks on the
	  // Clip finishing; see comments.
	    public void run() {
	      try {
	    	 clip = AudioSystem.getClip();
	    	  AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("StarWars.wav"));
	          //Main.class.getResourceAsStream("/path/to/sounds/" + url));
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

public static void main(String[] args){
playInit();
    Menu m = new Menu();
 JFrame f = new JFrame();
//f.setLayout(new BorderLayout());
 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


f.setSize(800,800);
f.setVisible(true);
f.setContentPane(m);
}
}


