package R4.Chess.Piece;

import R4.Chess.GUI.Color;

public class Knight extends Piece{
    public Knight(Color color) {
        super(color,"PieceImageData/Chess_nlt60.png", "PieceImageData/Chess_ndt60.png");
        this.setValue(70);
    }
}
