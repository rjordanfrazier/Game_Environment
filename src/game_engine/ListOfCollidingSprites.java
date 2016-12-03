package game_engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game_data.Location;
import game_data.Sprite;
import game_data.sprites.Player;
import game_data.states.Physics;
import game_data.states.State;
import javafx.geometry.Side;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ListOfCollidingSprites {
	
	private static double SHIFT_CONSTANT = .005;
	private Sprite targetSprite;
	private List<Sprite> spriteList;
	private Map<Sprite, Side> collisionSprites;
	private Map<Sprite, ImageView> mySpriteImages;
	//private double myTimeElapsed;
	//public ListOfCollidingSprites(Sprite targetSprite, List<Sprite> spriteList, Map<Sprite, ImageView> spriteImages, double timeElapsed) {
	public ListOfCollidingSprites(Sprite targetSprite, List<Sprite> spriteList, Map<Sprite, ImageView> spriteImages) {
		this.targetSprite = targetSprite;
		this.spriteList = spriteList;
		this.mySpriteImages=spriteImages;
		//myTimeElapsed=timeElapsed;
		getListOfSpritesCollided();
	}
	
	private Map<Sprite, Side> getListOfSpritesCollided(){
		collisionSprites = new HashMap<Sprite, Side>();
		/*UpdateLocation updateTargetLocation = new UpdateLocation(targetSprite, myTimeElapsed);
		double oldX = targetSprite.getMyLocation().getXLocation();
		double oldY = targetSprite.getMyLocation().getYLocation();
		double oldXVelo = targetSprite.getMyXVelocity();
		double oldYVelo = targetSprite.getMyYVelocity();
		double oldXAccel = targetSprite.getMyXAcceleration();
		double oldYAccel = targetSprite.getMyYAcceleration();
		updateTargetLocation.updateSpriteParameters();
		mySpriteImages.get(targetSprite).setX(targetSprite.getMyLocation().getXLocation());
		mySpriteImages.get(targetSprite).setY(targetSprite.getMyLocation().getYLocation());*/
		for(Sprite mySprite:spriteList){
			/*UpdateLocation updateLocation = new UpdateLocation(mySprite, myTimeElapsed);
			double oldX2 = mySprite.getMyLocation().getXLocation();
			double oldY2 = mySprite.getMyLocation().getYLocation();
			double oldXVelo2 = mySprite.getMyXVelocity();
			double oldYVelo2 = mySprite.getMyYVelocity();
			double oldXAccel2 = mySprite.getMyXAcceleration();
			double oldYAccel2 = mySprite.getMyYAcceleration();
			updateLocation.updateSpriteParameters();
			mySpriteImages.get(mySprite).setX(mySprite.getMyLocation().getXLocation());
			mySpriteImages.get(mySprite).setY(mySprite.getMyLocation().getYLocation());*/
			if(!mySprite.getName().equals(targetSprite.getName()) && (mySpriteImages.get(mySprite)
					.getBoundsInParent()).intersects(mySpriteImages.get(targetSprite).getBoundsInParent())) {
				collisionSprites.put(mySprite, findSideOfCollision(mySprite));			
			}
			/*mySprite.getMyLocation().setLocation(oldX2, oldY2);
			mySprite.setMyXVelocity(oldXVelo2);
			mySprite.setMyYVelocity(oldYVelo2);
			mySprite.setMyXAcceleration(oldXAccel2);
			mySprite.setMyYAcceleration(oldYAccel2);
			mySpriteImages.get(mySprite).setX(mySprite.getMyLocation().getXLocation());
			mySpriteImages.get(mySprite).setY(mySprite.getMyLocation().getYLocation());*/
		}
		/*
		targetSprite.getMyLocation().setLocation(oldX, oldY);
		targetSprite.setMyXVelocity(oldXVelo);
		targetSprite.setMyYVelocity(oldYVelo);
		targetSprite.setMyXAcceleration(oldXAccel);
		targetSprite.setMyYAcceleration(oldYAccel);
		mySpriteImages.get(targetSprite).setX(targetSprite.getMyLocation().getXLocation());
		mySpriteImages.get(targetSprite).setY(targetSprite.getMyLocation().getYLocation());*/
		return collisionSprites;
	}
	
	public Map<Sprite, Side> getCollisionSpriteMap(){
		return this.collisionSprites;
	}
	
	private Rectangle createRectangle(Sprite aSprite){
		double x = aSprite.getMyLocation().getXLocation();
		double y = aSprite.getMyLocation().getYLocation();
		double width = aSprite.getMyWidth();
		double height = aSprite.getMyHeight();
		return new Rectangle(x,y,width,height);
	}

	private int getMaxEdge(double leftDistance, double rightDistance, double topDistance, double bottomDistance) {
		Map<Integer, Double> thing = new HashMap<Integer, Double>();
		thing.put(0, leftDistance+0.05*targetSprite.getMyWidth());
		thing.put(1, rightDistance+0.05*targetSprite.getMyWidth());
		thing.put(2, topDistance);
		thing.put(3, bottomDistance);
		int min = 0;
		for(Integer d: thing.keySet()){
			if(thing.get(d)<thing.get(min)){
				min = d;
			}
		}
		return min;
	}

	private Side findSideOfCollision(Sprite mySprite) {
		Rectangle player = createRectangle(mySprite);
		Rectangle block = createRectangle(targetSprite);
		Shape intersection = Shape.intersect(block, player);

		double middleX = (intersection.getBoundsInParent().getMinX()+intersection.getBoundsInParent().getMaxX())/2.0;
		double middleY = (intersection.getBoundsInParent().getMinY()+intersection.getBoundsInParent().getMaxY())/2.0;
	
		double leftDistance = Math.abs(block.getX()-middleX);
		double rightDistance = Math.abs(block.getX()+block.getWidth()-middleX);
		double topDistance = Math.abs(block.getY()-middleY);
		double bottomDistance = Math.abs(block.getY()+block.getHeight()-middleY);
	
		int min = getMaxEdge(leftDistance, rightDistance, topDistance, bottomDistance);
		if(mySprite instanceof Player){
		//	mySprite.getMyLocation().setLocation(mySprite.getMyLocation().getXLocation(), targetSprite.getMyLocation().getYLocation() -0.5- mySprite.getMyHeight());
			shiftPlayer(min, mySprite, leftDistance, rightDistance, topDistance, bottomDistance);
			//printSide(min);
		}
		return pickSide(min, mySprite);
	}

	private void shiftPlayer(int min, Sprite aSprite, double leftDistance, double rightDistance,
			double topDistance, double bottomDistance){
		if(min == 0){
			aSprite.getMyLocation().setLocation(aSprite.getMyLocation().getXLocation()-leftDistance+SHIFT_CONSTANT, 
					aSprite.getMyLocation().getYLocation());
		}
		if(min == 1){
			aSprite.getMyLocation().setLocation(aSprite.getMyLocation().getXLocation()+rightDistance-SHIFT_CONSTANT, 
					aSprite.getMyLocation().getYLocation());
		}
		if(min == 2){
			aSprite.getMyLocation().setLocation(aSprite.getMyLocation().getXLocation(), 
					aSprite.getMyLocation().getYLocation()-topDistance+SHIFT_CONSTANT);
		}
		//if(min == 3){
		//	aSprite.getMyLocation().setLocation(aSprite.getMyLocation().getXLocation(), 
		//			aSprite.getMyLocation().getYLocation()+bottomDistance-SHIFT_CONSTANT);
		//}
	}
	
	private Side pickSide(int min, Sprite mySprite) {
		if(min == 0){
			return Side.LEFT;
		}
		if(min == 1){
			return Side.RIGHT;

		}
		if(min == 2){
			return Side.TOP;
		}
		if(min == 3){
			return Side.BOTTOM;
		}
		return null;
	}
	
	private void printSide(int min){
		if(min == 0){
			System.out.println("left");
		}
		if(min == 1){
			System.out.println("right");
		}
		if(min == 2){
			System.out.println("top");
		}
		if(min == 3){
			System.out.println("bottom");
		}
	}
}