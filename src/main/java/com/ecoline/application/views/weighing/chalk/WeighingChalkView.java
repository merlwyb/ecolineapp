package com.ecoline.application.views.weighing.chalk;

import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Навесчик мела")
@Route(value = "weighing-chalk", layout = MainLayout.class)
@RolesAllowed({"admin", "weigher_chalk"})
public class WeighingChalkView extends VerticalLayout {

    private Button weighingFormButton;

    public WeighingChalkView() {
        weighingFormButton = new Button("Добавить взвешивание");
        weighingFormButton.addClickListener(e -> UI.getCurrent().navigate(WeighingChalkFormView.class));
        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, weighingFormButton);
        add(weighingFormButton);
    }

}
