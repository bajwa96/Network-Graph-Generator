package maxFlowImpl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import Model.Constants;
import Model.FlowMetric;
import Model.Graph;
import maxFlow.FordFulkerson;

/**
 * DFS-like: When processing the neighbors v of the current node u (line 9 of
 * Dijkstra’s algorithm above), if v.d is infinity, decrease the key value for v
 * from infinity to a decreasing counter value in Q. If v.d is not infinity, do
 * not change v’s key value. This has the effect of performing a DFS-like
 * search.
 */
public class MaxFlowUsingDfsLike extends FordFulkersonImpl implements FordFulkerson {

	@Override
	public boolean augmentedPath(Graph graph, FlowMetric flowMetric) {
		graph.prepareAdjacencyMatrix(Constants.NODE_EDGE_WEIGHT_1);
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix().clone();
		int[] parents = dijkstra(adjacencyMatrix, graph.getSourceVertex(), graph.getSinkVertex(),
				graph.getResidualGraph());

		graph.setParent(parents);

		System.out.println();
		if (parents == null)
			return false;
		return true;
	}

	private int[] dijkstra(int[][] graph, int src, int sink, int[][] residualGraph) {
		int noOfVertices = graph.length;

		int[] parent = new int[noOfVertices];
		int[] pqKey = new int[noOfVertices];

		Arrays.fill(parent, -1);

		int counter = noOfVertices * noOfVertices;
		Arrays.fill(pqKey, 0);
		pqKey[src] = 0;

		parent[src] = -1;

		PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(v -> pqKey[v]));
		pq.add(src);

		while (!pq.isEmpty()) {
			int currentVertex = pq.poll();
			System.out.println("Now Porcessing u" + currentVertex);
			for (int currentlyProcessingV = 0; currentlyProcessingV < noOfVertices; ++currentlyProcessingV) {
				if ((pq.contains(currentlyProcessingV) || pqKey[currentlyProcessingV] != 0)) {
					continue;
				} else if (graph[currentVertex][currentlyProcessingV] > 0 && residualGraph[currentVertex][currentlyProcessingV] > 0) {
					parent[currentlyProcessingV] = currentVertex;
					pqKey[currentlyProcessingV] = counter--;
					pq.add(currentlyProcessingV);
				}
			}
		}

		// Print the DFS-like search order
		for (int i = 0; i < noOfVertices; ++i)
			System.out.println("Vertex " + i + " - DFS Order: " + pqKey[i] + ", Parent: " + parent[i]);
		if (pqKey[sink] != 0) {
			printSolution(src, pqKey, parent);
			return parent;
		}

		return null;
	}

	@Override
	public Integer getMaximumFlow(Graph graph, FlowMetric flowMetric) {
		return fordFulkerson(graph, flowMetric);
	}

}
