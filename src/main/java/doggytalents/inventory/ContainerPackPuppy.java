package doggytalents.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import doggytalents.entity.EntityDog;

/**
 * @author ProPercivalalb
 */
public class ContainerPackPuppy extends Container {

    private EntityDog dog;

    private InventoryTreatBag inventory;

    public ContainerPackPuppy(EntityPlayer player, EntityDog dog) {
        this.dog = dog;
        this.inventory = (InventoryTreatBag) dog.objects.get("packpuppyinventory");
        inventory.openChest();
        int packpuppyLevel = MathHelper.clamp_int(this.dog.talents.getLevel("packpuppy"), 0, 5);

        for (int j = 0; j < 3; j++) {
            for (int i1 = 0; i1 < packpuppyLevel; i1++)
                this.addSlotToContainer(new SlotPackPuppy(inventory, i1 * 3 + j, 79 + 18 * i1, 1 + 18 * j + 24, dog));
        }

        int var3;
        int var4;

        for (var3 = 0; var3 < 3; ++var3) for (var4 = 0; var4 < 9; ++var4)
            this.addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));

        for (var3 = 0; var3 < 9; ++var3) this.addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 142));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        int packpuppyLevel = MathHelper.clamp_int(this.dog.talents.getLevel("packpuppy"), 0, 5);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (i < 3 * packpuppyLevel) {
                if (!this.mergeItemStack(itemstack1, 3 * packpuppyLevel, this.inventorySlots.size(), true)) return null;
            } else if (!this.mergeItemStack(itemstack1, 0, 3 * packpuppyLevel, false)) return null;

            if (itemstack1.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();

            if (itemstack1.stackSize == itemstack.stackSize) return null;
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return ((IInventory) this.dog.objects.get("packpuppyinventory")).isUseableByPlayer(player);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        this.inventory.closeChest();
    }
}
