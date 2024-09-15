package io.github.foundationgames.animatica;

import io.github.foundationgames.animatica.animation.AnimationLoader;
import io.github.foundationgames.animatica.config.AnimaticaConfig;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.embeddedt.embeddium.api.OptionPageConstructionEvent;
import org.embeddedt.embeddium.client.gui.options.StandardOptions;

@Mod(Animatica.NAMESPACE)
public class Animatica {
    public static final Logger LOG = LogManager.getLogger("Animatica");
    public static final String NAMESPACE = "animatica";

    public Animatica(IEventBus modEventBus) {
        if (FMLLoader.getDist().isClient()) {
            NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, TickEvent.ClientTickEvent.class, event -> {
                if (event.phase == TickEvent.Phase.START) {
                    AnimationLoader.INSTANCE.tickTextures();
                }
            });

            modEventBus.addListener(RegisterClientReloadListenersEvent.class, event -> {
                event.registerReloadListener(AnimationLoader.INSTANCE);
            });

            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AnimaticaConfig.SPEC);
            ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));

            if (ModList.get().isLoaded("embeddium")) {
                OptionPageConstructionEvent.BUS.addListener(event -> {
                    if (event.getId().matches(StandardOptions.Pages.GENERAL)) {
                        event.addGroup(AnimaticaConfig.EmbeddiumExtendedConfig.getAnimatedTextures());
                    }
                });
            }
        }
    }

    public static Identifier id(String path) {
        return new Identifier(NAMESPACE, path);
    }
}
