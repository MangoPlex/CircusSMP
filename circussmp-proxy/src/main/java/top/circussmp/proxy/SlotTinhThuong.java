package top.circussmp.proxy;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;

public class SlotTinhThuong {
    private final SMPProxy plugin;

    public SlotTinhThuong(SMPProxy plugin) {
        this.plugin = plugin;
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPreLoginEvent(PreLoginEvent e) {
        if (this.plugin.getConfig().getAllowedOfflineUsernames().contains(e.getUsername())) {
            e.setResult(PreLoginEvent.PreLoginComponentResult.forceOfflineMode());
        }
    }
}
