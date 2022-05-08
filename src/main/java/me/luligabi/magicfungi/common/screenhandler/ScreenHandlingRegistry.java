package me.luligabi.magicfungi.common.screenhandler;

import me.luligabi.magicfungi.common.MagicFungi;
import me.luligabi.magicfungi.common.screenhandler.essence.EssenceExtractorScreenHandler;
import me.luligabi.magicfungi.common.screenhandler.glyph.GlyphCarvingScreenHandler;
import me.luligabi.magicfungi.common.screenhandler.misc.MorbusClockScreenHandler;
import me.luligabi.magicfungi.common.screenhandler.spell.SpellDiscoveryScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

// TODO: Refactor the rest of the registries to follow this pattern
public class ScreenHandlingRegistry {


    public static final ScreenHandlerType<GlyphCarvingScreenHandler> GLYPH_CARVING_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MagicFungi.MOD_ID, "glyph_carving_workbench"), GlyphCarvingScreenHandler::new);;
    public static final ScreenHandlerType<SpellDiscoveryScreenHandler> SPELL_DISCOVERY_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MagicFungi.MOD_ID, "spell_discovery_workbench"), SpellDiscoveryScreenHandler::new);
    public static final ScreenHandlerType<EssenceExtractorScreenHandler> ESSENCE_EXTRACTOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MagicFungi.MOD_ID, "essence_extractor"), EssenceExtractorScreenHandler::new);

    public static final ScreenHandlerType<MorbusClockScreenHandler> MORBUS_CLOCK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MagicFungi.MOD_ID, "morbus_clock"), MorbusClockScreenHandler::new);


    public static void init() {
        // NO-OP
    }

    private ScreenHandlingRegistry() {
        // NO-OP
    }
}