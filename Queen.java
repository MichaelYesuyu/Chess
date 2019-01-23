
package chess2;

//THIS IS AN UPDATED VERSION 
public class Queen extends Piece{
    
    /**
     * This is the queen object
     * @param boards common Board
     * @param x x coordinate
     * @param y y coordinate
     * @param id piece id
     * @param color color of piece
     */
    public Queen(Board boards, int x, int y, int id, char color){
        setup(boards, x,y,id,color);//uses base constructor
        setTag(id,"Q",color);//tag is just for display mainly, but also used for now, once we have graphics we can transition out
    }
    public void checkLegalMove(){
        queenMoves(false,tag.substring(0,1));
    }

}



