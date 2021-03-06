package student.maps;

import org.junit.Before;
import org.junit.Test;
import student.nodes.CavernNode;
import student.nodes.CavernNodeImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 05/02/2017.
 */
@SuppressWarnings("ALL")
public class CavernMapImplTest {

    private CavernMap map;

    @Before
    public void before(){
        map = new CavernMapImpl();
    }

    @Test
    public void addSingleNode(){
        long nodeId = 14L;
        CavernNode node = addNodeWithIdToMap(map, nodeId);
        node.setPathValue(54265);
        assertTrue(map.getConnectedNodes(node).size() == 0);
        assertTrue(map.getNode(nodeId).getPathValue() == node.getPathValue());
        assertEquals(node, map.getNode(nodeId));
    }

    @Test
    public void addTwoConnectedNodesForwardLinked(){
        CavernNode node5 = addNodeWithIdToMap(map, 5);
        CavernNode node8 = addNodeWithIdToMap(map, 8);
        CavernNode node12 = addNodeWithIdToMap(map, 12);
        map.connectNodes(node5, node8);
        map.connectNodes(node5, node12);
        assertTrue(map.getConnectedNodes(node5).size() == 2);
    }

    @Test
    public void addTwoConnectedNodesReverseLinked(){
        CavernNode node5 = addNodeWithIdToMap(map, 5);
        CavernNode node8 = addNodeWithIdToMap(map, 8);
        CavernNode node12 = addNodeWithIdToMap(map, 12);
        map.connectNodes(node5, node8);
        map.connectNodes(node5, node12);
        assertTrue(map.getConnectedNodes(node8).size() == 1);
    }

    @Test
    public void getConnectedNodesWeightTest(){
        int expected20to21 = 1;
        int expected21to22 = 5;

        CavernNode node20 = addNodeWithIdToMap(map, 20);
        CavernNode node21 = addNodeWithIdToMap(map, 21);
        CavernNode node22 = addNodeWithIdToMap(map, 22);
        map.connectNodes(node20, node21);
        map.connectNodes(node21, node22, expected21to22);

        assertEquals(expected20to21, (int)map.getConnectedNodesWeight(node21, node20));
        assertEquals(expected21to22, (int)map.getConnectedNodesWeight(node22, node21));
    }

    private CavernNode addNodeWithIdToMap(CavernMap map, long id){
        CavernNode node = new CavernNodeImpl();
        node.setId(id);
        map.addNode(node);
        return node;
    }

}