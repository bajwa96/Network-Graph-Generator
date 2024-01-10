package maxFlowImpl;

import Model.Constants;
import Model.FlowMetric;
import Model.Graph;

abstract public class FordFulkersonImpl {
	public abstract boolean augmentedPath(Graph g, FlowMetric flowMetric);

	protected Integer fordFulkerson(Graph graph, FlowMetric flowMetric) {
		graph.prepareResidualGraph();
		int maxFlow = 0;
		int totalNumberOfAugPaths = 0;
		while (augmentedPath(graph, flowMetric)) {
			totalNumberOfAugPaths++;

			int parent[] = graph.getParent();

			int path_flow = calculateMinimumFlowForAPath(parent, graph.getSinkVertex(), graph.getSourceVertex(),
					graph.getResidualGraph(), flowMetric);

			// update residual capacities of the edges and
			// reverse edges along the path
			for (int v = graph.getSinkVertex(); v != graph.getSourceVertex(); v = graph.getParent()[v]) {
				int u = graph.getParent()[v];
				graph.getResidualGraph()[u][v] -= path_flow;
				graph.getResidualGraph()[v][u] += path_flow;
			}

			// Add path flow to overall flow
			System.out.println("PathFlow inc:" + path_flow);
			maxFlow += path_flow;
		}
		flowMetric.setPaths(totalNumberOfAugPaths);
		// Return the overall flow
		return maxFlow;
	}

	protected int calculateMinimumFlowForAPath(int[] parent, int sinkVertex, int sourceVertex, int[][] residualGraph,
			FlowMetric flowMetric) {
		int path_flow = Constants.UNREACHABLE_NODE;
		int noOfEdgesInAugmentedPath = flowMetric.getTotalNumberOfEdgesInAugPath();
		for (int v = sinkVertex; v != sourceVertex; v = parent[v], noOfEdgesInAugmentedPath++) {
			int u = parent[v];
			path_flow = Math.min(path_flow, residualGraph[u][v]);
		}
		flowMetric.setTotalNumberOfEdgesInAugPath(noOfEdgesInAugmentedPath);
		return path_flow;
	}

	protected static void printSolution(int startVertex, int[] distances, int[] parents) {
		int nVertices = distances.length;
		System.out.println("Parents Array:");
		for (int p : parents)
			System.out.print(p + ", ");
		System.out.println();
		System.out.print("Vertex\t Distance\tPath");

		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
			if (vertexIndex != startVertex) {
				System.out.print("\n" + startVertex + " -> ");
				System.out.print(vertexIndex + " \t\t ");
				System.out.print(distances[vertexIndex] + "\t\t");
			}
		}
	}

}
