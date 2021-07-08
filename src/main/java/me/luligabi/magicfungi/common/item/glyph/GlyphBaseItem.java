package me.luligabi.magicfungi.common.item.glyph;

import me.luligabi.magicfungi.common.util.MushroomType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class GlyphBaseItem extends Item {

    protected SoundEvent soundEvent;
    protected MushroomType mushroomType;
    protected BlockPos blockPos;

    public GlyphBaseItem (Item.Settings settings) {
        super(settings);
        setMushroomType(MushroomType.INCOGNITA);
    }

    @Override //TODO: Fix glyph dupe when opening another UI after using glyphs.
    public ActionResult useOnBlock(ItemUsageContext context) {
        blockPos = context.getBlockPos();
        World world = context.getWorld();
        PlayerEntity user = context.getPlayer();
        if (!world.isClient) {
            executeBlockGlyph(user);
        }
        if (!user.getAbilities().creativeMode && executeBlockGlyph(user)) {
            context.getStack().decrement(1);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getEntityWorld();
        if (!world.isClient) {
            executeEntityGlyph(user, entity);
        }
        if (!user.getAbilities().creativeMode && executeEntityGlyph(user, entity)) {
            stack.decrement(1);
        }
        return ActionResult.CONSUME;
    }

    private void executeGlyph(PlayerEntity playerEntity) {
        playerEntity.getEntityWorld().playSound(null,
                playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                soundEvent, SoundCategory.NEUTRAL, 1F, 1F);
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    protected boolean executeBlockGlyph(PlayerEntity playerEntity) {
        executeGlyph(playerEntity);
        return true;
    }

    protected boolean executeEntityGlyph(PlayerEntity playerEntity, LivingEntity livingEntity) {
        executeGlyph(playerEntity);
        return true;
    }

    protected void setSound(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    public MushroomType getMushroomType() {
        return mushroomType;
    }

    protected void setMushroomType(MushroomType mushroomType) {
        this.mushroomType = mushroomType;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("tooltip.magicfungi.spell_info.1")
                .formatted(MushroomType.getDarkColor(getMushroomType()), Formatting.BOLD)
                .append(new TranslatableText("tooltip.magicfungi.spell_info.2", mushroomType.getFancyName(), mushroomType.getStatsName())
                        .formatted(MushroomType.getLightColor(getMushroomType()))));
    }
}