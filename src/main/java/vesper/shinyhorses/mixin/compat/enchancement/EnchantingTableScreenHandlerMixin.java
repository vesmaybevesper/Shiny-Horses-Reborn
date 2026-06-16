package vesper.shinyhorses.mixin.compat.enchancement;

import moriyashiine.enchancement.common.init.ModEnchantments;
import moriyashiine.enchancement.common.screenhandlers.EnchantingTableScreenHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableScreenHandler.class)
public class EnchantingTableScreenHandlerMixin {

    @Inject(method = "isEnchantmentAllowed", at = @At("TAIL"), cancellable = true)
    private static void isEnchantmentAllowed(Holder<Enchantment> enchantment, ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if(stack.getItem() instanceof AnimalArmorItem) {
            if (enchantment.is(ModEnchantments.BUOY) || enchantment.is(ModEnchantments.ADRENALINE) || enchantment.is(ModEnchantments.AMPHIBIOUS) || enchantment.is(ModEnchantments.STRAFE) || enchantment.is(ModEnchantments.WARDENSPINE)) {
                cir.setReturnValue(true);
            }
        }
    }
}
