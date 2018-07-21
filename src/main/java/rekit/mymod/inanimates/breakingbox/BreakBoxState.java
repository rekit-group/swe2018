package rekit.mymod.inanimates.breakingbox;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.primitives.geometry.Direction;

public abstract class BreakBoxState {

	protected final BreakBox parent;

	protected BreakBoxState(BreakBox parent) {
		this.parent = parent;
	}
	
	public abstract void logicLoop();
	
	public abstract void internalRender(GameGrid f);

	public abstract void reactToCollision(GameElement element, Direction dir);

	public abstract void timePassed(float deltaTime);

}
