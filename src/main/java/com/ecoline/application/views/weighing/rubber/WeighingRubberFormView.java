package com.ecoline.application.views.weighing.rubber;

import com.ecoline.application.data.entity.ComponentPortion;
import com.ecoline.application.data.entity.LogJournal;
import com.ecoline.application.data.entity.TechnologicalCard;
import com.ecoline.application.data.entity.util.SumAndCountUtility;
import com.ecoline.application.data.service.ComponentPortionService;
import com.ecoline.application.data.service.LogJournalService;
import com.ecoline.application.data.service.OrderService;
import com.ecoline.application.data.service.TechnologicalCardService;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.Random;

@PageTitle("Форма навески")
@Route(value = "weighing-form-rubber", layout = MainLayout.class)
@RolesAllowed({"admin", "weigher_rubber"})
@Uses(Icon.class)
public class WeighingRubberFormView extends Div {

    private TextField RFID = new TextField("RFID");
    private TextField componentType = new TextField();
    private TextField componentName = new TextField("Компонент");
    private NumberField weightActual = new NumberField("Вес (кг)");
    private NumberField weightIdeal = new NumberField("Вес по рецептуре (кг)");
    private NumberField weightWithDeviation = new NumberField("Рекомендуемый вес (кг)");
    private TextField respUsername = new TextField("Ответственный за навеску");

    private Button save = new Button("Добавить навеску");

    private Grid<TechnologicalCard> grid = new Grid<>(TechnologicalCard.class, false);

    private Binder<ComponentPortion> binder = new Binder(ComponentPortion.class);

    private TechnologicalCardService technologicalCardService;
    private OrderService orderService;
    private ComponentPortionService componentPortionService;

    private TechnologicalCard technologicalCard;
    private Random rand = new Random();

    @Autowired
    private LogJournalService logJournalService;

    public WeighingRubberFormView(@Autowired TechnologicalCardService technologicalCardService,
                                  @Autowired OrderService orderService, @Autowired ComponentPortionService componentPortionService) {
        this.technologicalCardService = technologicalCardService;
        this.orderService = orderService;
        this.componentPortionService = componentPortionService;
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        add(createRecipeLayout());
        componentType.setVisible(false);

        binder.bindInstanceFields(this);


        grid.addColumn("componentName").setAutoWidth(true).setHeader("Название компонента").setSortable(false);
        grid.addColumn("amountRemaining").setAutoWidth(true).setHeader("Количество взвешиваний").setSortable(false);
        grid.setItems(technologicalCardService.getAllWhereRubber());

        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        grid.setWidthFull();


        RFID.setReadOnly(true);
        //weightActual.setReadOnly(true);
        weightIdeal.setReadOnly(true);
        weightWithDeviation.setReadOnly(true);
        componentName.setReadOnly(true);
        respUsername.setReadOnly(true);
        componentType.setReadOnly(true);
        weightActual.setSuffixComponent(new Span("кг"));
        weightIdeal.setSuffixComponent(new Span("кг"));
        weightWithDeviation.setSuffixComponent(new Span("кг"));


        weightActual.addValueChangeListener(e -> {
            if (!componentName.isEmpty() && !weightActual.isEmpty() && !weightIdeal.isEmpty() && !weightWithDeviation.isEmpty()) {
                save.setEnabled(true);
            }
        });

        //Первоначальное заполнение
        try {
            technologicalCard = grid.getListDataView().getItem(0);

            binder.setBean(new ComponentPortion("Каучук"));
            RFID.setValue(String.valueOf(rand.nextInt(9998) + 1));
            respUsername.setValue(VaadinSession.getCurrent().getAttribute("username").toString());
            componentName.setValue(grid.getListDataView().getItem(0).getComponentName());
            weightActual.setValue(grid.getListDataView().getItem(0).getWeight() - 6 + (int) (Math.random() * 10));
            weightIdeal.setValue(grid.getListDataView().getItem(0).getWeight());

            try {
                SumAndCountUtility sacUtil = componentPortionService.getSumAndCountForDeviation(componentName.getValue(), grid.getListDataView().getItem(0).getOrderId());
                weightWithDeviation.setValue(Math.ceil(((sacUtil.getCountOfDeviation()+1) * grid.getListDataView().getItem(0).getWeight() - sacUtil.getSumOfDeviation())*1000)/1000);
            } catch (Exception exception) {
                weightWithDeviation.setValue(grid.getListDataView().getItem(0).getWeight());
            }

        } catch (Exception exception) {
            Notification.show("Ошибка при загрузке навесок, попробуйте перезагрузить страницу");
            save.setEnabled(false);
        }

        save.addClickListener(e -> {
            try {
                technologicalCard.setAmountRemaining(technologicalCard.getAmountRemaining() - 1);
                technologicalCardService.update(technologicalCard);

                ComponentPortion temp = binder.getBean();
                temp.setOrder(orderService.get(technologicalCard.getOrderId()).get());
                componentPortionService.update(temp);

                grid.setItems(technologicalCardService.getAllWhereRubber());
                technologicalCard = grid.getListDataView().getItem(0);

                binder.setBean(new ComponentPortion("Каучук"));
                componentName.setValue(grid.getListDataView().getItem(0).getComponentName());
                weightIdeal.setValue(grid.getListDataView().getItem(0).getWeight());
                weightActual.setValue(grid.getListDataView().getItem(0).getWeight() - 6 + (int) (Math.random() * 10));
                RFID.setValue(String.valueOf(rand.nextInt(9998) + 1));

                try {
                    SumAndCountUtility sacUtil = componentPortionService.getSumAndCountForDeviation(componentName.getValue(), grid.getListDataView().getItem(0).getOrderId());
                    weightWithDeviation.setValue(Math.ceil(((sacUtil.getCountOfDeviation()+1) * grid.getListDataView().getItem(0).getWeight() - sacUtil.getSumOfDeviation())*1000)/1000);
                } catch (Exception exception) {
                    weightWithDeviation.setValue(grid.getListDataView().getItem(0).getWeight());
                }
            } catch (Exception ex) {
                Notification.show("Нет данных для навески, попробуйте обновить страницу");
                save.setEnabled(false);
            }

        });
    }

//    private void clearForm() {
//        binder.setBean(new ComponentPortion("Каучук"));
//        respUsername.setValue(VaadinSession.getCurrent().getAttribute("username").toString());
//    }

    private Component createTitle() {
        return new H3("Информация о навеске");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(RFID, componentName, weightActual, weightIdeal, weightWithDeviation, respUsername);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.setMargin(true);
        return buttonLayout;
    }

    private Component createRecipeLayout() {
        VerticalLayout recipeLayout = new VerticalLayout();
        recipeLayout.addClassNames("master-detail-view", "flex", "flex-col", "h-full");
        recipeLayout.setId("recipes");
        recipeLayout.setSizeFull();
        recipeLayout.add(grid);
        recipeLayout.setMargin(true);
        return recipeLayout;
    }
}
