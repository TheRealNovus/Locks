package me.novus.locks;

import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.player.Action;
import org.spout.api.event.player.PlayerInteractBlockEvent;
import org.spout.api.geo.discrete.Point;
import org.spout.api.material.BlockMaterial;

public class EventManager implements Listener
{
	private LocksPlugin plugin;
	
	public EventManager(LocksPlugin lp)
	{
		plugin = lp;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractBlockEvent event)
	{
		Player player = event.getEntity();
		BlockMaterial material = event.getInteracted().getMaterial();
		Point position = event.getInteracted().getPosition();
		
		player.sendMessage(material.getDisplayName() + "; " + material.getName());
		
		if (Util.containsIgnoreCase(ConfigManager.BLOCKS, material.getDisplayName()))
		{
			if (event.getAction() == Action.RIGHT_CLICK)
			{
				if (plugin.getDataManager().isLocked(position))
				{
					if (plugin.getDataManager().hasAccess(player, position))
					{
						return;
					}
					else
					{
						player.sendMessage("You do not have access to this lock!");
						event.setCancelled(true);
					}
				}
				else
				{
					if (plugin.isSelecting(player))
					{
						plugin.getDataManager().addLock(player.getName(), position);
						plugin.select(player, false);
						player.sendMessage("This " + material.getDisplayName() + " is now locked!");
					}
				}
			}
		}
	}
}
