package com.ecoline.application.views.technologist;

import com.ecoline.application.data.entity.LogJournal;
import com.ecoline.application.data.entity.Recipe;
import com.ecoline.application.data.entity.RecipePart;
import com.ecoline.application.data.service.LogJournalService;
import com.ecoline.application.data.service.RecipePartService;
import com.ecoline.application.data.service.RecipeService;
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
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@PageTitle("Редактирование рецептуры")
@Route(value = "technologist-recipeMasterDetail/:recipePartID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
@RolesAllowed({"admin", "user", "technologist"})
public class TechnologistRecipeMasterDetailView extends Div implements BeforeEnterObserver {

    private final String RECIPEPART_ID = "recipePartID";
    private final String RECIPEPART_EDIT_ROUTE_TEMPLATE = "technologist-recipeMasterDetail/%s/edit";

    private Grid<RecipePart> grid = new Grid<>(RecipePart.class, false);

    private Label recipeLabel = new Label("Идентификатор рецепта");
    private Select<String> recipeStringIdentifierSelect = new Select<>();
    private Label recipeStringLabel = new Label("Идентификатор нового рецепта");
    private TextField recipeString = new TextField();
    private Button recipeStringButton = new Button("Добавить новый рецепт");

    private Select<String> componentType;
    private TextField componentName;
    private NumberField idealWeight;
    private NumberField deviation;
    private Label helperLabel;

    private Button cancel = new Button("Отменить");
    private Button save = new Button("Сохранить");

    private BeanValidationBinder<RecipePart> binder;

    private RecipePart recipePart;

    private RecipeService recipeService;
    private RecipePartService recipePartService;

    @Autowired
    private LogJournalService logJournalService;

    public TechnologistRecipeMasterDetailView(@Autowired RecipeService recipeService, @Autowired RecipePartService recipePartService) {
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

        recipeStringButton.setEnabled(false);
        save.setEnabled(false);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(RECIPEPART_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(TechnologistRecipeMasterDetailView.class);
            }
        });

        try {
            recipeStringIdentifierSelect.setItems(recipeService.getAll().stream().map(Recipe::getRecipeStringIdentifier).collect(Collectors.toList()));
        } catch (Exception exception) {
            Notification.show("Ошибка при загрузке рецептов");
        }


        binder = new BeanValidationBinder<>(RecipePart.class);

        binder.bindInstanceFields(this);

        recipeStringIdentifierSelect.addValueChangeListener(e -> {
            if (!e.getValue().isEmpty()) {
                grid.setItems(recipeService.getByRecipeStringIdentifier(e.getValue()).getRecipeParts());
                save.setEnabled(true);
            } else {
                grid.setItems();
                Notification.show("Нет рецептов в базе данных");
            }
        });

        recipeString.setPattern("[А-Я]{1,2}(-[0-9]{1,4})+");
        recipeString.setHelperText("Формат ввода: ИК-12-31");
        recipeString.addValueChangeListener(e ->
                recipeStringButton.setEnabled(!recipeString.isInvalid())
        );

        recipeStringButton.addClickListener(e -> {
            if (!recipeString.isInvalid() && recipeService.getByRecipeStringIdentifier(recipeString.getValue())==null) {
                Recipe recipe = new Recipe();
                recipe.setRecipeStringIdentifier(recipeString.getValue());
                recipeService.update(recipe);
                Notification.show("Новый рецепт был добавлен");
                logJournalService.update(new LogJournal(LocalDateTime.now(), VaadinSession.getCurrent().getAttribute("username").toString(), "Рецепт", "Пользователь добавил новый рецепт №" + recipeString.getValue()));
                try {
                    recipeStringIdentifierSelect.setItems(recipeService.getAll().stream().map(Recipe::getRecipeStringIdentifier).collect(Collectors.toList()));
                } catch (Exception exception) {
                    Notification.show("Ошибка при загрузке рецептов");
                }
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
                    if (this.recipePart == null) {
                        this.recipePart = new RecipePart();
                        binder.writeBean(this.recipePart);
                        recipePartService.update(this.recipePart);

                        Recipe recipe = recipeService.getByRecipeStringIdentifier(recipeStringIdentifierSelect.getValue());
                        recipe.getRecipeParts().add(this.recipePart);
                        recipeService.update(recipe);
                        Notification.show("Компонент добавлен.");
                    } else {
                        binder.writeBean(this.recipePart);
                        recipePartService.update(this.recipePart);
                        Notification.show("Данные о компоненте сохранены.");
                    }
                    clearForm();
                    refreshGrid();
                    UI.getCurrent().navigate(TechnologistRecipeMasterDetailView.class);
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
        Optional<Long> recipePartId = event.getRouteParameters().get(RECIPEPART_ID).map(Long::valueOf);
        if (recipePartId.isPresent()) {
            Optional<RecipePart> recipePartFromBackend = recipePartService.get(recipePartId.get());
            if (recipePartFromBackend.isPresent()) {
                populateForm(recipePartFromBackend.get());
            } else {
                Notification.show(String.format("Данный компонент не был найден, ID = %s", recipePartId.get()),
                        3000, Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(TechnologistRecipeMasterDetailView.class);
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
        helperLabel= new Label("Для того, чтобы добавить новый заказ, выберите заказ и введите данные в пустые поля");
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
        grid.setItems(recipeService.getByRecipeStringIdentifier(recipeStringIdentifierSelect.getValue()).getRecipeParts());
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(RecipePart value) {
        this.recipePart = value;
        binder.readBean(this.recipePart);

    }

    private Component createLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(recipeLabel, recipeStringIdentifierSelect, recipeStringLabel, recipeString, recipeStringButton);
        horizontalLayout.setMargin(true);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        return horizontalLayout;
    }
}