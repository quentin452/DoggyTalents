package doggytalents.entity.ai;

import java.util.Random;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

import doggytalents.entity.EntityDog;
import doggytalents.entity.ModeUtil.EnumMode;

public class EntityAIDogWander extends EntityAIBase {

    protected final EntityDog dog;
    protected double x;
    protected double y;
    protected double z;
    protected final double speed;
    protected boolean mustUpdate;

    public EntityAIDogWander(EntityDog dog, double speedIn) {
        this.dog = dog;
        this.speed = speedIn;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.mustUpdate) {
            if (this.dog.getAge() >= 100) return false;

            if (this.dog.getRNG()
                .nextInt(this.dog.mode.isMode(EnumMode.WANDERING) ? 40 : 120) != 0) return false;
        }

        Vec3 vec3d = this.dog.mode.isMode(EnumMode.WANDERING) ? generateRandomPos(this.dog) : this.getPosition(10);

        if (vec3d == null) return false;
        else {
            this.x = vec3d.xCoord;
            this.y = vec3d.yCoord;
            this.z = vec3d.zCoord;
            this.mustUpdate = false;
            return true;
        }
    }

    protected Vec3 getPosition(int xz) {
        return RandomPositionGenerator.findRandomTarget(this.dog, xz, 7);
    }

    private static Vec3 generateRandomPos(EntityDog dog) {
        PathNavigate pathnavigate = dog.getNavigator();
        Random random = dog.getRNG();
        int bowlPosX = dog.coords.getBowlX();
        int bowlPosY = dog.coords.getBowlY();
        int bowlPosZ = dog.coords.getBowlZ();

        int xzRange = 5;
        int yRange = 6;

        float bestWeight = -99999.0F;
        int x = 0, y = 0, z = 0;

        for (int attempt = 0; attempt < 10; ++attempt) {
            int l = random.nextInt(2 * xzRange + 1) - xzRange;
            int i1 = random.nextInt(2 * yRange + 1) - yRange;
            int j1 = random.nextInt(2 * xzRange + 1) - xzRange;

            float weight = dog.getBlockPathWeight(l + bowlPosX, i1 + bowlPosY, j1 + bowlPosZ);

            if (weight > bestWeight) {
                bestWeight = weight;
                x = l;
                y = i1;
                z = j1;
            }

        }

        return Vec3.createVectorHelper(bowlPosX + x, bowlPosY + y, bowlPosZ + z);
    }

    @Override
    public boolean continueExecuting() {
        return !this.dog.getNavigator()
            .noPath();
    }

    @Override
    public void startExecuting() {
        this.dog.getNavigator()
            .tryMoveToXYZ(this.x, this.y, this.z, this.speed);
    }

    public void makeUpdate() {
        this.mustUpdate = true;
    }
}
