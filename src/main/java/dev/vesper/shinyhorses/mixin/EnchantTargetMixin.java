package dev.vesper.shinyhorses.mixin;

import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantTargetMixin {
    @Inject(method = "isSupportedItem", at = @At("RETURN"), cancellable = true)
    private void enchantHorseArmor(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir){
        Enchantment enchantment = (Enchantment) (Object) this;
        if (itemStack.getItem() instanceof AnimalArmorItem){
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
