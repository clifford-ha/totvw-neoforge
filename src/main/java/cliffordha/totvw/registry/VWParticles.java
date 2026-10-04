package cliffordha.totvw.registry;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.util.VWUtil;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static cliffordha.totvw.TOTVW.sendClassRegisterLog;

public class VWParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, TOTVW.MOD_ID);

    public static final Supplier<SimpleParticleType> BENEDICTION_TRIGGER_PARTICLE = registerParticle("benediction_trigger_particle");
    public static final Supplier<SimpleParticleType> VERDANT_BIOMES_ENVIRONMENT_AMBIANCE = registerParticle("verdant_biomes_environment_ambiance");
    public static final Supplier<SimpleParticleType> VERIXIUM_POWDER_RAIN_PARTICLE = registerParticle("verixium_powder_rain_particle");
    public static final Supplier<SimpleParticleType> MIGHT_PARALYZE_PARTICLE = registerParticle("might_paralyze_particle");

    private static Supplier<SimpleParticleType> registerParticle(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(true));
    }
    public static void showBlessingParticle(LivingEntity entity, int frequency) {
        if (!(entity.level() instanceof ServerLevel level)) return;
        DustParticleOptions dust = new DustParticleOptions(VWColors.VERDANT_WIND, level.getRandom().nextFloat() + 0.5f);
        VWUtil.sendParticles(dust, level, entity.blockPosition(), 4 * (frequency + 1), 1);
    }

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
        sendClassRegisterLog("Particles");
    }
}