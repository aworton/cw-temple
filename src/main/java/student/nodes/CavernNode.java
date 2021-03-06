package student.nodes;

/**
 * Created by Alexander Worton on 05/02/2017.
 */
@SuppressWarnings("ALL")
public interface CavernNode {
  /**
   * Getter for the node path value.
   *
   * @return path value
   */
  int getPathValue();

  /**
   * Setter for the node path value.
   * This should be set to the max value (in place of infinity) by default
   *
   * @param pathValue the value to be set
   * @throws IllegalArgumentException if the argument provided is negative
   */
  void setPathValue(int pathValue);

  /**
   * Getter for the node distance value.
   *
   * @return distance value
   */
  int getDistance();

  /**
   * Setter for the node distance value.
   * This should be set to the max value by default
   *
   * @param distance the value to be set
   * @throws IllegalArgumentException if the argument provided is negative
   */
  void setDistance(int distance);

  /**
   * Getter for the node id.
   *
   * @return id
   * @throws IllegalStateException if a valid id has not been set
   */
  long getId();

  /**
   * Setter for the node id.
   *
   * @param id the value to be set
   * @throws IllegalArgumentException if the argument provided is les than 1
   */
  void setId(long id);

  /**
   * Getter for visited state.
   *
   * @return true if node has been visited, false otherwise
   */
  Boolean isVisited();

  /**
   * Setter for the visited state.
   *
   * @param visited the new state
   */
  void setVisited(Boolean visited);
}