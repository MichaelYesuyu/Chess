/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *We have a state to determine whether or not the ai is considering openings or picking a move with eval 
 * The ai has the common board, the the player that it is optimizing moves for and the opposing player 
 * all moves is part of opening and it is a list of all the moves made(in chess notation) and this is compared against opening move lists 
 * The count is also for opening and keeps track of all the moves made 
 * whiteMove is a list of white moves made 
 * blackMove is a list of black moves made 
 */
public class AI{
    int state;
    Board board;
    Player player;
    Player enemy;
    ArrayList<String> allMoves = new ArrayList<String>(); //stores all the moves made in an arrayList, in chess notation
    int count = 0; 
    String whiteMove = "";
    String blackMove = "";
    boolean isActive = false;
    /**
     * Set globals = to chess 2 globals 
     * @param player the player we are creating a move for 
     * @param board the common board(an array of strings representing piece locations that every class has access to)  
     * @param enemy  the enemy 
     */
    public AI(Player player, Board board, Player enemy){
        this.player = player;
        this.board = board;
        this.enemy = enemy;
        state = 0; 
        isActive=true;
    }
    //blank constructor to throw null pointer exception 
    public AI(Board board){
        this.board = board;
        isActive = false;
    }
    public ArrayList<String> allMoves(){
        return this.allMoves;
    }
    /**
     * Used to pick our opening move 
     * First we created a buffered reader to read through our file 
     * Next get our file 
     * Make sure it exists 
     * Create a series of strings to store specific parts of each line(these will be reused as we move through each line)
     * Try and create a file reader and then create an String containing all the moves that we have made in order to compare to each line 
     *To simplify the steps after this(we look at every line and compare our move line to that line after the \m:( each line has this before 
     * they show their opening and see if it matches up. If there is a match return that move 
     * If there are no matches between the moves made and any of the lines, then return 00000 
     * @return 
     */
    public String PickOpeningMove(){
    
    BufferedReader in = null;
    
     File file = new File("OpeningRepretoire.txt"); //this file contains the Opening repretoires, each line is one Opening
        
     if(!file.exists()){ //tests to see if the file exists
           System.out.println("error have no maybe so");
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
           
        for(int i = 0; i<allMoves.size(); i++){
            
            System.out.println("runnign here");
            compareMoveLine = compareMoveLine + allMoves.get(i) + " ";//always add a space as we never want an opening without a next move
            //System.out.println("---"+compareMoveLine);
            //System.out.println(compareMoveLine);
            display ++;
        }
        
        
           
        while(!keepGoingDown&&( line = in.readLine() ) != null ){
            boolean handlingLine = false;
            int startingIndex = -1; 
            int endingIndex = -1;
            
            for(int i =0; i < line.length(); i++){
                if(line.length()>i+4&&line.substring(i,i+4).equals("\"m\":")&&!handlingLine){
                    //System.out.println("truth man we are truth ");
                    handlingLine = true;
                    startingIndex = i+6;
                    endingIndex = line.length()-3;
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
                    
                    boolean handle = false;
                    String returns = "";
                    int constant = startingIndex + compareMoveLine.length();
                    int count= 0;
                    while(!handle){
                        if(count+constant>=line.length()||line.charAt(count+constant)==' '||line.charAt(count+constant)=='"'){
                            handle = true;
      
                        }else{
                            returns += line.charAt(count+constant);
                        }
                        count++;
                    }
                    
                    keepGoingDown=true;
                    return returns;
                }else{
                    
                    //we want to see what one exampe looks like 
                }
           }
       }
       in.close();
       //System.out.println(count);
       return "00000"; //returns something to show that there is not a match, and the program instead utilizes eval

                                                                                                                                  
       }
        
        catch (IOException e)
        {
            System.out.println("Error");
        }
        return ""; //temporary code
    }
    /**
     * For conversions between chess notation and our board notation 
     * @param x The letter in chess notation 
     * @return The location on our board a = 0, b= 1 etc. 
     */
    public int LetterToInt(String x){
        switch(x){
            case "a":
                return 0;
            case "b":
                return 1;
            case "c":
                return 2;
            case "d":
                return 3;
            case "e":
                return 4;
            case "f":
                return 5;
            case "g":
                return 6;
            case "h":
                return 7;
            default:
                return 00000;
        }
    }
    /**
     * Converting from our board notation to chess notation so we can take moves that the human makes and put them in chess notation 
     * @param x the board location in int form 
     * @return the resulting letter 
     */
    public String IntToLetter(int x){
        switch(x){
            case 0:
                return "a";
            case 1:
                return "b";
            case 2:
                return "c";
            case 3:
                return "d";
            case 4:
                return "e";
            case 5:
                return "f";
            case 6:
                return "g";
            case 7:
                return "h";
            default:
                return "00000";
        }
    }
    /**
     * Accounts for the fact that our locations are flopped on the board so simply flips integers around to put them in chess notation 
     * @param inverse the board location that we want to flip
     * @return the flipped int 
     */
    public int InverseInt (int inverse){
        switch(inverse){
            case 0:
                return 7;
            case 1:
                return 6;
            case 2:
                return 5;
            case 3:
                return 4;
            case 4:
                return 3;
            case 5:
                return 2;
            case 6:
                return 1;
            case 7:
                return 0;
            default:
                return 00000;
        }
    }
    /**
     * Takes our Pick opening function and outputs it as a move on the board 
     * String movePicked is the output of this 
     * If the movePicked returns a move(not "00000" which is no match found) then add the move to all moves 
     * use the letter to int function and inverse int to convert chess notation into board coordinates locX and locY(the starting coordinates) 
     * Store the board tag at locX locY and get the first character so we know what type of piece is at this board positon 
     * Store the other half of the movePicked as board coordinates(where the piece will move) 
     * Using the first character of the original board tag we figure out what type of piece we are supposed to be looking at and then check if 
     * our new coordinates are legal coordinates for any of those pieces 
     * If they are then move that piece and update the board accordingly 
     * Also if we do not make a move then change our state so that we start evaluating moves 
     */
    public void openingMove(){
        System.out.println("running");
        String movePicked = PickOpeningMove(); //movePicked is a string which opening repretoire plays. example is d2d4
        if(!movePicked.equals("00000")){
                
                allMoves.add(movePicked);
                
                int locX = LetterToInt(movePicked.substring(0,1));
                
                int locY = InverseInt(Integer.valueOf(movePicked.substring(1,2)) - 1);
               
                String tag = board.getBoard()[locY][locX];
             
                char type = tag.charAt(1);
                int newLocX = LetterToInt(movePicked.substring(2,3));
                int newLocY = InverseInt(Integer.valueOf(movePicked.substring(3,4)) - 1);
                System.out.println("newLocX " + newLocX + " newLocY " + newLocY );
                        
                
                System.out.println(type);
                switch(type){
                    
                    case 'P':
                        for(int i =0 ; i< 8; i++){
                            if(player.pawns[i].getX()==locX&&player.pawns[i].getY()==locY){
                                player.pawns[i].checkLegalMove();
                                
                                player.pawns[i].checkPossibleMoves(newLocX,newLocY,true);
                                System.out.println(player.pawns[i].getX() + " " + player.pawns[i].getY());
                                board.switchTurn();
                            }
                        }
                        break;
                    case 'B':
                        for(int i =0 ; i< 2; i++){
                            if(player.bishops[i].getX()==locX&&player.bishops[i].getY()==locY){
                                player.bishops[i].checkLegalMove();
                                player.bishops[i].checkPossibleMoves(newLocX,newLocY,true);
                                board.switchTurn();
                            }
                        }
                        break;
                    case 'R':
                        for(int i =0 ; i< 2; i++){
                            if(player.rooks[i].getX()==locX&&player.rooks[i].getY()==locY){
                                player.rooks[i].checkLegalMove();
                                player.rooks[i].checkPossibleMoves(newLocX,newLocY,true);
                                board.switchTurn();
                            }
                        }
                        break;
                    case 'H':
                        for(int i =0 ; i< 2; i++){
                            if(player.knights[i].getX()==locX&&player.knights[i].getY()==locY){
                                player.knights[i].checkLegalMove();
                                player.knights[i].checkPossibleMoves(newLocX,newLocY,true);
                                board.switchTurn();
                            }
                        }
                        break;
                     case 'Q':
                        
                            if(player.queen.getX()==locX&&player.queen.getY()==locY){
                                player.queen.checkPossibleMoves(newLocX,newLocY,true);
                            }
                        
                        break;
                    case 'K':
                       
                            if(player.king.getX()==locX&&player.king.getY()==locY){
                                player.king.checkPossibleMoves(newLocX,newLocY,true);
                            }
                        
                        break;
                }
                
        
                
            }
        else{
            state=1;
            smartMove();
        }
    }
    /**
     * Used to add moves to all moves for opening and is called in chess2  
     * Get the last move made 
     * Use our functions to convert from board notation to chess notation 
     * add the new string to allMoves
     */
    public void addMove(){
        String stored = board.getMoveRecorder().get(board.getMoveRecorder().size()-1);//tagy1x1yx
        String x1 = IntToLetter(Integer.valueOf(stored.substring(6,7)));
        String x= IntToLetter(Integer.valueOf(stored.substring(8,9)));
        String y1= String.valueOf(8-Integer.valueOf(stored.substring(5,6)));
        String y = String.valueOf(8-Integer.valueOf(stored.substring(7,8)));
        System.out.println(x1 + y1 + x + y);
        allMoves.add(x1+y1+x+y);
    }
    /**
     * Used to make sure we don't randomly give up a piece by saying if we protect a piece the same amount of times as our enemy can take it its safe 
     * otherwise subtract parameter val(the value of the piece) so we are forced to protect it 
     * @param x the location of the piece we are checking 
     * @param y the y location of the piece we are protecting 
     * @param val the value of the piece we are protecting 
     * @return if we fully protect the piece return 0, else return the value of the piece which is subtracted in eval 
     */
    public double handleMove(int x, int y,double val){
        player.deleteMoves();
        enemy.deleteMoves();
        enemy.updateLegals();
        player.updateLegals();
        int updater = 0;
        String[][] storedBoard = new String[8][8];
        String saver = board.getBoard()[y][x];
        
        for(int i =0; i < enemy.movesX.size();i++){
            if(enemy.movesX.get(i)==x&&enemy.movesY.get(i)==y){
                updater--;
            }
        }
        if(player.getPlayerTurn()){
            board.getBoard()[y][x] = "bP000";
        }
        else{
            board.getBoard()[y][x] = "wP000";
        }
        
        for(int i =0; i < player.movesX.size();i++){
            if(player.movesX.get(i)==x&&player.movesY.get(i)==y){
                updater++;
            }
        }
        board.getBoard()[y][x]=saver;
        player.deleteMoves();
        enemy.deleteMoves();
        if(updater<0){
            return val;
        }
        return 0;
    }
    /**
     * Where we evaluate who is winning 
     * For pawns our x and y locations give bonuses and pawns are worth +1 
     * Rooks are worth 5, bishops 3.3 knights 2.7, queens 9 and kings 100(so we never give up our king) 
     * Use handle move to make sure each piece is protected 
     * Whitetotal keeps track of the totals for all of the white pieces on the board and blacktotal all the black pieces 
     * depending on if we are black or white we subtract our total from the enemies total and return that value 
     * @param isWhite whether or not we are white 
     * @return our total - enemies total 
     */
    public double evalBoard(boolean isWhite){
        double whiteTotal = 0;
        double blackTotal =0;
        
        for(int i =0;i<8;i++){
            for(int j =0;j<8;j++){
                
                if(board.getBoard()[i][j].startsWith("wP")){
                    double jconstant = 0+j; 
                    if(j >=3){jconstant=7-j;}
                    if(i==4){
                        System.out.println("hey we are looking at this " + " piece tag " + board.getBoard()[i][j] + " " +(1+(0.1*(7-i)) + (0.2*(jconstant + (7-i)))- handleMove(j,i, (1+0.1*(7-i)))));
                    }
                    
                    whiteTotal+=1+(0.01*(7-i)) + (0.05*(jconstant + (7-i)))- handleMove(j,i, (1+0.01*(7-i)));
                    
                }
                if(board.getBoard()[i][j].startsWith("wR")){
                    
                    whiteTotal+=5-handleMove(j,i,5);
                }
                if(board.getBoard()[i][j].startsWith("wB")){
                    
                    whiteTotal+=3.3-handleMove(j,i,3.3);
                }
                if(board.getBoard()[i][j].startsWith("wH")){
                    
                    whiteTotal+=2.7-handleMove(j,i,2.7);
                }
                if(board.getBoard()[i][j].startsWith("wK")){
                    whiteTotal+=100-handleMove(j,i,100);
                }
                if(board.getBoard()[i][j].startsWith("wQ")){
                   
                    whiteTotal+=9-handleMove(j,i,9);
                }
                if(board.getBoard()[i][j].startsWith("bP")){
                   int jconstant = 0+j; 
                   if(j >=3){jconstant=7-j;}
                    blackTotal+=1+(0.01*i) +(0.05*(jconstant+i))- handleMove(j,i, (1+(0.01*i)));
                    
                }
                if(board.getBoard()[i][j].startsWith("bR")){
                    
                    blackTotal+=5-handleMove(j,i,5);
                }
                if(board.getBoard()[i][j].startsWith("bB")){
                    
                    blackTotal+=3.3-handleMove(j,i,3.3);
                }
                if(board.getBoard()[i][j].startsWith("bH")){
                    
                    blackTotal+=2.7-handleMove(j,i,2.7);
                }
                if(board.getBoard()[i][j].startsWith("bK")){
                    blackTotal+=100-handleMove(j,i,100);
                }
                if(board.getBoard()[i][j].startsWith("bQ")){
                   
                    blackTotal+=9-handleMove(j,i,9);
                }
            }
        }
        if(isWhite){
            return whiteTotal-blackTotal;
        }
        
        return blackTotal-whiteTotal;
    }/**
     * Used to handle any collisions that occur so that we can make sure there are the correct amount of pieces targeting another piece in handle moves 
     * and to make sure we can take a piece to get out of check 
     * @param originalSquare the tag of the square at the location(where the supposed collision takes place)
     * @param newSquare the new tag of the square at the location
     * @param x the x coordinate of the location
     * @param y the y coordinate of the location
     */
    public void handleTempCollisions(String originalSquare, String newSquare, int x, int y){
        boolean control = false;
        if(player.getPlayerTurn()&&originalSquare.charAt(0)=='b'&&newSquare.charAt(0)=='w'){
            control = true;
        }else if(!player.getPlayerTurn()&&originalSquare.charAt(0)=='w'&&newSquare.charAt(0)=='b'){
            control = true;
        }
        if(control){
            for(int i =0;i<8;i++){
                if(enemy.pawns[i].getX()==x&&enemy.pawns[i].getY()==y){
                    enemy.pawns[i].controlledOff = false;
                }
            }
            for(int i =0;i<2;i++){
                if(enemy.rooks[i].getX()==x&&enemy.rooks[i].getY()==y){
                    enemy.rooks[i].controlledOff = false;
                }
            }
            for(int i =0;i<2;i++){
                if(enemy.bishops[i].getX()==x&&enemy.bishops[i].getY()==y){
                    enemy.bishops[i].controlledOff = false;
                }
            }
            for(int i =0;i<2;i++){
                if(enemy.knights[i].getX()==x&&enemy.knights[i].getY()==y){
                    enemy.knights[i].controlledOff = false;
                }
            }
            if(enemy.queen.getX()==x&&enemy.queen.getY()==y){
                    enemy.queen.controlledOff = false;
                }
            
        }
    }
    /**
     * Checks if we protect a square, used to moving to a protected square
     * @param x the x location of the square
     * @param y the y location of the square 
     * @return if we protect it or not 
     */
    public boolean isProtectedSquare(int x, int y){
        player.updateLegals();
        for(int i =0; i < player.movesX.size();i++){
            if(player.movesX.get(i)==x&&player.movesY.get(i)==y){
                
                return true;
            }
        }
        
        return false;
    }
    /**
     * The main part of the ai class and it is where all of our functions are put together 
     * First if we are in the opening state play out opening 
     * Else play out evaluation 
     * What we do here to summarize is we create arraylists that store information that we need and then loop through every single move, play out the 
     * move, evaluate the board and if a move is legal(i.e no check) then add the result of the eval, the moveX and moveY,the index of the piece, 
     * and the piece tag to our arrayLists 
     * After we have evaluated  all the positions, loop through these arrayLists that we have created and pick out the one that results in the 
     * highest evaluation and execute that move 
     */
    public void smartMove(){
        if(player.getPlayerTurn()==board.getTurn()){
        if(state==0){
           openingMove(); 
        }
        else if(state==1){
            if(player.getPlayerTurn() == board.getTurn()){
                ArrayList<Double> evals = new ArrayList<>();
                ArrayList<Integer> moveX = new ArrayList<>();
                ArrayList<Integer> moveY = new ArrayList<>();
                ArrayList<Integer> storedIndex = new ArrayList<>();
                ArrayList<String> pieceTag = new ArrayList<>();       
                int movecount = 0; 
                String maxTag  = "";
                
                for(int i =0; i < 8;i++){
                    player.pawns[i].deleteMoves();
                    player.pawns[i].checkLegalMove();
                    int storedX = player.pawns[i].getX();
                    int storedY = player.pawns[i].getY();
                    System.out.println("stored x and stored y" + " x: " + storedX + " Y: " + storedY);
                    ArrayList<Integer> cloneX = new ArrayList<>();
                    ArrayList<Integer> cloneY = new ArrayList<>();
                    int movecounts = player.pawns[i].getMoveCount();
                    System.out.println("movecount " + movecounts);
                    for(int j =0; j < player.pawns[i].getMovesX().size();j++){
                        cloneX.add(player.pawns[i].getMovesX().get(j));
                        cloneY.add(player.pawns[i].getMovesY().get(j));
                    }
                    if(cloneY.size()>1){
                        System.out.println("so we are considering mulitple");
                    }
                    for(int j =0; j < cloneY.size();j++){
                        double bonus = 0; 
                        if(player.pawns[i].getMoveCount()==0){
                            bonus =0.05;
                        }
                        if(isProtectedSquare(cloneX.get(j),cloneY.get(j))){
                            bonus += 0.05;
                        }else if(allMoves.size()>=6){
                            bonus -=.2;
                        }
                        
                        System.out.println(player.pawns[i].getTag()+ " " + cloneX.get(j) + "  "+ cloneY.get(j));
                        player.pawns[i].checkLegalMove();
                        String storedTag = board.getBoard()[cloneY.get(j)][cloneX.get(j)];
                        
                        player.pawns[i].checkPossibleMoves(cloneX.get(j),cloneY.get(j),false);
                        handleTempCollisions(storedTag,player.pawns[i].getTag(),player.pawns[i].getX(),player.pawns[i].getY());
                        
                        if(board.isInSpecificCheck(enemy.getPlayerTurn())){
                           bonus = 0.25; 
                        }
                        enemy.deleteMoves();
                        enemy.updateLegals();
                        if(enemy.movesX.isEmpty()){
                            board.setCheckMessage("AI WINS GG");
                            bonus+=1000;
                            System.out.println("chekcmate gg man gg man gg man ");
                        }
                        
                        if(!board.isInSpecificCheck(player.getPlayerTurn())){
                            evals.add(evalBoard(player.getPlayerTurn()));
                            moveX.add(cloneX.get(j));
                            moveY.add(cloneY.get(j));
                            storedIndex.add(i);
                            pieceTag.add(player.pawns[i].getTag());
                        }else{
                           
                            if(!board.isInSpecificCheck(player.getPlayerTurn())){
                                evals.add(evalBoard(player.getPlayerTurn())+bonus);
                                moveX.add(cloneX.get(j));
                                moveY.add(cloneY.get(j));
                                storedIndex.add(i);
                                pieceTag.add(player.pawns[i].getTag());
                            }
                            
                            
                        }
                        
                        player.pawns[i].moveChanges(storedX,storedY);
                        player.pawns[i].editMoveCount(movecounts);
                        System.out.println(player.pawns[i].getMoveCount());
                        board.getBoard()[player.pawns[i].getY()][player.pawns[i].getX()] = player.pawns[i].getTag();
                        board.getBoard()[player.pawns[i].getY1()][player.pawns[i].getX1()] = storedTag;
                        movecount++;
                            
                    }

                }
                for(int i =0; i < 2;i++){
                    player.bishops[i].deleteMoves();
                    player.bishops[i].checkLegalMove();
                    int storedX = player.bishops[i].getX();
                    int storedY = player.bishops[i].getY();
                    System.out.println("stored x and stored y" + " x: " + storedX + " Y: " + storedY);
                    ArrayList<Integer> cloneX = new ArrayList<>();
                    ArrayList<Integer> cloneY = new ArrayList<>();
                    int movecounts = player.bishops[i].getMoveCount();
                    for(int j =0; j < player.bishops[i].getMovesX().size();j++){
                        cloneX.add(player.bishops[i].getMovesX().get(j));
                        cloneY.add(player.bishops[i].getMovesY().get(j));
                    }

                    for(int j =0; j < cloneY.size();j++){
                        player.bishops[i].checkLegalMove();    
                        double bonus = 0; 
                        if(player.bishops[i].getMoveCount()==0){
                            bonus =0.03;
                        }
                        if(isProtectedSquare(cloneX.get(j),cloneY.get(j))){
                            bonus += 0.2;
                        }
                        else if(allMoves.size()>=6){
                            bonus -=.2;
                        }
                        if(player.getPlayerTurn()&&player.bishops[i].getMoveCount()>0&&cloneY.get(j)==7){
                            bonus-=0.01;
                        }else if(!player.getPlayerTurn()&&player.bishops[i].getMoveCount()>0&&cloneY.get(j)==0){
                            bonus-=0.01;
                        }
                        String storedTag = board.getBoard()[cloneY.get(j)][cloneX.get(j)];
                        
                        player.bishops[i].checkPossibleMoves(cloneX.get(j),cloneY.get(j),false);
                        handleTempCollisions(storedTag,player.bishops[i].getTag(),player.bishops[i].getX(),player.bishops[i].getY());
                       
                        if(board.isInSpecificCheck(enemy.getPlayerTurn())){
                            bonus = 0.25; 
                        }
                        enemy.deleteMoves();
                        enemy.updateLegals();
                        if(enemy.movesX.isEmpty()){
                            board.setCheckMessage("AI WINS GG");
                            bonus+=1000;
                            System.out.println("chekcmate gg man gg man gg man ");
                        }
                        if(!board.isInSpecificCheck(player.getPlayerTurn())){
                            evals.add(evalBoard(player.getPlayerTurn())+bonus);
                            moveX.add(cloneX.get(j));
                            moveY.add(cloneY.get(j));
                            storedIndex.add(i);
                            pieceTag.add(player.bishops[i].getTag());
                        }enemy.setAlives();
                        player.bishops[i].moveChanges(storedX,storedY);
                        player.bishops[i].editMoveCount(movecounts);
                        board.getBoard()[player.bishops[i].getY()][player.bishops[i].getX()] = player.bishops[i].getTag();
                        board.getBoard()[player.bishops[i].getY1()][player.bishops[i].getX1()] = storedTag;
                        movecount++;

                    }

                }
                for(int i =0; i < 2;i++){
                   
                    player.rooks[i].deleteMoves();
                    player.rooks[i].checkLegalMove();
                    int storedX = player.rooks[i].getX();
                    int storedY = player.rooks[i].getY();
                    System.out.println("stored x and stored y" + " x: " + storedX + " Y: " + storedY);
                    ArrayList<Integer> cloneX = new ArrayList<>();
                    ArrayList<Integer> cloneY = new ArrayList<>();
                    int movecounts = player.rooks[i].getMoveCount();
                    for(int j =0; j < player.rooks[i].getMovesX().size();j++){
                        cloneX.add(player.rooks[i].getMovesX().get(j));
                        cloneY.add(player.rooks[i].getMovesY().get(j));
                    }

                    for(int j =0; j < cloneY.size();j++){
                        
                        player.rooks[i].checkLegalMove();    
                        double bonus = 0; 
                        if(player.rooks[i].getMoveCount()==0){
                            bonus =0.05;
                        }
                        if(isProtectedSquare(cloneX.get(j),cloneY.get(j))){
                            bonus += 0.1;
                        }else if(allMoves.size()>=6){
                            bonus -=.2;
                        }
                        if(player.getPlayerTurn()&&player.rooks[i].getMoveCount()>0&&cloneY.get(j)==7){
                            bonus-=0.01;
                        }else if(!player.getPlayerTurn()&&player.rooks[i].getMoveCount()>0&&cloneY.get(j)==0){
                            bonus-=0.01;
                        }
                        String storedTag = board.getBoard()[cloneY.get(j)][cloneX.get(j)];
                        
                        player.rooks[i].checkPossibleMoves(cloneX.get(j),cloneY.get(j),false);
                        handleTempCollisions(storedTag,player.rooks[i].getTag(),player.rooks[i].getX(),player.rooks[i].getY());
                       
                        if(board.isInSpecificCheck(enemy.getPlayerTurn())){
                           bonus = 0.25; 
                        }
                        enemy.deleteMoves();
                        enemy.updateLegals();
                        if(enemy.movesX.isEmpty()){
                            board.setCheckMessage("AI WINS GG");
                            bonus+=1000;
                            System.out.println("chekcmate gg man gg man gg man ");
                        }
                        if(!board.isInSpecificCheck(player.getPlayerTurn())){
                            evals.add(evalBoard(player.getPlayerTurn())+bonus);
                            moveX.add(cloneX.get(j));
                            moveY.add(cloneY.get(j));
                            storedIndex.add(i);
                            pieceTag.add(player.rooks[i].getTag());
                        }
                        enemy.setAlives();
                         player.rooks[i].moveChanges(storedX,storedY);
                        player.rooks[i].editMoveCount(movecounts);
                        board.getBoard()[player.rooks[i].getY()][player.rooks[i].getX()] = player.rooks[i].getTag();
                        board.getBoard()[player.rooks[i].getY1()][player.rooks[i].getX1()] = storedTag;
                        movecount++;

                    }

                }
                for(int i =0; i < 2;i++){
                    player.knights[i].deleteMoves();
                    player.knights[i].checkLegalMove();
                    int storedX = player.knights[i].getX();
                    int storedY = player.knights[i].getY();
                    System.out.println("stored x and stored y" + " x: " + storedX + " Y: " + storedY);
                    ArrayList<Integer> cloneX = new ArrayList<>();
                    ArrayList<Integer> cloneY = new ArrayList<>();
                    int movecounts = player.knights[i].getMoveCount();
                    for(int j =0; j < player.knights[i].getMovesX().size();j++){
                        cloneX.add(player.knights[i].getMovesX().get(j));
                        cloneY.add(player.knights[i].getMovesY().get(j));
                    }

                    for(int j =0; j < cloneY.size();j++){
                        
                        player.knights[i].checkLegalMove();
                        String storedTag = board.getBoard()[cloneY.get(j)][cloneX.get(j)];
                        double bonus = 0; 
                        if(player.knights[i].getMoveCount()==0){
                            bonus =0.031;
                        }
                        if(isProtectedSquare(cloneX.get(j),cloneY.get(j))){
                            bonus +=0.1;
                        }
                        else if(allMoves.size()>=6){
                            bonus -=.2;
                        }if(player.getPlayerTurn()&&player.knights[i].getMoveCount()>0&&cloneY.get(j)==7){
                            bonus-=0.01;
                        }else if(!player.getPlayerTurn()&&player.knights[i].getMoveCount()>0&&cloneY.get(j)==0){
                            bonus-=0.01;
                        }
                        player.knights[i].checkPossibleMoves(cloneX.get(j),cloneY.get(j),false);
                        handleTempCollisions(storedTag,player.knights[i].getTag(),player.knights[i].getX(),player.knights[i].getY());
                        
                        if(board.isInSpecificCheck(enemy.getPlayerTurn())){
                           bonus = 0.25; 
                        }
                        
                        enemy.deleteMoves();
                        enemy.updateLegals();
                        if(enemy.movesX.isEmpty()){
                            board.setCheckMessage("AI WINS GG");
                            bonus+=1000;
                            System.out.println("chekcmate gg man gg man gg man ");
                        }
                        if(!board.isInSpecificCheck(player.getPlayerTurn())){
                            evals.add(evalBoard(player.getPlayerTurn())+bonus);
                            moveX.add(cloneX.get(j));
                            moveY.add(cloneY.get(j));
                            storedIndex.add(i);
                            pieceTag.add(player.knights[i].getTag());
                        }
                        enemy.setAlives();
                        player.knights[i].moveChanges(storedX,storedY);
                        player.knights[i].editMoveCount(movecounts);
                        board.getBoard()[player.knights[i].getY()][player.knights[i].getX()] = player.knights[i].getTag();
                        board.getBoard()[player.knights[i].getY1()][player.knights[i].getX1()] = storedTag;
                        movecount++;

                    }

                }
                for(int i =0; i < 1;i++){
                    player.queen.deleteMoves();
                    player.queen.checkLegalMove();
                    int storedX = player.queen.getX();
                    int storedY = player.queen.getY();
                    System.out.println("stored x and stored y" + " x: " + storedX + " Y: " + storedY);
                    ArrayList<Integer> cloneX = new ArrayList<>();
                    ArrayList<Integer> cloneY = new ArrayList<>();
                    int movecounts = player.queen.getMoveCount();
                    for(int j =0; j < player.queen.getMovesX().size();j++){
                        cloneX.add(player.queen.getMovesX().get(j));
                        cloneY.add(player.queen.getMovesY().get(j));
                    }

                    for(int j =0; j < cloneY.size();j++){
                        
                        player.queen.checkLegalMove();
                        String storedTag = board.getBoard()[cloneY.get(j)][cloneX.get(j)];
                        double bonus = 0; 
                        //make sure we have made a few moves before we add a bonus to moving the queen
                        if(player.queen.getMoveCount()==0&&allMoves.size()>10){
                            bonus =0.09;
                        }if(isProtectedSquare(cloneX.get(j),cloneY.get(j))){
                            bonus += 0.2;
                        }else if(allMoves.size()>=6){
                            bonus -=.2;
                        }
                        if(player.getPlayerTurn()&&player.queen.getMoveCount()>0&&cloneY.get(j)==7){
                            bonus-=0.01;
                        }else if(!player.getPlayerTurn()&&player.queen.getMoveCount()>0&&cloneY.get(j)==0){
                            bonus-=0.01;
                        }
                        player.queen.checkPossibleMoves(cloneX.get(j),cloneY.get(j),false);
                        handleTempCollisions(storedTag,player.queen.getTag(),player.queen.getX(),player.queen.getY());
                        
                        if(board.isInSpecificCheck(enemy.getPlayerTurn())){
                           bonus = 0.25; 
                           System.out.println("queen is checking the king ");
                        }
                        
                        enemy.deleteMoves();
                        enemy.updateLegals();
                        if(enemy.movesX.isEmpty()){
                            board.setCheckMessage("AI WINS GG");
                            bonus+=1000;
                            System.out.println("chekcmate gg man gg man gg man ");
                        }
                        if(!board.isInSpecificCheck(player.getPlayerTurn())){
                            
                            evals.add(evalBoard(player.getPlayerTurn())+bonus);
                            moveX.add(cloneX.get(j));
                            moveY.add(cloneY.get(j));
                            storedIndex.add(i);
                            pieceTag.add(player.queen.getTag());
                        } else{
                            
                        }
                        enemy.setAlives();
                        player.queen.moveChanges(storedX,storedY);
                        player.queen.editMoveCount(movecounts);
                        
                        board.getBoard()[player.queen.getY()][player.queen.getX()] = player.queen.getTag();
                        board.getBoard()[player.queen.getY1()][player.queen.getX1()] = storedTag;
                        movecount++;

                    }

                }
                for(int i =0; i < 1;i++){
                    player.king.deleteMoves();
                    player.king.checkLegalMove();
                    int storedX = player.king.getX();
                    int storedY = player.king.getY();
                    System.out.println("stored x and stored y" + " x: " + storedX + " Y: " + storedY);
                    ArrayList<Integer> cloneX = new ArrayList<>();
                    ArrayList<Integer> cloneY = new ArrayList<>();
                    int movecounts = player.king.getMoveCount();
                    for(int j =0; j < player.king.getMovesX().size();j++){
                        cloneX.add(player.king.getMovesX().get(j));
                        cloneY.add(player.king.getMovesY().get(j));
                    }

                    for(int j =0; j < cloneY.size();j++){
                        //player.king.deleteMoves();
                        //player.king.checkLegalMove();
                        player.king.checkLegalMove();
                        String storedTag = board.getBoard()[cloneY.get(j)][cloneX.get(j)];
                        double bonus = 0; 
                        if(player.king.special.get(j).startsWith("castle")){
                            bonus =0.15;
                            
                        }
                        if(isProtectedSquare(cloneX.get(j),cloneY.get(j))){
                            bonus -= 0.01;
                        }else if(allMoves.size()>6){
                            bonus -=.2;
                        }
                        
                        player.king.checkPossibleMoves(cloneX.get(j),cloneY.get(j),false);
                        handleTempCollisions(storedTag,player.king.getTag(),player.king.getX(),player.king.getY());
                        
                        if(board.isInSpecificCheck(enemy.getPlayerTurn())){
                           bonus = 0.25; 
                        }
                        enemy.deleteMoves();
                        enemy.updateLegals();
                        if(enemy.movesX.isEmpty()){
                            board.setCheckMessage("AI WINS GG");
                            bonus+=1000;
                            System.out.println("chekcmate gg man gg man gg man ");
                        }
                        if(!board.isInSpecificCheck(player.getPlayerTurn())){      
                            evals.add(evalBoard(player.getPlayerTurn())+bonus);
                            moveX.add(cloneX.get(j));
                            moveY.add(cloneY.get(j));
                            storedIndex.add(i);
                            pieceTag.add(player.king.getTag());
                        }
                        enemy.setAlives();
                        player.king.moveChanges(storedX,storedY);
                        player.king.editMoveCount(movecounts);
                        board.getBoard()[player.king.getY()][player.king.getX()] = player.king.getTag();
                        board.getBoard()[player.king.getY1()][player.king.getX1()] = storedTag;
                        movecount++;

                    }

                }
                double maxEval = -2000;
                int storedLocs =0;
                for(int j =0; j < evals.size();j++){
                    System.out.println(pieceTag.get(j) + " " + evals.get(j));
                    if(evals.get(j)>maxEval){
                        storedLocs = j;
                        maxEval = evals.get(j);
                        
                    }
                   
                    
                }
                if(player.getPlayerTurn()==board.getTurn()&&storedIndex.size()>0){
                    int storedIndexs = storedIndex.get(storedLocs);
                    char switchable = pieceTag.get(storedLocs).charAt(1);
                    switch(switchable){
                        case 'P':
                            player.pawns[storedIndex.get(storedLocs)].checkLegalMove();
                            if(player.pawns[storedIndex.get(storedLocs)].checkPossibleMoves(moveX.get(storedLocs),moveY.get(storedLocs),true)==1){
                                System.out.println(player.pawns[storedIndex.get(storedLocs)].getX() +  " Player " + player.pawns[storedIndex.get(storedLocs)].getY());
                            }
                            break;
                        case 'B':
                            player.bishops[storedIndex.get(storedLocs)].checkLegalMove();
                            if(player.bishops[storedIndex.get(storedLocs)].checkPossibleMoves(moveX.get(storedLocs),moveY.get(storedLocs),true)==1){
                                System.out.println(player.pawns[storedIndex.get(storedLocs)].getX() +  " Player " + player.pawns[storedIndex.get(storedLocs)].getY());
                            }
                            break;
                        case 'H':
                            player.knights[storedIndex.get(storedLocs)].checkLegalMove();
                            if(player.knights[storedIndex.get(storedLocs)].checkPossibleMoves(moveX.get(storedLocs),moveY.get(storedLocs),true)==1){
                                System.out.println(player.pawns[storedIndex.get(storedLocs)].getX() +  " Player " + player.pawns[storedIndex.get(storedLocs)].getY());
                            }
                            break;
                        case 'R':
                            player.rooks[storedIndex.get(storedLocs)].checkLegalMove();
                            if(player.rooks[storedIndex.get(storedLocs)].checkPossibleMoves(moveX.get(storedLocs),moveY.get(storedLocs),true)==1){
                                System.out.println(player.pawns[storedIndex.get(storedLocs)].getX() +  " Player " + player.pawns[storedIndex.get(storedLocs)].getY());
                            }
                            break;
                        case 'Q':
                            player.queen.checkLegalMove();
                            if(player.queen.checkPossibleMoves(moveX.get(storedLocs),moveY.get(storedLocs),true)==1){
                                System.out.println(player.pawns[storedIndex.get(storedLocs)].getX() +  " Player " + player.pawns[storedIndex.get(storedLocs)].getY());
                            }
                            break;
                        case 'K':
                            player.king.checkLegalMove();
                            if(player.king.checkPossibleMoves(moveX.get(storedLocs),moveY.get(storedLocs),true)==1){
                                System.out.println(player.pawns[storedIndex.get(storedLocs)].getX() +  " Player " + player.pawns[storedIndex.get(storedLocs)].getY());
                            }
                            break;

                    }

                    System.out.println(moveX.get(storedLocs) + " " + moveY.get(storedLocs));

                    board.switchTurn();
                    board.displayBoard();
                    System.out.println("movecount" + movecount);
                    System.out.println("player pawn x " + player.pawns[storedIndexs].getX() + player.pawns[storedIndexs].getY());
                }
            }
        }
        }
        else{
            System.out.println("its not your turn");
        }
    }
    public void setPlayers(Player player, Player enemy){
        this.player = player;
        this.enemy = enemy;
    }
   
            
}
