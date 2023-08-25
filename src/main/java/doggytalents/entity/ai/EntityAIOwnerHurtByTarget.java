package doggytalents.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

import doggytalents.entity.EntityDog;
import doggytalents.entity.ModeUtil.EnumMode;

/**
 * @author ProPercivalalb
 **/
public class EntityAIOwnerHurtByTarget extends EntityAITarget {

    private EntityDog dog;
    private EntityLivingBase theOwnerAttacker;
    private int timestamp;

    public EntityAIOwnerHurtByTarget(EntityDog dog) {
        super(dog, false);
        this.dog = dog;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.dog.isTamed() || !this.dog.mode.isMode(EnumMode.AGGRESIVE)
            || this.dog.isIncapacicated()
            || this.dog.isSitting()) return false;

        EntityLivingBase owner = this.dog.getOwner();
        if (owner == null) return false;

        this.theOwnerAttacker = owner.getAITarget();
        return this.theOwnerAttacker != null && this.theOwnerAttacker != this.dog
            && this.theOwnerAttacker.isEntityAlive();
    }

    @Override
    public boolean continueExecuting() {
        return !this.dog.isIncapacicated() && !this.dog.isSitting() && super.continueExecuting();
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        super.startExecuting();
    }
}
