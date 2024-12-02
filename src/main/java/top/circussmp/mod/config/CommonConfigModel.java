package top.circussmp.mod.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

import java.util.ArrayList;
import java.util.List;

@Modmenu(modId = "circussmp")
@Config(name = "circussmp-common-config", wrapperName = "CommonConfig")
public class CommonConfigModel {
    public String offlinePassword = "password";
    public List<String> offlinePlayers = new ArrayList<>();
}
