package com.iqbalhs.discord.commands;

import org.javacord.api.entity.activity.Activity;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

/**
 * grabbed from :
 * https://github.com/Javacord/JavacordExampleBot/blob/master/src/main/java/org/javacord/examplebot/command/UserInfoCommand.java
 */
public class UserInfoCommand implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase("!userInfo")) {
            MessageAuthor author = event.getMessage().getAuthor();
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("User Info")
                    .addField("Display Name", author.getDisplayName(), true)
                    .addField("Name + Discriminator", author.getDiscriminatedName(), true)
                    .addField("User Id", author.getIdAsString(), true)
                    .setAuthor(author);
            // Keep in mind that a message author can either be a webhook or a normal user
            author.asUser().ifPresent(user -> {
                embed.addField("Online Status", user.getStatus().getStatusString(), true);
                // The User#getActivity() method returns an Optional
                embed.addField("Activity", user.getActivity().map(Activity::getName).orElse("none"), true);
            });
            // Keep in mind that messages can also be sent as private messages
            event.getMessage().getServer()
                    .ifPresent(server -> embed.addField("Server Admin", author.isServerAdmin() ? "yes" : "no", true));
            // Send the embed. It logs every exception, besides missing permissions (you are not allowed to send message in the channel)
            event.getChannel().sendMessage(embed)
                    .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
        }
    }
}
