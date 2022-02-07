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
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@PageTitle("Корректировка навесок")
@Route(value = "correction-form", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "technologist"})
@Uses(Icon.class)
public class CorrectionFormView extends Div {

    private TextField orderId = new TextField("Номер заказа");
    private TextField respUsername = new TextField("Ответственный");

    private NumberField rubberCorrectedWeight = new NumberField("Вес каучука с доб. (г)");
    private NumberField bulkCorrectedWeight = new NumberField("Вес смеси с доб. (г)");
    private NumberField chalkCorrectedWeight = new NumberField("Вес мела с доб. (г)");
    private NumberField carbonCorrectedWeight = new NumberField("Вес тех. углерода с доб. (г)");

    private Select<String> recipeSelect = new Select<>("1 рецепт", "2 рецепт");
    private Image recipeImage = new Image();

    private Button cancel = new Button("Отменить");
    private Button proceed = new Button("Скорректировать");

    private Binder<Order> binder = new Binder(Order.class);

    public CorrectionFormView(OrderService orderService) {
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        add(createRecipeLayout());

        rubberCorrectedWeight.setHelperText("Формат ввода: 123,456");
        bulkCorrectedWeight.setHelperText("Формат ввода: 123,456");
        chalkCorrectedWeight.setHelperText("Формат ввода: 123,456");
        carbonCorrectedWeight.setHelperText("Формат ввода: 123,456");

        binder.forField(orderId)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Введите номер заказа"))
                .bind("id");
        binder.bindInstanceFields(this);

        recipeSelect.setLabel("Рецептура");
        recipeSelect.setItems(new File("src/main/resources/META-INF/resources/recipes").list());
        //750x350 png
        recipeSelect.addValueChangeListener(e -> {
            if (!recipeSelect.isEmpty())
                recipeImage.setSrc("recipes/"+recipeSelect.getValue());
        });

        clearForm(orderService);

        orderId.setReadOnly(true);

        cancel.addClickListener(e -> {
            VaadinSession.getCurrent().setAttribute("orderToCorrect", "");
            clearForm(orderService);
        });

        proceed.addClickListener(e -> {
            try {
                binder.getBean().setCorrected(true);
                orderService.update(binder.getBean());
                VaadinSession.getCurrent().setAttribute("orderToCorrect", "");
            } catch (Exception exception) {
                binder.getBean().setCorrected(false);
                Notification.show("При сохранении данных произошла ошибка." + exception.getMessage());
            }
            Notification.show("Данные сохранены.");
            clearForm(orderService);
        });
    }

    private void clearForm(OrderService orderService) {
        try {
            orderId.setValue(VaadinSession.getCurrent().getAttribute("orderToCorrect").toString());
            binder.setBean(orderService.get(Long.valueOf(VaadinSession.getCurrent().getAttribute("orderToCorrect").toString())).get());
            proceed.setEnabled(true);
        } catch (Exception e) {
            orderId.setValue("");
            proceed.setEnabled(false);
            Notification.show("Вы должны выбрать заказ для корректировки через список заказов");
        }

        respUsername.setValue(VaadinSession.getCurrent().getAttribute("username").toString());
        respUsername.setReadOnly(true);
    }

    private Component createTitle() {
        return new H3("Информация о корректировке");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(orderId, respUsername, rubberCorrectedWeight, bulkCorrectedWeight, chalkCorrectedWeight, carbonCorrectedWeight);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        proceed.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(proceed);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private Component createRecipeLayout() {
        HorizontalLayout recipeLayout = new HorizontalLayout();
        recipeLayout.setSizeFull();
        recipeLayout.add(recipeSelect);
        recipeLayout.add(recipeImage);
        return recipeLayout;
    }
}