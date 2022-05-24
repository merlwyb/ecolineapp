package com.ecoline.application.views.labworker;

import com.ecoline.application.data.entity.LabJournal;
import com.ecoline.application.data.entity.LogJournal;
import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.entity.Recipe;
import com.ecoline.application.data.service.LabJournalService;
import com.ecoline.application.data.service.LogJournalService;
import com.ecoline.application.data.service.OrderService;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

//todo Добавить поля для журнала лаборанта
@PageTitle("Журнал 1")
@Route(value = "lab-master-detail/:labJournalID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "labworker"})
public class LabMasterDetailView extends Div implements BeforeEnterObserver {

    private final String LABJOURNAL_ID = "labJournalID";
    private final String LABJOURNAL_EDIT_ROUTE_TEMPLATE = "lab-master-detail/%s/edit";

    private Grid<LabJournal> grid = new Grid<>(LabJournal.class, false);
    private ComboBox<Order> orderStringIdentifierSelector = new ComboBox<>("Номер заказа");

    private DatePicker date;
    private TextField brand;
    private TextField numberLaying;
    private DatePicker vulcanizationDate;
    private IntegerField vulcanizationTemperature;
    private IntegerField vulcanizationTime;
    private IntegerField hardnessActual;
    private TextField hardnessIdeal;
    private NumberField enduranceActual;
    private TextField enduranceIdeal;
    private IntegerField lengtheningActual;
    private TextField lengtheningIdeal;
    private TextField deformationActual;
    private TextField vylezhka;

    private Button cancel = new Button("Очистить");
    private Button save = new Button("Сохранить");

    private BeanValidationBinder<LabJournal> binder;

    private LabJournal labJournal;

    private final LabJournalService labJournalService;
    private final OrderService orderService;

    @Autowired
    private LogJournalService logJournalService;

    public LabMasterDetailView(LabJournalService labJournalService, OrderService orderService) {
        this.labJournalService = labJournalService;
        this.orderService = orderService;
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        add(createOrderLayout()); //TODO заполнение журнала при выборе заказа
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);

        add(splitLayout);

        // Configures
        //stringOrderIdentifier.setReadOnly(true);
        date.setReadOnly(true);
        //company.setReadOnly(true); //todo заполнение компании автоматически
        save.setEnabled(false);
        vulcanizationTemperature.setSuffixComponent(new Span("°C"));
        vulcanizationTime.setSuffixComponent(new Span("с"));
        hardnessIdeal.setSuffixComponent(new Span("у.е."));
        hardnessActual.setSuffixComponent(new Span("у.е."));
        enduranceIdeal.setSuffixComponent(new Span("мПа"));
        enduranceActual.setSuffixComponent(new Span("мПа"));
        lengtheningIdeal.setSuffixComponent(new Span("%"));
        lengtheningActual.setSuffixComponent(new Span("%"));
//        numberLaying, vulcanizationDate, vulcanizationTemperature,
//                vulcanizationTime, hardnessActual, hardnessIdeal, enduranceActual, enduranceIdeal, lengtheningActual,
//                lengtheningIdeal, deformationActual, vylezhka, company

        // Configure Grid
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        grid.addColumn("date").setAutoWidth(true).setHeader("Дата изготовления");
        grid.addColumn("brand").setAutoWidth(true).setHeader("Марка резинов. смеси");
        grid.addColumn("numberLaying").setAutoWidth(true).setHeader("Номер закладки");

        Grid.Column<LabJournal> vul1 = grid.addColumn("vulcanizationDate").setAutoWidth(true).setHeader("дата");
        Grid.Column<LabJournal> vul2 = grid.addColumn("vulcanizationTemperature").setAutoWidth(true).setHeader("температура");
        Grid.Column<LabJournal> vul3 = grid.addColumn("vulcanizationTime").setAutoWidth(true).setHeader("время");

        Grid.Column<LabJournal> hard1 = grid.addColumn("hardnessActual").setAutoWidth(true).setHeader("факт.");
        Grid.Column<LabJournal> hard2 = grid.addColumn("hardnessIdeal").setAutoWidth(true).setHeader("норма");

        Grid.Column<LabJournal> end1 = grid.addColumn("enduranceActual").setAutoWidth(true).setHeader("факт.");
        Grid.Column<LabJournal> end2 = grid.addColumn("enduranceIdeal").setAutoWidth(true).setHeader("норма");

        Grid.Column<LabJournal> len1 = grid.addColumn("lengtheningActual").setAutoWidth(true).setHeader("факт.");
        Grid.Column<LabJournal> len2 = grid.addColumn("lengtheningIdeal").setAutoWidth(true).setHeader("норма");

        grid.addColumn("deformationActual").setAutoWidth(true).setHeader("Деформация факт.");
        grid.addColumn("vylezhka").setAutoWidth(true).setHeader("Вылежка");
        grid.addColumn("company").setAutoWidth(true).setHeader("Потребитель");

        HeaderRow headerRow = grid.prependHeaderRow();
        headerRow.join(vul1, vul2, vul3).setText("Вулканизация");
        headerRow.join(hard1, hard2).setText("Твердость по Шор А, у.е.");
        headerRow.join(end1, end2).setText("Усл. прочность, н.м., мПа");
        headerRow.join(len1, len2).setText("Относит. удлинение, н.м., %");

//        grid.setItems(query -> labJournalService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
        orderStringIdentifierSelector.setItems(orderService.getAllWhereIsCompleted());
        orderStringIdentifierSelector.setItemLabelGenerator(Order::getStringIdentifier);
        orderStringIdentifierSelector.addValueChangeListener(e -> {
                    if (!orderStringIdentifierSelector.isEmpty()) {
                        save.setEnabled(true);
                        grid.setItems(labJournalService.getAllByStringIdentifier(e.getValue().getStringIdentifier()));

                        //stringOrderIdentifier.setValue(orderStringIdentifierSelector.getValue().getStringIdentifier());
                        //splitLayout.setVisible(true); //todo вернуть обратно
                    } else {
                        grid.setItems(new ArrayList<>());
                        save.setEnabled(false);
                    }
                }
        );
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(LABJOURNAL_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(LabMasterDetailView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(LabJournal.class);

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            if (!orderStringIdentifierSelector.isEmpty()) {
                if (!date.isEmpty() && !brand.isEmpty() && !numberLaying.isEmpty() &&
                        !vulcanizationDate.isEmpty() && !vulcanizationTemperature.isEmpty() && !vulcanizationTime.isEmpty() &&
                        !hardnessActual.isEmpty() && !hardnessIdeal.isEmpty() && !enduranceActual.isEmpty() && !enduranceIdeal.isEmpty() &&
                        !lengtheningActual.isEmpty() && !lengtheningIdeal.isEmpty() && !deformationActual.isEmpty() && !vylezhka.isEmpty()
                ) {
                    try {
                        if (this.labJournal == null) {
                            this.labJournal = new LabJournal();
                        }
                        this.labJournal.setStringOrderIdentifier(orderStringIdentifierSelector.getValue().getStringIdentifier());
                        this.labJournal.setCompany(orderStringIdentifierSelector.getValue().getCompany());
                        binder.writeBean(this.labJournal);

                        labJournalService.update(this.labJournal);
                        clearForm();
                        refreshGrid();
                        Notification.show("Данные сохранены.");
                        logJournalService.update(new LogJournal(LocalDateTime.now(),
                                VaadinSession.getCurrent().getAttribute("username").toString(), "Лаборант",
                                "Пользователь добавил журнал для заказа №" + orderStringIdentifierSelector.getValue().getStringIdentifier()));
                        UI.getCurrent().navigate(LabMasterDetailView.class);
                    } catch (ValidationException validationException) {
                        Notification.show("Произошла ошибка при сохранении данных.");
                    }
                } else {
                    Notification.show("Заполните все поля");
                }
            } else {
                Notification.show("Выберите заказ");
            }
        });
        clearForm();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> labJournalId = event.getRouteParameters().get(LABJOURNAL_ID).map(Long::valueOf);
        if (labJournalId.isPresent()) {
            Optional<LabJournal> labJournalFromBackend = labJournalService.get(labJournalId.get());
            if (labJournalFromBackend.isPresent()) {
                populateForm(labJournalFromBackend.get());
            } else {
                Notification.show(String.format("The requested labJournal was not found, ID = %s", labJournalId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(LabMasterDetailView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("person-form1-view flex flex-col");

        Div editorDiv = new Div();
        editorDiv.setClassName("person-form1-view editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        //stringOrderIdentifier = new TextField("Номер заказа");
        date = new DatePicker("Дата изготовления");
        brand = new TextField("Марка резинов. смеси");
        numberLaying = new TextField("Номер закладки");
        vulcanizationDate = new DatePicker("Вулканизация - дата");
        vulcanizationTemperature = new IntegerField("Вулканизация - температура");
        vulcanizationTime = new IntegerField("Вулканизация - время");
        hardnessActual = new IntegerField("Твердость по Шор А - факт.");
        hardnessIdeal = new TextField("Твердость по Шор А - норма");
        enduranceActual = new NumberField("Усл. прочность - факт.");
        enduranceIdeal = new TextField("Усл. прочность - норма.");
        lengtheningActual = new IntegerField("Относ. удлинение - факт.");
        lengtheningIdeal = new TextField("Относ. удлинение - норма");
        deformationActual = new TextField("Деформация - факт.");
        vylezhka = new TextField("Вылежка");
        Component[] fields = new Component[]{date, brand, numberLaying, vulcanizationDate, vulcanizationTemperature,
                vulcanizationTime, hardnessActual, hardnessIdeal, enduranceActual, enduranceIdeal, lengtheningActual,
                lengtheningIdeal, deformationActual, vylezhka};

        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        if (!orderStringIdentifierSelector.isEmpty()) {
            grid.setItems(labJournalService.getAllByStringIdentifier(orderStringIdentifierSelector.getValue().getStringIdentifier()));
        }
    }

    private void clearForm() {
        populateForm(null);
        //stringOrderIdentifier.setValue(orderStringIdentifierSelector.getValue().getStringIdentifier());
        date.setValue(LocalDate.now());
        //todo после очистки заполнение данных автоматически
    }

    private void populateForm(LabJournal value) {
        this.labJournal = value;
        binder.readBean(this.labJournal);

    }

    private Component createOrderLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(orderStringIdentifierSelector);
        horizontalLayout.setMargin(true);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        return horizontalLayout;
    }
}


