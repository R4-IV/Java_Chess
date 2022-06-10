package R4.Chess.Piece;

import R4.Chess.GUI.Color;

public class Rook extends Piece{

    public Rook(Color color) {
        super(color,"PieceImageData/Chess_rlt60.png" , "PieceImageData/Chess_rdt60.png");
        this.setValue(80);
    }
}
