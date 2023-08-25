package doggytalents.network.packet.client;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import doggytalents.ModItems;
import doggytalents.entity.EntityDog;
import doggytalents.entity.ModeUtil.EnumMode;
import doggytalents.helper.ChatUtil;
import doggytalents.network.AbstractMessage.AbstractServerMessage;

// TODO CHEck is the same as 1.12
public class CommandMessage extends AbstractServerMessage {

    public int commandId;

    public CommandMessage() {}

    public CommandMessage(int commandId) {
        this.commandId = commandId;
    }

    @Override
    protected void read(PacketBuffer buffer) {
        this.commandId = buffer.readInt();
    }

    @Override
    protected void write(PacketBuffer buffer) {
        buffer.writeInt(this.commandId);

    }

    @Override
    public void process(EntityPlayer player, Side side) {
        World world = player.worldObj;
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack == null) return;

        if (stack.getItem() == ModItems.COMMAND_EMBLEM) {

            if (this.commandId == 1) {
                world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
                boolean isDog = false;
                List nearEnts = world
                    .getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(20D, 20D, 20D));
                for (Object o : nearEnts) {
                    if (o instanceof EntityDog) {
                        EntityDog dog = (EntityDog) o;
                        if (dog.canInteract(player)) {
                            dog.getSitAI()
                                .setSitting(false);
                            dog.setPathToEntity((PathEntity) null);
                            dog.setTarget((Entity) null);
                            dog.setAttackTarget((EntityLivingBase) null);
                            if (dog.mode.isMode(EnumMode.WANDERING)) {
                                dog.mode.setMode(EnumMode.DOCILE);
                            }
                            isDog = true;
                        }
                    }
                }
                if (isDog) {
                    player.addChatMessage(ChatUtil.getChatComponentTranslation("dogcommand.come"));
                }
            } else if (this.commandId == 2) {
                world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
                boolean isDog = false;
                List nearEnts = world
                    .getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(20D, 20D, 20D));
                for (Object o : nearEnts) {
                    if (o instanceof EntityDog) {
                        EntityDog dog = (EntityDog) o;
                        if (dog.canInteract(player)) {
                            dog.getSitAI()
                                .setSitting(true);
                            dog.setPathToEntity((PathEntity) null);
                            dog.setTarget((Entity) null);
                            dog.setAttackTarget((EntityLivingBase) null);
                            if (dog.mode.isMode(EnumMode.WANDERING)) {
                                dog.mode.setMode(EnumMode.DOCILE);
                            }
                            isDog = true;
                        }
                    }
                }
                if (isDog) {
                    player.addChatMessage(ChatUtil.getChatComponentTranslation("dogcommand.stay"));
                }
            } else if (this.commandId == 3) {
                world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
                boolean isDog = false;
                List nearEnts = world
                    .getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(20D, 20D, 20D));
                for (Object o : nearEnts) {
                    if (o instanceof EntityDog) {
                        EntityDog dog = (EntityDog) o;
                        if (dog.canInteract(player)) {
                            if (dog.getMaxHealth() / 2 >= dog.getHealth()) {
                                dog.getSitAI()
                                    .setSitting(true);
                                dog.setPathToEntity((PathEntity) null);
                                dog.setTarget((Entity) null);
                                dog.setAttackTarget((EntityLivingBase) null);
                            } else {
                                dog.getSitAI()
                                    .setSitting(false);
                                dog.setPathToEntity((PathEntity) null);
                                dog.setTarget((Entity) null);
                                dog.setAttackTarget((EntityLivingBase) null);
                            }
                            isDog = true;
                        }
                    }
                }
                if (isDog) {
                    player.addChatMessage(ChatUtil.getChatComponentTranslation("dogcommand.ok"));
                }
            } else if (this.commandId == 4) {
                world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
                boolean isDog = false;
                List nearEnts = world
                    .getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(20D, 20D, 20D));
                for (Object o : nearEnts) {
                    if (o instanceof EntityDog) {
                        EntityDog dog = (EntityDog) o;
                        if (dog.canInteract(player) && !dog.isSitting() && !dog.mode.isMode(EnumMode.WANDERING)) {
                            int i = MathHelper.floor_double(player.posX) - 2;
                            int j = MathHelper.floor_double(player.posZ) - 2;
                            int k = MathHelper.floor_double(player.boundingBox.minY);
                            for (int l = 0; l <= 4; l++) {
                                for (int i1 = 0; i1 <= 4; i1++) {
                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3)
                                        && World.doesBlockHaveSolidTopSurface(world, i + l, k - 1, j + i1)
                                        && !world.getBlock(i + l, k, j + i1)
                                            .isNormalCube()
                                        && !world.getBlock(i + l, k + 1, j + i1)
                                            .isNormalCube()
                                        && world.getBlock(i + l, k + 1, j + i1) != Blocks.flowing_lava
                                        && world.getBlock(i + l, k + 1, j + i1) != Blocks.lava) {
                                        dog.setLocationAndAngles(
                                            (float) (i + l) + 0.5F,
                                            k,
                                            (float) (j + i1) + 0.5F,
                                            dog.rotationYaw,
                                            dog.rotationPitch);
                                    }
                                }
                            }
                            isDog = true;
                        }
                    }
                }

                if (isDog) {
                    player.addChatMessage(ChatUtil.getChatComponentTranslation("dogcommand.heel"));
                }
            }

        }

    }

}
