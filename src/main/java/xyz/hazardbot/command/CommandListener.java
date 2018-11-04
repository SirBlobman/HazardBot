package xyz.hazardbot.command;

import java.util.Arrays;
import java.util.Map;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import xyz.hazardbot.utility.Util;

public class CommandListener implements MessageCreateListener {
    private static Map<String, ICommand> COMMANDS = Util.newMap();
    
    public static void registerCommands(ICommand... commands) {
        Arrays.stream(commands).forEach(ic -> {
            String cmd = ic.getMainCommand();
            String[] aliases = ic.getAliases();
            Arrays.stream(aliases).forEach(alias -> COMMANDS.put(alias, ic));
            COMMANDS.put(cmd, ic);
        });
    }
    
    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        Message message = e.getMessage();
        TextChannel textChannel = e.getChannel();
        
        String actualMessage = message.getContent();
        String[] split = actualMessage.split(" ");
        if (split.length > 0) {
            String cmd = split[0].toLowerCase();
            String[] args = (split.length > 1) ? Arrays.copyOfRange(split, 1, split.length) : new String[0];
            
            if (cmd.startsWith("??")) {
                String command = cmd.substring(2);
                if (COMMANDS.containsKey(command)) {
                    ICommand ic = COMMANDS.get(command);
                    ic.onCommand(message, textChannel, command, args);
                }
            }
        }
    }
}