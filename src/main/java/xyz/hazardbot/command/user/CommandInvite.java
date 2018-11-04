package xyz.hazardbot.command.user;
import java.awt.Color;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionState;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import xyz.hazardbot.command.ICommand;

public class CommandInvite extends ICommand {
	public CommandInvite() {super("invite", "");}

	@Override
	public void run(MessageAuthor ma, TextChannel tc, String[] args) {
        Permissions permissions = new PermissionsBuilder()
                .setState(PermissionType.ADMINISTRATOR, PermissionState.ALLOWED)
                .build();
        String inviteLink = ma.getApi().createBotInvite(permissions);

        EmbedBuilder eb1 = new EmbedBuilder()
            .setTitle("Bot Invite Link")
            .setDescription(inviteLink)
            .setColor(Color.magenta);
        tc.sendMessage(eb1);
		};
	

	@Override
	public String getHelpDescription() {
		return "";
	}

	@Override
	public int getRequiredAccessLevel() {
		return 0;
	}
}
