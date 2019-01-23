
//THIS IS AN UPDATED VERSION 
package chess2;


public class Bishop extends Piece {
    
    /**
     * This is the bishop object
     * @param boards common Board
     * @param x x coordinate
     * @param y y coordinate
     * @param id piece id
     * @param color color of piece
     */
     public Bishop(Board boards, int x, int y, int id, char color){
        setup(boards, x,y,id,color);//uses base constructor
        setTag(id,"B",color);//tag is just for display mainly, but also used for now, once we have graphics we can transition out
    }
     
    /**
     * Checks for all legal moves, handled in another method
     */
    public void checkLegalMove(){
        bishopMoves(false,tag.substring(0,1));//l here is 8 so it is not considered 
    }
}
