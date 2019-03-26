package doggytalents.entity.ai;

import java.util.HashMap;
import java.util.Map;

import doggytalents.DoggyTalentsMod;
import doggytalents.api.inferface.ITalent;
import doggytalents.api.registry.TalentRegistry;
import doggytalents.entity.EntityDog;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ProPercivalalb
 */
public class TalentFeature extends DogFeature {
	
	private Map<String, Integer> dataMap;
	private String lastSaveString;
	
	public TalentFeature(EntityDog dogIn) {
		super(dogIn);
		this.dataMap = new HashMap<String, Integer>();
		this.lastSaveString = "";
	}
	
	@Override
    public void writeAdditional(NBTTagCompound compound) {
        compound.setString("talents", this.getSaveString());
    }

    @Override
    public void readAdditional(NBTTagCompound compound) {
    	this.dog.dataTracker.setTalentString(compound.getString("talents"));
    	for(ITalent talent : TalentRegistry.getTalents())
    		talent.onLevelUpdate(this.dog, this.getLevel(talent));
    }
	
	public String getSaveString() {
		String saveString = this.dog.dataTracker.getTalentString();
		return saveString == null ? "" : saveString;
	}
	
	public int getLevel(ITalent talent) {
		return this.getLevel(talent.getKey());
	}
	
	public int getLevel(String id) {
		String saveString = this.getSaveString();
		if(!this.lastSaveString.equals(saveString)) {
			this.dataMap.clear();
			String[] split = saveString.split(":");
			if(split.length % 2 == 0 && split.length > 0) {
				for(int i = 0; i < split.length; i += 2)
					this.dataMap.put(split[i], Integer.valueOf(split[i + 1]));
			}
			this.lastSaveString = saveString;
		}
		
		if(!this.dataMap.containsKey(id))
			return 0;
		else {
			int level = this.dataMap.get(id);
			return level;
		}
	}
	
	public void setLevel(ITalent talent, int level) {
		this.setLevel(talent.getKey(), level);
	}
	
	public void setLevel(String id, int level) {
		if(level == this.getLevel(id) && level > 5)
			return;

		this.dataMap.put(id, level);
		TalentRegistry.getTalent(id).onLevelUpdate(this.dog, level);
		
		String saveString = "";
		boolean first = true;
		
		for(String key : this.dataMap.keySet()) {
			if(!first)
				saveString += ":";
			
			saveString += key + ":" + this.dataMap.get(key);
			first = false;
		}
		
		this.dog.dataTracker.setTalentString(saveString);
	}
	
	public void resetTalents() {
		this.dataMap.clear();
		this.dog.dataTracker.setTalentString("");
	}
}