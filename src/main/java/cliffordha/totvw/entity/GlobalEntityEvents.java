package cliffordha.totvw.entity;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.effect.*;
import cliffordha.totvw.enchantment.ArcticWindsEffect;
import cliffordha.totvw.enchantment.WitheringEffect;
import cliffordha.totvw.entity.player.AtrocityManager;
import cliffordha.totvw.entity.skills.RevivalByProxy;
import cliffordha.totvw.entity.player.VWPlayerBehaviors;
import cliffordha.totvw.entity.skills.RunestoneEffects;
import cliffordha.totvw.entity.skills.VerdantWindBlessing;
import cliffordha.totvw.entity.wolf.VWWolfBehaviors;
import cliffordha.totvw.item.events.VWItemBlessings;
import cliffordha.totvw.registry.*;
import cliffordha.totvw.registry.attachments.HavocData;
import cliffordha.totvw.registry.attachments.HavocType;
import cliffordha.totvw.registry.attachments.VWAttachment;
import cliffordha.totvw.registry.attachments.WolfFragmentData;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;
import cliffordha.totvw.tag.VWBiomeTags;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.List;

import static cliffordha.totvw.util.ExtUtil.*;

@EventBusSubscriber(modid = TOTVW.MOD_ID)
public class GlobalEntityEvents {
    public static void register() {
        VWPlayerBehaviors.registerModPlayerBehaviors();
        VWWolfBehaviors.registerModWolfBehaviors();
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        if (event.getEffectInstance() instanceof MobEffectInstance instance) {
            removeEffectAttributes(entity, instance.getEffect());
        }
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        LivingEntity entity = event.getEntity();
        if (event.getEffectInstance() instanceof MobEffectInstance instance) {
            removeEffectAttributes(entity, instance.getEffect());
        }
    }

    private static void removeEffectAttributes(LivingEntity entity, Holder<MobEffect> effect) {
        if (effect.equals(VWEffects.AMPLIFIED_MIGHT)) {
            AmplifiedMightEffect.removeModifiers(entity);
        }
        if (effect.equals(VWEffects.BLOODLUST)) {
            BloodlustEffect.removeModifiers(entity);
        }
        if (effect.equals(VWEffects.BLESSING_OF_THE_VERDANT_WIND)) {
            BlessingOfTheVerdantWindEffect.removeModifiers(entity);
        }
        if (effect.equals(VWEffects.PARALYZE)) {
            ParalyzeEffect.removeModifiers(entity);
        }
        if (effect.equals(VWEffects.HAVOC)) {
            HavocEffect.removeHavoc(entity);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!allowDamageEvent(event.getEntity(), event.getSource(), event.getAmount())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAllowDeath(LivingDeathEvent event) {
        if (!RevivalByProxy.allowPlayerDeath(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        afterDeathEvent(event.getEntity(), event.getSource());
        RunestoneEffects.reapplyLinkStatus(event.getEntity(), event.getSource());
    }

    @SubscribeEvent
    public static void onAfterDamage(LivingDamageEvent.Post event) {
        afterDamageEvent(event.getEntity(), event.getSource(), event.getOriginalDamage());
    }


    private static void afterDeathEvent(LivingEntity entity, DamageSource damageSource) {
        if (!(entity.level() instanceof ServerLevel level)) return;
        Entity attacker = damageSource.getEntity();

        boolean checkWardenStat = entity instanceof Warden warden
                && isInBiome(warden, VWBiomeTags.IS_VERDANT_BIOMES);

        Player playerWardenSlayer = attacker instanceof Player getPlayer ? getPlayer : (attacker instanceof Wolf wolf && wolf.getOwner() instanceof Player owner ? owner : null);
        if (checkWardenStat && playerWardenSlayer != null) {
            int acquisition = playerWardenSlayer.getData(PlayerAttachment.GENESIS_RUNESTONE_ACQUISITION_COUNT);
            boolean shouldGrant = VWEnchantments.getBenediction(playerWardenSlayer) && acquisition < 2;
            if (shouldGrant) {
                playerWardenSlayer.getInventory().add(new ItemStack(VWItems.GENESIS_RUNESTONE_PLATE.get()));
                playerWardenSlayer.setData(PlayerAttachment.GENESIS_RUNESTONE_ACQUISITION_COUNT, acquisition + 1);

                sendParticles(ParticleTypes.EXPLOSION_EMITTER, level, playerWardenSlayer.blockPosition(), 12, 3);
                sendToChat(playerWardenSlayer, VWColors.VERDANT_WIND, false, "Verdant Wind's Benediction: You have been granted a Genesis Runestone Plate.");
            }
        }

        if (entity instanceof ElderGuardian guardian && attacker instanceof Player player) {
            List<Wolf> tamedWolf = level.getEntitiesOfClass(Wolf.class, scanArea(guardian, 16), t -> t.getOwner() == player);
            if (!tamedWolf.isEmpty()) {
                addToInventory(player, VWItems.TETHER_RUNESTONE_PLATE.get());
                for (Wolf wolf : tamedWolf) {
                    wolf.setHealth(wolf.getMaxHealth());
                }
                player.setHealth(player.getMaxHealth());

            } else if (level.getRandom().nextFloat() < 0.07f) {
                addToInventory(player, VWItems.TETHER_RUNESTONE_PLATE.get());
            }
        }

        if (attacker instanceof Player player) {
            if (entity.is(EntityTypes.WOLF) || entity.is(EntityTypes.VILLAGER) || entity.is(EntityTypes.WANDERING_TRADER)) {
                AtrocityManager.processAtrocity(player, entity, true);
            }
        }
        if (entity instanceof Player player) {
            if (attacker instanceof Player attackerPlayer) {
                updateNearbyWolvesOnDamage(player, attackerPlayer, true);
            }

            HavocType type = HavocData.getType(player);
            boolean hasType = HavocType.isAnnihilation(player) || HavocType.isVoid(player);
            boolean havocPenalty = player.hasEffect(VWEffects.HAVOC) && hasType;

            if (havocPenalty && attacker instanceof LivingEntity getAttacker) {
                RunestoneEffects.triggerHavocPenalty(getAttacker, type);
            }
        }
    }
    private static void afterDamageEvent(LivingEntity victim, DamageSource damageSource, float damage) {
        if (victim == null) return;
        Entity attacker = damageSource.getEntity();

        if (attacker instanceof Player player) {
            if (victim.is(EntityTypes.WOLF) || victim.is(EntityTypes.VILLAGER) || victim.is(EntityTypes.WANDERING_TRADER)) {
                AtrocityManager.processAtrocity(player, victim, false);
            }
        }
        if (victim instanceof Player player) {
            if (attacker instanceof Player attackerPlayer) {
                updateNearbyWolvesOnDamage(player, attackerPlayer, false);
                PlayerAttachment.removeTrustedPlayer(player, attackerPlayer);
                PlayerAttachment.removeTrustedPlayer(attackerPlayer, player);
            }
        }
        if (victim instanceof Wolf wolf && attacker instanceof Player player) {
            if (wolf.getOwner() == player) return;

            if (wolf.isTame() && wolf.getOwner() != null && wolf.getOwner() != player) {
                WolfAttachment.addPlayerToAggressors(wolf, player);
            }
        }
    }
    private static boolean allowDamageEvent(LivingEntity entity, DamageSource source, float damage) {
        Entity attacker = source.getEntity();

        if (entity.hasEffect(VWEffects.WIND_VEIL)) {
            return windVeilEffect(entity, source, damage);
        }

        boolean attackerIsLive = attacker instanceof LivingEntity;

        if (attackerIsLive) {
            if (ArcticWindsEffect.frostBoundStack(attacker) > 0) return false;
            if (attacker.getData(VWAttachment.LIFT_DURATION) > 0) return false;
        }

        if (entity instanceof Wolf wolf) {
            boolean isPeer = attacker instanceof Wolf peer && wolf.getOwner() == peer.getOwner();
            boolean isAccidentalDMG = wolf.getOwner() instanceof LivingEntity owner && attacker instanceof LivingEntity mob && owner == mob;
            boolean fromFireOrIce = source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_FREEZING);

            if (isPeer || isAccidentalDMG) {
                return false;
            }

            if (wolf.hasData(WolfAttachment.FRAGMENT_DATA)) {
                if (!(attacker instanceof LivingEntity)) return false;
                if (WolfFragmentData.getHits(wolf) <= 0) {
                    WitheringEffect.unloadFragmentedWolf((ServerLevel) wolf.level(), wolf);
                    return false;
                }
                WolfFragmentData.decrementHit(wolf);
                return false;
            }

            if (attacker instanceof Player player) {
                if (wolf.getOwner() instanceof Player owner && PlayerAttachment.isTrusted(owner, player)) {
                    return false;

                } else {
                    return !WolfAttachment.isTrusted(wolf, player);
                }
            }

            if (fromFireOrIce && ArcticWindsEffect.negateFreezingOrFireDMG(wolf, source)) {
                return false;
            }

            boolean allow = VerdantWindBlessing.triggerBenediction(wolf, source, damage);
            if (!allow) retaliate(wolf, source);
            return allow;

        } else if (entity instanceof Player player) {
            boolean disableAccidentalDMG = attacker instanceof Wolf wolf && wolf.getOwner() == player;

            if (attacker instanceof Player other && PlayerAttachment.isTrusted(player, other)) {
                return false;
            }

            if (attacker instanceof Wolf wolf && WolfAttachment.isTrusted(wolf, player)) {
                return false;
            }

            if (source.is(DamageTypeTags.IS_EXPLOSION) && VWItemBlessings.negateExplosionDamage(player)) {
                return false;

            } else if (disableAccidentalDMG) {
                return false;

            } else if (source.is(DamageTypeTags.IS_FIRE) && ArcticWindsEffect.negateFireDamageForOwner(player, (ServerLevel) player.level(), source, damage)) {
                return false;

            } else {
                boolean allow = VerdantWindBlessing.triggerBenediction(player, source, damage);
                if (!allow) retaliate(player, source);
                return allow;
            }

        } else {
            return true;
        }
    }

    private static boolean windVeilEffect(LivingEntity entity, DamageSource damageSource, float damage) {
        if (!(entity.level() instanceof ServerLevel level)) return true;
        if (entity instanceof Monster) return true;

        if (damageSource.getEntity() instanceof LivingEntity attacker
                && !attacker.is(EntityTypes.WOLF)
                && (entity instanceof Player || entity instanceof Wolf)) {
            RandomSource random = level.getRandom();
            DamageSource dmg = level.damageSources().magic();

            if (VWEnchantments.getBenediction(entity) && random.nextFloat() < 0.6f) {
                attacker.hurtServer(level, dmg, Math.max(2, attacker.getHealth() * 0.15f));
                if (attacker instanceof Player player && !player.getMainHandItem().isEmpty()) {
                    player.getCooldowns().addCooldown(player.getMainHandItem(), 60);
                }
                attacker.knockback(Math.max(1, attacker.getMaxHealth() * 0.05f), -attacker.getYHeadRot(), -entity.getYHeadRot(), dmg, 0);
                level.playSound(null, entity.blockPosition(), SoundEvents.THORNS_HIT, SoundSource.PLAYERS);
                for (int i = 0; i < random.nextIntBetweenInclusive(1, 5); i++) {
                    level.sendParticles(ParticleTypes.ENCHANT, entity.getX(), entity.getY(), entity.getZ(), 3, 0, 0, 0, 0);
                }
            }

            if (entity instanceof Player player && player.hasEffect(VWEffects.HAVOC)) {
                RunestoneEffects.getHavocForPlayer(player, attacker, damage);
            }
            retaliate(entity, damageSource);
        }
        return false;
    }
    private static void retaliate(LivingEntity victim, DamageSource source) {
        if (!(victim.level() instanceof ServerLevel level)) return;
        if (!(source.getEntity() instanceof LivingEntity attacker) || attacker.is(EntityTypes.WOLF)) return;

        if (victim instanceof Wolf wolf) {
            wolf.setTarget(attacker);

        } else if (victim instanceof Player player) {
            for (Wolf wolf : level.getEntities(EntityTypes.WOLF, scanArea(player, 16), w -> w.getOwner() == player)) {
                wolf.setTarget(attacker);
            }
        }
    }
    private static void updateNearbyWolvesOnDamage(Player player, Player attacker, boolean isDeath) {
        if (!(player.level() instanceof ServerLevel level)) return;

        if (isDeath) {
            for (ServerLevel serverLevel : level.getServer().getAllLevels()) {
                serverLevel.getAllEntities().forEach(e -> {
                    if (e instanceof Wolf wolf && wolf.getOwner() == player) {
                        WolfAttachment.removeTrustedPlayer(wolf, attacker);
                    }
                });
            }
            return;
        }

        List<Wolf> wolves = level.getEntitiesOfClass(Wolf.class, scanArea(player, 16), wolf -> wolf.getOwner() == player);
        if (wolves.isEmpty()) return;

        for (Wolf wolf : wolves) {
            WolfAttachment.removeTrustedPlayer(wolf, attacker);
        }
    }
}
