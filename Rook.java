
//THIS IS AN UPDATED VERSION 
package chess2;

public class Rook extends Piece {
     
    /**
     * This is the rook object
     * @param boards uses the common Board
     * @param x x coordinate 
     * @param y y coordinate
     * @param id piece id
     * @param color color of the piece
     */
    public Rook(Board boards, int x, int y, int id, char color){
        setup(boards, x,y,id,color);//uses base constructor
        setTag(id,"R",color);//tag is just for display mainly, but also used for now, once we have graphics we can transition out
    }
    
    /**
     * Checks all the legal moves, processed in another method
     */
    public void checkLegalMove(){
        rookMoves(false,tag.substring(0,1));//l here is 8 so that it is not considered 
    }
}



