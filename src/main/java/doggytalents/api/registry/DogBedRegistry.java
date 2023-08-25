package doggytalents.api.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import doggytalents.DoggyTalents;
import doggytalents.ModBlocks;
import doggytalents.api.inferface.DefaultDogBedIcon;
import doggytalents.api.inferface.IDogBedIcon;

/**
 * @author ProPercivalalb
 */
public class DogBedRegistry {

    public final static DogBedRegistry CASINGS = new DogBedRegistry("casing");
    public final static DogBedRegistry BEDDINGS = new DogBedRegistry("bedding");

    private final List<String> keys = new ArrayList<String>();
    private final Map<String, String> lookupnames = new HashMap<String, String>();
    private final Map<String, IDogBedIcon> textures = new HashMap<String, IDogBedIcon>();
    private final Map<String, CustomIngredient> craftingItems = new HashMap<String, CustomIngredient>();
    private final String key;

    public DogBedRegistry(String key) {
        this.key = key;
    }

    public boolean isValidId(String id) {
        return this.keys.contains(id);
    }

    public void registerMaterial(String blockId, String textureLocation) {
        this.registerMaterial(blockId, 0);
    }

    public void registerMaterial(Block block, String textureLocation) {
        this.registerMaterial(block, 0);
    }

    public void registerMaterial(String blockId, int meta) {
        if (!Block.blockRegistry.containsKey(blockId))
            DoggyTalents.LOGGER.warn("The block id {} does not exist for a material", blockId);
        else {
            Block block = Block.getBlockFromName(blockId);
            String lookupname = String.format("dogbed.%s.%s.%d", this.key, blockId, meta);
            ItemStack stack = new ItemStack(block, 1, meta);
            this.registerMaterial(blockId + "." + meta, lookupname, new DefaultDogBedIcon(block, meta), stack);
        }
    }

    public void registerMaterial(Block block, int meta) {
        if (block == null || block == Blocks.air) {
            DoggyTalents.LOGGER.warn("Null block cannot be registered for a material");
            return;
        }

        String blockId = Block.blockRegistry.getNameForObject(block);
        String lookupname = String.format("dogbed.%s.%s.%d", this.key, blockId, meta);
        ItemStack stack = new ItemStack(block, 1, meta);
        this.registerMaterial(blockId + "." + meta, lookupname, new DefaultDogBedIcon(block, meta), stack);
    }

    public void registerMaterial(String key, String lookupname, IDogBedIcon dogBedIcon, ItemStack craftingItem) {
        if (this.isValidId(key))
            DoggyTalents.LOGGER.warn("Tried to register a dog bed material with the id {} more that once", key);
        else {
            this.keys.add(key);
            this.lookupnames.put(key, lookupname);
            this.textures.put(key, dogBedIcon);
            this.craftingItems.put(key, CustomIngredient.fromStacks(craftingItem));

            // todo make crash on startup DoggyTalents.LOGGER.info("Register dog bed {} under the key {}", this.key, key);
        }
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public String getLookUpValue(String id) {
        if (!this.isValidId(id)) return null;
        return this.lookupnames.get(id);
    }

    public IIcon getIcon(String id, int side) {
        if (!this.isValidId(id)) return null;
        return this.textures.get(id)
            .getIcon(side);
    }

    public String getIdFromCraftingItem(ItemStack stack) {
        for (Entry<String, CustomIngredient> entry : craftingItems.entrySet()) {
            if (entry.getValue()
                .apply(stack)) return entry.getKey();
        }
        return "";
    }

    public static ItemStack createItemStack(String casingId, String beddingId) {
        ItemStack stack = new ItemStack(ModBlocks.DOG_BED, 1, 0);
        stack.setTagCompound(new NBTTagCompound());

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("casingId", casingId);
        tag.setString("beddingId", beddingId);
        stack.getTagCompound()
            .setTag("doggytalents", tag);
        return stack;
    }
}
