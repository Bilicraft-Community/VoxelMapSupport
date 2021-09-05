package com.bilicraft.voxelmapsupport;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.ByteBuffer;

public final class VoxelMapSupport extends JavaPlugin implements Listener, PluginMessageListener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "worldinfo:world_id", this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "worldinfo:world_id");
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (channel.contentEquals("worldinfo:world_id")) {
            String worldName = getConfig().getString("name")+"_"+player.getWorld().getName();
            byte[] nameBytes = worldName.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(2+nameBytes.length).put((byte)0).put((byte) nameBytes.length).put(nameBytes);
            player.sendPluginMessage(this, "worldinfo:world_id", buffer.array());
        }
    }
}
