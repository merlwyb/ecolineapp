package com.ecoline.application.views.weighing.bulk;

import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Навесчик сып. смеси")
@Route(value = "weighing-bulk", layout = MainLayout.class)
@RolesAllowed({"admin","weigher_bulk"})
public class WeighingBulkView extends VerticalLayout {

    private Button weighingFormButton;

    public WeighingBulkView() {
        weighingFormButton = new Button("Добавить взвешивание");
        weighingFormButton.addClickListener(e-> UI.getCurrent().navigate(WeighingBulkFormView.class));
        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, weighingFormButton);
        add(weighingFormButton);
    }

}
