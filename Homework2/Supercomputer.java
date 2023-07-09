import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Supercomputer {

	static class Node {

		public int value;
		public int dataSet;
		public int degree = 0;

		public ArrayList<Node> neighbours;

		public Node(int dataSet) {
			this.dataSet = dataSet;
		}
	}

	// The number of nodes
	static int n;
	// The number of edges
	static int m;
	// The array I will use in case I start with set 1
	static ArrayList<Node> nodesOne = new ArrayList<>();
	// The array I will use in case I start with set 2
	static ArrayList<Node> nodesTwo = new ArrayList<>();
	// The priority queue help me to select the next node
	static PriorityQueue<Node> queue;
	// The variable use to save current set in a moment
	static int currentSet;

	// the method by which I compare two nodes
	public static class NodeComparator implements Comparator<Node> {
		@Override
		public int compare(Node n1, Node n2) {
			if (n1.dataSet == currentSet) {
				return -1;
			}
			if (n2.dataSet == currentSet) {
				return 1;
			}
			return 0;
		}
	}

	// This method to solve the program based by khan algorithm
	public static int searchContextSwitch(int info, ArrayList<Node> nodes) {

		int nrContextSwitch = 0;
		currentSet = info;

		// Here I search and add in queue each node which start with incoming degree
		// equal with 0
		for (int index = 1; index <= n; index++) {
			if (nodes.get(index).degree == 0) {
				queue.add((nodes.get(index)));
				nodes.get(index).degree = -1;
			}
		}

		while (!queue.isEmpty()) {

			Node nodeChecked = queue.poll();
			// When 'currentSet' changed I increased 'nrContextSwitch'
			if (nodeChecked.dataSet != currentSet) {
				currentSet = nodeChecked.dataSet;
				nrContextSwitch++;
			}

			// This block of code decrease incoming degree of each node that had
			// 'nodeChecked' as a parent and added it
			// in queue in case became 0
			for (Node auxNode : nodeChecked.neighbours) {
				auxNode.degree--;
				if (auxNode.degree == 0) {
					queue.add(auxNode);
					auxNode.degree = -1;
				}
			}

		}

		return nrContextSwitch;
	}

	public static void main(String[] args) {

		try {
			Scanner sc = new Scanner(new File("supercomputer.in"));

			n = sc.nextInt();
			m = sc.nextInt();

			nodesOne = new ArrayList<Node>();
			nodesTwo = new ArrayList<Node>();
			queue = new PriorityQueue<>(new NodeComparator());

			// The block of code which create, set value and initialize neighbours list for
			// each node
			nodesOne.add(new Node(0));
			nodesTwo.add(new Node(0));
			for (int index = 1; index <= n; index++) {
				int set = sc.nextInt();
				nodesOne.add(new Node(set));
				nodesOne.get(index).value = index;
				nodesOne.get(index).neighbours = new ArrayList<Node>();
				nodesTwo.add(new Node(set));
				nodesTwo.get(index).value = index;
				nodesTwo.get(index).neighbours = new ArrayList<Node>();
			}

			// The block of code which set each node's incoming degree
			for (int indexDependency = 1; indexDependency <= m; indexDependency++) {
				int nodeSource = sc.nextInt();
				int nodeDestination = sc.nextInt();
				nodesOne.get(nodeSource).neighbours.add(nodesOne.get(nodeDestination));
				nodesOne.get(nodeDestination).degree++;
				nodesTwo.get(nodeSource).neighbours.add(nodesTwo.get(nodeDestination));
				nodesTwo.get(nodeDestination).degree++;
			}

			int rez1, rez2;
			rez1 = searchContextSwitch(1, nodesOne);
			rez2 = searchContextSwitch(2, nodesTwo);

			// System.out.println("rez = " + Math.min(rez1, rez2));

			try {
				FileWriter fw = new FileWriter("supercomputer.out");
				fw.write(Long.toString(Math.min(rez1, rez2)));
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
