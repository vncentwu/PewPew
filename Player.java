/**
 * @(#)Player.java
 *
 *
 * @author 
 * @version 1.00 2013/6/2
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

public class Player extends TestEnt{

	ArrayList<Gun> guns = new ArrayList<Gun>();
	int gunPos;
	static int kills = 0;
	static int currentTick;
	static int stacks;
	boolean start;
	static Clip clip;
	public static final int PLAYER_TYPE = 2;
	public static boolean unlimited;
	public static boolean godmode;
	public static boolean doubleKill;
	public static boolean tripleKill;
	public static boolean megaKill;
	public static boolean ultraKill;
	public static boolean monsterKill;
	public static boolean wowKill;
	public static boolean godlike;		
	public static boolean killingSpree;
	
		
		
		
			
    public Player(ArrayList<TestEnt> temp) {
    	
    	super(temp);
    	type = 2;
    	hp = 500;
    	gunPos = 0;
    	init();
    	name = "Player";
    	maxHP = 500;
    }
 
    public Player(ArrayList<TestEnt> temp, int row, int col) {
    	
    	super(temp, row, col);
    	type = 2;
    	hp = 500;
    	gunPos = 0;
    	init();
    	name = "Player";
    	maxHP = 500;
    }
    
    public void init()
    {
    	String soundName = "doublekill.wav";
    	
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
     
     
     
     
    public static void addKill()
    {

    	currentTick = Game.tick;
    	stacks++;
    	kills++;
    	
    	switch(stacks)
    	{
    		case 2: 
  			 		String sname = "doublekill.wav";
  			 		doubleKill = true;
					AudioInputStream audioInputStream = null;
					try{
					audioInputStream = AudioSystem.getAudioInputStream(new File(sname).getAbsoluteFile());	
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
					clip.stop();
	    			clip.setFramePosition(0);
	    			clip.start();	
							
  					break;  
  						
     		case 3: 
  			 		String sname2 = "triplekill.wav";
  			 		tripleKill = true;
					AudioInputStream audioInputStream2 = null;
					try{
					audioInputStream2 = AudioSystem.getAudioInputStream(new File(sname2).getAbsoluteFile());	
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
						clip.open(audioInputStream2);			
					}
					catch(LineUnavailableException e)
					{
						
					}  
					catch(IOException ex){
					
					} 
					clip.stop();
	    			clip.setFramePosition(0);
	    			clip.start();	
							
  					break;  						
  						
     		case 4: 
  			 		String sname3 = "megakill.wav";
  			 		megaKill = true;
					AudioInputStream audioInputStream3 = null;
					try{
					audioInputStream3 = AudioSystem.getAudioInputStream(new File(sname3).getAbsoluteFile());	
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
						clip.open(audioInputStream3);			
					}
					catch(LineUnavailableException e)
					{
						
					}  
					catch(IOException ex){
					
					} 
					clip.stop();
	    			clip.setFramePosition(0);
	    			clip.start();	
							
  					break;    						
  						
     		case 5: 
  			 		String sname4 = "ultrakill.wav";
  			 		ultraKill = true;
					AudioInputStream audioInputStream4 = null;
					try{
					audioInputStream4 = AudioSystem.getAudioInputStream(new File(sname4).getAbsoluteFile());	
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
						clip.open(audioInputStream4);			
					}
					catch(LineUnavailableException e)
					{
						
					}  
					catch(IOException ex){
					
					} 
					clip.stop();
	    			clip.setFramePosition(0);
	    			clip.start();	
							
  					break;      						
      		case 6: 
  			 		String sname5 = "monsterkill.wav";
  			 		monsterKill = true;
					AudioInputStream audioInputStream5 = null;
					try{
					audioInputStream5 = AudioSystem.getAudioInputStream(new File(sname5).getAbsoluteFile());	
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
						clip.open(audioInputStream5);			
					}
					catch(LineUnavailableException e)
					{
						
					}  
					catch(IOException ex){
					
					} 
					clip.stop();
	    			clip.setFramePosition(0);
	    			clip.start();	
							
  					break;       						
       		case 7: 
       				wowKill = true;
  			 		String sname6 = "wowkill.wav";
					AudioInputStream audioInputStream6 = null;
					try{
					audioInputStream6 = AudioSystem.getAudioInputStream(new File(sname6).getAbsoluteFile());	
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
						clip.open(audioInputStream6);			
					}
					catch(LineUnavailableException e)
					{
						
					}  
					catch(IOException ex){
					
					} 
					clip.stop();
	    			clip.setFramePosition(0);
	    			clip.start();	
							
  					break;  						
        		case 8: 
        			godlike = true;
  			 		String sname7 = "godlike.wav";
					AudioInputStream audioInputStream7 = null;
					try{
					audioInputStream7 = AudioSystem.getAudioInputStream(new File(sname7).getAbsoluteFile());	
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
						clip.open(audioInputStream7);			
					}
					catch(LineUnavailableException e)
					{
						
					}  
					catch(IOException ex){
					
					} 
					clip.stop();
	    			clip.setFramePosition(0);
	    			clip.start();	
							
  					break;   						
  									
    	}		
		if(stacks >= 9)
		{
					killingSpree = true;
  			 		String sname8 = "killingspree.wav";
					AudioInputStream audioInputStream8 = null;
					try{
					audioInputStream8 = AudioSystem.getAudioInputStream(new File(sname8).getAbsoluteFile());	
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
						clip.open(audioInputStream8);			
					}
					catch(LineUnavailableException e)
					{
						
					}  
					catch(IOException ex){
					
					} 
					clip.stop();
	    			clip.setFramePosition(0);
	    			clip.start();			
			
			
			
			
		}
    		
    		
    		
    		
    		
    }
   	
    
     
        
    public void fire(Game game)	
    {
    	
    }
    
    public void addGun(Gun gun)
    {
    	guns.add(gun);
    }
    
    public Gun getGun()
    {
    	
    	return guns.get(gunPos);
    	
    }
    
    public static void update()
    {
    	//System.out.println(Game.tick);
    	//System.out.println(currentTick);
    	if(Game.tick - currentTick > 150)
    	{
    		stacks = 0;
    		//System.out.println("!!");
    	}
    	
    	
    	
    	
    }
    
    public void switchGun(int pos)
    {
    	if(pos <= guns.size() -1)
    	{
    		gunPos = pos;
    	}
    	
    	
    	
    }

    
}