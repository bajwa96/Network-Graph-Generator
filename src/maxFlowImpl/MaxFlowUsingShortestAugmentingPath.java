package maxFlowImpl;

import Model.Constants;
import Model.FlowMetric;
import Model.Graph;
import maxFlow.FordFulkerson;

/**
 * PART 1) Shortest Augmenting Path (SAP): Run Dijkstra’s algorithm while
 * treating the edge 2 lengths as unit distances other than their actual
 * Euclidean distances. This will be effectively the same as running the BFS
 * algorithm.
 * 
 */
public class MaxFlowUsingShortestAugmentingPath extends FordFulkersonImpl implements FordFulkerson {

	@Override
	public boolean augmentedPath(Graph graph, FlowMetric flowMetric) {
		graph.prepareAdjacencyMatrix(Constants.NODE_EDGE_WEIGHT_1);
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix().clone();
		int[] parents = dijkstraForSap(adjacencyMatrix, graph.getSourceVertex(), graph.getSinkVertex(),
				graph.getResidualGraph());

		graph.setParent(parents);
		System.out.println();
		if (parents == null)
			return false;
		return true;
	}

	private int[] dijkstraForSap(int[][] adjacencyMatrix, int startVertex, Integer sinkVertex, int[][] residualGraph) {
		int noOfVertices = adjacencyMatrix[0].length;

		int[] shortestDistances = new int[noOfVertices];

		boolean[] added = new boolean[noOfVertices];

		for (int vertexIndex = 0; vertexIndex < noOfVertices; vertexIndex++) {
			shortestDistances[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = false;
		}

		shortestDistances[startVertex] = 0;

		int[] parents = new int[noOfVertices];

		parents[startVertex] = -1;

		for (int i = 1; i < noOfVertices; i++) {
			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < noOfVertices; vertexIndex++) {
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}

			if (nearestVertex == -1) {
				continue;
			}
			added[nearestVertex] = true;

			for (int vertexIndex = 0; vertexIndex < noOfVertices; vertexIndex++) {
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])
						&& residualGraph[nearestVertex][vertexIndex] > 0) {
					parents[vertexIndex] = nearestVertex;
					shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
				}
			}
		}

		printSolution(startVertex, shortestDistances, parents);
		if (shortestDistances[sinkVertex] != Integer.MAX_VALUE) {
			return parents;
		}
		return null;
	}

	@Override
	public Integer getMaximumFlow(Graph graph, FlowMetric flowMetric) {
		return fordFulkerson(graph, flowMetric);
	}

}
