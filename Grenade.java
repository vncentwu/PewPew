/**
 * @(#)Grenade.java
 *
 *
 * @author 
 * @version 1.00 2013/6/8
 */

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Grenade extends Projectile{


	int far;
	int expSize;
	boolean exploding;
	int timer = 5;
	public static final int GRENADE_TYPE = 6;
    public Grenade(double dir, double row, double col, ArrayList<TestEnt> temp)
    {
		super(dir, row, col, temp);
		setSpeed(7);
		far = 30;
		type = 6;
		expSize = 100;
		damage = 100;
 	
    }
    
  
    public void act()
    {
    	if(exploding)
    	{
    		
    	}
    	else
    	{
	    	distance++;
	    	move();
	    	if(distance == far)
	    	{
	    		
	    		explode();
	    		//System.out.println("boom");
	    		
	    	}    		
    	}

   	
    	
    }
    
    public void afterCollide()
    {

    }   
    	 
  	public void explode()
  	{
  		
     	for(TestEnt e: ents)
    	{
    		if(!(e == this))
    		{

				try{
				double tempRow = e.getRow() + e.getRadius();
				double tempCol = e.getCol() + e.getRadius(); 
				double myRow = rowTL + 3;
				double myCol = colTL + 3; 			
				double y = tempRow - myRow;
				double x= tempCol - myCol;
				double result = Math.pow(x, 2) + Math.pow(y, 2);
				double duoWidth = (expSize) + (e.getWidth()/2);
				double duoWidth2 = Math.pow(duoWidth, 2);
				if(result <= duoWidth2)
				{
					if(e.type != 6)
						e.subtractHP(damage);
					
				}    						
				}
				catch(ConcurrentModificationException oops)
				{
					System.out.println("oh well");
				}    
  			}  
    	}
    	exploding = true;
    	//die();
  	}
    
    public boolean check()
    {
    	timer--;
    
    	if(timer == 0)
    	{
    		remove = true;	
    		setRow(-10000);
    		setCol(-10000);	
    		return true;
    		
    	}
    	else
    	{
    		return false;
    	}
    }
    
    
}