package R4.Chess.Piece;

import R4.Chess.GUI.Color;

public class Queen extends Piece{
    public Queen(Color color) {
        super(color,"PieceImageData/Chess_qlt60.png", "PieceImageData/Chess_qdt60.png");
        this.setValue(300);
    }
}
