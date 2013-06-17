package me.novus.locks;

import org.spout.api.command.CommandArguments;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;

public class CommandManager 
{
	private LocksPlugin plugin;
	
	public CommandManager(LocksPlugin plugin)
	{
		this.plugin = plugin;	
	}
	
	@Command(aliases = "lock", desc = "Locks a block.")
	public void mainCommand(CommandSource source, CommandArguments args) throws CommandException
	{
		try
		{
			if (!(source instanceof Player))
			{
				source.sendMessage("You must be logged-in as a player to use Locks!");
				return;
			}
			
			Player player = (Player) source;
			
			if (player.hasPermission("locks.set"))
			{
				if (plugin.isSelecting(player))
				{
					plugin.select(player, false);
					player.sendMessage("Stopped locking blocks.");
				}
				else
				{
					plugin.select(player, true);
					player.sendMessage("Right click a block to lock it!");
				}
			}
			else
			{
				player.sendMessage("You don't have permission to lock blocks. (locks.set)");
			}
		}
		catch (Exception e)
		{
			source.sendMessage("Error! " + e.getMessage());
			e.printStackTrace();
		}
	}
}