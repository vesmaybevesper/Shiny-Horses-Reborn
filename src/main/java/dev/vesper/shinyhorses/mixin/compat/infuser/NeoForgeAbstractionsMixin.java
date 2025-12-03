package dev.vesper.shinyhorses.mixin.compat.infuser;

import fuzs.puzzleslib.neoforge.impl.core.NeoForgeAbstractions;
import net.minecraft.core.Holder;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NeoForgeAbstractions.class, remap = false)
public class NeoForgeAbstractionsMixin {
    @Inject(method = "canApplyAtEnchantingTable", at = @At("HEAD"), cancellable = true)
    private static void allowHorseArmor(Holder<Enchantment> enchantment, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir){
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
