/**
 * @(#)Ammo.java
 *
 *
 * @author 
 * @version 1.00 2013/6/10
 */
import java.util.ArrayList;

public class Ammo extends TestEnt{

    public Ammo(ArrayList<TestEnt> temp, int row, int col) {
    	
     	hp = 100;
    	type = 7;
    	rowTL = row;
    	colTL = col;
    	this.width = 50;
    	this.height = 50;
    	name = "Ammo";   	
 	
    	
    }
	public void die()
	{
		vanish();
	}    
    
    public void act()
    {
    	life++;
    }
    
}