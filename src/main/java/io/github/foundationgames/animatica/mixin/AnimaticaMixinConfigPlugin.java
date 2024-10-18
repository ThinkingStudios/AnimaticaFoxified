package io.github.foundationgames.animatica.mixin;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.neoforged.fml.loading.FMLLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class AnimaticaMixinConfigPlugin implements IMixinConfigPlugin {
    private final Object2BooleanMap<String> conditionalMixins = new Object2BooleanOpenHashMap<>();

    @Override
    public void onLoad(String mixinPackage) {
        boolean sodiumInstalled = FMLLoader.getLoadingModList().getModFileById("sodium") != null;
        this.conditionalMixins.put("io.github.foundationgames.animatica.mixin.sodium.SodiumGameOptionPagesMixin", sodiumInstalled);
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return this.conditionalMixins.getOrDefault(mixinClassName, true);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
