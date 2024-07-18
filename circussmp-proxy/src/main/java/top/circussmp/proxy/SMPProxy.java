package top.circussmp.proxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "circus-smp", name = "Circus SMP", version = "0.1.0", authors = {"Just Mango"})
public class SMPProxy {
    private final ProxyServer server;
    private final Logger logger;

    private final SMPConfig config;

    @Inject
    public SMPProxy(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        this.config = new SMPConfig(this);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public SMPConfig getConfig() {
        return this.config;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new SlotTinhThuong(this));
    }
}
