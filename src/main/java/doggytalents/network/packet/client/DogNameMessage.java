package doggytalents.network.packet.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import doggytalents.entity.EntityDog;
import doggytalents.network.AbstractMessage.AbstractServerMessage;

public class DogNameMessage extends AbstractServerMessage {

    public int entityId;
    public String name;

    public DogNameMessage() {}

    public DogNameMessage(int entityId, String name) {
        this.entityId = entityId;
        this.name = name;
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.entityId = buffer.readInt();
        this.name = ByteBufUtils.readUTF8String(buffer);
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        ByteBufUtils.writeUTF8String(buffer, this.name);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        Entity target = player.worldObj.getEntityByID(this.entityId);

        if (!(target instanceof EntityDog)) return;

        EntityDog dog = (EntityDog) target;

        dog.setCustomNameTag(this.name);
    }
}
