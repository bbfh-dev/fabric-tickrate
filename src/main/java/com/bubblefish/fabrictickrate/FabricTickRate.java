package com.bubblefish.fabrictickrate;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import java.util.concurrent.atomic.AtomicReference;

public class FabricTickRate implements ModInitializer {

    private static AtomicReference<Float> customTickrate = new AtomicReference<>(20.0F);
    @Override
    public void onInitialize() {
         CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(CommandManager.literal("tickrate")
                 .then(CommandManager.literal("get").executes(context -> {
                     assert MinecraftClient.getInstance().player != null;
                     MinecraftClient.getInstance().player.sendMessage(Text.of("Current TPS: " + customTickrate.get()), true);
                     return 1;
                 }))
                 .then(CommandManager.literal("set")
                         .then(CommandManager.argument("value", FloatArgumentType.floatArg()).executes(context -> {
                             customTickrate.set(FloatArgumentType.getFloat(context, "value"));
                                 assert MinecraftClient.getInstance().player != null;
                                 MinecraftClient.getInstance().player.sendMessage(Text.of("TPS Changed to: " + customTickrate.get()), true);
                             return 1;
                         }))
                 )
         ));
    }

    private static float oldTPSMaster = 20.0F;
    public static void updateTPSMaster() {
        if (customTickrate.get() != oldTPSMaster) {
            TPSMaster.change(customTickrate.get());
            oldTPSMaster = customTickrate.get();
        }
    }
}
