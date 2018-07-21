package rekit.mymod.inanimates;

import rekit.config.GameConf;
import rekit.core.GameGrid;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Vec;
import rekit.util.ReflectUtils.LoadMe;

/**
 * Sample Enemy showing Sprite functionality
 *
 * @author Angelo Aracri
 */
@LoadMe
public final class SpriteDummy extends Enemy {
	private String imgPath;
	/**
	 * Prototype constructor used to dynamically
	 * {@link SpriteDummy#create(Vec, String...)} clones without knowing the
	 * concrete type.
	 */
	public SpriteDummy() {
		super();
	}

	/**
	 * Standard constructor that saves the initial position.
	 *
	 * @param startPos
	 *            the initial position of the Enemy.
	 */
	public SpriteDummy(Vec startPos, Vec size, String imgPath) {
		super(startPos, new Vec(), size);
		this.imgPath = imgPath;
	}

	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), imgPath, true, true, false, false);
	}
	
	@Override
	public void innerLogicLoop() {
		// do nothing
	}

	@Override
	public Enemy create(Vec startPos, String... options) {
		if (options.length >= 3) {
			Vec size;
			try {
				size = new Vec(Float.parseFloat(options[0]), Float.parseFloat(options[1]));
				return new SpriteDummy(startPos, size, options[2]);
			} catch (NullPointerException NumberFormatException) {
				GameConf.GAME_LOGGER.error("Error in SpriteDummy: Could not parse size");
				return null;
			}
		} else {
			GameConf.GAME_LOGGER.error("Error in SpriteDummy: Did not specifiy input parameters: sizeX, sizeY, path");
			return null;
		}
	}


}
