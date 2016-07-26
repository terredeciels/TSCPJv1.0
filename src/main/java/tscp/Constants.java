package tscp;

public interface Constants {

    int TRUE = 1;
    int FALSE = 0;
    int LIGHT = 0;
    int DARK = 1;
    int PAWN = 0;
    int KNIGHT = 1;
    int BISHOP = 2;
    int ROOK = 3;
    int QUEEN = 4;
    int KING = 5;
    int EMPTY = 6;
    int A1 = 56;
    int B1 = 57;
    int C1 = 58;
    int D1 = 59;
    int E1 = 60;
    int F1 = 61;
    int G1 = 62;
    int H1 = 63;
    int A8 = 0;
    int B8 = 1;
    int C8 = 2;
    int D8 = 3;
    int E8 = 4;
    int F8 = 5;
    int G8 = 6;
    int H8 = 7;

    int[] mailbox = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, -1, -1, 8, 9, 10, 11, 12, 13, 14, 15, -1, -1, 16, 17, 18, 19, 20, 21, 22, 23, -1, -1, 24, 25, 26, 27, 28, 29, 30, 31, -1, -1, 32, 33, 34, 35, 36, 37, 38, 39, -1, -1, 40, 41, 42, 43, 44, 45, 46, 47, -1, -1, 48, 49, 50, 51, 52, 53, 54, 55, -1, -1, 56, 57, 58, 59, 60, 61, 62, 63, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    int[] mailbox64 = {21, 22, 23, 24, 25, 26, 27, 28, 31, 32, 33, 34, 35, 36, 37, 38, 41, 42, 43, 44, 45, 46, 47, 48, 51, 52, 53, 54, 55, 56, 57, 58, 61, 62, 63, 64, 65, 66, 67, 68, 71, 72, 73, 74, 75, 76, 77, 78, 81, 82, 83, 84, 85, 86, 87, 88, 91, 92, 93, 94, 95, 96, 97, 98};

    int[] slide = {FALSE, FALSE, TRUE, TRUE, TRUE, FALSE};

    int[] offsets = {0, 8, 4, 4, 8, 8};

    int[][] offset
            = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {-21, -19, -12, -8, 8, 12, 19, 21},
            {-11, -9, 9, 11, 0, 0, 0, 0},
            {-10, -1, 1, 10, 0, 0, 0, 0},
            {-11, -10, -9, -1, 1, 9, 10, 11},
            {-11, -10, -9, -1, 1, 9, 10, 11}
    };


    /* This is the castle_mask array. We can use it to determine
       the castling permissions after a move. What we do is
	   logical-AND the castle bits with the castle_mask bits for
	   both of the move's squares. Let's say castle is 1, meaning
	   that white can still castle kingside. Now we play a move
	   where the rook on h1 gets captured. We AND castle with
	   castle_mask[63], so we have 1&14, and castle becomes 0 and
	   white can't castle kingside anymore. */
    int[] castle_mask = {7, 15, 15, 15, 3, 15, 15, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 13, 15, 15, 15, 12, 15, 15, 14};

    String[] piece_char_light = {"P", "N", "B", "R", "Q", "K"};
    String[] piece_char_dark = {"p", "n", "b", "r", "q", "k"};

    /* the initial board state */
    int[] init_color = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    int[] init_piece = {3, 1, 2, 4, 5, 2, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 2, 4, 5, 2, 1, 3};

}
