package doggytalents.addon.extratrees;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

import doggytalents.api.inferface.IDogBedIcon;

/**
 * @author ProPercivalalb
 */
public class ExtraTreesBedIcon implements IDogBedIcon {

    private Block block;
    private int metadata;

    public ExtraTreesBedIcon(Block block) {
        this(block, 0);
    }

    public ExtraTreesBedIcon(Block block, int meta) {
        this.block = block;
        this.metadata = meta;
    }

    @Override
    public IIcon getIcon(int side) {
        return this.block.getIcon(side, this.metadata);
    }
}
