package rekit.mymod.inanimates;

import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.inanimate.InanimateBox;
import rekit.mymod.enemies.missilelauncher.MissileLauncher;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

/**
 * Only purpose is to enforce reactToCollision on the other GameElement
 * @author angeloaracri
 */
@LoadMe
public class InanimateClone extends InanimateBox {

	InanimateClone() {
		super(null, null, null);
	}
	InanimateClone(Vec startPos) {
		super(startPos, new Vec(1,1), new RGBAColor(255,255,0,255));
	}
	@Override
	public InanimateClone create(Vec startPos, String... options) {
		return new InanimateClone(startPos);
	}

	@Override
    public void reactToCollision(GameElement element, Direction dir) {
		element.reactToCollision(element, dir.getOpposite());
        super.reactToCollision(element, dir);
        if (element instanceof MissileLauncher) {
        	this.destroy();
        }
    }
	
	
}
