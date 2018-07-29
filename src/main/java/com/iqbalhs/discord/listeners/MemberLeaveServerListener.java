package com.iqbalhs.discord.listeners;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

public class MemberLeaveServerListener implements ServerMemberLeaveListener {
    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent event) {
        event.getServer().getTextChannelById("473047486253105204").ifPresent(serverTextChannel -> {
            User user = event.getUser();
            MessageBuilder msg = new MessageBuilder();
            msg.append("User " + user.getDiscriminatedName() + " has left the channel :(");
            msg.send(serverTextChannel);
        });
    }
}
