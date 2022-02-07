package com.ecoline.application.views.weighing;

import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Навесчик")
@Route(value = "weighing", layout = MainLayout.class)
@RolesAllowed({"admin","user","weigher"})
public class PortionView extends VerticalLayout {

    private Button weighingDetailButton;
    private Button weighingFormButton;

    public PortionView() {
        weighingDetailButton = new Button("Просмотр взвешиваний");
        weighingFormButton = new Button("Добавить взвешивание");

        weighingDetailButton.addClickListener(e-> UI.getCurrent().navigate(PortionDetailView.class));
        weighingFormButton.addClickListener(e-> UI.getCurrent().navigate(PortionFormView.class));

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, weighingDetailButton, weighingFormButton);

        add(weighingDetailButton, weighingFormButton);
    }

}
