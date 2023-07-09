import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Ferate {

	// The number of nodes
	static int n;
	// The number of edges
	static int m;
	// The root node
	static int rootNode;

	static class Graph {

		// The numbers of nodes
		public int nrNodes;
		// List of lists that represents each node that has straight way from index node
		ArrayList<ArrayList<Integer>> nodes;

		// The variable which represent index of each strong connected component in
		// Tarjan's algorithm solve
		int id;

		Graph(int n) {
			this.nrNodes = n;
			this.nodes = new ArrayList<>(nrNodes + 1);
			this.id = 1;
			for (int i = 0; i <= nrNodes; i++) {
				nodes.add(new ArrayList<>());
			}
		}

		// The method which add a new edge in graph
		void addEdge(int source, int destination) {
			nodes.get(source).add(destination);
		}

		// The method which traversal graph in dfs mode to search every strong connected
		// connection
		// This method check and increase parents array in case one node that wasn't
		// member of SCC went through SSC
		void dfs(int v, int[] ids, int[] low, int[] parents, Stack<Integer> stack) {
			stack.add(v);
			ids[v] = low[v] = id++;
			// Iterate through each rootNode children
			for (Integer children : nodes.get(v)) {

				// Here I check if child node is visited, in that case I will iterate each child
				// node of him
				if (ids[children] == -1) {
					dfs(children, ids, low, parents, stack);
				}
				// Here I check if child node is on the stack which mean I found a SSC
				if (stack.search(children) > 0) {
					low[v] = Math.min(low[v], low[children]);
				}
				// Here I check if child node went through another SSC
				if (low[children] > -1 && low[v] != low[children]) {
					parents[low[children]]++;
				}
			}

			// Here I clean up the stack until I find the root SSC node and make every id
			// that was created and
			// I won't use it again in the program -1
			if (ids[v] == low[v]) {
				for (Integer node = stack.pop(); node != v; node = stack.pop()) {
					parents[ids[node]] = -1;
					low[node] = ids[v];
				}
			}

		}

	}

	static int solveFerateProblem(Graph graph) {
		// The array with every id
		int[] ids = new int[graph.nrNodes + 1];
		// The array with every SCC marked with a specific id
		int[] low = new int[graph.nrNodes + 1];
		// The array that contain every number of parents for every SCC
		int[] parents = new int[graph.nrNodes + 1];
		// The stack used in Tarjan's algorithm
		Stack<Integer> stack = new Stack<>();

		for (int index = 1; index <= graph.nrNodes; index++) {
			ids[index] = -1;
		}

		// Iterate through every node that is unvisited
		for (int index = 1; index <= graph.nrNodes; index++) {
			if (ids[index] == -1) {
				graph.dfs(index, ids, low, parents, stack);
			}
		}

		// Count every index of SSC that it hadn't a parent
		int nrEdgeNeeded = 0;
		for (int index = 1; index <= graph.nrNodes; index++) {
			if (parents[index] == 0) {
				nrEdgeNeeded++;
			}
		}

		// The root node may not have a parent, but it should not be considered
		if (parents[low[rootNode]] == 0) {
			nrEdgeNeeded--;
		}
		return nrEdgeNeeded;
	}

	public static void main(String[] args) {

		try {
			Scanner sc = new Scanner(new File("ferate.in"));

			n = sc.nextInt();
			m = sc.nextInt();
			rootNode = sc.nextInt();

			Graph graph = new Graph(n);

			// In this block of code I read each and put them into graph
			for (int indexEdge = 1; indexEdge <= m; indexEdge++) {
				int sourceNode = sc.nextInt();
				int destinationNode = sc.nextInt();
				graph.addEdge(sourceNode, destinationNode);
			}

			int rez = solveFerateProblem(graph);

			try {
				FileWriter fw = new FileWriter("ferate.out");
				fw.write(Long.toString(rez));
				fw.close();

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}
}
