package xyz.hazardbot.command.user;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import xyz.hazardbot.HazardBot;
import xyz.hazardbot.command.ICommand;

public class CommandHelp extends ICommand {
    public CommandHelp() {
        super("help", "");
    }
    private String prefix;
    private double year;
    private String status;
    @Override
    public void run(MessageAuthor ma, TextChannel tc, String[] args) {        
    	prefix =  HazardBot.getPrefix();
    	year = HazardBot.getYear();
    	status = HazardBot.getStatus();
    	EmbedBuilder eb1 = new EmbedBuilder().setTitle("Hazard Bot Help Menu").setDescription("version" + HazardBot.getVersion())
        		.setColor(Color.GREEN)
        		.setFooter("Hazard Official Bot © " + year + ".")
        		.addField("⚙**Main Bot Commands**", prefix + "help" + " | " + prefix + "admin" + " | " + prefix + "ping" + " | " + prefix + "version")
        		.addField("📏**Math Commands**", prefix + "add" + " | " + prefix + "subtract" + " | " + prefix + "multiply" + " | " +  prefix + "divide" + " | " + prefix + "modulo | " + prefix + "bool")
        		.addField("🃏**Fun Commands**", prefix + "meow")
        		.addField("🎤**Other Bot Commands**", prefix + "website" + " | " + prefix + "upvote" + " | " + prefix + "support" + " | " + prefix + "suggestion")
        		.addField("🏆**Bot Developers**", "HZD Nicky#5934\nSirBlobman#7235", true)
        		.addField("🔋**Current Bot Status**", status, true)
        		.addField("❓**Don't Know A Command?**", "If you don't know what a command does, type " + prefix + " and just the command! It will send you the command along with the correct usage.");
        		sendPrivateMessage(ma, eb1);
    };
    
    
    private void sendPrivateMessage(MessageAuthor author, EmbedBuilder content) {
		// blob pls help i dont know how to do this e.e ;--------;
	}


	@Override
    public String getHelpDescription() {
        return "Get the list of help commands for the Hazard Bot";
    }
    
    @Override
    public int getRequiredAccessLevel() {
        return 0;
    }
    
}