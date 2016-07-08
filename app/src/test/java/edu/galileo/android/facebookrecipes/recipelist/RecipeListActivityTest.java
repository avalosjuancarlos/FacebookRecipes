package edu.galileo.android.facebookrecipes.recipelist;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.facebook.FacebookActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.internal.runtime.RuntimeAdapter;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import java.util.List;

import edu.galileo.android.facebookrecipes.BaseTest;
import edu.galileo.android.facebookrecipes.BuildConfig;
import edu.galileo.android.facebookrecipes.FacebookRecipesApp;
import edu.galileo.android.facebookrecipes.R;
import edu.galileo.android.facebookrecipes.entities.Recipe;
import edu.galileo.android.facebookrecipes.libs.base.ImageLoader;
import edu.galileo.android.facebookrecipes.login.ui.LoginActivity;
import edu.galileo.android.facebookrecipes.recipelist.ui.RecipeListActivity;
import edu.galileo.android.facebookrecipes.recipelist.ui.RecipeListView;
import edu.galileo.android.facebookrecipes.recipelist.ui.adapters.OnItemClickListener;
import edu.galileo.android.facebookrecipes.recipelist.ui.adapters.RecipesAdapter;
import edu.galileo.android.facebookrecipes.recipemain.ui.RecipeMainActivity;
import edu.galileo.android.facebookrecipes.support.ShadowRecyclerView;
import edu.galileo.android.facebookrecipes.support.ShadowRecyclerViewAdapter;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by avalo.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,
        shadows = {ShadowRecyclerView.class, ShadowRecyclerViewAdapter.class})
public class RecipeListActivityTest extends BaseTest {
    @Mock
    private Recipe recipe;
    @Mock
    private List<Recipe> recipeList;
    @Mock
    private ImageLoader imageLoader;
    @Mock
    private RecipesAdapter adapter;
    @Mock
    private RecipeListPresenter presenter;

    private RecipeListView view;
    private RecipeListActivity activity;
    private OnItemClickListener onItemClickListener;

    private ShadowRecyclerViewAdapter shadowAdapter;
    private ShadowActivity shadowActivity;
    private ActivityController<RecipeListActivity> controller;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RecipeListActivity recipeListActivity = new RecipeListActivity(){
            @Override
            public void setTheme(@StyleRes int resid) {
                super.setTheme(R.style.AppTheme_NoActionBar);
            }

            public RecipesAdapter getAdapter(){
                return adapter;
            }

            public RecipeListPresenter getPresenter(){
                return presenter;
            }
        };

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), recipeListActivity).create().visible();
        activity = controller.get();
        view = activity;
        onItemClickListener = activity;

        shadowActivity = shadowOf(activity);
    }

    @Test
    public void testOnCreate_ShouldCallPresenter() throws Exception {
        verify(presenter).onCreate();
        verify(presenter).getRecipes();
    }

    @Test
    public void testOnDestroy_ShouldCallPresenter() throws Exception {
        controller.destroy();
        verify(presenter).onDestroy();
    }

    @Test
    public void testLogoutMenuClicked_ShouldLaunchLoginActivity() throws Exception {
        shadowActivity.clickMenuItem(R.id.action_logout);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, LoginActivity.class), intent.getComponent());
    }

    @Test
    public void testMainMenuClicked_ShouldLaunchMainActivity() throws Exception {
        shadowActivity.clickMenuItem(R.id.action_main);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, RecipeMainActivity.class), intent.getComponent());
    }

    @Test
    public void testSetRecipes_ShouldSetInAdapter() throws Exception {
        view.setRecipes(recipeList);
        verify(adapter).setRecipes(recipeList);
    }

    @Test
    public void testRecipeUpdated_ShouldSetInAdapter() throws Exception {
        view.recipeUpdated();
        verify(adapter).notifyDataSetChanged();
    }

    @Test
    public void testRecipeDeleted_ShouldSetInAdapter() throws Exception {
        view.recipeDeleted(recipe);
        verify(adapter).removeRecipe(recipe);
    }

    @Test
    public void testOnRecyclerViewScroll_ShouldChangeScrollPosition() throws Exception {
        int scrollPosition = 1;

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        ShadowRecyclerView shadowRecyclerView = (ShadowRecyclerView) ShadowExtractor.extract(recyclerView);

        recyclerView.smoothScrollToPosition(scrollPosition);
        assertEquals(scrollPosition, shadowRecyclerView.getSmoothScrollToPosition());
    }

    @Test
    public void testOnToolbarClicked_RecyclerViewShouldScrollToTop() throws Exception {
        int scrollPosition = 1;
        int topScrollPosition = 0;
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        ShadowRecyclerView shadowRecyclerView = (ShadowRecyclerView) ShadowExtractor.extract(recyclerView);
        recyclerView.smoothScrollToPosition(scrollPosition);

        toolbar.performClick();

        assertEquals(topScrollPosition, shadowRecyclerView.getSmoothScrollToPosition());
    }

    @Test
    public void testRecyclerViewItemClicked_ShouldStartViewActivity() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClick(positionToClick);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(Intent.ACTION_VIEW, intent.getAction());
        assertEquals(recipeList.get(positionToClick).getSourceUrl(), intent.getDataString());
    }

    @Test
    public void testRecyclerViewFavoriteClicked_ShouldCallPresenter() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClickOverViewInHolder(positionToClick, R.id.imgFav);

        verify(presenter).toggleFavorite(recipe);
    }

    @Test
    public void testRecyclerViewRemoveClicked_ShouldCallPresenter() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClickOverViewInHolder(positionToClick, R.id.imgDelete);

        verify(presenter).removeRecipe(recipe);
    }

    @Test
    public void testRecyclerViewFBShareClick_ShouldStartFBActivity() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClickOverViewInHolder(positionToClick, R.id.fbShare);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(RuntimeEnvironment.application, FacebookActivity.class), intent.getComponent());
    }

    @Test
    public void testRecyclerViewFBSendClick_ShouldStartFBActivity() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClickOverViewInHolder(positionToClick, R.id.fbSend);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(RuntimeEnvironment.application, FacebookActivity.class), intent.getComponent());
    }

    private void setUpShadowAdapter(int positionToClick){
        when(recipe.getSourceUrl()).thenReturn("http://galileo.edu");
        when(recipeList.get(positionToClick)).thenReturn(recipe);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        RecipesAdapter adapterPopulated = new RecipesAdapter(recipeList, imageLoader, onItemClickListener);
        recyclerView.setAdapter(adapterPopulated);
        shadowAdapter = (ShadowRecyclerViewAdapter) ShadowExtractor.extract(recyclerView.getAdapter());
    }
}
