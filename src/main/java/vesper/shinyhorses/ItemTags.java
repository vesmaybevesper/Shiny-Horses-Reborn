package vesper.shinyhorses;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTags {
    public static final TagKey<Item> HORSE_ARMOR = TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("horse_armor"));
}
