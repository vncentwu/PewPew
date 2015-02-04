/**
 * @(#)Gun.java
 *
 *
 * @author 
 * @version 1.00 2013/6/7
 */
import java.util.ArrayList;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ConcurrentModificationException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.io.*;

public class Gun {

	int cap;
	int bullets;
	int magazines;
	int damage;
	int rate;
	int type;
	int speed;
	int power;
	Clip clip;
	
	public static final int M4 = 0;
	public static final int GLOCK = 1;
	public static final int AWP = 2;
	public static final int SHOTGUN = 3;
	public static final int SABER = 4;
	public static final int PORTAL_GUN = 5;
		
    public Gun() 
    {
    	cap = 100;
    	bullets = 100;
    	magazines = 3;
    	damage = 10;
    	rate = 1;
    	speed = 5;
    	type = 5;
    	init();
    }
	public Gun(int type)
	{
		init();
		switch(type)
		{
			case M4:
		    	cap = 50;
		    	bullets = 50;
		    	magazines = 3;
		    	damage = 10;
		    	rate = 2;
		    	speed = 60;
		    	this.type = M4;
		    	power = 30;
		    	//rate is the speed at which the gun fires, speed is the speed of the projectile
		    	//the higher the rate is, the longer
		    	
		    break;
		    	
			case GLOCK:
		    	cap = 15;
		    	bullets = 15;
		    	magazines = 3;
		    	damage = 15;
		    	rate = 6;
		    	speed = 60;
		    	power = 20;
		    	this.type = GLOCK;
		    	
		    	
		    break;
		    
			case AWP:
		    	cap = 10;
		    	bullets = 10;
		    	magazines = 3;
		    	damage = 150;
		    	rate = 60;
		    	speed = 90;
		    	power = 10;
		    	this.type = AWP;
		    	
		    break;	
		    		    		    	
			case SHOTGUN:
		    	cap = 8;
		    	bullets = 8;
		    	magazines = 3;
		    	damage = 60;
		    	rate = 30;
		    	speed = 60;
		    	power = 15;
		    	this.type = SHOTGUN;
		    	
		    break;
		    
		    case SABER:
		    	cap = 500;
		    	bullets = 500;
		    	magazines = 1;
		    	damage = 5;
		    	rate = 30;
		    	speed = 60;
		    	power = 15;
		    	this.type = SABER;
		    	
		    	break;
		    		
		    case PORTAL_GUN:  		
		    	cap = 10;
		    	bullets = 10;
		    	magazines = 1;
		    	damage = 0;
		    	rate = 30;
		    	speed = 60;
		    	power = 15;
		    	this.type = PORTAL_GUN;	
		    
		    break;		    		
		    		
		    			    			    				
		}
		
		
		
	}    
    
    
    
    
    public int getDamage()
    {
    	return damage;
    }
    
    public int getType()
    {
    	
    	return type;
    }
    
    public void reload()
    {
    	if(magazines>0)
    	{
	    	magazines--;
	    	bullets = cap;	
    		clip.stop();
    		clip.setFramePosition(0);
    		clip.start();
    	}
    	
    	
    	
    }
    
    public void init()
    {
    	String soundName = "reload.wav";
    	AudioInputStream audioInputStream = null;
 		try{
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());   	
 		}
		catch(UnsupportedAudioFileException e)
		{
			
		}
		catch(IOException ex)
		{
			
		}    		
		try
		{
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		}
		catch(LineUnavailableException e)
		{
			
		}  
		catch(IOException ex){
		
		}
	} 
		
    public void addMag()
    {
    	magazines++;
    }
    
    public String toString()
    {
    	if(type == M4)
    	{
    		return "M4: " + bullets + "/" + cap;	
    	}
    	else if(type == GLOCK)
    	{
    		return "GLOCK: " + bullets + "/" + cap;
    	}
    	else if(type == AWP)
    	{
    		return "AWP: " + bullets + "/" + cap;
    	}
    	else if(type == SHOTGUN)
    	{
    		return "SHOTGUN: " + bullets + "/" + cap;
    	}
    	else if(type == SABER)
    	{
    		return "LIGHTSABER: " + bullets + "/" + cap;
    	}
    	else if(type == PORTAL_GUN)
    	{
    		return "PORTALGUN: " + bullets + "/" + cap;
    	}    	
    	else
    	{
    		return "ERROR";
    	}
    	
    }
    
    public void shoot()
    {
    	//loses ammo
    	if(!(Player.unlimited))
    		bullets--;
    }   
}