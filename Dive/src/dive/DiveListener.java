package dive;

import java.util.ArrayList;

import net.canarymod.api.DamageType;
import net.canarymod.api.entity.EntityLiving;
import net.canarymod.api.entity.Player;
import net.canarymod.api.inventory.Item;
import net.canarymod.hook.Hook;
import net.canarymod.hook.command.PlayerCommandHook;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.plugin.PluginListener;

public class DiveListener extends PluginListener{
	
	ArrayList<String> players = new ArrayList<String>();
	
	public Hook onCommand(PlayerCommandHook hook) {
		Player player = hook.getPlayer();
		String[] split = hook.getCommand();
		if (split[0].equalsIgnoreCase("/dive")) {
			if (!player.hasPermission("Dive.dive")) {
				player.notify("§f[§aDive§f]§c You cant use this command!");
				hook.setCancelled();
				return hook;
			}
				if (players.contains(player.getName())) {
					player.notify("§f[§aDive§f]§1 You stopped diving, You can now remove the glass from your head");
					players.remove(player.getName());
					hook.setCancelled();
					return hook;
				}
				if (player.getItemHeld() == null) {
					player.notify("§f[§aDive§f]§c Please hold an glass block in order to dive!");
					hook.setCancelled();
					return hook;
				}
				if (player.getItemHeld().getId() != 20) {
					player.notify("§f[§aDive§f]§c Please hold an glass block in order to dive!");
					hook.setCancelled();
					return hook;
				}
				if (getItemFromSlot(player.getInventory().getContents(), 39) == null) {
				if (player.getItemHeld().getAmount() == 1) {
					player.getInventory().removeItem(player.getItemHeld());
				} else {
					player.getInventory().removeItem(player.getItemHeld());
					player.getInventory().setSlot(20,player.getItemHeld().getAmount()-1,player.getItemHeld().getSlot());
				}
				player.getInventory().setSlot(20, 1, 39);
				players.add(player.getName());
				player.sendMessage("§f[§aDive§f]§1 You can now dive!");
				hook.setCancelled();
				return hook;
		}
				player.notify("§f[§aDive§f]§c Please remove the contents from your helmet slot!");
				hook.setCancelled();
				return hook;
		}
		return hook;
	}
	
	public Hook onDamage(DamageHook hook) {
		if (hook.getDefender().isLiving()) {
			EntityLiving defender = hook.getDefender();
			if (defender.isPlayer()) {
				Player player = hook.getDefender().getPlayer();
				if (players.contains(player.getName())
						&& getItemFromSlot(player.getInventory().getContents(),39) != null && getItemFromSlot(player.getInventory().getContents(),39).getId() == 20) {
					if (hook.getDamageSource().getDamagetype() == DamageType.WATER) {
						hook.setCancelled();
						return hook;
					}
				}
			}
		}
		return hook;
	}

	public Item getItemFromSlot(Item[] ia, int slot) {
		if (ia == null){return null;}
		for (Item item : ia) {
			if (item.getSlot() == slot) {
				return item;
			}
		}
		return null;
	}
}
