package org.newdawn.spaceinvaders;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import javax.sound.sampled.Clip;
/**
 *
 * @author Ayush
 */
public class SinglePanel extends JPanel 
{
	private Clip clip;
    String [] levels = {"Level 1","Level 2","Level 3","Level 4"};
    protected JTextField textField;
    protected String name;
    private final static String newline = "\n";
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
      /* super(new BorderLayout());
        setBackground(Color.BLACK);
         
        textField = new JTextField(5);
      // textField.setSize(10,2);
      // textField.setBackground(Color.BLACK);
    //   textField.setForeground(Color.WHITE);
      /* textField.addActionListener( new ActionListener()
        {public void actionPerformed(ActionEvent evt){ 
        name = textField.getText();
        }});*/
  /*      JLabel welcome = new JLabel("Please Enter Your name below");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font(Font.SERIF,Font.BOLD,50));
        JButton play = new JButton("Click to play");
        play.addActionListener(new ActionListener()
        {public void actionPerformed(ActionEvent evt){
         setVisible(false);
         player1_name = textField.getText();

         System.out.println(player1_name);
        }});*/
       //name.s
       /* add(BorderLayout.NORTH,welcome);
        add(BorderLayout.CENTER,textField);
        add(BorderLayout.SOUTH,play);*/
  /*      add(welcome,BorderLayout.NORTH);
        add(textField,BorderLayout.NORTH);
        add(play,BorderLayout.SOUTH);*/
        
    }

   public void setClip(Clip clip)
   {
	   this.clip = clip;
   }

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
    private static void createAndShowGUI() 
    {
        //Create and set up the window.
        JFrame frame = new JFrame("TextDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);
        //Add contents to the window.
        frame.setContentPane(new SinglePanel());

        //Display the window.
        frame.setSize(200, 200);
        //frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                createAndShowGUI();
            }
        });
    }

}
