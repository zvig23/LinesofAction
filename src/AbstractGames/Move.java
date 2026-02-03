package AbstractGames;

/**
 * The Move class. Each Abstract game should have an extension of this class. It should store the action(s)
 * that a player can execute.
 */
public abstract class Move {

  public double value; /**< Store an estimate of the move for move ordering */
  public Move next;    /**< Pointer to the next move in the linked list */

  /**
   * Base constructor.
   */
  public Move() {
    value = 0.0;
    next = null;
  }

  /**
   * Converts this Move into a String for printing
   * @return The move as a string
   */
  public String toString() {
    return null;
  }

  /**
   * Compare two moves
   * @param Move object to compare with this one
   * @return true if the moves are the same false if not
   */
  public abstract boolean equals(Move move);
}
