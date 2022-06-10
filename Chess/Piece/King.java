package R4.Chess.Piece;

import R4.Chess.GUI.Color;

public class King extends Piece{


    private boolean isChecked = false;

    public King(Color color) {
        super(color,"PieceImageData/Chess_klt60.png","PieceImageData/Chess_kdt60.png");
        this.setValue(100);
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }
}
