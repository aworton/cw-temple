package student;

import java.util.List;

/**
 * Created by Alexander Worton on 05/02/2017.
 */
public interface CavernMap {

    /**
     * add a new node to the map
     * @param node
     */
    void addNode(CavernNode node);

    /**
     * Connects the supplied nodes if unconnected
     * @param start
     * @param end
     */
    void connectNodes(CavernNode start, CavernNode end);

    /**
     *
     * @param node
     * @return a list of nodes connected to the supplied node. List is empty if no nodes are connected
     * @throws IllegalArgumentException if the supplied node is unknown to the map
     */
    List<CavernNode> getConnectedNodes(CavernNode node) throws IllegalArgumentException;

}
