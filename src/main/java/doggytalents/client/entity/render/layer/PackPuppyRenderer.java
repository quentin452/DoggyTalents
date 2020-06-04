package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;

import doggytalents.client.entity.model.DogBackpackModel;
import doggytalents.client.entity.model.DogModel;
import doggytalents.common.entity.DogEntity;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;

public class PackPuppyRenderer implements ITalentRenderer {

    private final EntityModel<DogEntity> model;

    public PackPuppyRenderer() {
        this.model = new DogBackpackModel(0.0F);
    }

    @Override
    public void render(LayerRenderer<DogEntity, DogModel<DogEntity>> layer, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, DogEntity dog, int level, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!dog.isInvisible()) {
            layer.getEntityModel().copyModelAttributesTo(this.model);
            this.model.setLivingAnimations(dog, limbSwing, limbSwingAmount, partialTicks);
            this.model.setRotationAngles(dog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            LayerRenderer.renderCutoutModel(this.model, Resources.TALENT_CHEST, matrixStackIn, bufferIn, packedLightIn, dog, 1.0f, 1.0f, 1.0f);
        }
    }
}