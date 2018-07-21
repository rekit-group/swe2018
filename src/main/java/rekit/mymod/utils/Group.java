package rekit.mymod.utils;

import java.util.ArrayList;
import java.util.List;

import rekit.logic.gameelements.GameElement;

public class Group {
	
	private GameElement master;
	private List<Notifyable> notifyableList = new ArrayList<>();
	
	public void setMaster(GameElement master) {
		this.master = master;
	}
	
	public boolean masterIsNull() {
		return master == null;
	}
	
	public void addNotifyable(Notifyable n) {
		notifyableList.add(n);
	}
	
	public void nofiy() {
		for(Notifyable n : notifyableList) {
			n.run(master);
		}
	}

}
