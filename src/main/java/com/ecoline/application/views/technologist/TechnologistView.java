package com.ecoline.application.views.technologist;

import com.ecoline.application.data.service.RecipeService;
import com.ecoline.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Технолог")
@Route(value = "technologist", layout = MainLayout.class)
@RolesAllowed({"admin", "user", "technologist"})
public class TechnologistView extends VerticalLayout {

    private Button technologistOrderDetailButton;
    //private Button technologistOrderFormButton;
    private Button technologistRecipeFormButton;
    private Button technologistRecipeMasterDetailButton;
    //private Select<String> recipeSelect = new Select<>();

    public TechnologistView(RecipeService recipeService) {
        technologistOrderDetailButton = new Button("Просмотр заказов");
        //technologistOrderFormButton = new Button("форма заказа");
        //technologistRecipeFormButton = new Button("Создать рецепт");
        technologistRecipeMasterDetailButton = new Button("Редактировать/Создать рецепт");

        technologistOrderDetailButton.addClickListener(e -> UI.getCurrent().navigate(TechnologistOrderDetailView.class));
        technologistRecipeMasterDetailButton.addClickListener(e -> UI.getCurrent().navigate(TechnologistRecipeMasterDetailView.class));
        //technologistOrderFormButton.addClickListener(e -> UI.getCurrent().navigate(TechnologistOrderFormView.class));

//        try {
//            recipeSelect.setItems(recipeService.getAll().stream().map(Recipe::getRecipeStringIdentifier).collect(Collectors.toList()));
//        } catch (Exception ex){
//            Notification.show("Что то пошло не так");
//        }

        setMargin(true);
        setHorizontalComponentAlignment(Alignment.START, technologistOrderDetailButton, technologistRecipeMasterDetailButton);

        add(technologistOrderDetailButton, technologistRecipeMasterDetailButton);
    }

}