package rekit.mymod.inanimates.explodingbox.states;

import rekit.logic.gameelements.GameElement;
import rekit.mymod.inanimates.explodingbox.ExplodingBox;
import rekit.mymod.inanimates.explodingbox.ExplodingBoxState;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;

public class ExplodingIdleState extends ExplodingBoxState {
	
	private final ExplodingWaitingState waitingState;
	
	public ExplodingIdleState(ExplodingBox parent) {
		super(parent);
		this.waitingState = new ExplodingWaitingState(parent, this);
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
		if (element.getTeam().isHostile(this.parent.getTeam())) {
			this.parent.setState(waitingState);
		}
	}

	@Override
	public void timePassed(float deltaTime) {
		// do nothing
	}
}
