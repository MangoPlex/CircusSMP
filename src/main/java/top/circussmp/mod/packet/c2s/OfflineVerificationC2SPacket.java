package top.circussmp.mod.packet.c2s;

import com.mojang.authlib.GameProfile;
import org.jetbrains.annotations.NotNull;

public record OfflineVerificationC2SPacket(@NotNull GameProfile profile, @NotNull String password) {
}
