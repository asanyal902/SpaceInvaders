/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.spaceinvaders;
import java.applet.Applet;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class Menu extends JPanel
{
    JButton onePlayer;
    JButton twoPlayer;
    JLabel welcome;
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
public static void main(String[] args){

    Menu m = new Menu();
 JFrame f = new JFrame();
//f.setLayout(new BorderLayout());
 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


f.setSize(800,800);
f.setVisible(true);
f.setContentPane(m);
}
}


