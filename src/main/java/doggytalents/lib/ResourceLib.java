package doggytalents.lib;

import java.util.HashMap;

import net.minecraft.util.ResourceLocation;

/**
 * @author ProPercivalalb
 */
public class ResourceLib {

    public static final ResourceLocation GUI_FOOD_BOWL = new ResourceLocation(
        "doggytalents",
        getGuiTexturePath("food_bowl.png"));
    public static final ResourceLocation GUI_PACK_PUPPY = new ResourceLocation(
        "doggytalents",
        getGuiTexturePath("pack_puppy.png"));
    public static final ResourceLocation GUI_TREAT_BAG = new ResourceLocation(
        "doggytalents",
        getGuiTexturePath("treat_bag.png"));

    public static HashMap<Integer, ResourceLocation> doggyTameSkins = new HashMap<Integer, ResourceLocation>();
    public static HashMap<Integer, ResourceLocation> doggyFancyCollars = new HashMap<Integer, ResourceLocation>();

    public static final ResourceLocation MOB_LAYER_DOG_COLLAR = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_collar.png"));
    public static final ResourceLocation MOB_DOG_TAME = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_tame.png"));
    public static final ResourceLocation MOB_LAYER_DOG_HURT = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_hurt.png"));
    public static final ResourceLocation MOB_DOG_WILD = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggywild.png"));
    public static final ResourceLocation MOB_LAYER_RADIO_COLLAR = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_radio_collar.png"));
    public static final ResourceLocation MOB_LAYER_SADDLE = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_saddle.png"));
    public static final ResourceLocation MOB_LAYER_CAPE = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_cape.png"));
    public static final ResourceLocation MOB_LAYER_SUNGLASSES = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_sunglasses.png"));
    public static final ResourceLocation MOB_LAYER_SUNGLASSES_NIGHT = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_sunglasses_night.png"));
    public static final ResourceLocation MOB_LAYER_CAPE_COLOURED = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_cape_coloured.png"));
    public static final ResourceLocation MOB_LAYER_LEATHER_JACKET = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_leather_jacket.png"));
    public static final ResourceLocation MOB_LAYER_CAPE2_1 = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_cape1_1.png"));
    public static final ResourceLocation MOB_LAYER_ARMOR = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_armor.png"));
    public static final ResourceLocation MOB_LAYER_WINGS = new ResourceLocation(
        "doggytalents",
        getMobTexturePath("doggy_wings.png"));

    public static ResourceLocation getTameSkin(int index) {
        if (!doggyTameSkins.containsKey(index)) doggyTameSkins
            .put(index, new ResourceLocation("doggytalents", getMobTexturePath("dog/doggytex" + index + ".png")));
        return doggyTameSkins.get(index);
    }

    public static ResourceLocation getFancyCollar(int index) {
        if (!doggyFancyCollars.containsKey(index)) doggyFancyCollars
            .put(index, new ResourceLocation("doggytalents", getMobTexturePath("doggy_collar_" + index + ".png")));
        return doggyFancyCollars.get(index);
    }

    /**
     * Gets a local gui texture file path.
     * 
     * @param textureFileName The .png file that relates to the texture file.
     * @return The whole path string including the given parameter.
     */
    public static String getGuiTexturePath(String textureFileName) {
        return String.format("%s/gui/%s", new Object[] { getOverrideTexturesPath(), textureFileName });
    }

    /**
     * Gets a local gui texture file path.
     * 
     * @param textureFileName The .png file that relates to the texture file.
     * @return The whole path string including the given parameter.
     */
    public static String getMobTexturePath(String textureFileName) {
        return String.format("%s/mob/%s", new Object[] { getOverrideTexturesPath(), textureFileName });
    }

    /**
     * Gets the location of the mods textures.
     * 
     * @return The default texture local
     */
    private static String getOverrideTexturesPath() {
        return "textures";
    }
}
