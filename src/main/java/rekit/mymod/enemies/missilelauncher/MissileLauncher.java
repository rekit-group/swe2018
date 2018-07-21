package rekit.mymod.enemies.missilelauncher;

import rekit.core.GameGrid;
import rekit.logic.gameelements.particles.ParticleSpawner;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Vec;
import rekit.util.ReflectUtils;

@ReflectUtils.LoadMe
public class MissileLauncher extends Enemy {
	
	private static final Vec OFFSET = new Vec(0, -1.2);
	private static final int DELAY = 1400;
	private static final String MISSILE_LAUNCHER = "missilelauncher/missilelauncher.png";
	
	private long sumTime;
	private Missile missile;
	private ParticleSpawner ps;
	
	/**
	 * Prototype constructor, used for dynamic instantiation.
	 */
	@SuppressWarnings("unused")
	private MissileLauncher() {
		super();
	}

	public MissileLauncher(Vec startPos) {
		super(startPos, new Vec(0, 0), new Vec(1, 1));
		this.ps = new ParticleSpawner();
	}
	
	@Override
	public void innerLogicLoop() {
		super.innerLogicLoop();
		sumTime += this.deltaTime;
		if (sumTime >= DELAY) {
			sumTime = 0;
			Enemy m = new Missile(getPos().add(OFFSET));
			this.getScene().addGameElement(m);
			this.ps.spawn(getScene(), getPos());
		}
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), MISSILE_LAUNCHER, true, false);
	}
	
	@Override
	public MissileLauncher create(Vec startPos, String... options) {
		return new MissileLauncher(startPos);
	}
}
 