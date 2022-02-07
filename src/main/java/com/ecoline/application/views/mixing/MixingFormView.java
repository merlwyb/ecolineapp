package com.ecoline.application.views.mixing;

import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.service.OrderService;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.aspectj.weaver.ast.Not;

import javax.annotation.security.RolesAllowed;
import java.util.stream.Collectors;

@PageTitle("Добавление смешивания")
@Route(value = "mixing-proceed", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "operator"})
@Uses(Icon.class)
public class MixingFormView extends Div {

    private Select<Long> orderId = new Select<>();
    private TextField rubberId = new TextField("ID каучука");
    private TextField bulkId = new TextField("ID смеси");
    private TextField chalkId = new TextField("ID мела");
    private TextField carbonId = new TextField("ID тех. углерода");
    private TextField respMixing = new TextField("Ответственный за смешивание");

    private Button cancel = new Button("Отменить");
    private Button save = new Button("Сохранить");

    private Binder<Order> binder = new Binder(Order.class);

    public MixingFormView(OrderService orderService) {
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        addItemsInSelectOrders(orderService);

        orderId.setLabel("Номер заказа");
        rubberId.setReadOnly(true);
        bulkId.setReadOnly(true);
        chalkId.setReadOnly(true);
        carbonId.setReadOnly(true);
        respMixing.setReadOnly(true);

        binder.forField(rubberId)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Ошибка с ID каучука"))
                .bind("rubberId");
        binder.forField(bulkId)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Ошибка с ID смеси"))
                .bind("bulkId");
        binder.forField(chalkId)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Ошибка с ID мела"))
                .bind("chalkId");
        binder.forField(carbonId)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Ошибка с ID тех. углерода"))
                .bind("carbonId");
        binder.bindInstanceFields(this);


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
                binder.getBean().setMixed(true);
                binder.getBean().setRespUsernameMixing(respMixing.getValue());
                orderService.update(binder.getBean());
                addItemsInSelectOrders(orderService);
                Notification.show("Данные сохранены.");
            } catch (Exception exception) {
                binder.getBean().setMixed(false);
                binder.getBean().setRespUsernameMixing("");
                Notification.show("Ошибка при сохранении данных");
            }

            clearForm();
        });
    }

    private void addItemsInSelectOrders(OrderService orderService) {
        try {
            orderId.setItems(orderService.getAllWhereIsNotMixed().stream().map(Order::getId).collect(Collectors.toList()));
        } catch (Exception exception) {
            Notification.show("Нет готовых к смешиванию заказов");
        }
    }

    private void clearForm() {
        orderId.clear();
        rubberId.clear();
        bulkId.clear();
        chalkId.clear();
        carbonId.clear();

        respMixing.setValue(VaadinSession.getCurrent().getAttribute("username").toString());

        save.setEnabled(false);
    }

    private Component createTitle() {
        return new H3("Информация о заказе");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(orderId, rubberId, bulkId, chalkId, carbonId, respMixing);
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


