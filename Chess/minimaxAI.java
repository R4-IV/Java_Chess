package R4.Chess;

import R4.Chess.GUI.Color;
import R4.Chess.Piece.Move;
import R4.Chess.Piece.Piece;

import java.util.ArrayList;

public class minimaxAI {

    Game game;
    public minimaxAI(Game game){
        this.game = game;
    }

    Piece[][] maximisedBoard;

    public int performMinimax(Piece[][] board, int depth, int alpha , int beta , boolean maximisingPlayer){
        if(depth == 0){
            return evaluateBoard(board);
        }

        if(maximisingPlayer){
            int maxEvaluation = -1000000;
            ArrayList<Move> validMoves = game.getBoardObject().returnOnlyValidMoves(Color.BLACK , board);
            if(validMoves == null){
                return 0;
            }
            for(int position = 0; position < validMoves.size(); position++){
                int evaluation = performMinimax(validMoves.get(position).getBoardResultingFromMove(), depth-1,alpha,beta, false);
                if(maxEvaluation < evaluation){
                    maxEvaluation = evaluation;
                    maximisedBoard = validMoves.get(position).getBoardResultingFromMove();
                }
                alpha = Math.max(alpha , evaluation);
                if(beta <= alpha){
                    break;
                }
            }
            return maxEvaluation;
        }
        else{
            int minEvaluation = 1000000;
            ArrayList<Move> validMoves = game.getBoardObject().returnOnlyValidMoves(Color.WHITE , board);
            if(validMoves == null){
                return 0;
            }
            for(Move position : validMoves){
                int evaluation = performMinimax(position.getBoardResultingFromMove(), depth-1,alpha,beta, true);
                minEvaluation = Math.min(minEvaluation , evaluation);
                beta = Math.min(beta , evaluation);
                if(beta <= alpha){
                    break;
                }
            }
            return minEvaluation;
        }
    }

    private int evaluateBoard(Piece[][] board){
        int whiteTotal = 0;
        int blackTotal = 0;
        for(int yCoordinate = 0; yCoordinate < board.length; yCoordinate++){
            for(int xCoordinate = 0; xCoordinate < board.length; xCoordinate++){
                if(board[yCoordinate][xCoordinate]!=null){
                    if(board[yCoordinate][xCoordinate].getPlayerColor() == Color.WHITE){
                        whiteTotal += board[yCoordinate][xCoordinate].getValue();
                    }
                    else{
                        blackTotal+= board[yCoordinate][xCoordinate].getValue();
                    }
                }
            }
        }
        return blackTotal - whiteTotal;
    }

    public Piece[][] getMaximisedBoard() {
        return maximisedBoard;
    }
}
