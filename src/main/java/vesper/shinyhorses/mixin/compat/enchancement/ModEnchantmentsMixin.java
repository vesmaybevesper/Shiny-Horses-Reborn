package vesper.shinyhorses.mixin.compat.enchancement;

import moriyashiine.enchancement.common.Enchancement;
import moriyashiine.enchancement.common.enchantment.effect.ModifySubmergedMovementSpeedEffect;
import moriyashiine.enchancement.common.enchantment.effect.entity.ConditionalAttributeEnchantmentEffect;
import moriyashiine.enchancement.common.enchantment.effect.entity.SetExtendedWaterTimeEffect;
import moriyashiine.enchancement.common.init.ModEnchantmentEffectComponentTypes;
import moriyashiine.enchancement.common.init.ModEnchantments;
import moriyashiine.enchancement.common.lootcondition.HasExtendedWaterTimeLootCondition;
import moriyashiine.enchancement.common.lootcondition.WetLootCondition;
import moriyashiine.enchancement.common.util.SubmersionGate;
import moriyashiine.enchancement.data.provider.ModEnchantmentTagProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vesper.shinyhorses.ItemTags;

import static moriyashiine.enchancement.common.init.ModEnchantments.BUOY;

@Mixin(ModEnchantments.class)
public class ModEnchantmentsMixin {

    @Shadow
    public static Enchantment create(ResourceLocation id, HolderSet<Item> supportedItems, int maxLevel, EquipmentSlotGroup slot, ModEnchantments.EffectsAdder effectsAdder) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment() && !ModEnchantmentTagProvider.ALL_ENCHANCEMENT_ENCHANTMENTS.contains(id)) {
            ModEnchantmentTagProvider.ALL_ENCHANCEMENT_ENCHANTMENTS.add(id);
        }

        Enchantment.Builder builder = Enchantment.enchantment(Enchantment.definition(supportedItems, 5, maxLevel, Enchantment.dynamicCost(5, 6), Enchantment.dynamicCost(20, 6), 2, new EquipmentSlotGroup[]{slot}));
        effectsAdder.addEffects(builder);
        return builder.build(id);
    }

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void bootstrap(BootstrapContext<Enchantment> registerable, CallbackInfo ci) {
        HolderGetter<Item> itemLookup = registerable.lookup(Registries.ITEM);

        registerable.register(BUOY, create(BUOY.location(), itemLookup.getOrThrow(ItemTags.HORSE_ARMOR), 2, EquipmentSlotGroup.BODY, (builder) -> {
            builder.withEffect(ModEnchantmentEffectComponentTypes.EXTEND_WATER_TIME);
            builder.withSpecialEffect(ModEnchantmentEffectComponentTypes.BOOST_IN_FLUID, new AddValue(LevelBasedValue.perLevel(1.0F, 0.5F)));
            builder.withEffect(ModEnchantmentEffectComponentTypes.FLUID_WALKING);
            builder.withEffect(ModEnchantmentEffectComponentTypes.PREVENT_SWIMMING);
            builder.withSpecialEffect(ModEnchantmentEffectComponentTypes.MODIFY_SUBMERGED_MOVEMENT_SPEED, new ModifySubmergedMovementSpeedEffect(new AddValue(LevelBasedValue.perLevel(0.35F)), SubmersionGate.WATER_ONLY));
            builder.withEffect(EnchantmentEffectComponents.TICK, new SetExtendedWaterTimeEffect(LevelBasedValue.perLevel(6.0F, 4.0F)), () -> WetLootCondition.INSTANCE);
            builder.withEffect(EnchantmentEffectComponents.TICK, new ConditionalAttributeEnchantmentEffect(new EnchantmentAttributeEffect(Enchancement.id("enchantment.buoy"), Attributes.STEP_HEIGHT, LevelBasedValue.constant(1.0F), AttributeModifier.Operation.ADD_VALUE), HasExtendedWaterTimeLootCondition.INSTANCE));
            builder.withEffect(EnchantmentEffectComponents.TICK, new ConditionalAttributeEnchantmentEffect(new EnchantmentAttributeEffect(Enchancement.id("enchantment.buoy"), Attributes.SAFE_FALL_DISTANCE, LevelBasedValue.perLevel(2.0F), AttributeModifier.Operation.ADD_VALUE), HasExtendedWaterTimeLootCondition.INSTANCE));
        }));
    }
}
