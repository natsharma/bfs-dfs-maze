package dfsbfs;

public class PossibleLocationsQueue implements PossibleLocations{

	ArrayQueue<Location> queue = new ArrayQueue<Location>();
	
	/* default constructor*/
	public PossibleLocationsQueue() {
		ArrayQueue<Location> queue = new ArrayQueue<Location>();
	}
	public PossibleLocationsQueue(int size) {
		ArrayQueue<Location> queue = new ArrayQueue<Location>(size);
	}

	@Override
	public void add(Location s) {
		if(s == null) return;
		queue.enqueue(s);
	}

	@Override
	public Location remove() {
		return queue.dequeue();
	}

	@Override
	public boolean isEmpty() {
		return (queue.isEmpty());
	}

}
