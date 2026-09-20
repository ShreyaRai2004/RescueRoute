package com.rescueroute.algorithm;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DijkstraAlgorithmTest {
    @Test void findsShortestPath(){
        Graph g=new Graph();
        g.addBidirectionalEdge("A","B",2);
        g.addBidirectionalEdge("B","C",2);
        g.addBidirectionalEdge("A","C",10);
        var r=new DijkstraAlgorithm().shortestPath(g,"A","C");
        assertEquals(4,r.distanceKm(),0.001);
        assertEquals(java.util.List.of("A","B","C"),r.path());
    }
}
