package AbstractGames;


import java.util.HashMap;

public class MinimaxSearch<BOARD extends Board, MOVE extends Move> implements Search<BOARD, MOVE> {
    BOARD board;
    int totalNodesSearched;
    int totalLeafNodes;
    HashMap<String, MOVE> transposition_table;

    @Override
    public MOVE findBestMove(BOARD board, int depth) {
        MOVE best_move = null;
        int runningNodeTotal = 0;
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long currentPeriod;
        long previousPeriod = 0;
        int i = 1;
        this.board = board;
        this.transposition_table = new HashMap<>();

        // Including the iterative deepening for consistency.
        while (i <= depth) {
            totalNodesSearched = totalLeafNodes = 0;

            best_move = Minimax(i); // Min-Max alpha beta with transposition tables

            elapsedTime = System.currentTimeMillis() - startTime;
            currentPeriod = elapsedTime - previousPeriod;
            double rate = 0.0;
            if (i > 3 && previousPeriod > 50)
                rate = (currentPeriod - previousPeriod) / previousPeriod;
            previousPeriod = elapsedTime;

            runningNodeTotal += totalNodesSearched;
            System.out.println("Depth: " + i + " Time: " + elapsedTime / 1000.0 + " " + currentPeriod / 1000.0 + " Nodes Searched: " +
                    totalNodesSearched + " Leaf Nodes: " + totalLeafNodes + " Rate: " + rate);

            // increment indexes;
            i = i + 2;
        }

        System.out.println("Nodes per Second = " + runningNodeTotal / (elapsedTime / 1000.0));
        if (best_move == null) {
            throw new Error("No Move Available - Search Error!");
        }
        return best_move;
    }


    /**
     * TODO Write Minimax here!
     *
     * @param depth Depth to search to
     * @return best move found at this node
     */
    private MOVE Minimax(int depth) {
        String possible_key = this.board.toString();
        if (this.transposition_table.containsKey(possible_key)) {
            return this.transposition_table.get(possible_key);
        }


        Move move_list = board.generateMoves();
        if (move_list == null)
            return null;

        int root_player = board.getCurrentPlayer();
        MOVE best_move = null;
        double best_value = Double.NEGATIVE_INFINITY;
        move_list = board.moveOrdering(move_list, 0);

        for (Move m = move_list; m != null; m = m.next) {
            totalNodesSearched++;

            if (!board.makeMove(m))
                continue;

            double optional_value = minimaxValue(depth - 1, root_player, -1, 1);

            board.reverseMove(m);

            best_value = Math.max(optional_value, best_value);
            if (optional_value == best_value)
                best_move = (MOVE) m;
        }
        this.transposition_table.put(this.board.toString(), best_move);
        return best_move;
    }


    private double minimaxValue(int depth, int rootPlayer, double alpha, double beta) {
        int game_condition = board.endGame();
        if (game_condition != Board.GAME_CONTINUE || depth == 0) {
            totalLeafNodes++;
            return evalForRoot(rootPlayer, game_condition);
        }


        Move move_list = board.generateMoves();
        if (move_list == null) {
            totalLeafNodes++;
            return evalForRoot(rootPlayer, board.endGame());
        }

        boolean is_maximizing_player = (board.getCurrentPlayer() == rootPlayer);
        move_list = board.moveOrdering(move_list, 0);
        depth--;
        if (is_maximizing_player) {
            double best_value = Double.NEGATIVE_INFINITY;
            for (Move m = move_list; m != null; m = m.next) {
                totalNodesSearched++;
                if (!board.makeMove(m))
                    continue;
                double optional_max_value = minimaxValue(depth, rootPlayer, alpha, beta);
                board.reverseMove(m);
                best_value = Math.max(best_value, optional_max_value);
                alpha = Math.max(alpha, optional_max_value);
                if (beta <= alpha) {
                    break;
                }
            }
            return best_value;
        } else {
            double best_value = Double.POSITIVE_INFINITY;
            for (Move m = move_list; m != null; m = m.next) {
                totalNodesSearched++;
                if (!board.makeMove(m))
                    continue;
                double optional_min_value = minimaxValue(depth, rootPlayer, alpha, beta);
                board.reverseMove(m);
                best_value = Math.min(best_value, optional_min_value);
                beta = Math.min(alpha, optional_min_value);
                if (beta <= alpha) {
                    break;
                }
            }
            return best_value;
        }
    }

    private double evalForRoot(int rootPlayer, int endState) {
        if (endState == Board.GAME_DRAW) return 0.0;
        if (endState != Board.GAME_CONTINUE)
            return (endState == rootPlayer) ? 1.0 : -1.0;
        return board.heuristicEvaluation();
    }
}
