package levels;

public interface Updatable {
	public int update(); //TODO: Make this package private? and just increase visibility if needed?
	//Maybe? This leads to no guarantee of having access to this method given that its an Updatable type
}
