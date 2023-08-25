package doggytalents.network.packet.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import cpw.mods.fml.relauncher.Side;
import doggytalents.entity.EntityDog;
import doggytalents.network.AbstractMessage.AbstractServerMessage;

public class DogModeMessage extends AbstractServerMessage {

    public int entityId, doggyMode;

    public DogModeMessage() {}

    public DogModeMessage(int entityId, int dogMode) {
        this.entityId = entityId;
        this.doggyMode = dogMode;
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.entityId = buffer.readInt();
        this.doggyMode = buffer.readInt();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.doggyMode);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        Entity target = player.worldObj.getEntityByID(this.entityId);
        if (!(target instanceof EntityDog)) return;

        EntityDog dog = (EntityDog) target;

        dog.mode.setMode(this.doggyMode);
    }
}
