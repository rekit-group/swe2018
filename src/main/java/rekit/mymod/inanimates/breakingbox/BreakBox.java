package rekit.mymod.inanimates.breakingbox;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.mymod.inanimates.breakingbox.states.SolidState;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

@LoadMe
public class BreakBox extends DynamicInanimate {
	
	private BreakBoxState state;
	
	public BreakBox() {
		super();
	}

	public BreakBox(Vec startPos) {
		super(startPos, new Vec(1,1), new RGBAColor(0,0,0,1));
		this.state = new SolidState(this);
	}
	
	public void setState(BreakBoxState state) {
		this.state = state;
	}
	
	@Override
	public void logicLoop() {
		state.logicLoop();
	}
	
	@Override
	public void internalRender(GameGrid f) {
		state.internalRender(f);
	}
	
	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		super.reactToCollision(element, dir);
		state.reactToCollision(element, dir);
	}
	
	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		return new BreakBox(startPos);
	}
}
