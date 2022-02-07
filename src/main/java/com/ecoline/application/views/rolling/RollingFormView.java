package com.ecoline.application.views.rolling;

import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.service.OrderService;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.RolesAllowed;
import java.util.stream.Collectors;

@PageTitle("Добавление вальцевания")
@Route(value = "rolling-proceed", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "rollerman"})
@Uses(Icon.class)
public class RollingFormView extends Div {

    private Select<Long> orderId = new Select<>();
    private TextField respRolling = new TextField("Ответственный за вальцевание");

    private Button cancel = new Button("Отменить");
    private Button save = new Button("Сохранить");

    private Binder<Order> binder = new Binder(Order.class);

    public RollingFormView(OrderService orderService) {
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        addItemsInSelectOrders(orderService);

        orderId.setLabel("Номер заказа");
        respRolling.setReadOnly(true);

        //binder.bindInstanceFields(this);


        clearForm();

        orderId.addValueChangeListener(e -> {
            if (!orderId.isEmpty()) {
                binder.setBean(orderService.get(orderId.getValue()).get());
                //binder.getBean().setRespUsernameMixing(VaadinSession.getCurrent().getAttribute("username").toString());
                save.setEnabled(true);
            } else {
                Notification.show("Выберите заказ");
            }
        });
        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            try {
                binder.getBean().setRolled(true);
                binder.getBean().setRespUsernameRolling(respRolling.getValue());
                orderService.update(binder.getBean());
                addItemsInSelectOrders(orderService);
                Notification.show("Данные сохранены.");
            } catch (Exception exception) {
                binder.getBean().setRolled(false);
                binder.getBean().setRespUsernameRolling("");
                Notification.show("Ошибка при сохранении данных");
            }

            clearForm();
        });
    }

    private void addItemsInSelectOrders(OrderService orderService) {
        try {
            orderId.setItems(orderService.getAllWhereIsNotRolled().stream().map(Order::getId).collect(Collectors.toList()));
        } catch (Exception exception) {
            Notification.show("Нет готовых к вальцеванию заказов");
        }
    }

    private void clearForm() {
        orderId.clear();

        respRolling.setValue(VaadinSession.getCurrent().getAttribute("username").toString());

        save.setEnabled(false);
    }

    private Component createTitle() {
        return new H3("Информация о вальцевании");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(orderId, respRolling);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
}


