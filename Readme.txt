Project COMP 6651 MAX Flow

Description
	This Java application generates random network graphs, stores them in CSV files, and performs various algorithms(related to max flow) on them.

Features
	Generate Graph: Generate a random graph with specified parameters.
	Read/Write CSV: Read and write graph data to CSV files.
	Longest Acyclic Path: Find the longest acyclic path using BFS.
	Max Flow Algorithms:
	Shortest Augmenting Path (SAP): Ford-Fulkerson algorithm with Dijkstra's shortest path modification.
	DFS-like: Ford-Fulkerson algorithm with DFS-like search modification.
	Maximum Capacity (MaxCap): Ford-Fulkerson algorithm with maximum capacity augmenting path.

Getting Started
	Prerequisites
		Java installed on your machine.
	Build the project
	Under runner package, MainApplication.java should be invoked
	
	Run the JAR file:
		The jar is available under my location /home/n/na_bajwa/Documents/ on computation.encs.concordia.ca
		java -jar build/libs/network-graph-generator.jar "Output/graphEdges.csv" "Output/graphNodes.csv" "Output/graphEdges.csv" "Output/graphNodes.csv"
		
		If running without arguments the application will ask for value of n,r and upper capacity.
		
		Replace the CSV file paths with your desired output paths.
			Here all four paths are optional arguments, 
			1)For loading graph from system, there are two arguments: 
				filePathForGraphEdges: which stores the information related to Graph edges
				filePathForGraphNodes: which stores the information related to Graph Nodes(including coordinates for vertices)
			2)Similarly for storing graph from system, there are two arguments: 
				filePathForGraphEdges: which stores the information related to Graph edges
				filePathForGraphNodes: which stores the information related to Graph Nodes(including coordinates for vertices)

Implementation Details
	The project is implemented in Java using simple java project.
	The application uses various algorithms for graph generation, BFS, and Ford-Fulkerson for Max Flow.
	CSV files are used to store and read graph data.

Results
The application has been tested with different sets of parameters (n, r, upperCap) to ensure the correctness and efficiency of the algorithms.