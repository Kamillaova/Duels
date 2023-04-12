package me.realized.duels.api.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.HashSet;
import java.util.Set;

public final class InventoryMoveListener implements Listener {
	private static final String BYPASS_PERM = "duels.moveitems";

	public static final Set<String> blockedMove = new HashSet<>();

	public static boolean isBypass(Player p) {
		return p.hasPermission(BYPASS_PERM);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) return;
		if (!(e.getWhoClicked() instanceof Player player)) return;

		if ((e.getClickedInventory().getType().equals(InventoryType.CRAFTING)
			|| e.getClickedInventory().getType().equals(InventoryType.PLAYER)
		) && !isBypass(player)
			&& blockedMove.contains(player.getName())
		) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryDrag(InventoryDragEvent e) {
		if (!(e.getWhoClicked() instanceof Player player)) return;

		if ((e.getInventory().getType().equals(InventoryType.CRAFTING)
			|| e.getInventory().getType().equals(InventoryType.PLAYER)
		) && !isBypass(player)
			&& blockedMove.contains(player.getName())
		) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		var player = e.getPlayer();

		if (!isBypass(player) && blockedMove.contains(player.getName())) {
			e.setCancelled(true);
		}
	}
}
