package cliffordha.totvw.mixin;

import cliffordha.totvw.util.TranslucentRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public abstract class LivingEntityRenderStateMixin implements TranslucentRenderState {
    @Unique
    private boolean totvw_$translucent;

    @Override
    public void totvw_$setTranslucent(boolean translucent) {
        this.totvw_$translucent = translucent;
    }

    @Override
    public boolean totvw_$isTranslucent() {
        return this.totvw_$translucent;
    }
}
