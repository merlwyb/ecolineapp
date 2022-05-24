package com.ecoline.application.views.technologist;

import com.ecoline.application.data.entity.*;
import com.ecoline.application.data.service.*;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Редактирование заказа")
@Route(value = "technologist-order-form", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "technologist"})
@Uses(Icon.class)
public class TechnologistOrderFormView extends Div {

    private TextField stringIdentifier = new TextField("Идентификатор заказа");
    private TextField respUsername = new TextField("Отвественный за заказ");
    private ComboBox<Recipe> recipeStringIdentifierSelector = new ComboBox<>("Рецептура");

    private Button cancel = new Button("Очистить");
    private Button save = new Button("Сохранить");

    private Label recipeLabel = new Label("Содержимое рецепта");
    private Grid<RecipePart> grid = new Grid<>(RecipePart.class, false);

    private Binder<Order> binder = new Binder(Order.class);

    @Autowired
    private LogJournalService logJournalService;

    public TechnologistOrderFormView(OrderService orderService, RecipeService recipeService,
                                     RecipeCardService recipeCardService, RecipeCardPartService recipeCardPartService) {
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        add(createRecipeLayout());

        stringIdentifier.setReadOnly(true);

        recipeStringIdentifierSelector.setItems(recipeService.getAll());
        recipeStringIdentifierSelector.setItemLabelGenerator(Recipe::getRecipeStringIdentifier);
        recipeStringIdentifierSelector.setPlaceholder("Выберите рецепт");

        respUsername.setValue(VaadinSession.getCurrent().getAttribute("username").toString());
        respUsername.setReadOnly(true);

        grid.addColumn("componentType").setAutoWidth(true).setHeader("Тип");
        grid.addColumn("componentName").setAutoWidth(true).setHeader("Название");
        grid.addColumn("idealWeight").setAutoWidth(true).setHeader("Эталонный вес (кг)");

        binder.bindInstanceFields(this);

        try {
            binder.setBean(orderService.getByStringIdentifier(VaadinSession.getCurrent().getAttribute("orderToProceed").toString()));
        } catch (Exception exception) {
            Notification.show("Сначала выберите заказ");
        }


        recipeStringIdentifierSelector.addValueChangeListener(e -> {
            grid.setItems(recipeStringIdentifierSelector.getValue().getRecipeParts());
        });

        cancel.addClickListener(e -> clearForm());

        save.addClickListener(e -> {
            if (!stringIdentifier.isEmpty() && !respUsername.isEmpty() && !recipeStringIdentifierSelector.isEmpty()) {
                binder.getBean().setRecipeSelected(true);
                Recipe recipe = recipeStringIdentifierSelector.getValue();
                RecipeCard recipeCard = new RecipeCard();
                recipeCard.setRecipeStringIdentifier(recipe.getRecipeStringIdentifier());
                Set<RecipeCardPart> recipeCardParts = new HashSet<>();
                recipe.getRecipeParts().forEach(recipePart ->
                        recipeCardParts.add(recipeCardPartService.update(new RecipeCardPart(
                                recipePart.getComponentType(), recipePart.getComponentName(),
                                recipePart.getIdealWeight(), recipePart.getDeviation()
                        )))
                );
                recipeCard.getRecipeCardParts().addAll(recipeCardParts);
                binder.getBean().setRecipeCard(recipeCard);
                recipeCardService.update(recipeCard);
                orderService.update(binder.getBean());

                Notification.show("Данные сохранены.");
                logJournalService.update(new LogJournal(LocalDateTime.now(),
                        VaadinSession.getCurrent().getAttribute("username").toString(),
                        "Редактирование заказа", "Пользователь отредактировал заказ №" +
                        binder.getBean().getStringIdentifier()));
                clearForm();
                UI.getCurrent().navigate(TechnologistOrderDetailView.class);
            } else {
                Notification.show("Заполните все поля");
            }
        });
    }

    private void clearForm() {
        VaadinSession.getCurrent().setAttribute("orderToProceed", "");
        binder.removeBean();
//        stringIdentifier.clear();
//        amount.clear();
        save.setEnabled(true);
    }

    private Component createTitle() {
        return new H3("Подготовка заказа");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(stringIdentifier, respUsername, recipeStringIdentifierSelector);
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

    private Component createRecipeLayout() {
        VerticalLayout recipeLayout = new VerticalLayout();
        recipeLayout.addClassNames("master-detail-view", "flex", "flex-col", "h-full");
        recipeLayout.setId("recipes");
        recipeLayout.setSizeFull();
        recipeLayout.add(recipeLabel, grid);
        //recipeLayout.add(new HorizontalLayout(componentRecipe, componentCount));
        recipeLayout.setMargin(true);
        //recipeLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        //recipeLayout.add(new HorizontalLayout(componentRecipeFromDb, componentCountFromDb));

        return recipeLayout;
    }
}


