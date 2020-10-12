package com.bubblefish.fabrictickrate;

import com.bubblefish.fabrictickrate.mixins.MinecraftClientMixin;
import com.bubblefish.fabrictickrate.mixins.RenderTickCounterMixin;
import net.minecraft.client.MinecraftClient;

public class TPSMaster {
    public static RenderTickCounterMixin rtc = null;

    public static void change(float tps) {
        if (rtc == null) {
            MinecraftClientMixin client = (MinecraftClientMixin) MinecraftClient.getInstance();
            rtc = (RenderTickCounterMixin) client.getRenderTickCounter();
        }
        if (rtc.getTickTime() != 1000f / tps) {
            rtc.setTickTime(1000f / tps);
        }
    }
}
