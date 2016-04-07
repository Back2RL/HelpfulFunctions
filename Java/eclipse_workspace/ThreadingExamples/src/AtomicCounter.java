
import java.util.concurrent.atomic.AtomicInteger;

class AtomicCounter {
	private AtomicInteger c = new AtomicInteger(0);

	public void increment() {
		c.incrementAndGet();
	}

	public void decrement() {
		c.decrementAndGet();
	}

	public int value() {
		return c.get();
	}

}

// Thread local Random generation
// int r = ThreadLocalRandom.current() .nextInt(4, 77);
