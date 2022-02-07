package com.ecoline.application.views.selecting;

import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Лаборант")
@Route(value = "selecting", layout = MainLayout.class)
@RolesAllowed({"admin","user","labworker"})
public class SelectingView extends VerticalLayout {

    private Button selectingFormButton;

    public SelectingView() {
        selectingFormButton = new Button("Отобрать образцы для лаборатории");

        selectingFormButton.addClickListener(e-> UI.getCurrent().navigate(SelectingFormView.class));

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, selectingFormButton);

        add(selectingFormButton);
    }

}