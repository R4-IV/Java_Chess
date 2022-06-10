package R4;

import R4.Chess.GUI.CustomCanvas;
import javafx.stage.Stage;
import org.sintef.jarduino.JArduino;

import static javafx.application.Application.launch;

public class Main {

    public static void main(String[] args) {
        //JArduino jArduino = new Arduino("/dev/cu.usbmodem14401");
        //jArduino.runArduinoProcess();
        launch(CustomCanvas.class);


    }
}
