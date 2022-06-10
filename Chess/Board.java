package R4.Chess;

//Java class used to contain the playing board 8x8 2d piece array.

import R4.Chess.GUI.Color;
import R4.Chess.Piece.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Board {

    private Piece[][] board = new Piece[8][8];
    private int sqSize = 80;
    private boolean whiteTurn = true;


    public Board(){
        populateInitialPieces();
    }


    //uses a hashmap to reduce movement definition in the pawn class
    private HashMap<Color,Integer> generateFactionMultiplier(){
        HashMap<Color,Integer> boolIntMap = new HashMap<>();
        boolIntMap.put(Color.WHITE , -1);
        boolIntMap.put(Color.BLACK , 1);

        return boolIntMap;
    }

    public void moveValidator(int xCoordinate, int yCoordinate , Piece[][] board){

        if(board[yCoordinate][xCoordinate]== null){
            return;
        }

        Piece piece = board[yCoordinate][xCoordinate];
        piece.clearArrayList();

        if(piece.getClass().equals(Pawn.class)){
            generateValidPawnMoves(xCoordinate,yCoordinate , board);
        }
        else if(piece.getClass().equals(Rook.class)){
            generateValidCardinalMovement(xCoordinate,yCoordinate, 7, board);
        }
        else if(piece.getClass().equals(Bishop.class)){
            generateValidDiagonalMovement(xCoordinate,yCoordinate, 7, board);
        }
        else if(piece.getClass().equals(Queen.class)){
            generateValidCardinalMovement(xCoordinate,yCoordinate,7, board);
            generateValidDiagonalMovement(xCoordinate,yCoordinate, 7, board);
        }
        else if(piece.getClass().equals(King.class)){
            generateValidCardinalMovement(xCoordinate,yCoordinate , 1, board);
            generateValidDiagonalMovement(xCoordinate,yCoordinate , 1, board);
        }
        else if(piece.getClass().equals(Knight.class)){
            generateValidKnightMoves(xCoordinate,yCoordinate, board);
        }
    }

    private void populateInitialPieces(){
        //Pawns

        for(int pawns = 0; pawns < 8; pawns++){
            board[1][pawns] = new Pawn(Color.BLACK);
            board[6][pawns] = new Pawn(Color.WHITE);
        }



        //Rooks
        board[0][0] = new Rook(Color.BLACK);
        board[7][0] = new Rook(Color.WHITE);
        board[0][7] = new Rook(Color.BLACK);
        board[7][7] = new Rook(Color.WHITE);

        //Knights
        board[0][1] = new Knight(Color.BLACK);
        board[7][1] = new Knight(Color.WHITE);
        board[0][6] = new Knight(Color.BLACK);
        board[7][6] = new Knight(Color.WHITE);

        //Bishops
        board[0][2] = new Bishop(Color.BLACK);
        board[7][2] = new Bishop(Color.WHITE);
        board[0][5] = new Bishop(Color.BLACK);
        board[7][5] = new Bishop(Color.WHITE);

        //Queens
        board[0][3] = new Queen(Color.BLACK);
        board[7][3] = new Queen(Color.WHITE);
        //Kings

        board[0][4] = new King(Color.BLACK);
        board[7][4] = new King(Color.WHITE);

    }


    public Piece[][] getBoard() {
        return board;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    //clears the enPassant status of all opposing pawns after a turn
    private void clearEnPassant(Color color , Piece[][] board){
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board.length; col++){
                if(board[row][col]!= null){
                    if(board[row][col].getPlayerColor() == color && board[row][col].getClass() == Pawn.class){
                        ((Pawn)board[row][col]).setEnPassant(false);
                    }
                }
            }
        }
    }

    private void generateValidPawnMoves(int xCoordinate, int yCoordinate, Piece[][] board){

        int pawnX = xCoordinate;
        int pawnY = yCoordinate;
        Pawn pawn = (Pawn) board[yCoordinate][xCoordinate];

        Color pawnColor = pawn.getPlayerColor();
        boolean pawnFirstMove = pawn.getMovesMade() == 0;

        HashMap<Color,Integer> map = generateFactionMultiplier();

        int yMovement = pawnY + (1 * map.get(pawnColor));

        //Movement for pawn regardless of faction including en passant double move.
        if(yMovement <= 7 && yMovement >= 0){
            if(board[yMovement][pawnX] == null){
                Move move = new Move(pawn, xCoordinate, yCoordinate, pawnX , yMovement, pawnX, yMovement , false);
                pawn.setValidMovement(move);
                if(pawnFirstMove){
                    if(board[yMovement + (1 * map.get(pawnColor))][pawnX] == null){
                        move = new Move(pawn, xCoordinate, yCoordinate, pawnX , yMovement + (1 * map.get(pawnColor)) ,pawnX , yMovement + (1 * map.get(pawnColor)), false);
                        pawn.setValidMovement(move);
                    }
                }
            }
        }

        if(yMovement <= 7 && yMovement >= 0){
            if(pawnX - 1 >= 0){
                if(board[yMovement][pawnX -1] != null){
                    if(isEnemyFaction(pawn , board[yMovement][pawnX -1])){
                        Move move = new Move(pawn, xCoordinate, yCoordinate, pawnX - 1, yMovement,pawnX - 1, yMovement, true);
                        pawn.setValidMovement(move);
                    }
                }
            }
            if(pawnX + 1 <= 7){
                if(board[yMovement][pawnX +1] != null){
                    if(isEnemyFaction(pawn , board[yMovement][pawnX +1])){
                        Move move = new Move(pawn, xCoordinate, yCoordinate, pawnX +1, yMovement,pawnX +1, yMovement, true);
                        pawn.setValidMovement(move);
                    }
                }
            }
        }
        //en passant

        if(pawnX -1 >= 0){
            if(board[pawnY][pawnX-1] != null){
                if(board[pawnY][pawnX-1].getClass() == Pawn.class && isEnemyFaction(pawn , board[pawnY][pawnX-1])){
                    if(((Pawn)board[pawnY][pawnX-1]).isEnPassant()){
                        Move move = new Move(pawn, xCoordinate, yCoordinate, pawnX - 1, yMovement, pawnX-1,pawnY,true);
                        pawn.setValidMovement(move);
                    }
                }
            }
        }
        if(pawnX + 1 <= 7){
            if(board[pawnY][pawnX+1] != null){
                if(board[pawnY][pawnX+1].getClass() == Pawn.class && isEnemyFaction(pawn , board[pawnY][pawnX+1])){
                    if(((Pawn)board[pawnY][pawnX+1]).isEnPassant()){
                        Move move = new Move(pawn, xCoordinate, yCoordinate, pawnX + 1, yMovement,pawnX+1, pawnY, true);
                        pawn.setValidMovement(move);
                    }
                }
            }
        }
    }

    private void generateValidCardinalMovement(int xCoordinate, int yCoordinate, int movementLimit, Piece[][] board){

        int pieceX = xCoordinate;
        int pieceY = yCoordinate;
        Piece piece = board[yCoordinate][xCoordinate];

        int MAX_MOVEMENT_LIMIT = movementLimit;
        boolean[] cardinalDirections = new boolean[]{true,true,true,true};

        for(int action = 1; action <= MAX_MOVEMENT_LIMIT; action++){
            if(cardinalDirections[0] && (pieceX + action) <= 7){
                if(board[pieceY][pieceX + action] == null){
                    Move move = new Move(piece,xCoordinate, yCoordinate, pieceX + action, pieceY, pieceX + action, pieceY, false);
                    piece.setValidMovement(move);
                }
                else if(isEnemyFaction(piece ,board[pieceY][pieceX + action])){
                    Move move = new Move(piece,xCoordinate, yCoordinate, pieceX + action, pieceY, pieceX + action, pieceY, true);
                    move.getPiece().setValidMovement(move);
                    cardinalDirections[0] = false;
                }
                else cardinalDirections[0] = false;
            }
            if(cardinalDirections[1] && (pieceX - action) >= 0){
                if(board[pieceY][pieceX - action] == null){
                    Move move = new Move(piece,xCoordinate, yCoordinate, pieceX - action, pieceY, pieceX - action, pieceY, false);
                    piece.setValidMovement(move);
                }
                else if(isEnemyFaction(piece ,board[pieceY][pieceX - action])){
                    Move move = new Move(piece,xCoordinate, yCoordinate, pieceX - action, pieceY, pieceX - action, pieceY, true);
                    piece.setValidMovement(move);
                    cardinalDirections[1] = false;
                }
                else cardinalDirections[1] = false;
            }
            if(cardinalDirections[2] && (pieceY - action) >= 0){
                if(board[pieceY - action][pieceX] == null){
                    Move move = new Move(piece,xCoordinate, yCoordinate, pieceX, pieceY - action, pieceX, pieceY - action, false);
                    piece.setValidMovement(move);
                }
                else if(isEnemyFaction(piece ,board[pieceY - action][pieceX])){
                    Move move = new Move(piece,xCoordinate, yCoordinate, pieceX, pieceY - action, pieceX, pieceY - action, true);
                    piece.setValidMovement(move);
                    cardinalDirections[2] = false;
                }
                else cardinalDirections[2] = false;
            }
            if(cardinalDirections[3] && (pieceY + action) <= 7){
                if(board[pieceY + action][pieceX] == null){
                    Move move = new Move(piece,xCoordinate, yCoordinate, pieceX, pieceY + action, pieceX, pieceY + action, false);
                    piece.setValidMovement(move);
                }
                else if(isEnemyFaction(piece ,board[pieceY + action][pieceX])){
                    Move move = new Move(piece,xCoordinate, yCoordinate, pieceX, pieceY + action, pieceX, pieceY + action, true);
                    piece.setValidMovement(move);
                    cardinalDirections[3] = false;
                }
                else cardinalDirections[3] = false;
            }
        }
    }

    private void generateValidKnightMoves(int xCoordinate, int yCoordinate, Piece[][] board){

        int pieceX = xCoordinate;
        int pieceY = yCoordinate;
        Piece piece = board[yCoordinate][xCoordinate];

        int HORIZONTAL = 2;
        int VERTICAL = 1;

        if(pieceX + HORIZONTAL <= 7 && pieceY + VERTICAL <= 7){
            if(board[pieceY + VERTICAL][pieceX + HORIZONTAL] == null){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX + HORIZONTAL, pieceY + VERTICAL,false);
                piece.setValidMovement(move);
            }
            else if(isEnemyFaction(piece,board[pieceY + VERTICAL][pieceX + HORIZONTAL])){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX + HORIZONTAL, pieceY + VERTICAL,true);
                piece.setValidMovement(move);
            }
        }

        if(pieceX + VERTICAL <= 7 && pieceY + HORIZONTAL <= 7){
            if(board[pieceY + HORIZONTAL][pieceX + VERTICAL] == null){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX + VERTICAL, pieceY + HORIZONTAL,false);
                piece.setValidMovement(move);
            }
            else if(isEnemyFaction(piece,board[pieceY + HORIZONTAL][pieceX + VERTICAL])){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX + VERTICAL, pieceY + HORIZONTAL,true);
                piece.setValidMovement(move);
            }
        }

        if(pieceX - VERTICAL >= 0 && pieceY + HORIZONTAL <= 7){
            if(board[pieceY + HORIZONTAL][pieceX - VERTICAL] == null){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX - VERTICAL, pieceY + HORIZONTAL,false);
                piece.setValidMovement(move);
            }
            else if(isEnemyFaction(piece,board[pieceY + HORIZONTAL][pieceX - VERTICAL])){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX - VERTICAL, pieceY + HORIZONTAL,true);
                piece.setValidMovement(move);
            }
        }

        if(pieceX - HORIZONTAL >= 0 && pieceY + VERTICAL <= 7){
            if(board[pieceY + VERTICAL][pieceX - HORIZONTAL] == null){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX - HORIZONTAL, pieceY + VERTICAL,false);
                piece.setValidMovement(move);
            }
            else if(isEnemyFaction(piece,board[pieceY + VERTICAL][pieceX - HORIZONTAL])){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX - HORIZONTAL, pieceY + VERTICAL,true);
                piece.setValidMovement(move);
            }
        }

        if(pieceX + VERTICAL <= 7 && pieceY - HORIZONTAL >= 0){
            if(board[pieceY - HORIZONTAL][pieceX + VERTICAL] == null){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX + VERTICAL, pieceY - HORIZONTAL,false);
                piece.setValidMovement(move);
            }
            else if(isEnemyFaction(piece,board[pieceY - HORIZONTAL][pieceX + VERTICAL])){
                Move move =new Move(piece,xCoordinate, yCoordinate,pieceX + VERTICAL, pieceY - HORIZONTAL,true);
                piece.setValidMovement(move);
            }
        }

        if(pieceX + HORIZONTAL <= 7 && pieceY - VERTICAL >= 0){
            if(board[pieceY - VERTICAL][pieceX + HORIZONTAL] == null){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX + HORIZONTAL, pieceY - VERTICAL,false);
                piece.setValidMovement(move);
            }
            else if(isEnemyFaction(piece,board[pieceY - VERTICAL][pieceX + HORIZONTAL])){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX + HORIZONTAL, pieceY - VERTICAL,true);
                piece.setValidMovement(move);
            }
        }

        if(pieceX - VERTICAL >= 0 && pieceY - HORIZONTAL >= 0){
            if(board[pieceY - HORIZONTAL][pieceX - VERTICAL] == null){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX - VERTICAL, pieceY - HORIZONTAL,false);
                piece.setValidMovement(move);
            }
            else if(isEnemyFaction(piece,board[pieceY - HORIZONTAL][pieceX - VERTICAL])){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX - VERTICAL, pieceY - HORIZONTAL,true);
                piece.setValidMovement(move);
            }
        }

        if(pieceX - HORIZONTAL >= 0 && pieceY - VERTICAL >= 0){
            if(board[pieceY - VERTICAL][pieceX - HORIZONTAL] == null){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX - HORIZONTAL, pieceY - VERTICAL,false);
                piece.setValidMovement(move);
            }
            else if(isEnemyFaction(piece,board[pieceY - VERTICAL][pieceX - HORIZONTAL])){
                Move move = new Move(piece,xCoordinate, yCoordinate,pieceX - HORIZONTAL, pieceY - VERTICAL,true);
                piece.setValidMovement(move);
            }
        }
    }

    private void generateValidDiagonalMovement(int xCoordinate, int yCoordinate, int movementLimit,Piece[][] board){

        int pieceX = xCoordinate;
        int pieceY = yCoordinate;
        Piece piece = board[yCoordinate][xCoordinate];

        boolean[] directionStates = new boolean[]{true,true,true,true};

        for(int action = 1; action <= movementLimit; action++){
            if(directionStates[0]){
                if(pieceY + action <= 7 && pieceX + action <= 7){
                    if(board[pieceY + action][pieceX + action] == null){
                        Move move = new Move(piece,xCoordinate, yCoordinate, pieceX + action, pieceY + action, false);
                        piece.setValidMovement(move);
                    }
                    else if(isEnemyFaction(piece, board[pieceY + action][pieceX + action])){
                        Move move = new Move(piece,xCoordinate, yCoordinate, pieceX + action, pieceY + action, true);
                        piece.setValidMovement(move);
                        directionStates[0] = false;
                    }
                    else directionStates[0] = false;
                }
            }
            if(directionStates[1]){
                if(pieceY - action >= 0 && pieceX + action <= 7){
                    if(board[pieceY - action][pieceX + action] == null){
                        Move move = new Move(piece,xCoordinate, yCoordinate, pieceX + action, pieceY - action, false);
                        piece.setValidMovement(move);
                    }
                    else if(isEnemyFaction(piece, board[pieceY - action][pieceX + action])){
                        Move move = new Move(piece,xCoordinate, yCoordinate, pieceX + action, pieceY - action, true);
                        piece.setValidMovement(move);
                        directionStates[1] = false;
                    }
                    else directionStates[1] = false;
                }
            }
            if(directionStates[2]){
                if(pieceY - action >= 0 && pieceX - action >= 0){
                    if(board[pieceY - action][pieceX - action] == null){
                        Move move = new Move(piece,xCoordinate, yCoordinate, pieceX - action, pieceY - action, false);
                        piece.setValidMovement(move);
                    }
                    else if(isEnemyFaction(piece, board[pieceY - action][pieceX - action])){
                        Move move = new Move(piece,xCoordinate, yCoordinate, pieceX - action, pieceY - action, true);
                        piece.setValidMovement(move);
                        directionStates[2] = false;
                    }
                    else directionStates[2] = false;
                }
            }
            if(directionStates[3]){
                if(pieceY + action <= 7 && pieceX - action >= 0){
                    if(board[pieceY + action][pieceX - action] == null){
                        Move move = new Move(piece,xCoordinate, yCoordinate, pieceX - action, pieceY + action, false);
                        piece.setValidMovement(move);
                    }
                    else if(isEnemyFaction(piece, board[pieceY + action][pieceX - action])){
                        Move move = new Move(piece,xCoordinate, yCoordinate, pieceX - action, pieceY + action, true);
                        piece.setValidMovement(move);
                        directionStates[3] = false;
                    }
                    else directionStates[3] = false;
                }
            }
        }
    }

    private boolean isKingInCheck(int kingX , int kingY, Piece[][] boardCp , ArrayList<Move> enemyMoves){

        Color kingFaction = boardCp[kingY][kingX].getPlayerColor();

            for(Move enemyMove : enemyMoves){
                if(enemyMove.isCaptureMove()){
                    if((enemyMove.getNewCaptureY() == kingY) && (enemyMove.getNewCaptureX() == kingX)){
                        return true;
                    }
                }
            }
        return false;
    }



    public ArrayList<Move> returnOnlyValidMoves(Color moveColor , Piece[][] board){

        ArrayList<Move> allCurrentPlayerMoves = getAllFactionMoves(moveColor , board);
        ArrayList<Move>allValidMoves = new ArrayList<>();


        //verify the moves not resulting in check on the current players king
        for(Move move : allCurrentPlayerMoves){
            Piece[][] boardCp = createACopyOfBoard();
            //performs the move
            boardCp[move.getNewCaptureY()][move.getNewCaptureX()] = null;
            boardCp[move.getNewMovementY()][move.getNewMovementX()] = boardCp[move.getCurrentY()][move.getCurrentX()];
            boardCp[move.getCurrentY()][move.getCurrentX()] = null;


            if(move.getPiece().getClass() == Pawn.class){
                Pawn pawn = (Pawn) boardCp[move.getNewMovementY()][move.getNewMovementX()];
                pawn.setValue(pawn.getValue() + 1);
                if(pawn.getMovesMade() == 0 && (move.getNewMovementY() == 3 || move.getNewMovementY() == 4)){
                    pawn.setEnPassant(true);
                }
                pawn.incrementMove();
                if(move.getNewMovementY() == 0 || move.getNewMovementY() == 7){
                    boardCp[move.getNewMovementY()][move.getNewMovementX()] = new Queen(move.getPiece().getPlayerColor());
                }
            }


            clearEnPassant(returnOppositeFaction(moveColor), boardCp);
            int[] kingCoordinates = findTargetKing(moveColor , boardCp);
            ArrayList<Move> enemyMoves = getAllFactionMoves(returnOppositeFaction(moveColor), boardCp);
            if(!isKingInCheck(kingCoordinates[1] , kingCoordinates[0], boardCp , enemyMoves)){
                move.setBoardResultingFromMove(boardCp);
                allValidMoves.add(move);
            }
        }
        return allValidMoves;
    }



    private Piece[][] createACopyOfBoard(){
        Piece[][] boardCopy = new Piece[board.length][board.length];
        for(int pieceY = 0; pieceY < board.length; pieceY++){
            for(int pieceX = 0; pieceX < board.length; pieceX++){
                if(board[pieceY][pieceX]!= null){
                    boardCopy[pieceY][pieceX] = (Piece) board[pieceY][pieceX].clone();
                }
            }
        }
        return boardCopy;
    }



    private int[] findTargetKing(Color color, Piece[][] board){
        for(int pieceY = 0; pieceY < board.length; pieceY++){
            for(int pieceX = 0; pieceX < board.length; pieceX++){
                if(board[pieceY][pieceX]!= null){
                    if(board[pieceY][pieceX].getClass() == King.class && board[pieceY][pieceX].getPlayerColor() == color){
                        return new int[]{pieceY,pieceX};
                    }
                }
            }
        }
        return null;
    }



    private ArrayList<Move> getAllFactionMoves(Color color , Piece[][] board){
        ArrayList<Move> allMoves = new ArrayList<>();

        for(int pieceY = 0; pieceY < board.length; pieceY++){
            for(int pieceX = 0; pieceX < board.length; pieceX++){
                if(board[pieceY][pieceX]!= null){
                    if(board[pieceY][pieceX].getPlayerColor() == color){
                        moveValidator(pieceX , pieceY , board);
                        for(Move move : board[pieceY][pieceX].getValidMovement()){
                            allMoves.add(move);
                        }
                    }
                }
            }
        }
        return allMoves;
    }

    private boolean isEnemyFaction(Piece first, Piece second){
        if(first.getPlayerColor()==Color.WHITE && !(second.getPlayerColor()==Color.WHITE)|| !(first.getPlayerColor()==Color.WHITE) && second.getPlayerColor()==Color.WHITE){
            return true;
        }
        return false;
    }

    private Color returnOppositeFaction(Color color){
        if(color == Color.WHITE){
            return Color.BLACK;
        }
        else{
            return Color.WHITE;
        }
    }


}
