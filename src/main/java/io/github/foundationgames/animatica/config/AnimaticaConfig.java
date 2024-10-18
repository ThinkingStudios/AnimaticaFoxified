package io.github.foundationgames.animatica.config;

import io.github.foundationgames.animatica.Animatica;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.embeddedt.embeddium.api.options.control.TickBoxControl;
import org.embeddedt.embeddium.api.options.structure.OptionFlag;
import org.embeddedt.embeddium.api.options.structure.OptionGroup;
import org.embeddedt.embeddium.api.options.structure.OptionImpact;
import org.embeddedt.embeddium.api.options.structure.OptionImpl;
import org.embeddedt.embeddium.impl.gui.options.storage.EmbeddiumOptionsStorage;

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

    public static class EmbeddiumExtended {
        private static final EmbeddiumOptionsStorage embeddiumOpts = new EmbeddiumOptionsStorage();
        private static final OptionGroup animatedTextures;

        static {
            animatedTextures = OptionGroup.createBuilder()
                    .setId(Animatica.id("animated_textures"))
                    .add(OptionImpl.createBuilder(Boolean.TYPE, embeddiumOpts)
                            .setName(Text.translatable("option.animatica.animated_textures"))
                            //.setTooltip(Text.of(""))
                            .setControl(TickBoxControl::new)
                            .setBinding((embeddiumOptions, aBoolean) -> AnimaticaConfig.ANIMATED_TEXTURES.set(aBoolean), embeddiumOptions -> AnimaticaConfig.ANIMATED_TEXTURES.get())
                            .setImpact(OptionImpact.VARIES)
                            .setFlags(new OptionFlag[]{OptionFlag.REQUIRES_ASSET_RELOAD})
                            .build()
                    ).build();
        }

        public static OptionGroup getAnimatedTextures() {
            return animatedTextures;
        }
    }
}