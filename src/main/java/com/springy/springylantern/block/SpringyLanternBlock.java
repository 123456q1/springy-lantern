package com.springy.springylantern.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.StateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class SpringyLanternBlock extends Block {
    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    public SpringyLanternBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    // 右键切换点亮状态
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            boolean lit = !state.get(LIT);
            world.setBlockState(pos, state.with(LIT, lit), 3);
            world.playSound(null, pos, lit ? SoundEvents.BLOCK_BELL_RESONATE : SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 1.0F, lit ? 1.1F : 0.8F);
        }
        return ActionResult.success(world.isClient);
    }

    // 被踩到时弹起实体
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient) {
            // 只对在地面的实体生效，防止重复触发
            if (entity.isOnGround()) {
                Vec3d v = entity.getVelocity();
                // 设置向上速度（可调），并清除坠落距离，避免摔落伤害
                entity.setVelocity(v.x, 0.8D, v.z);
                entity.fallDistance = 0.0F;
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
