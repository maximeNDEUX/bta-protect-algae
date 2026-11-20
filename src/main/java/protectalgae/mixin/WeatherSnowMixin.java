package protectalgae.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.weather.WeatherSnow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import protectalgae.ProtectAlgae;

/**
 * Snow freezes the still water found under the highest solid block. Algae do not block motion,
 * so the game looks straight through them and freezes the water they float on, which then breaks them.
 * When the protectAlgae gamerule is enabled, these mixins skip freezing when algae sit on the water.
 */
@Mixin(value = WeatherSnow.class, remap = false)
@SuppressWarnings("deprecation") // Chunk.getBlockID(int, int, int) is what WeatherSnow itself uses
public abstract class WeatherSnowMixin {

	/**
	 * Freezing while it snows: the second {@code setBlockWithNotify} call places ice at (x, y, z).
	 */
	@WrapWithCondition(
		method = "doEnvironmentUpdate",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockWithNotify(IIII)Z", ordinal = 1)
	)
	private boolean protectalgae$skipIceUnderAlgae(World world, int x, int y, int z, int id) {
		return !(ProtectAlgae.isEnabled(world) && world.getBlockId(x, y + 1, z) == Blocks.ALGAE.id());
	}

	/**
	 * Freezing when a chunk loads during snowfall: the second {@code setBlockIDWithMetadataRaw} call
	 * places ice at (x, y, z).
	 */
	@WrapWithCondition(
		method = "doChunkLoadEffect",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/chunk/Chunk;setBlockIDWithMetadataRaw(IIIII)Z", ordinal = 1)
	)
	private boolean protectalgae$skipChunkLoadIceUnderAlgae(Chunk chunk, int x, int y, int z, int id, int meta,
															 @Local(argsOnly = true) World world) {
		return !(ProtectAlgae.isEnabled(world) && chunk.getBlockID(x, y + 1, z) == Blocks.ALGAE.id());
	}

	/**
	 * The third {@code setBlockIDWithMetadataRaw} call turns the water under the new ice into flowing water.
	 * Only do it if the ice was actually placed above.
	 */
	@WrapWithCondition(
		method = "doChunkLoadEffect",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/chunk/Chunk;setBlockIDWithMetadataRaw(IIIII)Z", ordinal = 2)
	)
	private boolean protectalgae$skipFlowingWaterWithoutIce(Chunk chunk, int x, int y, int z, int id, int meta) {
		return chunk.getBlockID(x, y + 1, z) == Blocks.ICE.id();
	}
}
