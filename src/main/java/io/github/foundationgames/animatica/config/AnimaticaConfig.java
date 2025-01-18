package io.github.foundationgames.animatica.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.neoforged.neoforge.common.ModConfigSpec;

public class AnimaticaConfig {
    private static final ModConfigSpec.Builder BUILDER;
    public static final ModConfigSpec.BooleanValue ANIMATED_TEXTURES;
    public static final ModConfigSpec SPEC;

    static {
        BUILDER = new ModConfigSpec.Builder();
        ANIMATED_TEXTURES = BUILDER.translation("option.animatica.animated_textures").define("animated_textures", true);
        SPEC = BUILDER.build();
    }

    public static class VanillaExtended {
        private static final SimpleOption<Boolean> animatedTexturesOption;

        static {
            animatedTexturesOption = SimpleOption.ofBoolean(
                    "option.animatica.animated_textures",
                    AnimaticaConfig.ANIMATED_TEXTURES.getAsBoolean(),
                    value -> {
                        AnimaticaConfig.ANIMATED_TEXTURES.set(value);
                        MinecraftClient.getInstance().reloadResources();
                    }
            );
        }

        public static SimpleOption<Boolean> getAnimatedTexturesOption() {
            return animatedTexturesOption;
        }
    }
}