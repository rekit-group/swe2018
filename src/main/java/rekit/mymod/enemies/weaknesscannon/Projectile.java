package rekit.mymod.enemies.weaknesscannon;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Frame;
import rekit.primitives.geometry.Vec;

public class Projectile extends Enemy{
	private static final Vec SIZE_OUTER = new Vec(0.5f, 0.5f);
	private Messenger messenger = null;

	private int ctr = 0;

	public Projectile(Vec startPos, Vec direction, Messenger messenger) {
		super(startPos, new Vec(), Projectile.SIZE_OUTER);
		this.messenger = messenger;

		float length = (float) Math.sqrt(direction.x*direction.x+direction.y*direction.y);
		this.setVel(direction.scalar(1/length));
	}

	public Messenger gerMessenger() {
		return messenger;
	}
	
	@Override
	protected void innerLogicLoop() {
		// calculate new position
		// s1 = s0 + v*t because physics, thats why!
		//Vec vec = this.getVel();
		
		this.setPos(this.getPos().add(this.getVel().scalar(this.deltaTime / 300F)));
	}

	@Override
	public void collidedWithSolid(Frame collision, Direction dir) {
	}

	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		// Only continue if the element is hostile to the enemy
		// (meaning element is Player)
		if (!this.getTeam().isHostile(element.getTeam())) {
			return;
		}
		
		element.addDamage(1);
		this.destroy();
	}
	
	@Override
	public void internalRender(GameGrid f) {
		ctr++;
		if(ctr <= 4) {
			f.drawImage(this.getPos(), this.getSize(), "projectiles/laserball_medium_1.png");
		} else if (ctr <= 8) {
			f.drawImage(this.getPos(), this.getSize(), "projectiles/laserball_medium_2.png");
		} else if (ctr <= 12) {
			f.drawImage(this.getPos(), this.getSize(), "projectiles/laserball_medium_3.png");
		} else if (ctr <= 16) {
			f.drawImage(this.getPos(), this.getSize(), "projectiles/laserball_medium_4.png");
			ctr = 0;
		}
	}

	@Override
	public Enemy create(Vec arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
