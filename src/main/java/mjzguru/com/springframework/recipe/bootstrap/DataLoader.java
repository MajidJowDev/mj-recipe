package mjzguru.com.springframework.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import mjzguru.com.springframework.recipe.domain.*;
import mjzguru.com.springframework.recipe.repositories.CategoryRepository;
import mjzguru.com.springframework.recipe.repositories.RecipeRepository;
import mjzguru.com.springframework.recipe.repositories.UnitOfMeasureRepository;
import mjzguru.com.springframework.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner, ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    UnitOfMeasureReactiveRepository reactiveRepository;

    public DataLoader(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {

        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    //My Method
    @Override
    public void run(String... args) throws Exception {
//        System.out.println("Data Loaded by Majid's Profile");
//        loadRecipesData();
    }

    //Guru approach
    @Override
    @Transactional // to avoid getting lazy initiation exception
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //System.out.println("Data Loaded by Guru's Profile");
        loadCategories();
        loadUom();
        log.info("Data loaded in DataLoader class by Guru Method!!!!");

        recipeRepository.saveAll(getRecipes());
        log.debug("getRecipes Method Called Successfully!!!!****");
        log.error("#######");
        // because the return type is Mono since this repository is a reactive type, so we need to use block() to get it started (we are using block temporarily)
        log.error("Reactive Repository Count: " + reactiveRepository.count().block().toString());

    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setDescription("Italian");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setDescription("Mexican");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");
        categoryRepository.save(cat4);
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");
        unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");
        unitOfMeasureRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");
        unitOfMeasureRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");
        unitOfMeasureRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");
        unitOfMeasureRepository.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");
        unitOfMeasureRepository.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");
        unitOfMeasureRepository.save(uom8);
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
        //ingAvocado.setRecipe(guacamole);
        ingAvocado.setAmount(BigDecimal.valueOf(2));
        ingAvocado.setDescription("ripe avocados");
        ingAvocado.setUom(uomQty.get());
        guacamole.getIngredients().add(ingAvocado);

        //1/4 teaspoon salt, plus more to taste
        Ingredient ingSalt = new Ingredient();
        //ingSalt.setRecipe(guacamole);
        ingSalt.setAmount(BigDecimal.valueOf(0.25));
        ingSalt.setDescription("salt, plus more to taste");
        ingSalt.setUom(uomTeaspoon.get());
        guacamole.getIngredients().add(ingSalt);

        //1 tablespoon fresh lime or lemon juice
        Ingredient ingLemon = new Ingredient();
        //ingLemon.setRecipe(guacamole);
        ingLemon.setAmount(BigDecimal.valueOf(1));
        ingLemon.setDescription("fresh lime or lemon juice");
        ingLemon.setUom(uomTablespoon.get());
        guacamole.getIngredients().add(ingLemon);

        //2-4 tablespoons minced red onion or thinly sliced green onion
        Ingredient ingOnion = new Ingredient();
        //ingOnion.setRecipe(guacamole);
        ingOnion.setAmount(BigDecimal.valueOf(4));
        ingOnion.setDescription("minced red onion or thinly sliced green onion");
        ingOnion.setUom(uomTablespoon.get());
        guacamole.getIngredients().add(ingOnion);

        //1-2 serrano (or jalapeño) chilis, stems and seeds removed, minced
        Ingredient ingSerrano = new Ingredient();
        //ingSerrano.setRecipe(guacamole);
        ingSerrano.setAmount(BigDecimal.valueOf(2));
        ingSerrano.setDescription("serrano (or jalapeño) chilis, stems and seeds removed, minced");
        ingSerrano.setUom(uomQty.get());
        guacamole.getIngredients().add(ingSerrano);

        //2 tablespoons cilantro (leaves and tender stems), finely chopped
        Ingredient ingCilantro = new Ingredient();
        //ingCilantro.setRecipe(guacamole);
        ingCilantro.setAmount(BigDecimal.valueOf(2));
        ingCilantro.setDescription("cilantro (leaves and tender stems), finely chopped");
        ingCilantro.setUom(uomTablespoon.get());
        guacamole.getIngredients().add(ingCilantro);

        //Pinch freshly ground black pepper
        Ingredient ingBlackPepper = new Ingredient();
        //ingBlackPepper.setRecipe(guacamole);
        ingBlackPepper.setAmount(BigDecimal.valueOf(1));
        ingBlackPepper.setDescription("freshly ground black pepper");
        ingBlackPepper.setUom(uomPinch.get());
        guacamole.getIngredients().add(ingBlackPepper);

        //1/2 ripe tomato, chopped (optional)
        Ingredient ingTomato = new Ingredient();
        //ingTomato.setRecipe(guacamole);
        ingTomato.setAmount(BigDecimal.valueOf(0.5));
        ingTomato.setDescription("ripe tomato, chopped (optional)");
        ingTomato.setUom(uomQty.get());
        guacamole.getIngredients().add(ingTomato);

        //Red radish or jicama slices for garnish (optional)
        Ingredient ingRadish = new Ingredient();
        //ingRadish.setRecipe(guacamole);
        ingRadish.setAmount(BigDecimal.valueOf(1));
        ingRadish.setDescription("Red radish or jicama slices for garnish (optional)");
        ingRadish.setUom(uomQty.get());
        guacamole.getIngredients().add(ingRadish);

        //Tortilla chips, to serve
        Ingredient ingTortilla = new Ingredient();
        //ingTortilla.setRecipe(guacamole);
        ingTortilla.setAmount(BigDecimal.valueOf(0.2));
        ingTortilla.setDescription("Tortilla chips, to serve");
        ingTortilla.setUom(uomPound.get());
        guacamole.getIngredients().add(ingTortilla);

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        //guacamoleNotes.setRecipe(guacamole);
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
        //chicken_Ancho.setRecipe(spicyGrilledChicken);
        chicken_Ancho.setAmount(BigDecimal.valueOf(2));
        chicken_Ancho.setDescription("ancho chili powder");
        chicken_Ancho.setUom(uomTablespoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Ancho);

        //1 teaspoon dried oregano
        Ingredient chicken_Oregano = new Ingredient();
        //chicken_Oregano.setRecipe(spicyGrilledChicken);
        chicken_Oregano.setAmount(BigDecimal.valueOf(1));
        chicken_Oregano.setDescription("dried oregano");
        chicken_Oregano.setUom(uomTeaspoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Oregano);

        //1 teaspoon dried cumin
        Ingredient chicken_Cumin = new Ingredient();
        //chicken_Cumin.setRecipe(spicyGrilledChicken);
        chicken_Cumin.setAmount(BigDecimal.valueOf(1));
        chicken_Cumin.setDescription("dried cumin");
        chicken_Cumin.setUom(uomTeaspoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Cumin);

        //1 teaspoon sugar
        Ingredient chicken_Sugar = new Ingredient();
        //chicken_Sugar.setRecipe(spicyGrilledChicken);
        chicken_Sugar.setAmount(BigDecimal.valueOf(1));
        chicken_Sugar.setDescription("sugar");
        chicken_Sugar.setUom(uomTeaspoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Sugar);

        //1/2 teaspoon salt
        Ingredient chicken_Salt = new Ingredient();
        //chicken_Salt.setRecipe(spicyGrilledChicken);
        chicken_Salt.setAmount(BigDecimal.valueOf(0.5));
        chicken_Salt.setDescription("salt");
        chicken_Salt.setUom(uomTeaspoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Salt);

        //1 clove garlic, finely chopped
        Ingredient chicken_Garlic = new Ingredient();
        //chicken_Garlic.setRecipe(spicyGrilledChicken);
        chicken_Garlic.setAmount(BigDecimal.valueOf(1));
        chicken_Garlic.setDescription("clove garlic, finely chopped");
        chicken_Garlic.setUom(uomQty.get());
        spicyGrilledChicken.getIngredients().add(chicken_Garlic);

        //1 tablespoon finely grated orange zest
        Ingredient chicken_Orange = new Ingredient();
        //chicken_Orange.setRecipe(spicyGrilledChicken);
        chicken_Orange.setAmount(BigDecimal.valueOf(1));
        chicken_Orange.setDescription("finely grated orange zest");
        chicken_Orange.setUom(uomTablespoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_Orange);

        //3 tablespoons fresh-squeezed orange juice
        Ingredient chicken_OrangeJucie = new Ingredient();
        //chicken_OrangeJucie.setRecipe(spicyGrilledChicken);
        chicken_OrangeJucie.setAmount(BigDecimal.valueOf(3));
        chicken_OrangeJucie.setDescription("fresh-squeezed orange juice");
        chicken_OrangeJucie.setUom(uomTablespoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_OrangeJucie);

        //2 tablespoons olive oil
        Ingredient chicken_OliveOil = new Ingredient();
        //chicken_OliveOil.setRecipe(spicyGrilledChicken);
        chicken_OliveOil.setAmount(BigDecimal.valueOf(2));
        chicken_OliveOil.setDescription("olive oil");
        chicken_OliveOil.setUom(uomTablespoon.get());
        spicyGrilledChicken.getIngredients().add(chicken_OliveOil);

        //4 to 6 skinless, boneless chicken thighs (1 1/4 pounds)
        Ingredient chicken_ChickenThighs = new Ingredient();
        //chicken_ChickenThighs.setRecipe(spicyGrilledChicken);
        chicken_ChickenThighs.setAmount(BigDecimal.valueOf(1.25));
        chicken_ChickenThighs.setDescription("skinless, boneless chicken thighs");
        chicken_ChickenThighs.setUom(uomPound.get());
        spicyGrilledChicken.getIngredients().add(chicken_ChickenThighs);

        Notes spicyChickenNotes = new Notes();
        spicyChickenNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        //spicyChickenNotes.setRecipe(spicyGrilledChicken);
        spicyGrilledChicken.setNotes(spicyChickenNotes);

        recipeRepository.save(spicyGrilledChicken);
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if(!cupsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teapoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();

        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        //Yummy Guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setUrl("http://www.simplyrecipes.com");
        guacRecipe.setSource("simplyrecipes");
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
       // guacNotes.setRecipe(guacRecipe); // removed because of setting in Recipe POJO
        guacRecipe.setNotes(guacNotes);

        /*
        guacRecipe.getIngredients().add(new Ingredient("ripe avocados", new BigDecimal(2), eachUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(".5"), teapoonUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom, guacRecipe));
        */

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teapoonUom));
        guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
        guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom));
        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        //add to return list
        recipes.add(guacRecipe);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        //tacoNotes.setRecipe(tacosRecipe);
        tacosRecipe.setNotes(tacoNotes);


        tacosRecipe.getIngredients().add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), teapoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), teapoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), teapoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Salt", new BigDecimal(".5"), teapoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom, tacosRecipe));
        tacosRecipe.getIngredients().add(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom, tacosRecipe));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(tacosRecipe);
        return recipes;
    }
}
