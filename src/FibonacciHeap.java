import java.util.HashMap;
import java.util.HashSet;

public class FibonacciHeap {
	HashMap<String, Node> map = new HashMap<>();
	String max;
	int q = 0;

	public static class Node {
		String key;
		int count;
		private HashSet<String> children = new HashSet<>();
		private String parent;
		private String next;
		private String pre;

		boolean childCut;

		@Override
		public String toString() {
			return "(" + pre + "< " + key + "{" + count + "}" + "> " + next + ")";
		}
	}

	public void insert(String key, int value) {
		Node node = map.get(key);
		if (node == null) {
			node = new Node();
			node.key = key;
			node.count = value;
			insertInQueue(key, node);
		} else {
			increaseKey(key, value);
		}
		map.put(key, node);
	}

	private void increaseKey(String key, int value) {
		// if (q > 2)
		// System.out.println("start increaseKey: " + key);
		Node node = map.get(key);
		node.count += value;
		// System.out.println("increase key: " + key + " " + node.count);
		if (node.parent != null) {
			Node parentNode = map.get(node.parent);
			String currentKey = key;
			while (parentNode != null && parentNode.childCut) {
				// System.out.println("p increase key: " + parentNode);
				// System.out.println("increaseKey: " + currentKey);
				parentNode.children.remove(currentKey);
				parentNode.childCut = false;
				node.parent = null;
				insertInQueue(currentKey, node);
				currentKey = parentNode.key;
				node = parentNode;
				parentNode = map.get(parentNode.parent);
			}
			if (parentNode != null) {
				parentNode.children.remove(currentKey);
				parentNode.childCut = true;
				node.parent = null;
				// if (q > 2)
				// System.out.println("--- increaseKey: " + currentKey);
				insertInQueue(currentKey, node);
			}
		} else {
			// System.out.println("p was null");

			if (map.get(max).count < node.count) {
				max = key;
				// System.out.println("new max " + max);
			}
		}
		node.childCut = false;
		if (q > 1)
			printHeap();
	}

	private void insertInQueue(String key, Node node) {

		if (max == null) {
			node.next = key;
			node.pre = key;
			max = key;
		} else {
			// System.out.println("--- insertInQueue: " + key);

			if (q > 2) {

				printHeap();
			}
			node.pre = max;
			node.next = map.get(max).next;
			map.get(max).next = key;
			map.get(node.next).pre = key;
			if (map.get(max).count < node.count) {
				max = key;
				// System.out.println("new max " + max);
			}
		}
	}

	public int size() {
		return map.size();
	}

	public Node findMax() {
		return map.get(max);
	}

	public Node removeMax() {
		// System.out.println("start removeMax: " + max);

		// printHeap();
		if (max == null)
			return null;
		Node maxNode = map.get(max);
		if (maxNode == null)
			return null;
		for (String childKey : maxNode.children) {
			map.get(childKey).parent = null;
			insertInQueue(childKey, map.get(childKey));
		}
		String toRemove = max;
		if (map.size() == 1) {
			max = null;
			map.clear();
			return maxNode;
		} else {
			if (q > 3) {
				// System.out.println(maxNode + " [" + maxNode.children + "]");
				printHeap();
			}
			removeNode(maxNode);
			max = maxNode.next;
			String temp = map.get(max).next;
			while (temp != null) {
				if (map.get(max).count < map.get(temp).count) {
					max = temp;
				}
				temp = map.get(temp).next;
				if (temp.equals(max)) {
					break;
				}
			}
		}
		// System.out.println(" -> " + max);
		merge();
		map.remove(toRemove);
		if (q > 2)
			printHeap();
		return maxNode;
	}

	private void removeNode(Node node) {
		map.get(node.next).pre = node.pre;
		map.get(node.pre).next = node.next;
	}

	private void merge() {
		// printHeap();
		HashMap<Integer, Node> degreeMap = new HashMap<>();
		String temp = max;
		while (temp != null) {
			Node node = map.get(temp);
			String next = node.next;
			while (degreeMap.containsKey(node.children.size())) {
				Node pre = degreeMap.remove(node.children.size());
				if (pre.key.equals(node.key)) {
					break;
				}
				// System.out.println(node + " " + pre);
				if (pre.count >= node.count) {
					pre.children.add(node.key);
					node.parent = pre.key;
					removeNode(node);
					if (node.key.equals(max)) {
						max = pre.key;
					}
					node.next = null;
					node.pre = null;
					node = pre;
				} else {
					node.children.add(pre.key);
					pre.parent = node.key;
					removeNode(pre);
					pre.next = null;
					pre.pre = null;
				}
			}
			degreeMap.put(node.children.size(), node);
			// if (q > 2) {
			// System.out.println("=== " + temp);
			// printHeap();
			// System.out.println(degreeMap);
			// }
			temp = next;

			if (temp.equals(max)) {
				break;
			}
		}
	}

	public String[] query(int n) {
		// System.out.println("start query: " + n);
		// System.out.println("b---------");
		// printHeap();
		// System.out.println("b---------");
		q++;
		String[] result = new String[n];
		Node[] removedNodes = new Node[n];

		for (int i = 0; i < n; i++) {
			removedNodes[i] = this.removeMax();
			result[i] = removedNodes[i].key;
		}
		for (int i = 0; i < n; i++) {
			this.insert(removedNodes[i].key, removedNodes[i].count);
		}
		// System.out.println("---------");
		// printHeap();
		// System.out.println("---------");
		return result;
	}

	public void printHeap() {
		// String temp = max;
		// while (true) {
		// System.out.print(" <- " + map.get(temp) + "(" +
		// map.get(temp).children + ") -> ");
		// temp = map.get(temp).next;
		// if (temp.equals(max)) {
		// break;
		// }
		// }
		// System.out.println();
	}

}
