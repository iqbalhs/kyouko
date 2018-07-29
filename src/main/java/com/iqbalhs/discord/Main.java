package com.iqbalhs.discord;

import com.iqbalhs.discord.commands.UserInfoCommand;
import com.iqbalhs.discord.listeners.MemberChangeNicknameListener;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

public class Main {
    public static void main(String[] args) {
        String token = args[0];

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().equalsIgnoreCase("!ping")) {
                event.getChannel().sendMessage("Pong!");
            }
        });
        api.addMessageCreateListener(new UserInfoCommand());
        api.addUserChangeNicknameListener(new MemberChangeNicknameListener());
    }
}
