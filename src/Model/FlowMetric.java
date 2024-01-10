package Model;

public class FlowMetric {

	private String algorithmName;

	private Graph graph;
	private int paths;
	private int totalNumberOfEdgesInAugPath;

	public FlowMetric(Graph graph, String algorithm) {
		this.graph = graph;
		this.algorithmName = algorithm;
	}

	/**
	 * Calculated variables based on dynamic values
	 */
	private double calMeanLength;
	private double calMeanProportionalLength;

	public int getTotalNumberOfEdgesInAugPath() {
		return totalNumberOfEdgesInAugPath;
	}

	public void setTotalNumberOfEdgesInAugPath(int totalNumberOfEdgesInAugPath) {
		this.totalNumberOfEdgesInAugPath = totalNumberOfEdgesInAugPath;
	}

	public void setPaths(int paths) {
		this.paths = paths;
	}

	/**
	 * the number of augmenting paths required until Ford-Fulkerson completes
	 */
	public int getPaths() {
		return paths;
	}

	/**
	 * average length (i.e., number of edges) of the augmenting paths
	 * 
	 */
	public double getMeanLength() {
		this.calMeanLength = ((float) this.totalNumberOfEdgesInAugPath) / (float) this.paths;
		return this.calMeanLength;
	}

	/**
	 * mean proportional length (MPL): the average length of the augmenting path as
	 * a fraction of the longest acyclic path from s to t
	 * 
	 * @return
	 */
	public double getMeanProportionalLength() {
		this.calMeanProportionalLength = (this.getMeanLength() / (float) (graph.getLongestAcyclicPath().size() - 1));
		return this.calMeanProportionalLength;
	}

	/**
	 * total edges: the total number of edges in the graph
	 * 
	 * @return
	 */
	public int getTotalEdges() {
		return this.graph.getTotalNumberOfEdges();
	}

	@Override
	public String toString() {
//		System.out.println("Algorithm\tn\tr\tUpper Capacity\tpaths\tML\tMPL\tTotal Edges");
		return "[Algorithm:" + this.algorithmName + "\tn:" + this.graph.getMaxSize() + "\tr:"
				+ this.graph.getMaximumDistanceBetweenNodes() + "\tUpper Capacity:" + this.graph.getUPPER_CAPACITY()
				+ "\tPaths:" + this.paths + "\tML:" + this.getMeanLength() + "\tMPL:" + this.getMeanProportionalLength()
				+ "\tTotal Edges:" + this.getTotalEdges()+"]";
	}
}
