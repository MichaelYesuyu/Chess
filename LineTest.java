/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess2;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import java.io.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author vshah-21
 */
public class LineTest extends JFrame implements Runnable, MouseListener, MouseMotionListener,KeyListener,ChangeListener{
    Thread gameloop;
    BufferedImage backbuffer;
    Graphics2D g2d;
    ArrayList<Double> displayedY= new ArrayList<>();
    ArrayList<Double> allValues = new ArrayList<>();
    
    int numberOfInputs = 0;
    double[] preCreatedValues= {200,500,200,210,400,200,500,200,210,400,200,500,200,210,400,200,500,200,210,400,200,500,200,210,400,200,500,200,210,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,400,300,200,300,420,300,200,250,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,220,230,250,260,275,300,280,270,280,276,275,274,275,274,275,276,275,274,275,274,275,276,275,274,275,274,275,276,275,274,275,274,275,276,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,274,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275,275};//for tests only, sets up a line to start with 
    int range = 25;//sets up the range 
    int setpoint = 275;//sets up the setpoint
    int gamestate = 0;
    double globalLow = 0; 
    double globalHigh =0;
    int currentLoc = 0;
    double scaling = 1;
   
    public LineTest(){
        super("Test");
        setSize(800,800);//sets up the window 
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addMouseListener(this);//the mouselisteners, listens for any mouse events 
        addMouseMotionListener(this);
        addKeyListener(this);
        backbuffer = new BufferedImage(800,800,BufferedImage.TYPE_INT_RGB);//creates a buffered image which graphics will be drawn on 
        g2d= backbuffer.createGraphics();//more graphics setup 
        gameloop = new Thread(this);//starts the game thread 
        gameloop.start();
        
    }
    public void run(){
        Thread current = Thread.currentThread();
        while(current==gameloop){
            try{
                gameloop.sleep(30);//around 60fps, fact check
            }catch(InterruptedException e){
                e.printStackTrace();//if for somereason there is an error, print where it occured 
            }
            gameUpdate();
        }
        
    }
    
    public void gameUpdate(){
        if(gamestate ==1||gamestate ==3){
            g2d.setColor(Color.WHITE);//sets the color to white because that is the first color that we need 
            g2d.fillRect(0, 0,800,800);
            g2d.setColor(Color.BLACK);
            double max = 0; 
            double min =0;
            if(displayedY.size()>0){
                min  =displayedY.get(0);
            }
            for(int i =0; i < displayedY.size();i++){
                if(i<displayedY.size()-1){
                   if(displayedY.get(i)>max){
                       max=displayedY.get(i);
                       
                   }else if(displayedY.get(i)<min){
                       min = displayedY.get(i);
                   }
                   
                }
            }
            double factor = 400/(max-min);//creates the scaling factor 
            for(int i =0; i < displayedY.size()-1;i++){
                g2d.setColor(Color.black);
                g2d.drawLine((800/displayedY.size())*i  + 40,(int)(500-((displayedY.get(i)-min)*factor)),(800/displayedY.size())*(i+1) + 40,(int)(500-((displayedY.get(i+1)-min)*factor))); 
                
            }
            
            //g2d.drawRect(0,setpoint*factor-range*factor,800, 2*range*factor);
            g2d.setColor(Color.red);
            g2d.drawLine(0,(int)(500-((setpoint-min)*factor)),800, (int)(500-((setpoint-min)*factor)));
            g2d.setColor(Color.black);
            g2d.drawLine(20,500,20,100);//draws the y axis;
            g2d.drawLine(20,500,720,500);//draws the x axis;
            for(int i =0; i < 11;i++){
                g2d.drawLine(15,500-40*i,25,500-40*i);
                g2d.drawString(String.valueOf(min+((max-min)/10)*i),30,500-40*i);
            }
            for(int i =0; i < 50;i++){
                g2d.drawLine((600/50)*i+30,495,(600/50)*i +30,505);
            }
            g2d.drawRect(325,600,125,100);
            g2d.drawString("stop getting elements",330,650);
        }
        else if(gamestate==0){
            
        }else if(gamestate ==4){
            //do nothing for now 
        }
        repaint();
    }
    public void updateDisplay(double y){
        if(gamestate==1){
            
            if(displayedY.size()>51){
                displayedY.remove(0);
                
                displayedY.add(y);

            }else{
                displayedY.add(y);
            }
            System.out.println("adding + " + y);
            allValues.add(y);
        }
        
        else{
            
        }
    }
    public void updateDisplays(ArrayList<Double> display,int x,int y){
        
        for(int i =displayedY.size()-1; i >-1;i--){//same with black moves 
            displayedY.remove(i);
            
        }
        for(int i =x; i < y;i++){
            displayedY.add(display.get(i));
  
        }
    }
    public void paint(Graphics g){
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(backbuffer,0,0,this);
        
    }
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}  
    public void keyReleased(KeyEvent e) {
        updateDisplay(preCreatedValues[currentLoc]);
        currentLoc++;
        if(e.getKeyChar()=='q'){
           gamestate=3;
            numberOfInputs = 0; 
            System.out.println("true");
        } 
    }
    public void mousePressed(MouseEvent e ){
        
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseDragged(MouseEvent e ){
        numberOfInputs++;
        if(gamestate==3){
        if(allValues.size()>50+numberOfInputs)
            updateDisplays(allValues,numberOfInputs,numberOfInputs+50);
        }
    }
    public void mouseMoved(MouseEvent e){}
     public void mouseClicked(MouseEvent e){
     }//added so that there is no exception
    public void mouseExited(MouseEvent e){}//added so that there is no exception
    public void mouseEntered(MouseEvent e){}//added so that there is no exception
    public void stateChanged(ChangeEvent event){
        
    }
    public boolean inButton(int x1, int x2){
        return true;
    }
    public static void main(String[] args){
        
        new LineTest();
    }
}
