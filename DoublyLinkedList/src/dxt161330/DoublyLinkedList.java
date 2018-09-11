/** @author Dinesh Reddy Thokala
 *  Doubly Linked List
 *  Ver 1.0: 08/28/2018
 *  DoublyLinkedList class inherits SinglyLinkedList class
 */

package dxt161330;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T>{
    /** class Entry inherits Entry class of SinglyLinkedList and
     *  holds a single node of the list
     */
    static class Entry<E> extends SinglyLinkedList.Entry<E> {
        Entry<E> prev;
        Entry(E x, Entry<E> next, Entry<E> prev) {
            super(x, next);
            this.prev = prev;
        }
    }

    public DoublyLinkedList() {
        head = new Entry<>(null, null, null);
        tail = (Entry<T>) head;
        size = 0;
    }

    public DoublyLinkedListIterator<T> dllIterator(){
        return new DLLIterator();
    }

    public interface DoublyLinkedListIterator<T>{
        boolean hasNext();
        boolean hasPrevious();
        T next();
        T previous();
        void add(T x);
        void remove();
    }

    protected class DLLIterator extends SLLIterator implements DoublyLinkedListIterator<T>{
        DLLIterator(){
            super();
        }

        public boolean hasPrevious() {
            return ((Entry<T>) cursor).prev != null;
        }

        // Returns the previous element and moves the cursor to previous node
        public T previous() {
        	ready = true;
            cursor=((Entry<T>)cursor).prev;
            prev = ((Entry<T>)cursor).prev;
            return cursor.element;
        }

        //inserts element x next to cursor and the cursor points to x
        public void add(T x) {
            Entry<T> ent = new Entry<>(x, (Entry<T>)cursor.next, (Entry<T>)cursor);
            ((Entry<T>) cursor.next).prev=ent;
            cursor.next=ent;
            cursor = (Entry<T>) cursor.next;
            prev = ((Entry<T>)cursor).prev;
            size++;
            ready = false;
        }

        // Removes the current element (retrieved by the most recent next())
        // remove() is allowed only after calling next() or prev()
        public void remove() {
            super.remove();
            if(cursor!=tail) {
                ((Entry<T>)cursor.next).prev = (Entry<T>)cursor;
            }
            prev = ((Entry<T>)cursor).prev;
        }
    }

    //Adds x at the end of the list
    public void add(T x)
    {
        super.add(new Entry<T>(x,null,(Entry<T>) tail));
    }

    public static void main(String[] args) throws NoSuchElementException{
        int n = 5;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        DoublyLinkedList<Integer> dlst = new DoublyLinkedList<>();
        for(int i=1; i<=n; i++) {
            dlst.add(Integer.valueOf(i));
        }
        dlst.printList();

        DoublyLinkedListIterator<Integer> iter = dlst.dllIterator();
        Scanner in = new Scanner(System.in);
        whileloop:
        while(in.hasNext()) {
            int com = in.nextInt();
            switch(com) {
                case 1: // Move to next element and print it
                    if(iter.hasNext()) {
                        System.out.println(iter.next());
                    } else {
                        System.out.println("There is no next element");
                    }
                    break;
                case 2: // Remove element
                    iter.remove();
                    dlst.printList();
                    break;
                case 3: // Move to previous element and print it
                    if(iter.hasPrevious()) {
                        System.out.println(iter.previous());
                    } else {
                        System.out.println("There is no previous element");
                    }
                    break;
                case 4: // Adds element next to cursor
                    System.out.println("Enter the element to be added next to the cursor");
                    iter.add(in.nextInt());
                    dlst.printList();
                    break;
                default:
                    break whileloop;
            }
        }
        dlst.printList();
        dlst.unzip();
        dlst.printList();
    }
}

/* Sample input: 
   1 1 2 4 6 1 3 0
   Sample output:
5: 1 2 3 4 5 
1
2
4: 1 3 4 5 
Enter the element to be added next to the cursor
5: 1 6 3 4 5 
3
6
5: 1 6 3 4 5 
5: 1 3 5 6 4 
 */
