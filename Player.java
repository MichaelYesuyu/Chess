/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess2;

import java.util.ArrayList;

/**
 * Player is the class that we created for the ai 
 * A player is a collection of pieces that the ai is able to manipulate 
 * We start by giving the player access to the common board and a group of pieces 
 * There is a type to define whether an player is an ai or a human 
 */
public class Player {
    Board board; 
    Pawn[] pawns;
    Rook[] rooks;
    Bishop[] bishops;
    Knight[] knights;
    King king; 
    Queen queen;
    ArrayList<Integer> movesX = new ArrayList<>();
    ArrayList<Integer> movesY = new ArrayList<>();
    boolean playerTurn;
    boolean ai;
    /**
     * if you don't want to initialize everything use this constructor 
     */
    public Player(){
        
    }
    /**
     * Acts a bridge between chess2 and Player and where we link our globals to the Pieces in chess2
     * @param board the common board
     * @param pawns the pawns array that we want to use 
     * @param rooks the rooks array that we want to use 
     * @param knights the knights array that we want to use 
     * @param bishops the bishops array that we want to use 
     * @param queen the queen that we want to use 
     * @param king the king that we want to use 
     * @param playerTurn our turn, very important for check and ai because it allows us to distinguish between black and white 
     */
    public Player(Board board, Pawn[] pawns, Rook[] rooks,Knight[] knights,Bishop[] bishops,Queen queen, King king,boolean playerTurn) {
        this.board = board;
        this.pawns = pawns;
        this.rooks = rooks;
        this.bishops = bishops;
        this.knights = knights;
        this.king = king;
        this.queen = queen;
        this.playerTurn = playerTurn;
    }
    /**
     * Usually compared to the current turn to decide whether or not we can make a move 
     * @return returns what our turn is 
     * true means that our turn is white turn 
     */
    public boolean getPlayerTurn(){
        return playerTurn;
    }
    /**
     * To set whether or not we are an ai 
     */
    public void setAI(boolean AI ){
        this.ai = AI;
    }
    /**
     * Resets all of the temporary alives of the player so that we can handle collisions in ai 
     */
    public void setAlives(){
        for(int i =0; i  <8; i++){
            pawns[i].controlledOff = true;
            
        }
        for(int i =0; i < 2; i++){
            rooks[i].controlledOff = true;
            bishops[i].controlledOff = true;
            knights[i].controlledOff = true;
        }
        king.controlledOff = true;
        queen.controlledOff = true;
    }
    /**
     * Updates all of our legal moves and stores them in an array 
     */
    public void updateLegals(){
        for(int i =0; i < 8; i++){
            pawns[i].checkLegalMove();
            for(int j =0; j< pawns[i].possiblemovesX.size();j++){
               movesX.add(pawns[i].getMovesX().get(j));
               movesY.add(pawns[i].getMovesY().get(j));
            }
        }
        for(int i =0;i < 2; i++){
            knights[i].checkLegalMove();
            bishops[i].checkLegalMove();
            rooks[i].checkLegalMove();
            for(int j =0; j< rooks[i].possiblemovesX.size();j++){
               movesX.add(rooks[i].getMovesX().get(j));
               movesY.add(rooks[i].getMovesY().get(j));
            }
            for(int j =0; j< knights[i].possiblemovesX.size();j++){
               movesX.add(knights[i].getMovesX().get(j));
               movesY.add(knights[i].getMovesY().get(j));
            }
            for(int j =0; j<bishops[i].possiblemovesX.size();j++){
               movesX.add(bishops[i].getMovesX().get(j));
               movesY.add(bishops[i].getMovesY().get(j));
            }
        }
        queen.checkLegalMove();
        for(int j =0; j< queen.possiblemovesX.size();j++){
               movesX.add(queen.getMovesX().get(j));
               movesY.add(queen.getMovesY().get(j));
            }
        king.checkLegalMove();
        for(int j =0; j<king.possiblemovesX.size();j++){
               movesX.add(king.getMovesX().get(j));
               movesY.add(king.getMovesY().get(j));
        }
    }
    /**
     * Deletes all of the legal move for each piece in the player and resets the Move arrays 
     */
    public void deleteMoves(){
        for(int i =0; i < 8; i++){
            pawns[i].deleteMoves();
        }
        for(int i =0;i < 2; i++){
            knights[i].deleteMoves();
            bishops[i].deleteMoves();
            rooks[i].deleteMoves();
        }
        queen.deleteMoves();
        king.deleteMoves();
        for(int i =0; i < movesX.size();i++){
            movesX.remove(i);
            movesY.remove(i);
        }
    }
    
}