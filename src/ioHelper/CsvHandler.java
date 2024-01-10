package ioHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Model.Constants;
import Model.Graph;
import Model.Point;

public class CsvHandler {

	public Graph loadGraphFromFile(String filePathForGraphEdges, String filePathForGraphNodes) throws IOException {
		Graph graph = readGraphNodesFromCSV(filePathForGraphNodes);
		if (graph != null) {
			readGraphEdgesFromCSV(filePathForGraphEdges, graph);
		}
		return graph;
	}

	// Function to read graph from CSV file
	private Graph readGraphNodesFromCSV(String filePath) throws IOException {
		Graph graph = null;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String parsedLine[] = line.split(Constants.DELIMETER);
				if (graph == null) {
					int maxSize = Integer.parseInt(parsedLine[0]);
					int sourceNode = Integer.parseInt(parsedLine[1]);
					int sinkVertex = Integer.parseInt(parsedLine[2]);
					int upperCapacity = Integer.parseInt(parsedLine[3]);
					float maximumDistanceBetweenNodes = Float.parseFloat(parsedLine[4]);
					List<Integer> longestAcyclicPath = readLongestAcyclicPath(parsedLine[5]);

					graph = new Graph(maxSize, upperCapacity);
					graph.setSourceVertex(sourceNode);
					graph.setSinkVertex(sinkVertex);
					graph.setMaximumDistanceBetweenNodes(maximumDistanceBetweenNodes);
					graph.setLongestAcyclicPath(longestAcyclicPath);
				} else {
					int vertex = Integer.parseInt(parsedLine[0]);
					double xCoordinate = Double.parseDouble(parsedLine[1]);
					double yCoordinate = Double.parseDouble(parsedLine[2]);
					Point pt = new Point(vertex, xCoordinate, yCoordinate);
					graph.getPointMetadata().put(vertex, pt);
				}
			}
		}
		return graph;
	}

	private List<Integer> readLongestAcyclicPath(String str) {
		List<Integer> path = new LinkedList<>();
		if (str != null && str.trim() != "") {
			System.out.println(str);
			List<String> pathInStr = Arrays.asList(str.split("-"));
			System.out.println(pathInStr);
			pathInStr.forEach(now -> path.add(Integer.parseInt(now)));
		}
		return path;
	}

	private void readGraphEdgesFromCSV(String filePath, Graph graph) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				// v, u, capcity, used capacity
				String parsedLine[] = line.split(Constants.DELIMETER);
				int sourceVertex = Integer.parseInt(parsedLine[0]);
				int targetVertex = Integer.parseInt(parsedLine[1]);
				int capcity = Integer.parseInt(parsedLine[2]);
				int usedCapacity = Integer.parseInt(parsedLine[3]);
				System.out.println("sourc" + sourceVertex + ", targetVertex=" + targetVertex + ", capcity" + capcity
						+ ", usedCapacity" + usedCapacity);
				graph.getCapacity()[sourceVertex][targetVertex] = capcity;
				graph.getUsedCapacity()[sourceVertex][targetVertex] = usedCapacity;
			}
		}
	}

	// Function to write graph to CSV file
	public void writeGraphEdgesToCSV(Graph graph, String filePath) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			Integer[][] capacity = graph.getCapacity();
			Integer[][] usedCapacity = graph.getUsedCapacity();
			for (int i = 0; i < graph.getMaxSize(); i++) {
				for (int j = 0; j < graph.getMaxSize(); j++) {
					if (capacity[i][j] != null && capacity[i][j] > 0) {
						bw.write(i + Constants.DELIMETER + j + Constants.DELIMETER
								+ (capacity[i] != null && capacity[i][j] == null ? 0 : capacity[i][j])
								+ Constants.DELIMETER
								+ (usedCapacity[i] != null && usedCapacity[i][j] == null ? 0 : usedCapacity[i][j]));
						bw.newLine();
					}
				}
			}
		}
	}

	public void writeGraphNodesToCSV(Graph graph, String filePath) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			Integer maxSize = graph.getMaxSize();
			Integer source = graph.getSourceVertex();
			Integer sinkVertex = graph.getSinkVertex();
			bw.write(maxSize + Constants.DELIMETER + source + Constants.DELIMETER + sinkVertex + Constants.DELIMETER
					+ graph.getUPPER_CAPACITY() + Constants.DELIMETER + graph.getMaximumDistanceBetweenNodes()
					+ Constants.DELIMETER + getLongestAcyclicPath(graph.getLongestAcyclicPath()));
			bw.newLine();
			Map<Integer, Point> metadata = graph.getPointMetadata();
			for (Entry<Integer, Point> now : metadata.entrySet()) {
				bw.write(now.getValue().toString());
				bw.newLine();
			}
		}
	}

	private String getLongestAcyclicPath(List<Integer> longestAcyclicPath) {
		StringBuilder str = new StringBuilder();
		if (longestAcyclicPath != null) {
			longestAcyclicPath.forEach(now -> str.append(now + "-"));
			return str.substring(0, str.length() - 1);
		}
		return str.toString();
	}
}
