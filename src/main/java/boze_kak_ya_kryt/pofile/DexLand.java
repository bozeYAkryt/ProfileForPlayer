package boze_kak_ya_kryt.pofile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class DexLand extends JavaPlugin implements Listener, CommandExecutor {

    // Метод, который выполняется при активации плагина
    @Override
    public void onEnable() {
        // Регистрирует плагин в качестве слушателя событий
        getServer().getPluginManager().registerEvents(this, this);
        // Регистрирует команды "/setwebsite" и "/profile"
        getCommand("setwebsite").setExecutor(new SetWebsiteCommand(this));
        getCommand("profile").setExecutor(this);
    }

    // Метод, который выполняется при деактивации плагина
    @Override
    public void onDisable() {
        // Отменяет все запланированные задачи плагина
        Bukkit.getScheduler().cancelTasks(this);
    }

    // Метод, который обрабатывает команды, введенные игроками
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Проверяет, является ли отправитель команды игроком
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Команда доступна только игрокам.");
            return true;
        }
        // Обрабатывает команду "/profile"
        if (command.getName().equalsIgnoreCase("profile")) {
            // Проверяет, что в команде указан один аргумент (имя игрока)
            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Использование: /profile <имя-игрока>");
                return true;
            }

            Player viewer = (Player) sender;
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            // Проверяет, существует ли игрок с указанным именем
            if (targetPlayer == null) {
                sender.sendMessage(ChatColor.RED + "Игрок с именем " + args[0] + " не найден.");
                return true;
            }
            // Отображает меню профиля
            showProfileMenu(viewer, targetPlayer);
            return true;
        }

        return false;
    }

    // Обработчик события щелчка по инвентарю
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Предотвращает взаимодействие с инвентарем профиля игрока
        if (event.getView().getTitle().equals("Профиль игрока")) {
            event.setCancelled(true);
        }
    }

    private void showProfileMenu(Player viewer, Player targetPlayer) {
        // Создает инвентарь размером 9x1 с названием "Профиль игрока"
        Inventory inventory = Bukkit.createInventory(viewer, 9, "Профиль игрока");
        // Добавляет предметы в инвентарь с информацией о игроке
        inventory.setItem(0, createItem(ChatColor.GOLD + "Имя:", targetPlayer.getName()));
        inventory.setItem(1, createItem(ChatColor.GOLD + "Никнейм:", targetPlayer.getDisplayName()));
        inventory.setItem(2, createItem(ChatColor.GOLD + "Веб-сайт:", targetPlayer.hasMetadata("website") && targetPlayer.getMetadata("website").get(0) != null ? (String) targetPlayer.getMetadata("website").get(0).value() : "Профиль не указан"));

        viewer.openInventory(inventory);
    }

    // Этот метод создает предмет с именем и описанием
    private ItemStack createItem(String name, String value) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(value));
        item.setItemMeta(meta);
        return item;
    }
}