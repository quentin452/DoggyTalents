package doggytalents.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import doggytalents.ModItems;

/**
 * @author ProPercivalalb
 */
public class CreativeTabDoggyTalents extends CreativeTabs {

    public CreativeTabDoggyTalents() {
        super("doggytalents");
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.TRAINING_TREAT;
    }
}
