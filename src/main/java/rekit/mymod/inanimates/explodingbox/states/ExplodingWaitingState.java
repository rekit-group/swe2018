package rekit.mymod.inanimates.explodingbox.states;

import rekit.logic.gameelements.GameElement;
import rekit.mymod.inanimates.explodingbox.ExplodingBox;
import rekit.mymod.inanimates.explodingbox.ExplodingBoxState;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;

public class ExplodingWaitingState extends ExplodingBoxState {
	
	private float time = 0;
	private float collisionTime = 0;
	private final float timeThreshold = 5000;
	
	final private ExplodingIdleState idelState;
	
	public ExplodingWaitingState(ExplodingBox parent, ExplodingIdleState idelState) {
		super(parent);
		this.idelState = idelState;
	}

	@Override
	public Vec getVel() {
		return new Vec();
	}
	
	@Override
	public Vec getVisualOffset() {
		return new Vec();
	}

	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		collisionTime = 0;
		if (element.getTeam().isHostile(this.parent.getTeam())) {
			//this.parent.setState(new ExplodingWaitingState(parent));
		}
	}

	@Override
	public void timePassed(float deltaTime) {
		collisionTime += deltaTime;
		if(collisionTime > 1000) {
			parent.setState(idelState);
			collisionTime = 0;
			return;
		}
		time += deltaTime;
		if(time < timeThreshold*0.3) {
			parent.setCurrentImage(1);
		} else if (time < timeThreshold*0.6) {
			parent.setCurrentImage(2);
		} else if (time >= timeThreshold) {
			parent.setCurrentImage(3);
		} else {
			parent.setState(new ExplodingExplodingState(parent));
		}
	}

}
