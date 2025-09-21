package vesper.shinyhorses.mixin;

import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method ={"getEnchantability()I"} , at = {@At("HEAD")}, cancellable = true)
    private void horseArmorEnchantability(CallbackInfoReturnable<Integer> cir){
        if ((Object) this instanceof AnimalArmorItem horseArmor){
            String material = String.valueOf(horseArmor.getMaterial());
            int enchantability = switch (material){
                case "diamond" -> 10;
                case "gold" -> 25;
                case "iron" -> 9;
                case "leather" -> 15;
                default -> 1;
            };
            cir.setReturnValue(enchantability);
        }
    }

    @Inject(method = "isEnchantable", at = @At("HEAD"), cancellable = true)
    private void horseArmorEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (stack.getItem() instanceof AnimalArmorItem){
            cir.setReturnValue(true);
        }
    }
}
