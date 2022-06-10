package R4.Chess.GUI;

import R4.Chess.Game;
import R4.Chess.Piece.Move;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;


public class CustomCanvas extends Application {

    private int SCENE_WIDTH = 650;
    private int SCENE_HEIGHT = 650;
    private int SQ_SIZE = 80;

    Game game = new Game();
    ArrayList<Move> pieceValidMoves;



    Canvas canvas = new Canvas();
    GraphicsContext gc = canvas.getGraphicsContext2D();

    private void drawBoard(){
        //draw the light squares first
        int leftOffset = 5;
        int topOffset = 5;
        int pieceX = 0;
        int pieceY = 0;

        int horizontalSqOffset = leftOffset;
        int verticalSqOffset = topOffset;

        int NUM_OF_SQUARES_PER_COLOR = 64;
        int numOfSqInRow = 0;
        boolean blackSq = false;

        for(int wSquare = 0; wSquare < NUM_OF_SQUARES_PER_COLOR; wSquare++){

            if(blackSq){
                blackSq = false;
                gc.setFill(Color.valueOf("#5c4d42"));
            }
            else{
                blackSq = true;
                gc.setFill(Color.valueOf("#f0dfa1"));
            }
            gc.fillRect(horizontalSqOffset , verticalSqOffset , SQ_SIZE , SQ_SIZE);

            if(game.getCurrentBoard()[pieceY][pieceX]!= null){
                if(game.getCurrentBoard()[pieceY][pieceX].getPlayerColor() == R4.Chess.GUI.Color.WHITE){
                    gc.drawImage(game.getCurrentBoard()[pieceY][pieceX].getWhiteFactionImage(), horizontalSqOffset + 10  , verticalSqOffset + 10 , 60 , 60);
                }
                else{
                    gc.drawImage(game.getCurrentBoard()[pieceY][pieceX].getBlackFactionImage(), horizontalSqOffset + 10  , verticalSqOffset + 10 , 60 , 60);
                }
            }

            horizontalSqOffset += SQ_SIZE;
            numOfSqInRow++;
            pieceX++;

            if(numOfSqInRow == 8){
                numOfSqInRow = 0;
                verticalSqOffset += SQ_SIZE;
                horizontalSqOffset = leftOffset;
                blackSq = !blackSq;
                pieceY++;
                pieceX = 0;
                }
            }
        }

        private void drawOutline(int xOffset, int yOffset){
        gc.setLineWidth(5.0);
        gc.setStroke(Color.RED);
        gc.strokeRect(xOffset + 10, yOffset + 10 ,SQ_SIZE -20 , SQ_SIZE -20);
        }

        private void clear(){
        gc.clearRect(0 , 0, canvas.getWidth() , canvas.getHeight());
        }


    public void start(Stage stage) {

        //Sets title of the stage object
        stage.setTitle("Chess GUI");

        // set height and width
        canvas.setHeight(SCENE_HEIGHT);
        canvas.setWidth(SCENE_WIDTH);

        drawBoard();

        // create a Group
        Group group = new Group(canvas);


        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {


                clear();
                drawBoard();

                int xArr = (int)(event.getX()/SQ_SIZE);
                int yArr = (int)(event.getY()/SQ_SIZE);


                if(game.isPlayerPieceSelected() == true){
                    game.makeChessTurn(xArr,yArr);
                    clear();
                    drawBoard();
                }
                else if(game.isPlayerPieceSelected() == false){
                    pieceValidMoves = game.makeChessTurn(xArr,yArr);
                    if(pieceValidMoves != null){
                        game.setPlayerPieceSelected(true);
                        clear();
                        drawBoard();
                        for(int move = 0; move < pieceValidMoves.size(); move++){
                            drawOutline(5 + pieceValidMoves.get(move).getNewMovementX() * SQ_SIZE, 5 + pieceValidMoves.get(move).getNewMovementY() * SQ_SIZE);
                        }
                    }
                    else{
                        game.setPlayerPieceSelected(false);
                    }
                }
            }
        }
        );
        // create a scene
        Scene scene = new Scene(group, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setResizable(true);

        // set the scene
        stage.setScene(scene);

        stage.show();
    }
}
