package org.newdawn.spaceinvaders;

public class Level 
{
	private int level;
	private Entity ship;
	private Game game;
	public Level(int x,Entity y,Game z)
	{
		level = x;
		ship = y;
		game = z;
		
		
	}
	/**
	 * Initialise the starting state of the entities (ship and aliens). Each
	 * entitiy will be added to the overall list of entities in the game.
	 */
	public void createEntities()
	{
		if(level == 1)
		{
			initEntities1(ship,game);
		}
		else if(level == 2)
		{
			initEntities2(ship,game);
		}
		else if(level == 3)
		{
			initEntities3(ship,game);
		}
		else if(level == 4)
		{
			initEntities4(ship,game);
		}
	}
	private void initEntities1(Entity ship,Game game)
	{
			// create the player ship and place it roughly in the center of the screen
			//ship = new ShipEntity(game,"sprites/ship.gif",370,550);
			//game.entities.add(ship);
			
			// create a block of aliens (5 rows, by 12 aliens, spaced evenly)
			game.alienCount = 0;
			for (int row=1;row<7;row++) 
			{
				for (int x=0;x<row;x++) 
				{
					if(row == 5 && x == 1)
					{
						BossEntity boss = new BossEntity(game,"sprites/alien.gif",370+(x*50),(200)-row*30,1);
						game.entities.add(boss);
					}
					else
					{
					Entity alien = new AlienEntity(game,"sprites/alien.gif",370+(x*50),(200)-row*30);
					game.entities.add(alien);
					}
					game.alienCount++;
				}
			}
			for (int row=2;row<7;row++) 
			{
				for (int x=0;x<row;x++) 
				{
					Entity alien = new AlienEntity(game,"sprites/alien.gif",370-(x*50),(200)-row*30);
					game.entities.add(alien);
					game.alienCount++;
				}
			}
		}

		private void initEntities2(Entity ship,Game game)
		{
			game.alienCount = 0;
			for (int row=0;row<5;row++) 
			{
				for (int x=0;x<12;x++) 
				{
					Entity alien;
	                BossEntity boss;
	                if((row == 2 && x == 4) || (row == 4 && x == 8))
	                {
	                	boss = new BossEntity(game,"sprites/alien.gif",100+(x*50),(50)+row*30,2);
	                    game.entities.add(boss);
	                }
	                else
	                {
	                	alien = new AlienEntity(game,"sprites/alien.gif",100+(x*50),(50)+row*30);
	                	game.entities.add(alien);
	                }
				    game.alienCount++;
				}
			}
		}
		
		private void initEntities3(Entity ship,Game game)
		{
			game.alienCount = 0;
			for (int x=0;x<12;x++) 
			{
				for (int row=0;row<x;row++) 
				{
					Entity alien;
	                BossEntity boss;
	                if((row == 2 && x == 4) || (row == 4 && x == 8) || (row == 9 && x == 11))
	                {
	                	boss = new BossEntity(game,"sprites/alien.gif",100+(x*50),(50)+row*30,3);
	                    game.entities.add(boss);
	                }
	                else
	                {
	                	alien = new AlienEntity(game,"sprites/alien.gif",100+(x*50),(50)+row*30);
	                	game.entities.add(alien);
	                }
				    game.alienCount++;
				}
			}
		}
		private void initEntities4(Entity ship,Game game)
		{
			game.alienCount = 0;
			for (int x=0;x<12;x++) 
			{
				for (int row=0;row<(x/2);row++) 
				{
					Entity alien;
	                BossEntity boss;
	                if((row == 1 && x == 4) || (row == 3 && x == 8) || (row == 4 && x == 11))
	                {
	                	boss = new BossEntity(game,"sprites/alien.gif",80+(x*50),(50)+row*30,4);
	                    game.entities.add(boss);
	                }
	                else
	                {
	                	alien = new AlienEntity(game,"sprites/alien.gif",80+(x*50),(50)+row*30);
	                	game.entities.add(alien);
	                }
				    game.alienCount++;
				}
			}
			for (int row=0;row<6;row++) 
			{
				for (int x=0;x<3;x++) 
				{
					Entity alien;
	                BossEntity boss;
	                if((row == 2 && x == 1) || (row == 4 && x == 0))
	                {
	                	boss = new BossEntity(game,"sprites/alien.gif",100-(x*50),(50)+row*30,4);
	                    game.entities.add(boss);
	                }
	                else
	                {
	                	alien = new AlienEntity(game,"sprites/alien.gif",100-(x*50),(50)+row*30);
	                	game.entities.add(alien);
	                }
				    game.alienCount++;
				}
			}
			for (int x=0;x<12;x++) 
			{
				for (int row=0;row<(x/2);row++) 
				{
					Entity alien;
	                BossEntity boss;
	                if((row == 1 && x == 4) || (row == 3 && x == 8) || (row == 4 && x == 11))
	                {
	                	boss = new BossEntity(game,"sprites/alien.gif",80+(x*50),(200)+row*30,4);
	                    game.entities.add(boss);
	                }
	                else
	                {
	                	alien = new AlienEntity(game,"sprites/alien.gif",80+(x*50),(200)+row*30);
	                	game.entities.add(alien);
	                }
				    game.alienCount++;
				}
			}
			
		}
}
