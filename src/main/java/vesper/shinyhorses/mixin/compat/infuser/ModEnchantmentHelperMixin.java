package vesper.shinyhorses.mixin.compat.infuser;

import fuzs.enchantinginfuser.util.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
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
    private static void horseArmorEnchants(DynamicRegistryManager registryAccess, ItemStack itemStack, TagKey<Enchantment> availableEnchantments, boolean primaryOnly, CallbackInfoReturnable<Collection<RegistryEntry<Enchantment>>> cir){
        if (itemStack.getItem() instanceof AnimalArmorItem){
            var registry = registryAccess.get(RegistryKeys.ENCHANTMENT);
            var tags = registry.getEntryList(availableEnchantments);
            if (tags.isPresent()){
                var result = new HashSet<RegistryEntry<Enchantment>>();

                for (var entry : tags.get()){
                    String id = entry.getIdAsString().toLowerCase();

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
