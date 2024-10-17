package gl1tch.chaos.mixin;

import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MathHelper.class)
public class MathChangingMixin {
    private static final float[] SINE_TABLE = (float[]) Util.make(new float[65536], (sineTable) -> {
        for(int i = 0; i < sineTable.length; ++i) {
            sineTable[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 65536.0);
        }

    });



    @Inject(at = @At("HEAD"), method = "cos", cancellable = true)
    private static float cosToSin(float value, CallbackInfoReturnable<Float> callback) {
        callback.setReturnValue(SINE_TABLE[(int)(value * 10430.378F) & '\uffff']);
        return callback.getReturnValue();
    }

    @Inject(at = @At("HEAD"), method = "sin", cancellable = true)
    private static float sinToCos(float value, CallbackInfoReturnable<Float> callback) {
        callback.setReturnValue(SINE_TABLE[(int)(value * 10430.378F + 16384.0F) & '\uffff']);
        return callback.getReturnValue();
    }

    @Inject(at = @At("HEAD"), method = "nextGaussian", cancellable = true)
    private static float funny(Random random, float mean, float deviation, CallbackInfoReturnable<Float> callback) {
        callback.setReturnValue(mean + (float)random.nextGaussian() / deviation);
        return callback.getReturnValue();
    }

    @Inject(at = @At("HEAD"), method = "perlinFade", cancellable = true)
    private static double funny2(double value, CallbackInfoReturnable<Double> callback) {
        callback.setReturnValue(value * value * value * (value * (value / 6.0 + 15.0) - 10.0));
        return callback.getReturnValue();
    }
}
