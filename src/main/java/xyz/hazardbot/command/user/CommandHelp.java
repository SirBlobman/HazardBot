package xyz.hazardbot.command.user;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import xyz.hazardbot.HazardBot;
import xyz.hazardbot.command.ICommand;
import xyz.hazardbot.constants.Symbols;

public class CommandHelp extends ICommand {
    public CommandHelp() {super("help", "");}
    
    @Override
    public void run(MessageAuthor ma, TextChannel tc, String[] args) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Hazard Bot Help Menu").setDescription("version" + HazardBot.BOT_VERSION)
                .setColor(Color.GREEN)
                .setFooter("Hazard Official Bot " + Symbols.SYMBOL_COPYRIGHT + " " + HazardBot.BOT_YEAR + ".")
                .addField(Symbols.EMOJI_GEAR + "**Main Bot Commands**", HazardBot.BOT_COMMAND_PREFIX + "help" + " | " + HazardBot.BOT_COMMAND_PREFIX + "admin" + " | " + HazardBot.BOT_COMMAND_PREFIX + "ping" + " | " + HazardBot.BOT_COMMAND_PREFIX + "version")
                .addField(Symbols.EMOJI_STRAIGHT_RULER + "**Math Commands**", HazardBot.BOT_COMMAND_PREFIX + "add" + " | " + HazardBot.BOT_COMMAND_PREFIX + "subtract" + " | " + HazardBot.BOT_COMMAND_PREFIX + "multiply" + " | " +  HazardBot.BOT_COMMAND_PREFIX + "divide" + " | " + HazardBot.BOT_COMMAND_PREFIX + "modulo | " + HazardBot.BOT_COMMAND_PREFIX + "bool")
                .addField(Symbols.EMOJI_PLAYING_CARD_BLACK_JOKER + "**Fun Commands**", HazardBot.BOT_COMMAND_PREFIX + "meow")
                .addField(Symbols.EMOJI_MICROPHONE + "**Other Bot Commands**", HazardBot.BOT_COMMAND_PREFIX + "website" + " | " + HazardBot.BOT_COMMAND_PREFIX + "upvote" + " | " + HazardBot.BOT_COMMAND_PREFIX + "support" + " | " + HazardBot.BOT_COMMAND_PREFIX + "suggestion")
                .addField(Symbols.EMOJI_TROPHY + "**Bot Developers**", "HZD Nicky#5934\nSirBlobman#7235", true)
                .addField(Symbols.EMOJI_FULL_BATTERY + "**Current Bot Status**", HazardBot.BOT_STATUS, true)
                .addField(Symbols.EMOJI_QUESTION_MARK + "**Don't Know A Command?**", "If you don't know what a command does, type " + HazardBot.BOT_COMMAND_PREFIX + " and just the command! It will send you the command along with the correct usage.");
        
        ma.asUser().ifPresent(user -> user.sendMessage(embed));
    };
    
    
    @Override
    public String getHelpDescription() {
        return "Get the list of help commands";
    }
    
    @Override
    public int getRequiredAccessLevel() {
        return 0;
    }
    
}