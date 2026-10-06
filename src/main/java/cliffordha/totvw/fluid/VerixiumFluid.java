package cliffordha.totvw.fluid;

import cliffordha.totvw.registry.VWBlocks;
import cliffordha.totvw.registry.VWFluids;
import cliffordha.totvw.registry.VWParticles;
import cliffordha.totvw.tag.VWBiomeTags;
import cliffordha.totvw.util.VWUtil;

import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;

import static cliffordha.totvw.util.VWUtil.addHiddenEffect;
import static cliffordha.totvw.util.VWUtil.isInBiome;

@SuppressWarnings("NullableProblems")
public class VerixiumFluid extends LiquidBlock {
    private static final Direction[] ALL_DIRECTIONS = { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN, Direction.UP};

    public VerixiumFluid(Properties properties) {
        super(VWFluids.FLOWING_VERIXIUM_FLUID.get(), properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double glowX = (double)pos.getX() + random.nextDouble() * 9.0 - 3.0;
        double glowY = (double)pos.getY() + random.nextDouble() * 3.0;
        double glowZ = (double)pos.getZ() + random.nextDouble() * 9.0 - 3.0;

        if (!state.getFluidState().isSource()) {
            if (random.nextInt(64) == 0) {
                level.playLocalSound((double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, SoundEvents.WATER_AMBIENT, SoundSource.AMBIENT, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            level.addParticle(ParticleTypes.UNDERWATER, (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble(), (double)pos.getZ() + random.nextDouble(), 0.0F, 0.0F, 0.0F);
        }
        if (random.nextDouble() <= 0.1) {
            level.addParticle(VWParticles.VERDANT_BIOMES_ENVIRONMENT_AMBIANCE.get(), glowX, glowY, glowZ, 3D, 1D, 3D);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 20);
            convertToDeepslate(level, pos);

            double randomD = level.getRandom().nextDouble();
            float randomF = level.getRandom().nextFloat();
            if (VWUtil.isInBiome(level, pos, BiomeTags.IS_NETHER) && randomF < 0.33f) {
                level.destroyBlock(pos, false);
                level.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + randomD, (double) pos.getY() + randomD, (double) pos.getZ() + randomD, 0.0F, 0.0F, 0.0F);
                level.playLocalSound(pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.2F + randomF * 0.2F, 0.9F + randomF * 0.15F, false);
            }
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.tick(state, level, pos, random);
        if (random.nextFloat() < 0.33f) {
            transformAdjacentBlocks(level, pos);
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    private static void convertToDeepslate(ServerLevel level, BlockPos pos) {
        double xx = pos.getX();
        double yy = (double)pos.getY() + (double)1.0F;
        double zz = pos.getZ();
        for (Direction direction : ALL_DIRECTIONS) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);
            if (neighborState.is(Blocks.WATER)) {
                level.setBlock(neighborPos, Blocks.DEEPSLATE.defaultBlockState(), Block.UPDATE_CLIENTS);
                level.addParticle(ParticleTypes.LARGE_SMOKE, xx, yy, zz, 0.0F, 0.0F, 0.0F);
                level.playSound(null, pos, SoundEvents.BASALT_BREAK, SoundSource.AMBIENT, 0.2F + level.getRandom().nextFloat() * 0.2F, 0.9F + level.getRandom().nextFloat() * 0.15F);
            } else if (neighborState.is(Blocks.LAVA)) {
                level.setBlock(neighborPos, Blocks.DEEPSLATE.defaultBlockState(), Block.UPDATE_CLIENTS);
                level.addParticle(ParticleTypes.LARGE_SMOKE, xx, yy, zz, 0.0F, 0.0F, 0.0F);
                level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.AMBIENT, 0.2F + level.getRandom().nextFloat() * 0.2F, 0.9F + level.getRandom().nextFloat() * 0.15F);
            }
        }
    }
    private static void transformAdjacentBlocks(ServerLevel level, BlockPos pos) {
        double xx = pos.getX();
        double yy = (double)pos.getY() + (double)1.0F;
        double zz = pos.getZ();
        float random = level.getRandom().nextFloat();

        for (Direction direction : ALL_DIRECTIONS) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);
            if (neighborState.is(Blocks.GLASS) && random < 0.67f) {
                level.setBlock(neighborPos, VWBlocks.IRIDESCENT_GLASS.get().defaultBlockState(), Block.UPDATE_CLIENTS);
                level.addParticle(ParticleTypes.GUST_EMITTER_LARGE, xx, yy, zz, 0.0F, 0.0F, 0.0F);
                level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.AMBIENT, 0.2F + level.getRandom().nextFloat() * 0.2F, 0.9F + level.getRandom().nextFloat() * 0.15F);
            } else if (neighborState.is(Blocks.GLASS_PANE) && random < 0.67f) {
                level.setBlock(neighborPos, VWBlocks.IRIDESCENT_GLASS_PANE.get().defaultBlockState(), Block.UPDATE_CLIENTS);
                level.addParticle(ParticleTypes.GUST_EMITTER_LARGE, xx, yy, zz, 0.0F, 0.0F, 0.0F);
                level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.AMBIENT, 0.2F + level.getRandom().nextFloat() * 0.2F, 0.9F + level.getRandom().nextFloat() * 0.15F);
            }
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        effectApplier.apply(InsideBlockEffectType.EXTINGUISH);
        effectApplier.apply(InsideBlockEffectType.CLEAR_FREEZE);

        if (!(level instanceof ServerLevel) || !(entity instanceof LivingEntity livingEntity)) return;

        if (level.getGameTime() % 60 == 0) {
            if (VWUtil.isInBiome(livingEntity, VWBiomeTags.IS_VERDANT_BIOMES)) {
                if (livingEntity.is(EntityTypeTags.UNDEAD) || livingEntity.is(EntityTypeTags.ILLAGER)) return;
                if (livingEntity.hasEffect(MobEffects.WITHER)) {
                    livingEntity.removeEffect(MobEffects.POISON);
                    livingEntity.removeEffect(MobEffects.WITHER);
                }
            }
            whoIsThis(livingEntity);
        }
    }

    public static int setTime(int min,  int sec) {return ((min * (20 * 60)) + (sec * 20));}
    private static void whoIsThis(LivingEntity entity) {
        int bossTime;
        int bossAmp;
        int time;
        int amplifier;
        if (entity.level().getBiome(entity.blockPosition()).is(VWBiomeTags.IS_VERDANT_BIOMES)) {
            bossTime = setTime(1, 30);
            bossAmp = 1;
            time = setTime(0, 12);
            amplifier = 0;
        } else {
            bossTime = setTime(0, 40);
            bossAmp = 0;
            time = setTime(0, 3);
            amplifier = -1;
        }
        switch (entity) {
            case Warden warden -> {
                addHiddenEffect(warden, MobEffects.WITHER, bossTime, bossAmp);
                if ((warden.level().getDifficulty() == Difficulty.HARD)) return;
                evaluateSlowness(warden);
            }
            case Player player -> {
                addHiddenEffect(player, MobEffects.REGENERATION, time, amplifier);
                if (player.isCreative() || player.isSpectator()) return;
                evaluateSlowness(player);
            }
            case Wolf wolf -> {
                addHiddenEffect(wolf, MobEffects.REGENERATION, Mth.ceil(time * 1.4), amplifier);
                if (wolf.isBaby()) return;
                evaluateSlowness(wolf);
            }
            case Sniffer sniffer -> {
                addHiddenEffect(sniffer, MobEffects.REGENERATION, time, amplifier);
            }
            case Villager villager -> {
                addHiddenEffect(villager, MobEffects.REGENERATION, Mth.ceil(time * 1.2), amplifier);
                if (villager.isBaby()) return;
                evaluateSlowness(villager);
            }
            case WanderingTrader wanderingTrader -> {
                addHiddenEffect(wanderingTrader, MobEffects.REGENERATION, Mth.ceil(time * 1.2), amplifier);
                if (wanderingTrader.isBaby()) return;
                evaluateSlowness(wanderingTrader);
            }
            default -> {
                evaluateSlowness(entity);
                if (entity.is(EntityTypeTags.UNDEAD)) return;
                addHiddenEffect(entity, MobEffects.REGENERATION, time, amplifier);
            }
        }
    }
    private static void evaluateSlowness(LivingEntity livingEntity) {
        int defaultDuration;
        int defaultAmp;
        boolean inVerdantBiome = isInBiome(livingEntity, VWBiomeTags.IS_VERDANT_BIOMES);
        boolean inForest = isInBiome(livingEntity, BiomeTags.IS_FOREST);
        boolean inEnd = isInBiome(livingEntity, BiomeTags.IS_END);
        if (inVerdantBiome) {
            defaultDuration = setTime(0, 3);
            defaultAmp = 0;
        } else if (inForest) {
            defaultDuration = setTime(0, 30);
            defaultAmp = 0;
        } else if (inEnd) {
            defaultDuration = setTime(0, 12);
            defaultAmp = 1;
        } else {
            defaultDuration = setTime(0, 45);
            defaultAmp = 2;
        }
        addHiddenEffect(livingEntity, MobEffects.SLOWNESS, defaultDuration, defaultAmp);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return !fluid.isSame(VWFluids.VERIXIUM_FLUID.get()) || !fluid.isSame(VWFluids.FLOWING_VERIXIUM_FLUID.get());
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return 100.0f;
    }

    @Override
    public  Optional<SoundEvent> getPickupSound() { return Optional.of(SoundEvents.BUCKET_FILL); }
}