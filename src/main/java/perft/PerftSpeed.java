package perft;

import tools.FenToBoard;
import perft.PerftCompare.PerftResult;
import tscp.Board;

import java.io.IOException;
import java.util.List;

import static tscp.Constants.TRUE;
import tscp.Move;

public class PerftSpeed {

    public static void main(String[] args) throws IOException {
        perftTest();
    }

    private static void perftTest() {
        //voir http://chessprogramming.wikispaces.com/Perft+Results     
        String f = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        Board board = FenToBoard.toBoard(f);
        int max_depth = 6;
        double t0 = System.nanoTime();
        for (int depth = 1; depth <= max_depth; depth++) {
            PerftResult res = perft(new Board(board), depth);
            double t1 = System.nanoTime();
            System.out.println("Depth " + depth + " : " + (t1 - t0) / 1000000000 + " sec");
            System.out.println("Count = " + res.moveCount);
        }
//        res = perft(board, 2);
//        res = perft(board, 3);
//        res = perft(board, 4);
//        res = perft(board, 5);
//        res = perft(board, 6);
    }

    private static PerftResult perft(Board board, int depth) {

        PerftResult result = new PerftResult();
        if (depth == 0) {
            result.moveCount++;
            return result;
        }
        board.gen();
        List<Move> moves = board.pseudomoves;
        for (Move move : moves) {
            if (board.makemove(move) == TRUE) {
                PerftResult subPerft = perft(new Board(board), depth - 1);
                board.takeback();
                result.moveCount += subPerft.moveCount;
            }
        }
        return result;
    }
}
