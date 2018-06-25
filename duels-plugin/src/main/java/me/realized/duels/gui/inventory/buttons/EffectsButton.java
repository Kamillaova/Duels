package me.realized.duels.gui.inventory.buttons;

import java.util.stream.Collectors;
import me.realized.duels.DuelsPlugin;
import me.realized.duels.gui.BaseButton;
import me.realized.duels.util.StringUtil;
import me.realized.duels.util.compat.CompatUtil;
import me.realized.duels.util.inventory.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class EffectsButton extends BaseButton {

    public EffectsButton(final DuelsPlugin plugin, final Player player) {
        super(plugin, ItemBuilder
            .of(Material.POTION, 1, (short) 8237)
            .name(plugin.getLang().getMessage("GUI.inventory-view.buttons.effects.name"))
            .lore(player.getActivePotionEffects().stream()
                .map(effect -> plugin.getLang().getMessage("GUI.inventory-view.buttons.effects.lore-format",
                        "type", StringUtils.capitalize(effect.getType().getName().replace("_", " ").toLowerCase()),
                        "amplifier", StringUtil.toRoman(effect.getAmplifier() + 1),
                        "duration", (effect.getDuration() / 20))).collect(Collectors.toList()))
            .build());
        editMeta(meta -> {
            if (!CompatUtil.isPre1_8()) {
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            }
        });
    }
}