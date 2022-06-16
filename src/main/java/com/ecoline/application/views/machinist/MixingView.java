package com.ecoline.application.views.machinist;

import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Машинист")
@Route(value = "mixing", layout = MainLayout.class)
@RolesAllowed({"admin","user","machinist"})
public class MixingView extends VerticalLayout {

    private Button mixingFormButton;

    public MixingView() {
        mixingFormButton = new Button("Перейти к смешиванию");

        mixingFormButton.addClickListener(e-> UI.getCurrent().navigate(MixingFormView.class));

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, mixingFormButton);

        add(mixingFormButton);
    }

}