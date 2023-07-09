import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Magazin {

	// The number of nodes
	static int n;
	// The number of questions
	static int q;

	// Structure which contain all nodes
	static ArrayList<Node> nodes;

	// List of nodes based by Dfs crossing
	static ArrayList<Node> dfsOrder;
	static int sizeDfsOrder;

	static class Node {

		// Value of node
		public int value;

		// Variable which count number of nodes which have this node as a root
		public int children;
		// Index of node in dfsOrder list
		public int indexDfsOrder;

		// List of nodes which this node can reach in a straight way
		public ArrayList<Node> neighbours;

		public Node(int value) {
			this.value = value;
			this.neighbours = new ArrayList<>();
			this.children = 0;
		}

	}

	// In this block of code I saved order of each node based by Dfs algorithm
	static void dfs(Node nodeRoot) {
		dfsOrder.add(nodeRoot);
		sizeDfsOrder++;
		nodeRoot.indexDfsOrder = sizeDfsOrder;
		for (Node auxNode : nodeRoot.neighbours) {
			dfs(auxNode);
			nodeRoot.children += auxNode.children + 1;
		}
	}

	static void solveMagazinProblem(Scanner sc) {
		try {
			FileWriter fw = new FileWriter("magazin.out");

			// Iterate through each question and checked if from startNode I could reach a
			// node at the
			// specified distance
			for (int indexQuestion = 0; indexQuestion < q; indexQuestion++) {
				int nodeStart = sc.nextInt();
				int distance = sc.nextInt();
				if (distance <= nodes.get(nodeStart).children) {
					try {
						int pozNodeStart = nodes.get(nodeStart).indexDfsOrder;
						fw.write(dfsOrder.get(pozNodeStart + distance).value + "\n");
					} catch (IndexOutOfBoundsException e) {
						fw.write(-1 + "\n");
					}
				} else {
					fw.write(-1 + "\n");
				}
			}

			fw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args) {
		// long time = new Date().getTime();
		try {
			Scanner sc = new Scanner(new File("magazin.in"));

			// Initialize all variables
			n = sc.nextInt();
			q = sc.nextInt();
			nodes = new ArrayList<Node>();
			dfsOrder = new ArrayList<Node>();
			dfsOrder.add(new Node(0));
			sizeDfsOrder = 0;

			// Create and set each node
			nodes.add(new Node(0));
			nodes.add(new Node(1));
			for (int index = 2; index <= n; index++) {
				nodes.add(new Node(index));
			}

			// Initialize each node's list of neighbours
			for (int index = 2; index <= n; index++) {
				int nodeParent = sc.nextInt();
				nodes.get(nodeParent).neighbours.add(nodes.get(index));
			}

			// Call Dfs algorithm to save each node in a list based by a specific order
			dfs(nodes.get(1));

			// Call function which solve problem based by list calculated before
			solveMagazinProblem(sc);

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}
}
