/**
 * @author Dinesh Reddy Thokala
 * Short Project 3: Priority queues
 * Ver 1.0: 09/19/18
 */


package dxt161330;
import java.util.Comparator;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
	T[] pq;
	Comparator<T> comp;
	int actualSize, allotedSize;

	// Constructor for building an empty priority queue using natural ordering of T
	public BinaryHeap(T[] q) {
		this(q, new Comparator<T>() {
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		});
	}


	// Constructor for building an empty priority queue with custom comparator
	public BinaryHeap(T[] q, Comparator<T> c) {
		pq = q;
		comp = c;
		this.actualSize = 0;
		this.allotedSize = pq.length;
	}

	// Print the elements of the priority queue
	public void printList() {
		for(int i =0; i< actualSize; i++) {
			System.out.print(pq[i]+" ");
		}
		System.out.println();
	}

	// Adds an element to priority queue
	// Throws exception if pq is full 
	public void add(T x) { 
		try {
			if(actualSize == allotedSize) {
				throw new Exception("pq is full");
			}
			else {
				pq[actualSize] = x;
				percolateUp(actualSize);
				actualSize++;
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	// Adds an element to priority queue
	// Returns false if pq is full 
	public boolean offer(T x) { 
		if(actualSize == allotedSize) {
			return false;
		}
		else {
			pq[actualSize] = x;
			percolateUp(actualSize);
			actualSize++;
			return true;
		}
	}

	// Removes first element from the priority queue
	// Throws exception if pq is empty
	public T remove() { 
		try {
			if(actualSize == 0) {
				throw new Exception("pq is empty");
			}
			else {
				T min = pq[0];
				pq[0] = pq[actualSize - 1];
				actualSize--;
				percolateDown(0);
				return min;
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// Removes first element from the priority queue
	// Returns null if pq is empty
	public T poll() { 
		if(actualSize == 0) {
			return null;
		}
		else {
			T min = pq[0];
			pq[0] = pq[actualSize - 1];
			actualSize--;
			percolateDown(0);
			return min;
		}
	}

	// Returns first element from the priority queue
	// Returns null if pq is empty
	public T peek() { 
		if(actualSize == 0) {
			return null;
		}
		return pq[0];
	}

	// Helper method for add and offer
	/** pq[i] may violate heap order with parent */
	void percolateUp(int i) { 
		T x = pq[i];
		while(i>0 && comp.compare(x, pq[parent(i)]) == -1) {
			pq[i] = pq[parent(i)];
			i = parent(i);
		}
		pq[i] = x;
	}

	// Helper method for remove and poll
	/** pq[i] may violate heap order with children */
	void percolateDown(int i) { 
		T x = pq[i];
		int c = leftChild(i);
		while(c<=(actualSize-1)) {
			if(c<(actualSize - 1) && (comp.compare(pq[c], pq[c+1])>0)) {
				c = c + 1;
			}
			if(comp.compare(x, pq[c])<=0) break;
			pq[i] = pq[c];
			i = c;
			c = leftChild(c);
		}
		pq[i] = x;
	}

	// returns the parent element's index of element at index i
	int parent(int i) {
		return (i-1)/2;
	}

	// returns the leftchild element's index of element at index i
	int leftChild(int i) {
		return 2*i + 1;
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the priority queue size");
		Integer[] arr = new Integer[sc.nextInt()];
		BinaryHeap<Integer> bh = new BinaryHeap<>(arr);
		whileloop:
			while(sc.hasNext()) {
				switch(sc.nextInt()) {
				case 1:
					// call add method
					bh.add(sc.nextInt());
					break;
				case 2:
					// call offer method
					System.out.println(bh.offer(sc.nextInt()));
					break;
				case 3:
					// call remove method
					System.out.println(bh.remove());
					break;
				case 4:
					// call poll method
					System.out.println("polled element is "+ bh.poll());
					break;
				case 5:
					// call peek method
					System.out.println(bh.peek());
					break;
				case 6:
					// exit while loop
					break whileloop;
				case 7: 
					// print pq array
					System.out.print("priority queue is ");
					bh.printList();
					break;
				}
			}
		sc.close();
	}

}