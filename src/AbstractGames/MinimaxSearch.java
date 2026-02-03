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


    private boolean isGameEnded() {
        return this.board.endGame() != Board.GAME_CONTINUE;
    }


    /**
     * TODO Write Minimax here!
     *
     * @param depth Depth to search to
     * @return best move found at this node
     */
    private MOVE Minimax(int depth) {
        return Maximize(depth, -1, 1);
    }


    private MOVE Maximize(int depth, double alpha, double beta) {
        String possible_key = String.format(this.board.toString(), this.board.getCurrentPlayer());
        if (this.transposition_table.containsKey(possible_key)) {
            return this.transposition_table.get(possible_key);
        }
        Move move_list = this.board.generateMoves();
        Move best_move = move_list;
        if (this.isGameEnded() || depth == 0) {
            Move move_order_asc = this.board.moveOrdering(move_list, 0);
            this.totalNodesSearched += 1;
            this.totalLeafNodes += 1;
            return (MOVE) move_order_asc;
        }
        for (Move m = move_list; m != null; m = m.next) {
            this.board.makeMove(m);
            this.totalNodesSearched++;
            Move optinal_move = this.Minimize(depth - 1, alpha, beta);
            if (optinal_move.value > best_move.value)
                best_move = optinal_move;
            this.board.reverseMove(m);
            alpha = Math.max(alpha, optinal_move.value);
            if (beta <= alpha)
                return (MOVE) best_move;
        }
        return (MOVE) best_move;
    }


    private MOVE Minimize(int depth, double alpha, double beta) {
        String possible_key = String.format(this.board.toString(), this.board.getCurrentPlayer());
        if (this.transposition_table.containsKey(possible_key))
            return this.transposition_table.get(possible_key);
        Move move_list = this.board.generateMoves();
        Move best_move = move_list;
        if (this.isGameEnded() || depth == 0) {
            Move move_order_asc = this.board.moveOrdering(move_list, 0);
            this.totalNodesSearched += 1;
            this.totalLeafNodes += 1;
            while (move_order_asc.next != null)
                move_order_asc = move_order_asc.next;
            return (MOVE) move_order_asc;

        }
        for (Move m = move_list; m != null; m = m.next) {
            this.board.makeMove(m);
            this.totalNodesSearched++;
            Move optinal_move = this.Maximize(depth - 1, alpha, beta);
            if (optinal_move.value < best_move.value)
                best_move = optinal_move;
            this.board.reverseMove(m);
            beta = Math.min(beta, optinal_move.value);
            if (beta <= alpha)
                return (MOVE) best_move;

        }
        return (MOVE) best_move;
    }
}
