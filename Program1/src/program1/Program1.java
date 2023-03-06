/***************************************************************
 * File: Program1.java
 * Author: Nour Shinnawi
 * Class: CS 4450
 * 
 * Assignment: Program 1
 * Date Last Modified: 9/9/20
 * 
 * Purpose: This program will accept coordinates from a file
 * and draw the primitives in different colors. The program
 * should also be able to use the escape key to quit the program.
 * 
 * Comments: I had trouble reading in the coordinates that had spaces
 * so I changed them to commas and it ended up working. I tried to 
 * fix it but could not figure it out, so any coordinates with spaces
 * do not work.
*****************************************************************/
package program1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Program1 {
    
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
    
    //Method: createWindow
    //Purpose: This method is used to set up the display window
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 1");
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
    private void render(){
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            try{
                File file = new File("coordinates.txt");
                BufferedReader br = new BufferedReader (new FileReader(file));
                String string;
                
                while((string = br.readLine()) != null){
                    String array1[] = string.split(" ");
                    if(array1[0].equalsIgnoreCase("l")){
                        String array2[] = array1[1].split(",");
                        line(Double.parseDouble(array2[0]),
                                Double.parseDouble(array2[2]),
                                Double.parseDouble(array2[2]),
                                Double.parseDouble(array2[3]));
                    }
                    else if(array1[0].equalsIgnoreCase("c")){
                        String array2[] = array1[1].split(",");
                        circle(Float.parseFloat(array2[0]),
                                Float.parseFloat(array2[2]),
                                Float.parseFloat(array2[2]));
                    }
                    else if(array1[0].equalsIgnoreCase("e")){
                        String array2[] = array1[1].split(",");
                        ellipse(Float.parseFloat(array2[0]),
                                Float.parseFloat(array2[2]),
                                Float.parseFloat(array2[2]),
                                Float.parseFloat(array2[3]));
                    }
                }
                Display.update();
            }catch(IOException | NumberFormatException e){
            }
        }
        Display.destroy();
    }
    
    //Method: line
    //Purpose: To create the line primitive
    private void line(double x1, double y1, double x2, double y2){
        glColor3f(1.0f, 0.0f, 0.0f);
        glBegin(GL_LINES);
        glVertex2f((float) x1, (float) y1);
        glVertex2f((float) x2, (float) y2);
        glEnd();
        Display.update();
    }
    
    //Method: circle
    //Purpose: To calculate and create the circle primitive
    private void circle(float x, float y, double radius){
        int line = 200;
        double theta = 2.0f * 3.14159265359;
        glColor3f(0.0f, 0.0f, 1.0f);
        glBegin(GL_LINE_LOOP);
        for(int i=0; i <= line; i++){
            glVertex2f( x + ((float)radius * (float)Math.cos(i * theta / line)),
                    y + ((float)radius * (float)Math.sin(i * theta / line)));
        }
        glEnd();
        Display.update();
    }
    
    //Method: ellipse
    //Purpose: To calculate and create the ellipse primitive
    private void ellipse(float x, float y, float xRadius, float yRadius){
        float num = 100;
        float theta = (float) (2*3.14159265359 / num);
        float cos = (float) Math.cos(theta);
        float sin = (float) Math.sin(theta);
        float a;
        float x1 = 1;
        float y1 = 0;
        glColor3f(0.0f,1.0f,0.0f);
        glBegin(GL_LINE_LOOP);
        for(int i = 0; i < num; i++){
            glVertex2f(x1 * xRadius + x, y1 * yRadius + y);
            a = x1;
            x1 = cos * x1 - sin * y1;
            y1 = sin * a + cos * y1;
        }
        glEnd();
        Display.update();
    }
    
    //Method: main
    //Purpose: To call the start method
    //and run the program
    public static void main(String[] args) {
        Program1 program = new Program1();
        program.start();
    }   
}
