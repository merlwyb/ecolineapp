package com.ecoline.application.views.admin;

import com.ecoline.application.data.entity.LogJournal;
import com.ecoline.application.data.entity.User;
import com.ecoline.application.data.service.LogJournalService;
import com.ecoline.application.data.service.UserService;
import com.ecoline.application.security.AuthenticatedUser;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.RolesAllowed;
import java.time.format.DateTimeFormatter;


@PageTitle("Список пользователей")
@Route(value = "admin-panel-users", layout = MainLayout.class)
@RolesAllowed({"admin"})
public class AdminUsersDetailView extends Div {

    private Grid<User> grid = new Grid<>(User.class, false);


    public AdminUsersDetailView(@Autowired UserService userService) {
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.add(grid);

        add(verticalLayout);

        grid.addColumn("username").setAutoWidth(true).setHeader("Пользователь");
        grid.addColumn("name").setAutoWidth(true).setHeader("ФИ");
        grid.addColumn(user->user.getRoles().toString()).setAutoWidth(true).setHeader("Роли");
        grid.sort(grid.getSortOrder());
        grid.setItems(query -> userService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();
    }

}
