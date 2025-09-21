package vesper.shinyhorses;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class Util {

    public static VertexConsumer renderHorseArmorGlintHook(VertexConsumer old, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, HorseEntity horse,
                                                           float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = horse.getBodyArmor();
        AnimalArmorItem horseArmorItem = (AnimalArmorItem) stack.getItem();
        boolean glint = horseArmorItem.hasGlint(stack);
        if (glint) {
            Identifier texture = horseArmorItem.getEntityTexture();
            return VertexConsumers.union(
                    bufferIn.getBuffer(RenderLayer.getEntityGlint()),
                    bufferIn.getBuffer(RenderLayer.getEntityCutoutNoCull(texture))
            );
        }
        return old;
    }

    public static void checkHorseHook(RegistryEntry<Enchantment> enchantmentIn, LivingEntity entityIn, CallbackInfoReturnable<Integer> cir) {
        if (entityIn instanceof HorseEntity) {
            ItemStack armor = ((HorseEntity) entityIn).getBodyArmor();
            if (armor.getItem() instanceof AnimalArmorItem) {
                int level = EnchantmentHelper.getLevel(enchantmentIn, armor);
                cir.setReturnValue(level);
            }
        }
    }
}
