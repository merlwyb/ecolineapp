package com.ecoline.application.views.correction;

import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Технолог")
@Route(value = "correction", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "technologist"})
public class CorrectionView extends VerticalLayout {

    private Button correctionOrderFormButton;
    private Button correctionDetailButton;

    public CorrectionView() {
        correctionOrderFormButton = new Button("Добавить заказ");
        correctionDetailButton = new Button("Просмотр заказов");


        correctionOrderFormButton.addClickListener(e -> UI.getCurrent().navigate(CorrectionOrderFormView.class));
        correctionDetailButton.addClickListener(e -> UI.getCurrent().navigate(CorrectionDetailView.class));

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, correctionOrderFormButton, correctionDetailButton);

        add(correctionOrderFormButton, correctionDetailButton);
    }

}