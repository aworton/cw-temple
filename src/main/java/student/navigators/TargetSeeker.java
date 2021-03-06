package student.navigators;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import student.maps.CavernMap;
import student.nodes.CavernNode;
import student.nodes.CavernNodeImpl;
import student.nodes.HasIdAndDistance;

/**
 * Created by Alexander Worton on 06/02/2017.
 */
@SuppressWarnings("ALL")
public class TargetSeeker implements Seeker {

  /**
   * navigator field.
   **/
  private Navigator navigator;
  /**
   * map field.
   **/
  private CavernMap map;
  /**
   * currentLocationId field.
   **/
  private long currentLocationId;
  /**
   * path field.
   **/
  private List<CavernNode> path;

  /**
   * Constructor.
   *
   * @param nav the injected navigator
   * @param map the injected map
   */
  public TargetSeeker(Navigator nav, CavernMap map) {
    setNav(nav);
    setMap(map);
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public long getNextMove(long location, Collection<? extends HasIdAndDistance> neighbours) {
    if (!map.contains(location)) {
      throw new IllegalStateException("Node with that id not known");
    }

    setCurrentLocationId(location);
    CavernNode currentLocation = map.getNode(location);
    addNeighboursToMap(currentLocation, neighbours);
    return getNextClosestNodeId(neighbours);
  }

  /**
   * select any remaining unexplored neighbour node, closest first, then plot a path
   * to the next closest unexplored node in map.
   *
   * @return next closest node to target
   */
  private Long getNextClosestNodeId(Collection<? extends HasIdAndDistance> neighbours) {
    if (pathExists()) {
      return getNextPathNodeId();
    }
    return getNextNodeId(neighbours);
  }

  /**
   * Attempt to retrieve the neighbour closest to target, if there is no such
   * neighbour, get a path back to the closest seen node to the target node.
   *
   * @param neighbours of the current location
   * @return the id of the next node to visit
   */
  private long getNextNodeId(Collection<? extends HasIdAndDistance> neighbours) {
    Long nextNodeId = getClosestNeighbourNode(neighbours);
    if (nextNodeId == null) {
      nextNodeId = getNewPathToClosestAvailableNode();
    }
    return nextNodeId;
  }

  /**
   * Retrieve the neighbouring node which is closest to the target node.
   *
   * @param neighbours the set of nodes to examine
   * @return id of closest node to target, null if none exists
   */
  private Long getClosestNeighbourNode(Collection<? extends HasIdAndDistance> neighbours) {
    return neighbours.stream()
            .sorted(Comparator.comparingInt(HasIdAndDistance::getDistance))
            .map(n -> map.getNode(n.getId()))
            .filter(n -> !n.isVisited())
            .findFirst()
            .map(CavernNode::getId)
            .orElse(null);
  }

  /**
   * Generate a path/route to the closest known node to the target.
   *
   * @return the id of the first node to move to from the current node
   */
  private Long getNewPathToClosestAvailableNode() {
    navigator.setStartNode(map.getNode(this.currentLocationId));
    navigator.setDestinationNode(getClosestUnvisitedNodeOnMap());
    this.path = SeekerLibrary.setNewPath(this.navigator, this.currentLocationId);
    return getNextPathNodeId();
  }

  /**
   * Get the node to path to by taking into account both the via nodes
   * distance to the target and the distance from current location to the node.
   *
   * @return an unvisited node which is closest navigate to target
   */
  private CavernNode getClosestUnvisitedNodeOnMap() {
    return map.getAllNodes().stream()
            .filter(n -> !n.isVisited())
            .sorted(Comparator.comparingInt(n -> (n.getDistance() + getDistanceToNode(n))))
            .findFirst()
            .orElse(null);
  }

  /**
   * Getter for the distance to target node.
   *
   * @param n the target node
   * @return the distance to the node from current location
   */
  private int getDistanceToNode(CavernNode n) {
    navigator.setStartNode(map.getNode(this.currentLocationId));
    navigator.setDestinationNode(n);
    return navigator.getShortestDistanceToDestination();
  }

  /**
   * add neighbours to the map when supplied.
   *
   * @param currentLocation the current location
   * @param neighbours      the neighbouring nodes
   */
  private void addNeighboursToMap(CavernNode currentLocation,
                                  Collection<? extends HasIdAndDistance> neighbours) {
    neighbours.forEach(n -> {
      CavernNode node = addToOrGetExistingNodeFromMap(n.getId(), n.getDistance());
      map.connectNodes(currentLocation, node);
    });
  }

  /**
   * Check that a node with the id is in the map, if so, return it
   * otherwise add a new node to the map and return that.
   *
   * @param id       the id of a node
   * @param distance the distance to target
   * @return node from map
   */
  private CavernNode addToOrGetExistingNodeFromMap(long id, int distance) {
    if (!map.contains(id)) {
      CavernNode node = new CavernNodeImpl(id);
      node.setDistance(distance);
      map.addNode(node);
    }
    return map.getNode(id);
  }

  /**
   * Setter for navigator.
   *
   * @param nav the new navigator
   */
  private void setNav(Navigator nav) {
    this.navigator = nav;
  }

  /**
   * Setter for the map.
   *
   * @param map the new map
   */
  private void setMap(CavernMap map) {
    this.map = map;
  }

  /**
   * Pop the element at the front of the list and return the id if available.
   *
   * @return null if list empty or null, node's id otherwise
   */
  private Long getNextPathNodeId() {
    if (!pathExists()) {
      return null;
    }
    return path.remove(0).getId();
  }

  /**
   * Check path exists.
   * @return true if a valid path exists, false otherwise
   */
  private Boolean pathExists() {
    return this.path != null && this.path.size() > 0;
  }

  /**
   * Set the currentLocation and mark it as visited.
   *
   * @param currentLocationId the id of the current location
   */
  private void setCurrentLocationId(long currentLocationId) {
    this.currentLocationId = currentLocationId;
    map.getNode(currentLocationId).setVisited(true);
  }
}
