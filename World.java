import java.lang.Math;
import java.util.*;

public class World
{
	private Survivor [] survivors;
	private int worldSize;
	private int zombieCount;
	private int humanCount;
	
	public World(int zombies, int humans, int size)
	{
		this.worldSize = size;
		this.zombieCount = zombies;
		this.humanCount = humans;
		// initialize survivors array
		survivors = new Survivor[humans + zombies];

		// populate survivors array
		for (int i = 0; i < survivors.length; i++)
		{
			//generate random x and y positions
			double rX = Math.random();
			double rY = Math.random();

			int x = (int) (rX * size);
			int y = (int) (rY * size);

			//random strength
			double strength = Math.random();
			
			if (i < humans)
			{
				survivors[i] = new Survivor(false, x, y, strength);
			}
			else
			{
				survivors[i] = new Survivor(true, x, y, strength);
			}
		}
	}
	public int getHumanCount()
	{
		return this.humanCount;
	}
	public int getZombieCount()
	{
		return this.zombieCount;
	}
	public void updateWorld()
	{
		//attacks
		for (int i = 0; i < this.survivors.length; i++)
		{
			for (int j = 0; j < this.survivors.length; j++)
			{
				int xi = this.survivors[i].getX();
				int yi = this.survivors[i].getY();
				int xj = this.survivors[j].getX();
				int yj = this.survivors[j].getY();
				
				
				// don't attack yourself and don't attack survivor of the same kind
				if (i == j && this.survivors[i].getInfected() == this.survivors[j].getInfected())
				{
					continue;
				}
				else if (xi == xj && yi == yj)
				{
					this.survivors[i].attack(survivors[j]);
				}
				//convert zombies if humans die
				if (this.survivors[i].getHealth() <= 0 && this.survivors[i].getInfected() == false)
				{
					this.survivors[i].setHealth(100);
					this.survivors[i].setInfected(true);
					this.zombieCount++;
					this.humanCount--;
				}
				//update zombie count if human kills zombie
				if (this.survivors[i].getHealth() <=0 && this.survivors[i].getInfected() == true)
				{
					this.zombieCount--;
				}
			}
		}

		//move positions
		for (int i = 0; i < this.survivors.length; i++)
		{
			boolean increase = false;
			boolean xAxis = false;

			if (Math.random() < 0.5)
			{
				increase = true;
			}
			if (Math.random() < 0.5)
			{
				xAxis = true;
			}

			
			//decide which axis to move on
			if (xAxis)	
			{
				//decide which direction along chosen axis to move on
				if (increase == true)
				{
					this.survivors[i].setX(this.survivors[i].getX() + 1 % this.worldSize);
				}
				else
				{
					this.survivors[i].setX(this.survivors[i].getX() - 1 % this.worldSize);
				}
			}
			else
			{
				if(increase == true)
				{
					this.survivors[i].setY(this.survivors[i].getX() + 1 % this.worldSize);
				
				}
				else
				{
					this.survivors[i].setY(this.survivors[i].getX() - 1 % this.worldSize);
				}
			}	
			

			
		}
	}

	public ArrayList<Survivor> getSurvivors(int x, int y)
	{
		ArrayList<Survivor> s = new ArrayList<Survivor>();

		for (int i = 0; i < this.survivors.length; i++)
		{
			int sX = this.survivors[i].getX();
			int sY = this.survivors[i].getY();

			if (sX == x && sY == y)
			{
				s.add(this.survivors[i]);
			}
		}
		
		return s;
	}
	public String toString()
	{
		String printString = "";
		//make a string array to hold the grid
		for (int y = 0; y < worldSize; y++)
		{
			for (int x = 0; x < worldSize; x++)
			{
				String gridString = "";
				//get survivors at position y, x 	
				ArrayList<Survivor> matches = getSurvivors(x, y);
				if (matches.size() == 0)
				{
					gridString = "-----";
				}
				else
				{
					for(int i = 0; i < matches.size(); i++)
					{
						Survivor s = matches.get(i);
						if (s.getInfected() == true)
						{
							gridString += "Z";	
						}
						else
						{
							gridString += "H";
						}

					}
				}
				
				printString += gridString;
			}
			printString += "\n";
		}
		return printString;
	}

	/// the Thread.sleep() is optional it's just to make printing wait to look like an animation
	public static void main (String [] args) throws InterruptedException
	{

		int steps = 1000;
		int size = 20;
		int zombies = 2;
		int humans = 10;

		World w = new World(zombies, humans, size);
		int i = 0;
		while (i < steps)
		{
			System.out.println("Zombies: " + w.getZombieCount() + " Humans: " + w.getHumanCount());
			w.updateWorld();
			System.out.println(w);
			Thread.sleep(500);
			i++;
		}
	}
}
