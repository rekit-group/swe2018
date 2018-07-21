package rekit.mymod.inanimates.explodingbox;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.mymod.inanimates.explodingbox.states.*;
import rekit.mymod.utils.Notifyable;
import rekit.mymod.utils.Register;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Polygon;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

@LoadMe
public class ExplodingBox extends DynamicInanimate implements Notifyable {
	
	private ExplodingBoxState state;
	private String currentImage;
	private static final String block1 = "arcadeflavor/breakableblock_0";
	private static final String blockBreaking = "arcadeflavor/breakableblock_breaking_0";
	private static final String blockEnding = ".png";

	private ExplodingBox() {
		super();
	}
	
	public ExplodingBox(Vec startPos) {
		super(startPos, new Vec(1), new RGBAColor(0,0,0,1));
		this.state = new ExplodingIdleState(this);
		currentImage = block1 + "1" + blockEnding;
	}
	
	public ExplodingBox(Vec startPos, int groupId) {
		this(startPos);
		Register.getRegister().getGroup(groupId).addNotifyable(this);
	}
	
	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		try {
			if(options.length >= 1) {
				int groupId = Integer.parseInt(options[0]);
				return new ExplodingBox(startPos, groupId);
			}
			
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
		}
		return new ExplodingBox(startPos);
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(getPos(), getSize(), currentImage);
	}
	
	public void setState(ExplodingBoxState state) {
		this.state = state;
	}
	
	public void setCurrentImage (int number) {
		currentImage = block1 + number + blockEnding;
	}
	
	public void setCurrentBreakableImage (int number) {
		currentImage = blockBreaking + number + blockEnding;
	}
	
	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		super.reactToCollision(element, dir);
		state.reactToCollision(element, dir);
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();
		state.timePassed(this.deltaTime);
	}

	@Override
	public void run(Object parent) {
		state = new ExplodingUnbreakableState(this);	
	}

}
