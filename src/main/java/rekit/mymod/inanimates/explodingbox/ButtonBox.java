package rekit.mymod.inanimates.explodingbox;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.mymod.utils.Register;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

@LoadMe
public class ButtonBox extends DynamicInanimate {

	private final String block1 = "trinity/button_orange_0";
	private final String blockEnding = ".png";
	private String currentImage;
	private int groupId;
	
	private ButtonBox()  {
		super();
	}
	
	public ButtonBox(Vec startPos, int groupId) {
		super(startPos, new Vec(1), new RGBAColor(0,0,0,1));
		this.groupId = groupId;
		setCurrentImage(1);
		Register register = Register.getRegister();
		if(!register.addGroup(groupId, this)) {
			System.err.println("GroupId already exists");
		}
		
	}
	
	public ButtonBox(Vec startPos) {
		super(startPos, new Vec(1), new RGBAColor(0,0,0,1));
		setCurrentImage(1);
	}
	
	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		try {
			if(options.length >= 1) {
				int groupId = Integer.parseInt(options[0]);
				return new ButtonBox(startPos, groupId);
			}
			
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
		}
		return new ButtonBox(startPos);
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(getPos(), getSize(), currentImage);
	}
	
	private void setCurrentImage (int number) {
		currentImage = block1 + number + blockEnding;
	}
	
	
	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		super.reactToCollision(element, dir);
		setCurrentImage(2);
		Register.getRegister().getGroup(groupId).nofiy();
		
		
	}
}
