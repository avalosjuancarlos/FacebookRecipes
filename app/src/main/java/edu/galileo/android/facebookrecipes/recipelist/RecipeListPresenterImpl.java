package edu.galileo.android.facebookrecipes.recipelist;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.facebookrecipes.entities.Recipe;
import edu.galileo.android.facebookrecipes.libs.base.EventBus;
import edu.galileo.android.facebookrecipes.recipelist.events.RecipeListEvent;
import edu.galileo.android.facebookrecipes.recipelist.ui.RecipeListView;

/**
 * Created by avalo.
 */
public class RecipeListPresenterImpl implements RecipeListPresenter {
    private EventBus eventBus;
    private RecipeListView view;
    private RecipeListInteractor listIntereactor;
    private StoredRecipesInteractor storedInteractor;

    public RecipeListPresenterImpl(EventBus eventBus, RecipeListView view, RecipeListInteractor listIntereactor, StoredRecipesInteractor storedInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.listIntereactor = listIntereactor;
        this.storedInteractor = storedInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void getRecipes() {
        listIntereactor.execute();
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        storedInteractor.executeDelete(recipe);
    }

    @Override
    public void toggleFavorite(Recipe recipe) {
        boolean fav = recipe.getFavorite();
        recipe.setFavorite(!fav);
        storedInteractor.executeUpdate(recipe);
    }

    @Override
    @Subscribe
    public void onEventMainThread(RecipeListEvent event) {
        if(view!= null){
            switch (event.getType()){
                case RecipeListEvent.READ_EVENT:
                    view.setRecipes(event.getRecipeList());
                    break;
                case RecipeListEvent.UPDATE_EVENT:
                    view.recipeUpdated();
                    break;
                case RecipeListEvent.DELETE_EVENT:
                    Recipe recipe = event.getRecipeList().get(0);
                    view.recipeDeleted(recipe);
                    break;
            }
        }
    }

    @Override
    public void showAll() {
        listIntereactor.execute();
    }

    @Override
    public void showFavs() {
        listIntereactor.searchFavs();
    }

    @Override
    public RecipeListView getView() {
        return this.view;
    }
}
