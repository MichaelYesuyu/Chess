/**
Chess Interface Version 2.12
*What we have done so far 
    *All rules are working including basic moves, check, checkmate, en passant and castling
    *We have not included threefold repetition because it requires excessive computational power and will significantly slow down program
    *This is because threefold repetition is the same position happening 3 times, and can happen anytime (move 10, then 30, then 50)
*Future Version Plans 
    *will start adding versions, every major update will be a + 0.1, bug fix + 0.01 and once we finish the interface with out bugs it should become 1.0
    *Basic alpha beta pruning is 2.0
    *Finished version with filtering should be 3.0
*/
/**
 * Tentative Schedule for before the break 
    *Make so opening can play more than 2 moves 
    * Make so end game isn't scuffed
    * Add another layer of move making 
    * Improve to alpha beta pruning algo 
    * Make second and third layers 
 */
/**
 * Bugs to fix EVERY TIME A BUG OCCURS ADD IT TO THE LIST AND UPDATE DRIVE WITH THE NEW LIST
 *  All the bugs have been fixed as of now, other bugs may pop up though
 */
/**
 * Things to do before the version can be updated to 1.0 and we can begin the engine 
 * Fix all the bugs 
 * Create new methods that the engine can use to completely analyze the board and put a value on a piece and a position 
 * Finish the U.I
 */
package chess2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Chess2 extends JFrame implements Runnable, MouseListener, MouseMotionListener, KeyListener{
    Images whiteRook = new Images("whiterook.png");//creates new Images, all of whitch are displayed in gameUpdate
    Images whiteKnight = new Images("whiteknight.png");
    Images whiteBishop = new Images("whitebishop.png");
    Images whiteQueen = new Images("whitequeen.png");
    Images whiteKing = new Images("whiteking.png");
    Images whitePawn = new Images("whitepawn.png");
    Images blackRook = new Images("blackrook.png");
    Images blackKnight = new Images("blackknight.png");
    Images blackBishop= new Images("blackbishop.png");
    Images blackQueen = new Images("blackqueen.png");
    Images blackKing= new Images("blackking.png");
    Images blackPawn = new Images("blackpawn.png");
    Images undo = new Images("undo.png");
    Images slide1 = new Images("slide1.png");
    Images slide2 = new Images("slide2.png");
    Images slide3 = new Images("slide3.png");
    Images rightArrow = new Images("rightarrow.png");
    Images leftArrow = new Images("leftarrow.png");
    Images home = new Images("home.png");
    String[][] boards ={//an 8 by 8 array representing the Board the Board is meant to be transfered between the Board class and this class so that the ai can keep track of the Board, setup in Board
            {"bR260","bH270","bB280","bQ290","bK300","bB310","bH320","bR330"},//each piece has a tag, the first char is the color, second is type, and third is id  
            {"bP340","bP350","bP360","bP370","bP380","bP390","bP400","bP410"},//tags are used to calculate the legal moves available, and differentiate between pieces of the same type
            {"00000", "00000","00000","00000", "00000","00000","00000", "00000"},//these all represent the blank squares 
            {"00000", "00000","00000","00000", "00000","00000","00000", "00000"},
            {"00000", "00000","00000","00000", "00000","00000","00000", "00000"},
            {"00000", "00000","00000","00000", "00000","00000","00000", "00000"},
            {"wP180","wP190","wP200","wP210","wP220","wP230","wP240","wP250"},//we added piece id becasue a pawn can move up so every piece has an id but not a set type, 
            {"wR100","wH110","wB120","wQ130","wK140","wB150","wH160","wR170"},//the first char is the color, and the second is the type the last two digits are the piece id
    
            };
    boolean turn = true; //whites turn = true, blacks turn = false
    Board board = new Board(boards);//creates a new Board with this array, in order to allow for flipage in the future Board can be created from any array, however Board makes assumptions based on the array being chess focused 
    Pawn[] pawnsWhite = new Pawn[8];//creates pieces, will be further setup in the constructor, in array form so the code is less redundant and 
    Pawn[] pawnsBlack = new Pawn[8];//we do not have to account for every single piece this way, rather they can be looped through an array 
    Rook[] rooksWhite = new Rook[2];
    Rook[] rooksBlack = new Rook[2];
    Knight[] knightsWhite = new Knight[2];
    Knight[] knightsBlack = new Knight[2];
    Bishop[] bishopsWhite = new Bishop[2];
    Bishop[] bishopsBlack = new Bishop[2];
    Queen queenBlack = new Queen(board,3,0,Integer.parseInt(boards[0][3].substring(2,4)),'b');//no need to setup in constructor as it is not an array
    Queen queenWhite = new Queen(board,3,7,Integer.parseInt(boards[7][3].substring(2,4)),'w');
    King kingBlack = new King(board,4,0,Integer.parseInt(boards[0][4].substring(2,4)),'b');
    King kingWhite = new King(board,4,7,Integer.parseInt(boards[7][4].substring(2,4)),'w');
    int saveK = 0;//when the mouse is pressed, it stores the specific piece that was pressed because some pieces are stored in array, this way when released we can immediately know which piece, was selected, see mousePressed() and mouseReleased() for more details
    String type  = "";//used to store information about the piece selected in mousepressed so that knowing what moves to check is easier 
    Graphics2D g2d;//creates the graphics to which every thing will be setup  
    BufferedImage backbuffer;//graphisc will be drawn on this so there is no buffering and flickering 
    Thread gameloop;//the game thread, used for fps control and the while running loop which is where repaint is managed
    boolean setFill = false;//used for determining when a square should be drawn as a black square and a white square in gameUpdate()
    int gamestate = 0;
    boolean specialOn = false;
    int totalMovecount = 0;
    ArrayList<Integer> blackMovesX= new ArrayList<>();// these four arrayLists below store the current possible moves of both white and black
    ArrayList<Integer> whiteMovesX = new ArrayList<>();
    ArrayList<Integer> blackMovesY= new ArrayList<>();
    ArrayList<Integer> whiteMovesY = new ArrayList<>();
    ArrayList<String> moveRecorder = new ArrayList<>();
    ArrayList<Integer> blackOtherMovesX= new ArrayList<>();//these store the potential moves based on a hypothetical change to the Board 
    ArrayList<Integer> whiteOtherMovesX = new ArrayList<>();
    ArrayList<Integer> blackOtherMovesY= new ArrayList<>();
    ArrayList<Integer> whiteOtherMovesY = new ArrayList<>();
    int whitecount=0;//counts the number of moves that white can make, if whiteocunt =0, then look for checkmate or stalemate
    int blackcount =0;
    int kingwhitex = 0; //stores the potential king x and y if you move the king to a possible location that way check can be looked for in that location
    int kingblackx = 0; 
    int kingwhitey =0; 
    int kingblacky = 0;
    ArrayList<String> checkingPieces = new ArrayList<>();//potential add to look for pieces that are checking the king 
    String checkmessage="";//used  to display the state of the game 
    int displayBoard = 0; 
    boolean handle = true;//determines whether or not there has been a checkmate 
    AI ai;
    Player white;
    Player black;
    boolean updateState = true;
    boolean displayLoadingScreen = false;
    boolean wasPromoted = false;
    String type1Tag = "human";
    String type2Tag = "human";
    boolean started = false;
    public Chess2(){    
        super("Chess Game");//tittle is chess game
        addMouseListener(this);//the mouselisteners, listens for any mouse events 
        addMouseMotionListener(this);
        addKeyListener(this);
        for(int i =0; i< 8; i++){//sets up the pawns 
           pawnsWhite[i] = new Pawn(board,i,6,Integer.parseInt(boards[6][i].substring(2,4)),'w');//creates the white pawns, see details on constructor in Piece.setup()
           pawnsBlack[i] = new Pawn(board,i,1,Integer.parseInt(boards[1][i].substring(2,4)),'b');//creates black pawns
           pawnsWhite[i].checkLegalMove();//gets all the possible legal moves 
           
        }   
        for(int i =0; i < 2; i++){//setting up the other array based pieces, rook, knight, and bishop
            rooksWhite[i] = new Rook(board,i+i*6,7,Integer.parseInt(boards[7][i+i*6].substring(2,4)),'w');
            rooksBlack[i] = new Rook(board,i+i*6,0,Integer.parseInt(boards[0][i+i*6].substring(2,4)),'b');
            knightsWhite[i] = new Knight(board,1+i*5,7,Integer.parseInt(boards[7][1+i*5].substring(2,4)),'w');
            knightsBlack[i] = new Knight(board,1+i*5,0,Integer.parseInt(boards[0][1+i*5].substring(2,4)),'b');
            bishopsWhite[i] = new Bishop(board,2+i*3,7,Integer.parseInt(boards[7][2+i*3].substring(2,4)),'w');
            bishopsBlack[i] = new Bishop(board,2+i*3,0,Integer.parseInt(boards[0][2+i*3].substring(2,4)),'b');
         }
        
        setSize(800,800);//sets up the window 
        setVisible(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backbuffer = new BufferedImage(800,800,BufferedImage.TYPE_INT_RGB);//creates a buffered image which graphics will be drawn on 
        g2d= backbuffer.createGraphics();//more graphics setup 
        gameloop = new Thread(this);//starts the game thread 
        gameloop.start();
        white = new Player(board,pawnsWhite, rooksWhite, knightsWhite, bishopsWhite, queenWhite,kingWhite,true);
        black = new Player(board,pawnsBlack, rooksBlack, knightsBlack, bishopsBlack, queenBlack,kingBlack,false);
        board.setBlack(black);
        board.setWhite(white);
        board.addMoves(board.getBoard());
        board.setReady(true);
        ai = new AI(board);
    }
    
    public void run(){//the while running loop goes here 
         Thread current = Thread.currentThread();
        while(current==gameloop){
            try{
                gameloop.sleep(60);//around 60fps, fact check
            }catch(InterruptedException e){
                e.printStackTrace();//if for somereason there is an error, print where it occured 
            }
            displayBoard++;
            if(displayBoard>50){//delays the load in order to avoid buffering of the Images as they load to the screen, it takes 12 loops for buffering to stop
                setVisible(true);
        
            }
            if(updateState){
                setBoard(board.getBoard());
            }
            gameUpdate();//draws the Board graphics
        
        }
    }
    /**
     * This is where all the graphics are managed 
 No matter what draw the white button and a home button
The methods for restarting haven't been done yet     
 If the game state is 0
Draw the home screen 
If the gamestate is 1
Draw the directions page
If a gamestate is 2 or 3 or 4 draw the regular Board        
 This is the class where the graphical Board is redrawn
 First the blank squares are drawn 
 Next, based on the value for every element in the Board array an image is drawn to represent all the pieces 
 If the gamestate is 5
Open the settings page  
     */
    public void gameUpdate(){//where the Board is drawn, draws squares that represent the Board squares, and it takes the boards array, and based off the tags draws the appropriate piece
        //THIS IS DRAWN NO MATTER WHAT 
        g2d.setColor(Color.WHITE);//sets the color to white because that is the first color that we need 
        g2d.fillRect(0, 0,800,800);
        if(gamestate==0){
            if(!started){
            g2d.setColor(Color.BLACK);
            g2d.fill(new Rectangle(200,400,100,100));
            g2d.fill(new Rectangle(400,400,100,100));
            
            g2d.setColor(Color.WHITE);
            g2d.drawString("Start",440,450);
            g2d.drawString("Directions",220,450);
            }
            else{
                g2d.setColor(Color.BLACK);
            g2d.fill(new Rectangle(200,400,100,100));
            g2d.fill(new Rectangle(400,400,100,100));
            
            g2d.setColor(Color.WHITE);
            g2d.drawString("Resume",430,450);
            g2d.drawString("Directions",220,450);
            }
        }
        if(gamestate==5){
            g2d.setColor(Color.BLACK);
            g2d.fill(new Rectangle(499,399,102,102));
            g2d.setColor(Color.WHITE);
            g2d.fill(new Rectangle(500,400,100,100));
            g2d.setColor(Color.BLACK);
            
            g2d.fill(new Rectangle(200,400,100,100));
            g2d.setColor(Color.BLACK);
            g2d.drawString(type2Tag ,540,450); 
            g2d.fill(new Rectangle(350,550,100,100));
            g2d.setColor(Color.WHITE);
            g2d.drawString(type1Tag,240,450);
            g2d.drawString("Start game",370,600);
        }
        if(gamestate >=2&&gamestate<=4){
            g2d.drawImage(undo.getImage(),750,50,this);
            for(int i =7; i >= 0; i--){//a two by two loop, starts from the bottom at 7,7 for formating purposes

                for(int j =7; j >= 0; j--){

                    if(setFill){//set fill describes what color the Board square should be drawn as, if it is true then draw a brown squaree
                        setFill = false;
                        g2d.setColor(new Color(200,160,120));//a brown color from online
                        Rectangle rect = new Rectangle(100+j*75, 100+i*75, 75, 75);//starting location is 100,100 every new square is drawn at increments of 75
                        g2d.fill(rect);

                    }else{//lse draw a white square
                        setFill = true;
                        g2d.setColor(Color.WHITE);
                        Rectangle rect = new Rectangle(100+j*75, 100+i*75, 75, 75);//j is ALWAYS the x, and i is ALWAYS the y(it gets confusting some times) 
                        g2d.fill(rect);

                    }
                    //these Images are all drawn after the drawing of the squares so that the squares don't overlapp the
                    if(boards[i][j].substring(0,2).equals("wP")){//based on the tag, if the tag, every location on the Board has a tag 
                            g2d.drawImage(whitePawn.getImage(),100+75*j,100+i*75,this);//draws the image that corresponds to the tag

                    }
                    if(boards[i][j].substring(0,2).equals("wR")){
                            g2d.drawImage(whiteRook.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("wH")){
                            g2d.drawImage(whiteKnight.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("wB")){
                            g2d.drawImage(whiteBishop.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("wQ")){
                            g2d.drawImage(whiteQueen.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("wK")){
                            g2d.drawImage(whiteKing.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("bR")){
                            g2d.drawImage(blackRook.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("bH")){
                            g2d.drawImage(blackKnight.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("bB")){
                            g2d.drawImage(blackBishop.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("bQ")){
                            g2d.drawImage(blackQueen.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("bK")){
                            g2d.drawImage(blackKing.getImage(),100+75*j,100+i*75,this);

                    }
                    if(boards[i][j].substring(0,2).equals("bP")){
                            g2d.drawImage(blackPawn.getImage(),100+75*j,100+i*75,this);

                    }

                }
                if(setFill){setFill=false;}//switches the fill color for every y value change(change in i) so that the chess Board is accuratley represented
                    else{setFill=true;}
                

            }
            for(int i =0; i < 9; i++){//draws the lines around the Board 
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5.0f));//sets the stroke width to five for more defined lines 
                g2d.drawLine(100,100+i*75, 700, 100+i*75);
                g2d.drawLine(100,100,100,700);
                g2d.drawLine(100+i*75,100,100+i*75,700);
            }
            Font stringFont = new Font( "SansSerif", Font.PLAIN, 18 );//we got this method from stack exchange in a post about changing fonts 
            g2d.setFont(stringFont);

            g2d.drawString("The checkmessage is: " + checkmessage,200,780);
        }
        if(gamestate ==3){//if the state is 3( The state for a passed white pawn) then draw a queen, rook,knight and bishop to the screen
                boolean secfill = !setFill;//used to switch the color of the squares for these Images without messing with the normal boards setFill 
                for(int j =0; j < 4; j++){
                    if(!secfill){//sec fill describes what color the Board square should be drawn as, if it is true then draw a brown squaree
                        secfill = true;
                        g2d.setColor(new Color(200,160,120));//a brown color from online
                        Rectangle rect = new Rectangle(100+(j+2)*75, 25, 75, 75);//starting location is 100,100 every new square is drawn at increments of 75
                        g2d.fill(rect);
                    
                    }else{//else draw a white square
                        secfill = false;
                        g2d.setColor(Color.WHITE);
                        Rectangle rect = new Rectangle(100+(j+2)*75, 25, 75, 75);//j is ALWAYS the x, and i is ALWAYS the y(it gets confusting some times) 
                        g2d.fill(rect);
                   
                    }
                    
                }
                g2d.drawImage(whiteQueen.getImage(),100+75*2,28,this);//starting above the second square start drawing the Board 
                g2d.drawImage(whiteBishop.getImage(),100+75*3,28,this);
                g2d.drawImage(whiteKnight.getImage(),100+75*4,28,this);
                g2d.drawImage(whiteRook.getImage(),100+75*5,28,this);
                
        }
        if(gamestate ==4){//same as state 3 but for a black passed pawn 
                boolean secfill = !setFill;
                for(int j =0; j < 4; j++){
                    if(!secfill){//set fill describes what color the Board square should be drawn as, if it is true then draw a brown squaree
                        secfill = true;
                        g2d.setColor(new Color(200,160,120));//a brown color from online
                        Rectangle rect = new Rectangle(100+(j+2)*75, 700, 75, 75);//starting location is 100,100 every new square is drawn at increments of 75
                        g2d.fill(rect);
                    
                    }else{//lse draw a white square
                        secfill = false;
                        g2d.setColor(Color.WHITE);
                        Rectangle rect = new Rectangle(100+(j+2)*75, 700, 75, 75);//j is ALWAYS the x, and i is ALWAYS the y(it gets confusting some times) 
                        g2d.fill(rect);
                   
                    }
                    
                }
                g2d.drawImage(blackQueen.getImage(),100+75*2,700,this);
                g2d.drawImage(blackBishop.getImage(),100+75*3,700,this);
                g2d.drawImage(blackKnight.getImage(),100+75*4,700,this);
                g2d.drawImage(blackRook.getImage(),100+75*5,700,this);
                
        }
        if(gamestate ==6){
            g2d.drawImage(slide1.getImage(),20,50,this);
        }
        if(gamestate==7){
            g2d.drawImage(slide2.getImage(),20,50,this);
        }
        if(gamestate==8){
            g2d.drawImage(slide3.getImage(),300,300,this);
        }
        if(gamestate>=6){
             
            g2d.drawImage(rightArrow.getImage(),740,400,this);
            g2d.drawImage(leftArrow.getImage(),-5,400,this);
        }
        if(gamestate!=0){
            g2d.drawImage(home.getImage(),50,50,this);
        }
        repaint();//calls the redraw of the buffered image that the graphics are drawn on so that it is constantly updated
    }
    /**
     * 
     * @param g a graphics that we can use to draw our image to 
     * In here  the buffered image which contains all of our graphics is drawn 
     */
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(backbuffer,0,0,this);
        
    }
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}
    /**
     * here we move handle any ai moves if we can make a move
     * the if statement handles a bug where the board wasn't updating 
     * @param e the key event
     */
    public void keyReleased(KeyEvent e) {
        
        if(e.getKeyChar() == 'e'){
            resetBoard();
        }else{
        try{
        if(ai.isActive){
            ai.smartMove();
            if(ai.player.getPlayerTurn()&&ai.allMoves().size()==1){
                setBoard(board.getBoard());
            }
            if(wasPromoted){
                setBoard(board.getBoard());
                wasPromoted = false;
            }
        }
        }catch(NullPointerException e2){}
        
        }
    }
    public void mouseDragged(MouseEvent e ){}
    public void mouseMoved(MouseEvent e){}
     public void mouseClicked(MouseEvent e){}//added so that there is no exception
    public void mouseExited(MouseEvent e){}//added so that there is no exception
    public void mouseEntered(MouseEvent e){}//added so that there is no exception
    /**
     * This is used to get user input and convert it to move coordinates that can be used to update the Board accordingly 
 Stores the type of the piece to a global variable that other classes can access to see what piece was originally clicked
 Also accounts for input in other gamestates such as 3 and 4, the passed pawn game states
 in the passed pawn game state you cannot interact with the Board 
     * @param e used to get the mouse x and y and to account for a mouseEvent 
     */
    public void mousePressed(MouseEvent e){//Where the event handeling begins, here the program checks if a piece has been selected, and if it has store information about it 
        board.updateBoard(boards);
        updateState = false;
        int mouseX = e.getX();//gets the x and y values of the mouse 
        int mouseY = e.getY();
        for(int i =whiteMovesX.size()-1; i >-1;i--){//resets the whiteMovesX list to account for a potential move change 
            whiteMovesX.remove(i);
            whiteMovesY.remove(i);
        }
        for(int i =blackMovesX.size()-1; i >-1;i--){//same with black moves 
            blackMovesX.remove(i);
            blackMovesY.remove(i);
        }
        for(int i =whiteOtherMovesX.size()-1; i >-1;i--){//also reset the temporary moves 
            whiteOtherMovesX.remove(i);
            whiteOtherMovesY.remove(i);
        }
        for(int i =blackOtherMovesX.size()-1; i >-1;i--){
            blackOtherMovesX.remove(i);
            blackOtherMovesY.remove(i);
        }
        if(gamestate ==0){
         if(mouseX >=400&&mouseX<=500&&mouseY>=400&mouseY<=500){
             displayBoard = 0;//give time for the Board to render in the graphics 
             setVisible(false);
             if(!started){
                gamestate = 5;
                started = true;
             }
             else{
                gamestate=2;
             }
             
         }
         if(mouseX >=200&&mouseX<=300&&mouseY>=400&mouseY<=500){
             displayBoard = 0;//give time for the Board to render in the graphics 
             setVisible(false);
             gamestate = 6;
         }
        }
        
        if(gamestate==3){//if we are in a state where a white pawn has been passed
            
            if(mouseX>=250&&mouseX<=325&&mouseY>28&&mouseY<=103){//look to see if you clicked on a queen
                
                pawnsWhite[saveK].setTag(pawnsWhite[saveK].getID(),"Q",pawnsWhite[saveK].getTag().charAt(0));//change the pawn into a queen
                 boards[pawnsWhite[saveK].getY()][pawnsWhite[saveK].getX()]=pawnsWhite[saveK].getTag();//update the Board with the new tag
                board.updateBoard(boards);//update teh common Board to the displayed Board 
                gamestate = 2;//allow for input again 
                 wasPromoted = true;
                //processMove(pawnsWhite[saveK],pawnsWhite[saveK].getX(),pawnsWhite[saveK].getY());
            }
            else if(mouseX>=325&&mouseX<=400&&mouseY>28&&mouseY<=103){//same thing as queen but for a bishop 
                
                pawnsWhite[saveK].setTag(pawnsWhite[saveK].getID(),"B",pawnsWhite[saveK].getTag().charAt(0));
                
                 boards[pawnsWhite[saveK].getY()][pawnsWhite[saveK].getX()]=pawnsWhite[saveK].getTag();
                 board.updateBoard(boards);
                 gamestate = 2;
                  wasPromoted = true;
                //processMove(pawnsWhite[saveK],pawnsWhite[saveK].getX(),pawnsWhite[saveK].getY());
            }
            else if(mouseX>=400&&mouseX<=475&&mouseY>28&&mouseY<=103){//same thing as queen but for a horse 
                
                pawnsWhite[saveK].setTag(pawnsWhite[saveK].getID(),"H",pawnsWhite[saveK].getTag().charAt(0));
                gamestate = 2;
                 boards[pawnsWhite[saveK].getY()][pawnsWhite[saveK].getX()]=pawnsWhite[saveK].getTag();
                board.updateBoard(boards);
                 wasPromoted = true;
                //processMove(pawnsWhite[saveK],pawnsWhite[saveK].getX(),pawnsWhite[saveK].getY());
            }
            else if(mouseX>=475&&mouseX<=550&&mouseY>28&&mouseY<=103){//same thing as a queen but for a rook 
               
                pawnsWhite[saveK].setTag(pawnsWhite[saveK].getID(),"R",pawnsWhite[saveK].getTag().charAt(0));
                gamestate = 2;
                 boards[pawnsWhite[saveK].getY()][pawnsWhite[saveK].getX()]=pawnsWhite[saveK].getTag();
                board.updateBoard(boards);
                wasPromoted = true;
                //processMove(pawnsWhite[saveK],pawnsWhite[saveK].getX(),pawnsWhite[saveK].getY());
            }
        }
        if(gamestate==4){//the game state when a black pawn has been passed, same as the white pawn passed but a different x and y checked for 
            
            if(mouseX>=250&&mouseX<=325&&mouseY>700&&mouseY<=800){
                
                pawnsBlack[saveK].setTag(pawnsBlack[saveK].getID(),"Q",pawnsBlack[saveK].getTag().charAt(0));
                 boards[pawnsBlack[saveK].getY()][pawnsBlack[saveK].getX()]=pawnsBlack[saveK].getTag();
                board.updateBoard(boards);
                gamestate = 2;
                 wasPromoted = true;
                //processMove(pawnsWhite[saveK],pawnsWhite[saveK].getX(),pawnsWhite[saveK].getY());
            }
            else if(mouseX>=325&&mouseX<=400&&mouseY>700&&mouseY<=800){
                
                pawnsBlack[saveK].setTag(pawnsBlack[saveK].getID(),"B",pawnsBlack[saveK].getTag().charAt(0));
                board.updateBoard(boards);
                 boards[pawnsBlack[saveK].getY()][pawnsBlack[saveK].getX()]=pawnsBlack[saveK].getTag();
                gamestate = 2;
                 wasPromoted = true;
                //processMove(pawnsWhite[saveK],pawnsWhite[saveK].getX(),pawnsWhite[saveK].getY());
            }
            else if(mouseX>=400&&mouseX<=475&&mouseY>700&&mouseY<=800){
                
                pawnsWhite[saveK].setTag(pawnsWhite[saveK].getID(),"H",pawnsWhite[saveK].getTag().charAt(0));
                gamestate = 2;
                 boards[pawnsBlack[saveK].getY()][pawnsBlack[saveK].getX()]=pawnsBlack[saveK].getTag();
                board.updateBoard(boards);
                 wasPromoted = true;
                //processMove(pawnsWhite[saveK],pawnsWhite[saveK].getX(),pawnsWhite[saveK].getY());
            }
            else if(mouseX>=475&&mouseX<=550&&mouseY>700&&mouseY<=800){
               
                pawnsBlack[saveK].setTag(pawnsBlack[saveK].getID(),"R",pawnsBlack[saveK].getTag().charAt(0));
                gamestate = 2;
                 boards[pawnsBlack[saveK].getY()][pawnsBlack[saveK].getX()]=pawnsBlack[saveK].getTag();
                board.updateBoard(boards); 
                wasPromoted = true;
                //processMove(pawnsWhite[saveK],pawnsWhite[saveK].getX(),pawnsWhite[saveK].getY());
            }
        }
        boolean processing = false;
        if(board.getTurn()&&type2Tag.equals("human")){
            processing =true;
        }
        if(!board.getTurn()&&type1Tag.equals("human")){
            processing = true;
        }
        if(processing){
        for(int i =0; i < 8;i++){//the code below is used to check if the x and y values are in the Board and store coordinates amd tag 
            for(int j =0; j < 8; j++){//we loop through every square on the Board 
                if(mouseX>100+j*75&&mouseX<100+75+j*75&&mouseY>100+i*75&&mouseY<100+(i*75)+75&&gamestate ==2){//checks if the mouse was presed on the Board  
                    type = "";//resets type 
                    for(int k=0; k<8;k++){//used to check if it is one of the pawns 
                        if(Integer.parseInt(boards[i][j].substring(2,4))==pawnsWhite[k].getID()&&board.getTurn()){//if the id matches a pawnID and it is whites turn
                            saveK =k;//save the pawn that was selected so that we can check if the move made to it was legal when the mouse is released
                            pawnsWhite[k].deleteMoves();//delete any previous moves so that there is no overlapping of moves, see piece.deleteMove() for details
                            pawnsWhite[k].checkLegalMove();//stores all of the legal moves in an array list
                            type="wP";//since a pawn can be upgraded to a different piece we need to use its tag to save the type
                            //when the mouse is released, it will check if based on the tag, a legal move was made
                        }
                        if(Integer.parseInt(boards[i][j].substring(2,4))==pawnsBlack[k].getID()&&!board.getTurn()){//same thing but for black pawn
                            saveK =k;
                            pawnsBlack[k].deleteMoves();
                            pawnsBlack[k].checkLegalMove();
                            type="bP";
                        }
                    }
                    for(int k =0; k < 2;k++){//same as above but for other pieces 
                        if(Integer.parseInt(boards[i][j].substring(2,4))==rooksWhite[k].getID()&&board.getTurn()){
                            saveK = k;
                            rooksWhite[k].deleteMoves();
                            rooksWhite[k].checkLegalMove();
                            
                           type = "wR";
                        }
                        if(Integer.parseInt(boards[i][j].substring(2,4))==rooksBlack[k].getID()&&!board.getTurn()){
                            saveK = k;
                            rooksBlack[k].deleteMoves();
                            rooksBlack[k].checkLegalMove();
                            type = "bR";
                        }
                        if(Integer.parseInt(boards[i][j].substring(2,4))==knightsWhite[k].getID()&&board.getTurn()){
                            saveK = k;
                            knightsWhite[k].deleteMoves();
                            knightsWhite[k].checkLegalMove();
                            type = "wH";
                        }
                        if(Integer.parseInt(boards[i][j].substring(2,4))==knightsBlack[k].getID()&&!board.getTurn()){
                            saveK = k;
                            knightsBlack[k].deleteMoves();
                            knightsBlack[k].checkLegalMove();
                            type = "bH";
                        }
                        if(Integer.parseInt(boards[i][j].substring(2,4))==bishopsWhite[k].getID()&&board.getTurn()){
                            saveK = k;
                            bishopsWhite[k].deleteMoves();
                            bishopsWhite[k].checkLegalMove();
                            type = "wB";
                        }
                        if(Integer.parseInt(boards[i][j].substring(2,4))==bishopsBlack[k].getID()&&!board.getTurn()){
                            saveK = k;
                            bishopsBlack[k].deleteMoves();
                            bishopsBlack[k].checkLegalMove();
                            type = "bB";
                        }
                    }
                    if(Integer.parseInt(boards[i][j].substring(2,4))==queenWhite.getID()&&board.getTurn()){
                            queenWhite.deleteMoves();
                            queenWhite.checkLegalMove();
                            type = "wQ";
                    }
                    if(Integer.parseInt(boards[i][j].substring(2,4))==queenBlack.getID()&&!board.getTurn()){
                           
                            queenBlack.deleteMoves();
                            queenBlack.checkLegalMove();
                            type = "bQ";
                    }
                    if(Integer.parseInt(boards[i][j].substring(2,4))==kingWhite.getID()&&board.getTurn()){
                           
                            kingWhite.deleteMoves();
                            kingWhite.checkLegalMove();
                            type = "wK";
                    }
                    if(Integer.parseInt(boards[i][j].substring(2,4))==kingBlack.getID()&&!board.getTurn()){
                            kingBlack.deleteMoves();
                            kingBlack.checkLegalMove();
                            type = "bK";
                    }
            }
        }
        }
        }
    }
    /**
     * Converts the mouseX and mouseY to the Board index through a loop where the position is stored as i and j 
 Checks to see if a piece was selected before by using the global variable type 
 If a pice was selected before then see if the move was legal by using processMove and the Board i and j
 If the piece was a pawn look to see if it was pushed to the end and if it was then call the processPush method, which switches the gamestate
     * @param e  used to get the x and the y of the mouse 
     */
    public void mouseReleased(MouseEvent e){//here checks if a piece has been selected ,and if so, based on the x and y of the mouse if the move made to it is leaal
        int mouseX = e.getX();//stores the x and y of the mouse 
        int mouseY = e.getY();
        if(gamestate!=0){
            if(mouseX>50&&mouseX<75&&mouseY>50&&mouseY<75){
                gamestate=0;
            }
        }
        if(gamestate>=6){
            if(mouseX>770&&mouseX<800&&mouseY>390&&mouseY<440){
                gamestate++;
            }
            if(mouseX>0&&mouseX<50&&mouseY>390&&mouseY<440){
                gamestate--;
            }
            if(gamestate<6){
                gamestate = 6;
            }
            if(gamestate>8){
                gamestate =8;
            }
        }
        else if(gamestate==5){//coordinates of t1Tag-> 200,400,100,100 two: 350,550,100,100 three: 500,400,100,100 ->t2tag
            System.out.println("getting input");
            boolean stateChanged = false;
            if(!stateChanged&&mouseX >=200&&mouseX<=300&&mouseY>=400&mouseY<=500&&type2Tag.equals("human")&&type1Tag.equals("human")){
                type1Tag = "AI";
                stateChanged = true;
             
            }
            else if(!stateChanged&&mouseX >=500&&mouseX<=600&&mouseY>=400&mouseY<=500&&type1Tag.equals("human")&&type2Tag.equals("human")){
                type2Tag = "AI";
                stateChanged = true;
            }
            if(!stateChanged&&mouseX >=200&&mouseX<=300&&mouseY>=400&mouseY<=500&&type1Tag.equals("AI")){
                type1Tag = "human";
                System.out.println("we are changing this state");
                stateChanged = true;
             
            }
            else if(!stateChanged&&mouseX >=500&&mouseX<=600&&mouseY>=400&mouseY<=500&&type2Tag.equals("AI")){
                type2Tag = "human";
                System.out.println("we are changing this states");
                stateChanged = true;
            }
            else if(mouseX >=350&&mouseX<=450&&mouseY>=550&mouseY<=650){
                if(type1Tag.equals("AI")){
                    ai = new AI(black,board,white);
                } else if(type2Tag.equals("AI")){
                    ai = new AI(white,board,black);
                }
                displayBoard = 0;//give time for the Board to render in the graphics 
                setVisible(false);
                gamestate = 2;
                System.out.println("recognizing");
             
            }
            else{
                System.out.println("mouse X" + mouseX + " mouseY" + mouseY);
            }
            System.out.println(stateChanged);
            
        }
        if(gamestate>1&&gamestate<5){
            if(mouseX>750&&mouseX<800&&mouseY>50&&mouseY<100){
                System.out.println("udone");
                board.undoMove();
                setBoard(board.getBoard());
            }else{
            for(int i =0; i < 8;i++){//same loop as above, if the mouse was pressed on the Board, get the coordinates of the Board through x and y 
                for(int j =0; j < 8; j++){
                   if(mouseX>100+j*75&&mouseX<100+75+j*75&&mouseY>100+i*75&&mouseY<100+(i*75)+75){

                        if(type.equals("wP")){//if the type of piece that was selected was a white pawn
                           processMove(pawnsWhite[saveK],j,i);//see if that was a legal move for the pawn and based off that peforms actions, see processMove for details, right below this class
                           if(pawnsWhite[saveK].getY()==0&&pawnsWhite[saveK].getTag().charAt(1)=='P'){
                               processPush(pawnsWhite[saveK]);
                           }
                        }
                        if(type.equals("bP")){//same code 
                            processMove(pawnsBlack[saveK],j,i);
                            if(pawnsBlack[saveK].getY()==7&&pawnsBlack[saveK].getTag().charAt(1)=='P'){
                               processPush(pawnsBlack[saveK]);
                           }
                        }
                        if(type.equals("wR")){
                            processMove(rooksWhite[saveK],j,i);
                        }
                        if(type.equals("bR")){
                            processMove(rooksBlack[saveK],j,i);
                        }
                        if(type.equals("wB")){
                            processMove(bishopsWhite[saveK],j,i);
                        }
                        if(type.equals("bB")){
                            processMove(bishopsBlack[saveK],j,i);
                        }
                        if(type.equals("wH")){
                            processMove(knightsWhite[saveK],j,i);
                        }
                        if(type.equals("bH")){
                            processMove(knightsBlack[saveK],j,i);                       
                        }
                        if(type.equals("wQ")){
                            processMove(queenWhite,j,i);
                        }
                        if(type.equals("bQ")){
                            processMove(queenBlack,j,i);
                        }
                        if(type.equals("wK")){
                            processMove(kingWhite,j,i);
                        }
                        if(type.equals("bK")){
                            processMove(kingBlack,j,i);
                        }
                    }            

                }
            }
        }
        }
        
    }/**
     * If the piece was a white pawn then set the state to three, the state that process input for a white passed pawn 
     * @param piece used to see what type of piece was pushed based on its tag 
     */
    public void processPush(Piece piece){
        if(piece.getTag().substring(0,2).equals("wP")){
            gamestate = 3;
            System.out.println("OUT PUT FOUND OUT PUT FOUND OUT PUT FOUND ");
        }
        else if(piece.getTag().substring(0,2).equals("bP")){
            gamestate = 4;
        }
    }
    /**
     * This is for changing the location of the rook 
     * The location of the king is hardcoded because of new methods that overwrite the location of the king when castled
        *Potential fix, as it can create bugs   
     * @param piece this is the rook 
     * @param piece2 this is the king, the location of the rook changes based on the king, and whether it is black or white 
     * @param x This is so that the location of the rooks x can be changed independently based on the type of castling to prevent re-writing this alot
     * The turns are swapped to avoid any errors with turns
     */
    public void castleProcess(Piece piece,Piece piece2, int x){
        
            board.setTotalMoveCount(1);
            setBoard(board.getBoard());
            boards[piece.getY()][piece.getX()] = "00000";
            piece.moveChanges(piece2.getX()-x,piece2.getY());
            piece.decrementMoveCount();
            System.out.println(piece.getTag() + piece.getMoveCount());
            boards[piece.getY()][piece.getX()]=piece.getTag();            
            board.updateBoard(boards);
            board.switchTurn();
               
    }/**
     * Here any potential moves gathered from the mouseInputs are tested
     * @param piece the piece that we want to move to a location
     * @param x the x location that is being tested
     * @param y same as x but with  y
     * First we make sure that checkmate or stalemate has not been declared via the handle boolean, which is originally true but turned off when 
     * either of these game ending states occur 
     * Secondly it checks if the move that was created based on where the mouse was pressed and released was legal, this however doesn't require mouse
     * input and an ai can use this class to 
     * After this, based on the type of move we determine what type of move was made and look for a check, or in castling's case looks for multiple 
     * squares to make sure that the opposite color doesn't have a legal move that intersects it including check
     * If the move has been legal we edit the board and handle any collisions that occur
     * If the move is illegal reset the board to the original version 
     */

    /**
     * Here any potential moves gathered from the mouseInputs are tested
     * @param piece the piece that we want to move to a location
     * @param x the x location that is being tested
     * @param y same as x but with  y
First we have to make handle was promoted(a boolean that is used to make sure we are updating the board after a promotion occured) and then after
make sure that checkmate or stalemate has not been declared via the handle boolean, which is originally true but turned off when either of these game 
ending states occur Secondly it checks if the move that was created based on where the mouse was pressed and released was legal, this however 
doesn't require mouse input and an ai can use this class to 
After this, based on the type of move we determine what type of move was made and look for a check, or in castling's case looks for multiple 
squares to make sure that the opposite color doesn't have a legal move that intersects it including check
If the move has been legal we edit the Board and handle any collisions that occur
If the move is illegal reset the Board to the original version
     */
    public void processMove(Piece piece, int x,int y){//mangages any move done to a piece
        if(wasPromoted){
            wasPromoted = false;
        }
        
        if(handle){
        
        switch(piece.checkPossibleMoves(x,y,true)){
            case 1:
                handleCollision(piece.getTag().substring(0,2),piece.getY(),piece.getX(),true);
                board.setTotalMoveCount(1);//everything is done in Board now for processing moves, will update all later, but this way we can transition into Board becoming the main class
                board.getBoard()[piece.getY1()][piece.getX1()]="00000";
                piece.setTag(Integer.parseInt(piece.getTag().substring(2,4)),piece.getTag().substring(1,2),piece.getTag().charAt(0));
                System.out.println(piece.getTag());
                board.getBoard()[piece.getY()][piece.getX()]=piece.getTag(); 
                
                if(piece.getTag().substring(0,2).equals("wQ")){
                    System.out.println("working here");
                    piece.displayMoves();
                }
                
                //piece.deleteMoves();
                if(!isInCheck(piece.getTag().charAt(0),true)){//if it is not in check 
                    try{if(ai.isActive){ai.addMove();}}catch(NullPointerException e){}
                    setBoard(board.getBoard());//then update the display Board accordingly 
                    board.switchTurn();
                }else{
                    //board.displayBoard();
                    board.removeMove();
                    System.out.println("there was a check detected");
                    board.updateBoard(boards);//sets the Board back to the original Board
                    piece.moveChanges(piece.getX1(),piece.getY1());//moves the piece to its original location and sets the new location back to blank if it doesn't prevent a check
                    break;//ends before the turn is switched so the person has a chace to get it right
                    
                }
                
                break;
        
            case 2://will update this later when i have time 
                board.setTotalMoveCount(1);
                System.out.println("special considered");
                if(!isInCheck(piece.getTag().charAt(0),true)){
                    setBoard(board.getBoard());
                    board.switchTurn();
                    try{if(ai.isActive){ai.addMove();}}catch(NullPointerException e){}
                }
                else{
                    board.updateBoard(boards);
                    
                    piece.moveChanges(piece.getX1(),piece.getY1());
                    break;
                }
                setBoard(board.getBoard());
                turn = !turn;
                break;
            case 3: 
                System.out.println("this piece has no moves");
                System.out.println(piece.getMovesX().size());
                piece.displayMoves();
                break;
            case 4: 
                System.out.println("is it the case that is an error");
                if(!isInOpposite('w',4,7)&&!isInOpposite('w',5,7)&&!isInOpposite('w',6,7)&&!isInOpposite('w',7,7)){
                    castleProcess(rooksWhite[1],kingWhite,1);
                    kingWhite.moveChanges(6,7);
                    boards[7][6] = kingWhite.getTag();
                    boards[7][4] = "00000";
                    System.out.println("KingWhite x and y " + kingWhite.getX() + " " +  kingWhite.getY());
                    board.updateBoard(boards);
                
                    try{if(ai.isActive){ai.addMove();}}catch(NullPointerException e){}
                }    
                else{
                    board.updateBoard(boards);
                    //piece.movecount = 0;
                    piece.moveChanges(4,7);
                    piece.movecount = 0;
                }    
                break;
            case 5:
                if(!isInOpposite('b',4,0)&&!isInOpposite('b',5,0)&&!isInOpposite('b',6,0)&&!isInOpposite('b',7,0)){
                    System.out.println("ladies and gentlemen we gottem");
                    castleProcess(rooksBlack[1],kingBlack,1);
                    kingWhite.moveChanges(6,0);
                    boards[0][6] = kingBlack.getTag();
                    boards[0][4] = "00000";
                    System.out.println("KingBlack x and y " + kingBlack.getX() + " " +  kingWhite.getY());
                    board.updateBoard(boards);
                    
                    try{if(ai.isActive){ai.addMove();}}catch(NullPointerException e){}
                }
                else{
                    board.updateBoard(boards);
                    //piece.movecount = 0;
                    piece.moveChanges(4,0);
                    piece.movecount = 0;
                }
                
                break;
            case 6: 
                if(!isInOpposite('w',0,7)&&!isInOpposite('w',1,7)&&!isInOpposite('w',2,7)&&!isInOpposite('w',3,7)&&!isInOpposite('w',4,7)){
                    System.out.println("ladies and gentlemen we gottem");
                    castleProcess(rooksWhite[0],kingWhite,-1);
                    kingWhite.moveChanges(2,7);
                    boards[7][2] = kingWhite.getTag();
                    boards[7][4] = "00000";
                    System.out.println("KingWhite x and y " + kingWhite.getX() + " " +  kingWhite.getY());
                    board.updateBoard(boards);
                    
                   try{if(ai.isActive){ai.addMove();}}catch(NullPointerException e){}
                }
                else{
                    board.updateBoard(boards);
                    
                    piece.moveChanges(4,7);
                    piece.movecount = 0;
                    board.switchTurn();
                }
                
                break;
            case 7: 
                if(!isInOpposite('b',0,0)&&!isInOpposite('b',1,0)&&!isInOpposite('b',2,0)&&!isInOpposite('b',3,0)&&!isInOpposite('b',4,0)){
                    System.out.println("ladies and gentlemen we gottem");
                    castleProcess(rooksBlack[0],kingBlack,-1);
                    kingWhite.moveChanges(2,0);
                    boards[0][2] = kingBlack.getTag();
                    boards[0][4] = "00000";
                    System.out.println("KingWhite x and y " + kingWhite.getX() + " " +  kingWhite.getY());
                    board.updateBoard(boards);
                  try{if(ai.isActive){ai.addMove();}}catch(NullPointerException e){}
                }
                else{
                    board.updateBoard(boards);
                    
                    piece.moveChanges(4,0);
                    piece.movecount = 0;
                }
                
                break;
                
        }
        }
        whitecount=0;
        blackcount =0;
        updateArrays();
        System.out.println("whitecount: " + whitecount);
        System.out.println("blackcount: " + blackcount);
        
        if(whitecount ==0||blackcount==0){
            if(isInCheck('b',true)){ 
                System.out.println("checkmate handled ");
                checkmessage = "checkmate, white won!!! ggs  ";
                handle = false;
                
            }else if(isInCheck('w',true)){
                System.out.println("checkmate handled ");
                checkmessage = "checkmate, black won!!!! ggs ";
                handle = false;
            }
            else{
                System.out.println("stalemate");
                checkmessage= "stalemate";
                handle = false;
            }
        }else if(isInCheck('w',true)){checkmessage = "is in checkwhite";}
        else if(isInCheck('b',true)){checkmessage = "is in checkblack";}
        else{checkmessage = "is not in check";}
        updateState = true;
    }
    /**
     * This is for castling and checks if black has a legal move in any of the squares that king and rook will cross through/inhibit
 Can also be used to see if a specific square on the Board is in the legal moves of the opposite side 
     * @param color the color of the square that you are testing, used to compare with the color of a square is a legal mvoe for the opposite color 
     * @param x the x coordinate of the Board piece that your are testing
     * @param y the y coordinate of teh Board piece that you are testing 
     * @return if the square is in the possible moves of the opponent then return true 
     */
    public boolean isInOpposite(char color, int x, int y ){
        updateArrays();
        if(color == 'b'){
            for(int i =0; i < whiteMovesX.size();i++){
                if(x==whiteMovesX.get(i)&&y==whiteMovesY.get(i)){
                    return true;
                }
            }
        }
        if(color == 'w'){
            for(int i =0; i < blackMovesX.size();i++){
                if(x==blackMovesX.get(i)&&y==blackMovesY.get(i)){
                    System.out.println("oh no we didn't get em");
                    return true;
                }
            }
            
        }
        
        System.out.println("we gottem");
        return false;
    }
    
    
    /**
     * 
     * @param color used to get the color of the other piece
     * @param kingupdate used to determine whether or not it is a theoretical move or it is a move that is being tested 
     * @return returns whether or not there is a square in check 
     */
    public boolean isInCheck(char color, boolean kingupdate){//checks if it is in check based on the color of the piece moved
        //here you need to add to the arraylist checking pieces to figure out what pieces are checking the king
        
        boolean Private = kingupdate;//because
        if(kingupdate == true){
            kingwhitex = kingWhite.getX();
            kingblackx = kingBlack.getX();
            kingwhitey = kingWhite.getY();
            kingblacky = kingBlack.getY();
            
        }
        if(color == 'w'){
            updateBlack();
            for(int i =0;i<blackOtherMovesX.size();i++){
                if(kingwhitex==blackOtherMovesX.get(i)&&kingwhitey==blackOtherMovesY.get(i)){
                    //tests if the kings location is in the potential locatiosn for the 
                    if(kingupdate){
                        checkmessage = "is in check white";
                    }
                    if(color=='w'){
                        
                        return true;}//if the piece moved was white and the white king is in check then return treu
                }
            }
        }
        if(color == 'b'){
            updateWhite();
            for(int i =0;i<whiteOtherMovesX.size();i++){//tests if the king
                if(kingblackx==whiteOtherMovesX.get(i)&&kingblacky==whiteOtherMovesY.get(i)){
                    //same logic for black
                    if(kingupdate){
                        checkmessage = "is in check black";
                    }
                    if(color=='b'){
                       
                        return true;
                    }

                }
            }
        }
        if(kingupdate){
            checkmessage = "not in check";
        }
        return false;
    }/**
     * not added yet but here we are going to add a method to undo a move 
     */
    public void undoMove(){
        
    }
    /**
     * the method where all the possible moves are gathered and tested to see if they don't result in check 
     * First it calls setLocation() which checks all the legal moves of a piece and for every moves see's if it results in a check 
     * After that it resets the moves so that there are no errors with movement 
     * The class itself could be phased out be the ability to loop through arrays and consider all positions is what is important 
     * It is only used for castling as of now 
     */
    public void updateArrays(){//where all the possible moves are gathered, setLocation() is below this                 
                    queenWhite.checkLegalMove();
                    setLocation(queenWhite,'w');
                    queenWhite.checkLegalMove();
                    
                    kingWhite.checkLegalMove();
                    setLocation(kingWhite,'w');
                    kingWhite.checkLegalMove();
                    
                    for(int k=0; k<8;k++){//used to check if it is one of the pawns 
                        pawnsWhite[k].checkLegalMove();//gets all of the legal moves, delete moves added to all of the mousePressed() so no overlap
                        setLocation(pawnsWhite[k],'w');//adds it to the whiteMovesX and whiteMovesY array list
                        pawnsWhite[k].checkLegalMove();
                    }for(int k =0; k < 8; k++){    
                        pawnsBlack[k].checkLegalMove();//gets all of the legal moves for a black pawn 
                        setLocation(pawnsBlack[k],'b');// adds it to the blackMovesX and blackMovesY array list
                        pawnsBlack[k].checkLegalMove();
                    }
                    for(int k =0; k < 2; k++){
                        rooksWhite[k].checkLegalMove();    
                        setLocation(rooksWhite[k],'w');
                        rooksWhite[k].checkLegalMove();
                        
                       
                        knightsWhite[k].checkLegalMove();
                        setLocation(knightsWhite[k],'w');
                        knightsWhite[k].checkLegalMove();
                   
                        bishopsWhite[k].checkLegalMove();
                        setLocation(bishopsWhite[k],'w');
                        bishopsWhite[k].checkLegalMove();
                          
                    }for(int k =0; k < 2; k++){       
                        rooksBlack[k].checkLegalMove();
                        setBlackLocation(rooksBlack[k],'b');
                        rooksBlack[k].checkLegalMove();
                   
                    
                        knightsBlack[k].checkLegalMove();
                        
                        setLocation(knightsBlack[k],'b');
                        knightsBlack[k].checkLegalMove();
    
                        bishopsBlack[k].checkLegalMove();
                        setLocation(bishopsBlack[k],'b');
                        bishopsBlack[k].checkLegalMove();
                    }
                        queenBlack.checkLegalMove();
                        setLocation(queenBlack,'b');
                        queenBlack.checkLegalMove();
                         
                        kingBlack.checkLegalMove();
                        setLocation(kingBlack,'b');
                        kingBlack.checkLegalMove();
                
                    
           
    }
    /**
     * 
     * @param board this is used to update the display Board(the array called boards) with another array
 Mainly used to update boards to the most updated array in Board, which is how all the pieces are edited so that there is no confusion between 
 graphics and between the 
     */
    public void setBoard(String[][] board){
       for(int i =0;i<8;i++){
           for(int j =0; j < 8;j++){
            boards[i][j]=board[i][j];
           }
       }
    }
    /**
     * 
     * @param piece The piece that we are checking the legal moves for 
     * @param color gets the color of the piece so we don't have to type piece.getTag().getCharAt...
     * First it makes sure a piece is alive before testing the possible moves to save processing power 
     * Next a list of arrays called prevX and prevY which are used to make sure that the same move isn't tested twice which would 
     * make it so we don't redundantly check the same move and make sure we don't increase the move count higher than it should be
     * If the move has not previously occured
     */
    public void setLocation(Piece piece, char color){//where the pieces are added to the array 
        if(piece.returnAlive()){
            ArrayList<Integer> prevX = new ArrayList<>();
            ArrayList<Integer> prevY = new ArrayList<>();
            
            for(int i =0; i < piece.getMovesX().size();i++){
                boolean on = false;
                for(int j =0; j < prevX.size();j++){
                    if(prevX.get(j).equals(piece.getMovesX().get(i))&&prevY.get(j).equals(piece.getMovesY().get(i))){
                        on = true;
                    }
                }
                if(!on){
                    int x = 0; 
                    int y =0;
                    String special = "";
                    prevX.add(piece.getMovesX().get(i));
                    prevY.add(piece.getMovesY().get(i));
                    x=piece.getMovesX().get(i); 
                    y=piece.getMovesY().get(i);
                    special = piece.getSpecial().get(i);
                    handleCollision(piece.getTag().substring(0,2),y,x,false);

                    if(piece.getTag().charAt(0)=='w'){

                            if(piece.getTag().startsWith("wK")){

                                kingwhitex = x;
                                kingwhitey = y;
                                

                            }else{
                                kingwhitex = kingWhite.getX();
                                kingwhitey = kingWhite.getY();
                            }
                            String currenttag = board.getBoard()[y][x];
                            board.getBoard()[piece.getY()][piece.getX()]="00000";

                            board.getBoard()[y][x]=piece.getTag();
                            //board.displayBoard();
                            if(!isInCheck('w',false)){
                               
                               whitecount++;
                               whiteMovesX.add(x);//gets the ArrayList that stores moves in piece getMovesX() and getMovesY() and adds every element to the code
                               whiteMovesY.add(y);
                               
                            }else{

                            }
       
                            board.updateBoard(boards);

                        }else{


                            String currenttag = board.getBoard()[y][x];
                            board.getBoard()[piece.getY()][piece.getX()]="00000";

                            board.getBoard()[y][x]=piece.getTag();
                            //board.displayBoard();
                            if(piece.getTag().startsWith("bK")){
                                kingblackx = x;
                                kingblacky = y;
                            }else{
                                kingblackx = kingBlack.getX();
                                kingblacky = kingBlack.getY();
                            }

                            if(!isInCheck('b',false)){
                                blackcount++;
                                blackMovesX.add(x);//gets the ArrayList that stores moves in piece getMovesX() and getMovesY() and adds every element to the code
                                blackMovesY.add(y);
                                //System.out.println("Piece tag here lol xD fa: " + piece.getTag() + " x: " + piece.getX() + " y: " +  piece.getY());
                                
                            }else{


                            }

        //System.out.println("Possible moves: "+ piece.getTag()+ " "+piece.getMovesX().get(i) + " " + piece.getMovesY().get(i));, test code 
                            board.updateBoard(boards);

                        //System.out.println("Possible moves: "+ piece.getTag() + " "+ piece.getMovesX().get(i) + " " + piece.getMovesY().get(i));, test code
                        }

                    }
                turnOnTemps();
                }

            piece.deleteMoves();//delets moves so no overlap, can now remove deleteMoves() from mousePressed()
        }
        kingwhitex = kingWhite.getX();
        kingwhitey = kingWhite.getY();
        kingblackx= kingBlack.getX();
        kingblacky = kingBlack.getY();
    }/**
     * The purpose of this class is incase we need a variable there is one already setup 
     * @param specialOn In case we need to anything that requires an extra or editable variable this is what  this class is for 
     */
    public void setSpecialOn(boolean specialOn){
        this.specialOn = specialOn;
    }
    /**
     * This class handles collisions pieces 
     * First it makes sure that the original square(the square from the display array boards) is of the opposite color from black
     * If this is true, a variable called control is true and from there on we loop through every piece to find the one with the matching id
     * If we find a piece with a matching id, we make sure that it is not the original piece 
     * If there is no error, we check to see whether or not we are doing a temporary death for position testing or a permanent death using the booelan 
     * type to check for this 
     * If we are doing a permanent death then we turn the aliveBoolean to false, otherwise we turn the temporary, or controlledOff boolean off 
     * @param tag used to get the color and type of piece, only the FIRST TWO characters of the normal tag since that is all that we need 
     * @param i the y location of the piece 
     * @param j the x location of the piece 
     * @param type determines whether or not we are doing testing or we are actually looking for a collision 
     */
    public void handleCollision(String tag, int i, int j,boolean type){
        boolean control = false;
        if(tag.charAt(0)=='w'&&boards[i][j].charAt(0)=='b'){
            control = true;
        }else if(tag.charAt(0)=='b'&&boards[i][j].charAt(0)=='w'){
            control = true;
        }
        if(control){
            System.out.println("collisions are being handled"); 
            for(int k=0; k<8;k++){//used to check if it is one of the pawns 
                        if(!tag.equals("wP")&&Integer.parseInt(boards[i][j].substring(2,4))==pawnsWhite[k].getID()){//same loop as in mousePressed(), except all inputs are considered
                            if(type)
                                pawnsWhite[k].setAlive(false);
                            else
                                pawnsWhite[k].controlledOff = false;
                        }
                        
                    }//the above logic repeats for every piece on the Board 
                    for(int k =0; k < 2;k++){//same as above but for other pieces 
                        if(!tag.equals("wR")&&Integer.parseInt(board.getBoard()[i][j].substring(2,4))==rooksWhite[k].getID()){
                            if(type)
                                rooksWhite[k].setAlive(false);
                            else
                                rooksWhite[k].controlledOff = false;
                        }
                        
                        if(!tag.equals("wH")&&Integer.parseInt(boards[i][j].substring(2,4))==knightsWhite[k].getID()){
                            if(type)
                                knightsWhite[k].setAlive(false);
                            else
                                knightsWhite[k].controlledOff = false;
                        }
                        
                        if(!tag.equals("wB")&&Integer.parseInt(boards[i][j].substring(2,4))==bishopsWhite[k].getID()){
                            if(type)
                                bishopsWhite[k].setAlive(false);
                            else
                                bishopsWhite[k].controlledOff = false;
                        }
                       
                    }
                    if(!tag.equals("wQ")&&Integer.parseInt(boards[i][j].substring(2,4))==queenWhite.getID()){
                            if(type)
                                queenWhite.setAlive(false);
                            else{
                                System.out.println("other collision was being handled ");
                                queenWhite.controlledOff = false;
                            }
                    }
                   
                    if(!tag.equals("wK")&&Integer.parseInt(boards[i][j].substring(2,4))==kingWhite.getID()){
                            if(type)
                                kingWhite.setAlive(false);
                            else
                                kingWhite.controlledOff = false;

                    }
               for(int k=0; k<8;k++){//used to check if it is one of the pawns 
                        if(!tag.equals("bP")&&Integer.parseInt(boards[i][j].substring(2,4))==pawnsBlack[k].getID()){
                            if(type)
                                pawnsBlack[k].setAlive(false);
                            else
                               pawnsBlack[k].controlledOff = false;
                        }
                }//the above logic repeats for every piece on the Board 
                for(int k =0; k < 2;k++){//same as above but for other pieces 
                    
                    if(!tag.equals("bR")&&Integer.parseInt(boards[i][j].substring(2,4))==rooksBlack[k].getID()){
                        if(type)
                             rooksBlack[k].setAlive(false);
                            else
                               rooksBlack[k].controlledOff = false;
                    }
                    
                    if(!tag.equals("bH")&&Integer.parseInt(boards[i][j].substring(2,4))==knightsBlack[k].getID()){
                        if(type)
                             knightsBlack[k].setAlive(false);
                            else
                              knightsBlack[k].controlledOff = false;
                    }
                    
                    if(!tag.equals("bB")&&Integer.parseInt(boards[i][j].substring(2,4))==bishopsBlack[k].getID()){
                        if(type)
                             bishopsBlack[k].setAlive(false);
                            else
                               bishopsBlack[k].controlledOff = false;
                    }
                }
                
                if(!tag.equals("bQ")&&Integer.parseInt(boards[i][j].substring(2,4))==queenBlack.getID()){
                    if(type)
                             queenBlack.setAlive(false);
                            else
                               queenBlack.controlledOff = false;
                }
                
                if(!tag.equals("bK")&&Integer.parseInt(boards[i][j].substring(2,4))==kingBlack.getID()){
                       if(type)
                             kingBlack.setAlive(false);
                            else
                               kingBlack.controlledOff = false;
                }

        }
    }/**
     * The purpose of this is to turn on all temporary offs after testing 
     */
    public void turnOnTemps(){
        for(int i =0; i < 8;i++){
            pawnsWhite[i].controlledOff = true;
            pawnsBlack[i].controlledOff = true;
        }
        for(int i =0; i < 2; i++){
            rooksWhite[i].controlledOff = true;
            rooksBlack[i].controlledOff = true;
            knightsWhite[i].controlledOff = true;
            knightsBlack[i].controlledOff = true;
            bishopsWhite[i].controlledOff = true;
            bishopsBlack[i].controlledOff = true;
            
        }
        kingWhite.controlledOff =true;
        kingBlack.controlledOff =true;
        queenWhite.controlledOff = true;
        queenBlack.controlledOff = true;
    }/**
     * updateBlack is called to see if a move that white makes will put the king in check or to test the resulting white moves based on a move
     * The array adds the moves to an array called blackOtherMovesX and blackOtherMovesY, two arrays used to store potential moves 
     */
    public void updateBlack(){
        for(int i =blackOtherMovesX.size()-1; i >-1;i--){
            blackOtherMovesX.remove(i);
            blackOtherMovesY.remove(i);
        }

                for(int k=0; k<8;k++){//used to check if it is one of the pawns 
                        
                            pawnsBlack[k].deleteMoves();
                            pawnsBlack[k].checkLegalMove();//gets all of the legal moves for a black pawn 
                            setBlackLocation(pawnsBlack[k],'b');// adds it to the blackMovesX and blackMovesY array list
                            
                    }//the above logic repeats for every piece on the Board 
                for(int k =0; k < 2;k++){//same as above but for other pieces 
                   
                        rooksBlack[k].deleteMoves();
                        rooksBlack[k].checkLegalMove();
                        setBlackLocation(rooksBlack[k],'b');
                        
                        knightsBlack[k].deleteMoves();
                        knightsBlack[k].checkLegalMove();               
                        setBlackLocation(knightsBlack[k],'b');
                        
                    
                        bishopsBlack[k].deleteMoves();
                        bishopsBlack[k].checkLegalMove();
                        setBlackLocation(bishopsBlack[k],'b');
                        
                    }
                
                        queenBlack.deleteMoves();
                        queenBlack.checkLegalMove();
                        setBlackLocation(queenBlack,'b');
                
                        kingBlack.deleteMoves();
                        kingBlack.checkLegalMove();
                        setBlackLocation(kingBlack,'b');       
    } /**
     * Same as updateBlack() but for the whiteOtherMovesX and whiteOtherMovesY arrays 
     */
    public void updateWhite(){
        for(int i =whiteOtherMovesX.size()-1; i >-1;i--){
            whiteOtherMovesX.remove(i);
            whiteOtherMovesY.remove(i);
        }
        
                for(int k=0; k<8;k++){//used to check if it is one of the pawns 
                         pawnsWhite[k].deleteMoves();
                            pawnsWhite[k].checkLegalMove();//gets all of the legal moves, delete moves added to all of the mousePressed() so no overlap
                            setWhiteLocation(pawnsWhite[k],'w');//adds it to the whiteMovesX and whiteMovesY array list
                            
                }            
                           
                for(int k =0; k < 2;k++){//same as above but for other pieces 
                    
                        rooksWhite[k].deleteMoves();
                        rooksWhite[k].checkLegalMove();

                        setWhiteLocation(rooksWhite[k],'w');
                  
                        knightsWhite[k].deleteMoves();
                        knightsWhite[k].checkLegalMove();
                        setWhiteLocation(knightsWhite[k],'w');
                    
                        bishopsWhite[k].deleteMoves();
                        bishopsWhite[k].checkLegalMove();
                        setWhiteLocation(bishopsWhite[k],'w');
                        
                    }
                    
              
                        queenWhite.deleteMoves();
                        queenWhite.checkLegalMove();

                        setWhiteLocation(queenWhite,'w');
                

                        kingWhite.deleteMoves();
                        kingWhite.checkLegalMove();
                        setWhiteLocation(kingWhite,'w');
    }/**
     * The purpose of this is to update the temporary black arrays, the arrays that are used to store the results of a move made and see if it is legal or good 
     * @param piece Checks if a piece is alive, and if it is alive then it loops through all the moves that the piece can make 
     * and adds them to the temporary black pieces array
     * @param color Not needed, just in case we need it 
     */
    public void setBlackLocation(Piece piece, char color)
    {
        if(piece.returnAlive()){
            for(int i = 0; i < piece.getMovesX().size();i++){
                blackOtherMovesX.add(piece.getMovesX().get(i));
                blackOtherMovesY.add(piece.getMovesY().get(i));
            }
        }
    }/**
     * same as setBlackLocations(} but it is used to update the white temporary arrays 
     * @param piece Checks if a pice is alive, and if it is alive it loops through all the moves that the piece can make 
     * @param color not needed, just in case we need it
     */
    public void setWhiteLocation(Piece piece, char color){
        if(piece.returnAlive()){
            for(int i = 0; i < piece.getMovesX().size();i++){
                whiteOtherMovesX.add(piece.getMovesX().get(i));
                whiteOtherMovesY.add(piece.getMovesY().get(i));
            }
        }
    }
    public void resetBoard(){
        for(int i =0; i < board.allMoves.size()-2;i++){
            board.allMoves.remove(board.allMoves.size()-1);
        }
        board.undoMove();
        board.setTurn(true);
        for(int i =0; i < ai.allMoves.size();i++){
            ai.allMoves.remove(ai.allMoves.size()-1);
        }
        ai.state =0;
        gamestate=0;
        started= false;
    }
    /**
     * 
     * @param args the main method is called 
     */
    public static void main(String[] args) {
       new Chess2();
    }
    
}