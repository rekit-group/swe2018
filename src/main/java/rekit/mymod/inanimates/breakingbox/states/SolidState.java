package rekit.mymod.inanimates.breakingbox.states;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.mymod.inanimates.breakingbox.BreakBox;
import rekit.mymod.inanimates.breakingbox.BreakBoxState;
import rekit.primitives.geometry.Direction;

public class SolidState extends BreakBoxState {

	private int breakCounter;
	
	public SolidState(BreakBox parent) {
		super(parent);
		breakCounter = 1;
	}

	@Override
	public void logicLoop() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(parent.getPos(), parent.getSize(), "arcadeflavor/breakableblock_0" + breakCounter + ".png");
	}
	
	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		if (breakCounter > 2) {
			this.parent.setState(new BreakingState(parent));
			breakCounter = 1;
		} else {
			breakCounter++;
		}
	}

	@Override
	public void timePassed(float deltaTime) {
		// TODO Auto-generated method stub
	}
}
