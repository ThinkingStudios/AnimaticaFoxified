package io.github.foundationgames.animatica.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.foundationgames.animatica.animation.AnimationLoader;
import io.github.foundationgames.animatica.config.AnimaticaConfig;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    @ModifyVariable(method = "_setShaderTexture(ILnet/minecraft/util/Identifier;)V", at = @At("HEAD"), index = 1, argsOnly = true)
    private static Identifier animatica$replaceWithAnimatedTexture(Identifier old) {
        if (AnimaticaConfig.ANIMATED_TEXTURES.get()) {
            var anim = AnimationLoader.INSTANCE.getAnimationId(old);
            if (anim != null) {
                return anim;
            }
        }
        return old;
    }
}
