package rekit.mymod.quiz;

import rekit.core.GameGrid;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.primitives.geometry.Polygon;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

@LoadMe
public class BlockadeBox extends DynamicInanimate {

	private Polygon triangle;
	
	private BlockadeBox() {
		super();
	}
	
	public BlockadeBox(Vec startPos) {
		super(startPos, new Vec(1), new RGBAColor(0,0,0,1));

		this.triangle = new Polygon(new Vec(), new Vec[] {
				new Vec(-0.3, -0.5),
				new Vec(0.3, -0.5)
		});
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), "trinity/laserwall_100.png", true, true, false, false);
	}
	
	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		return new BlockadeBox(startPos);
	}
	
	@Override
	public Integer getZHint() {
		return 1;
	}
}
