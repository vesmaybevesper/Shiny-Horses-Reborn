package vesper.shinyhorses;

import moriyashiine.enchancement.common.component.entity.BoostInFluidComponent;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShinyHorsesReborn implements ModInitializer {
	public static final String MOD_ID = "shiny-horses-reborn";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		if (FabricLoader.getInstance().isModLoaded("enchancement")) {
			new EntityComponentFactoryRegistry
			registry.registerForPlayers(BOOST_IN_FLUID, BoostInFluidComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		}
	}
}