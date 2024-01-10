package graphHandlerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import Model.Graph;
import Model.Point;
import graphHandler.GraphHandler;
import ioHelper.CsvHandler;

public class GraphHandlerImpl implements GraphHandler {

	private CsvHandler csvHandler;

	public GraphHandlerImpl() {
		this.csvHandler = new CsvHandler();
	}

	@Override
	public Graph readGraphFromCsv(String filePathForGraphEdges, String filePathForGraphNodes) {
		try {
			Graph gh = csvHandler.loadGraphFromFile(filePathForGraphEdges, filePathForGraphNodes);
			if (gh != null) {
				System.out.println("Success reading graph.");
			}
			return gh;
		} catch (Exception e) {
			System.out.println("Unable to parse graph from csv file, filepath=" + filePathForGraphEdges);
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String storeGraphToFile(Graph graph, String filePathForGraphEdges, String filePathForGraphNodes) {
		try {
			this.csvHandler.writeGraphEdgesToCSV(graph, filePathForGraphEdges);
			this.csvHandler.writeGraphNodesToCSV(graph, filePathForGraphNodes);
			System.out.println("Success storing graph.");
		} catch (IOException e) {
			System.out.println("Unable to write graph to csv file, filepath=" + filePathForGraphEdges);
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Graph generateGraph(Integer n, Float r, Integer upperCap) {
		Graph graph = new Graph(n, upperCap);
		graph.setMaximumDistanceBetweenNodes(r);
		
		Random random = new Random();
		// Generate random Cartesian coordinates for each vertex
		for (int u = 1; u <= n; u++) {
			double x = random.nextDouble();
			double y = random.nextDouble();
			graph.getPointMetadata().put(u, new Point(u, x, y));
		}

		System.out.println(graph.getPointMetadata());
		// Generate edges of length ≤ r without creating parallel edges

		for (int u = 1; u <= n; u++) {
			for (int v = 1; v <= n; v++) {
				if (u != v && !checkIfEdgeExists(graph, u - 1, v - 1)
						&& getDistanceBetweenPoints(graph, u, v) <= Math.pow(r, 2)) {
					double rand = random.nextDouble();
					if (rand < 0.5) {
						graph.getCapacity()[u - 1][v - 1] = random.nextInt(upperCap) + 1;
					}

					else {
						graph.getCapacity()[v - 1][u - 1] = random.nextInt(upperCap) + 1;
					}

				}
			}
		}

		return graph;
	}

	private boolean checkIfEdgeExists(Graph graph, int u, int v) {
		return (graph.getCapacity()[u][v] == null && graph.getCapacity()[v][u] == null) ? Boolean.FALSE : Boolean.TRUE;
	}

	private double getDistanceBetweenPoints(Graph graph, int u, int v) {
		return Math.pow(graph.getPointMetadata().get(u).getX() - graph.getPointMetadata().get(v).getX(), 2)
				+ Math.pow(graph.getPointMetadata().get(u).getY() - graph.getPointMetadata().get(v).getY(), 2);
	}

	public List<Integer> findLongestAcyclicPathBFS(Graph graph, int sourceVertex) {
		Queue<List<Integer>> queue = new LinkedList<>();
		Set<Integer> visited = new HashSet<>();
		List<Integer> longestPath = new ArrayList<>();

		queue.add(Collections.singletonList(sourceVertex));

		while (!queue.isEmpty()) {
			List<Integer> currPath = queue.poll();
			int currVertex = currPath.get(currPath.size() - 1);

			if (!visited.contains(currVertex)) {
				visited.add(currVertex);

				// Check if the current path is longer than the current longest path
				if (currPath.size() > longestPath.size()) {
					longestPath = new ArrayList<>(currPath);
				}

				for (Integer neighbor : graph.getNeighbors(currVertex)) {
					if (!visited.contains(neighbor)) {
						List<Integer> newPath = new ArrayList<>(currPath);
						newPath.add(neighbor);
						queue.add(newPath);
					}
				}
			}
		}

		return longestPath;
	}

	@Override
	public void findSourceAndSinkNode(Graph graph) {
		Random rand = new Random();
		int sourceNode = rand.nextInt(graph.getMaxSize());
		List<Integer> maxPath = findLongestAcyclicPathBFS(graph, sourceNode);
		while (maxPath.size() == 0) {
			sourceNode = rand.nextInt(graph.getMaxSize());
			maxPath = findLongestAcyclicPathBFS(graph, sourceNode);
		}

		System.out.println(maxPath);
		graph.setSourceVertex(sourceNode);
		graph.setSinkVertex(maxPath.get(maxPath.size() - 1));
		graph.setLongestAcyclicPath(maxPath);
	}
}
