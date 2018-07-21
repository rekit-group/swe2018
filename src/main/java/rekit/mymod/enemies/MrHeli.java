package rekit.mymod.enemies;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Frame;
import rekit.primitives.geometry.Vec;
import rekit.util.ReflectUtils.LoadMe;

@LoadMe
public final class MrHeli extends Enemy {
	private static final int POINTS = 20;
	private long sumTime;
	private static Vec VELOCITY = new Vec(3.0f, 0.0f);
	private Vec downLeft;
	private Vec downRight;
	private Vec topLeft;
	private Vec topRight;
	
	/**
	 * Prototype constructor used to dynamically {@link MrHeli#create(Vec, String...)}
	 * clones without knowing the concrete type.
	 */
	public MrHeli() {
		super();
	}
	
	/**
	 * Standard constructor that saves the initial position.
	 *
	 * @param startPos
	 *            the initial position of the Enemy.
	 */
	public MrHeli(Vec startPos) {
		super(startPos, new Vec(), new Vec(1));
		
		downLeft = new Vec(startPos.x, startPos.y);
		downRight = new Vec(startPos.x + 3, startPos.y);
		topLeft = new Vec(startPos.x, startPos.y - 3 );
		topRight = new Vec(startPos.x + 3, startPos.y - 3);
		this.setVel(VELOCITY);
		
		
	}

	@Override
	public Enemy create(Vec startPos, String... options) {
		return new MrHeli(startPos);
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), "roboindustries/flydrone_0" + (((int) (10 * this.sumTime / 1000f) % 4) + 1) + ".png", true, true, false, false);
	}


	@Override
	protected void innerLogicLoop() {
		if (this.invincibility != null) {
			this.invincibility.logicLoop();
		}
		
		this.sumTime += this.deltaTime;
		
		
		// calculate new position
		// s1 = s0 + v*t because physics, thats why!
		this.setPos(this.getPos().add(this.getVel().scalar(this.deltaTime / 1000F)));

		Vec newVel = this.getVel();

		if(this.getPos().x > downRight.x && (Math.abs(this.getPos().y - downRight.y) < 0.1f)) {
			newVel = newVel.setX(0);
			newVel = newVel.setY(-3);
		}

		if(this.getPos().y < topRight.y && (Math.abs(this.getPos().x - topRight.x) < 0.1f)) {
			newVel = newVel.setX(-3);	
			newVel = newVel.setY(0);	
			}
		 
		if(this.getPos().x < topLeft.x && (Math.abs(this.getPos().y - topLeft.y) < 0.1f)) {
			newVel = newVel.setX(0);	
			newVel = newVel.setY(3);	
			}
		
		if(this.getPos().y > downLeft.y && (Math.abs(this.getPos().x - downLeft.x) < 0.1f)) {
			newVel = newVel.setX(3);	
			newVel = newVel.setY(0);	
			}
		// apply slowing down walk
		
		// we don't want weird floating point velocities
		if (Math.abs(newVel.x) < 0.05) {
			newVel = newVel.setX(0);
		}

		// save new velocity
		this.setVel(newVel);
	}
	
	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		// Only continue if the element is hostile to the enemy
		// (meaning element is Player)
		if (!this.getTeam().isHostile(element.getTeam())) {
			return;
		}

		// If hit from above:
		if (dir == Direction.UP) {
			// give the player points
			this.getScene().getPlayer().addPoints(MrHeli.POINTS);
			// Let the player jump 
			element.killBoost();
			// kill the enemy
			this.addDamage(1);
		} else {
			// Touched dangerous side
			// Give player damage
			element.addDamage(1);
			// Kill the enemy itself
			this.destroy();
		}
	}

	@Override
	public void collidedWithSolid(Frame collision, Direction dir) {
		// standard behavior, that prevents clipping into other blocks
		// super.collidedWithSolid(collision, dir);

	}

}
