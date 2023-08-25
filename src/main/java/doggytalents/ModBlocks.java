package doggytalents;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;
import doggytalents.block.BlockDogBath;
import doggytalents.block.BlockDogBed;
import doggytalents.block.BlockFoodBowl;
import doggytalents.block.ItemDogBed;
import doggytalents.tileentity.TileEntityDogBath;
import doggytalents.tileentity.TileEntityDogBed;
import doggytalents.tileentity.TileEntityFoodBowl;

/**
 * @author ProPercivalalb
 */
public class ModBlocks {

    public static Block DOG_BED;
    public static Block DOG_BATH;
    public static Block FOOD_BOWL;

    public static void init() {
        DoggyLogger.LOGGER.info("Registering Blocks");
        DOG_BED = new BlockDogBed().setBlockName("doggytalents.dogbed");
        DOG_BATH = new BlockDogBath().setBlockName("doggytalents.dogbath");
        FOOD_BOWL = new BlockFoodBowl().setBlockName("doggytalents.foodbowl");

        GameRegistry.registerTileEntity(TileEntityDogBed.class, "doggytalents:dog_bed");
        GameRegistry.registerTileEntity(TileEntityDogBath.class, "doggytalents:dog_bath");
        GameRegistry.registerTileEntityWithAlternatives(
            TileEntityFoodBowl.class,
            "doggytalents:dog_bowl",
            "doggytalents.dog_bowl");

        DOG_BED.setHarvestLevel("axe", 0);
        DOG_BATH.setHarvestLevel("pickaxe", 0);
        FOOD_BOWL.setHarvestLevel("pickaxe", 0);

        GameRegistry.registerBlock(DOG_BED, ItemDogBed.class, "dog_bed");
        GameRegistry.registerBlock(DOG_BATH, "dog_bath");
        GameRegistry.registerBlock(FOOD_BOWL, "food_bowl");
    }
}
