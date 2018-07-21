package rekit.mymod.enemies.weaknesscannon;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

@LoadMe
public class CannonWeakness extends DynamicInanimate {

	private Messenger messenger = null;
	
	/**
	 * Prototype constructor, used for dynamic instantiation.
	 */
	@SuppressWarnings("unused")
	private CannonWeakness() {
		super();
	}
	
	public CannonWeakness(Vec start, String id) {
		this(start);
		setMessenger(id);
	}
	
	public CannonWeakness(Vec start) {
		super(start.addY(0.25f), new Vec(1, 0.5), new RGBAColor(0, 0, 0, 255));
	}

	
	public CannonWeakness(Vec startPos, String[] options) {
		this(startPos, options[0]);
	}
	
	
	public void setMessenger(String id) {
		messenger = Beobachter.getBeobachter().addFuse(id, this);
	}
	
	public Messenger getMessenger() {
		return messenger;
	}

	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), "trinity/button_orange_01.png");
	}
	

	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		// Only continue if the element is hostile to the enemy
		// (meaning element is Player)
		if(messenger != null && messenger.containsBullet(element)) {
			messenger.FuseExplode(this);
			this.destroy();
		}
		if (!this.getTeam().isHostile(element.getTeam())) {
			return;
		}
		if(messenger != null) {
			messenger.FuseExplode(this);
		}
		this.destroy();
	}
	
	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		return new CannonWeakness(startPos, options[0]);
	}
}
