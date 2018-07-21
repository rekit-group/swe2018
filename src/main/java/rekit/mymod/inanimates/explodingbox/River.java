package rekit.mymod.inanimates.explodingbox;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.mymod.utils.Notifyable;
import rekit.mymod.utils.Register;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

@LoadMe
public class River extends DynamicInanimate implements Notifyable {

	private int vel = 1;

	@Override
	public void run(Object parent) {
		vel = 2;

	}

	private River() {
		super();
	}

	public River(Vec startPos) {
		super(startPos, new Vec(1), new RGBAColor(0, 0, 0, 1));
	}
	
	public River(Vec startPos, int vel) {
		super(startPos, new Vec(1), new RGBAColor(0, 0, 0, 1));
		this.vel = vel;
	}

	public River(Vec startPos, int vel, int groupId) {
		this(startPos, vel);
		Register.getRegister().getGroup(groupId).addNotifyable(this);
	}

	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		try {
			int vel;
			if (options.length == 1) {
				vel = Integer.parseInt(options[0]);
				return new River(startPos, vel);
			} else if (options.length >= 2) {
				vel = Integer.parseInt(options[0]);
				int groupId = Integer.parseInt(options[1]);
				return new River(startPos, vel, groupId);
			}

		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
		}
		return new River(startPos);
	}

	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(getPos(), getSize(), "trinity/laserwall_110.png");
	}

	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		super.reactToCollision(element, dir);
		element.setVel(new Vec(-vel, 0));
	}

}
