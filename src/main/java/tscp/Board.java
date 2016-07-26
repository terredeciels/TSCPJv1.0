package tscp;

import java.util.ArrayList;
import java.util.List;

public class Board implements Constants {

    public int[] color = new int[64]; // LIGHT, DARK, or EMPTY
    public int[] piece = new int[64]; // PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING, or EMPTY
    public int side; // the side to move
    public int xside; // the side not to move
    public int castle;
    /**
     * a bitfield with the castle permissions. if 1 is set, white can still
     * castle kingside. 2 is white queenside. 4 is black kingside. 8 is black
     * queenside.
     */
    public int ep;
    public List<Move> pseudomoves = new ArrayList<>();
    private int fifty;
    private UndoMove um = new UndoMove();

    public Board() {
//        init_board();
    }

    /**
     * public Board copy() { Board board = new Board(); board.color = color;
     * board.piece = piece; board.side = side; board.xside = xside; board.castle
     * = castle; board.ep = ep; board.fifty = fifty; board.gen_dat = new
     * ArrayList<>(); board.hist_dat = new UndoMove(); return board; }
     */
    public Board(Board board) {
        color = board.color;
        piece = board.piece;
        side = board.side;
        xside = board.xside;
        castle = board.castle;
        ep = board.ep;
        fifty = board.fifty;
        pseudomoves = new ArrayList<>();
        um = new UndoMove();
    }
//
//    public static String move_str(Move m) {
//        String str;
//        byte c;
//
//        if ((m.bits & 32) != 0) {
//            switch (m.promote) {
//                case KNIGHT:
//                    c = (byte) 'n';
//                    break;
//                case BISHOP:
//                    c = (byte) 'b';
//                    break;
//                case ROOK:
//                    c = (byte) 'r';
//                    break;
//                default:
//                    c = (byte) 'q';
//                    break;
//            }
//            str = String.format("%c%d%c%d%c", (m.from & 7) + 'a', 8 - (m.from >> 3), (m.to & 7) + 'a', 8 - (m.to >> 3), c);
//        } else {
//            str = String.format("%c%d%c%d", (m.from & 7) + 'a', 8 - (m.from >> 3), (m.to & 7) + 'a', 8 - (m.to >> 3));
//        }
//        return str;
//    }

    /**
     * public final void init_board() { pseudomoves = new ArrayList<>(); um =
     * new UndoMove(); for (int i = 0; i < 64; ++i) { color[i] = init_color[i];
     * piece[i] = init_piece[i]; } side = LIGHT; xside = DARK; castle = 15; ep =
     * -1; fifty = 0; } @param s @return
     */
    private int in_check(int s) {
        int i;

        for (i = 0; i < 64; ++i) {
            if (piece[i] == KING && color[i] == s) {
                return attack(i, s ^ 1);
            }
        }
        return TRUE; // shouldn't get here
    }

    private int attack(int sq, int s) {
        int i;
        int j;
        int n;

        for (i = 0; i < 64; ++i) {
            if (color[i] == s) {
                if (piece[i] == PAWN) {
                    if (s == LIGHT) {
                        if ((i & 7) != 0 && i - 9 == sq) {
                            return TRUE;
                        }
                        if ((i & 7) != 7 && i - 7 == sq) {
                            return TRUE;
                        }
                    } else {
                        if ((i & 7) != 0 && i + 7 == sq) {
                            return TRUE;
                        }
                        if ((i & 7) != 7 && i + 9 == sq) {
                            return TRUE;
                        }
                    }
                } else {
                    for (j = 0; j < offsets[piece[i]]; ++j) {
                        for (n = i; ; ) {
                            n = mailbox[mailbox64[n] + offset[piece[i]][j]];
                            if (n == -1) {
                                break;
                            }
                            if (n == sq) {
                                return TRUE;
                            }
                            if (color[n] != EMPTY) {
                                break;
                            }
                            if (slide[piece[i]] == FALSE) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return FALSE;
    }

    public void gen() {
        int i;
        int j;
        int n;

        for (i = 0; i < 64; ++i) {
            if (color[i] == side) {
                if (piece[i] == PAWN) {
                    if (side == LIGHT) {
                        if ((i & 7) != 0 && color[i - 9] == DARK) {
                            gen_push(i, i - 9, 17);
                        }
                        if ((i & 7) != 7 && color[i - 7] == DARK) {
                            gen_push(i, i - 7, 17);
                        }
                        if (color[i - 8] == EMPTY) {
                            gen_push(i, i - 8, 16);
                            if (i >= 48 && color[i - 16] == EMPTY) {
                                gen_push(i, i - 16, 24);
                            }
                        }
                    } else {
                        if ((i & 7) != 0 && color[i + 7] == LIGHT) {
                            gen_push(i, i + 7, 17);
                        }
                        if ((i & 7) != 7 && color[i + 9] == LIGHT) {
                            gen_push(i, i + 9, 17);
                        }
                        if (color[i + 8] == EMPTY) {
                            gen_push(i, i + 8, 16);
                            if (i <= 15 && color[i + 16] == EMPTY) {
                                gen_push(i, i + 16, 24);
                            }
                        }
                    }
                } else {
                    for (j = 0; j < offsets[piece[i]]; ++j) {
                        for (n = i; ; ) {
                            n = mailbox[mailbox64[n] + offset[piece[i]][j]];
                            if (n == -1) {
                                break;
                            }
                            if (color[n] != EMPTY) {
                                if (color[n] == xside) {
                                    gen_push(i, n, 1);
                                }
                                break;
                            }
                            gen_push(i, n, 0);
                            if (slide[piece[i]] == FALSE) {
                                break;
                            }
                        }
                    }
                }
            }
        }

        /* generate castle moves */
        if (side == LIGHT) {
            if ((castle & 1) != 0) {
                gen_push(E1, G1, 2);
            }
            if ((castle & 2) != 0) {
                gen_push(E1, C1, 2);
            }
        } else {
            if ((castle & 4) != 0) {
                gen_push(E8, G8, 2);
            }
            if ((castle & 8) != 0) {
                gen_push(E8, C8, 2);
            }
        }

        /* generate en passant moves */
        if (ep != -1) {
            if (side == LIGHT) {
                if ((ep & 7) != 0 && color[ep + 7] == LIGHT && piece[ep + 7] == PAWN) {
                    gen_push(ep + 7, ep, 21);
                }
                if ((ep & 7) != 7 && color[ep + 9] == LIGHT && piece[ep + 9] == PAWN) {
                    gen_push(ep + 9, ep, 21);
                }
            } else {
                if ((ep & 7) != 0 && color[ep - 9] == DARK && piece[ep - 9] == PAWN) {
                    gen_push(ep - 9, ep, 21);
                }
                if ((ep & 7) != 7 && color[ep - 7] == DARK && piece[ep - 7] == PAWN) {
                    gen_push(ep - 7, ep, 21);
                }
            }
        }
    }

    private void gen_push(int from, int to, int bits) {
        if ((bits & 16) != 0) {
            if (side == LIGHT) {
                if (to <= H8) {
                    gen_promote(from, to, bits);
                    return;
                }
            } else if (to >= A1) {
                gen_promote(from, to, bits);
                return;
            }
        }
//        Move g = new Move();
//        g.from = (byte) from;
//        g.to = (byte) to;
//        g.promote = 0;
//        g.bits = (byte) bits;
//        pseudomoves.add(g);
        pseudomoves.add(new Move((byte) from, (byte) to, (byte) 0, (byte) bits));

    }

    private void gen_promote(int from, int to, int bits) {
        for (int i = KNIGHT; i <= QUEEN; ++i) {
//            Move g = new Move();
//            g.from = (byte) from;
//            g.to = (byte) to;
//            g.promote = (byte) i;
//            g.bits = (byte) (bits | 32);
//            pseudomoves.add(g);
            pseudomoves.add(new Move((byte) from, (byte) to, (byte) i, (byte) (bits | 32)));
        }
    }

    public int makemove(Move m) {
        if ((m.bits & 2) != 0) {
            int from;
            int to;

            if (in_check(side) != 0) {
                return FALSE;
            }
            switch (m.to) {
                case 62:
                    if (color[F1] != EMPTY || color[G1] != EMPTY || attack(F1, xside) != 0 || attack(G1, xside) != 0) {
                        return FALSE;
                    }
                    from = H1;
                    to = F1;
                    break;
                case 58:
                    if (color[B1] != EMPTY || color[C1] != EMPTY || color[D1] != EMPTY || attack(C1, xside) != 0 || attack(D1, xside) != 0) {
                        return FALSE;
                    }
                    from = A1;
                    to = D1;
                    break;
                case 6:
                    if (color[F8] != EMPTY || color[G8] != EMPTY || attack(F8, xside) != 0 || attack(G8, xside) != 0) {
                        return FALSE;
                    }
                    from = H8;
                    to = F8;
                    break;
                case 2:
                    if (color[B8] != EMPTY || color[C8] != EMPTY || color[D8] != EMPTY || attack(C8, xside) != 0 || attack(D8, xside) != 0) {
                        return FALSE;
                    }
                    from = A8;
                    to = D8;
                    break;
                default: // shouldn't get here
                    from = -1;
                    to = -1;
                    break;
            }
            color[to] = color[from];
            piece[to] = piece[from];
            color[from] = EMPTY;
            piece[from] = EMPTY;
        }

        /* back up information so we can take the move back later. */
        um.mov = m;
        um.capture = piece[(int) m.to];
        um.castle = castle;
        um.ep = ep;
        um.fifty = fifty;

        castle &= castle_mask[(int) m.from] & castle_mask[(int) m.to];

        if ((m.bits & 8) != 0) {
            if (side == LIGHT) {
                ep = m.to + 8;
            } else {
                ep = m.to - 8;
            }
        } else {
            ep = -1;
        }
        if ((m.bits & 17) != 0) {
            fifty = 0;
        } else {
            ++fifty;
        }

        /* move the piece */
        color[(int) m.to] = side;
        if ((m.bits & 32) != 0) {
            piece[(int) m.to] = m.promote;
        } else {
            piece[(int) m.to] = piece[(int) m.from];
        }
        color[(int) m.from] = EMPTY;
        piece[(int) m.from] = EMPTY;

        /* erase the pawn if this is an en passant move */
        if ((m.bits & 4) != 0) {
            if (side == LIGHT) {
                color[m.to + 8] = EMPTY;
                piece[m.to + 8] = EMPTY;
            } else {
                color[m.to - 8] = EMPTY;
                piece[m.to - 8] = EMPTY;
            }
        }

        side ^= 1;
        xside ^= 1;
        if (in_check(xside) != 0) {
            takeback();
            return FALSE;
        }

        return TRUE;
    }

    public void takeback() {

        side ^= 1;
        xside ^= 1;

        Move m = um.mov;
        castle = um.castle;
        ep = um.ep;
        fifty = um.fifty;

        color[(int) m.from] = side;
        if ((m.bits & 32) != 0) {
            piece[(int) m.from] = PAWN;
        } else {
            piece[(int) m.from] = piece[(int) m.to];
        }
        if (um.capture == EMPTY) {
            color[(int) m.to] = EMPTY;
            piece[(int) m.to] = EMPTY;
        } else {
            color[(int) m.to] = xside;
            piece[(int) m.to] = um.capture;
        }
        if ((m.bits & 2) != 0) {
            int from;
            int to;

            switch (m.to) {
                case 62:
                    from = F1;
                    to = H1;
                    break;
                case 58:
                    from = D1;
                    to = A1;
                    break;
                case 6:
                    from = F8;
                    to = H8;
                    break;
                case 2:
                    from = D8;
                    to = A8;
                    break;
                default: // shouldn't get here
                    from = -1;
                    to = -1;
                    break;
            }
            color[to] = side;
            piece[to] = ROOK;
            color[from] = EMPTY;
            piece[from] = EMPTY;
        }
        if ((m.bits & 4) != 0) {
            if (side == LIGHT) {
                color[m.to + 8] = xside;
                piece[m.to + 8] = PAWN;
            } else {
                color[m.to - 8] = xside;
                piece[m.to - 8] = PAWN;
            }
        }
    }
//
//    public void print_board() {
//        System.out.print("\n8 ");
//        for (int i = 0; i < 64; ++i) {
//            switch (color[i]) {
//                case EMPTY:
//                    System.out.print(". ");
//                    break;
//                case LIGHT:
//                    System.out.printf(piece_char_light[piece[i]] + " ");
//                    break;
//                case DARK:
//                    System.out.printf(piece_char_dark[piece[i]] + " ");
//                    break;
//            }
//            if ((i + 1) % 8 == 0 && i != 63) {
//                System.out.printf("\n%d ", 7 - (i >> 3));
//            }
//        }
//        System.out.print("\n\n   a b c d e f g h\n\n");
//    }
}
