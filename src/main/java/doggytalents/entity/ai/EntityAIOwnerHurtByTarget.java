package doggytalents.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

import doggytalents.entity.EntityDog;
import doggytalents.entity.ModeUtil.EnumMode;

/**
 * @author ProPercivalalb
 */
public class EntityAIOwnerHurtByTarget extends EntityAITarget {

    private static final int REVENGE_TIMER_INDEX = 20;
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
        else {
            EntityLivingBase owner = this.dog.getOwner();

            if (owner == null) return false;
            else {
                this.theOwnerAttacker = owner.getAITarget();
                int i = owner.getDataWatcher()
                    .getWatchableObjectInt(REVENGE_TIMER_INDEX);
                return i != this.timestamp && this.isSuitableTarget(this.theOwnerAttacker, false)
                    && this.dog.func_142018_a(this.theOwnerAttacker, owner);
            }
        }
    }

    @Override
    public boolean continueExecuting() {
        return !this.dog.isIncapacicated() && !this.dog.isSitting() && super.continueExecuting();
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        EntityLivingBase owner = this.dog.getOwner();

        if (owner != null) this.timestamp = owner.getDataWatcher()
            .getWatchableObjectInt(REVENGE_TIMER_INDEX);

        super.startExecuting();
    }
}
