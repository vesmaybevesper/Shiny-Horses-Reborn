package vesper.shinyhorses;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class Util {

    public static VertexConsumer renderHorseArmorGlintHook(VertexConsumer old, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Horse horse,
                                                           float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = horse.getBodyArmorItem();
        AnimalArmorItem horseArmorItem = (AnimalArmorItem) stack.getItem();
        boolean glint = horseArmorItem.isFoil(stack);
        if (glint) {
            ResourceLocation texture = horseArmorItem.getTexture();
            return VertexMultiConsumer.create(
                    bufferIn.getBuffer(RenderType.entityGlint()),
                    bufferIn.getBuffer(RenderType.entityCutoutNoCull(texture))
            );
        }
        return old;
    }

    public static void checkHorseHook(Holder<Enchantment> enchantmentIn, LivingEntity entityIn, CallbackInfoReturnable<Integer> cir) {
        if (entityIn instanceof Horse) {
            ItemStack armor = ((Horse) entityIn).getBodyArmorItem();
            if (armor.getItem() instanceof AnimalArmorItem) {
                int level = EnchantmentHelper.getItemEnchantmentLevel(enchantmentIn, armor);
                cir.setReturnValue(level);
            }
        }
    }
}
