package gameplayer.back_end.keycode_handler;

public class MovementHandler {
	
	private double myXMovement;
	private double myYMovement;
	
	public MovementHandler() {
		//myKeyCodeMap.put(KeyCode.RIGHT, -3.3);
		//myKeyCodeMap.put(KeyCode.LEFT, 3.3);
	}
	
	public void setYMovement(double aYVelocity) {
		double answer = aYVelocity * .0166589;
		myYMovement = answer;
	}
	
	public double getYMovement() {
		return -myYMovement;
	}
	
	public void setXMovement(double aXVelocity) {
		//System.out.println("velocity: " + aXVelocity + "\n");
		double answer = aXVelocity * .0166589;
		myXMovement = answer;
	}
	
	public double getXMovement() {
		//System.out.println("key: " + aKey);
		return -myXMovement;
	}
	
}
