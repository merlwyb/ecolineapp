package com.ecoline.application.views.correction;

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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.RolesAllowed;

@PageTitle("Добавление заказа")
@Route(value = "correction-order-add", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "technologist"})
@Uses(Icon.class)
public class CorrectionOrderFormView extends Div {

    private TextField orderId = new TextField("Номер заказа");
    private TextField respUsername = new TextField("Ответственный за заказ");

    private Button cancel = new Button("Очистить");
    private Button save = new Button("Добавить");

    private Binder<Order> binder = new Binder(Order.class);

    public CorrectionOrderFormView(OrderService orderService) {
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        //orderId.setHelperText("Формат ввода: 1234567890");

        binder.forField(orderId)
                .withNullRepresentation("")
                .withValidator(new RegexpValidator("Допустимы только числа!", "[ 0-9]+"))
                .withConverter(new StringToLongConverter("Введите номер заказа"))
                .bind("id");
        binder.bindInstanceFields(this);


        clearForm();

        orderId.addValueChangeListener(e -> {

            save.setEnabled(!orderId.isInvalid());
        });
        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            if (!orderId.isInvalid()) {
                orderService.update(binder.getBean());
                Notification.show("Данные сохранены.");
                clearForm();
            }
        });
    }

    private void clearForm() {
        binder.setBean(new Order());

        respUsername.setValue(VaadinSession.getCurrent().getAttribute("username").toString());
        respUsername.setReadOnly(true);

        save.setEnabled(false);
    }

    private Component createTitle() {
        return new H3("Добавление заказа");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(orderId, respUsername);
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


