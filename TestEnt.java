/**
 * @(#)TestEnt.java
 *
 *
 * @author 
 * @version 1.00 2013/6/2
 */
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ConcurrentModificationException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.util.*;

public class TestEnt {

	int type = 0;
	double rowTL;
	double colTL;
	double width;
	double height;
	double direction;
	double dotWidth;
	double dotHeight;
	double speed;
	double radius;
	ArrayList<TestEnt> ents;
	boolean remove = false;
	int hp = 100;
	int maxHP = 100;
	int damage = 10;
	Clip clip;
	String name;
	boolean selected;
	double armor = 50;
	double maxArmor = 100;
	int life = 0;
	

	public TestEnt()
	{
		
	}
    public TestEnt(ArrayList<TestEnt> temp)
    {
    	rowTL = 400;
    	colTL = 400;
    	width = 40;
    	height = 40;
    	radius = width/2;
    	direction = 0;
    	dotWidth = 10;
    	dotHeight = 10;
    	speed = 5;
    	ents = temp;
    }
    
     public TestEnt(ArrayList<TestEnt> temp, int row, int col)
    {
    	rowTL = row;
    	colTL = col;
    	width = 40;
    	height = 40;
    	radius = width/2;
    	direction = 0;
    	dotWidth = 10;
    	dotHeight = 10;
    	speed =10;
    	ents = temp;
    }   
    

	
	
	
    public boolean handleCollisions()
    {
    	for(TestEnt e: ents)
    	{
    		
    		if(!(e == this))
    		{
    			//System.out.println("hi");
    			double tempRow = e.getRow() + e.getRadius();
    			double tempCol = e.getCol() + e.getRadius(); 
    			double myRow = rowTL + radius;
    			double myCol = colTL + radius; 			
    			double y = tempRow - myRow;
    			double x= tempCol - myCol;
    			double result = Math.pow(x, 2) + Math.pow(y, 2);
    			double duoWidth = (width/2) + (e.getWidth()/2);
    			double duoWidth2 = Math.pow(duoWidth, 2);
    			if(result < duoWidth2)
    			{
    				//System.out.println("TRUE!!!");
    				return true;
    				
    			}
    			else
    			{
    				//System.out.println("FALSE!!!");
    				return false;
    			}
    			
    			
    		}
    		
    		
    		
    	}
    	
    	
    	return false;
    }

    public boolean handleNextCollisions(double raw, double cal) throws ConcurrentModificationException
    {
    	if(type == 6)
    		return false;
    	//System.out.println(ents.size());
    	try
    	{
    		

    	for(TestEnt e: ents)
    	{
    		if(!(e == this))
    		{
    			if(e.type == 0 || e.type == 2)
    			{
	    			try{
		    			//System.out.println("once");
		    			double tempRow = e.getRow() + e.getRadius();
		    			double tempCol = e.getCol() + e.getRadius(); 
		    			double myRow = raw + radius;
		    			double myCol = cal + radius; 			
		    			double y = tempRow - myRow;
		    			double x= tempCol - myCol;
		    			double result = Math.pow(x, 2) + Math.pow(y, 2);
		    			double duoWidth = (width/2) + (e.getWidth()/2);
		    			double duoWidth2 = Math.pow(duoWidth, 2);
		    			if(result <= duoWidth2)
		    			{
		    				if(type != 6)
		    					applyDamage(e);
		    				return true;
		    				
		    			}    						
	    			}
	    			catch(ConcurrentModificationException oops)
	    			{
	    				System.out.println("oh well");
	    			}    				
    			}
    			else if(e.type == 1)
    			{
    				Rectangle r1 = new Rectangle((int)cal, (int)raw, (int)width, (int)height);
    				Rectangle r2 = new Rectangle((int)e.getCol() + 2, (int)e.getRow() + 2, (int)e.getWidth()-4, (int)e.getHeight()- 4);
    				//System.out.println("hi");
    				if(r1.intersects(r2))
    				{
    					return true;
    				}
    				
    				
    				
    			}
				else if(e.type == 4)
				{
					if(type != 5)
					{
						Rectangle r1 = new Rectangle((int)cal, (int)raw, (int)width, (int)height);
	    				Rectangle r2 = new Rectangle((int)e.getCol() + 2, (int)e.getRow() + 2, (int)e.getWidth()-4, (int)e.getHeight()- 4);
	    				//System.out.println("hi");
	    				if(r1.intersects(r2))
	    				{
	    					return true;
	    				}						
	
					}
					
					
					
				}
				else if(e.type == 7 && type == 2)
				{
					if(type != 5)
					{
						Rectangle r1 = new Rectangle((int)cal, (int)raw, (int)width, (int)height);
	    				Rectangle r2 = new Rectangle((int)e.getCol() + 2, (int)e.getRow() + 2, (int)e.getWidth()-4, (int)e.getHeight()- 4);
	    				//System.out.println("hi");
	    				if(r1.intersects(r2))
	    				{
	    					
	    					getGun().addMag();
	    					e.die();
	    					
	    					
	    					return false;
	    				}						
	
					}					
	
				}
				else if(e.type == Portal.IN && (type == Player.PLAYER_TYPE || type == Projectile.PROJECTILE_TYPE||type == Grenade.GRENADE_TYPE))
				{
	    			try{
		    			//System.out.println("once");
		    			double tempRow = e.getRow() + e.getRadius();
		    			double tempCol = e.getCol() + e.getRadius(); 
		    			double myRow = raw + radius;
		    			double myCol = cal + radius; 			
		    			double y = tempRow - myRow;
		    			double x= tempCol - myCol;
		    			double result = Math.pow(x, 2) + Math.pow(y, 2);
		    			double duoWidth = (width/2) + (e.getWidth()/2);
		    			double duoWidth2 = Math.pow(duoWidth, 2);
		    			if(result <= duoWidth2)
		    			{
							TestEnt temp = null;
							for(TestEnt e2: ents)
							{
								if(e2.type == Portal.OUT && !(e2.isGone()))
								{
									temp = e2;
								}
								if(temp!= null)
								{
									setRow(temp.rowTL + 40 * Math.sin(Math.toRadians(getDirection())));
									setCol(temp.colTL + 40 * Math.cos(Math.toRadians(getDirection())));
		
		
								}
	
							}		    				
		    			}					
	    			}
	    			catch(ConcurrentModificationException oh)
	    			{
	    				
	    			}
				}
				else if(e.type == Portal.OUT && (type == Player.PLAYER_TYPE || type == Projectile.PROJECTILE_TYPE||type == Grenade.GRENADE_TYPE))
				{
	    			try{
		    			//System.out.println("once");
		    			double tempRow = e.getRow() + e.getRadius();
		    			double tempCol = e.getCol() + e.getRadius(); 
		    			double myRow = raw + radius;
		    			double myCol = cal + radius; 			
		    			double y = tempRow - myRow;
		    			double x= tempCol - myCol;
		    			double result = Math.pow(x, 2) + Math.pow(y, 2);
		    			double duoWidth = (width/2) + (e.getWidth()/2);
		    			double duoWidth2 = Math.pow(duoWidth, 2);
		    			if(result <= duoWidth2)
		    			{
							TestEnt temp = null;
							for(TestEnt e2: ents)
							{
								if(e2.type == Portal.IN && !(e2.isGone()))
								{
									temp = e2;
								}
								if(temp!= null)
								{
									setRow(temp.rowTL + 40 * Math.sin(Math.toRadians(getDirection())));
									setCol(temp.colTL + 40 * Math.cos(Math.toRadians(getDirection())));
		
								}
	
							}		    				
		    			}					
	    			}
	    			catch(ConcurrentModificationException oh)
	    			{
	    				
	    			}
				}

    			
    			
    		}
    		
    		
    		
    	}
    	
    	
    	return false;
    }
	catch(ConcurrentModificationException hj)
	{
		
	}
	return false;
    }
    
	public Gun getGun()
	{
		return null;
	}
	
	public int getHP()
	{
		return hp;
	}
	
	public void die()
	{
			Player.addKill();
    		remove = true;	
    		setRow(-10000);
    		setCol(-10000);		
	}
	
	public void vanish()
	{
			
    		remove = true;	
    		setRow(-10000);
    		setCol(-10000);
    }
    
	public void applyDamage(TestEnt e)
	{
		
	}
	
	public double getHeight()
	{
		return height;
	}
	public void move()
	{
		if(isGone())
			return;
		//when changing this, make sure to change all the other movement modififiers
		double temp = Math.toRadians(direction);
		double tempRow = (rowTL + (speed*Math.sin(temp)));
		double tempCol = (colTL + (speed*Math.cos(temp)));
		//boolean ow = handleNextCollisions(tempRow, tempCol);
		boolean ow = false;
		if(ow)
		{
			afterCollide();	
		}
		else
		{
			noCollide();
			
		}
							
	}
	
	public void moveRight()
	{
		System.out.println(direction);
		double tempDirection = direction + 90;
		System.out.println(tempDirection);
		double temp = Math.toRadians(tempDirection);
		double tempRow = (rowTL + (speed*Math.sin(temp)));
		double tempCol = (colTL + (speed*Math.cos(temp)));
		boolean ow = handleNextCollisions(tempRow, tempCol);
		if(ow)
		{
			afterCollide();	
		}
		else
		{
			noCollideRight();
			
		}
							
	}	
	
	public void moveLeft()
	{
		double temp = Math.toRadians(direction) - Math.PI/2;
		double tempRow = (rowTL + (speed*Math.sin(temp)));
		double tempCol = (colTL + (speed*Math.cos(temp)));
		boolean ow = handleNextCollisions(tempRow, tempCol);
		if(ow)
		{
			afterCollide();	
		}
		else
		{
			noCollideLeft();
			
		}
							
	}		
	
	public void back()
	{
		double temp = Math.toRadians(direction);
		double tempRow = (rowTL - (speed*Math.sin(temp)));
		double tempCol = (colTL - (speed*Math.cos(temp)));
		boolean ow = handleNextCollisions(tempRow, tempCol);
		if(ow)
		{
			afterCollideBack();	
		}
		else
		{
			noCollideBack();
			
		}
	}
	
	public void noCollide()
	{
		double temp = Math.toRadians(direction);
		double tempRow = (rowTL + (speed*Math.sin(temp)));
		double tempCol = (colTL + (speed * Math.cos(temp)));


		rowTL = tempRow;
		
		colTL = tempCol;
			
	}
	
	public void noCollideRight()
	{
		double temp = Math.toRadians(direction) + Math.PI/2;
		double tempRow = (rowTL + (speed*Math.sin(temp)));
		double tempCol = (colTL + (speed * Math.cos(temp)));


		rowTL = tempRow;
		
		colTL = tempCol;
			
	}	
		
	public void noCollideLeft()
	{
		double temp = Math.toRadians(direction) - Math.PI/2;
		double tempRow = (rowTL + (speed*Math.sin(temp)));
		double tempCol = (colTL + (speed * Math.cos(temp)));


		rowTL = tempRow;
		
		colTL = tempCol;
			
	}	
			
	public void noCollideBack()
	{
		double temp = Math.toRadians(direction);
		double tempRow = (rowTL - (speed*Math.sin(temp)));
		double tempCol = (colTL - (speed * Math.cos(temp)));


		rowTL = tempRow;
		
		colTL = tempCol;
			
	}
		
	public void afterCollide()
	{

	}
	
	public void afterCollideBack()
	{
		
	}
	public void rotateLeft()
	{
		direction = direction -5;
	}

	public void rotateRight()
	{
		direction = direction + 5;
	}
    
    public double getRowCenter()
    {
    	return rowTL + (height/2);
    }
    
	public double getColCenter()
	{
		return colTL + (width/2);
	}  
		
	public double getDotCenterRow()
	{
		return getDotRow() + dotHeight/2;
		
	}	
	
	public double getDotCenterCol()
	{
		return getDotCol() + dotWidth/2;
	}	
		
		
	public double getDotRow()
	{
		double temp = Math.toRadians(direction);	
		return getRowCenter() + ((height/2) * Math.sin(temp)) - getDotHeight()/2;
	}		
		
	public double getDotCol()
	{
		double temp = Math.toRadians(direction);
		return getColCenter() + ((width/2) * Math.cos(temp)) - getDotWidth()/2;		
	}		  
 
	public double getDotWidth()
	{
		return dotWidth;
	}
 
	public double getDotHeight()
	{
		return dotHeight;
	} 
    
    public void setDirection(double dir)
    {
    	direction = dir;
    }
    
    public void act()
    {
    	life++;
    }
    
    public void subtractHP(int amount)
    {
    	if(Player.godmode && type == Player.PLAYER_TYPE)
    		return;
    	
    	double half3 = ((double)(amount))/2;
    	double half4 = ((double)(amount))/2;
    	int half = (int)Math.ceil(half3);
    	int half2 = (int)Math.floor(half4);
    	if(armor>0)
    	{
    		if(armor - half < 0)
    		{
    			half = half - (int)armor;
    			armor = 0;
    			hp = hp - half - half2;
    		}
    		else
    		{
    			armor = armor - half;
    			hp = hp - half2;
    			
    		}
    			
    	}
    	else
    		hp = hp - amount;
    	if(hp<= 0)
    		die();
    }
    public boolean isGone()
    {
    	return remove;
    }
    
    public void fire(Game game)
    {
    	
    }
    
    public double getDirection()
    {
    	return direction;
    }
    
    public double getRadius()
    {
    	return radius;
    }
    public void setHeight(int set)
    {
    	height = set;
    }
 
	public void setWidth(int set)
	{
		width = set;
	}   
	
	public void setRadius(int set)
	{
		radius = set;
	}
	public void setRow(double set)
	{
		rowTL = set;
	}
	
	public void setCol(double set)
	{
		colTL = set;
	}
	
	public double getRow()
	{
		return rowTL;
		
	}
	
	public double getCol()
	{
		return colTL;
	}		

	public void setSpeed(double set)
	{
		speed = set;
	}

	public double getWidth()
	{
		return width;
	}

	public void removeSelf()
	{
			try
			{
		     Iterator<TestEnt> it = ents.iterator();
		      while(it.hasNext()) {
		         TestEnt e = it.next();
		         if(e.isGone())
		         	it.remove();
		      }				
						
					
			/*	for(int i = 0; i<ents.size(); i++)
				{
					TestEnt e= ents.get(i);
					if(e.isGone())
					{
						ents.remove(i);
					}
					
				}*/				
	
			}
			catch(ConcurrentModificationException e)
			{
				System.out.println("oh well");
			}
	}
	
	public ArrayList<TestEnt> getEnts()
	{
		return ents;
	}

}