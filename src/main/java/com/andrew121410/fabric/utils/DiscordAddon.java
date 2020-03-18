package com.andrew121410.fabric.utils;

import CCUtils.Sockets.Sockets.Client.SimpleSocketClient;
import com.andrew121410.lackAPI.player.LackPlayer;
import org.json.simple.JSONObject;

public class DiscordAddon extends SimpleSocketClient {

    private boolean isEnabled;
    private boolean isWaitingForAResponse;

    public DiscordAddon(boolean isEnabled) {
        super("localhost", 2020);

        this.isEnabled = isEnabled;
    }

    public void sendJoinMessage(LackPlayer player) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "PlayerJoin");
        jsonObject.put("Player", player.getDisplayName());
        ourJsonPrintOut(jsonObject, false);
    }

    public void sendLeaveMessage(LackPlayer player) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "PlayerQuit");
        jsonObject.put("Player", player.getDisplayName());
        ourJsonPrintOut(jsonObject, false);
    }

    public void sendServerStartMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "ServerStart");
        ourJsonPrintOut(jsonObject, false);
    }

    public void sendServerQuitMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "ServerQuit");
        ourJsonPrintOut(jsonObject, false);
    }

    public void sendPlayerMessage(LackPlayer player, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", "PlayerMessage");
        jsonObject.put("Player", player.getDisplayName());
        jsonObject.put("Message", message);
        ourJsonPrintOut(jsonObject, false);
    }

    public void ourJsonPrintOut(JSONObject jsonObject, boolean waitForAResponse) {
        if (!isEnabled) return;
        jsonObject.put("SV", "1.16");
        isWaitingForAResponse = waitForAResponse;
        jsonPrintOut(jsonObject, "AndrewsVanilla", waitForAResponse);
    }

    @Override
    public void translate(JSONObject jsonObject) {
        if (isWaitingForAResponse) isWaitingForAResponse = false;
    }
}
