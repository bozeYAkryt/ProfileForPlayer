package boze_kak_ya_kryt.pofile;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ProfileCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ProfileCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Эту команду могут использовать только игроки.");
            return true;
        }

        Player player = (Player) sender;

        // Проверка корректного количества аргументов
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Используйте: /profile <имя-игрока>");
            return true;
        }

        String targetName = args[0];
        Player targetPlayer = plugin.getServer().getPlayer(targetName);

        // Если целевой игрок существует
        if (targetPlayer != null) {
            // Показать меню профиля для целевого игрока
            showProfileMenu(player, targetPlayer);
        } else {
            // Сообщить игроку, что игрок с таким именем не найден
            player.sendMessage(ChatColor.RED + "Игрок с именем " + targetName + " не найден");
        }

        return true;
    }

    private void showProfileMenu(Player viewer, Player targetPlayer) {
    }
}
