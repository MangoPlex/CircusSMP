package xyz.mangostudio.smp.launcher;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import mjson.Json;

import net.fabricmc.installer.util.Utils;

public class ModUpdater {
    private final Path modsDir;
    private final String minecraftVersion;
    private final List<ModrinthInfo> modrinthInfos;

    public ModUpdater(Path modsDir, String minecraftVersion, List<ModrinthInfo> modrinthInfos) {
        this.modsDir = modsDir;
        this.minecraftVersion = minecraftVersion;
        this.modrinthInfos = modrinthInfos;
    }

    public ModUpdater(Path modsDir, String minecraftVersion, String... modrinthInfos) {
        this(modsDir, minecraftVersion, Arrays.stream(modrinthInfos).map(string -> {
            String[] split = string.split(":");

            if (split.length != 2) {
                throw new IllegalArgumentException("Invalid modrinth info: " + string);
            }

            return new ModrinthInfo(split[0], split[1]);
        }).toList());
    }

    public void update() {
        for (ModrinthInfo modrinthInfo : modrinthInfos) {
            updateMod(modrinthInfo);
        }
    }

    private void updateMod(ModrinthInfo modInfo) {
        try {
            Json versionResponse;

            if (modInfo.useLatestVersion()) {
                Json getProjectVersionsResponse = LauncherUtil.readJson(new URL("https://api.modrinth.com/v2/project/" + modInfo.id + "/version?loaders=%5B%22fabric%22%5D&game_versions=%5B%22" + minecraftVersion + "%22%5D"));
                versionResponse = getProjectVersionsResponse.asJsonList().get(0);
            } else {
                versionResponse = LauncherUtil.readJson(new URL("https://api.modrinth.com/v2/version/" + modInfo.version));
            }

            Json files = versionResponse.at("files").asJsonList().get(0);
            String fileName = files.at("filename").asString();

            if (checkHash(modsDir.resolve(fileName), files.at("hashes").at("sha1").asString())) {
                System.out.println("Skipping " + modInfo.id + " as it is already up to date");
                return;
            }

            System.out.println("Downloading " + modInfo.id);
            String downloadURL = files.at("url").asString();
            Utils.downloadFile(new URL(downloadURL), modsDir.resolve(fileName));
            System.out.println("Downloaded " + modInfo.id);
        } catch (Exception e) {
            System.out.println("Failed to update " + modInfo.id);
            e.printStackTrace();
        }
    }

    private boolean checkHash(Path pathToCheck, String sha1) throws IOException {
        return Files.exists(pathToCheck) && Utils.sha1String(pathToCheck).equalsIgnoreCase(sha1);
    }

    public record ModrinthInfo(String id, String version) {
        public boolean useLatestVersion() {
            return Objects.equals(version, "latest");
        }
    }
}
