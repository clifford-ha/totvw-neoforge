package cliffordha.totvw.datagen;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.registry.VWSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class VWSoundsProvider extends SoundDefinitionsProvider {
    public VWSoundsProvider(PackOutput output) {
        super(output, TOTVW.MOD_ID);
    }
    @Override
    public void registerSounds() {
        for (SoundEvent event : VWSounds.getAllSounds()) {
            add(event, definition().subtitle(event.toString()).with(sound(event.location())));
        }
    }

    @Override
    public String getName() {
        return "TOTVW Sounds";
    }
}
