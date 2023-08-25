package doggytalents.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import doggytalents.entity.EntityDog;
import doggytalents.inventory.ContainerPackPuppy;
import doggytalents.inventory.InventoryPackPuppy;
import doggytalents.lib.ResourceLib;

/**
 * @author ProPercivalalb
 */
public class GuiPackPuppy extends GuiContainer {

    private EntityDog dog;
    private InventoryPackPuppy inventory;

    public GuiPackPuppy(EntityPlayer player, EntityDog dog) {
        super(new ContainerPackPuppy(player, dog));
        this.dog = dog;
        this.inventory = (InventoryPackPuppy) this.dog.objects.get("packpuppyinventory");
        this.allowUserInput = false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String name = inventory.getInventoryName();
        this.fontRendererObj.drawString(name, this.xSize / 2 - 10, 14, 4210752);
        this.fontRendererObj
            .drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 95 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTickTime, int xMouse, int yMouse) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
            .bindTexture(ResourceLib.GUI_PACK_PUPPY);
        int l = (this.width - this.xSize) / 2;
        int i1 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(l, i1, 0, 0, this.xSize, this.ySize);

        for (int j1 = 0; j1 < 3; j1++)
            for (int k1 = 0; k1 < MathHelper.clamp_int(this.dog.talents.getLevel("packpuppy"), 0, 5); k1++)
                this.drawTexturedModalRect(l + 78 + 18 * k1, i1 + 9 + 18 * j1 + 15, 197, 2, 18, 18);

        int petPosX = l + 51;
        int petPosY = i1 + 75;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) petPosX, (float) petPosY, 50.0F);
        GL11.glScalef(-50.0F, 50.0F, 50.0F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f1 = this.dog.renderYawOffset;
        float f2 = this.dog.rotationYaw;
        float f3 = this.dog.rotationPitch;
        float f4 = this.dog.prevRotationYawHead;
        float f5 = this.dog.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan((double) (partialTickTime / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        this.dog.renderYawOffset = (float) Math.atan((double) (xMouse / 40.0F)) * 20.0F;
        this.dog.rotationYaw = (float) Math.atan((double) (xMouse / 40.0F)) * 40.0F;
        this.dog.rotationPitch = -((float) Math.atan((double) (yMouse / 40.0F))) * 20.0F;
        this.dog.rotationYawHead = this.dog.rotationYaw;
        this.dog.prevRotationYawHead = this.dog.rotationYaw;
        GL11.glTranslatef(0.0F, this.dog.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(this.dog, 0.0D, 0.0D, 0.0D, 0.0F, partialTickTime);
        this.dog.renderYawOffset = f1;
        this.dog.rotationYaw = f2;
        this.dog.rotationPitch = f3;
        this.dog.prevRotationYawHead = f4;
        this.dog.rotationYawHead = f5;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
    }
}
