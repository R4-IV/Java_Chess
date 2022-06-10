package R4;

import org.sintef.jarduino.*;

public class Arduino extends JArduino{

    private DigitalPin stepPin = DigitalPin.PIN_8;
    private DigitalPin dirPin = DigitalPin.PIN_9;
    private long PULSE_WIDTH = 1;


    public Arduino(String port) {
        super(port);
    }


    @Override
    protected void setup() {
        pinMode(stepPin , PinMode.INPUT);
        pinMode(dirPin , PinMode.INPUT);
    }

    private void rotateMotor(long speed, DigitalPin step, DigitalPin dir, int numOfSteps , DigitalState direction){
        //sets rotation
        digitalWrite(dir , direction);

        for(int steps = 0; steps < numOfSteps; steps++){
            digitalWrite(step , DigitalState.HIGH);
            delay(300);
            digitalWrite(step , DigitalState.LOW);
            delay(100);
        }
    }

    @Override
    protected void loop() {
        digitalWrite(stepPin , DigitalState.HIGH);
        delay(300);
        digitalWrite(stepPin , DigitalState.LOW);
        delay(100);

        rotateMotor(2 , stepPin , dirPin , 2000 , DigitalState.HIGH);
    }
}
