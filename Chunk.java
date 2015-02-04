/**
 * @(#)Chunk.java
 *
 *
 * @author 
 * @version 1.00 2013/6/15
 */
import java.awt.Rectangle;

public class Chunk {

	Rectangle rect;
	boolean spawned;

    public Chunk(int col, int row, int width, int height)
    {
    	
    	rect = new Rectangle(col, row, width, height);
    	spawned = false;
    	
    }
    
    public Chunk(int col, int row, int width, int height, boolean spawned)
    {
    	
    	rect = new Rectangle(col, row, width, height);
    	this.spawned = spawned;
    	
    }   
    	 
 	public Rectangle getRectangle()
 	{
 		return rect;
 	}   
    
    public void setSpawned(boolean tf)
    {
    	spawned = tf;
    }
    
    public int getX()
    {
    	return (int)rect.getX();
    }
    
    public int getY()
    {
    	return (int)rect.getY();
    }   
    
    public String toString()
    {
    	return "(" + getX() + "," + getY() + ")";
    }
    
    public boolean equals(Object x)
    {
    	Chunk o = (Chunk)x;
    	if(o.getX() == getX() && o.getY() == getY())
    		return true;
    	else
    		return false;
    	
    	
    	
    	
    }
    
    
    
}