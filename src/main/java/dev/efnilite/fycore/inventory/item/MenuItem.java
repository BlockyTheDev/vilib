package dev.efnilite.fycore.inventory.item;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Super class for every type of item which will be displayed in the menu
 *
 * @author Efnilite
 */
public abstract class MenuItem {

    protected Map<ClickType, BiConsumer<ItemStack, InventoryClickEvent>> clickFunctions = new HashMap<>();

    /**
     * Set the function on click
     *
     * @param   consumer
     *          Useful values which are gathered in the click e vent
     *
     * @return the instance of this class
     */
    public MenuItem click(BiConsumer<ItemStack, InventoryClickEvent> consumer,  ClickType... clickType) {
        if (clickType.length == 0) {
            for (ClickType type : ClickType.values()) {
                clickFunctions.put(type, consumer);
            }
        } else {
            for (ClickType type : clickType) {
                clickFunctions.put(type, consumer);
            }
        }
        return this;
    }

    public void handleClick(ItemStack item, InventoryClickEvent watcher, ClickType clickType) {
        BiConsumer<ItemStack, InventoryClickEvent> consumer = clickFunctions.get(clickType);
        if (consumer == null)  {
            return;
        }
        consumer.accept(item, watcher);
    }

    /**
     * Finishes everything and gives the ItemStack result.
     *
     * @return the result
     */
    public abstract ItemStack build();

    /**
     * Whether this item can be moved by the player
     *
     * @return true -> it can, false -> it cant
     */
    public abstract boolean isMovable();

}