package doggytalents.network.packet.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import cpw.mods.fml.relauncher.Side;
import doggytalents.entity.EntityDog;
import doggytalents.network.AbstractMessage.AbstractServerMessage;

public class DogFriendlyFireMessage extends AbstractServerMessage {

    public int entityId;
    public boolean friendlyFire;

    public DogFriendlyFireMessage() {}

    public DogFriendlyFireMessage(int entityId, boolean obey) {
        this.entityId = entityId;
        this.friendlyFire = obey;
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.entityId = buffer.readInt();
        this.friendlyFire = buffer.readBoolean();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeBoolean(this.friendlyFire);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        Entity target = player.worldObj.getEntityByID(this.entityId);
        if (!(target instanceof EntityDog)) return;

        EntityDog dog = (EntityDog) target;

        dog.setFriendlyFire(this.friendlyFire);
    }
}
