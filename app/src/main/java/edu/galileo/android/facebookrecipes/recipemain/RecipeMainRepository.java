package edu.galileo.android.facebookrecipes.recipemain;

import edu.galileo.android.facebookrecipes.entities.Recipe;

/**
 * Created by avalo.
 */
public interface RecipeMainRepository {
    int COUNT = 1;
    String RECENT_SORT = "r";
    int RECIPE_RANGE = 100000;

    void getNextRecipe();
    void saveRecipe(Recipe recipe);
    void setRecipePage(int recipePage);
}
