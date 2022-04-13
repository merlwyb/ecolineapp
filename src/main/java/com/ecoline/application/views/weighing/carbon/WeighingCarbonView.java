package com.ecoline.application.views.weighing.carbon;

import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Навесчик техуглерода")
@Route(value = "weighing-carbon", layout = MainLayout.class)
@RolesAllowed({"admin","weigher_carbon"})
public class WeighingCarbonView extends VerticalLayout {

    private Button weighingFormButton;

    public WeighingCarbonView() {
        weighingFormButton = new Button("Добавить взвешивание");
        weighingFormButton.addClickListener(e-> UI.getCurrent().navigate(WeighingCarbonFormView.class));
        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, weighingFormButton);
        add(weighingFormButton);
    }

}
