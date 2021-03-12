//Kasper Rosenberg karo5568

// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera. 
//DHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap. Note that all "matching" is based on the compareTo
 * method.
 * 
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>> {
	int d = 0;

	/**
	 * Construct the binary heap.
	 */
	public DHeap() {
		this(2);
	}

	/**
	 * Construct the binary heap.
	 * 
	 * @param capacity the capacity of the binary heap.
	 * @throws Exception
	 */
	public DHeap(int d) {
		this.d = d;
		if (d < 2) {
			throw new IllegalArgumentException();
		}
		currentSize = 0;
		array = (AnyType[]) new Comparable[d + 1];
	}

	/**
	 * Construct the binary heap given an array of items.
	 */
	public DHeap(AnyType[] items) {
		currentSize = items.length;
		array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];
		int i = 1;
		for (AnyType item : items)
			array[i++] = item;
		buildHeap();
	}

	/**
	 * Insert into the priority queue, maintaining heap order. Duplicates are
	 * allowed.
	 * 
	 * @param x the item to insert.
	 */
	public int size() {
		return currentSize;
	}

	public AnyType get(int index) {
		return array[index];
	}

	public void insert(AnyType x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1);

		// Percolate up
		int hole = ++currentSize;
		for (array[0] = x; hole != 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole))
			array[hole] = array[parentIndex(hole)];
		array[hole] = x;
	}

	private void enlargeArray(int newSize) {
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for (int i = 0; i < old.length; i++)
			array[i] = old[i];
	}

	/**
	 * Find the smallest item in the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return array[1];
	}

	/**
	 * Remove the smallest item from the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType deleteMin() {
		if (isEmpty())
			throw new UnderflowException();

		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);

		return minItem;
	}

	/**
	 * Establish heap order property from an arbitrary arrangement of items. Runs in
	 * linear time.
	 */
	private void buildHeap() {
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
	}

	/**
	 * Test if the priority queue is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * Make the priority queue logically empty.
	 */
	public void makeEmpty() {
		currentSize = 0;
	}

	private static final int DEFAULT_CAPACITY = 10;

	private int currentSize; // Number of elements in heap
	private AnyType[] array; // The heap array

	/**
	 * Internal method to percolate down in the heap.
	 * 
	 * @param hole the index at which the percolate begins.
	 */

	private void percolateDown(int hole) {
		int child;
		AnyType tmp = array[hole];
		for (; firstChildIndex(hole) <= currentSize; hole = child) {
			child = firstChildIndex(hole);
			for (int i = 1; i < d && (firstChildIndex(hole) + i) <= currentSize; i++) {
				if (array[firstChildIndex(hole) + i].compareTo(array[child]) < 0) {
					child = firstChildIndex(hole) + i;
				}
			}
			if (array[child].compareTo(tmp) < 0)
				array[hole] = array[child];
			else {
				break;
			}
		}
		array[hole] = tmp;
	}

	public int firstChildIndex(int i) {
		if (i < 1) {
			throw new IllegalArgumentException();
		}
		return d * i + (2 - d);
	}

	public int parentIndex(int i) {
		if (i < 2) {
			throw new IllegalArgumentException();
		}
		return (i - 2 + d) / d;
	}

	// Test program
	public static void main(String[] args) {
		int numItems = 10000;
		DHeap<Integer> h = new DHeap<>();
		int i = 37;

		for (i = 37; i != 0; i = (i + 37) % numItems)
			h.insert(i);
		for (i = 1; i < numItems; i++)
			if (h.deleteMin() != i)
				System.out.println("Oops! " + i);
	}
}