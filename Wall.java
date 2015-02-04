/**
 * @(#)Wall.java
 *
 *
 * @author 
 * @version 1.00 2013/6/3
 */
import java.util.ArrayList;

public class Wall extends TestEnt{



    public Wall(ArrayList<TestEnt> temp, int row, int col, int width, int height) {
    	hp = 100;
    	type = 1;
    	rowTL = row;
    	colTL = col;
    	this.width = width;
    	this.height = height;
    	
    }
    
    
}