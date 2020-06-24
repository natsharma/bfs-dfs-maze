package dfsbfs;
public interface PossibleLocations {
	void add ( Location s );
	Location remove ();
	boolean isEmpty();
}
