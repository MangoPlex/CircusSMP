package xyz.mangostudio.smp.bridge;

import java.util.List;

public interface MinecraftServerBridge {
    List<String> getWhitelistNames();

    void setWhitelistNames(List<String> whitelistNames);
}
