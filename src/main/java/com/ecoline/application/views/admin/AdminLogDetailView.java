package com.ecoline.application.views.admin;

import com.ecoline.application.data.entity.LogJournal;
import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.entity.RecipeCardPart;
import com.ecoline.application.data.entity.TechnologicalCard;
import com.ecoline.application.data.service.LogJournalService;
import com.ecoline.application.data.service.OrderService;
import com.ecoline.application.data.service.TechnologicalCardService;
import com.ecoline.application.security.AuthenticatedUser;
import com.ecoline.application.views.MainLayout;
import com.ecoline.application.views.technologist.TechnologistOrderFormView;
import com.ecoline.application.views.technologist.TechnologistRecipeCardMasterDetailView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.RolesAllowed;
import java.time.format.DateTimeFormatter;


@PageTitle("Журнал логов")
@Route(value = "admin-panel-logs", layout = MainLayout.class)
@RolesAllowed({"admin"})
public class AdminLogDetailView extends Div {

    private Grid<LogJournal> grid = new Grid<>(LogJournal.class, false);

    private AuthenticatedUser authenticatedUser;
    private LogJournalService logJournalService;

    public AdminLogDetailView(@Autowired LogJournalService logJournalService) {
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.add(grid);

        add(verticalLayout);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss | dd-MM-yyyy");

        grid.addColumn("id").setAutoWidth(true).setHeader("ID");
        grid.addColumn(logJournal -> formatter.format(logJournal.getDateTime())).setAutoWidth(true).setHeader("Время и дата");
        grid.addColumn("username").setAutoWidth(true).setHeader("Пользователь");
        grid.addColumn("actionType").setAutoWidth(true).setHeader("Действие");
        grid.addColumn("description").setAutoWidth(true).setHeader("Описание");
        grid.sort(grid.getSortOrder());
        grid.setItems(query -> logJournalService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();
    }
}
