package doggytalents.base.c;

import doggytalents.base.IBridge;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class Bridge implements IBridge {

	@Override
	public Object createBlockPos(int x, int y, int z) {
		return null;
	}
	
	@Override
	public TileEntity getTileEntity(World world, int x, int y, int z) {
		return world.getTileEntity(new BlockPos(x, y, z));
	}

	@Override
	public String translateToLocal(String key) {
		return I18n.translateToLocal(key);
	}

	@Override
	public String translateToLocalFormatted(String key, Object... format) {
		return I18n.translateToLocalFormatted(key, format);
	}
	
	@Override
	public int clamp(int num, int min, int max) {
		return MathHelper.clamp(num, min, max);
	}
	
	@Override
	public float clamp(float num, float min, float max) {
		return MathHelper.clamp(num, min, max);
	}
	
	@Override
	public float cos(float value) {
		return MathHelper.cos(value);
	}
	
	@Override
	public float sin(float value) {
		return MathHelper.sin(value);
	}
	
	@Override
	public int floor(double value) {
		return MathHelper.floor(value);
	}
	
	@Override
	public int ceil(double value) {
		return MathHelper.ceil(value);
	}
	
	@Override
	public float sqrt(double value) {
		return MathHelper.sqrt(value);
	}

	@Override
	public double atan2(double x, double z) {
		return MathHelper.atan2(x, z);
	}
	
	@Override
	public float wrapDegrees(float value) {
		return MathHelper.wrapDegrees(value);
	}
}
