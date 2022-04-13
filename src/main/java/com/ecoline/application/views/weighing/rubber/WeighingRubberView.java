package com.ecoline.application.views.weighing.rubber;

import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Навесчик каучука")
@Route(value = "weighing-rubber", layout = MainLayout.class)
@RolesAllowed({"admin", "weigher_rubber"})
public class WeighingRubberView extends VerticalLayout {

    //private Button weighingDetailButton;
    private Button weighingFormButton;

    public WeighingRubberView() {
        //weighingDetailButton = new Button("Просмотр взвешиваний");
        weighingFormButton = new Button("Добавить взвешивание");

        //weighingDetailButton.addClickListener(e-> UI.getCurrent().navigate(WeighingRubberView.class));
        weighingFormButton.addClickListener(e -> UI.getCurrent().navigate(WeighingRubberFormView.class));

        setMargin(true);
        //setHorizontalComponentAlignment(Alignment.START, weighingDetailButton, weighingFormButton);
        setHorizontalComponentAlignment(Alignment.START, weighingFormButton);

        //add(weighingDetailButton, weighingFormButton);
        add(weighingFormButton);
    }

}
