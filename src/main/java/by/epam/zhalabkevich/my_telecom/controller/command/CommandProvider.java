package by.epam.zhalabkevich.my_telecom.controller.command;

import by.epam.zhalabkevich.my_telecom.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.EDIT_PROFILE, new EditProfileCommand());
        commands.put(CommandName.UPDATE_PASSWORD, new UpdateUserPasswordCommand());
        commands.put(CommandName.UPDATE_PROMOTION, new UpdatePromotionCommand());
        commands.put(CommandName.CREATE_PROMOTION, new CreatePromotionCommand());
        commands.put(CommandName.DELETE_PROMOTION, new DeletePromotionCommand());
        commands.put(CommandName.ADD_PROMOTION_TO_TARIFF, new AddPromotionToTariffCommand());
        commands.put(CommandName.SHOW_PROMOTIONS, new ShowPromotionsCommand());
        commands.put(CommandName.SHOW_USER_INFO, new ShowUserInfoCommand());
        commands.put(CommandName.UPDATE_USER_INFO, new UpdateUserInfoCommand());
        commands.put(CommandName.LOGOUT, new LogOutCommand());
        commands.put(CommandName.SHOW_TARIFFS, new ShowTariffsCommand());
        commands.put(CommandName.UPDATE_BALANCE, new UpdateBalanceCommand());
        commands.put(CommandName.GO_TO_PAGE, new GoToPageCommand());
        commands.put(CommandName.SHOW_USERS, new ShowUsersCommand());
        commands.put(CommandName.CHANGE_STATUS, new ChangeUserStatusCommand());
        commands.put(CommandName.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandName.CHANGE_BALANCE, new ChangeUserBalanceCommand());
        commands.put(CommandName.SHOW_USER_TARIFF, new ShowUserTariffsCommand());
        commands.put(CommandName.BLOCK, new BlockUsersWithNegativeBalanceCommand());
        commands.put(CommandName.PAYMENT, new WithdrawPaymentForMonthCommand());
        commands.put(CommandName.STATISTIC, new StatisticCommand());

//        commands.put(CommandName.ADD_NOTE, new AddNoteCommand());
//        commands.put(CommandName.DELETE_NOTE, new DeleteNoteCommand());
//        commands.put(CommandName.ADD_TARIFF, new AddTariffCommand());
//        commands.put(CommandName.EDIT_TARIFF, new EditTariffCommand());
//        commands.put(CommandName.DELETE_TARIFF, new DeleteTariffCommand());


    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Command getCommand(String commandName) {
        CommandName name = CommandName.valueOf(commandName.toUpperCase());
        Command command;
        if (null != name) {
            command = commands.get(name);
        } else {
            command = commands.get(CommandName.NO_SUCH_COMMAND);
        }
        return command;
    }

}
