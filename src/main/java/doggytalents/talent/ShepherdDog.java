package doggytalents.talent;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;

import doggytalents.api.inferface.ITalent;
import doggytalents.entity.EntityDog;
import doggytalents.entity.ModeUtil.EnumMode;

/**
 * @author ProPercivalalb
 */
public class ShepherdDog extends ITalent {

    @Override
    public void onLivingUpdate(EntityDog dog) {
        int level = dog.talents.getLevel(this);
        int masterOrder = dog.masterOrder();

        if (level > 0) {
            if (masterOrder == 3 && dog.getAttackTarget() != null) {
                double d0 = dog.getDistanceSqToEntity(dog.getAttackTarget());
                if (d0 <= 4.0D) {
                    dog.getAttackTarget()
                        .mountEntity(dog);
                    dog.setAttackTarget(null);
                }
            }

            if (dog.isTamed() && masterOrder != 3 && dog.riddenByEntity instanceof EntityAnimal) {
                dog.riddenByEntity.ridingEntity = null;
                dog.riddenByEntity = null;
            }
        }
    }

    @Override
    public int onHungerTick(EntityDog dog, int totalInTick) {
        if (dog.riddenByEntity != null && !dog.isControllingPassengerPlayer())
            totalInTick += 5 - dog.talents.getLevel(this);
        return totalInTick;
    }

    @Override
    public boolean shouldDamageMob(EntityDog dog, Entity entity) {
        if (dog.mode.isMode(EnumMode.DOCILE) && dog.masterOrder() == 3) return false;
        return true;
    }

    @Override
    public String getKey() {
        return "shepherddog";
    }

}
