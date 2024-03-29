package dev.efnilite.vilib.inventory.item;

import dev.efnilite.vilib.chat.Message;
import dev.efnilite.vilib.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * A class for creating items.
 *
 * @author Efnilite
 */
public class Item extends MenuItem {

    private int amount;
    private int durability;
    private boolean glowing;
    private boolean unbreakable;
    private String name;
    private ItemMeta meta;
    private Material material;
    private final List<String> lore;

    /**
     * Creates a new instance
     *
     * @param   material
     *          The material
     *
     * @param   name
     *          The name of the item
     */
    public Item(Material material, String name) {
        this(material, 1, name);
    }

    /**
     * Creates a new instance
     *
     * @param   material
     *          The material
     *
     * @param   amount
     *          The amount of the item
     *
     * @param   name
     *          The name of the item
     */
    public Item(Material material, int amount, String name) {
        this.amount = amount;
        if (material != null) {
            this.durability = material.getMaxDurability();
        } else {
            material = Material.GRASS_BLOCK;
        }
        this.name = name;
        this.lore = new ArrayList<>();
        this.material = material;
        this.unbreakable = false;
    }

    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }
        if (meta == null) {
            return item;
        }

        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        meta.setDisplayName(Message.parseFormatting(name));
        meta.setLore(lore);

        if (Version.isHigherOrEqual(Version.V1_13)) {
            ((Damageable) meta).setDamage(Math.abs(durability - material.getMaxDurability()));
            meta.setUnbreakable(unbreakable);
        }

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Item clone() {
        Item item = new Item(material, amount, name);

        item.glowing = glowing;
        item.durability = durability;
        item.unbreakable = unbreakable;
        item.meta = meta;
        item.lore.addAll(lore);

        return item;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    /**
     * Set unbreakable
     *
     * @return the instance this class
     */
    public Item unbreakable() {
        this.unbreakable = true;
        return this;
    }

    /**
     * Set glowing
     *
     * @return the instance this class
     */
    public Item glowing() {
        this.glowing = true;
        return this;
    }

    /**
     * Set glowing
     *
     * @return the instance of this class
     */
    public Item glowing(boolean predicate) {
        if (predicate) {
            this.glowing = true;
        }
        return this;
    }

    /**
     * Sets the name
     *
     * @param   name
     *          The name
     *
     * @return  the instance this class
     */
    public Item name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the ItemMeta
     *
     * @param   meta
     *          The meta
     *
     * @return  the instance this class
     */
    public Item meta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Sets the durability of the item
     *
     * @return the instance of this class
     */
    public Item durability(int durability) {
        this.durability = durability;
        return this;
    }


    /**
     * Sets the item amount
     *
     * @param   amount
     *          The item amount
     *
     * @return the instance of this class
     */
    public Item amount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Sets the type
     *
     * @param   material
     *          The type
     *
     * @return  the instance this class
     */
    public Item material(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Sets the lore
     *
     * @param   lore
     *          The lore
     *
     * @return  the instance this class
     */
    public Item lore(@Nullable List<String> lore) {
        if (lore == null || lore.isEmpty()) {
            return this;
        }
        this.lore.clear();
        for (String l : lore) {
            this.lore.add(Message.parseFormatting(l));
        }
        return this;
    }

    /**
     * Sets the lore
     *
     * @param   lore
     *          The lore
     *
     * @return the instance this class
     */
    public Item lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    /**
     * Modifies the lore line by line.
     * Useful for updating items.
     *
     * @param   function
     *          The function. The line of lore is given, and it must return the modified version of that lore line
     *
     * @return the instance of this class
     */
    public Item modifyLore(Function<String, String> function) {
        List<String> newLore = new ArrayList<>();
        for (String l : lore) {
            newLore.add(Message.parseFormatting(function.apply(l)));
        }
        lore.clear();
        lore.addAll(newLore);
        return this;
    }

    /**
     * Modifies the name of an item.
     * Useful for updating items.
     *
     * @param   function
     *          The function. The title is given, and it must return an altered version of this title.
     *
     * @return the instance of this class
     */
    public Item modifyName(Function<String, String> function) {
        name = function.apply(name);
        return this;
    }

    /**
     * Gets the amount
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the lore
     *
     * @return the lore
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Gets the item type
     *
     * @return the type
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Gets the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
