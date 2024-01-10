package graphHandler;

import Model.Graph;

//of n, r, and upperCap values
public interface GraphHandler {
	/**
	 * Reads file from CSV
	 * 
	 * @param filePath
	 * @return
	 */
	public Graph readGraphFromCsv(String filePathForGraphEdges, String filePathForGraphNodes);

	/**
	 * 
	 * Writes file from CSV
	 * 
	 * @param graph
	 * @return
	 */
	public String storeGraphToFile(Graph graph, String filePathForGraphEdges, String filePathForGraphNodes);

	/**
	 * 
	 * @param upperCapacity
	 * @return
	 */
	public Graph generateGraph(Integer numberOfVertices, Float maximumDistance, Integer upperCapacity);

	public void findSourceAndSinkNode(Graph graph);

}
