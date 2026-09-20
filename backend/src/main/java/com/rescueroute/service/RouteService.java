package com.rescueroute.service;
import com.rescueroute.algorithm.DijkstraAlgorithm;
import com.rescueroute.dto.RouteResponse;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
    private final GraphService graphService;
    private final DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
    private static final double AVERAGE_SPEED_KMH = 40.0;

    public RouteService(GraphService graphService){this.graphService=graphService;}

    public RouteResponse calculate(String start,String end){
        var result=dijkstra.shortestPath(graphService.getGraph(),start,end);
        if(result.path().isEmpty()) throw new IllegalArgumentException("No route exists between the selected nodes.");
        return new RouteResponse(result.path(), result.distanceKm(),
                (result.distanceKm()/AVERAGE_SPEED_KMH)*60.0);
    }
}
