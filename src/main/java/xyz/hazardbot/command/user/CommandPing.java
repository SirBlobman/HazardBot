package xyz.hazardbot.command.user;

import java.awt.Color;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import xyz.hazardbot.command.ICommand;

public class CommandPing extends ICommand {
	public CommandPing() {super("ping", "");}

	@Override
	public void run(MessageAuthor ma, TextChannel tc, String[] args) {
		Message message = getOriginalDiscordMessage();
		EmbedBuilder eb1 = new EmbedBuilder()
			.setTitle("Please Wait")
			.setDescription("Pinging...")
			.setColor(Color.BLACK);
		CompletableFuture<Message> fsent = tc.sendMessage(eb1);
		fsent.whenComplete((sent, error) -> {
			Instant inst1 = message.getCreationTimestamp();
			Instant inst2 = sent.getCreationTimestamp();
			
			long time1 = inst1.toEpochMilli();
			long time2 = inst2.toEpochMilli();
			long time3 = (time2 - time1);
			EmbedBuilder eb2 = new EmbedBuilder()
				.setTitle("Pong!")
				.setDescription("Took " + time3 + " ms.")
				.setColor(Color.BLUE);
			sent.delete();
			tc.sendMessage(eb2);
		});
	}

	@Override
	public String getHelpDescription() {
		return "Pong!";
	}

	@Override
	public int getRequiredAccessLevel() {
		return 0;
	}
}
