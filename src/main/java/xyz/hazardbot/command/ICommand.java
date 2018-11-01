package xyz.hazardbot.command;

import java.awt.Color;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import xyz.hazardbot.utility.UserUtil;

public abstract class ICommand {
	private final String command, usage;
	private final String[] aliases;
	private final int minArgs;
	public ICommand(String command, String usage, String... aliases) {
		this.command = command;
		this.usage = usage;
		this.aliases = aliases;
		
		int minimumArguments = 0;
		if(usage != null && !usage.isEmpty()) {
            Pattern pat = Pattern.compile("<.*?>");
            Matcher mat = pat.matcher(usage);
            while(mat.find()) {minimumArguments += 1;}
        }
		this.minArgs = minimumArguments;
	}
	
	public abstract void run(MessageAuthor ma, TextChannel tc, String[] args);
	public abstract String getHelpDescription();
	public abstract int getRequiredAccessLevel();

	public void onCommand(Message message, TextChannel textChannel, String commandUsed, String[] args) {
		this.message = message;
		this.commandUsed = commandUsed;
		
		MessageAuthor author = message.getAuthor();
		
		if(minArgs > args.length) {
			String format = "??" + commandUsed + " " + getUsage();
			EmbedBuilder eb = new EmbedBuilder()
				.setTitle("Error!")
				.setDescription("Invalid command usage")
				.setColor(Color.RED)
				.addField("Valid Usage", format);
			textChannel.sendMessage(eb);
		}
		
		if(getRequiredAccessLevel() > 0) {
			Optional<ServerTextChannel> ostc = textChannel.asServerTextChannel();
			if(ostc.isPresent()) {
				ServerTextChannel stc = ostc.get();
				Server server = stc.getServer();
				long serverID = server.getId();
				long authorID = author.getId();
				int userAccessLevel = UserUtil.getAccessLevel(serverID, authorID);
				int requiredLevel = getRequiredAccessLevel();
				if(userAccessLevel < requiredLevel) {
					EmbedBuilder eb = new EmbedBuilder()
						.setTitle("Error!")
						.setDescription("Access Denied!")
						.setColor(Color.RED);
					textChannel.sendMessage(eb);
				}
			} else return;
		}
		
		try {
			run(author, textChannel, args);
		} catch(Throwable ex) {
			EmbedBuilder eb = new EmbedBuilder()
				.setTitle("Error!")
				.setDescription("An exception occured, tell a bot owner!")
				.setColor(Color.RED)
				.addField("Exception Message", ex.getMessage());
			textChannel.sendMessage(eb);
		}
	}
	
	private String commandUsed;
	private Message message;
	public String getCommandUsed() {return commandUsed;}
	public Message getOriginalDiscordMessage() {return message;}
	
	public String getMainCommand() {return command;}
	public String getUsage() {return usage;}
	public String[] getAliases() {return aliases;}
}