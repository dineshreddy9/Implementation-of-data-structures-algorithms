/** @author Dinesh Reddy Thokala
 * 	Bounded Queue
 * 	Ver 1.0: 09/03/2018
 */

package dxt161330;

import java.util.Arrays;
import java.util.Scanner;

public class BoundedQueue<T> {
	int capacity, size;
	int front, rear;
	Object qarray[]; 
	
	// Constructor to initialize the array
	BoundedQueue(int size){
		capacity = size;
		front = rear = -1;
		this.size = 0;
		qarray = new Object[capacity];
	}

	// add a new element x at the rear of the queue
	public boolean offer(T x) {
		if(size == capacity) {
			return false;
		} else {
			if(isEmpty()) {
				front = rear = 0;
				qarray[rear] = x;
				size++;
				return true;
			} else {
				rear = ((rear + 1 )%capacity);
				qarray[rear] = x;
				size++;
				return true;
			}
		}
	}

	// remove and return the element at the front of the queue
	public T poll() {
		if(isEmpty()) {
			return null;
		} else {
			T temp = (T) qarray[front];
			if(size==1) {
				front = rear = -1;
			} else {
				front = ((front+1)%capacity);
			}
			size--;
			return temp;
		}
	}

	// return front element, without removing it
	public T peek() {
		if(isEmpty()) {
			return null;
		} else {
			return (T) qarray[front];
		}
	}
	
	// return the number of elements in the queue
	public int size() {
		return size;
	}
	
	// check if the queue is empty
	boolean isEmpty() {
		return (size == 0);
	}
	
	// clear the queue (size=0)
	public void clear() {
		size = 0;
		front = rear = -1;
	}
	
	// fill user supplied array with the elements of the queue, in queue order
	public void toArray(T[] a) {
		int i = -1;
		int sizetemp = size;
		int fronttemp = front;
		if(sizetemp==0) {
			System.out.println("queue is empty");
		}
		while(sizetemp!=0) {
			a[++i] = (T) qarray[fronttemp];
			fronttemp = (fronttemp+1)%capacity;
			sizetemp--;
		}
	}
	
	public void printArray() {
		int sizetemp = size;
		int fronttemp = front;
		if(sizetemp==0) {
			System.out.println("queue is empty");
		}
		while(sizetemp!=0) {
			System.out.print(qarray[fronttemp]+" ");
			fronttemp = (fronttemp+1)%capacity;
			sizetemp--;
		}
	}
	
	public static void main(String[] args) {
		
		BoundedQueue<Integer> bq = new BoundedQueue<>(5);
		Scanner sc = new Scanner(System.in);
		whileloop:
		while(sc.hasNext()) {
			int num = sc.nextInt();
			switch(num) {
			case 1: // add a new element x at the rear of the queue
				System.out.println("Enter element");
				System.out.println(bq.offer(new Integer(sc.nextInt())));
				break;
			case 2: // remove and return the element at the front of the queue
				System.out.println(bq.poll());
				break;
			case 3: // return front element, without removing it
				System.out.println(bq.peek());
				break;
			case 4: // return the number of elements in the queue
				System.out.println(bq.size());
				break;
			case 5: // clear the queue (size=0)
				bq.clear();
				break;
			case 6: // fill user supplied array with the elements of the queue
				Integer[] ar = new Integer[bq.size];
				bq.toArray(ar);
				System.out.println(Arrays.toString(ar));
				break;
			case 7:
				bq.printArray();
				break;
			default:
				break whileloop;
			}
		}
	}
}

