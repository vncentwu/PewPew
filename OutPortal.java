/**
 * @(#)OutPortal.java
 *
 *
 * @author 
 * @version 1.00 2013/6/10
 */
import java.util.ArrayList;

public class OutPortal extends Portal{

    public OutPortal(double dir, double row, double col, ArrayList<TestEnt> temp) {
    	super(dir, row, col, temp);
    	type = 9;
    }
    
}