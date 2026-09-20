package com.rescueroute.algorithm;
import java.util.*;
public class Graph {
    private final Map<String,List<Edge>> edges = new HashMap<>();
    public void addBidirectionalEdge(String a,String b,double distance){
        edges.computeIfAbsent(a,k->new ArrayList<>()).add(new Edge(b,distance));
        edges.computeIfAbsent(b,k->new ArrayList<>()).add(new Edge(a,distance));
    }
    public Map<String,List<Edge>> edges(){return edges;}
    public record Edge(String to,double distance){}
}
