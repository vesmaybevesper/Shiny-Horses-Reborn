package vesper.shinyhorses.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vesper.shinyhorses.Util;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantHelperMixin {
    @Inject(method = "getEnchantmentLevel", at = @At("HEAD"), cancellable = true)
    private static void checkHorse(Holder<Enchantment> enchantment, LivingEntity entity, CallbackInfoReturnable<Integer> cir){
        Util.checkHorseHook(enchantment, entity, cir);
    }
}
