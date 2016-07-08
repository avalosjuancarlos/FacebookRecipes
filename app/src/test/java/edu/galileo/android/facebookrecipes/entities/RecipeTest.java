package edu.galileo.android.facebookrecipes.entities;

import org.junit.Test;

import edu.galileo.android.facebookrecipes.BaseTest;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by avalo.
 */
public class RecipeTest extends BaseTest {

    private final static String RECIPE_ID = "recipeId";
    private final static String TITLE = "title";
    private final static String IMAGE_URL = "imageUrl";
    private final static String SOURCE_URL = "sourceUrl";
    private final static boolean FAVORITE = false;

    private Recipe recipe;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        recipe = new Recipe();
    }

    @Test
    public void testGettersAndSetters() throws Exception {
        recipe.setRecipeId(RECIPE_ID);
        recipe.setTitle(TITLE);
        recipe.setImageUrl(IMAGE_URL);
        recipe.setSourceUrl(SOURCE_URL);
        recipe.setFavorite(FAVORITE);

        assertThat(recipe.getRecipeId(), is(RECIPE_ID));
        assertThat(recipe.getTitle(), is(TITLE));
        assertThat(recipe.getImageUrl(), is(IMAGE_URL));
        assertThat(recipe.getSourceUrl(), is(SOURCE_URL));
        assertThat(recipe.getFavorite(), is(FAVORITE));
    }

    @Test
    public void testEquals_ShouldBeTRUE() throws Exception {
        recipe.setRecipeId(RECIPE_ID);

        Recipe other = new Recipe();
        other.setRecipeId(RECIPE_ID);

        assertTrue(recipe.equals(other));
    }

    @Test
    public void testNoEquals_ShouldBeFALE() throws Exception {
        recipe.setRecipeId(RECIPE_ID);

        Recipe other = new Recipe();
        other.setRecipeId("other");

        assertFalse(recipe.equals(other));
    }

    @Test
    public void testNoEquals_OtherObject() throws Exception {
        recipe.setRecipeId(RECIPE_ID);

        String other = RECIPE_ID;

        assertFalse(recipe.equals(other));
    }
}
