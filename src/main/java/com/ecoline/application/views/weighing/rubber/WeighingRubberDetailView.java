package com.ecoline.application.views.weighing.rubber;

import com.ecoline.application.security.AuthenticatedUser;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.annotation.security.RolesAllowed;

//@PageTitle("Список навесок")
//@Route(value = "weighing-detail-rubber", layout = MainLayout.class)
//@RolesAllowed({"admin","user","weigher_rubber"})
//public class WeighingRubberDetailView extends Div {
//
//    private Grid<Portion> grid = new Grid<>(Portion.class, false);
//
//    private PortionService portionService;
//
//    private AuthenticatedUser authenticatedUser;
//
//    public WeighingRubberDetailView(@Autowired PortionService portionService) {
//        this.portionService = portionService;
//        addClassNames("master-detail-view", "flex", "flex-col", "h-full");
//
//        SplitLayout splitLayout = new SplitLayout();
//        splitLayout.setSizeFull();
//
//        createGridLayout(splitLayout);
//
//        add(splitLayout);
//
//        grid.addColumn("id").setAutoWidth(true).setHeader("Id");
//        grid.addColumn("component").setAutoWidth(true).setHeader("Компонент");
//        grid.addColumn("weight").setAutoWidth(true).setHeader("Вес (г)");
//        grid.addColumn("respUsername").setAutoWidth(true).setHeader("Ответственный");
//
//        String currentUser = VaadinSession.getCurrent().getAttribute("username").toString();
//        if(currentUser.equals("admin"))
//            grid.setItems(query -> portionService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
//        else{
//            grid.setItems(portionService.listWithUsername(currentUser));
//        }
//
//        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
//        grid.setHeightFull();
//
//    }
//
//
//
//    private void createGridLayout(SplitLayout splitLayout) {
//        Div wrapper = new Div();
//        wrapper.setId("grid-wrapper");
//        wrapper.setWidthFull();
//        splitLayout.addToPrimary(wrapper);
//        wrapper.add(grid);
//    }
//
//    private void refreshGrid() {
//        grid.select(null);
//        grid.getLazyDataView().refreshAll();
//    }
//
//}
