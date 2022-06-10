package R4.Chess.Piece;

import R4.Chess.GUI.Color;

public class Pawn extends Piece {

    private int movesMade = 0;
    private boolean enPassant = false;

    public Pawn(Color color) {
        super(color, "PieceImageData/Chess_plt60.png" ,"PieceImageData/Chess_pdt60.png");
        this.setValue(20);
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void incrementMove(){
        movesMade = 1;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }


}
