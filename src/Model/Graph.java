package Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Graph implements Cloneable {

	final private Integer UPPER_CAPACITY;

	private Integer maxSize;
	private Integer capacity[][];// capacity graph
	private Integer usedCapacity[][];
	private int parent[];
	private Map<Integer, Point> pointMetadata;
	private int residualGraph[][];
	private List<Integer> longestAcyclicPath;
	private int sourceVertex;
	private int sinkVertex;
	private int adjacencyMatrix[][];
	private float maximumDistanceBetweenNodes;

	public Graph(int maxSize, int upperCap) {
		this.UPPER_CAPACITY = upperCap;
		this.maxSize = maxSize;
		this.pointMetadata = new HashMap<>();
		this.parent = new int[maxSize];
		capacity = new Integer[maxSize][maxSize];
		usedCapacity = new Integer[maxSize][maxSize];
	}

	private Graph(Graph graph) {
		this.UPPER_CAPACITY = graph.UPPER_CAPACITY;
		this.capacity = graph.capacity.clone();
		this.usedCapacity = graph.usedCapacity.clone();
		this.maxSize = graph.maxSize;
		this.pointMetadata = new HashMap<>();
		for (Entry<Integer, Point> now : graph.getPointMetadata().entrySet()) {
			this.pointMetadata.put(now.getKey(), (Point) now.getValue().clone());
		}

	}

	public int[][] prepareResidualGraph() {
		this.residualGraph = new int[this.maxSize][this.maxSize];
		for (int i = 0; i < this.maxSize; i++)
			for (int j = 0; j < this.maxSize; j++)
				this.residualGraph[i][j] = (this.capacity[i] == null || this.capacity[i][j] == null) ? 0
						: (this.usedCapacity[i] == null || this.usedCapacity[i][j] == null) ? this.capacity[i][j]
								: this.capacity[i][j] - this.usedCapacity[i][j];

		return this.residualGraph;
	}

	public int[][] getResidualGraph() {
		return residualGraph;
	}

	/*
	 * 
	 * r 4 c 6 4->6 capacity20 4->6 used cap 10
	 * 
	 * 4 :: Point(name, x, ycordinte)
	 * 
	 * 
	 * 
	 *
	 */
	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Integer[][] getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer[][] capacity) {
		this.capacity = capacity;
	}

	public Integer[][] getUsedCapacity() {
		return usedCapacity;
	}

	public void setUsedCapacity(Integer[][] usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

	public Map<Integer, Point> getPointMetadata() {
		return pointMetadata;
	}

	public void setPointMetadata(Map<Integer, Point> pointMetadata) {
		this.pointMetadata = pointMetadata;
	}

	public Integer getUPPER_CAPACITY() {
		return UPPER_CAPACITY;
	}

	public List<Integer> getLongestAcyclicPath() {
		return longestAcyclicPath;
	}

	public void setLongestAcyclicPath(List<Integer> longestAcyclicPath) {
		this.longestAcyclicPath = longestAcyclicPath;
	}

	public void printRGraph() {
		if (this.residualGraph != null) {
			StringBuilder str = new StringBuilder("Residual Graph\n");
			for (int i = 0; i < this.maxSize; i++) {
				for (int j = 0; j < this.maxSize; j++) {
					str.append(this.residualGraph[i][j] == 0 ? "-" : this.residualGraph[i][j]);
					if (j < maxSize - 1)
						str.append("\t");
				}
				str.append("\n");
			}
			System.out.println(str.toString());
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Capacity:\n");
		for (int i = 0; i < this.maxSize; i++) {
			for (int j = 0; j < this.maxSize; j++) {
				str.append(this.capacity[i][j] == null ? "-" : this.capacity[i][j]);
				if (j < maxSize - 1)
					str.append("\t");
			}
			str.append("\n");
		}
//		str.append("Used-Capacity:\n");
//		for (int i = 0; i < this.maxSize; i++) {
//			for (int j = 0; j < this.maxSize; j++) {
//				str.append(this.usedCapacity[i][j] == null ? "-" : this.capacity[i][j]);
//				if (j < maxSize - 1)
//					str.append("\t");
//			}
//			str.append("\n");
//		}
		return str.toString();
	}

	public int[] getParent() {
		return parent;
	}

	public void setParent(int[] parent) {
		this.parent = parent;
	}

	public int getSourceVertex() {
		return sourceVertex;
	}

	public void setSourceVertex(int sourceVertex) {
		this.sourceVertex = sourceVertex;
	}

	public Integer getSinkVertex() {
		return sinkVertex;
	}

	public void setSinkVertex(Integer sinkVertex) {
		this.sinkVertex = sinkVertex;
	}

	@Override
	public Object clone() {
		return new Graph(this);
	}

	public int[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	public float getMaximumDistanceBetweenNodes() {
		return maximumDistanceBetweenNodes;
	}

	public void setMaximumDistanceBetweenNodes(float maximumDistanceBetweenNodes) {
		this.maximumDistanceBetweenNodes = maximumDistanceBetweenNodes;
	}

	public void setAdjacencyMatrix(int[][] adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}

	public Set<Integer> getNeighbors(int current) {
		Set<Integer> neighbors = new HashSet<>();
		for (int i = 0; i < this.maxSize; i++) {
			if (this.capacity[current][i] != null) {
				neighbors.add(i);
			}
		}
		return neighbors;
	}

	public void prepareAdjacencyMatrix(int weightOfEdge) {
		this.adjacencyMatrix = new int[this.maxSize][this.maxSize];
		for (int i = 0; i < this.maxSize; i++)
			for (int j = 0; j < this.maxSize; j++)
				this.adjacencyMatrix[i][j] = (this.capacity[i][j] == null) ? 0 : weightOfEdge;
	}

	public int[] getDistance(int sourceVertex) {
		int[] distanceForVertex = new int[this.maxSize];
		for (int i = 0; i < this.maxSize; i++)
			distanceForVertex[i] = this.adjacencyMatrix[sourceVertex][i];
		return distanceForVertex;
	}

	public int getTotalNumberOfEdges() {
		int noOfEdges = 0;
		for (int i = 0; i < this.maxSize; i++) {
			for (int j = 0; j < this.maxSize; j++) {
				if (this.capacity[i][j] != null && this.capacity[i][j]>0) {
					noOfEdges++;
				}
			}
		}
		return noOfEdges;
	}
}
