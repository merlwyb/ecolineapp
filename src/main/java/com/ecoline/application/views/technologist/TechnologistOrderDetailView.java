package com.ecoline.application.views.technologist;

import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.entity.RecipeCardPart;
import com.ecoline.application.data.entity.TechnologicalCard;
import com.ecoline.application.data.service.OrderService;
import com.ecoline.application.data.service.TechnologicalCardService;
import com.ecoline.application.security.AuthenticatedUser;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionModel;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle("Список заказов")
@Route(value = "technologist-order-detail", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "technologist"})
public class TechnologistOrderDetailView extends Div {

    private Grid<Order> grid = new Grid<>(Order.class, false);
    private Label orderLabel = new Label("Идентификатор заказа");
    private TextField orderToProceed = new TextField();
    private Button proceed = new Button("Перейти к редактированию заказа");
    private Button sendForWeighing = new Button("Отправить на навеску");
    private Button recipeMasterDetail = new Button("Перейти к редактированию рецептур");

    private AuthenticatedUser authenticatedUser;
    private Order orderForWeighing;

    public TechnologistOrderDetailView(@Autowired OrderService orderService,
                                       @Autowired TechnologicalCardService technologicalCardService) {
        addClassNames("master-detail-view", "flex", "flex-col", "h-full");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        verticalLayout.add(grid);

        add(createLayout(), verticalLayout);

        proceed.setEnabled(false);
        sendForWeighing.setEnabled(false);
        orderToProceed.setReadOnly(true);
        proceed.addClickListener(e -> {
            if (!orderToProceed.isEmpty()) {
                VaadinSession.getCurrent().setAttribute("orderToProceed", orderToProceed.getValue());
                UI.getCurrent().navigate(TechnologistOrderFormView.class);
            } else {
                Notification.show("Выберите заказ без рецепта");
            }
        });

        sendForWeighing.addClickListener(e -> {
            //вычисление количества порций
            double tempWeight = orderForWeighing.getRecipeCard().getRecipeCardParts().stream().mapToDouble(RecipeCardPart::getIdealWeight).sum();
            orderForWeighing.setAmount((int) Math.ceil(orderForWeighing.getWeightRequired() / tempWeight));

            if (!orderToProceed.isEmpty()) {
                orderForWeighing.getRecipeCard().getRecipeCardParts().forEach(recipeCardPart -> technologicalCardService.update(
                        new TechnologicalCard(orderForWeighing.getId(), recipeCardPart.getComponentType(), recipeCardPart.getComponentName(),
                                orderForWeighing.getAmount(),
                                recipeCardPart.getIdealWeight(), recipeCardPart.getDeviation()))
                );
                orderForWeighing.setSend(true);
                orderService.update(orderForWeighing);
                orderToProceed.clear();
                grid.setItems(orderService.getAllWhereIsNotSend());
            } else {
                Notification.show("Выберите заказ с рецептом");
            }
        });

        recipeMasterDetail.addClickListener(e -> {
            UI.getCurrent().navigate(TechnologistRecipeCardMasterDetailView.class);
        });

        grid.addColumn("id").setAutoWidth(true).setHeader("№");
        grid.addColumn("stringIdentifier").setAutoWidth(true).setHeader("Идентификатор заказа");
        grid.addColumn("weightRequired").setAutoWidth(true).setHeader("Вес заказа");
        grid.addComponentColumn(item -> {
            Icon icon;
            if (item.isRecipeSelected()) {
                icon = VaadinIcon.CHECK.create();
                icon.setColor("green");
            } else {
                icon = VaadinIcon.CLOSE.create();
                icon.setColor("red");
            }
            return icon;
        }).setAutoWidth(true).setHeader("Рецептура выбрана");


        grid.setItems(orderService.getAllWhereIsNotSend());

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addItemClickListener(e -> {
            Order selectedOrder = e.getItem();
            this.orderForWeighing = selectedOrder;
            orderToProceed.setValue(selectedOrder.getStringIdentifier());
            if (!selectedOrder.isRecipeSelected()) {
                proceed.setEnabled(true);
                sendForWeighing.setEnabled(false);
            } else {
                proceed.setEnabled(false);
                sendForWeighing.setEnabled(true);
            }
        });
    }

    private Component createLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(orderLabel, orderToProceed, proceed, sendForWeighing, recipeMasterDetail);
        horizontalLayout.setMargin(true);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        return horizontalLayout;
    }

}
