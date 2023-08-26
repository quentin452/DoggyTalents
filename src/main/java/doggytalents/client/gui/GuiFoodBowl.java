package doggytalents.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import doggytalents.inventory.ContainerFoodBowl;
import doggytalents.lib.ResourceLib;
import doggytalents.tileentity.TileEntityFoodBowl;

/**
 * @author ProPercivalalb
 **/
public class GuiFoodBowl extends GuiContainer {

    private final TileEntityFoodBowl foodBowl;

    public GuiFoodBowl(InventoryPlayer playerInventory, TileEntityFoodBowl par2TileEntityFoodBowl) {
        super(new ContainerFoodBowl(playerInventory, par2TileEntityFoodBowl));
        this.foodBowl = par2TileEntityFoodBowl;
        this.ySize = 127;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int var1, int var2) {
        String inventoryName = this.foodBowl.inventory.getInventoryName();
        String localizedInventoryName = StatCollector.translateToLocal(inventoryName);
        this.fontRendererObj.drawString(localizedInventoryName, 10, 8, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
            .bindTexture(ResourceLib.GUI_FOOD_BOWL);
        int var2 = (this.width - this.xSize) / 2;
        int var3 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var2, var3, 0, 0, this.xSize, this.ySize);
    }
}
