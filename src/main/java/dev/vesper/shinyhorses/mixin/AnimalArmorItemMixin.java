package dev.vesper.shinyhorses.mixin;

import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalArmorItem.class)
public class AnimalArmorItemMixin {
    @Final
    @Shadow
    private AnimalArmorItem.BodyType bodyType;

    @Inject(method = "isEnchantable",at = @At("RETURN"), cancellable = true)
    private void enchantHorseArmor(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (this.bodyType == AnimalArmorItem.BodyType.EQUESTRIAN) cir.setReturnValue(true);
    }
}
