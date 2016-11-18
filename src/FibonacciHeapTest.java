import static org.junit.Assert.*;

import org.junit.Test;

public class FibonacciHeapTest {

	@Test
	public void shouldAddHashTag() {
		FibonacciHeap heap = new FibonacciHeap();
		heap.insert("#saturday", 5);
		assertEquals(heap.size(), 1);
	}

	@Test
	public void shouldReturnMax() {
		FibonacciHeap heap = new FibonacciHeap();
		heap.insert("#saturday", 5);
		heap.insert("#sunday", 3);
		heap.insert("#saturday", 10);
		heap.insert("#monday", 2);
		heap.insert("#reading", 4);
		heap.insert("#playing_games", 2);
		String actual = "#saturday";
		assertEquals(heap.findMax().key, actual);
	}

	@Test
	public void shouldReturnMaxTwo() {
		FibonacciHeap heap = new FibonacciHeap();
		heap.insert("#saturday", 5);
		heap.insert("#sunday", 3);
		heap.insert("#saturday", 10);
		heap.insert("#monday", 2);
		heap.insert("#reading", 4);
		heap.insert("#playing_games", 2);
		String[] result = heap.query(2);
		String actual1 = "#saturday";
		String actual2 = "#reading";

		assertEquals(result[0], actual1);
		assertEquals(result[1], actual2);

	}
}
