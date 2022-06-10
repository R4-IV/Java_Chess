package R4.Chess.Piece;

import R4.Chess.GUI.Color;

public class Bishop extends Piece{
    public Bishop(Color color) {
        super(color, "PieceImageData/Chess_blt60.png", "PieceImageData/Chess_bdt60.png");
        this.setValue(80);
    }
}
