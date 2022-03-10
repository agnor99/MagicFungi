package me.luligabi.magicfungi.client.compat.rei;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import me.luligabi.magicfungi.client.compat.rei.glyph.GlyphDisplayCategory;
import me.luligabi.magicfungi.client.compat.rei.glyph.GlyphRecipeDisplay;
import me.luligabi.magicfungi.client.compat.rei.spell.SpellDisplayCategory;
import me.luligabi.magicfungi.client.compat.rei.spell.SpellRecipeDisplay;
import me.luligabi.magicfungi.common.MagicFungi;
import me.luligabi.magicfungi.common.block.BlockRegistry;
import me.luligabi.magicfungi.common.item.ItemRegistry;
import me.luligabi.magicfungi.common.item.glyph.GlyphRegistry;
import me.luligabi.magicfungi.common.item.spell.SpellRegistry;
import me.luligabi.magicfungi.common.recipe.glyph.GlyphRecipe;
import me.luligabi.magicfungi.common.recipe.spell.SpellRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.item.ItemConvertible;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.Map;

public class ReiPlugin implements REIClientPlugin {


    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new GlyphDisplayCategory());
        registry.addWorkstations(GLYPH_CARVING, EntryStacks.of(BlockRegistry.GLYPH_CARVING_BLOCK));

        registry.add(new SpellDisplayCategory());
        registry.addWorkstations(SPELL_DISCOVERY, EntryStacks.of(BlockRegistry.SPELL_DISCOVERY_BLOCK));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(GlyphRecipe.class, GlyphRecipeDisplay::new);

        registry.registerFiller(SpellRecipe.class, SpellRecipeDisplay::new);


        Map<ItemConvertible, String> reiInformationMap = Maps.newHashMap((new ImmutableMap.Builder<ItemConvertible, String>())
                .put(BlockRegistry.IMPETUS_MUSHROOM_PLANT_BLOCK, "description.magicfungi.impetus_mushroom")
                .put(BlockRegistry.CLYPEUS_MUSHROOM_PLANT_BLOCK, "description.magicfungi.clypeus_mushroom")
                .put(BlockRegistry.UTILIS_MUSHROOM_PLANT_BLOCK, "description.magicfungi.utilis_mushroom")
                .put(BlockRegistry.VIVIFICA_MUSHROOM_PLANT_BLOCK, "description.magicfungi.vivifica_mushroom")
                .put(BlockRegistry.MORBUS_MUSHROOM_PLANT_BLOCK, "description.magicfungi.morbus_mushroom")

                .put(BlockRegistry.GLYPH_CARVING_BLOCK, "description.magicfungi.glyph_carving_workbench")
                .put(BlockRegistry.SPELL_DISCOVERY_BLOCK, "description.magicfungi.spell_discovery_workbench")

                // Glyphs
                .put(GlyphRegistry.EXPONENTIA_GLYPH, "description.magicfungi.exponentia_glyph")

                .put(GlyphRegistry.PLUVIAM_GLYPH, "description.magicfungi.pluviam_glyph")
                .put(GlyphRegistry.CADENTIS_GLYPH, "description.magicfungi.cadentis_glyph")

                .put(GlyphRegistry.PUDICITIAM_GLYPH, "description.magicfungi.pudicitiam_glyph")
                .put(GlyphRegistry.SANCTIFICARE_GLYPH, "description.magicfungi.sanctificare_glyph")

                .put(GlyphRegistry.PARASITUS_GLYPH, "description.magicfungi.parasitus_glyph")
                .put(GlyphRegistry.CORRUMPERE_GLYPH, "description.magicfungi.corrumpere_glyph")
                .put(GlyphRegistry.MALEDICTIO_GLYPH, "description.magicfungi.maledictio_glyph")

                // Spells
                .put(SpellRegistry.IGNEI_SPELL, "description.magicfungi.ignei_spell")
                .put(SpellRegistry.SCINTILLAM_SPELL, "description.magicfungi.scintillam_spell")

                .put(SpellRegistry.CADERE_SPELL, "description.magicfungi.cadere_spell")
                .put(SpellRegistry.GLACIES_SPELL, "description.magicfungi.glacies_spell")

                .put(SpellRegistry.TRACTABILE_SPELL, "description.magicfungi.tractabile_spell")
                .put(SpellRegistry.CIBUS_SPELL, "description.magicfungi.cibus_spell")

                .put(SpellRegistry.FERTILIS_SPELL, "description.magicfungi.fertilis_spell")

                // Misc
                .put(ItemRegistry.GUIDE_BOOK, "description.magicfungi.guide_book")
                .build());

        for(Map.Entry<ItemConvertible, String> entry : reiInformationMap.entrySet()) {
            DefaultInformationDisplay info = DefaultInformationDisplay.createFromEntry(EntryStacks.of(entry.getKey()), new LiteralText(""));
            info.line(new TranslatableText(entry.getValue()));
            registry.add(info);
        }
    }

    public static final CategoryIdentifier<GlyphRecipeDisplay> GLYPH_CARVING = CategoryIdentifier.of(MagicFungi.MOD_ID, "glyph_carving");

    public static final CategoryIdentifier<SpellRecipeDisplay> SPELL_DISCOVERY = CategoryIdentifier.of(MagicFungi.MOD_ID, "spell_discovery");

}