package rekit.mymod.enemies.weaknesscannon;
import java.util.ArrayList;
import java.util.List;



public class Beobachter {
	
	private static Beobachter beobachter = null;
	private List<Messenger> messenger = new ArrayList<>();
	
	public void addBullet(String id, Projectile bullet) {
		getMesenger(id, true).addBullet(bullet);
	}
	
	public Messenger addCannon(String id, WeaknessCannon cannon) {
		return getMesenger(id, true).addCannon(cannon);
	}
	
	public Messenger addFuse(String id, CannonWeakness fuse) {
		return getMesenger(id, true).addFuse(fuse);
	}
	
	private Messenger getMesenger(String id, boolean createNew) {
		Messenger mes = getMesenger(id);
		if(mes == null && createNew) {
			mes = new Messenger(id);
			messenger.add(mes);
		}
		return mes;
	}
	
	private Messenger getMesenger(String id) {
		for (Messenger mes : messenger) {
			if(mes.getMessage().equals(id)) {
				return mes;
			}
		}
		return null;
	}
	
	private Beobachter() {
		
	}
	
	public static Beobachter getBeobachter() {
		if(beobachter == null) {
			beobachter = new Beobachter();
		}
		return beobachter;
	}
}
