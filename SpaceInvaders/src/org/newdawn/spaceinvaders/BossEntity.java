/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.spaceinvaders;
import java.util.ArrayList;
/**
 *
 * @author xyz
 */
public class BossEntity extends AlienEntity {
   	/** The game in which the alien exists */
	private Game game;
		/** The speed at which the alient moves horizontally */
	private double moveSpeed = 75;

	/** The game in which the entity exists */	
	/**
	 * Create the boss entity- an alien that can shoot!
	 * 
	 * @param game The game in which this entity is being created
	 * @param ref The sprite which should be displayed for this alien
	 * @param x The intial x location of this alien
	 * @param y The intial y location of this alient
	 */
	public BossEntity(Game game,String ref,int x,int y) {
           super(game,ref,x,y);
               // shot = new BigshotEntity(game,"sprites/shot.gif",x+10,y+30);
	 this.game = game;
        }
        public void bossFire(){	
		// if we waited long enough, create the shot entity, and record the time.
		lastBossFire = System.currentTimeMillis();
		BigshotEntity shot = new BigshotEntity(game,"sprites/shot.gif",getX()+10,getY()+30);
		game.entities.add(shot);
	}

    
}
