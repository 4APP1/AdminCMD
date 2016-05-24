/*
 * This file is part of AdminCMD
 * Copyright (C) 2015 AdminCMD Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/
package com.admincmd.bukkit;

import com.admincmd.api.Registry;
import com.admincmd.api.block.BlockType;
import com.admincmd.api.item.EnchantType;
import com.admincmd.api.item.ItemType;
import com.admincmd.bukkit.block.BukkitBlockType;
import com.admincmd.bukkit.item.BukkitEnchantType;
import com.admincmd.bukkit.item.BukkitItemType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class BukkitRegistry implements Registry {

    private BukkitModule module;

    private final Map<String, ItemType> itemTypes = new LinkedHashMap<>();
    private final Map<String, BlockType> blockTypes = new LinkedHashMap<>();
    private final Map<String, String> itemAliases = new LinkedHashMap<>();
    private final Map<String, EnchantType> enchantTypes = new LinkedHashMap<>();
    private final Map<String, String> enchantAliases = new LinkedHashMap<>();

    public BukkitRegistry(BukkitModule module) {
        this.module = module;

        Material[] materials = Material.values();
        for (Material material : materials) {
            itemTypes.put(material.name(), new BukkitItemType(material));
        }

        for (Material material : materials) {
            if (material.isBlock()) {
                blockTypes.put(material.name(), new BukkitBlockType(material));
            }
        }

        itemAliases.putAll(module.getFileManager().getItemAliases());

        Enchantment[] enchants = Enchantment.values();
        for (Enchantment enchant : enchants) {
            enchantTypes.put(enchant.getName(), new BukkitEnchantType(enchant));
        }

        enchantAliases.putAll(module.getFileManager().getEnchantAliases());
    }

    @Override
    public ItemType getItemType(String id) {
        return itemTypes.get(itemAliases.get(id));
    }

    @Override
    public Collection<ItemType> getItemTypes() {
        return itemTypes.values();
    }

    @Override
    public BlockType getBlockType(String id) {
        return blockTypes.get(itemAliases.get(id));
    }

    @Override
    public Collection<BlockType> getBlockTypes() {
        return blockTypes.values();
    }

    @Override
    public EnchantType getEnchantType(String id) {
        return enchantTypes.get(enchantAliases.get(id));
    }

    @Override
    public Collection<EnchantType> getEnchantTypes() {
        return enchantTypes.values();
    }
}
