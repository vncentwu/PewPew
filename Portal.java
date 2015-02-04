/**
 * @(#)Portal.java
 *
 *
 * @author 
 * @version 1.00 2013/6/10
 */
import java.util.ArrayList;

public class Portal extends Projectile{

	public static final int IN = 8;
	public static final int OUT = 9;
	
	
    public Portal(double dir, double row, double col, ArrayList<TestEnt> temp, int type)
    {
    	
		super(dir, row, col, temp);
		setSpeed(5);	
		this.type = type;
		damage = 0;
		distance = 0;
		width = 40;
		height = 40;
		radius = 20;
 	
    }
    
    public void act()
    {
    	distance++;
    	if(distance<50)
    		move();
    }

	public void afterCollide()
	{
		System.out.println("!!");
	}
	
	public void move()
	{
		if(isGone())
			return;
		//when changing this, make sure to change all the other movement modififiers
		double temp = Math.toRadians(direction);
		double tempRow = (rowTL + (speed*Math.sin(temp)));
		double tempCol = (colTL + (speed*Math.cos(temp)));
		boolean ow = handleNextCollisions(tempRow, tempCol);

			noCollide();
			

							
	}   
		 
}