package com.ecoline.application.views.rolling;

import com.ecoline.application.views.MainLayout;
import com.ecoline.application.views.correction.CorrectionDetailView;
import com.ecoline.application.views.correction.CorrectionOrderFormView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Вальцовщик")
@Route(value = "rolling", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "rollerman"})
public class RollingView extends VerticalLayout {

    private Button rollingFormButton;
    private Button rollingDryingFormButton;

    public RollingView() {
        rollingFormButton = new Button("Отметить этап вальцирования");
        rollingDryingFormButton = new Button("Отметить этап сушки");

        rollingFormButton.addClickListener(e -> UI.getCurrent().navigate(RollingFormView.class));
        rollingDryingFormButton.addClickListener(e -> UI.getCurrent().navigate(RollingDryingFormView.class));

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, rollingFormButton, rollingDryingFormButton);

        add(rollingFormButton, rollingDryingFormButton);
    }

}