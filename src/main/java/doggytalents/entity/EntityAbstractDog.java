package doggytalents.entity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.lib.Reference;

public abstract class EntityAbstractDog extends EntityTameable {

    private static final Map<EntityAbstractDog, ForgeChunkManager.Ticket> entityTickets = new HashMap<>();
    // #todo fix https://github.com/quentin452/DoggyTalents/issues/2  private boolean isInitialized = false;
    private float headRotationCourse;
    private float headRotationCourseOld;
    private boolean isWet;
    public boolean isShaking;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;

    public EntityAbstractDog(World world) {
        super(world);
        this.setSize(0.6F, 0.85F);
    }
// #todo fix https://github.com/quentin452/DoggyTalents/issues/2
    /*
    public static void requestTicket(Entity entity) {

        if (!(entity instanceof EntityAbstractDog)) return;

        EntityAbstractDog dog = (EntityAbstractDog) entity;

        if (!dog.isInitialized) return;

        ForgeChunkManager.Ticket ticket = getOrCreateTicket(dog);
        forceChunkLoading(ticket, dog);

    }

    private static ForgeChunkManager.Ticket getOrCreateTicket(EntityAbstractDog dog) {
        ForgeChunkManager.Ticket ticket = entityTickets.get(dog);

        if (ticket == null) {
            ticket = ForgeChunkManager.requestTicket(Reference.MOD_ID, dog.worldObj, ForgeChunkManager.Type.ENTITY);
            entityTickets.put(dog, ticket);
        }

        return ticket;
    }

    private static void forceChunkLoading(ForgeChunkManager.Ticket ticket, EntityAbstractDog dog) {

        if (dog == null) {
            return;
        }

        ticket.bindEntity(dog);

        ChunkCoordIntPair coords;

        if (dog != null) {
            coords = new ChunkCoordIntPair(
                MathHelper.floor_double(dog.posX) >> 4,
                MathHelper.floor_double(dog.posZ) >> 4);
        } else {
            return;
        }

        ForgeChunkManager.forceChunk(ticket, coords);

        ChunkCoordIntPair neighborCoords = null;

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {

                if (dog != null) {
                    neighborCoords = new ChunkCoordIntPair(coords.chunkXPos + x, coords.chunkZPos + z);
                }

            }

            ForgeChunkManager.forceChunk(ticket, neighborCoords);

        }
    }

    protected static boolean worldReady(World world) {
        return world != null;
    }

    private static ForgeChunkManager.Ticket getTicket(EntityAbstractDog entity) {
        return entityTickets.get(entity);
    }

     */

    @Override
    protected void updateAITick() {
        super.updateAITick();
// #todo fix https://github.com/quentin452/DoggyTalents/issues/2
     /*   if (worldReady(worldObj)) {
            requestTicket(this);
        }

      */
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(25, 0); // Boolean data
        // #todo fix https://github.com/quentin452/DoggyTalents/issues/2 isInitialized = true;
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    protected String getLivingSound() {
        return (this.rand.nextInt(3) == 0
            ? (this.isTamed() && this.getHealth() < this.getMaxHealth() / 2 ? "mob.wolf.whine" : "mob.wolf.panting")
            : "mob.wolf.bark");
    }

    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }

    @Override
    public float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected Item getDropItem() {
        return Item.getItemById(-1);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.worldObj.setEntityState(this, (byte) 8);
            this.playSound("mob.wolf.step", 0.15F, 1.0F);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        // #todo fix https://github.com/quentin452/DoggyTalents/issues/2 updateChunkLoading();
        this.headRotationCourseOld = this.headRotationCourse;

        if (this.isBegging()) this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
        else this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;

        if (this.isWet()) {
            this.isWet = true;
            this.isShaking = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        } else if ((this.isWet || this.isShaking) && this.isShaking) {
            if (this.timeWolfIsShaking == 0.0F) this.playSound(
                "mob.wolf.shake",
                this.getSoundVolume(),
                (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;

            if (this.prevTimeWolfIsShaking >= 2.0F) {
                this.isWet = false;
                this.isShaking = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;

                this.onFinishShaking();
            }

            if (this.timeWolfIsShaking > 0.4F) {
                float f = (float) this.boundingBox.minY;
                int i = (int) (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j) {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.worldObj.spawnParticle(
                        "splash",
                        this.posX + (double) f1,
                        (double) (f + 0.8F),
                        this.posZ + (double) f2,
                        this.motionX,
                        this.motionY,
                        this.motionZ);
                }
            }
        }
    }
// #todo fix https://github.com/quentin452/DoggyTalents/issues/2
  /*  private void updateChunkLoading() {
        if (this.worldObj == null || !this.isEntityAlive()) {
            releaseChunkTicket();
            return;
        }

        requestTicket(this);
    }

    @Override
    protected void onDeathUpdate() {
        releaseChunkTicket();
        entityTickets.remove(this);
    }

    private void releaseChunkTicket() {
        ForgeChunkManager.Ticket ticket = entityTickets.remove(this);
        if (ticket != null) {
            ForgeChunkManager.releaseTicket(ticket);
        }
    }

   */

    @SideOnly(Side.CLIENT)
    public boolean isDogWet() {
        return this.isWet;
    }

    @SideOnly(Side.CLIENT)
    public float getShadingWhileWet(float partialTickTime) {
        return 0.75F
            + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * partialTickTime)
                / 2.0F
                * 0.25F;
    }

    @SideOnly(Side.CLIENT)
    public float getShakeAngle(float partialTickTime, float p_70923_2_) {
        float f = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * partialTickTime
            + p_70923_2_) / 1.8F;

        f = MathHelper.clamp_float(f, 0.0F, 1.0F);

        return MathHelper.sin(f * (float) Math.PI) * MathHelper.sin(f * (float) Math.PI * 11.0F)
            * 0.15F
            * (float) Math.PI;
    }

    @SideOnly(Side.CLIENT)
    public float getInterestedAngle(float partialTickTime) {
        return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * partialTickTime)
            * 0.15F
            * (float) Math.PI;
    }

    @SideOnly(Side.CLIENT)
    public float getTailRotation() {
        return this.isTamed()
            ? (0.55F - ((this.getMaxHealth() - this.getHealth()) / (this.getMaxHealth() / 20.0F)) * 0.02F)
                * (float) Math.PI
            : ((float) Math.PI / 5F);
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.8F;
    }

    @Override
    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte id) {
        if (id == 8) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        } else super.handleHealthUpdate(id);
    }

    @Override
    public double getYOffset() {
        return this.ridingEntity instanceof EntityPlayer ? -1D : 0.0D;
    }

    public boolean isBegging() {
        return this.getCustomData(5);
    }

    public void setBegging(boolean beg) {
        this.setCustomData(5, beg);
    }

    public void setCustomData(int BIT, boolean flag) {
        int in = this.dataWatcher.getWatchableObjectInt(25);
        if (flag) in |= (1 << BIT);
        else in &= ~(1 << BIT);
        this.dataWatcher.updateObject(25, in);
    }

    public boolean getCustomData(int BIT) {
        return (this.dataWatcher.getWatchableObjectInt(25) & (1 << BIT)) == (1 << BIT);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack != null && DoggyTalentsAPI.BREED_WHITELIST.containsItem(stack);
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal == this) return false;
        else if (!this.isTamed()) return false;
        else if (!(otherAnimal instanceof EntityAbstractDog)) return false;
        else {
            EntityAbstractDog entitydog = (EntityAbstractDog) otherAnimal;
            return !entitydog.isTamed() ? false
                : (entitydog.isSitting() ? false : this.isInLove() && entitydog.isInLove());
        }
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public boolean canBeSteered() {
        return this.riddenByEntity instanceof EntityPlayer;
    }

    @Override
    public boolean func_142018_a(EntityLivingBase target, EntityLivingBase owner) {
        if (!(target instanceof EntityCreeper) && !(target instanceof EntityGhast)) {
            if (target instanceof EntityDog) {
                EntityDog entitydog = (EntityDog) target;

                if (entitydog.isTamed() && entitydog.getOwner() == owner) return false;
            } else if (target instanceof EntityWolf) {
                EntityWolf entitywolf = (EntityWolf) target;

                if (entitywolf.isTamed() && entitywolf.getOwner() == owner) return false;
            }

            if (target instanceof EntityPlayer && owner instanceof EntityPlayer
                && !((EntityPlayer) owner).canAttackPlayer((EntityPlayer) target)) return false;
            else if (target == owner) return false;
            else return !(target instanceof EntityHorse) || !((EntityHorse) target).isTame();
        }

        return false;
    }

    private void onFinishShaking() {

    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
    }
}
