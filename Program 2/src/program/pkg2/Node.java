/***************************************************************
 * File: Node.java
 * Author: Nour Shinnawi
 * Class: CS 4450
 * 
 * Assignment: Program 2
 * Date Last Modified: 10/2/20
 * 
 * Purpose: This program will read a file titled coordinates.txt 
 * and draw the corresponding filled polygon in this window using 
 * the scanline polygon fill algorithm.
*****************************************************************/
package program.pkg2;

public class Node {

    protected int xValue;
    protected int yValue;
    
    //Method: Node
    //Purpose: This method is a constructor
    // and it is used to set the variables
    public Node(int xValue, int yValue){
        this.xValue = xValue;
        this.yValue = yValue;
    }
    
    //Method: getXValue
    //Purpose: This method is used to return xValue
    public int getXValue(){
        return xValue;
    }
    
    //Method: getYValue
    //Purpose: This method is used to return yValue
    public int getYValue(){
        return yValue;
    }
}
