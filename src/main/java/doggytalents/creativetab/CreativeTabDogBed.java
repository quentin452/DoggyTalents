package doggytalents.creativetab;

import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doggytalents.ModItems;
import doggytalents.api.registry.DogBedRegistry;

/**
 * @author ProPercivalalb
 */
public class CreativeTabDogBed extends CreativeTabs {

    private Random random = new Random();

    @SideOnly(Side.CLIENT)
    private ItemStack iconItemStack;

    public CreativeTabDogBed() {
        super("doggytalents.dogbed");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        if (this.iconItemStack == null) this.iconItemStack = DogBedRegistry.createItemStack(
            pickRandomString(DogBedRegistry.CASINGS.getKeys()),
            pickRandomString(DogBedRegistry.BEDDINGS.getKeys()));

        return this.iconItemStack;
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.TRAINING_TREAT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List p_78018_1_) {
        for (String beddingId : DogBedRegistry.BEDDINGS.getKeys())
            for (String casingId : DogBedRegistry.CASINGS.getKeys())
                p_78018_1_.add(DogBedRegistry.createItemStack(casingId, beddingId));
    }

    public String pickRandomString(List<String> strs) {
        return strs.get(random.nextInt(strs.size()));
    }
}
