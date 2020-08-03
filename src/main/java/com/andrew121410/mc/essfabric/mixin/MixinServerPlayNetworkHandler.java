package com.andrew121410.mc.essfabric.mixin;

import com.andrew121410.mc.essfabric.Main;
import net.minecraft.SharedConstants;
import net.minecraft.client.options.ChatVisibility;
import net.minecraft.network.MessageType;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler implements ServerPlayPacketListener {

    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    public abstract void sendPacket(Packet<?> packet);

    @Shadow
    public abstract void disconnect(Text reason);

    @Shadow
    protected abstract void executeCommand(String string);

    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    private int messageCooldown;

    /**
     * @author Andrew121410
     * @reason To convert links to clickable links :wink
     */
    @Overwrite
    public void onGameMessage(ChatMessageC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.player.getServerWorld());
        if (this.player.getClientChatVisibility() == ChatVisibility.HIDDEN) {
            this.sendPacket(new GameMessageS2CPacket((new TranslatableText("chat.cannotSend")).formatted(Formatting.RED), MessageType.SYSTEM, Util.NIL_UUID));
        } else {
            this.player.updateLastActionTime();
            String string = StringUtils.normalizeSpace(packet.getChatMessage());

            for (int i = 0; i < string.length(); ++i) {
                if (!SharedConstants.isValidChar(string.charAt(i))) {
                    this.disconnect(new TranslatableText("multiplayer.disconnect.illegal_characters"));
                    return;
                }
            }

            if (string.startsWith("/")) {
                this.executeCommand(string);
            } else {
                //Andrew start
                String[] args = string.split(" ");
                LiteralText literalText = new LiteralText("");
                //Honestly retard but hey it works. :wink
                for (String arg : args) {
                    if (!literalText.getSiblings().isEmpty()) {
                        literalText.append(new LiteralText(" "));
                    }
                    literalText.append(new LiteralText(arg));
                }

                List<Text> textList = literalText.getSiblings();

                //Find the link
                String rawLink = null;
                int rawLinkID = 0;
                for (int i = 0; i < textList.size(); i++) {
                    Text get = textList.get(i);
                    if (get.asString().startsWith("http")) {
                        rawLink = get.asString();
                        rawLinkID = i;
                    }
                }

                if (rawLink != null) {
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Andrew's Clickable Links [CLICK ME]"));
                    ClickEvent linkEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, rawLink);
                    textList.set(rawLinkID, new LiteralText(rawLink).styled(it -> it.withClickEvent(linkEvent)).styled(it -> it.withHoverEvent(hoverEvent)));
                    LiteralText doneText = new LiteralText("");
                    for (Text text : textList) doneText.append(text);
                    TranslatableText translatableText = new TranslatableText("chat.type.text", new Object[]{this.player.getDisplayName(), doneText});
                    this.server.getPlayerManager().broadcastChatMessage(translatableText, MessageType.CHAT, this.player.getUuid());
                    //Andrew done
                } else {

                    //Name Ping
                    List<ServerPlayerEntity> playerEntities = Main.getInstance().getMinecraftDedicatedServer().getPlayerManager().getPlayerList();
                    for (ServerPlayerEntity playerEntity : playerEntities) {
                        if (string.contains(playerEntity.getName().asString())) {
                            playerEntity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0F, 10.0F);
                        }
                    }

                    Text text = new TranslatableText("chat.type.text", new Object[]{this.player.getDisplayName(), string});
                    this.server.getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, this.player.getUuid());
                }
            }

            this.messageCooldown += 20;
            if (this.messageCooldown > 200 && !this.server.getPlayerManager().isOperator(this.player.getGameProfile())) {
                this.disconnect(new TranslatableText("disconnect.spam"));
            }

        }
    }
}
