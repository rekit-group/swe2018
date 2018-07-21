package rekit.mymod.enemies.weaknesscannon;

import java.util.ArrayList;
import java.util.List;

import rekit.logic.gameelements.GameElement;

public class Messenger {
	private String id;
	private List<Projectile> bullets = new ArrayList<>();
	private List<WeaknessCannon> cannons = new ArrayList<>();
	private List<CannonWeakness> fuses = new ArrayList<>();
	
	public Messenger(String id) {
		this.id = id;
	}

	public String getMessage() {
		return id;
	}
	
	public void addBullet(Projectile bullet) {
		bullets.add(bullet);
	}
	
	public boolean containsBullet(GameElement element) {
		for (Projectile cannonBullet : bullets) {
			if(cannonBullet == element) {
				return true;
			}
		}
		return false;
	}
	
	public Messenger addCannon(WeaknessCannon cannon) {
		cannons.add(cannon);
		return this;
	}

	public Messenger addFuse(CannonWeakness	fuse) {
		fuses.add(fuse);
		return this;
	}
	
	public void FuseExplode(CannonWeakness fuse) {
		fuses.remove(fuse);
		if(fuses.size() <= 0) {
			for (Projectile cannonBullet : bullets) {
				cannonBullet.destroy();
			}
			for (WeaknessCannon cannon : cannons) {
				cannon.explode();
			}
		}
	}
}
