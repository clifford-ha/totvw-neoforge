package cliffordha.totvw.datagen;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.item.VWArmorMaterials;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class VWEquipmentAssetProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public VWEquipmentAssetProvider(PackOutput packOutput) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        output.accept(VWArmorMaterials.VERIXIUM_ARMOR_MATERIAL.assetId(), EquipmentClientInfo.builder()
                .addLayers(EquipmentClientInfo.LayerType.HUMANOID,
                                new EquipmentClientInfo.Layer(TOTVW.registerID("verixium0")))
                .addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                        new EquipmentClientInfo.Layer(TOTVW.registerID("verixium1")))

                .addLayers(EquipmentClientInfo.LayerType.WOLF_BODY,
                        new EquipmentClientInfo.Layer(TOTVW.registerID("verixium_wolf_armor")))

                .addLayers(EquipmentClientInfo.LayerType.HORSE_BODY,
                        new EquipmentClientInfo.Layer(TOTVW.registerID("verixium_horse_armor")))

                .replaceTrimPalette(
                        TrimMaterials.DIAMOND,
                        Identifier.withDefaultNamespace("trim/diamond_darker"))
                .build());
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets = new HashMap<>();
        bootstrap((id, asset) -> {
            if (equipmentAssets.putIfAbsent(id, asset) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + id);
            }
        });
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, this.pathProvider::json, equipmentAssets);
    }
    @Override
    public String getName() {
        return "Equipment Assets";
    }
}
