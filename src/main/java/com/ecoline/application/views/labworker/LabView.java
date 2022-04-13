package com.ecoline.application.views.labworker;

import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Лаборант")
@Route(value = "lab", layout = MainLayout.class)
@RolesAllowed({"admin","user","labworker"})
public class LabView extends VerticalLayout {

    private Button labMasterDetailButton;

    public LabView() {
        labMasterDetailButton = new Button("Отобрать образцы для лаборатории");

        labMasterDetailButton.addClickListener(e-> UI.getCurrent().navigate(LabMasterDetailView.class));

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, labMasterDetailButton);

        add(labMasterDetailButton);
    }

}