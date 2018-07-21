package rekit.mymod.inanimates.breakingbox.states;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.mymod.inanimates.breakingbox.BreakBox;
import rekit.mymod.inanimates.breakingbox.BreakBoxState;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;

public class BreakingState extends BreakBoxState {
	
	private final static long DELTA = 50;
	private long time;
	private int breakCounter;
	
	public BreakingState(BreakBox parent) {
		super(parent);
		time = System.currentTimeMillis();
		breakCounter = 1;
	}
	
	@Override
	public void logicLoop() {
		if (Math.abs(time - System.currentTimeMillis()) >= breakCounter * DELTA) {
			breakCounter++;
		}
		if (breakCounter == 6) {
			this.parent.destroy();
		}
	}
	
	@Override
	public void internalRender(GameGrid f) {
		Vec newPos = new Vec(parent.getPos().x - 0.5f, parent.getPos().y - 0.5f);
		if (breakCounter < 6) {
			f.drawImage(newPos, parent.getSize(), "arcadeflavor/breakableblock_breaking_0" + breakCounter + ".png");
		}
		
	}
	
	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		// TODO Auto-generated method stub
	}

	@Override
	public void timePassed(float deltaTime) {
		// TODO Auto-generated method stub
	}
}
