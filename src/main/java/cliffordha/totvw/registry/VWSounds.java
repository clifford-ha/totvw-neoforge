package cliffordha.totvw.registry;

import cliffordha.totvw.TOTVW;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class VWSounds {
    public static final String ALEX_JAUK = "alex_jauk.";
    public static final String DRAGON_STUDIO = "dragon_studio.";
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, TOTVW.MOD_ID);

    public static final Supplier<SoundEvent> WOLF_HOWL_A = registerSound(ALEX_JAUK + "wolf_howl_a");
    public static final Supplier<SoundEvent> WOLF_HOWL_B1 = registerSound(DRAGON_STUDIO + "wolf_howl_b1");
    public static final Supplier<SoundEvent> WOLF_HOWL_B2 = registerSound(DRAGON_STUDIO + "wolf_howl_b2");
    public static final Supplier<SoundEvent> WOLF_HOWL_B3 = registerSound(DRAGON_STUDIO + "wolf_howl_b3");

    public static final Supplier<SoundEvent> WOLF_SKILL_PARALYZE = registerSound( DRAGON_STUDIO + "wolf_skill_paralyze");

    public static final Supplier<SoundEvent> LODESTONE_WIND_CORE_AMBIENT = registerSound( DRAGON_STUDIO + "lodestone_wind_core_ambient");

    public static final Supplier<SoundEvent> NOTIFY = registerSound(DRAGON_STUDIO + "notify");


    private static Supplier<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name,
                () -> SoundEvent.createVariableRangeEvent(TOTVW.registerID(name)));
    }

    public static List<SoundEvent> getAllSounds() {
        return List.of(WOLF_HOWL_A.get(), WOLF_HOWL_B1.get(), WOLF_HOWL_B2.get(), WOLF_HOWL_B3.get(), WOLF_SKILL_PARALYZE.get(), LODESTONE_WIND_CORE_AMBIENT.get(), NOTIFY.get());
    }

    public static void register() {
        TOTVW.sendClassRegisterLog("Sounds");
    }

    /*

    ID | original file name
    ---------------------------
    || Alex Jauk
    wolf_howl_a = alex-jauk-howling-wolf-268894

    || Dragon Studio
    wolf_howl_b1 = dragon-studio-wolf-howl-2-359870
    wolf_howl_b2 = dragon-studio-wolf-howl-359873
    wolf_howl_b3 = dragon-studio-howling-wolves-515977
    wolf_skill_paralyze = dragon-studio-sci-fi-portal-jump-05-416165
    lodestone_wind_core_ambient = dragon-studio-blizzard-wind-463217
    notify = dragon-studio-notification-bell-sound-1-376885

     */
}
