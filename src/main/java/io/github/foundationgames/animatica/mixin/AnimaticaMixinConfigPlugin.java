package io.github.foundationgames.animatica.mixin;

import io.github.foundationgames.animatica.Animatica;
import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;

import java.util.List;
import java.util.Set;

public class AnimaticaMixinConfigPlugin extends RestrictiveMixinConfigPlugin {
    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    protected void onRestrictionCheckFailed(String mixinClassName, String reason) {
        Animatica.LOG.error("Disabled mixin {} due to {}", mixinClassName, reason);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }
}
