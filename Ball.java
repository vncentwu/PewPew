/**
 * @(#)Ball.java
 *
 *
 * @author 
 * @version 1.00 2013/3/26
 */


public class Ball {

	public double x;
	public double y;
	public double width;
	public double direction;
	public double speed;
	


    public Ball() 
    {
    	width = 20;
    	x = (400/2)-(width/2);
    	y = (600/2)-(width/2);
    	//direction = Math.random()*366;
    	//direction = Math.random() * (Math.PI * 2);
    	direction = 1;
    	System.out.println(direction);
    	speed = 10;
    	
    }
    
    public void move()
    {
		
    	//direction++;
    	x = x + Math.cos(direction) * speed;
    	y = y + Math.sin(direction) * speed;
    	System.out.println(direction);
    	if(x<=0 || y<=0 || x>=800 - 2 *width | y>=600 - 2*width)
    	{
    		System.out.println("BOUNCING");
    		if(direction>=0 && direction <= Math.PI)
    		{
    			direction = Math.PI - direction;
    		}
    		else if(direction>=Math.PI && direction <= Math.PI * 2)
    		{
    			direction = direction + Math.PI;
    			//System.out.println(Math.toDegrees(direction));
    			//direction = 180 + temp;
    			
    		}

    	}


    }
    
    
}