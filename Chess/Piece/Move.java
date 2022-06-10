package R4.Chess.Piece;

import R4.Chess.GUI.Color;

public class Move {

    Piece piece;

    Piece[][] resultingBoard;

    private int newMovementX;
    private int newMovementY;
    private int newCaptureX;
    private int newCaptureY;
    private Piece[][] boardResultingFromMove;

    private int currentX;
    private int currentY;

    private int moveValue = 0;

    private boolean capture;


    public Move(Piece piece ,int currentX, int currentY, int newMovementX, int newMovementY, int newCaptureX ,int newCaptureY, boolean capture){
        this.piece = piece;
        this.newMovementX = newMovementX;
        this.newMovementY = newMovementY;
        this.newCaptureX = newCaptureX;
        this.newCaptureY = newCaptureY;
        this.currentX = currentX;
        this.currentY = currentY;
        this.capture = capture;
    }

    public Move(Piece piece,int currentX, int currentY, int newMovementX ,int newMovementY, boolean capture){
        this.piece = piece;
        this.newMovementX = newMovementX;
        this.newMovementY = newMovementY;
        this.newCaptureX = newMovementX;
        this.newCaptureY = newMovementY;
        this.currentX = currentX;
        this.currentY = currentY;
        this.capture = capture;
    }

    public int getNewMovementY() {
        return newMovementY;
    }

    public int getNewMovementX() {
        return newMovementX;
    }

    public int getNewCaptureX() {
        return newCaptureX;
    }

    public int getNewCaptureY() {
        return newCaptureY;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public boolean isCaptureMove() {
        return capture;
    }

    public void setBoardResultingFromMove(Piece[][] boardResultingFromMove) {
        this.boardResultingFromMove = boardResultingFromMove;
    }

    public Piece[][] getBoardResultingFromMove() {
        return boardResultingFromMove;
    }



    public void setMoveValue(int moveValue) {
        this.moveValue = moveValue;
    }
}
