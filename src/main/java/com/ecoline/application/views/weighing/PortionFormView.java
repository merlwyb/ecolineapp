package com.ecoline.application.views.weighing;

import com.ecoline.application.data.entity.Portion;
import com.ecoline.application.data.service.OrderService;
import com.ecoline.application.data.service.PortionService;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.RolesAllowed;

@PageTitle("Добавление навески")
@Route(value = "weighing-form", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "weigher"})
@Uses(Icon.class)
public class PortionFormView extends Div {

    private Select<String> component = new Select<>();
    private NumberField weight = new NumberField("Вес (г)");
    private Select<Long> orderId = new Select<>();
    private TextField respUsername = new TextField("Ответственный за навеску");

    private Button cancel = new Button("Отменить");
    private Button save = new Button("Добавить");

    private Binder<Portion> binder = new Binder(Portion.class);

    public PortionFormView(PortionService portionService, OrderService orderService) {
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);

        clearForm();

        component.setLabel("Компонент");
        component.setItems("Каучук", "Сыпучая смесь", "Мел", "Тех. углерод");
        orderId.setLabel("Номер заказа");
        weight.setHelperText("Формат ввода: 123,456");
        respUsername.setReadOnly(true);

        component.addValueChangeListener(e -> {
            if (!component.isEmpty()) {
                try {
                    orderId.setItems(orderService.getIdsWithoutComponent(component.getValue()));
                } catch (Exception exception) {
                    Notification.show("Нет заказов");
                }
            }
        });

        orderId.addValueChangeListener(e -> save.setEnabled(true));
        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            try {
                Portion temp = binder.getBean();
                portionService.update(temp);
                orderService.updateComponentId(component.getValue(), orderId.getValue(), temp.getId(), temp.getWeight());
            } catch (Exception exception) {
                Notification.show("При сохранении данных произошла ошибка." + exception.getMessage());
            }
            Notification.show("Данные сохранены.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new Portion());

        orderId.setItems();

        respUsername.setValue(VaadinSession.getCurrent().getAttribute("username").toString());


        save.setEnabled(false);
    }

    private Component createTitle() {
        return new H3("Информация о навеске");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(component, weight, respUsername, orderId);
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
