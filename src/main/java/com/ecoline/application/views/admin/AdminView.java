package com.ecoline.application.views.admin;

import com.ecoline.application.data.entity.LogJournal;
import com.ecoline.application.views.MainLayout;
import com.ecoline.application.views.labworker.LabMasterDetailView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.commonmark.node.Link;

import javax.annotation.security.RolesAllowed;

@PageTitle("Административная панель")
@Route(value = "admin-panel", layout = MainLayout.class)
@RolesAllowed({"admin"})
public class AdminView extends VerticalLayout {

    private Label adminDbLabel;
    private Button adminLogDetailButton;
    private Button adminUsersDetailButton;

    public AdminView() {
        adminDbLabel = new Label("Для перехода в консоль H2 Database, вставьте в строку браузера: http://localhost:8080/h2-console");
        adminLogDetailButton = new Button("Перейти к логам");
        adminUsersDetailButton = new Button("Перейти к списку пользователей");

        adminLogDetailButton.addClickListener(e-> UI.getCurrent().navigate(AdminLogDetailView.class));
        adminUsersDetailButton.addClickListener(e-> UI.getCurrent().navigate(AdminUsersDetailView.class));

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, adminLogDetailButton,adminUsersDetailButton);

        add(adminDbLabel, adminLogDetailButton,adminUsersDetailButton);
    }

}