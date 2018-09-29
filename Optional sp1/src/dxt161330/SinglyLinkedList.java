/** @author dinesh
 * 	Singly Linked list: added new methods
 * 	ver 1.0: 09/01/2018: added methods addFirst(x), removeFirst(), remove(x)
 * 	ver 2.0: 09/02/2018: added methods get(index), set(index, x), add(index, x), remove(index)
 */


package dxt161330;
import java.util.Iterator;
import java.util.Scanner;


import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {

	/** Class Entry holds a single node of the list */
	static class Entry<E> {
		E element;
		Entry<E> next;

		Entry(E x, Entry<E> nxt) {
			element = x;
			next = nxt;
		}	
	}

	// Dummy header is used.  tail stores reference of tail element of list
	Entry<T> head, tail;
	int size;

	public SinglyLinkedList() {
		head = new Entry<>(null, null);
		tail = head;
		size = 0;
	}

	public Iterator<T> iterator() { return new SLLIterator(); }

	public SinglyLinkedListIterator<T> sllIterator(){
		return new SLLNewIterator();
	}

	public interface SinglyLinkedListIterator<T>{
		public void reset();
		public boolean hasNext();
		public T next();
		public void remove();
	}

	protected class SLLIterator implements Iterator<T> {
		Entry<T> cursor, prev;
		boolean ready;  // is item ready to be removed?

		SLLIterator() {
			cursor = head;
			prev = null;
			ready = false;
		}

		public boolean hasNext() {
			return cursor.next != null;
		}

		public T next() {
			prev = cursor;
			cursor = cursor.next;
			ready = true;
			return cursor.element;
		}

		// Removes the current element (retrieved by the most recent next())
		// Remove can be called only if next has been called and the element has not been removed
		public void remove() {
			if(!ready) {
				throw new NoSuchElementException();
			}
			prev.next = cursor.next;
			// Handle case when tail of a list is deleted
			if(cursor == tail) {
				tail = prev;
			}
			cursor = prev;
			ready = false;  // Calling remove again without calling next will result in exception thrown
			size--;
		}

	}  // end of class SLLIterator

	protected class SLLNewIterator extends SLLIterator implements SinglyLinkedListIterator<T>{
		public void reset() {
			cursor = head;
			prev = head;
		}
	}

	// Add new elements to the end of the list
	public void add(T x) {
		add(new Entry<>(x, null));
	}

	public void add(Entry<T> ent) {
		tail.next = ent;
		tail = tail.next;
		size++;
	}

	// add at the beginning of the list
	public void addFirst(T x) {
		Entry<T> ent = new Entry<>(x,null);
		ent.next = head.next;
		if(head.next==null)
			tail = ent;
		head.next = ent;
		size++;
	}

	// removes the first element of the list
	public void removeFirst() {
		Entry<T> rem = head.next;
		if(rem == null)
			throw new NoSuchElementException();
		if(rem.next==null) {
			tail = head;
			head.next = null;
		} else {
			head.next = rem.next;
		}
		size--;
	}

	// removes the first occurrence of element in the list
	public int remove(T x) {
		Entry<T> temp = head.next;
		Entry<T> previous = head;
		int index = 0;
		while(temp!=null) {
			if(temp.element == x) {
				if(temp.next==null) {
					tail = previous;
				}
				previous.next = temp.next;
				size--;
				return index;
			} else {
				previous = temp;
				temp = temp.next;
				index++;
			}
		}
		throw new NoSuchElementException();
	}

	// returns the element at index (first element is at index 0)
	public T get(int index) { 
		if(size<3 || size<=index) { throw new NoSuchElementException(); }
		else {
			int ind = 0;
			Entry<T> temp = head.next;
			while(temp!=null) {
				if(ind == index) {
					return temp.element;
				}
				temp = temp.next;
				ind++;
			}
		}
		throw new NoSuchElementException();
	}

	// replaces the element at given index to be x
	public void set(int index, T x) { 
		if(size<3 || size<=index) { throw new NoSuchElementException(); }
		else {
			int ind = 0;
			Entry<T> temp = head.next;
			while(temp!=null) {
				if(ind==index) {
					temp.element = x;
					return;
				}	
				temp = temp.next;
				ind++;
			}
		}
	}

	//	adds x as a new element at given index
	public void add(int index, T x) { 
		if(size<3 || size<=index) { throw new NoSuchElementException(); }
		else {
			int ind = 0;
			Entry<T> temp = head.next;
			Entry<T> previous = head;
			while(temp!=null) {
				if(ind == index) {
					Entry<T> ent = new Entry<>(x,null);
					ent.next = previous.next;
					previous.next = ent;
					size++;
				} 
				previous = temp;
				temp = temp.next;
				ind++;
			}
		}
	}

	// deletes and returns element at index
	public T removeIndex(int index) { 
		if(size<3 || size<=index) { throw new NoSuchElementException(); }
		else {
			int ind = 0;
			Entry<T> temp = head.next;
			Entry<T> previous = head;
			while(temp!=null) {
				if(ind == index) {
					if(temp.next==null) {
						tail = previous;
					}
					previous.next = temp.next;
					size--;
					return temp.element;
				} 
				previous = temp;
				temp = temp.next;
				ind++;
			}
		}
		throw new NoSuchElementException();
	}

	public void printList() {
		System.out.print(this.size + ": ");
		for(T item: this) {
			System.out.print(item + " ");
		}

		System.out.println();
	}


	// Rearrange the elements of the list by linking the elements at even index
	// followed by the elements at odd index. Implemented by rearranging pointers

	public void unzip() {
		if(size < 3) {  // Too few elements.  No change.
			return;
		}

		Entry<T> tail0 = head.next;
		Entry<T> head1 = tail0.next;
		Entry<T> tail1 = head1;
		Entry<T> c = tail1.next;
		int state = 0;

		// Invariant: tail0 is the tail of the chain of elements with even index.
		// tail1 is the tail of odd index chain.
		// c is current element to be processed.
		// state indicates the state of the finite state machine
		// state = i indicates that the current element is added after taili (i=0,1).
		while(c != null) {
			if(state == 0) {
				tail0.next = c;
				tail0 = c;
				c = c.next;
			} else {
				tail1.next = c;
				tail1 = c;
				c = c.next;
			}
			state = 1 - state;
		}
		tail0.next = head1;
		tail1.next = null;
		// Update the tail of the list
		tail = tail1;
	}

	public static void main(String[] args) throws NoSuchElementException {
		int n = 5;
		if(args.length > 0) {
			n = Integer.parseInt(args[0]);
		}

		SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
		for(int i=1; i<=n; i++) {
			lst.add(Integer.valueOf(i));
		}
		lst.printList();

		SinglyLinkedListIterator<Integer> it = lst.sllIterator();

		Scanner in = new Scanner(System.in);
		whileloop:
			while(in.hasNext()) {
				int com = in.nextInt();
				switch(com) {
				case 1:  // Move to next element and print it
					if (it.hasNext()) {
						System.out.println(it.next());
					} else {
						break whileloop;
					}
					break;
				case 2:  // Remove element
					it.remove();
					lst.printList();
					break;
				case 3:	// add at the beginning of the list
					lst.addFirst(6);
					it.reset();
					lst.printList();
					break;
				case 4:	// removes the first element of the list
					lst.removeFirst();
					it.reset();
					lst.printList();
					break;
				case 5:	// removes the first occurrence of element in the list
					System.out.println("Enter the element to be removed");
					System.out.println(lst.remove(in.nextInt()));
					it.reset();
					lst.printList();
					break;
				case 6:	// returns the element at index
					System.out.println("Enter the index for get");
					System.out.println(lst.get(in.nextInt()));
					it.reset();
					lst.printList();
					break;
				case 7:	// replaces the element at given index to be x
					System.out.println("Enter the index and element for set");
					lst.set(in.nextInt(), in.nextInt());
					lst.printList();
					break;
				case 8:	// adds x as a new element at given index
					System.out.println("Enter the index and element for add");
					lst.add(in.nextInt(), in.nextInt());
					it.reset();
					lst.printList();
					break;
				case 9:	// deletes and returns element at index
					System.out.println("Enter the index at which the element is to be removed");
					System.out.println(lst.removeIndex(in.nextInt()));
					it.reset();
					lst.printList();
					break;
				default:  // Exit loop
					break whileloop;
				}
			}
		lst.printList();
		lst.unzip();
		lst.printList();
	}
}

