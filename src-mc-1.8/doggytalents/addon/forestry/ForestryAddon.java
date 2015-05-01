package doggytalents.addon.forestry;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import doggytalents.addon.AddonEvent;
import doggytalents.api.registry.DogBedRegistry;

/**
 * @author ProPercivalalb
 */
public class ForestryAddon {

	private static ForestryAPI API = new ForestryAPI(ForestryLib.MOD_NAME);
	
	@SubscribeEvent
	public void onPre(AddonEvent.Pre event) {
		if(!Loader.isModLoaded(ForestryLib.MOD_NAME))
			return;
	}
	
	@SubscribeEvent
	public void onInit(AddonEvent.Init event) {
		if(!Loader.isModLoaded(ForestryLib.MOD_NAME))
			return;
	}

	@SubscribeEvent
	public void onPost(AddonEvent.Post event) throws Exception {
		if(!Loader.isModLoaded(ForestryLib.MOD_NAME))
			return;
		
		//TODO for(int i = 0; i < ForestryLib.PLANKS_1_COUNT; ++i)
			//TODO DogBedRegistry.CASINGS.registerMaterial(ForestryLib.PLANKS_1_ID, i);
			//TODO for(int i = 0; i < ForestryLib.PLANKS_2_COUNT; ++i)
		//TODO DogBedRegistry.CASINGS.registerMaterial(ForestryLib.PLANKS_2_ID, i);
		
	}
}
