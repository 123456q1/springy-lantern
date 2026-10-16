package com.springy.springylantern;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;

public class SpringyLanternMod implements ModInitializer {
    public static final String MOD_ID = "springylantern";
    public static final Block SPRINGY_LANTERN = new com.springy.springylantern.block.SpringyLanternBlock(
            AbstractBlock.Settings.of(Material.METAL)
                    .strength(2.0f)
                    .luminance(state -> state.get(com.springy.springylantern.block.SpringyLanternBlock.LIT) ? 15 : 0)
    );

    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "springy_lantern"), SPRINGY_LANTERN);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "springy_lantern"),
                new BlockItem(SPRINGY_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS)));
        System.out.println("[SpringyLantern] Initialized");
    }
}
