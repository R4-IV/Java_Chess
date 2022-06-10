package R4.Chess.Piece;

import R4.Chess.GUI.Color;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public abstract class Piece implements Cloneable{

    private Color color;
    private Image whiteFactionImage;
    private Image blackFactionImage;
    private int value;

    private ArrayList<Move> validMovement;

    public Piece(Color color , String whiteString , String blackString){
        this.color = color;
        setImageValues(whiteString , blackString);
        validMovement = new ArrayList<Move>();
    }

    public void setWhiteFactionImage(Image whiteFactionImage) {
        this.whiteFactionImage = whiteFactionImage;
    }

    public void setBlackFactionImage(Image blackFactionImage) {
        this.blackFactionImage = blackFactionImage;
    }

    public Color getPlayerColor(){
        return color;
    }

    public ArrayList<Move> getValidMovement() {
        return validMovement;
    }

    public int getValue() {
        return value;
    }

    public Image getBlackFactionImage() {
        return blackFactionImage;
    }

    public Image getWhiteFactionImage() {
        return whiteFactionImage;
    }

    public void setValidMovement(Move addMove) {
        this.validMovement.add(addMove);
    }

    public void clearArrayList(){
        validMovement.clear();
    }

    public void setValue(int value) {
        this.value = value;
    }

    private void setImageValues(String whiteString, String blackString){
        FileInputStream blackInput = null;
        FileInputStream whiteInput = null;
        try {
            whiteInput = new FileInputStream(whiteString);
            blackInput = new FileInputStream(blackString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setWhiteFactionImage(new Image(whiteInput));
        setBlackFactionImage(new Image(blackInput));
    }

    public Piece clone(){
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
