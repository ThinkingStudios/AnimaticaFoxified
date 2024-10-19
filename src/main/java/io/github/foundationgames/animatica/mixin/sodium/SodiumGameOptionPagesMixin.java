package io.github.foundationgames.animatica.mixin.sodium;

import io.github.foundationgames.animatica.config.AnimaticaConfig;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptionPages;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptions;
import net.caffeinemc.mods.sodium.client.gui.options.*;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;
import net.caffeinemc.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import net.minecraft.client.util.Monitor;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Restriction(require = @Condition("sodium"))
@Mixin(value = SodiumGameOptionPages.class, remap = false)
public abstract class SodiumGameOptionPagesMixin {
    @Shadow @Final private static SodiumOptionsStorage sodiumOpts;

    @Inject(method = "general", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", remap = false, ordinal = 2, shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private static void insertSetting(CallbackInfoReturnable<OptionPage> cir, Monitor monitor, @NotNull List<OptionGroup> groups) {
        OptionImpl<SodiumGameOptions, Boolean> animatedTextures = OptionImpl.createBuilder(Boolean.TYPE, sodiumOpts)
                .setName(Text.translatable("option.animatica.animated_textures"))
                .setTooltip(Text.of(""))
                .setControl(TickBoxControl::new)
                .setBinding((sodiumOptions, aBoolean) -> AnimaticaConfig.ANIMATED_TEXTURES.set(aBoolean), sodiumOptions -> AnimaticaConfig.ANIMATED_TEXTURES.get())
                .setImpact(OptionImpact.VARIES)
                .setFlags(new OptionFlag[]{OptionFlag.REQUIRES_ASSET_RELOAD})
                .build();
        groups.add(OptionGroup.createBuilder().add(animatedTextures).build());
    }
}
