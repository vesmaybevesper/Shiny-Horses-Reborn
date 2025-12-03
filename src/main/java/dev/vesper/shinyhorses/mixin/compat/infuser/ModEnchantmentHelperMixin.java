package dev.vesper.shinyhorses.mixin.compat.infuser;

import fuzs.enchantinginfuser.util.ModEnchantmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Mixin(ModEnchantmentHelper.class)
public class ModEnchantmentHelperMixin {
    @Unique
    private static final Set<String> ALLOWED_ENCHANTS = Set.of("protection", "fire_protection", "blast_protection", "projectile_protection",
            "thorns", "feather_falling");

    @Inject(method = "getEnchantmentsForItem", at = @At("HEAD"), cancellable = true)
    private static void horseArmorEnchants(RegistryAccess registryAccess, ItemStack itemStack, TagKey<Enchantment> availableEnchantments, boolean primaryOnly, CallbackInfoReturnable<Collection<Holder<Enchantment>>> cir){
        if (itemStack.getItem() instanceof AnimalArmorItem){
            var registry = registryAccess.registryOrThrow(Registries.ENCHANTMENT);
            var tags = registry.getTag(availableEnchantments);
            if (tags.isPresent()){
                var result = new HashSet<Holder<Enchantment>>();

                for (var entry : tags.get()){
                    String id = entry.getRegisteredName().toLowerCase();

                    for (String allowed : ALLOWED_ENCHANTS){
                        if (id.contains(allowed)){
                            result.add(entry);
                            break;
                        }
                    }
                }
                cir.setReturnValue(Collections.unmodifiableCollection(result));
            }
        }
    }
}
