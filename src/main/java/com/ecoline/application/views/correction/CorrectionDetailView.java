package com.ecoline.application.views.correction;

import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.service.OrderService;
import com.ecoline.application.security.AuthenticatedUser;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle("Список заказов для коррекции")
@Route(value = "correction-detail", layout = MainLayout.class)
@RolesAllowed({"admin","user","technologist"})
public class CorrectionDetailView extends Div {

    private Grid<Order> grid = new Grid<>(Order.class, false);
    private Label respUsernameLabel = new Label("Номер заказа");
    private TextField orderToCorrect = new TextField();
    private Button proceed = new Button("Скорректировать");

    private OrderService orderService;

    private AuthenticatedUser authenticatedUser;

    public CorrectionDetailView(@Autowired OrderService orderService) {
        this.orderService = orderService;
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");

        // Create UI
        //SplitLayout splitLayout = new SplitLayout();
        VerticalLayout splitLayout = new VerticalLayout();
        splitLayout.setSizeFull();

        //createGridLayout(splitLayout);
        splitLayout.add(grid);

        add(createLayout(), splitLayout);

        proceed.setEnabled(false);
        orderToCorrect.setReadOnly(true);
        proceed.addClickListener(e->{
           if (!orderToCorrect.isEmpty()){
               VaadinSession.getCurrent().setAttribute("orderToCorrect", orderToCorrect.getValue());
               UI.getCurrent().navigate(CorrectionFormView.class);
           } else {
               Notification.show("Сначала выберите заполненный заказ");
           }
        });

        // Конфигурация таблицы
        grid.addColumn("id").setAutoWidth(true).setHeader("Id");
        grid.addColumn("rubberId").setAutoWidth(true).setHeader("Id каучука");
        grid.addColumn("bulkId").setAutoWidth(true).setHeader("Id смеси");
        grid.addColumn("chalkId").setAutoWidth(true).setHeader("Id мела");
        grid.addColumn("carbonId").setAutoWidth(true).setHeader("Id углерода");

        grid.setItems(orderService.getAllWhereIsNotCorrected());

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addItemClickListener(e->{
            Order selectedOrder = e.getItem();
            if (selectedOrder.getRubberId()!=0 &&
                    selectedOrder.getBulkId()!=0 &&
                    selectedOrder.getChalkId()!=0 &&
                    selectedOrder.getCarbonId()!=0) {
                orderToCorrect.setValue(Long.toString(selectedOrder.getId()));
                proceed.setEnabled(true);
            } else {
                orderToCorrect.setValue("");
                proceed.setEnabled(false);
            }
        });
    }

//    private void createGridLayout(SplitLayout splitLayout) {
//        Div wrapper = new Div();
//        wrapper.setId("grid-wrapper");
//        wrapper.setWidthFull();
//        splitLayout.addToPrimary(wrapper);
//        wrapper.add(grid);
//    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private Component createLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        //FormLayout formLayout = new FormLayout();
        //formLayout.add();
        horizontalLayout.add(respUsernameLabel, orderToCorrect, proceed);
        horizontalLayout.setMargin(true);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        return horizontalLayout;
    }

}
