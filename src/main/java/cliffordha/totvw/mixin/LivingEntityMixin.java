package cliffordha.totvw.mixin;

import cliffordha.totvw.entity.VWGlobalEntityBehaviors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract boolean isInvulnerableTo(ServerLevel level, DamageSource source);

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void allowDeath(DamageSource source, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (VWGlobalEntityBehaviors.revivePlayerIfPossible(livingEntity, source)) {
            ci.cancel();
        }
        VWGlobalEntityBehaviors.afterDeathEvent(livingEntity, source);
    }

    @Inject(method = "hurtServer", at = @At("RETURN"), cancellable = true)
    private void shouldHurt(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!this.isInvulnerableTo(level, source)) {
            cir.setReturnValue(VWGlobalEntityBehaviors.allowDamageEvent(entity, source, damage));
            cir.cancel();
        }
    }

    @Inject(method = "actuallyHurt", at = @At("HEAD"))
    private void afterDamage(ServerLevel level, DamageSource source, float dmg, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!this.isInvulnerableTo(level, source)) {
            VWGlobalEntityBehaviors.afterDamageEvent(entity, source, dmg);
        }
    }
}
