package com.iqbalhs.discord.listeners;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.user.UserChangeNicknameEvent;
import org.javacord.api.listener.user.UserChangeNicknameListener;

import java.util.List;

public class MemberChangeNicknameListener implements UserChangeNicknameListener{
    public void onUserChangeNickname(UserChangeNicknameEvent event) {
        List<ServerTextChannel> serverTextChannels = event.getServer().getTextChannelsByNameIgnoreCase("server-log");
        if (!serverTextChannels.isEmpty()) {
            ServerTextChannel serverTextChannel = serverTextChannels.get(0);
            User user = event.getUser();
            MessageBuilder msg = new MessageBuilder();

            msg.append("User " + user.getDiscriminatedName() + " has changed their nickname");
            event.getOldNickname().ifPresent(oldNickname -> {
                msg.append(" from ");
                msg.append(oldNickname, MessageDecoration.BOLD);
            });
            msg.append(" to ");
            event.getNewNickname().ifPresentOrElse(
                    newNickname-> msg.append(newNickname, MessageDecoration.BOLD),
                    ()-> msg.append("their default name")
            );
            msg.send(serverTextChannel);
        }
    }
}
