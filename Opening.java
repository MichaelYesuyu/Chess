/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess2;

/**
 *
 * @author sye-21
 */

import java.util.*;
import java.io.*;

//probably extends the player class
public class Opening {
    
    int count = 0; 
    String whiteMove = "";
    String blackMove = "";
    
    //the ArrayList formating should be white move followed by space followed by black move
    ArrayList<String> allMoves = new ArrayList<String>(); //stores all the moves made in an arrayList, in chess notation
    
    public Opening(String whiteMove, String blackMove){
        this.whiteMove = whiteMove;
        this.blackMove = blackMove;
    }
    
    /**
     * This class picks the move based on a text file
     * Need to at char as a parameter later for color of piece but not included due to testing purposes
     * @param moveNumber the current move number
     * @return returns the move to play to movePick (which doesn't exist yet)
     */
    public String PickMove(int moveNumber){
 
    BufferedReader in = null;
    
     File file = new File("D:\\MyProfile\\Documents\\NetBeansProjects\\chess2\\src\\chess2\\OpeningRepretoire.txt"); //this file contains the Opening repretoires, each line is one Opening
        
     if(!file.exists()){ //tests to see if the file exists
           System.out.println("error");
       }
     
     String line = null;
     String moveToPlay = "";
     String compareMoveLine = "";
     String newMoveLine = "";
     String integerToString;
     int display = 1;
     String returnThis = "00";
     
     boolean keepGoingDown = false; //stops looking down at the next line of the text file unless "granted permission"
     
       try
      { 
           in = new BufferedReader ( 
                new FileReader (file));
           
        for(int i = 0; i<moveNumber; i++){
            
            compareMoveLine = compareMoveLine + allMoves.get(i) + " ";//always add a space as we never want an opening without a next move
            System.out.println(compareMoveLine);
            //System.out.println(compareMoveLine);
            display ++;
        }
        
        
           
        while(!keepGoingDown&&( line = in.readLine() ) != null ){
            boolean handlingLine = false;
            int startingIndex = -1; 
              
            for(int i =0; i < line.length(); i++){
                if(line.length()>i+4&&line.substring(i,i+4).equals("\"m\":")&&!handlingLine){
                    //System.out.println("truth man we are truth ");
                    handlingLine = true;
                    startingIndex = i+6;
                    count++;
                }
                
            }
            //compares the length of the line to the length of the currentMoveLine, if currentMoveLine is longer program skips this line
            //System.out.println(line.length()>compareMoveLine.length());
            if(line.length()>compareMoveLine.length()){
               if(handlingLine&&startingIndex+compareMoveLine.length()<line.length()){
                   
               }
                
                if (handlingLine&&startingIndex+compareMoveLine.length()<line.length()&&line.substring(startingIndex,startingIndex + compareMoveLine.length()).equals(compareMoveLine)){//if the current line contains the current moveLine 
                    //what to do if Opening matches up with an Opening in the repretoire
                    System.out.println("the part of the line that we are looking at " + line.substring(startingIndex,startingIndex+compareMoveLine.length()));
                    System.out.println("the part that we want to compare to  " + compareMoveLine);
                    System.out.println("are they equal? " + line.substring(startingIndex,startingIndex+compareMoveLine.length()).equals(compareMoveLine));
                    System.out.println("line loc" + count + " "  + "line: " + line);
                    boolean handle = false;
                    String returns = "";
                    int constant = startingIndex + compareMoveLine.length();
                    int count= 0;
                    while(!handle){
                        if(count+constant>=line.length()||line.charAt(count+constant)==' '){
                            handle = true;
                            System.out.println("something");
                        }else{
                            returns += line.charAt(count+constant);
                        }
                        count++;
                    }
                    //System.out.println(returnThis);
                    System.out.println(returns);
                    keepGoingDown=true;
                }else{
                    
                    //we want to see what one exampe looks liek 
                }
        }
          
       }
        
       
       in.close();
       System.out.println("00000"); //test code
       System.out.println(count);
       return "00000"; //returns something to show that there is not a match, and the program instead utilizes 

       
       }
        
        catch (IOException e)
        {
            System.out.println("Error");
        }
        return ""; //temporary code
    }
    
    /**
     * The main method is just for testing the program
     */
    public static void main(String [] args){
        Opening opening = new Opening("","");
        opening.allMoves.add("d2d4");
        opening.allMoves.add("g8f6");
        opening.allMoves.add("c2c4");
        opening.allMoves.add("g7g6");
        opening.allMoves.add("b1c3");
        opening.allMoves.add("f8g7");
        opening.allMoves.add("e2e4");
        opening.allMoves.add("d7d6");
        opening.allMoves.add("g1f3");
        opening.allMoves.add("e8g8");
        opening.allMoves.add("f1e2");
        opening.allMoves.add("e7e5");
        opening.allMoves.add("e1g1");
        opening.allMoves.add("b8c6");
        opening.allMoves.add("d4d5");
        opening.allMoves.add("c6e7");
        opening.allMoves.add("f3e1");
        opening.allMoves.add("f6d7");
        //d2d4 g8f6 c2c4 g7g6 b1c3 f8g7 e2e4 d7d6 g1f3 e8g8 f1e2 e7e5 e1g1 b8c6 d4d5 c6e7 f3e1 f6d7
        opening.PickMove(6);
   
    }
    
}