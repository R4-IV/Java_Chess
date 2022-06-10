package R4.Chess;


import R4.Chess.GUI.Color;
import R4.Chess.Piece.Move;
import R4.Chess.Piece.Piece;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    //creates initial board
    private Board board = new Board();

    private int turnNum = 0;

    private boolean playerPieceSelected = false;
    ArrayList<Move> playerMoves = board.returnOnlyValidMoves(Color.WHITE , getCurrentBoard());
    ArrayList<Move> specificPieceMoves = new ArrayList<>();

    public Game(){

    }

    public Board getBoardObject() {
        return board;
    }

    public Piece[][] getCurrentBoard(){
        return board.getBoard();
    }

    public boolean isPlayerPieceSelected() {
        return playerPieceSelected;
    }

    public void setPlayerPieceSelected(boolean playerPieceSelected) {
        this.playerPieceSelected = playerPieceSelected;
    }

    public ArrayList<Move> makeChessTurn(int xCoordinate, int yCoordinate){

        if(!isPlayerPieceSelected()){
            if(getCurrentBoard()[yCoordinate][xCoordinate]!=null){
                if(getCurrentBoard()[yCoordinate][xCoordinate].getPlayerColor() == Color.WHITE){
                    for(Move move: playerMoves){
                        if(move.getCurrentX() == xCoordinate && move.getCurrentY() == yCoordinate){
                            specificPieceMoves.add(move);
                        }
                    }
                }
            }
        }
        else if(isPlayerPieceSelected()){
            for(Move move: specificPieceMoves){
                if(move.getNewMovementY() == yCoordinate && move.getNewMovementX() == xCoordinate){
                    board.setBoard(move.getBoardResultingFromMove());
                    ArrayList<Move> aiMove = board.returnOnlyValidMoves(Color.BLACK , getCurrentBoard());
                    if(aiMove.size() == 0){
                        System.out.println("Checkmate");
                        return null;
                    }
                    minimaxAI Ai = new minimaxAI(this);
                    if(Ai.performMinimax(getCurrentBoard(),6,-1000000, 1000000,true)== 0){
                        board.setBoard(aiMove.get((int)(Math.random() * aiMove.size())).getBoardResultingFromMove());
                    }
                    else{
                        board.setBoard(Ai.getMaximisedBoard());
                    }
                    playerMoves = board.returnOnlyValidMoves(Color.WHITE , getCurrentBoard());
                }
            }
            specificPieceMoves.clear();
            playerPieceSelected = false;

        }
        return specificPieceMoves;
    }
}
