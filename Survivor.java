import java.lang.Math;

public class Survivor
{
	private boolean infected;
	private int xPos;
	private int yPos;
	private double strength;
	private int health;

	public Survivor(boolean inf, int x, int y, double str)
	{
		this.infected = inf;
		this.xPos = x;
		this.yPos = y;
		this.strength = str;
		this.health = 100;
	}

	public int getX()
	{
		return this.xPos;
	}
	public int getY()
	{
		return this.yPos;
	}
	public boolean getInfected()
	{
		return this.infected;
	}
	public int getHealth()
	{
		return this.health;
	}
	public void setInfected(boolean inf)
	{
		this.infected = inf;
	}
	public void setHealth(int h)
	{
		this.health = h;
	}
	public void setPos(int x, int y)
	{
		this.xPos = x;
		this.yPos = y;
	}
	public void setX(int x)
	{
		this.xPos = x;
	}
	public void setY(int y)
	{
		this.yPos = y;
	}

	public void attack(Survivor s)
	{
		double r = Math.random();
		// see if attack effective 
		if (r < this.strength)
		{
			s.health = s.health - 10; 
		}
		
	}

}
