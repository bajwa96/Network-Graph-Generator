package runner;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Model.Constants;
import Model.FlowMetric;
import Model.Graph;
import graphHandler.GraphHandler;
import graphHandlerImpl.GraphHandlerImpl;
import maxFlow.FordFulkerson;

public class MainApplication {

	public static List<FlowMetric> metrics = new LinkedList<>();

//"Output/graphEdges.csv" "Output/graphNodes.csv" "Output/graphEdges.csv" "Output/graphNodes.csv"
	public static void main(String args[]) throws Exception {
		GraphHandler gh = new GraphHandlerImpl();
		Graph graph = null;
//		graph = readGraph(args, gh, graph);

		if (graph == null) {
			try (Scanner sc = new Scanner(System.in)) {
				System.out.println("Please input no Of Nodes, n:");
				String n = sc.nextLine();
				System.out.println("Maximum distance between nodes sharing an edge, r:");
				String r = sc.nextLine();
				System.out.println("UpperCap, upperCap:");
				String upperCap = sc.nextLine();

				graph = gh.generateGraph(Integer.parseInt(n), Float.parseFloat(r), Integer.parseInt(upperCap));
				gh.findSourceAndSinkNode(graph);
			} catch (Exception e) {
				System.out.println("Unable to parse the input for graph, exiting the application");
				System.exit(0);
			}
		}

		storeGraph(args, gh, graph);

		FordFulkerson.getMaximumFlow(graph, Constants.SHORTEST_AUGMENTING_PATH);
		FordFulkerson.getMaximumFlow(graph, Constants.DFS_LIKE);
		FordFulkerson.getMaximumFlow(graph, Constants.MAXIMUM_CAPACITY);
		FordFulkerson.getMaximumFlow(graph, Constants.RANDOM);

		System.out.println("--------------------------------------------------------------------------");
		for (FlowMetric now : MainApplication.metrics) {
			System.out.println(now);
		}
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("Thanks for using the application.");

	}

	private static void storeGraph(String[] args, GraphHandler gh, Graph graph) {
		if (args != null && args.length >= 4) {
			String filePathForGraphEdges = String.valueOf(args[2]);
			String filePathForGraphNodes = String.valueOf(args[3]);
			if (filePathForGraphEdges != null && filePathForGraphEdges.trim() != "" && filePathForGraphNodes != null
					&& filePathForGraphNodes.trim() != null) {
				System.out.println("Storing graph to csv file, filePathForGraphEdges:" + filePathForGraphEdges
						+ ", filePathForGraphNodes:" + filePathForGraphNodes);
				gh.storeGraphToFile(graph, filePathForGraphEdges, filePathForGraphNodes);
			} else {
				System.out.println("File path for storing graph not found");
			}
		} else {
			System.out.println("File path for storing graph not found");
		}
	}

	private static Graph readGraph(String[] args, GraphHandler gh, Graph graph) {
		if (args != null && args.length >= 2) {
			String filePathForGraphEdges = String.valueOf(args[0]);
			String filePathForGraphNodes = String.valueOf(args[1]);
			if (filePathForGraphEdges != null && filePathForGraphEdges.trim() != "" && filePathForGraphNodes != null
					&& filePathForGraphNodes.trim() != null) {
				System.out.println("Reading graph from csv file, filePathForGraphEdges:" + filePathForGraphEdges
						+ ", filePathForGraphNodes:" + filePathForGraphNodes);
				graph = gh.readGraphFromCsv(filePathForGraphEdges, filePathForGraphNodes);
			} else {
				System.out.println("File path for reading graph not found");
			}
		} else {
			System.out.println("File path for reading graph not found");
		}
		return graph;
	}
}
