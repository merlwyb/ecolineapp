package com.ecoline.application.views.machinist;

import com.ecoline.application.data.entity.*;
import com.ecoline.application.data.service.*;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSingleSelectionModel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Добавление смешивания")
@Route(value = "mixing-proceed", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "machinist"})
@Uses(Icon.class)
public class MixingFormView extends Div {

    private Select<String> orderStringIdentifierSelect = new Select<>();

    private Button mix = new Button("Смешать");
    private Button mark = new Button("Отметить");

    private Label recipeLabel = new Label("Содержимое рецепта");
    private Grid<ComponentPortion> grid = new Grid<>(ComponentPortion.class, false);

    @Autowired
    private LogJournalService logJournalService;

    public MixingFormView(OrderService orderService, ComponentPortionService componentPortionService) {
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        add(createRecipeLayout());

        orderStringIdentifierSelect.setLabel("Номер заказа");
        orderStringIdentifierSelect.setItems(orderService.getAllWhereIsWeighted().stream().map(Order::getStringIdentifier).collect(Collectors.toList()));

        grid.addColumn("RFID").setAutoWidth(true).setHeader("RFID");
        grid.addColumn("componentType").setAutoWidth(true).setHeader("Тип");
        grid.addColumn("componentName").setAutoWidth(true).setHeader("Название");
        grid.addColumn("weightActual").setAutoWidth(true).setHeader("Вес (кг)");
        //grid.addColumn("deviationActual").setAutoWidth(true).setHeader("Отклонение");
        //grid.addColumn("isOnMixingMachine").setAutoWidth(true).setHeader("Навеска в смесительной камере");

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addComponentColumn(item -> {
            Icon icon;
            if (item.isOnMixingMachine()) {
                icon = VaadinIcon.CHECK.create();
                icon.setColor("green");
            } else {
                icon = VaadinIcon.CLOSE.create();
                icon.setColor("red");
            }
            return icon;
        }).setAutoWidth(true).setHeader("Навеска присутствует");


        orderStringIdentifierSelect.addValueChangeListener(e -> {
            try {
                grid.setItems(componentPortionService.getByOrderId(orderService.getByStringIdentifier(orderStringIdentifierSelect.getValue()).getId()));
                mix.setEnabled(true);
            } catch (Exception ex) {
                grid.setItems(new HashSet<>());
            }

        });

        mix.addClickListener(e -> {
            if (!orderStringIdentifierSelect.isEmpty() && grid.getListDataView().getItems().allMatch(ComponentPortion::isOnMixingMachine)) {
                Order order = orderService.getByStringIdentifier(orderStringIdentifierSelect.getValue());
                order.setMixed(true);
                orderService.update(order);
                Notification.show("Данные сохранены.");
                logJournalService.update(new LogJournal(LocalDateTime.now(),
                        VaadinSession.getCurrent().getAttribute("username").toString(),
                        "Смешивание", "Пользователь отметил смешивание заказ №" + orderStringIdentifierSelect.getValue()));
                mix.setEnabled(false);
                orderStringIdentifierSelect.setItems(orderService.getAllWhereIsWeighted().stream().map(Order::getStringIdentifier).collect(Collectors.toList()));
            } else {
                Notification.show("Отметье все навески заказа");
            }
        });

        mark.addClickListener(e -> {
            try {
                ComponentPortion componentPortion = ((GridSingleSelectionModel<ComponentPortion>) grid.getSelectionModel()).getSelectedItem().get();
                componentPortion.setOnMixingMachine(true);
                componentPortionService.update(componentPortion);
                grid.setItems(componentPortionService.getByOrderId(orderService.getByStringIdentifier(orderStringIdentifierSelect.getValue()).getId()));
            } catch (Exception exception) {
                Notification.show("Выберите навеску");
            }
        });
    }


    private Component createTitle() {
        return new H3("Процесс смешивания");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(orderStringIdentifierSelect);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        mix.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(mix);
        return buttonLayout;
    }

    private Component createRecipeLayout() {
        VerticalLayout recipeLayout = new VerticalLayout();
        recipeLayout.addClassNames("master-detail-view", "flex", "flex-col", "h-full");
        recipeLayout.setSizeFull();
        recipeLayout.add(recipeLabel, grid, mark);
        mark.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        recipeLayout.setMargin(true);

        return recipeLayout;
    }
}


