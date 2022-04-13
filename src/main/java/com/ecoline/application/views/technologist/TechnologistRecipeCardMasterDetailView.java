package com.ecoline.application.views.technologist;

import com.ecoline.application.data.entity.*;
import com.ecoline.application.data.service.*;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//todo мастер-детайл для рецептурной карты заказа
@PageTitle("Редактирование рецептурной карты заказа")
@Route(value = "technologist-recipeCardMasterDetail/:recipeCardPartID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
@RolesAllowed({"admin", "user", "technologist"})
public class TechnologistRecipeCardMasterDetailView extends Div implements BeforeEnterObserver {

    private final String RECIPECARDPART_ID = "recipeCardPartID";
    private final String RECIPECARDPART_EDIT_ROUTE_TEMPLATE = "technologist-recipeCardMasterDetail/%s/edit";
    private Grid<RecipeCardPart> grid = new Grid<>(RecipeCardPart.class, false);
    private Label recipeLabel = new Label("Номер заказа");
    private Select<String> orderStringIdentifierSelect = new Select<>();
    private Label recipeNewStringLabel = new Label("Идентификатор нового рецепта");
    private TextField recipeNewString = new TextField();
    private Button recipeNewStringButton = new Button("Добавить новый рецепт");

    private Select<String> componentType;
    private TextField componentName;
    private NumberField idealWeight;
    private NumberField deviation;
    private Label helperLabel;

    private Button cancel = new Button("Отменить");
    private Button save = new Button("Сохранить");

    private BeanValidationBinder<RecipeCardPart> binder;

    private RecipeCardPart recipeCardPart;

    private OrderService orderService;

    private RecipeCardService recipeCardService;
    private RecipeCardPartService recipeCardPartService;
    private RecipeService recipeService;
    private RecipePartService recipePartService;

    public TechnologistRecipeCardMasterDetailView(@Autowired OrderService orderService, @Autowired RecipeCardService recipeCardService,
                                                  @Autowired RecipeCardPartService recipeCardPartService, @Autowired RecipePartService recipePartService,
                                                  @Autowired RecipeService recipeService) {
        this.orderService = orderService;
        this.recipeCardService = recipeCardService;
        this.recipeCardPartService = recipeCardPartService;
        this.recipeService = recipeService;
        this.recipePartService = recipePartService;
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(createLayout(), splitLayout);


        grid.addColumn("componentType").setAutoWidth(true).setHeader("Тип компонента");
        grid.addColumn("componentName").setAutoWidth(true).setHeader("Название компонента");
        grid.addColumn("idealWeight").setAutoWidth(true).setHeader("Эталонный вес");
        grid.addColumn("deviation").setAutoWidth(true).setHeader("Отклонение");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        recipeNewStringButton.setEnabled(false);
        save.setEnabled(false);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(RECIPECARDPART_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(TechnologistRecipeCardMasterDetailView.class);
            }
        });

        try {
            orderStringIdentifierSelect.setItems(orderService.getAllWhereIsRecipeSelected().stream().map(Order::getStringIdentifier).collect(Collectors.toList()));
        } catch (Exception exception) {
            Notification.show("Ошибка при загрузке заказов");
        }


        binder = new BeanValidationBinder<>(RecipeCardPart.class);

        binder.bindInstanceFields(this);

        orderStringIdentifierSelect.addValueChangeListener(e -> {
            if (!e.getValue().isEmpty()) {
                grid.setItems(orderService.getByStringIdentifier(e.getValue()).getRecipeCard().getRecipeCardParts());
                save.setEnabled(true);
            } else {
                grid.setItems();
                Notification.show("Нет рецептов в базе данных");
            }
        });

        recipeNewString.setPattern("[А-Я]{1,2}(-[0-9]{1,4})+");
        recipeNewString.setHelperText("Формат ввода: ИК-12-31");
        recipeNewString.addValueChangeListener(e ->
                recipeNewStringButton.setEnabled(!recipeNewString.isInvalid())
        );

        //todo добавление нового рецепта из карты
        recipeNewStringButton.addClickListener(e -> {
            if (!recipeNewString.isInvalid() && recipeService.getByRecipeStringIdentifier(recipeNewString.getValue()) == null) {
                Recipe recipe = new Recipe();
                recipe.setRecipeStringIdentifier(recipeNewString.getValue());
                Set<RecipePart> recipeParts = new HashSet<>();
                grid.getListDataView().getItems().forEach(part ->
                        recipeParts.add(recipePartService.update(new RecipePart(
                                part.getComponentType(), part.getComponentName(),
                                part.getIdealWeight(), part.getDeviation()
                        )))
                );
                recipe.getRecipeParts().addAll(recipeParts);
                recipeService.update(recipe);
                Notification.show("Рецептурная карта была добавлена как новый рецепт");
            } else {
                Notification.show("Введите правильный идентификатор");
            }
        });

        cancel.addClickListener(e -> {
            clearForm();
            grid.select(null);
            //refreshGrid();
        });

        save.addClickListener(e -> {
            if (!componentType.isEmpty() && !componentName.isEmpty() && !idealWeight.isEmpty() && !deviation.isEmpty()) {
                try {
                    if (this.recipeCardPart == null) {
                        this.recipeCardPart = new RecipeCardPart();
                        binder.writeBean(this.recipeCardPart);
                        recipeCardPartService.update(this.recipeCardPart);

                        RecipeCard recipeCard = orderService.getByStringIdentifier(orderStringIdentifierSelect.getValue()).getRecipeCard();
                        recipeCard.getRecipeCardParts().add(this.recipeCardPart);
                        recipeCardService.update(recipeCard);
                        Notification.show("Компонент добавлен.");
                    } else {
                        binder.writeBean(this.recipeCardPart);
                        recipeCardPartService.update(this.recipeCardPart);
                        Notification.show("Данные о компоненте сохранены.");
                    }
                    clearForm();
                    refreshGrid();
                    UI.getCurrent().navigate(TechnologistRecipeCardMasterDetailView.class);
                } catch (ValidationException validationException) {
                    Notification.show("Ошибка при сохранении данных.");
                }
            } else {
                Notification.show("Заполните все поля.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> recipeCardPartId = event.getRouteParameters().get(RECIPECARDPART_ID).map(Long::valueOf);
        if (recipeCardPartId.isPresent()) {
            Optional<RecipeCardPart> recipeCardPartFromBackend = recipeCardPartService.get(recipeCardPartId.get());
            if (recipeCardPartFromBackend.isPresent()) {
                populateForm(recipeCardPartFromBackend.get());
            } else {
                Notification.show(String.format("Данный компонент не был найден, ID = %s", recipeCardPartId.get()),
                        3000, Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(TechnologistRecipeCardMasterDetailView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        componentType = new Select<>("Каучук", "Сыпучая смесь", "Мел", "Техуглерод");
        componentType.setLabel("Тип компонента");
        componentName = new TextField("Название компонента");
        idealWeight = new NumberField("Эталонный вес");
        deviation = new NumberField("Допустимое отклонение");
        helperLabel = new Label("Для того, чтобы добавить новый заказ, выберите заказ и введите данные в пустые поля");
        Component[] fields = new Component[]{componentType, componentName, idealWeight, deviation, helperLabel};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        //grid.getDataProvider().refreshAll();
        grid.setItems(orderService.getByStringIdentifier(orderStringIdentifierSelect.getValue()).getRecipeCard().getRecipeCardParts());
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(RecipeCardPart value) {
        this.recipeCardPart = value;
        binder.readBean(this.recipeCardPart);

    }

    private Component createLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(recipeLabel, orderStringIdentifierSelect, recipeNewStringLabel, recipeNewString, recipeNewStringButton);
        horizontalLayout.setMargin(true);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        return horizontalLayout;
    }
}