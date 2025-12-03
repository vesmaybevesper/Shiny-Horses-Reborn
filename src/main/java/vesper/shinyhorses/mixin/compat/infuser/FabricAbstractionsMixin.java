package vesper.shinyhorses.mixin.compat.infuser;

import fuzs.puzzleslib.fabric.impl.core.FabricAbstractions;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FabricAbstractions.class, remap = false)
public class FabricAbstractionsMixin {

    @Inject(method = "canApplyAtEnchantingTable", at = @At("HEAD"), cancellable = true)
    private static void allowHorseArmor(RegistryEntry<Enchantment> par1, ItemStack par2, CallbackInfoReturnable<Boolean> cir){
        if (par2.getItem() instanceof AnimalArmorItem){
            String enchantmentId = par1.toString().toLowerCase();
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
