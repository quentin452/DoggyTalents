package doggytalents.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import doggytalents.api.IDogTreat;
import doggytalents.api.IDogTreat.EnumFeedBack;
import doggytalents.entity.EntityDog;
import doggytalents.helper.ChatUtil;

public class ItemBigBone extends ItemDT implements IDogTreat {

    public ItemBigBone() {
        super("big_bone");
    }

    @Override
    public EnumFeedBack canGiveToDog(EntityPlayer player, EntityDog dog, int level, int direLevel) {
        if (dog.getGrowingAge() >= 0) return EnumFeedBack.JUSTRIGHT;
        else return EnumFeedBack.TOOYOUNG;
    }

    @Override
    public void giveTreat(EnumFeedBack type, EntityPlayer player, ItemStack stack, EntityDog dog) {
        if (type == EnumFeedBack.JUSTRIGHT) {
            if (!player.capabilities.isCreativeMode) --stack.stackSize;

            if (!player.worldObj.isRemote) {
                dog.setDogSize(dog.getDogSize() + 1);
            }
        } else if (type == EnumFeedBack.TOOYOUNG) {
            if (!player.worldObj.isRemote) {
                player.addChatMessage(ChatUtil.getChatComponentTranslation("dogtreat.tooyoung"));
            }
        }
    }
}
