package com.ecoline.application.views.rollerman;

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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.stream.Collectors;

@PageTitle("Отметка сушки")
@Route(value = "rolling-drying", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "rollerman"})
@Uses(Icon.class)
public class RollingDryingFormView extends Div {

    private Select<Long> orderId = new Select<>();
    //private TextField respDrying = new TextField("Ответственный за сушку");

    private Button cancel = new Button("Отменить");
    private Button save = new Button("Сохранить");

    private Binder<Order> binder = new Binder(Order.class);

    public RollingDryingFormView(OrderService orderService) {
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        addItemsInSelectOrders(orderService);

        orderId.setLabel("Номер заказа");
        //respDrying.setReadOnly(true);

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
                binder.getBean().setDried(true);
                //binder.getBean().setRespUsernameDrying(respDrying.getValue());
                orderService.update(binder.getBean());
                addItemsInSelectOrders(orderService);
                Notification.show("Данные сохранены.");
            } catch (Exception exception) {
                binder.getBean().setDried(false);
                //binder.getBean().setRespUsernameDrying("");
                Notification.show("Ошибка при сохранении данных");
            }

            clearForm();
        });
    }

    private void addItemsInSelectOrders(OrderService orderService) {
        try {
            orderId.setItems(orderService.getAllWhereIsNotDried().stream().map(Order::getId).collect(Collectors.toList()));
        } catch (Exception exception) {
            Notification.show("Нет готовых к сушке заказов");
        }
    }

    private void clearForm() {
        orderId.clear();

        //respDrying.setValue(VaadinSession.getCurrent().getAttribute("username").toString());

        save.setEnabled(false);
    }

    private Component createTitle() {
        return new H3("Информация о сушке");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(orderId);
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


