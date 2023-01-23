package xyz.mangostudio.smp.launcher;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;

import mjson.Json;

import net.fabricmc.installer.ServerLauncher;
import net.fabricmc.installer.util.Utils;

public final class LauncherUtil {
    public static Properties hijackFromInstaller() throws Exception {
        Method readPropertiesMethod = ServerLauncher.class.getDeclaredMethod("readProperties");
        readPropertiesMethod.setAccessible(true);

        return (Properties) readPropertiesMethod.invoke(null);
    }

    public static Json readJson(URL url) throws IOException {
        return Json.read(Utils.readString(url));
    }
}
