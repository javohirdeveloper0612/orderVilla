package com.example.admin.controller;
import com.example.admin.service.*;
import com.example.admin.util.AdminButtonName;
import com.example.admin.util.AdminStep;
import com.example.admin.util.AdminUsers;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminMainController {

    private final List<AdminUsers> list = new ArrayList<>();
    private final AdminMainService adminMainService;
    private final ActiveOrderAdminService activeOrderAdminService;
    private final NoActiveOrderAdminService noActiveOrderAdminService;

    public AdminMainController(AdminMainService adminMainService,
                               ActiveOrderAdminService activeOrderAdminService,
                               NoActiveOrderAdminService noActiveOrderAdminService) {

        this.adminMainService = adminMainService;
        this.activeOrderAdminService = activeOrderAdminService;
        this.noActiveOrderAdminService = noActiveOrderAdminService;
    }

    public void handle(Update update) {

        if(update.hasMessage()){

            var message = update.getMessage();
            var adminUsers = saveUser(message.getChatId());
            var text = message.getText();

            if(text.equals("/satrt")){

                adminMainService.adminMainMenu(message);
                adminUsers.setAdminStep(AdminStep.ADMINMAIN);

                if(adminUsers.getAdminStep().equals(AdminStep.ADMINMAIN)){

                    switch (message.getText()){

                        case AdminButtonName.activeOrder -> {
                            activeOrderAdminService.activeOrder(message);
                            adminUsers.setAdminStep(AdminStep.ACTIVEORDER);
                        }
                        case AdminButtonName.noActiveOrder -> {
                            noActiveOrderAdminService.noActiveOrder(message);
                            adminUsers.setAdminStep(AdminStep.NOACTIVEORDER);
                        }
                    }
                }
            }
        }



    }

    public AdminUsers saveUser(Long chatId) {
        for (AdminUsers adminUsers : list) {
            if (adminUsers.getChatId().equals(chatId)) {
                return adminUsers;
            }
        }
        var users = new AdminUsers();
        users.setChatId(chatId);
        list.add(users);
        return users;
    }

}
