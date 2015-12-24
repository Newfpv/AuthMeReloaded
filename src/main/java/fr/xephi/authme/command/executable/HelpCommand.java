package fr.xephi.authme.command.executable;

import fr.xephi.authme.command.CommandHandler;
import fr.xephi.authme.command.CommandUtils;
import fr.xephi.authme.command.ExecutableCommand;
import fr.xephi.authme.command.FoundCommandResult;
import fr.xephi.authme.command.FoundResultStatus;
import fr.xephi.authme.command.help.HelpProvider;
import fr.xephi.authme.permission.PermissionsManager;
import fr.xephi.authme.util.Wrapper;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

import static fr.xephi.authme.command.FoundResultStatus.MISSING_BASE_COMMAND;
import static fr.xephi.authme.command.FoundResultStatus.UNKNOWN_LABEL;

public class HelpCommand extends ExecutableCommand {

    // Convention: arguments is not the actual invoked arguments but the command that was invoked,
    // e.g. "/authme help register" would typically be arguments = [register], but here we pass [authme, register]
    @Override
    public void executeCommand(CommandSender sender, List<String> arguments) {
        // TODO #306 ljacqu 20151213: Get command handler from non-static context
        CommandHandler commandHandler = Wrapper.getInstance().getAuthMe().getCommandHandler();
        FoundCommandResult foundCommandResult = commandHandler.mapPartsToCommand(arguments);

        // TODO ljacqu 20151213: This is essentially the same logic as in CommandHandler and we'd like to have the same
        // messages. Maybe we can have another method in CommandHandler where the end command isn't executed upon
        // success.
        FoundResultStatus resultStatus = foundCommandResult.getResultStatus();
        if (MISSING_BASE_COMMAND.equals(resultStatus)) {
            sender.sendMessage(ChatColor.DARK_RED + "Could not get base command");
            return;
        } else if (UNKNOWN_LABEL.equals(resultStatus)) {
            if (foundCommandResult.getCommandDescription() == null) {
                sender.sendMessage(ChatColor.DARK_RED + "Unknown command");
                return;
            } else {
                sender.sendMessage(ChatColor.GOLD + "Assuming " + ChatColor.WHITE
                    + CommandUtils.constructCommandPath(foundCommandResult.getCommandDescription()));
            }
        }

        int mappedCommandLevel = foundCommandResult.getCommandDescription().getLabelCount();
        PermissionsManager permissionsManager = Wrapper.getInstance().getAuthMe().getPermissionsManager();
        List<String> lines = (mappedCommandLevel == 1)
            ? HelpProvider.printHelp(foundCommandResult, HelpProvider.SHOW_CHILDREN)
            : HelpProvider.printHelp(foundCommandResult, sender, permissionsManager, HelpProvider.ALL_OPTIONS);
        for (String line : lines) {
            sender.sendMessage(line);
        }

    }

}
