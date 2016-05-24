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
package com.admincmd.sponge;

import com.admincmd.api.Registry;
import com.admincmd.api.item.EnchantType;
import com.admincmd.sponge.block.SpongeBlockType;
import com.admincmd.sponge.item.SpongeEnchantType;
import com.admincmd.sponge.item.SpongeItemType;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.ItemType;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpongeRegistry implements Registry {

    private SpongeModule module;

    private GameRegistry registry = Sponge.getRegistry();

    private final Map<String, com.admincmd.api.item.ItemType> itemTypes = new LinkedHashMap<>();
    private final Map<String, com.admincmd.api.block.BlockType> blockTypes = new LinkedHashMap<>();
    private final Map<String, String> itemAliases = new LinkedHashMap<>();
    private final Map<String, com.admincmd.api.item.EnchantType> enchantTypes = new LinkedHashMap<>();
    private final Map<String, String> enchantAliases = new LinkedHashMap<>();

    public SpongeRegistry(SpongeModule module) {
        this.module = module;

        Collection<ItemType> items = registry.getAllOf(ItemType.class);
        for (ItemType item : items) {
            itemTypes.put(item.getName(), new SpongeItemType(item));
        }


        Collection<BlockType> blocks = registry.getAllOf(BlockType.class);
        for (BlockType block : blocks) {
            blockTypes.put(block.getName(), new SpongeBlockType(block));
        }

        itemAliases.putAll(module.getFileManager().getItemAliases());

        Collection<Enchantment> enchants = registry.getAllOf(Enchantment.class);
        for (Enchantment enchant : enchants) {
            enchantTypes.put(enchant.getName(), new SpongeEnchantType(enchant));
        }

        enchantAliases.putAll(module.getFileManager().getEnchantAliases());
    }

    @Override
    public com.admincmd.api.item.ItemType getItemType(String id) {
        return itemTypes.get(itemAliases.get(id));
    }

    @Override
    public Collection<com.admincmd.api.item.ItemType> getItemTypes() {
        return itemTypes.values();
    }

    @Override
    public com.admincmd.api.block.BlockType getBlockType(String id) {
        return blockTypes.get(itemAliases.get(id));
    }

    @Override
    public Collection<com.admincmd.api.block.BlockType> getBlockTypes() {
        return blockTypes.values();
    }

    @Override
    public com.admincmd.api.item.EnchantType getEnchantType(String id) {
        return enchantTypes.get(enchantAliases.get(id));
    }

    @Override
    public Collection<com.admincmd.api.item.EnchantType> getEnchantTypes() {
        return enchantTypes.values();
    }
}
