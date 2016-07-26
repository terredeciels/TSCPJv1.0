package tscp;

public interface Constants {

    public final int TRUE = 1;
    public final int FALSE = 0;
    public final int GEN_STACK = 1120;
    public final int MAX_PLY = 32;
    public final int HIST_STACK = 400;
    public final int LIGHT = 0;
    public final int DARK = 1;
    public final int PAWN = 0;
    public final int KNIGHT = 1;
    public final int BISHOP = 2;
    public final int ROOK = 3;
    public final int QUEEN = 4;
    public final int KING = 5;
    public final int EMPTY = 6;
    public final int A1 = 56;
    public final int B1 = 57;
    public final int C1 = 58;
    public final int D1 = 59;
    public final int E1 = 60;
    public final int F1 = 61;
    public final int G1 = 62;
    public final int H1 = 63;
    public final int A8 = 0;
    public final int B8 = 1;
    public final int C8 = 2;
    public final int D8 = 3;
    public final int E8 = 4;
    public final int F8 = 5;
    public final int G8 = 6;
    public final int H8 = 7;

    public int[] mailbox = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, -1, -1, 8, 9, 10, 11, 12, 13, 14, 15, -1, -1, 16, 17, 18, 19, 20, 21, 22, 23, -1, -1, 24, 25, 26, 27, 28, 29, 30, 31, -1, -1, 32, 33, 34, 35, 36, 37, 38, 39, -1, -1, 40, 41, 42, 43, 44, 45, 46, 47, -1, -1, 48, 49, 50, 51, 52, 53, 54, 55, -1, -1, 56, 57, 58, 59, 60, 61, 62, 63, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    public int[] mailbox64 = {21, 22, 23, 24, 25, 26, 27, 28, 31, 32, 33, 34, 35, 36, 37, 38, 41, 42, 43, 44, 45, 46, 47, 48, 51, 52, 53, 54, 55, 56, 57, 58, 61, 62, 63, 64, 65, 66, 67, 68, 71, 72, 73, 74, 75, 76, 77, 78, 81, 82, 83, 84, 85, 86, 87, 88, 91, 92, 93, 94, 95, 96, 97, 98};

    public int[] slide = {FALSE, FALSE, TRUE, TRUE, TRUE, FALSE};

    public int[] offsets = {0, 8, 4, 4, 8, 8};

    public int[][] offset
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
    public int[] castle_mask = {7, 15, 15, 15, 3, 15, 15, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 13, 15, 15, 15, 12, 15, 15, 14};

    public String[] piece_char_light = {"P", "N", "B", "R", "Q", "K"};
    public String[] piece_char_dark = {"p", "n", "b", "r", "q", "k"};

    /* the initial board state */
    public int[] init_color = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public int[] init_piece = {3, 1, 2, 4, 5, 2, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 2, 4, 5, 2, 1, 3};

}
