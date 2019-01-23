
//THIS IS AN UPDATED VERSION 
package chess2;

import java.util.ArrayList;
/**
 * The purpose of Board is to create an internal array that all movemeents can be managed on and every class can access and use 
 Board allows us to test moves and can be refreshed with the display Board any time it becoems corrupted or we want to refresh it to run other 
 tests 
 Board also has native methods that piece and other classes can use and we are working on adding a moveRecorder here so that we can have one 
 moveRecorder class instead of one for every piece which creates complication 
 */

public class Board {
    String[][] board = new String[8][8];//the Board is stored as an array, this array can be flipped, and is generated in chess, the weird array in the beginning
    //side note about these, coordinates will be stored from 
    ArrayList<int[]> whiteMoveLocations = new ArrayList<>();//can store the x and y coordinates of every white piece and its id
    ArrayList<int[]> blackMoveLocations = new ArrayList<>();//stores the x and y coordinates for every black piece and its id
    ArrayList<int[]> whiteLocations = new ArrayList<int[]>();//can store the x and y coordinates of every white piece and its id
    ArrayList<int[]> blackLocations = new ArrayList<int[]>();//stores the x and y coordinates for every black piece and its id
    ArrayList<String> moveRecorder = new ArrayList<String>();
    ArrayList<String[][]> allMoves = new ArrayList<String[][]>();
    int whitecount = 0;//for ai later maybe 
    int blackcount = 0; 
    int totalMovecount =0;
    boolean whiteking = true;//used to check if the king is alive
    boolean blackking = true;//used to check if the king is alive
    Player white;
    Player black; 
    boolean turn = true;
    boolean ready = false;
    String checkmessage = "";
    /**
     * 
     * @param boards the board array, or the public board is initiated using another array, this allows for creation from files 
     */
    public Board(String[][] boards){//sets up the boards based on an array in chess, the one with alread defined values
        for(int i =0; i < 8; i++){
            for(int j=0; j < 8; j++){
                board[i][j] = boards[i][j];
            }
        }
        
    }
    public ArrayList<String[][]> getAllMoves(){
        return allMoves;
    }
    public void addMoves(String[][] board){
        String[][] allStuff = new String[8][8];
                        
        for(int j=0; j < 8; j++){
            for(int k =0; k < 8;k++){
                allStuff[j][k] = board[j][k];
            }
        }
        allMoves.add(allStuff);
    }
    public void removeMove(){
        
        allMoves.remove(allMoves.size()-1);
    }
    public void undoMove(){
        if(allMoves.size()>1){
        System.out.println("move1 size" + allMoves.size());
        removeMove();
        System.out.println("move2 " + allMoves.size());
        updateBoard(allMoves.get(allMoves.size()-1));
        
        int blackQueenCount = 0;
        for(int i =0; i < 8; i ++){
            for(int j =0; j < 8; j++){
                for(int k =0; k < 8; k++){
                    if(board[i][j].substring(2,4).equals(Integer.toString(white.pawns[k].getID()))){
                        white.pawns[k].setAlive(true);
                        white.pawns[k].moveChanges(j,i);
                        white.pawns[k].editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    if(board[i][j].substring(2,4).equals(Integer.toString(black.pawns[k].getID()))){
                        black.pawns[k].setAlive(true);
                        black.pawns[k].moveChanges(j,i);
                        black.pawns[k].editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                }
               for(int k =0; k < 2; k++){
                    if(board[i][j].substring(2,4).equals(Integer.toString(white.bishops[k].getID()))){
                        white.bishops[k].setAlive(true);
                        white.bishops[k].moveChanges(j,i);
                        white.bishops[k].editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    if(board[i][j].substring(2,4).equals(Integer.toString(black.bishops[k].getID()))){
                        black.bishops[k].setAlive(true);
                        black.bishops[k].moveChanges(j,i);
                        black.bishops[k].editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    if(board[i][j].substring(2,4).equals(Integer.toString(white.knights[k].getID()))){
                        white.knights[k].setAlive(true);
                        white.knights[k].moveChanges(j,i);
                        white.knights[k].editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    if(board[i][j].substring(2,4).equals(Integer.toString(black.knights[k].getID()))){
                        black.knights[k].setAlive(true);
                        black.knights[k].moveChanges(j,i);
                        black.knights[k].editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    if(board[i][j].substring(2,4).equals(Integer.toString(white.rooks[k].getID()))){
                        white.rooks[k].setAlive(true);
                        white.rooks[k].moveChanges(j,i);
                        white.rooks[k].editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    if(board[i][j].substring(2,4).equals(Integer.toString(black.rooks[k].getID()))){
                        black.rooks[k].setAlive(true);
                        black.rooks[k].moveChanges(j,i);
                        black.rooks[k].editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    
                }
               if(board[i][j].substring(2,4).equals(Integer.toString(white.queen.getID()))){
                        white.queen.setAlive(true);
                        white.queen.moveChanges(j,i);
                        white.queen.editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    if(board[i][j].substring(2,4).equals(Integer.toString(black.queen.getID()))){
                        black.queen.setAlive(true);
                        black.queen.moveChanges(j,i);
                        black.queen.editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    if(board[i][j].substring(2,4).equals(Integer.toString(white.king.getID()))){
                        white.king.setAlive(true);
                        white.king.moveChanges(j,i);
                        white.king.editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
                    if(board[i][j].substring(2,4).equals(Integer.toString(black.king.getID()))){
                        black.king.setAlive(true);
                        black.king.moveChanges(j,i);
                        black.king.editMoveCount(Integer.parseInt(board[i][j].substring(4)));
                        
                    }
               
                
            }
        }
        switchTurn();
        }
    }
    public void setReady(boolean ready){
        this.ready = ready;
    }
    public boolean isReady(){
        return ready;
    }
    public void setCheckMessage(String checkmessage){
        this.checkmessage = checkmessage;
    }
    public String getCheckMessage(){
        return this.checkmessage;
    }
    /**
     * Here we add all of the last moves that were made to the move recorder array list 
     * @param move gets the tag of the last move that was made 
     */
    public void moveRecorderAdd(String move){
        moveRecorder.add(move);
    }
    /**
     * 
     * @return returns moveRecorder(the array list storing the tags of all the last moves made)
     */
    public ArrayList<String> getMoveRecorder(){//updated wiht a move recoder 
        return this.moveRecorder;
    }
    public void setWhite(Player player){
        this.white = player;
    }
    public void setBlack(Player player){
        this.black = player;
    }
    /**
     * Checks whether or not a specific player is in check, this way we can allow for checks in ai 
     * Delete the legal moves, and then update them(so no duplicates for processing efficiency) 
     * If our players king location is in the moves of the enemy, we are in check 
     * @param turn determines which player we are considering 
     * @return whether or not the player we are considering is in check 
     */
    public boolean isInSpecificCheck(boolean turn){
        black.deleteMoves();//clear our moves 
        white.deleteMoves();
        black.updateLegals();
        for(int i =0; i < black.movesX.size();i++){
            if(turn&&white.king.getX()==black.movesX.get(i)&&white.king.getY()==black.movesY.get(i)){
                black.deleteMoves();
                System.out.println("is in check  because yeah yeah white checkin black ooh ARGH WORK");
                return true;
            }
        }
        black.deleteMoves();
        
        white.updateLegals();
        for(int i =0; i < white.movesX.size();i++){
            if(!turn&&black.king.getX()==white.movesX.get(i)&&black.king.getY()==white.movesY.get(i)){
                white.deleteMoves();
                System.out.println("is in check because yeah yeah  black checkin white ooh lil check plz work why no work");
                return true;
            }
        }
        white.deleteMoves();
        return false;
    }
    /**
     * Same as seperate check but makes sure neither player is in check 
     * The ai cannot move if there is a check(prevents it from taking the king) if it checkmates  
     * @return whether or not there is a check detected, uses same method as above 
     */
    public boolean isInCheck(){
        black.deleteMoves();//clear our moves 
        white.deleteMoves();
        black.updateLegals();
        for(int i =0; i < black.movesX.size();i++){
            if(white.king.getX()==black.movesX.get(i)&&white.king.getY()==black.movesY.get(i)){
                black.deleteMoves();
                System.out.println("is in check  because yeah yeah white checkin black ooh ARGH WORK");
                return true;
            }
        }
        black.deleteMoves();
        
        white.updateLegals();
        for(int i =0; i < white.movesX.size();i++){
            if(black.king.getX()==white.movesX.get(i)&&black.king.getY()==white.movesY.get(i)){
                white.deleteMoves();
                System.out.println("is in check because yeah yeah  black checkin white ooh lil check plz work why no work");
                return true;
            }
        }
        white.deleteMoves();
        return false;
    }
    /**
     * any changes made to the array in Board are reflected here by updating the Board to the array that is in chess 
     * @param boards This is the common Board
     */
    public void updateBoard(String[][] boards){
        for(int i =0; i < 8; i++){
            for(int j=0; j < 8; j++){
                board[i][j] = boards[i][j];   
            }
        }
    }
    /**
     * sets the turn to a specific value 
     * When turn = true; it is whites turn;
     * When turn = false; it is blacks turn;
     * @param turn boolean that the turn will be set to
     */
    public void setTurn(boolean turn){
        this.turn = turn;
    }
    /**
     * Flips the turn 
     * If it is blacks turn, then it is whites turn 
     * If it is whites turn ,then it is blacks turn
     */
    public void switchTurn(){
        turn = !turn;
    }
    /**
     * true = white
     * false = black
     * @return the current state of the turn 
     */
    public boolean getTurn(){
        return this.turn;
    }
    /**
     * The totalMovecount is used to see how many turns have passed and mainly for moves such as castling and enpassant that need to know what the last 
     * move made was etc.
     * @return the totalmovecount 
     */
    public int getTotalMoveCount(){
        return totalMovecount;
    }
    /**
     * increments the total movecount by amount x
     * @param x how much we want to increment the total movecount by  
     */
    public void setTotalMoveCount(int x){
        totalMovecount+=x;
    }
    //returns the public Board so that other classes can acess it 
    public String[][] getBoard(){//used to access the Board in here so if it needs to be updated or used it can be 
        return this.board;
    }
    
    /**
     * Prints out the Board in the output box, this is for testing purposes
     */
    public void displayBoard(){ 
        for(int i =0; i < 8;i++){
            String n = "";
            for(int j =0; j < 8; j++){
                n = n + " " + board[i][j];
            }
            System.out.println(n);
        }
    }
    
    /**
     * Checks to see if there is a black piece in that coordinate
     * @param x x coordinate
     * @param y y coordinate
     * @return returns true if there is a black piece in that square, returns false otherwise
     */
    public boolean isInBlack(int x, int y){
        
        if(board[y][x].charAt(0)=='b'){
            return true;
        }return false;
    }
    
    /**
     * Checks to see if there is a white piece in that coordinate
     * @param x x coordinate
     * @param y y coordinate
     * @return returns true if there is a white piece in that square, returns false otherwise
     */
    public boolean isInWhite(int x, int y){
       
        if(board[y][x].charAt(0)=='w'){//same as is in black but for white 
            return true;
        }return false;
    }
    
    /**
     * checks the amount of white pieces, actually it doesn't now but will in future, and adds the values of each white piece to the ArrayList
     */
    public void getWhiteCount(){
        for(int i =0; i < 8; i++){
            for(int j=0; j<8;j++){
                
            }
        }
    }
    
    /**
     * checks the amount of black pieces, actually it doesn't now but will in future, and adds the values of each black piece to the ArrayList
     */
    public void getBlackCount(){
        for(int i =0; i < 8; i++){
            for(int j=0; j<8;j++){
                
            }
        }
    }

   
    }
