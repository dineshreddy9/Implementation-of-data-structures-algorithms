/*
 * @author Dinesh Reddy Thokala
 * Binary Search Tree
 * Ver 1.0: 09/26/2018
 */

package dxt161330;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
	static class Entry<T> {
		T element;
		Entry<T> left, right;

		public Entry(T x, Entry<T> left, Entry<T> right) {
			this.element = x;
			this.left = left;
			this.right = right;
		}
	}

	Entry<T> root;
	int size;
	//stack used by find method
	Stack<Entry<T>> stack = new Stack<>();
	//stack used by inorder method
	Stack<Entry<T>> istack = new Stack<>();

	public BinarySearchTree() {
		root = null;
		size = 0;
	}


	//returns true if tree contains x, else returns false
	public boolean contains(T x) {
		Entry<T> node = find(x);
		if(node==null || x.compareTo(node.element)!=0) {
			return false;
		} else {
			return true;
		} 
	}

	//Element in tree that is equal to x is returned, null otherwise.
	public T get(T x) {
		Entry<T> node = find(x);
		if(x.compareTo(node.element)==0){
			return x;
		} else {
			return null;
		}
	}

	// Helper method which finds the node that contains element x
	public Entry<T> find(T x) {
		stack.push(null);
		return find(root, x);
	}

	public Entry<T> find(Entry<T> root, T x) {
		if(root==null || x.compareTo(root.element)==0) {
			return root;
		}
		while(true) {
			if(x.compareTo(root.element)==-1) {
				if(root.left == null) {
					break;
				}
				else {
					stack.push(root);
					root = root.left;
				}
			}
			else if(x.compareTo(root.element)==0) {
				break;
			}
			else {
				if(root.right == null) {
					break;
				}
				else {
					stack.push(root);
					root = root.right;
				}
			}
		}
		return root;
	}

	/** Adds x to tree. 
	 *  If tree contains a node with same key, replaces element by x.
	 *  Returns true if x is a new element added to tree.
	 */
	public boolean add(T x) {
		if(size == 0) {
			root = new Entry<>(x, null, null);
			size++;
			return true;
		}
		else {
			Entry<T> node = find(x);
			if(x.compareTo(node.element)==0) {
				node.element = x;
				return false;
			} else if(x.compareTo(node.element)==-1) {
				node.left = new Entry<>(x, null, null);
			} else {
				node.right = new Entry<>(x, null, null);
			}
			size++;
			return true;
		}
	}

	/** Remove x from tree. 
	 *  Returns x if found, otherwise returns null
	 */
	public T remove(T x) {
		if(size == 0) {
			return null;
		}
		Entry<T> node = find(x);
		if(x.compareTo(node.element)!=0) {
			return null;
		}
		if(node.left==null || node.right==null) {
			bypass(node);
		}
		else {
			stack.push(node);
			Entry<T> minRight = find(node.right, x);
			node.element = minRight.element;
			bypass(minRight);
		}
		size--;
		return x;
	}
	
	//helper method for remove() to remove a node and attach the links
	public void bypass(Entry<T> node) {
		Entry<T> parent = stack.peek();
		Entry<T> child = node.left==null?node.right: node.left;
		if(parent == null) {
			root = child;
		} else if(parent.left==node) {
			parent.left = child;
		} else {
			parent.right = child;
		}
	}


	// Returns minimum element of BST
	public T min() {
		if(size == 0) {
			return null;
		} else {
			Entry<T> node = root;
			while(node.left!=null) {
				node = node.left;
			}
			return node.element;
		}
	}

	// Returns maximum element of BST
	public T max() {
		if(size == 0) {
			return null;
		} else {
			Entry<T> node = root;
			while(node.right!=null) {
				node = node.right;
			}
			return node.element;
		}
	}

	// Creates an array with the elements using in-order traversal of tree
	public Comparable[] toArray() {
		Comparable[] arr = new Comparable[size];
		Entry<T> node = root;
		inorder(node, arr, -1);
		return arr;
	}
	
	//helper method for toArray() for inorder traversal
	public void inorder(Entry<T> node,Comparable[] array, int i) {
		while(true) {
			if(node==null && istack.isEmpty()) { return ;}
			while(node!=null) {
				istack.push(node);
				node = node.left;
			}
			node = istack.pop();
			array[++i] = node.element;
			node = node.right;
		}
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> t = new BinarySearchTree<>();
		Scanner in = new Scanner(System.in);
		while(in.hasNext()) {
			int x = in.nextInt();
			if(x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				t.printTree();
			} else if(x < 0) {
				System.out.print("Remove " + x + " : ");
				t.remove(-x);
				t.printTree();
			} else {
				Comparable[] arr = t.toArray();
				System.out.print("Final: ");
				for(int i=0; i<t.size; i++) {
					System.out.print(arr[i] + " ");
				}
				System.out.println();
				System.out.println("contains(9) " + t.contains(9));
				System.out.println("get(7) " + t.get(7));
				System.out.println("min " + t.min());
				System.out.println("max " + t.max());
				return;
			}           
		}
		
	}


	public void printTree() {
		System.out.print("[" + size + "]");
		printTree(root);
		System.out.println();
	}

	// Inorder traversal of tree
	void printTree(Entry<T> node) {
		if(node != null) {
			printTree(node.left);
			System.out.print(" " + node.element);
			printTree(node.right);
		}
	}

	public Iterator<T> iterator() {
		return null;
	}

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 
contains(9) true
get(7) 7
min 1
max 10

 */