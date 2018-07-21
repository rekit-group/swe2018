package rekit.mymod.inanimates.explodingbox;

import rekit.logic.gameelements.GameElement;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;

public abstract class ExplodingBoxState {
	
	protected final ExplodingBox parent;
	
	protected ExplodingBoxState(ExplodingBox parent) {
		this.parent = parent;
	}
	
	public abstract Vec getVel();
	
	public abstract Vec getVisualOffset();
	
	public abstract void reactToCollision(GameElement element, Direction dir);
	
	public abstract void timePassed(float deltaTime);
}
