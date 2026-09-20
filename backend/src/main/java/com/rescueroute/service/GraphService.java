package com.rescueroute.service;
import com.rescueroute.algorithm.Graph;
import org.springframework.stereotype.Service;

@Service
public class GraphService {
    private final Graph graph = new Graph();

    public GraphService(){
        graph.addBidirectionalEdge("A", "B", 2.2);
        graph.addBidirectionalEdge("A", "C", 3.1);
        graph.addBidirectionalEdge("B", "D", 2.0);
        graph.addBidirectionalEdge("C", "D", 1.4);
        graph.addBidirectionalEdge("C", "E", 2.6);
        graph.addBidirectionalEdge("D", "F", 2.5);
        graph.addBidirectionalEdge("E", "F", 1.8);
        graph.addBidirectionalEdge("F", "G", 2.2);
        graph.addBidirectionalEdge("D", "G", 4.0);
        graph.addBidirectionalEdge("B", "C", 2.0);
    }

    public Graph getGraph(){return graph;}
}
