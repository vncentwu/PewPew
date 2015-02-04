/**
 * @(#)Camera.java
 *
 *
 * @author 
 * @version 1.00 2013/6/4
 */


public class Camera {

	int width;
	int height;
	int row;
	int col;
	boolean lock;


    public Camera() {
    	width = 1280;
    	height = 720;
    	row = 0;
    	col = 0;
    	
    	
    }
    
    
    public void panRight()
    {
    	if(!lock)
    		col = col + 3;
    }
    
    public void panLeft()
    {
    	if(!lock)
    		col = col - 3;
    }    
    
    public void panUp()
    {
    	if(!lock)
    		row =  row - 3;
    }
    
    public void panDown()
    {
    	if(!lock)
    		row = row + 5;
    }   
    
    public int getRow()
    {
    	return row;
    	
    } 
    	
    public int getCol()
    {
    	return col;
    }	   
    
    public int getWidth()
    {
    	return width;
    	
    }
    
 	public int getHeight()
 	{
 		return height;
 	}   
    
    public void focus(TestEnt player)
    {
    	double ra = player.getRow();
    	double ca = player.getCol();
    	ra = ra+ player.getHeight()/2;
    	ca = ca + player.getWidth()/2;
    	row = (int)(ra - height/2);
    	col = (int)(ca - width/2);
    		
    }
    
    public void lockScreen()
    {	
    	lock = !lock;  	
    }
    
    
    
    
  
}