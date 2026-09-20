package com.rescueroute.algorithm;
import java.util.*;

public class DijkstraAlgorithm {
    public record Result(List<String> path, double distanceKm) {}

    public Result shortestPath(Graph graph, String start, String end) {
        if (!graph.edges().containsKey(start) || !graph.edges().containsKey(end)) {
            return new Result(List.of(), Double.POSITIVE_INFINITY);
        }
        Map<String,Double> dist = new HashMap<>();
        Map<String,String> prev = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(Node::distance));
        for (String node: graph.edges().keySet()) dist.put(node, Double.POSITIVE_INFINITY);
        dist.put(start,0.0); pq.add(new Node(start,0.0));

        while(!pq.isEmpty()){
            Node current=pq.poll();
            if(current.distance()>dist.get(current.id())) continue;
            if(current.id().equals(end)) break;
            for(Graph.Edge edge: graph.edges().getOrDefault(current.id(),List.of())){
                double next=current.distance()+edge.distance();
                if(next<dist.getOrDefault(edge.to(),Double.POSITIVE_INFINITY)){
                    dist.put(edge.to(),next);
                    prev.put(edge.to(),current.id());
                    pq.add(new Node(edge.to(),next));
                }
            }
        }
        if(!dist.containsKey(end) || Double.isInfinite(dist.get(end))) return new Result(List.of(),Double.POSITIVE_INFINITY);
        LinkedList<String> path=new LinkedList<>();
        String cur=end;
        while(cur!=null){path.addFirst(cur); cur=prev.get(cur);}
        return new Result(path,dist.get(end));
    }

    private record Node(String id,double distance){}
}
