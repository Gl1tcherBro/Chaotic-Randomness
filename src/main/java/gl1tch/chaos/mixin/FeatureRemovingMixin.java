package gl1tch.chaos.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class FeatureRemovingMixin {

    @Inject(at = @At("HEAD"), method = "generateIfValid", cancellable = true)
    private boolean disable(FeatureConfig config, StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(false);
        return callback.getReturnValue();
    }
}
