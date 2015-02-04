/**
 * @(#)Bot.java
 *
 *
 * @author 
 * @version 1.00 2013/6/2
 */
import java.util.ArrayList;

public class Bot extends TestEnt{

	int range = 300;
	int rangeSet = 0;
	//public static final int BOT_TYPE = 10;
    public Bot(ArrayList<TestEnt> temp) {
    	super(temp);
    	name = "Bot";
    	type = 0;
    }

    public Bot(ArrayList<TestEnt> temp, int row, int col) {
    	super(temp);
    	setRow(row);
    	setCol(col);
    	setSpeed(3);
    	name = "Bot";
    	type = 0;
    }
    
    public void act()
    {
    	//System.out.println("please?");
		life++;
    	if(!isGone())
    	{
	    	int chance = (int)(Math.random()*10);
	    	{
	    		//System.out.println("acting");
	    		//setAIDirection();
	    		move();
	    		if(chance == 0)
	    		{
	    			
	    			setAIDirection();
	    		}
	    	}    		
	     	for(TestEnt e: getEnts())
	    	{
	    		//System.out.println(e.type);
	    		if(e instanceof Player)
	    		{
		    			
	    			double ox = e.getCol();
	    			double oy = e.getRow();
	    			double mx = getCol();
	    			double my = getRow();
	    			double diffx = Math.abs(ox - mx);
	    			double diffy = Math.abs(oy-my);
	    			if(diffx < range && diffy < range)
	    			{
	    				chance = (int)(Math.random()*30);
			    		if(chance == 0)
			    		{
			    			ArrayList<TestEnt> ents = getEnts();
			    			ents.add(new Projectile((getDirection()), getDotRow(), getDotCol(), ents, 60));
			    		}
	    			} 
	    		}
	    	}
    	}			
	    				
	    				
	    				
	    				
	    				  	
	    	

    		
    		   	
    }
    
    public void setRandomDirection()
    {
    	double random = (int)(Math.random()*361);
    	setDirection(random);
    	
    	
    }
    
    public void setAIDirection()
    {
    	//System.out.println(getEnts().size());
    	for(TestEnt e: getEnts())
    	{
    		//System.out.println(e.type);
    		if(e instanceof Player)
    		{
	    			
    			double ox = e.getCol();
    			double oy = e.getRow();
    			double mx = getCol();
    			double my = getRow();
    			double diffx = Math.abs(ox - mx);
    			double diffy = Math.abs(oy-my);
    			if(diffx > range || diffy > range)
    			{
    				setRandomDirection();
    				return;
    			}
    			
    			if((ox - mx >= 0) && (oy - my >= 0))
    			{
	    			//System.out.println("yeah?");
	    			double y = e.getRow()- getRow();
	    			double x = e.getCol() - getCol();
	    			double yx = y/x;
	    			double deg = Math.atan(yx);
	    			double deg2 = Math.toDegrees(deg);
	    			//System.out.println(deg2);
	    			setDirection((int)deg2);    							
    			}
    			else if((ox - mx <= 0) && (oy - my <= 0))
    			{
	    			//System.out.println("yeah?");
	    			double y = e.getRow()- getRow();
	    			double x = e.getCol() - getCol();
	    			double yx = y/x;
	    			double deg = Math.atan(yx);
	    			double deg2 = Math.toDegrees(deg);
	    			//System.out.println(deg2);
	    			setDirection((int)deg2 + 180);    				
    				
    			}
    			else if((ox - mx >= 0) && (oy - my <= 0))
    			{
	    			//S/ystem.out.println("yeah?");
	    			double y = e.getRow()- getRow();
	    			double x = e.getCol() - getCol();
	    			double yx = y/x;
	    			double deg = Math.atan(yx);
	    			double deg2 = Math.toDegrees(deg);
	    			//System.out.println(deg2);
	    			setDirection((int)deg2);    				
    				
    			}    	
     			else if((ox - mx <= 0) && (oy - my >= 0))
    			{
	    			//System.out.println("yeah?");
	    			double y = e.getRow()- getRow();
	    			double x = e.getCol() - getCol();
	    			double yx = y/x;
	    			double deg = Math.atan(yx);
	    			double deg2 = Math.toDegrees(deg);
	    			//System.out.println(deg2);
	    			setDirection((int)deg2 + 180);    				
    				
    			}    			
    			

    			
    		}
    			
    		
    	}
    	
    	
    }		
    
    
    
}