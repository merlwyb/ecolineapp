package com.ecoline.application.views.mixing;

import com.ecoline.application.views.MainLayout;
import com.ecoline.application.views.correction.CorrectionDetailView;
import com.ecoline.application.views.correction.CorrectionOrderFormView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Оператор")
@Route(value = "mixing", layout = MainLayout.class)
@RolesAllowed({"admin","user","operator"})
public class MixingView extends VerticalLayout {

    private Button mixingFormButton;

    public MixingView() {
        mixingFormButton = new Button("Заполнить форму для смешивания");

        mixingFormButton.addClickListener(e-> UI.getCurrent().navigate(MixingFormView.class));

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, mixingFormButton);

        add(mixingFormButton);
    }

}