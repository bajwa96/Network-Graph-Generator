package maxFlowImpl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import Model.FlowMetric;
import Model.Graph;
import maxFlow.FordFulkerson;

/**
 * Maximum Capacity (MaxCap): Modify Dijkstra’s algorithm to find the augmenting
 * path with the maximum capacity. Note that the capacity of the critical edge
 * (cf (p) of the augmenting path p) will be the value of t.d when the modified
 * algorithm completes.
 */
public class MaxFlowUsingMaxCap extends FordFulkersonImpl implements FordFulkerson {

	public int[] findMaxDistance(int[][] residualGraph, int source, int destination) {
		int noOfVertices = residualGraph.length;

		int[] parent = new int[noOfVertices];
		Arrays.fill(parent, -1);
		parent[source] = 0;

		int[] pqKey = new int[noOfVertices];
		Arrays.fill(pqKey, Integer.MAX_VALUE);

		boolean[] visited = new boolean[noOfVertices];
		Arrays.fill(visited, Boolean.FALSE);

		PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(v -> -pqKey[v]));
		priorityQueue.add(source);

		while (!priorityQueue.isEmpty()) {
			Integer currentVertex = priorityQueue.poll();

			if (visited[currentVertex])
				continue;
			visited[currentVertex] = true;

			for (int currentlyProcessingV = 0; currentlyProcessingV < noOfVertices; currentlyProcessingV++) {
				if (visited[currentlyProcessingV]) {
					continue;
				} else if (residualGraph[currentVertex][currentlyProcessingV] > 0) {

					int minCapacity = Math.min(pqKey[currentVertex],
							residualGraph[currentVertex][currentlyProcessingV]);
					if (minCapacity < pqKey[currentlyProcessingV]) {
						pqKey[currentlyProcessingV] = minCapacity;
						parent[currentlyProcessingV] = currentVertex;
						priorityQueue.add(currentlyProcessingV);
					}
				}
			}
		}

		if (pqKey[destination] != Integer.MAX_VALUE) {
			printSolution(source, pqKey, parent);
			return parent;
		}
		return null;
	}

	@Override
	public Integer getMaximumFlow(Graph graph, FlowMetric flowMetric) {
		return fordFulkerson(graph, flowMetric);
	}

	@Override
	public boolean augmentedPath(Graph graph, FlowMetric flowMetric) {
		int[] parents = findMaxDistance(graph.getResidualGraph(), graph.getSourceVertex(), graph.getSinkVertex());
		graph.setParent(parents);
		if (parents == null)
			return false;
		return true;
	}

}
