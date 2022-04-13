package com.ecoline.application.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;


@PageTitle("Стартовая страница")
@Route(value = "", layout = MainLayout.class)
@RolesAllowed({"admin","user","weigher","technologist","machinist","rollerman","labworker","role6","role7"})
public class MainView  extends VerticalLayout {

    public MainView() {
        setSpacing(false);

        Image img = new Image("images/logo.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H2("Стартовая страница"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
