package doggytalents.entity.ai;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityMob;

import doggytalents.entity.EntityDog;
import doggytalents.entity.ModeUtil.EnumMode;

/**
 * @author ProPercivalalb
 **/
public class EntityAIModeAttackTarget extends EntityAITarget {

    private EntityDog dog;
    private EntityLivingBase entityToAttack;
    private int timestamp;

    public EntityAIModeAttackTarget(EntityDog dog) {
        super(dog, true);
        this.dog = dog;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.dog.isTamed() || this.dog.mode.isMode(EnumMode.DOCILE)
            || this.dog.isIncapacicated()
            || this.dog.isSitting()) return false;
        else {
            EntityLivingBase entitylivingbase = this.dog.getOwner();

            if (entitylivingbase == null) return false;
            else {
                if (this.dog.mode.isMode(EnumMode.BERSERKER)) {
                    double distance = 16D;
                    List<Entity> list = dog.worldObj.getEntitiesWithinAABBExcludingEntity(
                        dog,
                        dog.boundingBox.expand(distance, distance, distance));

                    for (Entity entity1 : list) {
                        if (!(entity1 instanceof EntityMob)) continue;

                        this.entityToAttack = (EntityLivingBase) entity1;
                        if (this.isSuitableTarget(this.entityToAttack, false)
                            && this.dog.func_142018_a(this.entityToAttack, entitylivingbase)) return true;
                    }
                } else if (this.dog.mode.isMode(EnumMode.AGGRESIVE)) {
                    this.entityToAttack = entitylivingbase.getAITarget();
                    return this.entityToAttack != null && this.isSuitableTarget(this.entityToAttack, false)
                        && this.dog.func_142018_a(this.entityToAttack, entitylivingbase);
                }
                return false;
            }
        }
    }

    @Override
    public boolean continueExecuting() {
        return !this.dog.isIncapacicated() && !this.dog.isSitting() && super.continueExecuting();
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.entityToAttack);
        super.startExecuting();
    }
}
