/***************************************************************
 * File: Edges.java
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

public class Edges {
    
    protected int yMaximum;
    protected int yMinimum;
    protected int xValue;
    protected int slope;
    
    //Method: Edges
    //Purpose: This method is a constructor and
    //it is used to set the variables
    public Edges (int yMaximum, int yMinimum, int xValue, int slope){
        this.yMaximum = yMaximum;
        this.yMinimum = yMinimum;
        this.xValue = xValue;
        this.slope = slope;
    }
    
    //Method: getYMaximum
    //Purpose: This method is used to return yMaximum
    public int getYMaximum(){
        return yMaximum;
    }
    
    //Method: getYMinimum
    //Purpose: This method is used to return yMinimum
    public int getYMinimum(){
        return yMinimum;
    }
    
    //Method: getXValue
    //Purpose: This method is used to return xValue
    public int getXValue(){
        return xValue;
    }
    
    //Method: getSlope
    //Purpose: This method is used to return the slope
    public int getSlope(){
        return slope;
    }
    
    //Method: Compare
    //Purpose: This method is used to compare the edges
    public boolean Compare(Edges edge){
        if(this.yMinimum < edge.yMinimum){
            return true;
        }
        else if(this.yMinimum > edge.yMinimum){
            return false;
        }
        else{
            if(this.xValue < edge.xValue){
                return true;
            }
            else if(this.xValue > edge.xValue){
                return false;
            }
            else{
                if(this.yMaximum < edge.yMaximum){
                    return true;
                }
                else if(this.yMaximum > edge.yMaximum){
                    return false;
                }
                else{
                    return false;
                }
            }
        }
    }
    
    //Method: compareTo
    //Purpose: This method is used to compare the x values
    public int compareTo(Edges edge){
        if(this.xValue < edge.xValue){
            return -1;
        }
        else if(this.xValue == edge.xValue){
            return 0;
        }
        else{
            return 1;
        }
    }
}
