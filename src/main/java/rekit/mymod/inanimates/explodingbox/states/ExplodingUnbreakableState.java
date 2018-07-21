package rekit.mymod.inanimates.explodingbox.states;

import rekit.logic.gameelements.GameElement;
import rekit.mymod.inanimates.explodingbox.ExplodingBox;
import rekit.mymod.inanimates.explodingbox.ExplodingBoxState;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;

public class ExplodingUnbreakableState extends ExplodingBoxState {

	public ExplodingUnbreakableState(ExplodingBox parent) {
		super(parent);
		parent.setCurrentImage(4);
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
		//do nothing
	}

	@Override
	public void timePassed(float deltaTime) {
		// do nothing
	}

}
