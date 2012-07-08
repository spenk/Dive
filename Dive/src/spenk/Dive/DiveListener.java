/**
 * @name Dive
 * @version 1.0[r]
 * @author spenk
 */

package spenk.Dive;

import java.util.ArrayList;

import net.canarymod.api.DamageType;
import net.canarymod.api.entity.EntityLiving;
import net.canarymod.api.entity.Player;
import net.canarymod.hook.Hook;
import net.canarymod.hook.command.PlayerCommandHook;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.plugin.PluginListener;

public class DiveListener extends PluginListener{
	
	ArrayList<String> players = new ArrayList<String>();

	/**
	 *@param hook
	 *@return PlayerCommandHook 
	 *
	 *adds the player to arraylist <players> and puts the item on playes head slot
	 */
	
	public Hook onCommand(PlayerCommandHook hook) {
		Player player = hook.getPlayer();
		String[] split = hook.getCommand();
		if (split[0].equalsIgnoreCase("/dive")) {
			if (!player.hasPermission("Dive.dive")) {
				player.notify("§f[§aDive§f]§c You cant use this command!");
				hook.setCanceled();
				return hook;
			}
				if (players.contains(player.getName())) {
					player.notify("§f[§aDive§f]§1 You stopped diving, You can now remove the glass from your head");
					players.remove(player.getName());
					hook.setCanceled();
					return hook;
				}
				if (player.getItemHeld() == null) {
					player.notify("§f[§aDive§f]§c Please hold an glass block in order to dive!");
					hook.setCanceled();
					return hook;
				}
				if (player.getItemHeld().getId() != 20) {
					player.notify("§f[§aDive§f]§c Please hold an glass block in order to dive!");
					hook.setCanceled();
					return hook;
				}
				if (player.getInventory().getSlot(39) == null) {
				if (player.getItemHeld().getAmount() == 1) {
					player.getInventory().removeItem(player.getItemHeld());
				} else {
					player.getInventory().removeItem(player.getItemHeld());
					player.getInventory().setSlot(20,player.getItemHeld().getAmount()-1,player.getItemHeld().getSlot());
				}
				player.getInventory().setSlot(20, 1, 39);
				players.add(player.getName());
				player.sendMessage("§f[§aDive§f]§1 You can now dive!");
				hook.setCanceled();
				return hook;
		}
				player.notify("§f[§aDive§f]§c Please remove the contents from your helmet slot!");
				hook.setCanceled();
				return hook;
		}
		return hook;
	}
	
	/**
	 * @param hook
	 * @return DamageHook
	 * 
	 * prevents damage
	 */
	
	public Hook onDamage(DamageHook hook) {
		if (hook.getDefender().isLiving()) {
			EntityLiving defender = hook.getDefender();
			if (defender.isPlayer()) {
				Player player = hook.getDefender().getPlayer();
				if (players.contains(player.getName())&& player.getInventory().getSlot(39) != null && player.getInventory().getSlot(39).getId() == 20) {
					if (hook.getDamageSource().getDamagetype() == DamageType.WATER) {
						hook.setCanceled();
						return hook;
					}
				}
			}
		}
		return hook;
	}
}

