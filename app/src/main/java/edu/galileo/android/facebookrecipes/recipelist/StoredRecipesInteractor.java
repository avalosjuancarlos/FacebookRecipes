package edu.galileo.android.facebookrecipes.recipelist;

import edu.galileo.android.facebookrecipes.entities.Recipe;

/**
 * Created by avalo.
 */
public interface StoredRecipesInteractor {
    void executeUpdate(Recipe recipe);
    void executeDelete(Recipe recipe);
}
