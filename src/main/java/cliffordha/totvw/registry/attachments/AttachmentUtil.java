package cliffordha.totvw.registry.attachments;

import cliffordha.totvw.TOTVW;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class AttachmentUtil {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TOTVW.MOD_ID);

    public static final UUID EMPTY_UUID = new UUID(0L, 0L);

    private static final Codec<Pair<String, UUID>> ENTITY_DATA_CODEC = RecordCodecBuilder.create(
            pair -> pair.group(
                    Codec.STRING.fieldOf("name").forGetter(Pair::getA),
                    UUIDUtil.CODEC.fieldOf("uuid").forGetter(Pair::getB)
            ).apply(pair, Pair::new)
    );
    private static final StreamCodec<FriendlyByteBuf, Pair<String, UUID>> ENTITY_DATA_CODEC_STREAM = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, Pair::getA,
            UUIDUtil.STREAM_CODEC, Pair::getB,
            Pair::new
    );
    public static Supplier<AttachmentType<List<NameHolder>>> registerTrustData(String name) {
        return registerTrustData(name, false);
    }
    public static Supplier<AttachmentType<List<NameHolder>>> registerTrustData(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.<List<NameHolder>>builder(_ -> new ArrayList<>());
            builder.serialize(NameHolder.CODEC.listOf().fieldOf("trust_list"))
                    .sync(ByteBufCodecs.fromCodec(NameHolder.CODEC.listOf()));
            if (copyOnDeath) builder.copyOnDeath();
            return builder.build();
        });
    }
    public static Supplier<AttachmentType<List<ArmorData>>> registerArmorList(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.<List<ArmorData>>builder(_ -> new ArrayList<>())
                    .serialize(ArmorData.CODEC.listOf().fieldOf("armor_list"))
                    .sync(ByteBufCodecs.fromCodec(ArmorData.CODEC.listOf()));

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<ArmorData>> registerArmorData(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> ArmorData.NONE)
                    .serialize(ArmorData.CODEC.fieldOf("armor_data"))
                    .sync(ByteBufCodecs.fromCodec(ArmorData.CODEC));

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Runestone>> registerRunestone(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> Runestone.EMPTY)
                    .serialize(Runestone.CODEC.fieldOf("runestone"))
                    .sync(ByteBufCodecs.fromCodec(Runestone.CODEC));

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<TetherData>> registerTetherData(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> TetherData.EMPTY)
                    .serialize(TetherData.CODEC.fieldOf("tether_data"))
                    .sync(ByteBufCodecs.fromCodec(TetherData.CODEC));

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<ReturnPointData>> registerReturnPointData(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> ReturnPointData.DEFAULT)
                    .serialize(ReturnPointData.CODEC.fieldOf("return_point_data"))
                    .sync(ByteBufCodecs.fromCodec(ReturnPointData.CODEC));

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<WolfFragmentData>> registerWitherwolf(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> WolfFragmentData.EMPTY)
                    .serialize(WolfFragmentData.CODEC.fieldOf("wolf_fragment_data"))
                    .sync(ByteBufCodecs.fromCodec(WolfFragmentData.CODEC));

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<HavocData>> registerHavocData(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> HavocData.NONE)
                    .serialize(HavocData.CODEC.fieldOf("havoc_data"))
                    .sync(ByteBufCodecs.fromCodec(HavocData.CODEC));

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Collection<MobEffectInstance>>> registerEffectsCollection(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.<Collection<MobEffectInstance>>builder(() -> new ArrayList<>());
            return builder.build();
        });
    }
    public static Supplier<AttachmentType<List<CompoundTag>>> registerCompoundList(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.<List<CompoundTag>>builder(_ -> new ArrayList<>())
                    .serialize(CompoundTag.CODEC.listOf().fieldOf("list"))
                    .sync(ByteBufCodecs.fromCodec(CompoundTag.CODEC.listOf()));
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<List<String>>> registerStringList(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.<List<String>>builder(_ -> new ArrayList<>())
                    .serialize(Codec.STRING.listOf().fieldOf("list"))
                    .sync(ByteBufCodecs.fromCodec(Codec.STRING.listOf()));

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<List<UUID>>> registerUUIDList(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.<List<UUID>>builder(_ -> new ArrayList<>())
                    .serialize(UUIDUtil.CODEC.listOf().fieldOf("list"))
                    .sync(ByteBufCodecs.fromCodec(UUIDUtil.CODEC.listOf()));
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<UUID>> registerUUID(String name) {
        return registerUUID(name, false);
    }
    public static Supplier<AttachmentType<UUID>> registerUUID(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> EMPTY_UUID)
                    .serialize(UUIDUtil.CODEC.fieldOf("value"))
                    .sync(ByteBufCodecs.fromCodec(UUIDUtil.CODEC));
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<BlockPos>> registerBlockPos(String name) {
        return registerBlockPos(name, false);
    }
    public static Supplier<AttachmentType<BlockPos>> registerBlockPos(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> BlockPos.ZERO)
                    .serialize(BlockPos.CODEC.fieldOf("value"))
                    .sync(BlockPos.STREAM_CODEC);
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Integer>> registerInt(String name) {
        return registerInt(name, false);
    }
    public static Supplier<AttachmentType<Integer>> registerUnsyncedInt(String name) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT.fieldOf("value"));

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Integer>> registerInt(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT.fieldOf("value"))
                    .sync(ByteBufCodecs.INT);
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Boolean>> registerBool(String name) {
        return registerBool(name, false);
    }
    public static Supplier<AttachmentType<Boolean>> registerBool(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL.fieldOf("value"))
                    .sync(ByteBufCodecs.BOOL);
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Float>> registerFloat(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> 0.0f)
                    .serialize(Codec.FLOAT.fieldOf("value"))
                    .sync(ByteBufCodecs.FLOAT);
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
    }


    public static void registerAttachments(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }
}
