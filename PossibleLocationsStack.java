package dfsbfs;

public class PossibleLocationsStack implements PossibleLocations{
	
	SinglyLinkedList<Location> list = new SinglyLinkedList<Location>();
	@Override
	public void add(Location s) {
		if(s == null) {return;}
		list.addFirst(s);
		
	}
	@Override
	public Location remove() {
		return list.removeFirst();
	}
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

}
