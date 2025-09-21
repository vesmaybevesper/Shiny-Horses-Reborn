package vesper.shinyhorses.mixin;

import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalArmorItem.class)
public abstract class AnimalArmorItemMixin {

    @Shadow
    public abstract AnimalArmorItem.Type getType();

    @Inject(method = "isEnchantable",at = @At("RETURN"), cancellable = true)
    private void enchantHorseArmor(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (this.getType() == AnimalArmorItem.Type.EQUESTRIAN) cir.setReturnValue(true);
    }
}
