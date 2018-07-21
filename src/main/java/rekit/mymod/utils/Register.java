package rekit.mymod.utils;

import java.util.HashMap;

import rekit.logic.gameelements.GameElement;

public class Register {
	
	private static Register singleRegister;

	private HashMap<Integer, Group> groups = new HashMap();
	
	private Register () {
		
	}
	
	public static Register getRegister () {
		if(singleRegister == null) {
			singleRegister = new Register();
		}
		return singleRegister;
	}
	
	public boolean addGroup (int id, GameElement master) {
		if(groups.containsKey(id)) {
			if(groups.get(id).masterIsNull()) {
				groups.get(id).setMaster(master);
				return true;
			} else {
				return false;
			}
		}
		groups.put(id, new Group());
		return true;
	}
	
	/**
	 * 
	 * @param id
	 * @return null if the id doesn´t exist.
	 */
	public Group getGroup (int id) {
		if(groups.containsKey(id)) {
			return groups.get(id);
		} else {
			groups.put(id, new Group());
			return groups.get(id);
		}
		
	}
	
	public void removeGroup(int id) {
		groups.remove(id);
	}
}
