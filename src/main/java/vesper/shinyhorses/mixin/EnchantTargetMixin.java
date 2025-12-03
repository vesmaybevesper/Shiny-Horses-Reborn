package vesper.shinyhorses.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantTargetMixin {
    @Inject(method = "isSupportedItem", at = @At("RETURN"), cancellable = true)
    private void enchantHorseArmor(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        Enchantment enchantment = (Enchantment) (Object) this;
        if (stack.getItem() instanceof AnimalArmorItem){
            String enchantmentId = enchantment.toString().toLowerCase();
            boolean isAllowedEnchantment = enchantmentId.contains("protection") ||
                    enchantmentId.contains("fire_protection") ||
                    enchantmentId.contains("blast_protection") ||
                    enchantmentId.contains("projectile_protection") ||
                    enchantmentId.contains("thorns") ||
                    enchantmentId.contains("feather_falling");

            cir.setReturnValue(isAllowedEnchantment);
        }
    }
}
