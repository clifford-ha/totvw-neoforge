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
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
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
    public static final Codec<Pair<String, Boolean>> STRING_BOOLEAN_CODEC = RecordCodecBuilder.create(
            pair -> pair.group(
                    Codec.STRING.fieldOf("id").forGetter(Pair::getA),
                    Codec.BOOL.fieldOf("value").forGetter(Pair::getB)
            ).apply(pair, Pair::new)
    );
    public static final StreamCodec<FriendlyByteBuf, Pair<String, Boolean>> STRING_BOOLEAN_CODEC_STREAM = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, Pair::getA,
            ByteBufCodecs.BOOL, Pair::getB,
            Pair::new
    );

    public static Supplier<AttachmentType<HavocType>> registerHavocType(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> HavocType.NONE)
                    .serialize(HavocType.CODEC.fieldOf("type"))
                    .sync(ByteBufCodecs.fromCodec(HavocType.CODEC));
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Runestone>> registerRunestone(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> Runestone.EMPTY)
                    .serialize(Runestone.CODEC.fieldOf("runestone"))
                    .sync(ByteBufCodecs.fromCodec(Runestone.CODEC));
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<List<Pair<String, UUID>>>> registerListPair(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.<List<Pair<String, UUID>>>builder(_ -> new ArrayList<>())
                    .serialize(ENTITY_DATA_CODEC.listOf().fieldOf("list"))
                    .sync(ENTITY_DATA_CODEC_STREAM.apply(ByteBufCodecs.list()));
            if (copyOnDeath) builder.copyOnDeath();

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
    public static Supplier<AttachmentType<List<String>>> registerStringList(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.<List<String>>builder(_ -> new ArrayList<>())
                    .serialize(Codec.STRING.listOf().fieldOf("list"))
                    .sync(ByteBufCodecs.fromCodec(Codec.STRING.listOf()));
            if (copyOnDeath) builder.copyOnDeath();

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
    public static Supplier<AttachmentType<UUID>> registerUUID(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> EMPTY_UUID)
                    .serialize(UUIDUtil.CODEC.fieldOf("value"))
                    .sync(ByteBufCodecs.fromCodec(UUIDUtil.CODEC));
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
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
    public static Supplier<AttachmentType<Integer>> registerInt(String name, boolean copyOnDeath) {
        return ATTACHMENTS.register(name, () -> {
            var builder = AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT.fieldOf("value"))
                    .sync(ByteBufCodecs.INT);
            if (copyOnDeath) builder.copyOnDeath();

            return builder.build();
        });
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
}
