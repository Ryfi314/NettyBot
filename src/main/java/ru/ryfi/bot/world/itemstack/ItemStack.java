/*
 * Ryfi  2022.
 */

package ru.ryfi.bot.world.itemstack;

import lombok.Getter;
import lombok.Setter;

import ru.ryfi.bot.nbt.NBTTagCompound;

@Getter
@Setter
public class ItemStack {

    private final ItemType type;
    private int amount;
    private final NBTTagCompound nbt;
    public String toString() {
        return "ItemStack(id=" + this.getType() + ", amount=" + this.getAmount() + ", nbt=" + this.getNbt() + ")";
    }


    public ItemStack(int id) {
        this(id, 1);
    }

    public ItemStack(int id, int amount) {
        this(id, amount, null);
    }
    public ItemStack(int id, int amount, NBTTagCompound nbt) {
        type = ItemType.getItemById(id);
        this.amount = amount;
        this.nbt = nbt;
    }
}
