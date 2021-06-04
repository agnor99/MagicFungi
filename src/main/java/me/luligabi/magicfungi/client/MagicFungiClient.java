package me.luligabi.magicfungi.client;

import me.luligabi.magicfungi.common.registry.BlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(net.fabricmc.api.EnvType.CLIENT)
public class MagicFungiClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ATTACK_MUSHROOM_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.DEFENSE_MUSHROOM_BLOCK, RenderLayer.getCutout());
    }
}