package student.navigators;

import java.util.List;

import student.nodes.CavernNode;

/**
 * Created by Alexander Worton on 02/02/2017.
 */
@SuppressWarnings("ALL")
public interface Navigator {
  /**
   * set starting node.
   *
   * @param node the start node to be set
   */
  void setStartNode(CavernNode node);

  /**
   * set destination node.
   *
   * @param node the destination node to be set
   */
  void setDestinationNode(CavernNode node);

  /**
   * get the sequence of nodes to form the path from start to destination,
   * including both starting and destination nodes.
   *
   * @return a queue of cavernNodes
   */
  List<CavernNode> getPathFromStartToDestination();

  /**
   * @return shortest distance from source to destination.
   */
  int getShortestDistanceToDestination();
}
