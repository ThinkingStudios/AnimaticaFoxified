package io.github.foundationgames.animatica;

import io.github.foundationgames.animatica.animation.AnimationLoader;
import io.github.foundationgames.animatica.config.AnimaticaConfig;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.embeddedt.embeddium.api.OptionPageConstructionEvent;
import org.embeddedt.embeddium.api.options.structure.StandardOptions;

import java.util.Locale;

@Mod(value = Animatica.NAMESPACE, dist = Dist.CLIENT)
public class Animatica {
    public static final Logger LOG = LogManager.getLogger("Animatica");
    public static final String NAMESPACE = "animatica";

    public Animatica(IEventBus modEventBus, ModContainer modContainer) {
        if (FMLLoader.getDist().isClient()) {
            NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, ClientTickEvent.Pre.class, event -> {
                AnimationLoader.INSTANCE.tickTextures();
            });

            modEventBus.addListener(RegisterClientReloadListenersEvent.class, event -> {
                event.registerReloadListener(AnimationLoader.INSTANCE);
            });

            modContainer.registerConfig(ModConfig.Type.CLIENT, AnimaticaConfig.SPEC, String.format(Locale.ROOT, "%s.toml", NAMESPACE));
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

            if (ModList.get().isLoaded("embeddium")) {
                OptionPageConstructionEvent.BUS.addListener(event -> {
                    if (event.getId().matches(StandardOptions.Pages.GENERAL)) {
                        event.addGroup(AnimaticaConfig.EmbeddiumExtended.getAnimatedTextures());
                    }
                });
            }
        }
    }

    public static Identifier id(String path) {
        return Identifier.of(NAMESPACE, path);
    }
}
