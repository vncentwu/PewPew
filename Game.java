import java.awt.Graphics; 
import java.util.*;
import java.awt.Graphics2D; 
import java.util.HashSet;
import java.awt.Graphics2D.*; 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.*; 
import java.awt.*;
import javax.swing.*;
import static java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.Color; 
import javax.swing.BoxLayout;
import javax.swing.JFrame; 
import javax.imageio.*;
import javax.swing.JPanel; 
import java.util.ConcurrentModificationException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.UIManager;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Toolkit.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Cursor;
import java.awt.geom.Line2D;
import java.awt.GridLayout;
import javax.swing.JScrollPane;




public class Game extends JPanel 
{ 
	
	static int count;
	static private long now;
	static private int framesCount = 0;
	static private int framesCountAvg=0; 
	static private long framesTimer=0;
	
	
    JTextArea textArea;
    

	private int test;
	boolean boxesOn;
	//private JPanel panel;
	static JFrame frame = new JFrame();
	static JPanel panel = new JPanel();
	boolean saberin;
	JDialog dialog;
	Image background2 = Toolkit.getDefaultToolkit().getImage("epicbackground.jpg");
	BufferedImage cursor;
	BufferedImage ammoPic;
	BufferedImage splash;
	ArrayList<TestEnt> ents = new ArrayList<TestEnt>();
	Player ent = new Player(ents, 200, 200);
	Camera camera;
	Curse curse = new Curse();
	int recoil;
	Clip clip;
	Clip music;
	Clip grenade;
	Clip saberClip;
	public static int tick = 0;
	int sprayTick;
	ArrayList<Gun> guns = new ArrayList<Gun>();
	int button = 3;
	JTextArea field;
	JTextField input;
	String consoleText = "";
	boolean consoleOn;
	String command = "";
	boolean showFPS = true;
	TestEnt selected;
	Chunk currentChunk = new Chunk(5, 0, 1000, 1000);
	Chunk northChunk = new Chunk(0, 0, 1000, 1000);
	Chunk southChunk = new Chunk(0, 0, 1000, 1000);
	Chunk eastChunk = new Chunk(0, 0, 1000, 1000);
	Chunk westChunk = new Chunk(0, 0, 1000, 1000);
	Chunk northEastChunk = new Chunk(0, 0, 1000, 1000);
	Chunk northWestChunk = new Chunk(0, 0, 1000, 1000);
	Chunk southWestChunk = new Chunk(0, 0, 1000, 1000);
	Chunk southEastChunk = new Chunk(0, 0, 1000, 1000);
	ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	ArrayList<Chunk> chunkMap = new ArrayList<Chunk>();
	
	
	public Game()
	{
		addKeyListener(new MyKeyListener());
		frame.addKeyListener(new MyKeyListener());
		frame.addMouseListener(new MyMouseListener());
		frame.addMouseMotionListener(new MyMotionListener());		
		setLayout(new FlowLayout());
		
	}


	public void add()
	{
		test++;
	}
	
	
  public static void main(String[] args) throws ConcurrentModificationException, UnsupportedAudioFileException, LineUnavailableException, IOException
  { 
  	
  	
	Game game = new Game();
	game.init();
	
	game.add();
    //JFrame frame = new JFrame(); 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setUndecorated(true);
    //frame.setLayout(new FlowLayout());
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

	// Create a new blank cursor.
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
	    cursorImg, new Point(0, 0), "blank cursor");
	
	// Set the blank cursor to the JFrame.
	frame.getContentPane().setCursor(blankCursor);    
	
	
    frame.add(game); 
    
    	
    //MyKeyListener hell = new MyKeyListener();

    frame.setSize(game.camera.getWidth(),game.camera.getHeight()); 

    frame.setVisible(true); 

    count = 0;
    
    
    
    while(true)
    {
    	try {
				Thread.sleep(1);
			} catch(Exception e) {
				break;
			}
			
		long beforeTime = System.nanoTime();
        //... Update program & draw program...
        // DRAW FPS: 
        now=System.currentTimeMillis(); 	
			
		if(!game.consoleOn)
			game.update();
		frame.repaint();
		
		framesCount++;
		
        if(now-framesTimer>1000)
        { 
              framesTimer=now; 
              framesCountAvg=framesCount; 
              framesCount=0; 
        }	
    		
    }
		

  } 

	public void saberCheck()
	{
		if(ent.getGun().type == Gun.SABER && ent.getGun().bullets > 0)
		{
			//saber handler
			ent.getGun().shoot();
			double x1 = ent.getDotCenterCol();
			double y1 = ent.getDotCenterRow();
			double x2 = ent.getDotCenterCol()+ 50* Math.cos(Math.toRadians(ent.getDirection()));
			double y2 = ent.getDotCenterRow()+ 50* Math.sin(Math.toRadians(ent.getDirection()));
			
			if(saberin)
			{
				saberin = false;
				saberClip.stop();
				saberClip.setFramePosition(0);
				saberClip.start();			
			
			}

			
			
			
			Line2D line = new Line2D.Double(x1, y1, x2, y2);
			for(TestEnt e: ents)
			{
				if(e instanceof Bot)
				{
					
					Line2D l1 = new Line2D.Double(e.colTL, e.rowTL, e.colTL + e.width, e.rowTL);
					Line2D l2 = new Line2D.Double(e.colTL, e.rowTL, e.colTL, e.rowTL + e.height);
					Line2D l3 = new Line2D.Double(e.colTL + e.width, e.rowTL, e.colTL + e.width, e.rowTL + e.height);
					Line2D l4 = new Line2D.Double(e.colTL, e.rowTL + e.height, e.colTL, e.rowTL + e.width + e.height);
					//System.out.println("at least");
					
					if(line.intersectsLine(l1)||line.intersectsLine(l2)||line.intersectsLine(l3)||line.intersectsLine(l4))
					{
						e.subtractHP(ent.getGun().getDamage());
					}
					
					
					
					
					
				}
				else if(e instanceof Projectile)
				{
					Line2D l1 = new Line2D.Double(e.colTL, e.rowTL, e.colTL + e.width, e.rowTL);
					Line2D l2 = new Line2D.Double(e.colTL, e.rowTL, e.colTL, e.rowTL + e.height);
					Line2D l3 = new Line2D.Double(e.colTL + e.width, e.rowTL, e.colTL + e.width, e.rowTL + e.height);
					Line2D l4 = new Line2D.Double(e.colTL, e.rowTL + e.height, e.colTL, e.rowTL + e.width + e.height);
					//System.out.println("at least");
					
					if(line.intersectsLine(l1)||line.intersectsLine(l2)||line.intersectsLine(l3)||line.intersectsLine(l4))
					{
						((Projectile)(e)).reflect();
					}					
					
					
					
					
					
					
					
					
				}
			
			
			
			
			
			
			
			
			}
			
			
			
		}		
	}


	public void init()
	{
		input= new JTextField(50);
	
		input.addKeyListener(new ConsoleKeyListener());

		
		input.addAncestorListener( new RequestFocusListener() );
		field = new JTextArea();
		field.setEditable(false);
		field.setLineWrap(true);
		JScrollPane area = new JScrollPane(field, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		field.setRows(10);
		field.setText(consoleText);
		chunkCalculator();
		removeChunkDuplicates();


		
		selected = new TestEnt();
		Gun glock = new Gun(Gun.GLOCK);
		Gun m4 = new Gun(Gun.M4);
		Gun awp = new Gun(Gun.AWP);
		Gun shotgun = new Gun(Gun.SHOTGUN);
		Gun saber =  new Gun(Gun.SABER);
		Gun portalGun = new Gun(Gun.PORTAL_GUN);
		guns.add(glock);
		guns.add(m4);
		guns.add(awp);
		guns.add(shotgun);
		guns.add(saber);
		guns.add(portalGun);
		
		ent.addGun(glock);
		ent.addGun(m4);
		ent.addGun(awp);
		ent.addGun(shotgun);
		ent.addGun(saber);
		ent.addGun(portalGun);
		
		chunks.add(currentChunk);
		chunks.add(northChunk);
		chunks.add(southChunk);
		chunks.add(eastChunk);
		chunks.add(westChunk);
		chunks.add(northWestChunk);
		chunks.add(northEastChunk);
		chunks.add(southWestChunk);
		chunks.add(southEastChunk);

		chunkMap.add(currentChunk);
		chunkMap.add(northChunk);
		chunkMap.add(southChunk);
		chunkMap.add(eastChunk);
		chunkMap.add(westChunk);
		chunkMap.add(northWestChunk);
		chunkMap.add(northEastChunk);
		chunkMap.add(southWestChunk);
		chunkMap.add(southEastChunk);		
		//updateChunkLocations();
		//spawnInChunks();
		
		
		try{
		cursor = ImageIO.read(new File("crosshair-md.PNG"));
		ammoPic = ImageIO.read(new File("ammo.GIF"));	
		//splash = ImageIO.read(new File("five5.PNG"));	
		}
		catch(IOException e){
		
		}


		
		String soundName = "m4.wav";
		String musicName = "music1.wav";
		String nadeName = "explode3.wav";
		String saberName = "saber.wav";
		AudioInputStream audioInputStream = null;
		AudioInputStream musicStream = null;
		AudioInputStream nadeStream = null;
		AudioInputStream saberStream = null;
		try{
		audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		musicStream = AudioSystem.getAudioInputStream(new File(musicName).getAbsoluteFile());	
		nadeStream = AudioSystem.getAudioInputStream(new File(nadeName).getAbsoluteFile());	
		saberStream = AudioSystem.getAudioInputStream(new File(saberName).getAbsoluteFile());		
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
			music = AudioSystem.getClip();
			grenade =AudioSystem.getClip();
			saberClip = AudioSystem.getClip();
			clip.open(audioInputStream);
			grenade.open(nadeStream);
			saberClip.open(saberStream);			
			//music.open(musicStream);
			//music.start();			
		}
		catch(LineUnavailableException e)
		{
			
		}  
		catch(IOException ex){
		
		}
		
		ents.add(ent);
		ents.add(new Bot(ents));
		ents.add(new Bot(ents, 500, 700));
		ents.add(new Bot(ents, 600, 100));
		ents.add(new Bot(ents, 570, 250));
		camera = new Camera();
		//ents.add(new Water(ents, 0, 300, 320, 320));
		//ents.add(new Wall(ents, 380, 300, 20, 320));
		//ents.add(new Wall(ents, 0, 600, 20, 320));
		//ents.add(new Wall(ents, 380, 600, 20, 320));
		ents.add(new Ammo(ents, 100, 200));
				
		camera.focus(ent);
		
		
		

		
	}


	boolean timerStart = false;
	public void update() throws ConcurrentModificationException
	{

		
		if(!selected.selected)
		{
			if(!timerStart)
			{
				timerStart = true;
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
				    selected = new TestEnt();
				    timerStart = false;
				  }
				}, 5*1000);				
			}

				
		}
			

		if(Player.doubleKill)
		{
			print("Double kill");
			Player.doubleKill = false;
		}
		if(Player.tripleKill)
		{
			print("Triple kill");
			Player.tripleKill = false;
		}
		if(Player.megaKill)
		{
			print("Mega kill");
			Player.megaKill = false;
		}		
		if(Player.ultraKill)
		{
			print("Ultra kill");
			Player.ultraKill = false;
		}		
		if(Player.monsterKill)
		{
			print("Monster kill");
			Player.monsterKill = false;
		}
		if(Player.godlike)
		{
			print("Godlike");
			Player.godlike = false;
		}
		if(Player.killingSpree)
		{
			print("Killing spree!");
			Player.killingSpree = false;
		}
										
		if(held && recoil < 5)
			recoil++;
		else if(recoil > 0)
			recoil = recoil - 2;		
		
		
		
		//System.out.println(ents.size());
		Player.update();
		
		sec++;
		tick++;
		int yrow = (int)(Math.random() * 720);
		int ycol= (int)(Math.random() * 1280);
		if(held)
		{
			if(sec>=wait)
			{
				fire = true;
				handle();			
			}			
		}


		
		
		
		
		if(tick%100 == 0)
			spawnBots(1, 500);
		
		if(tick%300 == 0)
			spawnAmmo(1,500);
		
		if(tick%100 == 0)
		{
			try
			{
				for(int i = 0; i<ents.size(); i++)
				{
					TestEnt e= ents.get(i);
					if(e.isGone())
					{
						ents.remove(i);
					}
					
				}				
	
			}
			catch(ConcurrentModificationException e)
			{
				System.out.println("oh well");
			}

			
			
		}
		chunkCalculator();
		
		if(a||w||d||s||up||down||right||left)
			handle();
		try
		{
			for(TestEnt e: ents)
			{
	
				e.act();
	
			}			
			
		}
		catch(ConcurrentModificationException oops)
		{
			//System.out.println("oops");
		}	
			
			
		removeChunkDuplicates();

	}
	
	public void chunkCalculator()
	{
		double ruw = ent.rowTL;
		double cuw = ent.colTL;
		
		
		int chunkRow = (int)Math.floor(ruw/1000);
		int chunkCol = (int)Math.floor(cuw/1000);
		//System.out.println(chunkRow);
		//System.out.println(chunkCol);
		int chunkc = chunkCol * 1000;
		int chunkr = chunkRow * 1000;
		//System.out.println(chunkc);
		//System.out.println(chunkr);
		
		if(currentChunk.getRectangle().equals(new Rectangle(chunkc, chunkr, 1000, 1000)))
			return;
			
			
			
		currentChunk = new Chunk(chunkc, chunkr, 1000, 1000);
		//updateChunkLocations();
		print("Entered new chunk: (" + chunkc+ "," + chunkr + ")");
		int cyw = (int)currentChunk.getRectangle().getX();
		int ryw = (int)currentChunk.getRectangle().getY();

		boolean northFound = false;
		boolean southFound = false;
		boolean eastFound = false;
		boolean westFound = false;
		boolean northEastFound = false;
		boolean southEastFound = false;
		boolean northWestFound = false;
		boolean southWestFound = false;
		boolean currentFound = false;
		
		for(Chunk e:chunkMap)
		{
			if(e.getX() == cyw && e.getY() == ryw)
				currentFound = true;
			if(e.getX() == cyw && e.getY() == ryw - 1000)	
				northFound = true;
			if(e.getX() == cyw && e.getY() == ryw + 1000)
				southFound = true;
			if(e.getX() == cyw + 1000 && e.getY() == ryw)
				eastFound = true;
			if(e.getX() == cyw - 1000 && e.getY() == ryw)
				westFound = true;
			if(e.getX() == cyw - 1000 && e.getY() == ryw - 1000)
				northWestFound = true;
			if(e.getX() == cyw + 1000 && e.getY() == ryw - 1000)
				northEastFound = true;
			if(e.getX() == cyw + 1000 && e.getY() == ryw + 1000)
				southEastFound = true;				
			if(e.getX() == cyw - 1000 && e.getY() == ryw + 1000)
				southWestFound = true;				
				
		}
		if(!currentFound)
		{
			chunkMap.add(currentChunk);
		}
		if(!northFound)
		{
			chunkMap.add(new Chunk(cyw, ryw - 1000, 1000, 1000));
		}
		if(!southFound)
		{
			chunkMap.add(new Chunk(cyw, ryw + 1000, 1000, 1000));
		}		
		if(!eastFound)
		{
			chunkMap.add(new Chunk(cyw + 1000, ryw, 1000, 1000));
		}		
		if(!westFound)
		{
			chunkMap.add(new Chunk(cyw - 1000, ryw, 1000, 1000));
		}		
		if(!northWestFound)
		{
			chunkMap.add(new Chunk(cyw - 1000, ryw - 1000, 1000, 1000));
		}	
		if(!southWestFound)
		{
			chunkMap.add(new Chunk(cyw - 1000, ryw + 1000, 1000, 1000));
		}
		if(!southEastFound)
		{
			chunkMap.add(new Chunk(cyw +  1000, ryw + 1000, 1000, 1000));
		}		
		if(!northEastFound)
		{
			chunkMap.add(new Chunk(cyw +  1000, ryw - 1000, 1000, 1000));
		}			
		
		
		removeChunkDuplicates();
        
        
        
		// add elements to al, including duplicates
		HashSet hs = new HashSet();
		hs.addAll(chunkMap);
		chunkMap.clear();
		chunkMap.addAll(hs);
		
		
		
		spawnInChunks();			
		
		
		for(Chunk e: chunkMap)
		{
			System.out.println(e.toString());
		}
		
		
	}


	public void removeChunkDuplicates()
	{
		ArrayList<Chunk> l2 = new ArrayList<Chunk>();
		
		Iterator iterator = chunkMap.iterator();

        while (iterator.hasNext())
        {
            Chunk o = (Chunk) iterator.next();
            if(!l2.contains(o)) l2.add(o);
        }
        
       chunkMap = l2;		
		
		
		
	}











	/*deprecated*/
	public void updateChunkLocations()
	{
		int cyw = (int)currentChunk.getRectangle().getX();
		int ryw = (int)currentChunk.getRectangle().getY();
		boolean temp;
		
		for(Chunk e:chunkMap)
		{
			if(e.getX() == cyw && e.getY() == ryw - 1000)
				northChunk = e;
			else if(e.getX() == cyw && e.getY() == ryw + 1000)
				southChunk = e;
			else if(e.getX() == cyw + 1000 && e.getY() == ryw)
				eastChunk = e;
			else if(e.getX() == cyw - 1000 && e.getY() == ryw)
				westChunk = e;
			else if(e.getX() == cyw - 1000 && e.getY() == ryw - 1000)
				northWestChunk = e;
			else if(e.getX() == cyw + 1000 && e.getY() == ryw - 1000)
				northEastChunk = e;
			else if(e.getX() == cyw + 1000 && e.getY() == ryw + 1000)
				southEastChunk = e;				
			else if(e.getX() == cyw - 1000 && e.getY() == ryw + 1000)
				southWestChunk = e;
			else
			{
				print("new chunk not assigned...");	
				//chunkMap.add(new Chunk())	
			}
					
		}
	
		/*
		temp = northChunk.spawned;
		northChunk = new Chunk(cyw, ryw - 1000, 1000, 1000, temp);
		temp = southChunk.spawned;
		southChunk = new Chunk(cyw, ryw + 1000, 1000, 1000, temp);
		temp = eastChunk.spawned;
		eastChunk = new Chunk(cyw + 1000, ryw, 1000, 1000, temp);
		temp = westChunk.spawned;
		westChunk = new Chunk(cyw - 1000, ryw, 1000, 1000, temp);
		temp = northEastChunk.spawned;
		northEastChunk = new Chunk(cyw + 1000, ryw - 1000, 1000, 1000, temp);
		temp = northWestChunk.spawned;
		northWestChunk = new Chunk(cyw - 1000, ryw -1000, 1000, 1000, temp);
		temp = southWestChunk.spawned;
		southWestChunk = new Chunk(cyw - 1000, ryw + 1000, 1000, 1000, temp);
		temp = southEastChunk.spawned;
		southEastChunk = new Chunk(cyw + 1000, ryw + 1000, 1000, 1000, temp);
		*/
		spawnInChunks();
	}	
	
	public void spawnInChunks()
	{
		try
		{
			for(Chunk e: chunkMap)
			{

				if(!(e.spawned))
				{
					int number = 3;
					int choice = (int)(Math.random() * number);
					switch(choice)
					{
						case 0: 
							spawnTetris(e);
							break;
						case 1: 
							spawnHouse(e);
							break;
						case 2:
							spawnLong(e);
								break;	
					}
					
					
					e.setSpawned(true);
					//System.out.println("wee");
				}
	
				
			}
		}
		catch(ConcurrentModificationException e)
		{
			print("Overlap in data access...an operation may have been queued to a later update.");
		}
	}		

	public void spawnHouse(Chunk e)
	{
		int x = (int)(Math.random() * 1001);
		int y = (int)(Math.random() * 1001);
		
		//ents.add(new Wall(ents, 200 + e.getY(), 200 + e.getX(), 200, 200));
		ents.add(new Wall(ents, y+ e.getY(), x+ e.getX(), 20, 320));
		//ents.add(new Wall(ents, 0+ e.getY(), 600+ e.getX(), 20, 320));
		ents.add(new Wall(ents, y+ e.getY(), x + 300+ e.getX(), 20, 320));
		
	}

	public void spawnTetris(Chunk e)
	{
		int x = (int)(Math.random() * 1001);
		int y = (int)(Math.random() * 1001);
		//row, col, width, height
		ents.add(new Wall(ents, y+ e.getY(), x+ e.getX() + 0, 700, 20));
		ents.add(new Wall(ents, y+ e.getY(), x+ e.getX() + 0, 20, 600));
		ents.add(new Wall(ents, y+ e.getY() +300, x+ e.getX() + 300, 400, 20));
		ents.add(new Wall(ents, y+ e.getY() +300, x+ e.getX() + 300, 20, 200));
		ents.add(new Wall(ents, y+ e.getY() +500, x+ e.getX() + 300, 100, 20));
		ents.add(new Wall(ents, y+ e.getY() +600, x+ e.getX() + 300, 120, 20));
		ents.add(new Wall(ents, y+ e.getY() +500, x+ e.getX() + 400, 20, 100));
		ents.add(new Wall(ents, y+ e.getY() +600, x+ e.getX() + 300, 20, 150));
		ents.add(new Wall(ents, y+ e.getY() +750, x+ e.getX() + 0, 320, 20));
		
		/*ents.add(new Wall(ents, 0, 0, 700, 20));
		ents.add(new Wall(ents, 0, 0, 20, 600));
		ents.add(new Wall(ents, 300, 300, 400, 20));
		ents.add(new Wall(ents, 300, 300, 20, 200));
		ents.add(new Wall(ents, 500, 300, 100, 20));
		ents.add(new Wall(ents, 600, 300, 120, 20));
		ents.add(new Wall(ents, 500, 400, 20, 100));
		ents.add(new Wall(ents, 600, 300, 20, 150));
		ents.add(new Wall(ents, 750, 0, 320, 20));*/		
	
	}

	public void spawnLong(Chunk e)
	{
		int x = (int)(Math.random() * 1001);
		int y = (int)(Math.random() * 1001);
		ents.add(new Wall(ents, y+ e.getY(), x+ e.getX() + 0, 1000, 20));
		ents.add(new Wall(ents, 500 + y+ e.getY(), x+ e.getX() + 0, 1000, 20));
	}



  public void paintComponent(Graphics g) 
  { 

	int r = camera.getRow();
	int c = camera.getCol();

   ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 

                           RenderingHints.VALUE_ANTIALIAS_ON); 
                           	
		if(ent.getGun().type == Gun.SABER)
		{
			g.setColor(Color.RED);
			
			g.drawLine((int)ent.getDotCenterCol()-c, (int)ent.getDotCenterRow() -r, 
				(int)(ent.getDotCenterCol()-c + 50* Math.cos(Math.toRadians(ent.getDirection()))), 
					(int)(ent.getDotCenterRow()-r + 50 * Math.sin(Math.toRadians(ent.getDirection()))));
			
			
			
		}
		
		
		
		try
		{
			
		
	for(TestEnt ent: ents)
	{
		//g.drawImage(cursor, 0, 0, 50, 50, null);
		
		if(boxesOn || ent.selected)
		{
			if(ent instanceof Player)
				g.setColor(Color.RED);
			else if(ent instanceof Bot)
				g.setColor(Color.BLUE);
			else if(ent instanceof Grenade)
				g.setColor(Color.GREEN);
			else if(ent instanceof Projectile)
				g.setColor(Color.YELLOW);
			else if(ent instanceof Portal)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.BLACK);
			g.drawRect((int)ent.colTL  - c, (int)(ent.rowTL) - r, (int)(ent.width), (int)(ent.height));
		}
			
		
		g.setColor(Color.BLACK);
		
		if(!boom)
		{
			g.drawImage(cursor, (int)(curse.colTL -25 )-c, (int)(curse.rowTL -25)-r, 50, 50, null);
		}					
		else if(boom)
		{
			g.drawImage(cursor, (int)(curse.colTL -30 )-c, (int)(curse.rowTL -30)-r, 60,60, null);
			boom = false;				
		}
			

			
		if(!(ent.isGone()))
		{
			if(ent instanceof Portal)
			{
				if(ent.type == Portal.IN)
					g.setColor(Color.RED);
				else if(ent.type == Portal.OUT)
					g.setColor(Color.BLUE);
				g.fillOval((int)(ent.colTL-c - ent.width/2), (int)(ent.rowTL-r -ent.height/2), (int)(ent.width), (int)(ent.height));
				//System.out.println("calling");
		
			}	
			else if(ent instanceof Ammo)
			{
				g.drawImage(ammoPic, (int)ent.colTL - c, (int)ent.rowTL- r, (int)ent.width, (int)ent.height, null);
			}
			else if(ent instanceof Grenade)
			{
				Grenade nade = (Grenade)ent;
				if(nade.exploding)
				{
					g.setColor(Color.ORANGE);
					g.fillOval((int)(nade.colTL)-c - nade.expSize, (int)(nade.rowTL)-r -nade.expSize, (int)(nade.expSize * 2), (int)(nade.expSize * 2));
					
					if(nade.timer == 5)
					{
						grenade.stop();
						grenade.setFramePosition(0);
						grenade.start();	
					}
					nade.check();	
				}
				else
				{
					g.setColor(Color.GREEN);
					g.fillOval((int)(ent.colTL)-c - 3, (int)(ent.rowTL)-r -3, (int)(ent.width), (int)(ent.height));
				}		
				
				
			}
			else if(ent instanceof Water)
			{
				g.setColor(Color.BLUE);
				g.fillRect((int)(ent.colTL) - c, (int)(ent.rowTL) - r, (int)(ent.width), (int)(ent.height));
			}				
			else if(ent instanceof Wall)
			{
				g.setColor(Color.BLACK);
				g.fillRect((int)(ent.colTL) - c, (int)(ent.rowTL) - r, (int)(ent.width), (int)(ent.height));
			}
			else if(ent instanceof Projectile)
			{
				g.setColor(Color.ORANGE);
				g.fillOval((int)(ent.colTL)-c - 3, (int)(ent.rowTL)-r -3, (int)(ent.width), (int)(ent.height));
				//g.fillOval((int)(ent.colTL-c - 3 + 3*Math.sin(ent.getDirection())), (int)(ent.rowTL + 3*Math.sin(ent.getDirection())-r -3), (int)(ent.width), (int)(ent.height));
			}
			else if(ent instanceof Bot)
			{
				g.setColor(Color.BLACK);
				g.fillOval((int)(ent.colTL)-c, (int)(ent.rowTL)- r, (int)(ent.width), (int)(ent.height));
				g.setColor(Color.BLUE);
				
				g.fillOval((int)(ent.getDotCol() - c ), (int)(ent.getDotRow()-r), (int)(ent.getDotWidth()), (int)(ent.getDotHeight()));			
				String tempHP = "" + ent.getHP();
				g.drawString(tempHP, (int)ent.colTL-c, (int)ent.rowTL-r);
			}
			else if(ent instanceof Curse)
			{
				g.setColor(Color.BLACK);
				g.fillOval((int)(ent.colTL -3 )-c, (int)(ent.rowTL -3)-r, (int)(6), (int)(6));
			}
			else
			{
				g.setColor(Color.BLACK);
				g.fillOval((int)(ent.colTL-c), (int)(ent.rowTL-r), (int)(ent.width), (int)(ent.height));
				g.setColor(Color.RED);
				g.fillOval((int)(ent.getDotCol()-c), (int)(ent.getDotRow()-r), (int)(ent.getDotWidth()), (int)(ent.getDotHeight()));			
				
				
				//g.drawLine((int)ent.getColCenter()-c, (int)ent.getRowCenter()-r, (int)(ent.getDotCol()-c + 500 * Math.cos(Math.toRadians(ent.getDirection()))), 
					//(int)(ent.getDotRow() - r + 500 * Math.sin(Math.toRadians(ent.getDirection()))));
				String tempHP = "" + ent.getHP();
				g.drawString(tempHP, (int)ent.colTL-c, (int)ent.rowTL-r);
			}
		}		
	}
		}
		catch(ConcurrentModificationException e)
		{
			
		}

	drawInfoBox(g);
	drawAmmo(g);
	drawChunk(g);
	if(showFPS)
	{
		g.setColor(Color.BLACK);
		g.drawString("FPS: " + framesCountAvg, 20, 20);
		g.drawString("X: " + ent.colTL, 20, 40);
		g.drawString("Y: " + ent.rowTL, 20, 60);
		g.drawString("Entities: " + ents.size(), 20, 80);
		g.drawString("Chunks: " + chunkMap.size(), 20, 100);
		g.drawString("Current Chunk: (" + currentChunk.getX() + "," + currentChunk.getY() + ")", 20, 120);
	}
	
	
	
	}
	
	public void drawChunk(Graphics g)
	{
		int r = camera.getRow();
		int c = camera.getCol();
		g.drawRect((int)currentChunk.getRectangle().getX()-c, (int)currentChunk.getRectangle().getY()-r, (int)currentChunk.getRectangle().getWidth(), (int)currentChunk.getRectangle().getHeight());
		/*g.drawRect((int)northChunk.getRectangle().getX()-c, (int)northChunk.getRectangle().getY()-r, (int)northChunk.getRectangle().getWidth(), (int)northChunk.getRectangle().getHeight());
		g.drawRect((int)southChunk.getRectangle().getX()-c, (int)southChunk.getRectangle().getY()-r, (int)southChunk.getRectangle().getWidth(), (int)southChunk.getRectangle().getHeight());
		g.drawRect((int)westChunk.getRectangle().getX()-c, (int)westChunk.getRectangle().getY()-r, (int)westChunk.getRectangle().getWidth(), (int)westChunk.getRectangle().getHeight());
		g.drawRect((int)eastChunk.getRectangle().getX()-c, (int)eastChunk.getRectangle().getY()-r, (int)eastChunk.getRectangle().getWidth(), (int)eastChunk.getRectangle().getHeight());
		g.drawRect((int)northWestChunk.getRectangle().getX()-c, (int)northWestChunk.getRectangle().getY()-r, (int)northWestChunk.getRectangle().getWidth(), (int)northWestChunk.getRectangle().getHeight());
		g.drawRect((int)northEastChunk.getRectangle().getX()-c, (int)northEastChunk.getRectangle().getY()-r, (int)northEastChunk.getRectangle().getWidth(), (int)northEastChunk.getRectangle().getHeight());
		g.drawRect((int)southWestChunk.getRectangle().getX()-c, (int)southWestChunk.getRectangle().getY()-r, (int)southWestChunk.getRectangle().getWidth(), (int)southWestChunk.getRectangle().getHeight());
		g.drawRect((int)southEastChunk.getRectangle().getX()-c, (int)southEastChunk.getRectangle().getY()-r, (int)southEastChunk.getRectangle().getWidth(), (int)southEastChunk.getRectangle().getHeight());
		*/
		for(Chunk e: chunkMap)
		{
			g.drawRect((int)e.getX()-c, (int)e.getY()-r, 1000, 1000);
		}
	}
	
	public void drawBox(Graphics g)
	{
  		int guiwidth = 200;
  		int guiheight =  70;
  		g.setColor(Color.WHITE);
  		int cew = camera.width/2 - guiwidth/2;
  		int rew = 0;
  		
  		g.fillRect(cew,rew, guiwidth, guiheight);
  		g.setColor(Color.BLACK);
  		g.drawRect(camera.width/2 - guiwidth/2, 0 , guiwidth, guiheight);  			
	}
	public void drawAmmo(Graphics g)
	{
	  	if(show)
	  	{
	  		g.setColor(Color.WHITE);
	  		g.fillRect(1150, 640, 120, 15);
	  		g.setColor(Color.BLACK);
	  		g.drawString(ent.getGun().toString(), 1150, 650);
	  		g.setColor(Color.WHITE);
	  		g.fillRect(1150, 670, 120, 15);
	  		g.setColor(Color.BLACK);
	  		g.drawString("Magazines: " + ent.getGun().magazines, 1150, 680);
	  		g.drawString("Kills: " + Player.kills, 1150, 20);	
	  	}	
	}
	public void drawInfoBox(Graphics g)
  	{
		if(!show)
			return;
	  		int guiwidth = 200;
	  		int guiheight =  70;
	  		int cew = camera.width/2 - guiwidth/2;
	  		int rew = 0;
	
	  		if(selected instanceof Bot || selected instanceof Player)
	  		{
		  		
				drawBox(g);
	
				g.setColor(Color.BLACK);
				g.fillOval(cew + 10, rew + 10,40 ,40 );
				if(selected instanceof Bot)
					g.setColor(Color.BLUE);
				else if(selected instanceof Player)
					g.setColor(Color.RED);
				g.fillOval(cew + 10 + 40 - 5, rew + 30 - 5,10, 10);
				
	  			g.setColor(Color.BLACK);
	  			g.drawString(selected.name, cew + 60, rew + 20);
	  			int l = (int)(selected.life/25);
	  			if(selected.isGone())
	  				g.drawString("DEAD", cew + 110, rew + 20);
	  			else
	  				g.drawString("" + l + " seconds old", cew + 110, rew + 20);	
	  				
	  			g.drawString("HP: ", cew + 60, rew + 40);
	  			
	  			g.drawString("AP: ", cew + 60, rew + 60);
	  			g.setColor(Color.RED);
	  			double mod = ((double)selected.hp)/((double)selected.maxHP);
	  			double result = 100 * mod;
	  			int fin = (int)result;
	  			g.fillRect(cew + 80, rew + 31,fin , 10);
	  			g.setColor(Color.BLACK);
	  			g.drawRect(cew + 80, rew + 31,100 , 10);
	  			g.setColor(Color.BLACK);
	  			g.drawString("(" + selected.hp + "/" + selected.maxHP + ")", cew + 85, rew + 40);
	   			mod = ((double)selected.armor)/((double)selected.maxArmor);
	  			result = 100 * mod;
	  			fin = (int)result;
	  			g.setColor(Color.GREEN);
	  			g.fillRect(cew + 80, rew + 51,fin , 10);
	  			g.setColor(Color.BLACK);
	  			g.drawRect(cew + 80, rew + 51,100 , 10); 
	  			g.setColor(Color.BLACK);
	  			g.drawString("(" + (int)(selected.armor) + "/" + (int)(selected.maxArmor) + ")", cew + 85, rew + 60);			
		
	  		}
	 		else if(selected instanceof Ammo)
	 		{
	 			drawBox(g);
	 			g.setColor(Color.BLACK);
	  			g.drawString(selected.name, cew + 60, rew + 20);
	  			g.drawString("Adds one magazine", cew + 60, rew + 40);
	  			g.drawString("to the current gun.", cew + 60, rew + 50);
	  			
				g.drawImage(ammoPic, cew + 10, rew + 10,40 ,40, null);
				
				int l = (int)(selected.life/25);
	  			if(selected.isGone())
	  				g.drawString("TAKEN", cew + 110, rew + 20);
	  			else
	  				g.drawString("" + l + " seconds old", cew + 110, rew + 20);				
				
				
				
	 		} 		
  		
  		
  		
  		
  	}  		
  		
  		
  	
  
  	public boolean checkCollision()
  	{
  		return false;
  	}
  	boolean d;
  	boolean a;
  	boolean w;
  	boolean s;
  	boolean up;
  	boolean down;
  	boolean right;
  	boolean left;
  	boolean fire;
  	boolean show = true;
  	int sec = 5;
  	int wait = 2;
  	boolean focus = true;
  	boolean boom = false;
  	boolean held = false;
  	
  	public void handle()
  	{
  	
  		if(d)
  		{
  			//ent.rotateRight();
  			//System.out.println("yeah");
  			ent.moveRight();
	  		if(focus)
	  		{
	  			camera.focus(ent);
	  		}  				
  		}
  		if(a)
  		{
  			//ent.rotateLeft();	
  			ent.moveLeft();
	  		if(focus)
	  		{
	  			camera.focus(ent);
	  		}  	  			
  		}
  		if(w)
  		{
  			ent.move();
	  		if(focus)
	  		{
	  			camera.focus(ent);
	  		}  	  			
  		}
   		if(s)
  		{
  			ent.back();
	  		if(focus)
	  		{
	  			camera.focus(ent);
	  		}  	  			
  		} 		
  		if(fire && ent.getGun().bullets>0)
  		{
  			//if(tick - sprayTick > 20)
  			//{
  			//	recoil = 0;
  			//}
  		//	else
  			//{
  			//
  				recoil++;
  			//}

  			//System.out.println(recoil);
  				sprayTick = tick;
  			
  			
  			
  			
  			
  			fire = false;
  			wait = ent.getGun().rate;
  			//System.out.println(ent.getGun().getType());
  			switch(ent.getGun().getType())
  			{
  				
  				case Gun.M4:
  					int spray = (int)(Math.random() * recoil) + 2;
  					int neg = (int)(Math.random()*2);
  					
  					if(spray > 10)
  					{
  						spray = 10;
  					}	
  					
  					if(neg == 0)
  						spray = spray * -1;
  					
  					spray = (int)(spray/2);
  						
  					ents.add(new Projectile((int)(ent.getDirection()+spray), ent.getDotCenterRow() - 3, ent.getDotCenterCol()-3, ents, ent.getGun()));  					
			 		String sname = "m4.wav";
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
  					break;
  					
  				case Gun.GLOCK:
  					
  					//System.out.println("glock");
  					ents.add(new Projectile((int)(ent.getDirection()), ent.getDotCenterRow() - 3, ent.getDotCenterCol()-3, ents, ent.getGun()));   					
					String sname2 = "glock.wav";
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
  					break;
  					
  				case Gun.AWP:
  					ents.add(new Projectile((int)(ent.getDirection()), ent.getDotCenterRow() - 3, ent.getDotCenterCol()-3, ents, ent.getGun()));   					
					String sname3 = "awp.wav";
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
  					break;  					
  					
   				case Gun.SHOTGUN:
  					ents.add(new Projectile((int)(ent.getDirection()), ent.getDotCenterRow() - 3, ent.getDotCenterCol()-3, ents, ent.getGun())); 
  					ents.add(new Projectile((int)(ent.getDirection()+5), ent.getDotCenterRow() - 3, ent.getDotCenterCol()-3, ents, ent.getGun()));  	
  					ents.add(new Projectile((int)(ent.getDirection()-5), ent.getDotCenterRow() - 3, ent.getDotCenterCol()-3, ents, ent.getGun()));  	  					
					String sname4 = "shotgun.wav";
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
  					break;  					
  				case Gun.PORTAL_GUN:
  					
  					if(button==0)
  					{
  						
  						for(TestEnt e: ents)
  						{
  							if(e.type == Portal.IN)
  							{
  								e.die();
  							}
  						}
  						ents.add(new Portal((ent.getDirection()), ent.getDotCenterRow() , ent.getDotCenterCol(), ents, Portal.IN));
  						//System.out.println("made");
  					}
  					else if(button == 1)
  					{
  						for(TestEnt e: ents)
  						{
  							if(e.type == Portal.OUT)
  							{
  								e.die();
  							}
  						}  						
  						ents.add(new Portal((ent.getDirection()), ent.getDotCenterRow(), ent.getDotCenterCol(), ents, Portal.OUT));
  					}
  					
  					
  					
  					
  					
  					
  					break;
  					
  						 					
  				
  				
  			}
			ent.getGun().shoot();
  			sec = 0;
  			
  			
  			
  			
  			
  			boom = true;
  			clip.stop();
  			clip.setFramePosition(0);
  			if(!(ent.getGun().type == Gun.SABER))
  				clip.start();
  		}
  		if(up)
  		{
  			camera.panUp();
  		}
  		if(down)
  		{
  			camera.panDown();
  		}
  		if(right)
  		{
  			camera.panRight();
  		}
  		if(left)
  		{
  			camera.panLeft();
  		}
  		if(focus)
  		{
  			camera.focus(ent);
  		}
  		
  		
  		
  	}
  	
  	public void showConsole()
  	{
  		consoleOn = true;
		UIManager UI=new UIManager();
		 UI.put("OptionPane.background", Color.BLACK);
		 UI.put("Panel.background", Color.BLACK);
		 UI.put("OptionPane.messageForeground", Color.WHITE);
		boolean go = true;
		while(go)
		{
			
			input= new JTextField(50);
		
			input.addKeyListener(new ConsoleKeyListener());

			
			input.addAncestorListener( new RequestFocusListener() );
			field = new JTextArea();
			field.setEditable(false);
			field.setLineWrap(true);
			field.setOpaque(false);
			field.setBackground(new Color(0,0,0,0));
			JScrollPane area = new JScrollPane(field, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			field.setRows(10);
			field.setText(consoleText);
			JPanel myPanel = new JPanel();

			
			myPanel.setLayout(new BorderLayout(0,0));
			myPanel.add(input, BorderLayout.PAGE_END);
			myPanel.add(area, BorderLayout.PAGE_START);
			input.setFocusable(true);
			input.requestFocus();
			int result = 101;
			//int result = JOptionPane.showOptionDialog(null, myPanel,"", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
			JOptionPane pane = new JOptionPane(myPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
		
			//dialog = pane.createDialog(null, "");
			dialog = new JDialog((Frame)null, "");
			dialog.setUndecorated(true);
			dialog.setOpacity(0.80f);

            dialog.getRootPane().setOpaque(false);
            dialog.getRootPane().setBackground(new Color(0,0,0,0));
            
            dialog.setLayout(new BorderLayout());
            dialog.add(pane);
            dialog.pack();
			dialog.setLocationRelativeTo(this);
			dialog.setLocation(new Point(0 ,getHeight()-dialog.getHeight()));            
            dialog.setVisible(true);			

			
			
			//dialog.setOpacity(0.55f);
			//removeMinMaxClose(dialog);
			//removeMinMaxClose(pane); 
			//removeMinMaxClose(myPanel);
			//dialog.getRootPane().setOpaque(false);
			
			//JDialog dialog = new JDialog();
			//dialog.setVisible(false);
			//dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			//myPanel.setUndecorated(true);
			//dialog.setUndecorated(true);
			//dialog.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);  
       		//dialog.setBounds( 100, 100, 300, 200 );  

			dialog.show();
			
			input.requestFocusInWindow();
			if(result == 100)
			{
				
			}
			else
			{
				go = false;
			}
			
		} 
		 
  	}
  	
  	
  	
  	
  	public void print(String message)
  	{
  		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	//System.out.println(  );
    	
  		consoleText = consoleText + "\n(SYSTEM " + sdf.format(cal.getTime()) + "): " + message;
  		field.setText(consoleText);
  		
  	}
  	
  	public void godmode()
  	{
		if(Player.godmode)
		{
			print("Godmode disabled.");	
		}	
		else
		{
			print("Godmode enabled.");	
		}
		Player.godmode = !(Player.godmode); 		
  	}
  	
  	public void unlimitedAmmo()
  	{
  		if(Player.unlimited)
  			print("Unlimited ammo disabled.");
  		else
  			print("Unlimited ammo enabled.");
  		Player.unlimited =!(Player.unlimited);
  		
  		
  	}
  	
  	public boolean checkOverlap(int row, int col)
  	{
  		for(TestEnt e:ents)
  		{
  			if(!e.isGone())
  			{
  				Rectangle rect = new Rectangle((int)e.colTL, (int)e.rowTL, (int)e.width, (int)e.height);
  				if(rect.intersects(new Rectangle(col, row, 40, 40)) || rect.contains(new Rectangle(col, row, 40, 40)))
  				{
  					return true;
  				}
  					
  				
  				
  			}
  			
  			
  			
  		}
  		return false;
  	
  		
  		
  	}
  	
  	
  	public void spawnBots(int amount, int radius)
  	{
  		int diameter = radius * 2;
  		if(radius<75)
  			radius = 75;
  		int baseRow = (int)ent.rowTL;
  		int baseCol = (int)ent.colTL;
  		for(int i = amount; i >0; i--)
  		{
  			int val1;
  			int val2;
  			do
  			{
  				int mod1 = (int)(Math.random() * (diameter + 1));
  				int mod2 = (int)(Math.random() * (diameter + 1));
  				//print("mod1: " + mod1 + "mod2: " + mod2);
  				val1 = radius - mod1;
  				val2 = radius - mod2;	
  			}
  			while(Math.abs(val1)<50 || Math.abs(val2)<50||checkOverlap(baseRow + val1, baseCol + val2));
  			
  			//print("val1: " + val1 + "val2: " + val2);
  			ents.add(new Bot(ents, baseRow + val1, baseCol + val2));
  			
  			
  		}
	
  	}
 
  	public void spawnAmmo(int amount, int radius)
  	{
  		int diameter = radius * 2;
  		if(radius<75)
  			radius = 75;
  		int baseRow = (int)ent.rowTL;
  		int baseCol = (int)ent.colTL;
  		for(int i = amount; i >0; i--)
  		{
  			int val1;
  			int val2;
  			do
  			{
  				int mod1 = (int)(Math.random() * (diameter + 1));
  				int mod2 = (int)(Math.random() * (diameter + 1));
  				//print("mod1: " + mod1 + "mod2: " + mod2);
  				val1 = radius - mod1;
  				val2 = radius - mod2;	
  			}
  			while(Math.abs(val1)<50 || Math.abs(val2)<50||checkOverlap(baseRow + val1, baseCol + val2));
  			
  			//print("val1: " + val1 + "val2: " + val2);
  			ents.add(new Ammo(ents, baseRow + val1, baseCol + val2));
  			
  			
  		}
	
  	} 
 
 	public void killBots()
 	{
 		int am = 0;
 		try
 		{
 			for(int i = 0; i<ents.size(); i++)
 			{
 				if(ents.get(i) instanceof Bot)
 				{
 					ents.get(i).die();
 					am++;
 				}
 					
 			}
 			
 			
 		}
 		catch(ConcurrentModificationException e)
 		{
 			print("Warning - possible data-flow lag. (ConcurrentModificationException - entity calculation/removal may be queued to a later update.");
 		}
 		print("" + am + " bots have been killed.");
 	}
  	
  	public void clearMap()
  	{
  		int am = 0;
  		try
  		{
  			for(TestEnt e:ents)
  			{
  				if(e instanceof Bot || e instanceof Ammo)
  				{
  					e.die();
  					am++;
  				}
  					
  			}
  		
  		}
  		catch(ConcurrentModificationException e)
  		{
  			
  		}
  		print("" + am + " entities have been cleared.");
  		
  	}
  	
  	public void suicide()
  	{
  		ent.die();
  		print("You have suicided...");
  	}
  	
  	public void toggleOneHit()
  	{
  		if(Projectile.oneHit)
  		{
  			Projectile.oneHit = false;
  			print("One hit kill has been toggled off.");	
  		}
  		else
  		{
  			Projectile.oneHit = true;
  			print("One hit kill has been toggled on.");
  		}
  		
  	}
  	
  	public int getEntsSize()
  	{
  		print("" + ents.size());
  		return ents.size();
  	}
  	
  	public void showBoxes()
  	{
  		if(boxesOn)
  		{
  			print("Collision indicators have been toggled off.");
  			boxesOn = false;
  		}
  		else
  		{
  			print("Collision indicators have been toggled on.");
  			boxesOn = true;
  		}
  	}
  	
  	public void toggleFPS()
  	{
  		if(showFPS)
  		{
  			print("FPS and position indicator are being toggled off.");
  			showFPS = false;
  		}
  		else
  		{
  			print("FPS and position indicator are being toggled on.");
  			showFPS = true;
  		}
  	}
  	
  	public void teleport(int row, int col)
  	{
  		ent.setRow(row);
  		ent.setCol(col);
  		print("Player has been moved to (" + col + "," + row + ").");
  		
  		
  	}
  	
  	public void handleCommand()
  	{
  		command = command.toUpperCase();
  		
  		
  		if(command.contains("SPAWN") && command.indexOf("SPAWN") == 0)
  		{
  			int amount = -1;
  			int radius = -1;
  			int spaces = 0;
  			
  			
  			for(int i = 0; i<command.length(); i++)
  			{
  				if(command.charAt(i) == ' ')
  				{
  					spaces++;
  				}
  			}
  			//print("There are spaces" + spaces);
  			if(spaces == 0)
  			{
  				print("No amount or radius specified. Using default values...(10 bots, 300 radius).");
  				spawnBots(10, 300);
  				print("10 bots have been spawned within a 300 radius circle centered around the player.");
  			}
  			else if(spaces == 2)
  			{
  				String[] pieces = command.split(" ");
  				int val1;
  				int val2;
  				try
  				{
  					val1 = Integer.parseInt(pieces[1]);
  					val2 = Integer.parseInt(pieces[2]);
  				}
  				catch(NumberFormatException e)
  				{
  					print("Incorrect format of 'spawn' command entered. Correct format is: spawn <amount of bots> <radius>)");
  					return;
  				}
  				spawnBots(val1, val2);
  				print("" + val1 + " bots have been spawned within a " + val2 + " radius circle centered around the player.");
  				return;
			
  			}
  			else
  			{
					print("Incorrect format of 'spawn' command entered. Correct format is: spawn <amount of bots> <radius>)");
  					return;  				
  			}

  			return;
  		}
		else if(command.contains("TELEPORT") && command.indexOf("TELEPORT") == 0)
		{
			String[] pieces = command.split(" ");
			if(pieces.length == 3)
			{
				int val1;
				int val2;
  				try
  				{
  					val1 = Integer.parseInt(pieces[1]);
  					val2 = Integer.parseInt(pieces[2]);
  				}
  				catch(NumberFormatException e)
  				{
  					print("Incorrect format of 'teleport' command entered. Correct format is: teleport <col> <row>)");
  					return;
  				}
  				teleport(val2, val1);
  				camera.focus(ent);
  				return;					
			}
			
			
			
		}
  		
  		switch(command)
  		{
  			case "GODMODE":
  				
				godmode();
  				return;	
  			
  			case "AMMO":
  				unlimitedAmmo();
  				return;
  			case "UNLIMITED AMMO":
  				unlimitedAmmo();
  				return;
			case "KILL":
				killBots();
				return;
			case "NUKE":
				killBots();
				return;
			case "CLEAR":
				clearMap();
				return;
			case "CLEAR MAP":
				clearMap();
				return;
			case "CLEARMAP":
				clearMap();
				return;
			case "SUICIDE":
				suicide();
				return;
			case "1HIT":
				toggleOneHit();
				return;
			case "ONEHIT":
				toggleOneHit();
				return;
			case "ONE HIT":
				toggleOneHit();
				return; 
			case "SIZE":
				getEntsSize();
				return;
			case "BOXES":
				showBoxes();
				return;
			case "FPS":
				toggleFPS();
				return;
			case "SHOWFPS":
				toggleFPS();
				return;
			case "TOGGLEFPS":
				toggleFPS();
				return;							
  		}
  		
  		print("This command does not exist. Type help for a list of commands.");
  		
  		
  		
  		
  		
  		
  		
  		
  		
  	}
  	public void select(int row, int col)
  	{
  		Point temp = new Point(col, row);
  		for(TestEnt e:ents)
  		{
  			Rectangle rect = new Rectangle((int)e.colTL, (int)e.rowTL, (int)e.width, (int)e.height);
  			if(rect.contains(temp))
  			{
  				e.selected = true;
  				selected = e;
  			}		
  			else
  				e.selected = false;
  			
  		}
  		
  		
  		
  		
  		
  	}
    public void setMouseDirection(int row, int col)
    {
    	
	    			
	    		double dx = col - ent.getDotCenterCol();
	    		double dy = -(row - ent.getDotCenterRow());	
	    		double inRads = Math.atan2(dy, dx);
	    		if(inRads<0)
	    			inRads = Math.abs(inRads);
	    		else
	    			inRads = 2*Math.PI - inRads;
	    		
	    		ent.setDirection(Math.toDegrees(inRads));		
    }  
    		
  public void removeMinMaxClose(Component comp)  
  {  
    if(comp instanceof AbstractButton)  
    {  
      comp.getParent().remove(comp);  
    }  
    if (comp instanceof Container)  
    {  
      Component[] comps = ((Container)comp).getComponents();  
      for(int x = 0, y = comps.length; x < y; x++)  
      {  
        removeMinMaxClose(comps[x]);  
      }  
    }  
  }   	

class MyKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();

			switch (keyCode) {  
				
				case KeyEvent.VK_D: 
						d = true;
					handle();
					break;
					
				case KeyEvent.VK_A: 
						a = true;
						handle();
					break;
					
				case KeyEvent.VK_W:
						
						w = true;
						handle();
					break;
					
				case KeyEvent.VK_S:
						
						s = true;
						handle();
					break;
															
				case KeyEvent.VK_RIGHT: 
					
					right = true;
					handle();
					
					break;
					
				case KeyEvent.VK_LEFT: 
					left = true;
					handle();
					break;

				case KeyEvent.VK_UP: 
					up = true;
					handle();
					break;
					
				case KeyEvent.VK_DOWN: 
					down = true;
					handle();
					break;
					
				case KeyEvent.VK_Y: 
					camera.lockScreen();
					break;
					
				case KeyEvent.VK_CONTROL: 
					
					camera.focus(ent);
					focus = !focus;
					break;
						
				case KeyEvent.VK_SHIFT:
					
					ents.add(new Grenade((int)(ent.getDirection()-5), ent.getDotCenterRow() - 3, ent.getDotCenterCol()-3, ents));
					break;
										
				case KeyEvent.VK_1: 
					
					ent.switchGun(0);
					break;	
										
				case KeyEvent.VK_2: 
					
					ent.switchGun(1);
					break;	
						
				case KeyEvent.VK_3: 
					
					ent.switchGun(2);
					break;
				case KeyEvent.VK_4: 
					
					ent.switchGun(3);
					break;					
				case KeyEvent.VK_5: 
					
					ent.switchGun(4);
					break;					
				case KeyEvent.VK_6: 
					
					ent.switchGun(5);
					break;						
															
				case KeyEvent.VK_SPACE:
					if(sec>=wait) 
						fire = true;
					handle();
	
					break;	
																
				case KeyEvent.VK_R:
					ent.getGun().reload();

					break;	
						
					
											    		
			}	
	
			}
			
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();

			switch (keyCode) {  
				
				case KeyEvent.VK_D: 
						d = false;
						handle();
					
					break;
					
				case KeyEvent.VK_A: 
						a = false;
						handle();
						
					break;
					
				case KeyEvent.VK_W: 
						w = false;
						handle();
						
					break;	
						
				case KeyEvent.VK_S: 
						s = false;
						handle();
						
					break;							
										
				case KeyEvent.VK_RIGHT: 
					right = false;
					handle();
					break;
					
				case KeyEvent.VK_LEFT: 
					left = false;
					handle();
					break;
					
				case KeyEvent.VK_UP: 
					up = false;
					handle();
					break;	
						
				case KeyEvent.VK_DOWN: 
					down = false;
					handle();
					break;
														
				case KeyEvent.VK_SPACE: 
					
					//player.stopJump();
					
					break;	
						
				case KeyEvent.VK_CONTROL: 
					
					
					break;					
				case KeyEvent.VK_ESCAPE:
					frame.setVisible(false);
					frame.dispose();
					System.exit(0);
					break;	
																						
				case KeyEvent.VK_BACK_QUOTE:
					showConsole();
					
					break;					    		
			}	
	
			}			
			
		}
		
class MyMouseListener implements MouseListener{

    public void mousePressed(MouseEvent e) {
	//System.out.println("mouse");
	//System.out.println(e.getX() + "," + e.getY());
	held = true;
	button = 2;
	if(sec>=wait)
	{
		fire = true;
	    if(e.getButton() == MouseEvent.BUTTON1)
	    {
	      button = 0;
	    }	    
	    else if(e.getButton() == MouseEvent.BUTTON3)
	    {
	      button = 1;
	    }		
		//System.out.println(button);
	}
		
	handle();
	saberin = true;
	
    }

    public void mouseReleased(MouseEvent e) {
   saberin = true;
   held = false;
    }

    public void mouseEntered(MouseEvent e) {
       
    }

    public void mouseExited(MouseEvent e) {
       
    }

    public void mouseClicked(MouseEvent e) {
       
    }

  
 }

class MyMotionListener implements MouseMotionListener {

    public void mouseMoved(MouseEvent e) {
       //System.out.println("(" + e.getX() + "," + e.getY() + ")");
       setMouseDirection(e.getY() + camera.getRow(), e.getX() + camera.getCol());
       select(e.getY() + camera.getRow(), e.getX() + camera.getCol());
       curse.setRow(e.getY() + camera.getRow());
       curse.setCol(e.getX() + camera.getCol());
       
       
       
        //setMouseDirection(e.getY(), e.getX());
    }

    public void mouseDragged(MouseEvent e) {
        setMouseDirection(e.getY() + camera.getRow(), e.getX() + camera.getCol());
       curse.setRow(e.getY() + camera.getRow());
       curse.setCol(e.getX() + camera.getCol());  
 		select(e.getY() + camera.getRow(), e.getX() + camera.getCol());
       saberCheck(); 
    }


}
	
class ConsoleKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();

			switch (keyCode) {  
			
				
					
											    		
			}	
	
			}
			
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();

			switch (keyCode) {  
				
				case KeyEvent.VK_ENTER:
					
					if(!(input.getText().equals("")))
					{
						command = input.getText();
						if(consoleText.equals(""))
						{
							consoleText = input.getText();
						}
						else
						{
							consoleText = consoleText + "\n" + input.getText();	
						}						
					}

					
					input.setText("");
					field.setText(consoleText);
					handleCommand();
					break;
					
				case KeyEvent.VK_BACK_QUOTE:
					e.consume();
					input.setEditable(false);
					input.setText("");					
					if(consoleOn)
						dialog.dispose();
	
					consoleOn = false;	
					break;			
																					
					    		
			}	
	
			}			
			
		}






		
 	}  	










  	

 