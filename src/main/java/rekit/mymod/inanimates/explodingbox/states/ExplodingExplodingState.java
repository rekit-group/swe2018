package rekit.mymod.inanimates.explodingbox.states;

import rekit.logic.gameelements.GameElement;
import rekit.mymod.inanimates.explodingbox.ExplodingBox;
import rekit.mymod.inanimates.explodingbox.ExplodingBoxState;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;

public class ExplodingExplodingState extends ExplodingBoxState {
	
	float time = 0;
	float timeThreshold = 500;
	
	public ExplodingExplodingState(ExplodingBox parent) {
		super(parent);
		// TODO Auto-generated constructor stub
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
	public void timePassed(float deltaTime) {
		time += deltaTime;
		if(time < timeThreshold/5) {
			parent.setCurrentBreakableImage(1);
		} else if (time < timeThreshold*2/5) {
			parent.setCurrentBreakableImage(2);
		} else if (time < timeThreshold*3/5) {
			parent.setCurrentBreakableImage(3);
		} else if (time < timeThreshold*4/5) {
			parent.setCurrentBreakableImage(4);
		}else if (time < timeThreshold) {
			parent.setCurrentBreakableImage(5);
		} else {
			parent.destroy();
				
		}
	}


	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		// TODO Auto-generated method stub
		
	}}
