package protectalgae;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.data.gamerule.GameRuleBoolean;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.util.dependency.Key;

public class ProtectAlgae implements ModInitializer {
	public static final String MOD_ID = HalpLibe.registerMod("protectalgae", true);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * When true, snow does not freeze water that has algae on top of it.
	 * Registered in {@link #beforeGameStart()} so that the game's registries are ready.
	 */
	public static GameRuleBoolean PROTECT_ALGAE;

	@Override
	public void onInitialize() {
		CommonEvents.BEFORE_GAME_START.listen(Key.of(MOD_ID), this::beforeGameStart);
		LOGGER.info("Protect Algae initialized.");
	}

	public void beforeGameStart() {
		PROTECT_ALGAE = GameRules.register(new GameRuleBoolean("protectAlgae", "gamerule.protect_algae", false));
	}

	public static boolean isEnabled(World world) {
		return PROTECT_ALGAE != null && world.getGameRuleValue(PROTECT_ALGAE);
	}
}
