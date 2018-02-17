package com.mbouchenoire.competitive.programming.common.model.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class that models a graph and allows to scan it in order to produce
 * various results (just using <a
 * href="https://en.wikipedia.org/wiki/Breadth-first_search">BFS</a> for
 * now) Graph can be directed or bi directional
 *
 * Hint: you can use this graph implementation to: compute easily
 * distances from anywhere to one or several targets. Heat map
 * constructions Voronoi territory constructions etc
 *
 * for optimization reasons, results are produced as an array of values
 * (int or double) and by convention the relation between the result and
 * the node is by index
 *
 * @param <N> The class representing a node in the graph.
 */
public class Graph<N> {

    private final N[] nodes;
    private final List<List<Integer>> neighborIndexes;

    /**
     * Graph constructor
     *
     * Conventions: the nodes you give are in an array. They are identified by
     * their index in this array meaning that the links source and destination
     * you provide indicates the index of the node in this array
     *
     * @param nodes the array of nodes that does exists in the graph
     * @param edgeSources the array of index of the source nodes
     * @param edgeDestinations the array of index of the destination nodes
     * @param directed if true will consider the links you give are directed.
     *                 It means you can only go from the source node to the destination node but not the contrary
     * @throws IllegalArgumentException if there is not the same number of indexes between source and destination
     */
    public Graph(final N[] nodes, int[] edgeSources, int[] edgeDestinations, boolean directed) {
        if (edgeSources.length != edgeDestinations.length) {
            throw new IllegalArgumentException("The number of edge sources and destinations provided does not match!");
        }

        this.nodes = nodes;
        neighborIndexes = new ArrayList<>(nodes.length);

        for (N node : nodes) {
            neighborIndexes.add(new ArrayList<>(nodes.length));
        }

        for (int edgeIndex = 0; edgeIndex < edgeSources.length; edgeIndex++) {
            final int sourceIndex = edgeSources[edgeIndex];
            final int destinationIndex = edgeDestinations[edgeIndex];

            createLink(sourceIndex, destinationIndex);

            if (!directed) {
                createLink(destinationIndex, sourceIndex);
            }
        }
    }

    public Graph(final N[] nodes, GraphEdge<N>[] edges) {
        this.nodes = nodes;
        neighborIndexes = new ArrayList<>(nodes.length);

        for (N node : nodes) {
            neighborIndexes.add(new ArrayList<>(nodes.length));
        }

        for (final GraphEdge<N> edge : edges) {
            final int sourceIndex = indexOfNode(edge.source);
            final int destinationIndex = indexOfNode(edge.destination);

            createLink(sourceIndex, destinationIndex);

            if (!edge.oriented) {
                createLink(destinationIndex, sourceIndex);
            }
        }
    }

    private int indexOfNode(N node) {
        for (int nodeIndex = 0; nodeIndex < nodes.length; nodeIndex++) {
            final N n = nodes[nodeIndex];

            if (n.equals(node)) {
                return nodeIndex;
            }
        }

        throw new IllegalArgumentException("node not found");
    }

    private void createLink(int sourceIndex, int destinationIndex) {
        neighborIndexes.get(sourceIndex).add(destinationIndex);
    }

    public int longestNodeChainLength() {
        int longestNodeChainLength = 0;

        for (int nodeIndex = 0; nodeIndex < neighborIndexes.size(); nodeIndex++) {
            final int longestNodeChainLengthCandidate = longestNodeChainLength(nodeIndex, 1);

            if (longestNodeChainLengthCandidate > longestNodeChainLength) {
                longestNodeChainLength = longestNodeChainLengthCandidate;
            }
        }

        return longestNodeChainLength;
    }

    private int longestNodeChainLength(int currentNodeIndex, int currentLength) {
        final List<Integer> neighbors = neighborIndexes.get(currentNodeIndex);

        int longestChildChain = currentLength;

        for (Integer neighbor : neighbors) {
            final int longestChainCandidate = longestNodeChainLength(neighbor, currentLength+1);

            if (longestChainCandidate > longestChildChain) {
                longestChildChain = longestChainCandidate;
            }
        }

        return longestChildChain;
    }

    /**
     * Breadth-first search implementation on your graph.
     *
     * It will iteratively:
     * assign current level value to all source nodes
     * scan source nodes neighbor to find the reachable nodes that have not been reached yet
     * Compute next level value and consider all the reachable neighbors as the new source nodes
     *
     * Note: sadly I did not find a way to template this method as I would do in C++ without degrading performances.
     * So I must implement it also for int...
     *
     * @param intialValue the value that will be assigned to all nodes before starting the BFS
     *                    Hint: giving a value impossible to reach can allow you to identify which nodes have never been reached
     * @param firstValue the value that will be assigned to all the nodes in sources
     * @param traversable determines if a node should be considered in the BFS or simply ignored
     * @param nextValueIterator give the next level value from the current level value. Add 1 to compute distances
     * @param sources the list of nodes that will receive the first value
     * @return an array with the values of each node. The index in this array correspond to the index of the node given during the constructor
     */
    public double[] breadthFirstSearch(double intialValue, double firstValue, BFSTraversable<N> traversable,
                                       DoubleBFSNextLevelValueIterator nextValueIterator, List<Integer> sources) {
        double[] results = new double[nodes.length];
        Arrays.fill(results, intialValue);
        boolean[] alreadyScanned = new boolean[nodes.length];
        Arrays.fill(alreadyScanned, false);
        Set<Integer> currentNodesIndex = new HashSet<>(sources);

        iterativeDoubleBreadthFirstSearch(results, alreadyScanned, currentNodesIndex, firstValue, 0, traversable, nextValueIterator);

        return results;
    }

    private void iterativeDoubleBreadthFirstSearch(double[] results, boolean[] alreadyScanned, Set<Integer> currentNodes, double value, int iteration,
                                                   BFSTraversable<N> traversable, DoubleBFSNextLevelValueIterator nextValueIterator) {
        Set<Integer> nextNodes = new HashSet<>();

        for (int index : currentNodes) {
            if (!alreadyScanned[index]) {
                alreadyScanned[index] = true;
                if (traversable.canBeVisited(nodes[index])) {
                    results[index] = value;
                    nextNodes.addAll(neighborIndexes.get(index));
                }
            }
        }

        if (!nextNodes.isEmpty()) {
            iterativeDoubleBreadthFirstSearch(results, alreadyScanned, nextNodes, nextValueIterator.nextInterationValue(value, iteration + 1), iteration + 1,
                    traversable, nextValueIterator);
        }
    }

    /**
     * Breadth-first search implementation for integers.
     * See double implementation for the parameters details.
     * Note that for compilation reasons parameters are in a different order compared to the double version
     *
     * Hint: you can compute distances by providing to the sources by providing a +1 next value visitor
     */
    public int[] breadthFirstSearch(
            int intialValue,
            BFSTraversable<N> traversable,
            int firstValue,
            IntegerBFSNextValueIterator nextValueIterator,
            List<Integer> sourcesIndex) {
        int[] results = new int[nodes.length];
        Arrays.fill(results, intialValue);
        boolean[] alreadyScanned = new boolean[nodes.length];
        Arrays.fill(alreadyScanned, false);
        Set<Integer> currentNodesIndex = new HashSet<>(sourcesIndex);

        iterativeIntegerBreadthFirstSearch(results, alreadyScanned, currentNodesIndex, firstValue, 0, traversable, nextValueIterator);

        return results;
    }

    private void iterativeIntegerBreadthFirstSearch(int[] results, boolean[] alreadyScanned, Set<Integer> currentNodes, int value, int iteration,
                                                    BFSTraversable<N> traversable, IntegerBFSNextValueIterator nextValueIterator) {
        Set<Integer> nextNodes = new HashSet<>();

        for (int index : currentNodes) {
            if (!alreadyScanned[index]) {
                alreadyScanned[index] = true;
                if (traversable.canBeVisited(nodes[index])) {
                    results[index] = value;
                    nextNodes.addAll(neighborIndexes.get(index));
                }
            }
        }

        if (!nextNodes.isEmpty()) {
            iterativeIntegerBreadthFirstSearch(results, alreadyScanned, nextNodes, nextValueIterator.nextInterationValue(value, iteration + 1), iteration + 1,
                    traversable, nextValueIterator);
        }
    }
}
