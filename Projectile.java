/**
 * @(#)Projectile.java
 *
 *
 * @author 
 * @version 1.00 2013/6/2
 */
import java.util.ArrayList;

public class Projectile extends TestEnt{

	int gunType;
	int rate;
	int distance = 0;
	int power;
	boolean flipped;
	public static final int PROJECTILE_TYPE = 5;
	public static boolean oneHit = false;
	
    public Projectile(double dir, double row, double col, ArrayList<TestEnt> temp)
    {
    	super(temp);
    	setDirection(dir);
    	setHeight(6);
    	setWidth(6);
    	setRow(row);
    	setCol(col);
    	setSpeed(21);
    	setRadius(3);
    	type = 5;
    	power = 5;
    }
    
    public Projectile(double dir, double row, double col, ArrayList<TestEnt> temp, int power)
    {
    	super(temp);
    	setDirection(dir);
    	setHeight(6);
    	setWidth(6);
    	setRow(row);
    	setCol(col);
    	setSpeed(7);
    	setRadius(3);
    	type = 5;
    	this.power = power;
    } 
    	   
    public Projectile(double dir, double row, double col, ArrayList<TestEnt> temp, Gun gun)
    {
    	super(temp);
    	setDirection(dir);
    	setHeight(6);
    	setWidth(6);
    	setRow(row);
    	setCol(col);
    	setSpeed(gun.speed);
    	setRadius(3);
    	rate = gun.rate;
    	if(oneHit)
    	{
    		this.damage = 1000000;
    	}
    	else
    		this.damage = gun.getDamage();
    	type = 5;
    	power = gun.power;
    }  
    	
   	public void setPower(int power)
   	{
   		this.power = power;
   	}  
    public void act()
    {
    	distance++;
    	move();
    	if(distance > power)
    	{
    		damage = (int)(damage * .99);	
    		if(damage == 0)
    		{
    			damage = 1;
    		}
    		
    		
    	}
    	
    	if(distance > 200)
    		vanish();
    	
    	
    }
    
    public void reflect()
    {
    	if(!flipped)
    		direction = direction + 180;
    	
    	flipped = true;
    	
    }
    
    public void afterCollide()
    {
    
    		remove = true;	
    		setRow(-100);
    		setCol(-100);
    		


    }
    
    public void applyDamage(TestEnt e)
    {
    	e.subtractHP(damage);
    	
    }

    
    
    
}