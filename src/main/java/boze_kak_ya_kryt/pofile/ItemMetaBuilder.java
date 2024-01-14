package boze_kak_ya_kryt.pofile;

import org.bukkit.inventory.ItemStack;

public class ItemMetaBuilder {
    private String[] lore;

    public ItemMetaBuilder(ItemStack item) {
    }

    public ItemMetaBuilder setLore(String[] strings) {

        this.lore = strings;

        return this;
    }

}
