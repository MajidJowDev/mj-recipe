package mjzguru.com.springframework.recipe.bootstrap;

import mjzguru.com.springframework.recipe.domain.*;
import mjzguru.com.springframework.recipe.repositories.CategoryRepository;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {

        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadRecipesData();
    }

    private void loadRecipesData(){

        //Set<Recipe> lstRecipes = new HashSet<>();

        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");
        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        Optional<UnitOfMeasure> uomTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> uomTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> uomPinch = unitOfMeasureRepository.findByDescription("Pinch");
        Optional<UnitOfMeasure> uomQty = unitOfMeasureRepository.findByDescription("Qty");
        Optional<UnitOfMeasure> uomPound = unitOfMeasureRepository.findByDescription("Pound");


        Recipe guacamole = new Recipe();
        guacamole.setDescription("How to Make the Best Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(10);
        guacamole.setServings(4);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setSource("Simply Recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections("The best guacamole keeps it simple: just ripe avocados, salt, a squeeze of lime, onions, chilis, cilantro, and some chopped tomato. Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade.");


//        americanCategoryOptional.get().getRecipes().add(guacamole);
//        mexicanCategoryOptional.get().getRecipes().add(guacamole);
        guacamole.getCategories().add(americanCategoryOptional.get());
        guacamole.getCategories().add(mexicanCategoryOptional.get());


        //2 ripe avocados
        Ingredient ingAvocado = new Ingredient();
        ingAvocado.setRecipe(guacamole);
        ingAvocado.setAmount(BigDecimal.valueOf(2));
        ingAvocado.setDescription("ripe avocados");
        ingAvocado.setUom(uomQty.get());
        guacamole.getIngredients().add(ingAvocado);

        //1/4 teaspoon salt, plus more to taste
        Ingredient ingSalt = new Ingredient();
        ingSalt.setRecipe(guacamole);
        ingSalt.setAmount(BigDecimal.valueOf(0.25));
        ingSalt.setDescription("salt, plus more to taste");
        ingSalt.setUom(uomTeaspoon.get());
        guacamole.getIngredients().add(ingSalt);

        //1 tablespoon fresh lime or lemon juice
        Ingredient ingLemon = new Ingredient();
        ingLemon.setRecipe(guacamole);
        ingLemon.setAmount(BigDecimal.valueOf(1));
        ingLemon.setDescription("fresh lime or lemon juice");
        ingLemon.setUom(uomTablespoon.get());
        guacamole.getIngredients().add(ingLemon);

        //2-4 tablespoons minced red onion or thinly sliced green onion
        Ingredient ingOnion = new Ingredient();
        ingOnion.setRecipe(guacamole);
        ingOnion.setAmount(BigDecimal.valueOf(4));
        ingOnion.setDescription("minced red onion or thinly sliced green onion");
        ingOnion.setUom(uomTablespoon.get());
        guacamole.getIngredients().add(ingOnion);

        //1-2 serrano (or jalapeño) chilis, stems and seeds removed, minced
        Ingredient ingSerrano = new Ingredient();
        ingSerrano.setRecipe(guacamole);
        ingSerrano.setAmount(BigDecimal.valueOf(2));
        ingSerrano.setDescription("serrano (or jalapeño) chilis, stems and seeds removed, minced");
        ingSerrano.setUom(uomQty.get());
        guacamole.getIngredients().add(ingSerrano);

        //2 tablespoons cilantro (leaves and tender stems), finely chopped
        Ingredient ingCilantro = new Ingredient();
        ingCilantro.setRecipe(guacamole);
        ingCilantro.setAmount(BigDecimal.valueOf(2));
        ingCilantro.setDescription("cilantro (leaves and tender stems), finely chopped");
        ingCilantro.setUom(uomTablespoon.get());
        guacamole.getIngredients().add(ingCilantro);

        //Pinch freshly ground black pepper
        Ingredient ingBlackPepper = new Ingredient();
        ingBlackPepper.setRecipe(guacamole);
        ingBlackPepper.setAmount(BigDecimal.valueOf(1));
        ingBlackPepper.setDescription("freshly ground black pepper");
        ingBlackPepper.setUom(uomPinch.get());
        guacamole.getIngredients().add(ingBlackPepper);

        //1/2 ripe tomato, chopped (optional)
        Ingredient ingTomato = new Ingredient();
        ingTomato.setRecipe(guacamole);
        ingTomato.setAmount(BigDecimal.valueOf(0.5));
        ingTomato.setDescription("ripe tomato, chopped (optional)");
        ingTomato.setUom(uomQty.get());
        guacamole.getIngredients().add(ingTomato);

        //Red radish or jicama slices for garnish (optional)
        Ingredient ingRadish = new Ingredient();
        ingRadish.setRecipe(guacamole);
        ingRadish.setAmount(BigDecimal.valueOf(1));
        ingRadish.setDescription("Red radish or jicama slices for garnish (optional)");
        ingRadish.setUom(uomQty.get());
        guacamole.getIngredients().add(ingRadish);

        //Tortilla chips, to serve
        Ingredient ingTortilla = new Ingredient();
        ingTortilla.setRecipe(guacamole);
        ingTortilla.setAmount(BigDecimal.valueOf(0.2));
        ingTortilla.setDescription("Tortilla chips, to serve");
        ingTortilla.setUom(uomPound.get());
        guacamole.getIngredients().add(ingTortilla);

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        guacamoleNotes.setRecipe(guacamole);
        guacamole.setNotes(guacamoleNotes);

        recipeRepository.save(guacamole);

        //lstRecipes.add(guacamole);


        Recipe spicyGrilledChicken = new Recipe();
        spicyGrilledChicken.setDescription("Spicy Grilled Chicken Tacos");
        spicyGrilledChicken.setPrepTime(20);
        spicyGrilledChicken.setCookTime(15);
        spicyGrilledChicken.setServings(6);
        spicyGrilledChicken.setDifficulty(Difficulty.MODERATE);
        spicyGrilledChicken.setSource("Simply Recipes");
        spicyGrilledChicken.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyGrilledChicken.setDirections("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");

        spicyGrilledChicken.getCategories().add(americanCategoryOptional.get());
        spicyGrilledChicken.getCategories().add(italianCategoryOptional.get());

        // 2 tablespoons ancho chili powder
        Ingredient chicken_Ancho = new Ingredient();
        chicken_Ancho.setRecipe(spicyGrilledChicken);
        chicken_Ancho.setAmount(BigDecimal.valueOf(2));
        chicken_Ancho.setDescription("ancho chili powder");
        chicken_Ancho.setUom(uomTablespoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Ancho);

        //1 teaspoon dried oregano
        Ingredient chicken_Oregano = new Ingredient();
        chicken_Oregano.setRecipe(spicyGrilledChicken);
        chicken_Oregano.setAmount(BigDecimal.valueOf(1));
        chicken_Oregano.setDescription("dried oregano");
        chicken_Oregano.setUom(uomTeaspoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Oregano);

        //1 teaspoon dried cumin
        Ingredient chicken_Cumin = new Ingredient();
        chicken_Cumin.setRecipe(spicyGrilledChicken);
        chicken_Cumin.setAmount(BigDecimal.valueOf(1));
        chicken_Cumin.setDescription("dried cumin");
        chicken_Cumin.setUom(uomTeaspoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Cumin);

        //1 teaspoon sugar
        Ingredient chicken_Sugar = new Ingredient();
        chicken_Sugar.setRecipe(spicyGrilledChicken);
        chicken_Sugar.setAmount(BigDecimal.valueOf(1));
        chicken_Sugar.setDescription("sugar");
        chicken_Sugar.setUom(uomTeaspoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Sugar);

        //1/2 teaspoon salt
        Ingredient chicken_Salt = new Ingredient();
        chicken_Salt.setRecipe(spicyGrilledChicken);
        chicken_Salt.setAmount(BigDecimal.valueOf(0.5));
        chicken_Salt.setDescription("salt");
        chicken_Salt.setUom(uomTeaspoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Salt);

        //1 clove garlic, finely chopped
        Ingredient chicken_Garlic = new Ingredient();
        chicken_Garlic.setRecipe(spicyGrilledChicken);
        chicken_Garlic.setAmount(BigDecimal.valueOf(1));
        chicken_Garlic.setDescription("clove garlic, finely chopped");
        chicken_Garlic.setUom(uomQty.get());
        spicyGrilledChicken.getIngredients().add(chicken_Garlic);

        //1 tablespoon finely grated orange zest
        Ingredient chicken_Orange = new Ingredient();
        chicken_Orange.setRecipe(spicyGrilledChicken);
        chicken_Orange.setAmount(BigDecimal.valueOf(1));
        chicken_Orange.setDescription("finely grated orange zest");
        chicken_Orange.setUom(uomTablespoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Orange);

        //3 tablespoons fresh-squeezed orange juice
        Ingredient chicken_OrangeJucie = new Ingredient();
        chicken_OrangeJucie.setRecipe(spicyGrilledChicken);
        chicken_OrangeJucie.setAmount(BigDecimal.valueOf(3));
        chicken_OrangeJucie.setDescription("fresh-squeezed orange juice");
        chicken_OrangeJucie.setUom(uomTablespoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_OrangeJucie);

        //2 tablespoons olive oil
        Ingredient chicken_OliveOil = new Ingredient();
        chicken_OliveOil.setRecipe(spicyGrilledChicken);
        chicken_OliveOil.setAmount(BigDecimal.valueOf(2));
        chicken_OliveOil.setDescription("olive oil");
        chicken_OliveOil.setUom(uomTablespoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_OliveOil);

        //4 to 6 skinless, boneless chicken thighs (1 1/4 pounds)
        Ingredient chicken_ChickenThighs = new Ingredient();
        chicken_ChickenThighs.setRecipe(spicyGrilledChicken);
        chicken_ChickenThighs.setAmount(BigDecimal.valueOf(1.25));
        chicken_ChickenThighs.setDescription("skinless, boneless chicken thighs");
        chicken_ChickenThighs.setUom(uomPound.get());
        spicyGrilledChicken.getIngredients().add(chicken_ChickenThighs);

        Notes spicyChickenNotes = new Notes();
        spicyChickenNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        spicyChickenNotes.setRecipe(spicyGrilledChicken);
        spicyGrilledChicken.setNotes(spicyChickenNotes);

        recipeRepository.save(spicyGrilledChicken);


    }
}
