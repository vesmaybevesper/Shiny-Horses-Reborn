package vesper.shinyhorses.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.world.entity.animal.horse.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import vesper.shinyhorses.Util;

@Mixin(HorseArmorLayer.class)
public abstract class HorseArmorFeatureRendererMixin {
    @ModifyVariable(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private VertexConsumer renderArmorGlint(VertexConsumer original, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, Horse horseEntity, float f, float g, float h, float j, float k, float l){
        return Util.renderHorseArmorGlintHook(original, matrixStack, vertexConsumerProvider, i, horseEntity, f, g, h,  j, k, l);
    }
}
