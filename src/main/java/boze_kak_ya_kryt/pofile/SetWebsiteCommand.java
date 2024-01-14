package boze_kak_ya_kryt.pofile;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class SetWebsiteCommand implements CommandExecutor {

    private final DexLand plugin;

    public SetWebsiteCommand(DexLand plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Эту команду могут использовать только игроки.");
            return true;
        }

        Player player = (Player) sender;

        // Проверка разрешения на выполнение команды
        if (!player.hasPermission("dexland.setwebsite")) {
            player.sendMessage(ChatColor.RED + "У вас нет разрешения на выполнение этой команды.");
            return true;
        }

        // Проверка корректного количества аргументов
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Используйте: /setwebsite <веб-сайт>");
            return true;
        }

        String website = args[0];

        // Проверка валидности веб-сайта
        if (!isValidWebsite(website)) {
            player.sendMessage(ChatColor.RED + "Указанный веб-сайт не является действительным.");
            return true;
        }

        // Установка веб-сайта игрока
        player.setMetadata("website", new FixedMetadataValue(plugin, website));

        // Сообщение игроку, что их веб-сайт установлен
        player.sendMessage(ChatColor.GREEN + "Ваш веб-сайт успешно установлен.");

        return true;
    }

    private boolean isValidWebsite(String website) {
        // Проверка, начинается ли веб-сайт с http:// или https://
        return website.startsWith("http://") || website.startsWith("https://");
    }
}
