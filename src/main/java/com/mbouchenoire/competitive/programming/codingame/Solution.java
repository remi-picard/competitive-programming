package com.mbouchenoire.competitive.programming.codingame;

import com.mbouchenoire.competitive.programming.common.model.graph.Graph;
import com.mbouchenoire.competitive.programming.common.model.graph.GraphEdge;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class Solution {

    public static void main(String args[]) {
        final Scanner in = new Scanner(System.in);
        final int influenceRelationshipCount = in.nextInt(); // the number of relationships of influence

        final Set<Integer> uniqueNodes = new HashSet<>();

        final GraphEdge[] edges = new GraphEdge[influenceRelationshipCount];

        for (int i = 0; i < influenceRelationshipCount; i++) {
            final int source = in.nextInt();
            final int destination = in.nextInt();

            final GraphEdge<Integer> edge = new GraphEdge<>(source, destination, true);
            System.err.println(edge);
            edges[i] = edge;

            uniqueNodes.add(source);
            uniqueNodes.add(destination);
        }

        final Integer[] nodes = uniqueNodes.toArray(new Integer[uniqueNodes.size()]);
        final Graph graph = new Graph(nodes, edges);
        final int longestNodeChainLength = graph.longestNodeChainLength();
        System.out.println(longestNodeChainLength);
    }

    private static int[] toArray(List<Integer> list) {
        final int[] intArray = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            final int intValue = list.get(i);
            intArray[i] = intValue;
        }

        return intArray;
    }
}