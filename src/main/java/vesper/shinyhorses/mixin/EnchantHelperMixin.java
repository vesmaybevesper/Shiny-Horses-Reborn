package vesper.shinyhorses.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vesper.shinyhorses.Util;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantHelperMixin {
    @Inject(method = "getEquipmentLevel", at = @At("HEAD"), cancellable = true)
    private static void checkHorse(RegistryEntry<Enchantment> enchantment, LivingEntity entity, CallbackInfoReturnable<Integer> cir){
        Util.checkHorseHook(enchantment, entity, cir);
    }
}
