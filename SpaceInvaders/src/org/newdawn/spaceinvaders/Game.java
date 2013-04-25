package org.newdawn.spaceinvaders;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The main hook of our game. This class with both act as a manager
 * for the display and central mediator for the game logic. 
 * 
 * Display management will consist of a loop that cycles round all
 * entities in the game asking them to move and then drawing them
 * in the appropriate place. With the help of an inner class it
 * will also allow the player to control the main ship.
 * 
 * As a mediator it will be informed when entities within our game
 * detect events (e.g. alient killed, played died) and will take
 * appropriate game actions.
 * 
 * @author Kevin Glass
 */
public class Game extends Canvas {
	public final static Object lock = new Object();
	/** The stragey that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	/** True if the game is currently "running", i.e. the game loop is looping */
	private boolean gameRunning = true;
	/** The list of all the entities that exist in our game */
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	/** The list of entities that need to be removed from the game this loop */
	private ArrayList<Entity> removeList = new ArrayList<Entity>();
	/** The entity representing the player */
	private Entity ship;
	/** The speed at which the player's ship should move (pixels/sec) */
	private double moveSpeed = 300;
	/** The time at which last fired a shot */
	private long lastFire = 0;
	/** The interval between our players shot (ms) */
	private long firingInterval = 500;
	/** The number of aliens left on the screen */
	protected int alienCount;
	/**Score of the game**/
        private static int scoreplayer1;
        /* */
        private static int scoreplayer2;
	/** The message to display which waiting for a key press */
	private String message = "";
	/** True if we're holding up game play until a key has been pressed */
	private boolean waitingForKeyPress = true;
	/** True if the left cursor key is currently pressed */
	private boolean leftPressed = false;
	/** True if the right cursor key is currently pressed */
	private boolean rightPressed = false;
	/** True if we are firing */
	private boolean firePressed = false;
	/** True if the 'A' key is currently pressed */
	private boolean AkeyPressed = false;
	/** True if the 'D' cursor key is currently pressed */
	private boolean DkeyPressed = false;
	/** True if we are firing using 'W' key */
	private boolean fireWPressed = false;
	
	/** True if game logic needs to be applied this loop, normally as a result of a game event */
	private boolean logicRequiredThisLoop = false;
        /*Time between boss alien firing shots*/
    private long fireInterval = 1000;
    /* level of game to play*/
    public static int level;
    
    /*label for player1 name*/
    public static JLabel player1 = new JLabel("Chirayu");
    /*label for player2 name*/
    public static JLabel player2 = new JLabel("Ayush");
    /*label for player1 score*/
    private JLabel score_player1 = new JLabel("0");
    /*label for player2 score*/
    private JLabel score_player2= new JLabel("0");
	/*Flag to indicate whether the user selected 1player or 2 player*/
    public static boolean twoPlayer=true;
    /*The other ship, initiailized if 2 player game*/
	private ShipEntity shipOther=null;
	/*The time at which last fired a shot */
	private long lastFire2=0;  
	/*number pf players left*/
	private int players=1;
	/**
	 * Construct our game and set it running.
	 */
    private static JFrame container = new JFrame("Space Invaders 101");
	public Game() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                createAndShowGUI();
            }
        });
    	synchronized(lock){
    	       	        try {
							lock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    	    
    	}
    	if(twoPlayer)
    		players = 2;
        container.getContentPane().removeAll();
        validate();
        repaint();
		JPanel score_panel = new JPanel();
		score_panel.setPreferredSize(new Dimension(50,600));
		score_panel.setLayout(new BoxLayout(score_panel,BoxLayout.Y_AXIS));
		score_panel.add(player1);
		score_panel.add(score_player1);
		if(twoPlayer){
			score_panel.add(player2);
			score_panel.add(score_player2);
		}
		container.setLayout(new BorderLayout());
		container.add(score_panel,BorderLayout.WEST);
		container.add(panel,BorderLayout.CENTER);
		// setup our canvas size and put it into the content of the frame
		setBounds(0,0,850,600);
		panel.add(this);
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);
		
		// finally make the window visible 
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		
		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}); 
		
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		addKeyListener(new KeyInputHandler());
		
		// request the focus so key events come to us
		requestFocus();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		// initialise the entities in our game so there's something
		// to see at startup
		ship = new ShipEntity(this,"sprites/ship.gif",370,550);
		if(twoPlayer)
		{
			shipOther = new ShipEntity(this,"sprites/ship.gif",400,550);
			entities.add(shipOther);
		}
		entities.add(ship);
		Level currentLevel;
		if(twoPlayer)
			currentLevel = new Level(level,ship,shipOther,this); 
		else
			currentLevel = new Level(level,ship,this);
		currentLevel.createEntities();
	}
	
	/**
	 * Start a fresh game, this should clear out any old data and
	 * create a new set.
	 */
	private void startGame() {
		// clear out any existing entities and intialise a new set
		entities.clear();
		ship = new ShipEntity(this,"sprites/ship.gif",370,550);
		entities.add(ship);
		if(twoPlayer)
		{
			shipOther = new ShipEntity(this,"sprites/ship.gif",400,550);
			entities.add(shipOther);
		}
		/** change for level here 
		 * value from front user interface*/
		//initEntities1 ();
		Level currentLevel = new Level(level,ship,this);
		currentLevel.createEntities();
		// blank out any keyboard settings we might currently have
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
		AkeyPressed = false;
		DkeyPressed = false;
		fireWPressed = false;
	}
	
	/**
	 * Notification from a game entity that the logic of the game
	 * should be run at the next opportunity (normally as a result of some
	 * game event)
	 */
	public void updateLogic() {
		logicRequiredThisLoop = true;
	}
	
	/**
	 * Remove an entity from the game. The entity removed will
	 * no longer move or be drawn.
	 * 
	 * @param entity The entity that should be removed
	 */
	public void removeEntity(Entity entity) {
		removeList.add(entity);
	}
	
	/**
	 * Notification that the player has died. 
	 */
	public void notifyDeath() {
		/*Checking if any players left, if both dead do following*/
		if(players==1){ 
		playDeath();
		message = "Oh no! They got you, try again?";
		waitingForKeyPress = true;
		level = 0;
		}
		/*else do the following*/
		else{
			players--;
			playSound();	
		}
	}
	
	/**
	 * Notification that the player has won since all the aliens
	 * are dead.
	 */
	public void notifyWin() {
		playWin();
		if(level==5)
			message = "Well done! You've finished all levels and your score is "+ Integer.toString(scoreplayer1)+".	  "+"To exit press Esc OR Press any other key to restart the game";
		message = "Well done! Your score is "+ Integer.toString(scoreplayer1)+".	  "+"To exit press Esc OR Press any other key to move to the next level";
		waitingForKeyPress = true;
	}
	
	/**
	 * Notification that an alien has been killed
	 */
	public void notifyAlienKilled(int player) {
		// reduce the alient count, if there are none left, the player has won!
		alienCount--;
		playSound();
		if(player==1)
		{
		scoreplayer1++;
		score_player1.setText(Integer.toString(scoreplayer1));
		}
		else
		{
		scoreplayer2++;
		score_player2.setText(Integer.toString(scoreplayer2));
		}
		
		if (alienCount == 0) {
			notifyWin();
		}
		
		// if there are still some aliens left then they all need to get faster, so
		// speed up all the existing aliens
		for (int i=0;i<entities.size();i++) {
			Entity entity = (Entity) entities.get(i);
			
			if (entity instanceof AlienEntity) {
				// speed up by 2%
				entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
			}
		}
	}
	
	/**
	 * Attempt to fire a shot from the player. Its called "try"
	 * since we must first check that the player can fire at this 
	 * point, i.e. has he/she waited long enough between shots
	 */
	public void tryToFire() {
		// check that we have waiting long enough to fire
		if (System.currentTimeMillis() - lastFire < firingInterval) {
			return;
		}
		
		// if we waited long enough, create the shot entity, and record the time.
		lastFire = System.currentTimeMillis();
		ShotEntity shot = new ShotEntity(this,"sprites/shot.gif",ship.getX()+10,ship.getY()-30,1);
		entities.add(shot);
        }
	public void tryToFire2(){
		// check that we have waiting long enough to fire for ship 2
		if (System.currentTimeMillis() - lastFire2 < firingInterval) {
			return;
		}
		
		// if we waited long enough, create the shot entity, and record the time.
		lastFire2 = System.currentTimeMillis();
		ShotEntity shot = new ShotEntity(this,"sprites/shot.gif",shipOther.getX()+10,shipOther.getY()-30,2);
		entities.add(shot);
        } 
	
	/**
	 * The main game loop. This loop is running during all game
	 * play as is responsible for the following activities:
	 * <p>
	 * - Working out the speed of the game loop to update moves
	 * - Moving the game entities
	 * - Drawing the screen contents (entities, text)
	 * - Updating game events
	 * - Checking Input
	 * <p>
	 */
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		
		// keep looping round til the game ends
		while (gameRunning) {
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			// Get hold of a graphics context for the accelerated 
			// surface and blank it out
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,800,600);
			Image bg = new ImageIcon("moon-earth.jpg").getImage();
	    	g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
			// cycle round asking each entity to move itself
			if (!waitingForKeyPress) {
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					
					entity.move(delta);
				}
			}
			
			// cycle round drawing all the entities we have in the game
			for (int i=0;i<entities.size();i++) {
				Entity entity = (Entity) entities.get(i);
				
				entity.draw(g);
			}
			
			// brute force collisions, compare every entity against
			// every other entity. If any of them collide notify 
			// both entities that the collision has occured
			for (int p=0;p<entities.size();p++) {
				for (int s=p+1;s<entities.size();s++) {
					Entity me = (Entity)entities.get(p);
					Entity him = (Entity)entities.get(s);
					
					if (me.collidesWith(him)) {
						me.collidedWith(him);
						him.collidedWith(me);
					}
				}
			}
			
			// remove any entity that has been marked for clear up
			entities.removeAll(removeList);
			removeList.clear();

			// if a game event has indicated that game logic should
			// be resolved, cycle round every entity requesting that
			// their personal logic should be considered.
			if (logicRequiredThisLoop) {
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					entity.doLogic();
				}
				
				logicRequiredThisLoop = false;
			}
			
			// if we're waiting for an "any key" press then draw the 
			// current message 
			if (waitingForKeyPress) {
				g.setColor(Color.white);
				g.drawString(message,(800-g.getFontMetrics().stringWidth(message))/2,250);
				g.drawString("Press any key",(800-g.getFontMetrics().stringWidth("Press any key"))/2,300);
				score_player1.setText("0");
				scoreplayer1 = 0;
				score_player2.setText("0");
				scoreplayer2 = 0;
			}
			
			// finally, we've completed drawing so clear up the graphics
			// and flip the buffer over
			g.dispose();
			strategy.show();
			
			// resolve the movement of the ship. First assume the ship 
			// isn't moving. If either cursor key is pressed then
			// update the movement appropraitely
			ship.setHorizontalMovement(0);
			
			if ((leftPressed) && (!rightPressed)) {
				ship.setHorizontalMovement(-moveSpeed);
			} else if ((rightPressed) && (!leftPressed)) {
				ship.setHorizontalMovement(moveSpeed);
			}
			
			//update keys for ship2, if 2 player enabled
		if(twoPlayer)
		{
			shipOther.setHorizontalMovement(0);
			if ((AkeyPressed) && (!DkeyPressed)) {
				shipOther.setHorizontalMovement(-moveSpeed);
			} else if ((DkeyPressed) && (!AkeyPressed)) {
				shipOther.setHorizontalMovement(moveSpeed);
			}
		}
			// if we're pressing fire, attempt to fire
			if (firePressed) {
				tryToFire();
			}
			// if we're pressing fire for ship2, attempt to fire
			if (fireWPressed) {
				tryToFire2();
			}
			
                        
			//alien should shoot if time exceeded
			for (int i=0;i<entities.size();i++) {
            	if(entities.get(i) instanceof BossEntity){
                        BossEntity boss = (BossEntity)entities.get(i);
                        if(System.currentTimeMillis()- boss.lastBossFire > fireInterval && !waitingForKeyPress)
                            boss.bossFire();
                        
                        }
                        }
			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	/**
	 * A class to handle keyboard input from the user. The class
	 * handles both dynamic input during game play, i.e. left/right 
	 * and shoot, and more static type input (i.e. press any key to
	 * continue)
	 * 
	 * This has been implemented as an inner class more through 
	 * habbit then anything else. Its perfectly normal to implement
	 * this as seperate class if slight less convienient.
	 * 
	 * @author Kevin Glass
	 */
	private class KeyInputHandler extends KeyAdapter {
		/** The number of key presses we've had while waiting for an "any key" press */
		private int pressCount = 1;
		
		/**
		 * Notification from AWT that a key has been pressed. Note that
		 * a key being pressed is equal to being pushed down but *NOT*
		 * released. Thats where keyTyped() comes in.
		 *
		 * @param e The details of the key that was pressed 
		 */
		public void keyPressed(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "press"
			if (waitingForKeyPress) {
				return;
			}
			
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_A) {
				AkeyPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				DkeyPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				fireWPressed = true;
			}
		} 
		
		/**
		 * Notification from AWT that a key has been released.
		 *
		 * @param e The details of the key that was released 
		 */
		public void keyReleased(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "released"
			if (waitingForKeyPress) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = false;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_A) {
				AkeyPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				DkeyPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				fireWPressed = false;
			}
		}

		/**
		 * Notification from AWT that a key has been typed. Note that
		 * typing a key means to both press and then release it.
		 *
		 * @param e The details of the key that was typed. 
		 */
		public void keyTyped(KeyEvent e) {
			// if we're waiting for a "any key" type then
			// check if we've recieved any recently. We may
			// have had a keyType() event from the user releasing
			// the shoot or move keys, hence the use of the "pressCount"
			// counter.
			if (waitingForKeyPress) {
				if (pressCount == 1) {
					// since we've now recieved our key typed
					// event we can mark it as such and start 
					// our new game
					Menu.clip.stop();
					waitingForKeyPress = false;
					if(level<5)
						level++;
					if(level==5)  //restart the game
						level = 0;
					startGame();
					pressCount = 0;
				} else {
					pressCount++;
				}
			}
			
			// if we hit escape, then quit the game
			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
	public static synchronized void playSound() 
	{
		  new Thread(new Runnable() 
		  {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		    	  Clip clip = AudioSystem.getClip();
		    	  AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("blast.wav"));
		          //Main.class.getResourceAsStream("/path/to/sounds/" + url));
		    	  clip.open(inputStream);
		    	  clip.start(); 
		      } 
		      catch (Exception e) 
		      {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
	}
	
	public static synchronized void playDeath()  //plays clip when player dies
	{
		  new Thread(new Runnable() 
		  {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		    	  Clip clip = AudioSystem.getClip();
		    	  AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("death.wav"));
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
	
	public static synchronized void playWin()  //plays clip when player dies
	{
		  new Thread(new Runnable() 
		  {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		    	  Clip clip = AudioSystem.getClip();
		    	  AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("win.wav"));
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
	synchronized private static void createAndShowGUI() {
        //Create and set up the window.
	//f.setLayout(new BorderLayout());
	 container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//container.setSize(850,600);
	container.setVisible(true);
	container.setVisible(true);
	container.setPreferredSize(new Dimension(850,600));
	container.pack();
	container.setResizable(false);
    container.setContentPane(new Menu());
    }
	
	/**
	 * The entry point into the game. We'll simply create an
	 * instance of class which will start the display and game
	 * loop.
	 * 
	 * @param argv The arguments that are passed into our game
	 */
	public static void main(String argv[]) {
		Menu.playInit();

		Game g =new Game();

		 //Start the main game loop, note: this method will not
		// return until the game has finished running. Hence we are
		// using the actual main thread to run the game.
		g.gameLoop();
	}
}
