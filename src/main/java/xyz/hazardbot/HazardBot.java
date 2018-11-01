package xyz.hazardbot;

import java.awt.Color;
import java.io.File;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserStatus;

import xyz.hazardbot.command.CommandListener;
import xyz.hazardbot.command.user.CommandPing;
import xyz.hazardbot.constants.SpecialUserID;
import xyz.hazardbot.utility.FileUtil;
import xyz.hazardbot.utility.Util;
import xyz.hazardbot.utility.yaml.file.YamlConfiguration;

public class HazardBot {
	public static DiscordApi API;
	public static void main(String[] args) {
		YamlConfiguration config = loadMainConfig();
		String token = config.getString("discord api.token");
		if(!token.isEmpty()) {
			DiscordApiBuilder dab = new DiscordApiBuilder().setToken(token);
			CompletableFuture<DiscordApi> flogin = dab.login();
			flogin.whenComplete((dapi, ex) -> {
				if(ex != null || dapi == null) {
					String error = "Invalid token set in 'hazard bot.yml'";
					Util.print(error);
					ex.printStackTrace();
					return;
				} else {
					API = dapi;
					PermissionsBuilder pb = new PermissionsBuilder();
					pb.setAllowed(PermissionType.ADMINISTRATOR);
					Permissions perms = pb.build();
					String invite = dapi.createBotInvite(perms);
					Util.print("Invite me to your server! " + invite);
					
					sendBotStatus();
					registerCommands();
				}
			});
		} else {
			String error = "Invalid token set in 'hazard bot.yml'";
			Util.print(error);
			return;
		}
	}

	public static YamlConfiguration loadMainConfig() {
		try {
			YamlConfiguration config = new YamlConfiguration();
			File file = new File("hazard bot.yml");
			if(!file.exists()) FileUtil.copyFromJar("hazard bot.yml", file);
			
			config.load(file);
			return config;
		} catch(Throwable ex) {
			String error = "Failed to load 'hazard bot.yml'";
			Util.print(error);
			ex.printStackTrace();
			return new YamlConfiguration();
		}
	}
	
	private static void sendBotStatus() {
		API.updateStatus(UserStatus.ONLINE);
		ActivityType activityType = getActivityType();
		String activitiyMessage = getActivityMessage();
		API.updateActivity(activityType, activitiyMessage);
		Util.print("Hazard Bot Online!");
		
		String botStatus = getBotStatus();
		for(String userID : SpecialUserID.getAllBotCreators()) {
			CompletableFuture<User> fuser = API.getUserById(userID);
			fuser.whenComplete((user, ex) -> {
				if(user != null) {
					String userName = user.getName();
					EmbedBuilder eb = new EmbedBuilder()
						.setTitle("Welcome " + userName + "!")
						.setDescription("The bot has successfully booted")
						.setColor(Color.GREEN)
						.addField("Bot Status", botStatus);
					user.sendMessage(eb);
				}
			});
		}
	}

	private static String getBotStatus() {
		YamlConfiguration config = loadMainConfig();
		String status = config.getString("bot status", "Not set!");
		int max = Math.min(1025, status.length());
		String substatus = status.substring(0, max);
		return substatus;
	}

	private static String getActivityMessage() {
		YamlConfiguration config = loadMainConfig();
		String activity = config.getString("activity.message", "for ??help");
		int max = Math.min(1025, activity.length());
		String subActivity = activity.substring(0, max);
		return subActivity;
	}

	private static ActivityType getActivityType() {
		YamlConfiguration config = loadMainConfig();
		String activityType = config.getString("activity.type", ActivityType.WATCHING.name());
		try {
			ActivityType at = ActivityType.valueOf(activityType);
			return at;
		} catch(Throwable ex) {
			return ActivityType.WATCHING;
		}
	}

	private static void registerCommands() {
		API.addMessageCreateListener(new CommandListener());
		
		CommandListener.registerCommands(new CommandPing());
	}
}