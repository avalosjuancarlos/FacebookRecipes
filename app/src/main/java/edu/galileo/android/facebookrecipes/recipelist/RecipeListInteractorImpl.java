package edu.galileo.android.facebookrecipes.recipelist;

/**
 * Created by avalo.
 */
public class RecipeListInteractorImpl implements RecipeListInteractor {
    private RecipeListRepository repository;

    public RecipeListInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getSavedRecipes();
    }

    @Override
    public void searchFavs() {
        repository.getFavoritesRecipes();
    }
}
