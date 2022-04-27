package com.ecoline.application.views.rollerman;

import com.ecoline.application.data.entity.LogJournal;
import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.service.LogJournalService;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@PageTitle("Форма вальцевания")
@Route(value = "rolling-proceed", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "rollerman"})
@Uses(Icon.class)
public class RollingFormView extends Div {

    private Select<String> orderStringId = new Select<>();
    private IntegerField rollingTime = new IntegerField("Время вальцевания(с)");
    //private TextField respRolling = new TextField("Ответственный за вальцевание");

    private Button cancel = new Button("Отменить");
    private Button save = new Button("Сохранить");

    private Binder<Order> binder = new Binder(Order.class);

    @Autowired
    private LogJournalService logJournalService;

    public RollingFormView(OrderService orderService) {
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        addItemsInSelectOrders(orderService);

        orderStringId.setLabel("Номер заказа");
        //respRolling.setReadOnly(true);

        //binder.bindInstanceFields(this);


        clearForm();

        orderStringId.addValueChangeListener(e -> {
            binder.setBean(orderService.getByStringIdentifier(orderStringId.getValue()));
            //binder.getBean().setRespUsernameMixing(VaadinSession.getCurrent().getAttribute("username").toString());
            save.setEnabled(true);

        });
        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            if (!orderStringId.isEmpty() && !rollingTime.isEmpty()) {
                try {
                    binder.getBean().setRolled(true);
                    binder.getBean().setRollingTime(rollingTime.getValue());
                    //binder.getBean().setRespUsernameRolling(respRolling.getValue());
                    orderService.update(binder.getBean());
                    addItemsInSelectOrders(orderService);
                    Notification.show("Данные сохранены.");
                    logJournalService.update(new LogJournal(LocalDateTime.now(), VaadinSession.getCurrent().getAttribute("username").toString(), "Вальцевание", "Пользователь отметил вальцевание заказ №" + binder.getBean().getStringIdentifier()));
                } catch (Exception exception) {
                    binder.getBean().setRolled(false);
                    //binder.getBean().setRespUsernameRolling("");
                    Notification.show("Ошибка при сохранении данных");
                }
            } else {
                Notification.show("Заполните все поля");
            }
            clearForm();
        });
    }

    private void addItemsInSelectOrders(OrderService orderService) {
        try {
            orderStringId.setItems(orderService.getAllWhereIsNotRolled().stream().map(Order::getStringIdentifier).collect(Collectors.toList()));
        } catch (Exception exception) {
            Notification.show("Нет готовых к вальцеванию заказов");
        }
    }

    private void clearForm() {
        orderStringId.clear();
        rollingTime.clear();
        save.setEnabled(false);
    }

    private Component createTitle() {
        return new H3("Информация о вальцевании");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(orderStringId, rollingTime);
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


