package com.ecoline.application.views;

import com.ecoline.application.data.entity.User;
import com.ecoline.application.security.AuthenticatedUser;
//import com.ecoline.application.views.correction.CorrectionDetailView;
//import com.ecoline.application.views.correction.CorrectionFormView;
//import com.ecoline.application.views.correction.CorrectionOrderFormView;
//import com.ecoline.application.views.correction.CorrectionView;
//import com.ecoline.application.views.mixing.MixingFormView;
//import com.ecoline.application.views.mixing.MixingView;
//import com.ecoline.application.views.rolling.RollingDryingFormView;
//import com.ecoline.application.views.rolling.RollingFormView;
//import com.ecoline.application.views.rolling.RollingView;
//import com.ecoline.application.views.selecting.SelectingFormView;
//import com.ecoline.application.views.selecting.SelectingView;
//import com.ecoline.application.views.weighing.PortionDetailView;
//import com.ecoline.application.views.weighing.PortionFormView;
//import com.ecoline.application.views.weighing.PortionView;
//import com.ecoline.application.views.weighing.rubber.WeighingRubberDetailView;
//import com.ecoline.application.views.weighing.rubber.WeighingRubberFormView;
//import com.ecoline.application.views.weighing.rubber.WeighingRubberView;
import com.ecoline.application.views.admin.AdminLogDetailView;
import com.ecoline.application.views.admin.AdminUsersDetailView;
import com.ecoline.application.views.admin.AdminView;
import com.ecoline.application.views.labworker.LabMasterDetailView;
import com.ecoline.application.views.labworker.LabView;
import com.ecoline.application.views.machinist.MixingFormView;
import com.ecoline.application.views.machinist.MixingView;
import com.ecoline.application.views.rollerman.RollingDryingFormView;
import com.ecoline.application.views.rollerman.RollingFormView;
import com.ecoline.application.views.rollerman.RollingView;
import com.ecoline.application.views.technologist.*;
import com.ecoline.application.views.weighing.bulk.WeighingBulkFormView;
import com.ecoline.application.views.weighing.bulk.WeighingBulkView;
import com.ecoline.application.views.weighing.carbon.WeighingCarbonFormView;
import com.ecoline.application.views.weighing.carbon.WeighingCarbonView;
import com.ecoline.application.views.weighing.chalk.WeighingChalkFormView;
import com.ecoline.application.views.weighing.chalk.WeighingChalkView;
import com.ecoline.application.views.weighing.rubber.WeighingRubberFormView;
import com.ecoline.application.views.weighing.rubber.WeighingRubberView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PageTitle("Main")
public class MainLayout extends AppLayout {

    public static class MenuItemInfo {

        private String text;
        private String iconClass;
        private Class<? extends Component> view;

        public MenuItemInfo(String text, String iconClass, Class<? extends Component> view) {
            this.text = text;
            this.iconClass = iconClass;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public String getIconClass() {
            return iconClass;
        }

        public Class<? extends Component> getView() {
            return view;
        }

    }

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        //getElement().getThemeList().add("app-nav-layout");
        addToNavbar(createHeaderContent());


        //menu = createMenuTabs();
        //addToNavbar(menu);
    }

    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "flex-col", "w-full");

        Div layout = new Div();
        layout.addClassNames("flex", "h-xl", "items-center", "px-l");

        H1 appName = new H1("EcolineApp");
        appName.addClassNames("my-0", "me-auto", "text-l");
        layout.add(appName);

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            VaadinSession.getCurrent().setAttribute("username", user.getUsername());

            Avatar avatar = new Avatar(user.getName(), user.getProfilePictureUrl());
            avatar.addClassNames("me-xs");

            ContextMenu userMenu = new ContextMenu(avatar);
            userMenu.setOpenOnClick(true);
            userMenu.addItem("Выйти", e -> {
                authenticatedUser.logout();
            });

            Span name = new Span(user.getName());
            name.addClassNames("font-medium", "text-s", "text-secondary");

            layout.add(avatar, name);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        Nav nav = new Nav();
        nav.addClassNames("flex", "gap-s", "overflow-auto", "px-m");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("flex", "list-none", "m-0", "p-0");
        nav.add(list);

        for (RouterLink link : createLinks()) {
            ListItem item = new ListItem(link);
            list.add(item);
        }

        header.add(layout, nav);
        return header;
    }

    private List<RouterLink> createLinks() {
        MenuItemInfo[] menuItems = new MenuItemInfo[]{

                new MenuItemInfo("Панель администратора", "la la-user", AdminView.class),

                new MenuItemInfo("Журнал логов", "la la-columns", AdminLogDetailView.class),

                new MenuItemInfo("Список пользователей", "la la-columns", AdminUsersDetailView.class),

                new MenuItemInfo("Навесчик каучука", "la la-user", WeighingRubberView.class),

                new MenuItemInfo("Форма навески", "la la-plus", WeighingRubberFormView.class),

                new MenuItemInfo("Навесчик сып. смеси", "la la-user", WeighingBulkView.class),

                new MenuItemInfo("Форма навески", "la la-plus", WeighingBulkFormView.class),

                new MenuItemInfo("Навесчик мела", "la la-user", WeighingChalkView.class),

                new MenuItemInfo("Форма навески", "la la-plus", WeighingChalkFormView.class),

                new MenuItemInfo("Навесчик техуглерода", "la la-user", WeighingCarbonView.class),

                new MenuItemInfo("Форма навески", "la la-plus", WeighingCarbonFormView.class),

                new MenuItemInfo("Технолог", "la la-user", TechnologistView.class),

                new MenuItemInfo("Список заказов", "la la-columns", TechnologistOrderDetailView.class),

                new MenuItemInfo("Форма редактирования заказа", "la la-plus", TechnologistOrderFormView.class),

                new MenuItemInfo("Машинист", "la la-user", MixingView.class),

                new MenuItemInfo("Форма смешивания", "la la-plus", MixingFormView.class),

                new MenuItemInfo("Вальцовщик", "la la-user", RollingView.class),

                new MenuItemInfo("Форма вальцевания", "la la-plus", RollingFormView.class),

                new MenuItemInfo("Форма сушки", "la la-plus", RollingDryingFormView.class),

                new MenuItemInfo("Лаборант", "la la-user", LabView.class),

                new MenuItemInfo("Журнал 1", "la la-columns", LabMasterDetailView.class),

//                new MenuItemInfo("Редактирование рецептурной карты заказа", "la la-columns", TechnologistRecipeCardMasterDetailView.class),

//                new MenuItemInfo("Редактирование/добавление рецептуры", "la la-columns", TechnologistRecipeMasterDetailView.class),
//
//                //new MenuItemInfo("Форма корректировки","la la-plus", CorrectionFormView.class),
//
//                new MenuItemInfo("Оператор", "la la-user", MixingView.class),
//
//                new MenuItemInfo("Форма смешивания", "la la-plus", MixingFormView.class),
//
//                new MenuItemInfo("Вальцовщик", "la la-user", RollingView.class),
//
//                new MenuItemInfo("Форма вальцевания", "la la-plus", RollingFormView.class),
//
//                new MenuItemInfo("Форма вальцевания", "la la-plus", RollingDryingFormView.class),
//
//                new MenuItemInfo("Лаборант", "la la-user", SelectingView.class),
//
//                new MenuItemInfo("Форма выборки", "la la-plus", SelectingFormView.class),


                //new MenuItemInfo("Форма добавления заказа","la la-plus", CorrectionOrderFormView.class),

        };
        List<RouterLink> links = new ArrayList<>();
        for (MenuItemInfo menuItemInfo : menuItems) {
            if (accessChecker.hasAccess(menuItemInfo.getView())) {
                links.add(createLink(menuItemInfo));
            }

        }
        return links;
    }

    private static RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "h-m", "items-center", "px-s", "relative", "text-secondary");
        link.setRoute(menuItemInfo.getView());

        Span icon = new Span();
        icon.addClassNames("me-s", "text-l");
        if (!menuItemInfo.getIconClass().isEmpty()) {
            icon.addClassNames(menuItemInfo.getIconClass());
        }

        Span text = new Span(menuItemInfo.getText());
        text.addClassNames("font-medium", "text-s", "whitespace-nowrap");

        link.add(icon, text);
        return link;
    }

}
