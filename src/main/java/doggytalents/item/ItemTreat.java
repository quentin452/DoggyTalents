package doggytalents.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import doggytalents.api.IDogTreat;
import doggytalents.entity.EntityDog;
import doggytalents.helper.ChatUtil;

/**
 * @author ProPercivalalb
 **/
public class ItemTreat extends ItemDT implements IDogTreat {

    private int maxLevel;

    public ItemTreat(String iconPath, int maxLevel) {
        super(iconPath);
        this.maxLevel = maxLevel;
    }

    @Override
    public EnumFeedBack canGiveToDog(EntityPlayer player, EntityDog dog, int level, int direLevel) {
        if (level < this.maxLevel && dog.getGrowingAge() >= 0) {
            return EnumFeedBack.JUSTRIGHT;
        } else if (dog.getGrowingAge() < 0) {
            return EnumFeedBack.TOOYOUNG;
        } else {
            return EnumFeedBack.LEVELTOOHIGH;
        }
    }

    @Override
    public void giveTreat(EnumFeedBack type, EntityPlayer player, ItemStack stack, EntityDog dog) {

        if (type == EnumFeedBack.JUSTRIGHT) {
            if (!player.capabilities.isCreativeMode) --stack.stackSize;

            if (!player.worldObj.isRemote) {
                dog.levels.increaseLevel();
                dog.setHealth(dog.getMaxHealth());
                dog.getSitAI()
                    .setSitting(true);
                dog.worldObj.setEntityState(dog, (byte) 7);
                dog.playTameEffect(true);
                if (!player.worldObj.isRemote)
                    player.addChatMessage(ChatUtil.getChatComponentTranslation("dogtreat.levelup"));
            }
        } else if (type == EnumFeedBack.TOOYOUNG) {
            if (!player.worldObj.isRemote) {
                dog.playTameEffect(false);
                player.addChatMessage(ChatUtil.getChatComponentTranslation("dogtreat.tooyoung"));
            }
        } else if (type == EnumFeedBack.LEVELTOOHIGH) {
            player.worldObj.setEntityState(dog, (byte) 6);
            if (!player.worldObj.isRemote) {
                dog.playTameEffect(false);
                player.addChatMessage(ChatUtil.getChatComponentTranslation("dogtreat.leveltoohigh"));
            }
        }
    }

}
