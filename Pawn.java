
//THIS IS AN UPDATED VERSION 
package chess2;

public class Pawn extends Piece{
    String tagRight;
    String tagLeft;
    
    /**
     * This is the pawn object
     * @param boards common Board
     * @param x x coordinate
     * @param y y coordinate
     * @param id piece id
     * @param color color of piece
     */
    public Pawn(Board boards, int x, int y, int id, char color){
        setup(boards, x,y,id,color);//uses base constructor
        setTag(id,"P",color);//tag is just for display mainly, but also used for now, once we have graphics we can transition out
    }    
    
    /**
     * Checks to see if it is a legal move, handled promotion as well as en passant
     */
    public void checkLegalMove(){ 
        switch (tag.charAt(1)) {
            case 'Q':
                queenMoves(false,tag.substring(0,1));
                break;
            case 'H':
                knightMoves(tag.substring(0,1));
                break;
            case 'B':
                bishopMoves(false,tag.substring(0,1));
                break;
            case 'R':
                rookMoves(false,tag.substring(0,1));
                break;
            default:
                pawnMoves();
                String currentMove = "";
                if(totalMoveCount > 0&&moveRecorder.size()>0){
                    currentMove = moveRecorder.get(totalMoveCount - 1); //uses arrayList to get current move (move made before this)
                }
                if(tag.charAt(0)=='w'){
                    tagRight = returnPiece(x-1,y);
                    
                    tagLeft = returnPiece(x+1,y);
                    
                    
                    //enpassant to the right
                    if(checkbounds(getX()-1,getY()-1)&&getY()==3&&!boards.isInWhite(getX()-1,getY()-1)&&!boards.isInBlack(getX()-1, getY()-1)){
                        {
                            if(tagRight.charAt(1)=='P'&&Integer.parseInt(tagRight.substring(4))==1&&getLastMoveTag().equals(tagRight.substring(0,4))){
                                addPossibleMoves(getX()-1,getY()-1,"enpassant");
                                // Chess2.boards[getX()+1][getY()] = "00000";
                            }
                        }
                        
                    }
                    //enpassant to the left, not completed
                    if(checkbounds(getX()+1,getY()-1)&&getY()==3&&!boards.isInWhite(getX()+1,getY()-1)&&!boards.isInBlack(getX()+1, getY()-1)){
                        if(tagLeft.charAt(1)=='P'&&Integer.parseInt(tagLeft.substring(4))==1&&getLastMoveTag().equals(tagLeft.substring(0,4))){
                            addPossibleMoves(getX()+1,getY()-1,"enpassant");
                        }
                    }
                }else{
                    tagRight = returnPiece(x-1,y);
                    tagLeft = returnPiece(x+1,y);
                    //enpassant to the right
                    if(checkbounds(getX()-1,getY()+1)&&getY()==4&&!boards.isInBlack(getX()-1,getY()+1)&&!boards.isInWhite(getX()-1, getY()+1)){
                        {
                            if(tagRight.charAt(1)=='P'&&Integer.parseInt(tagRight.substring(4))==1&&getLastMoveTag().equals(tagRight.substring(0,4))){
                                
                                
                                addPossibleMoves(getX()-1,getY()+1,"enpassant");
                                // Chess2.boards[getX()+1][getY()] = "00000";
                            }
                        }
                        
                    }
                    //enpassant to the left, not completed
                    if(checkbounds(getX()+1,getY()-1)&&getY()==4&&!boards.isInWhite(getX()+1,getY()+1)&&!boards.isInBlack(getX()+1, getY()+1)){
                        if(tagLeft.charAt(1)=='P'&&Integer.parseInt(tagLeft.substring(4))==1&&getLastMoveTag().equals(tagLeft.substring(0,4))){
                            addPossibleMoves(getX()+1,getY()+1,"enpassant");
                        }
                    }
                }   break; 
        }
    }
}
