package maxFlow;

import Model.Constants;
import Model.FlowMetric;
import Model.Graph;
import maxFlowImpl.MaxFlowUsingDfsLike;
import maxFlowImpl.MaxFlowUsingMaxCap;
import maxFlowImpl.MaxFlowUsingRandomDfs;
import maxFlowImpl.MaxFlowUsingShortestAugmentingPath;
import runner.MainApplication;

public interface FordFulkerson {

	public Integer getMaximumFlow(Graph graph, FlowMetric flowMetric);

	public static Integer getMaximumFlow(Graph graph, String algorithm) throws Exception {
		FordFulkerson fordFulkerson;
		graph.prepareResidualGraph();
		System.out.println("Now going to execute Max Flow using algorithm:" + algorithm);
		System.out.println("--------------------------------------------------------------------------");
		graph.printRGraph();

		switch (algorithm) {
		case Constants.SHORTEST_AUGMENTING_PATH:
			fordFulkerson = new MaxFlowUsingShortestAugmentingPath();
			break;
		case Constants.DFS_LIKE:
			fordFulkerson = new MaxFlowUsingDfsLike();
			break;
		case Constants.MAXIMUM_CAPACITY:
			fordFulkerson = new MaxFlowUsingMaxCap();
			break;
		case Constants.RANDOM:
			fordFulkerson = new MaxFlowUsingRandomDfs();
			break;
		default:
			throw new Exception("Algorithm is not implemented yet!");
		}

		FlowMetric flowMetric = new FlowMetric(graph, algorithm);

		MainApplication.metrics.add(flowMetric);
		Integer maxFlow = fordFulkerson.getMaximumFlow(graph, flowMetric);
		System.out.println("MAXIMUM FLOW=" + maxFlow);
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Completed processing algorithm:" + algorithm);
		graph.printRGraph();
		System.out.println("------------------------------------------------------------------------");

		return maxFlow;
	}
}
