package game_data;

import java.util.HashSet;
import java.util.Set;

import game_data.characteristics.Characteristic;

/**
 * Represents any viewable object in a Level including characters, items,
 * terrains, projectiles, etc.
 * 
 * @author Addison, Austin, Cleveland Thompson
 */
public abstract class Sprite extends GameObject {

	private Location myLocation;
	private String myImagePath;
	private int myVelocity;
	private CollisionHandler myCollisionHandler;
	private Set<Characteristic> myCharacteristics;
	private String id;

	public Sprite(Location aLocation, String aImagePath) {
		myLocation = aLocation;
		myImagePath = aImagePath;
		myVelocity = 0;
		myCollisionHandler = null;
		myCharacteristics = new HashSet<Characteristic>();
		id = "";
	}

	// for copying sprites
	public Sprite(Sprite aSprite) {
		myLocation = new Location(aSprite.getMyLocation().getXLocation(), aSprite.getMyLocation().getYLocation(),
				aSprite.getMyLocation().getMyHeading());
		myImagePath = aSprite.getMyImagePath();
		myVelocity = aSprite.getMyVelocity();
		myCollisionHandler = null; // to change: would need to have the same
									// collision handler but we don't know what
									// that is yet
		myCharacteristics = copyCharacteristics(this.getCharacteristics());
	}

	/**
	 * should return a clone using the new Sprite(this) constructor
	 */
	public abstract Sprite clone();

	public Set<Characteristic> copyCharacteristics(Set<Characteristic> origCharacteristics) {
		Set<Characteristic> characteristicCopies = new HashSet<Characteristic>();
		for (Characteristic c : origCharacteristics) {
			characteristicCopies.add(c.copy());
		}
		return characteristicCopies;
	}

	public Set<Characteristic> getCharacteristics() {
		return myCharacteristics;
	}

	public void addCharacteristic(Characteristic aCharacteristic) {
		myCharacteristics.add(aCharacteristic);
	}

	public Location getMyLocation() {
		return myLocation;
	}

	public void setMyLocation(Location myLocation) {
		this.myLocation = myLocation;
		notifyListeners();
	}

	public int getMyVelocity() {
		return myVelocity;
	}

	public void setMyVelocity(int myVelocity) {
		this.myVelocity = myVelocity;
		notifyListeners();
	}

	public String getMyImagePath() {
		return myImagePath;
	}

	public void setMyImagePath(String myImagePath) {
		this.myImagePath = myImagePath;
		notifyListeners();
	}

	public CollisionHandler getMyCollisionHandler() {
		return myCollisionHandler;
	}

	public void setMyCollisionHandler(CollisionHandler myCollisionHandler) {
		this.myCollisionHandler = myCollisionHandler;
		notifyListeners();
	}

	public void setId(String id) {
		this.id = id;
		notifyListeners();
	}
	
	public String getId() {
		return id;
	}
}
