package vesper.shinyhorses.mixin;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HorseArmorFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import vesper.shinyhorses.Util;

@Mixin(HorseArmorFeatureRenderer.class)
public abstract class HorseArmorFeatureRendererMixin {
    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/HorseEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private VertexConsumer renderArmorGlint(VertexConsumer original, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, HorseEntity horseEntity, float f, float g, float h, float j, float k, float l){
        return Util.renderHorseArmorGlintHook(original, matrixStack, vertexConsumerProvider, i, horseEntity, f, g, h,  j, k, l);
    }
}
