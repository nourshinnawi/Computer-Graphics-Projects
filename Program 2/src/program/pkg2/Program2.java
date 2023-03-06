/***************************************************************
 * File: Program2.java
 * Author: Nour Shinnawi
 * Class: CS 4450
 * 
 * Assignment: Program 2
 * Date Last Modified: 10/2/20
 * 
 * Purpose: This program will read a file titled coordinates.txt 
 * and draw the corresponding filled polygon in this window using 
 * the scanline polygon fill algorithm.
 * 
 * Comments: This assignment was really difficult.
*****************************************************************/

package program.pkg2;

import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Program2 {
    
    //Method: createWindow
    //Purpose: This method is used to set up the display window
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 2");
        Display.create();
    }
    
    //Method: initGL
    //Purpose: This method is used to initialize the display
    private void initGL(){
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 0, 480, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    //Method: render
    //Purpose: This method is used to read in the coordinates
    // and to display the results
    private void render() throws FileNotFoundException{
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            try{
                 glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                 glLoadIdentity();
                 glColor3f(1.0f,1.0f,0.0f);
                 glPointSize(1);
                 glBegin(GL_POINTS);
                 File file = new File("coordinates.txt");
                 BufferedReader br = new BufferedReader (new FileReader(file));
                 Scanner scan = new Scanner(br);
                 ArrayList<Node> list = new ArrayList<>();
                 String find = "";
                 String placeHolder;
                 ArrayList<String> hold = new ArrayList<>();
                 char c;
                 Scanner b;
                 boolean transformation = false;
                 boolean newPolygon = false;
                 while(scan.hasNext()){
                     if(!newPolygon){
                         placeHolder = scan.nextLine();
                         c = placeHolder.charAt(0);
                         placeHolder = placeHolder.replaceAll("," , " ");
                         b = new Scanner(placeHolder);
                     }
                     else{
                         c = find.charAt(0);
                         placeHolder = find;
                         placeHolder = placeHolder.replaceAll("," , " ");
                         b = new Scanner(placeHolder);
                         transformation = false;
                         newPolygon = false;
                     }
                     if (c == 'P'){
                         String newHolder = placeHolder.substring(1);
                          b = new Scanner(newHolder);
                          glColor3f(b.nextFloat(), b.nextFloat(), b.nextFloat());
                     }
                     else if (Character.isDigit(c)){
                         int xHolder = b.nextInt();
                         int yHolder = b.nextInt();
                         list.add(new Node(xHolder, yHolder));
                     }
                     else if (c == 'T'){
                         transformation = true;
                         hold.removeAll(hold);
                         while (scan.hasNext()){
                             find = scan.nextLine();
                             if (find.charAt(0) == 'P'){
                                 transform(list, hold);
                                 fill(list);
                                 newPolygon = true;
                                 list = new ArrayList<>();
                                 break;
                             }
                             hold.add(find);
                         }
                         
                     }
                 }
                 transform(list, hold);
                 fill(list);
                 System.out.println("Done");
                 glEnd();
                 Display.update();
            }
            catch(Exception e){};
        }
        Display.destroy();
    }
    
    //Method: translate
    //Purpose: This method is used to get and translate the coordinates
    private void translate(ArrayList<Node>list, int x, int y){
        for (int i = 0; i<list.size(); i++){
            list.get(i).xValue += x;
            list.get(i).yValue += y;
        }
    }
    
    //Method: transform
    //Purpose: This method is used to get and transform the coordinates
    private void transform(ArrayList<Node> list, ArrayList<String> action){
        for (int i = 0; i <action.size(); i++){
            switch (action.get(i).charAt(0)) {
                case 'r':
                    {
                        Scanner scan = new Scanner(action.get(i).substring(1));
                        rotate(list, scan.nextInt(), scan.nextInt(), scan.nextInt());
                        break;
                    }
                case 't':
                    {
                        Scanner scan = new Scanner(action.get(i).substring(1));
                        translate(list, scan.nextInt(), scan.nextInt());
                        break;
                    }
                default:
                    {
                        Scanner scan = new Scanner(action.get(i).substring(1));
                        scaling(list, scan.nextInt(), scan.nextInt(), scan.nextInt(),scan.nextInt());
                        break;
                    }
                
            }
        }
    }
    
    //Method: centerXValue
    //Purpose: This method is used to find the value of the
    //center x
    private int centerXValue(ArrayList<Node>list){
        int xMax = 0;
        int xMin = 0;
        int yMax = 0;
        int yMin = 0;
        
        for (int i = 0; i<list.size(); i++){
            if(i==0){
                xMax = list.get(i).xValue;
            }
            else{
                if(list.get(i).xValue > xMax){
                    xMax = list.get(i).xValue;
                }
            }
        }
        for (int i = 0; i<list.size(); i++){
            if(i == 0){
                xMin = list.get(i).xValue;
            }
            else{
                if(list.get(i).xValue < xMin){
                    xMin = list.get(i).xValue;
                }
            }
        }
        for (int i = 0; i<list.size(); i++){
            if(i==0){
                yMax = list.get(i).yValue;
            }
            else{
                if(list.get(i).yValue > yMax){
                    xMax = list.get(i).yValue;
                }
            }
        }                
        for (int i = 0; i<list.size(); i++){
            if(i == 0){
                yMin = list.get(i).yValue;
            }
            else{
                if(list.get(i).yValue < xMin){
                    yMin = list.get(i).yValue;
                }
            }
        }
        return (xMin + ((xMax-xMin)/2));
    }
    
    //Method: centerYValue
    //Purpose: This method is used to find the value
    //of the center x
    private int centerYValue(ArrayList<Node>list){
        int xMax = 0;
        int xMin = 0;
        int yMax = 0;
        int yMin = 0;
        
        for (int i = 0; i<list.size(); i++){
            if(i==0){
                xMax = list.get(i).xValue;
            }
            else{
                if(list.get(i).xValue > xMax){
                    xMax = list.get(i).xValue;
                }
            }
        }
        for (int i = 0; i<list.size(); i++){
            if(i == 0){
                xMin = list.get(i).xValue;
            }
            else{
                if(list.get(i).xValue < xMin){
                    xMin = list.get(i).xValue;
                }
            }
        }
        for (int i = 0; i<list.size(); i++){
            if(i==0){
                yMax = list.get(i).yValue;
            }
            else{
                if(list.get(i).yValue > yMax){
                    xMax = list.get(i).yValue;
                }
            }
        }                
        for (int i = 0; i<list.size(); i++){
            if(i == 0){
                yMin = list.get(i).yValue;
            }
            else{
                if(list.get(i).yValue < xMin){
                    yMin = list.get(i).yValue;
                }
            }
        }
        return (yMin + ((yMax-yMin)/2));
    }   
    
    //Method: rotate
    //Purpose: This method is used to rotate
    private void rotate(ArrayList<Node> list, float theta, 
            float pivot1, float pivot2){
        float centerX = centerXValue(list);
        float centerY = centerYValue(list);
        
        for(int i = 0; i < list.size(); i++){
            float []placeHolder = {list.get(i).xValue,list.get(i).yValue}; 
            //ch 8 textbook
            AffineTransform.getRotateInstance(Math.toRadians(theta), pivot1, 
                    pivot2).transform(placeHolder, 0, placeHolder, 0, 1);
            list.get(i).xValue = (int) placeHolder[0];
            list.get(i).xValue = (int) placeHolder[1];
        }
    }
    
    //Method: scaling
    //Purpose: This method is used to scale
    private void scaling(ArrayList<Node> list, float scale1, float scale2, float pivot1, float pivot2){
        float centerX = centerXValue(list);
        float centerY = centerYValue(list);
        
        for(int i = 0; i < list.size(); i++){
            list.get(i).xValue = Math.round(((list.get(i).xValue - pivot1)*scale1)+pivot1);
            list.get(i).yValue = Math.round(((list.get(i).yValue - pivot2)*scale2)+pivot2);
        }
    }
    
    //Method: transform
    //Purpose: This method is used to fill the polygon
    private void fillPolygon(ArrayList<Edges> list, int y){
        boolean parity = false;
        
        for(int i = 0; i < list.size(); i++){
            parity = !parity;
            if(parity){
                int xMax = list.get(i+1).xValue;
                for (int j = list.get(i).xValue; j <= xMax; j++){
                    glVertex2f(j, y);
                }
            }
        }
        for (int j = 0; j < list.size(); j++){
            list.get(j).xValue = list.get(j).xValue + list.get(j).slope;
        }
        Collections.sort(list, (Edges e, Edges x) -> e.compareTo(x));
    }
    
    //Method: check
    //Purpose: This method is used to check the slope
    private boolean check(Edges edge){
        return edge.slope != 1999999;
    }
    
    //Method: findYMaximum
    //Purpose: This method is used to find the maximum y value
    private int findYMaximum(Node x, Node y){
        if(x.yValue > y.yValue){
            return 1;
        }
        else if (y.yValue > x.yValue){
            return 2;
        }
        else{
            return 0;
        }
    }
    
    //Method: findYMinimum
    //Purpose: This method is used to find the minimum y value
    private int findYMinimum(Node x, Node y){
        if(x.yValue < y.yValue){
            return 1;
        }
        else if (y.yValue < x.yValue){
            return 2;
        }
        else{
            return 0;
        }
    }
    
    //Method: inverseSlope
    //Purpose: This method is used to find the inverse slope
    private int inverseSlope(Node x, Node y){
        int dx = x.xValue;
        int dy = y.yValue;
        
        if(dy == 0){
            return 1999999;
        }
        else{
            return dx/dy;
        }
    }

    //Method: fill
    //Purpose: This method is used to get the edges
    //and fill the polygon
    private void fill(ArrayList<Node> list){
        ArrayList<Edges> all_edges = new ArrayList<>();
        ArrayList<Edges> global_edges = new ArrayList<>();
        ArrayList<Edges> active_edges = new ArrayList<>();
        
        for (int i = 0; i < list.size(); i++){
             if (i == (list.size()-1)){
                int currentYMin;
                int currentYMax;
                int currentXValue;
                int currentSlope;

                int findMin = findYMinimum(list.get(i), list.get(0));
                 switch (findMin) {
                     case 0:
                         currentYMin = list.get(0).yValue;
                         currentXValue = list.get(0).xValue;
                         break;
                     case 1:
                         currentYMin = list.get(i).yValue;
                         currentXValue = list.get(i).xValue;
                         break;
                     default:
                         currentYMin = list.get(0).yValue;
                         currentXValue = list.get(0).xValue;
                         break;
                 }

                int findMax = findYMaximum(list.get(i), list.get(0));
                 switch (findMax) {
                     case 0:
                         currentYMax = list.get(0).yValue;
                         break;
                     case 1:
                         currentYMax = list.get(i).yValue;
                         break;
                     default:
                         currentYMax = list.get(0).yValue;
                         break;
                 }

                currentSlope = inverseSlope(list.get(i), list.get(0));
                 all_edges.add(new Edges(currentYMin, currentYMax, currentXValue, currentSlope));
            }
             else{
                int currentYMin;
                int currentYMax;
                int currentXValue;
                int currentSlope;
                
                int findMin = findYMinimum(list.get(i), list.get(i+1));
                 switch (findMin) {
                     case 0:
                         currentYMin = list.get(i+1).yValue;
                         currentXValue = list.get(i+1).xValue;
                         break;
                     case 1:
                         currentYMin = list.get(i).yValue;
                         currentXValue = list.get(i).xValue;
                         break;
                     default:
                         currentYMin = list.get(i+1).yValue;
                         currentXValue = list.get(i+1).xValue;
                         break;
                 }

                int findMax = findYMaximum(list.get(i), list.get(i+1));
                 switch (findMax) {
                     case 0:
                         currentYMax = list.get(i+1).yValue;
                         break;
                     case 1:
                         currentYMax = list.get(i).yValue;
                         break;
                     default:
                         currentYMax = list.get(i+1).yValue;
                         break;
                 }

                currentSlope = inverseSlope(list.get(i), list.get(i+1));
                 all_edges.add(new Edges(currentYMin, currentYMax, currentXValue, currentSlope));
                
             }
        }
        
        for (int i = 0; i < all_edges.size(); i++){
            if(i == 0){
                if(check(all_edges.get(0))){
                    global_edges.add(all_edges.get(0));
                }
                else{
                    if(check(all_edges.get(i))){
                        for(int j = global_edges.size()-1; j>=0; j--){
                             if (all_edges.get(i).Compare(global_edges.get(j))){
                                 if(j == 0){
                                     global_edges.add(0, all_edges.get(i));
                                 }
                                 else{
                                     global_edges.add(j+1, all_edges.get(i));
                                     break;
                                 }
                            }
                        }
                    }
                }
                int scanline = global_edges.get(0).yMinimum;
                boolean parity = true;
                int max = 0;
                for (i = 0; i < global_edges.size(); i++){
                    if(i == 0){
                        max = global_edges.get(i).yMaximum;
                    }
                    else{
                        if(global_edges.get(i).yMaximum > max){
                            max = global_edges.get(i).yMaximum;
                        }
                    }
                }
                for(i = scanline; i < max; i++){
                    for(int j = 0; j < global_edges.size();){
                        if(global_edges.get(j).yMinimum!=i){
                            break;
                        }
                        else{
                            active_edges.add(global_edges.get(j));
                            global_edges.remove(j);
                        }
                        
                    }
                     Collections.sort(active_edges, (Edges e, Edges x) -> e.compareTo(x));
                }
                fillPolygon(active_edges, i);
                
                for (int j = active_edges.size()-1; j >-1; j--){
                    if(active_edges.get(j).yMaximum <= i+1){
                        active_edges.remove(j);
                    }
                }
            } 
        }
    }
    
    //Method: start
    //Purpose: This method is used to call the other methods
    public void start(){
        try{
            createWindow();
            initGL();
            render();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //Method: main
    //Purpose: To call the start method
    //and run the program
    public static void main(String[] args) {
        Program2 program = new Program2();
        program.start();
    }   
}
