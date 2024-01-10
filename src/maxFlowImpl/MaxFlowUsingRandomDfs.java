package maxFlowImpl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import Model.Constants;
import Model.FlowMetric;
import Model.Graph;
import maxFlow.FordFulkerson;

/**
 * Random: Same modifications as for DFS-like except use a random value as the
 * key value.
 */
public class MaxFlowUsingRandomDfs extends FordFulkersonImpl implements FordFulkerson {

	@Override
	public boolean augmentedPath(Graph graph, FlowMetric flowMetric) {
		graph.prepareAdjacencyMatrix(Constants.NODE_EDGE_WEIGHT_1);
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix().clone();
		int[] parents = dijkstraWithRandomDFS(adjacencyMatrix, graph.getSourceVertex(), graph.getSinkVertex(),
				graph.getResidualGraph());

		graph.setParent(parents);

		System.out.println();
		if (parents == null)
			return false;
		return true;
	}

	private int[] dijkstraWithRandomDFS(int[][] graph, int src, int sink, int[][] residualGraph) {
		int noOfVertices = graph.length;

		int[] dist = new int[noOfVertices];
		int[] parent = new int[noOfVertices];
		int[] pqKey = new int[noOfVertices];
		Arrays.fill(dist, Integer.MAX_VALUE);

		Arrays.fill(parent, -1);

		Arrays.fill(pqKey, 0);
		pqKey[src] = 0;

		dist[src] = 0;

		PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(v -> pqKey[v]));
		pq.add(src);

		while (!pq.isEmpty()) {
			int currentVertex = pq.poll();
			System.out.println("Now Porcessing u" + currentVertex);
			for (int currentlyProcessingV = 0; currentlyProcessingV < noOfVertices; ++currentlyProcessingV) {
				int currNodeDistance = dist[currentVertex];
				int edgeDistance = graph[currentVertex][currentlyProcessingV];

				if (edgeDistance > 0 && currNodeDistance != Constants.UNREACHABLE_NODE
						&& ((currNodeDistance + edgeDistance) < dist[currentlyProcessingV])
						&& residualGraph[currentVertex][currentlyProcessingV] > 0) {
					dist[currentlyProcessingV] = currNodeDistance + edgeDistance;
					parent[currentlyProcessingV] = currentVertex;
					pq.add(currentlyProcessingV);
				} else {
					if (dist[currentlyProcessingV] == Integer.MAX_VALUE) {
						if (pq.contains(currentlyProcessingV)) {
							pqKey[currentlyProcessingV] = (int) (Math.random() * noOfVertices);
							pq.add(currentlyProcessingV);
						}
					}
				}
			}
		}

		// Print the DFS-like search order
		for (int i = 0; i < noOfVertices; ++i)
			System.out.println("Vertex " + i + " - DFS Order: " + dist[i] + ", Parent: " + parent[i]);
		if (dist[sink] != Integer.MAX_VALUE) {
			printSolution(src, dist, parent);
			return parent;
		}

		return null;
	}

	@Override
	public Integer getMaximumFlow(Graph graph, FlowMetric flowMetric) {
		return fordFulkerson(graph, flowMetric);
	}
}
