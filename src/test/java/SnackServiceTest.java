import com.koiti.snackbar.domain.Ingredient;
import com.koiti.snackbar.domain.Snack;
import com.koiti.snackbar.repository.SnackRepository;
import com.koiti.snackbar.service.IngredientService;
import com.koiti.snackbar.service.SnackService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class SnackServiceTest {
    @InjectMocks
    SnackService service;

    @Mock
    SnackRepository snackRepository;

    @Mock
    IngredientService ingredientService;

    Ingredient ingredient1 = Ingredient.builder().name("Bacon").value(2.0).build();
    Ingredient ingredient2 = Ingredient.builder().name("Ovo").value(0.8).build();
    Ingredient ingredient3 = Ingredient.builder().name("Queijo").value(1.5).build();
    Ingredient ingredient4 = Ingredient.builder().name("Hamburguer de carne").value(3.0).build();
    Ingredient ingredient5 = Ingredient.builder().name("Alface").value(0.4).build();

    Snack snack1 = Snack.builder().name("X-Bacon").ingredients(Arrays.asList(ingredient1,ingredient2,ingredient3)).build();

    @Before
    public void setup(){
        when(ingredientService.getIngredients()).thenReturn(Arrays.asList(ingredient1,ingredient2,ingredient3,ingredient4,ingredient5));
        when(ingredientService.getIngredientValue(ingredient4.getName(), Arrays.asList(ingredient1,ingredient2,ingredient3,ingredient4,ingredient5))).thenReturn(ingredient4.getValue());
        when(ingredientService.getIngredientValue(ingredient3.getName(), Arrays.asList(ingredient1,ingredient2,ingredient3,ingredient4,ingredient5))).thenReturn(ingredient3.getValue());
        when(ingredientService.getIngredientValue(ingredient5.getName(), Arrays.asList(ingredient1,ingredient2,ingredient3,ingredient4,ingredient5))).thenReturn(ingredient5.getValue());
    }

    @Test
    public void getSnacksValueTest() {
        when(snackRepository.getSnacks()).thenReturn(Arrays.asList(snack1));

        Double finalValue = service.getSnacksValue(Arrays.asList("X-Bacon"), Arrays.asList("1"));
        assertEquals(4.3, finalValue, 0.0001);
    }

    @Test
    public void getSnacksValueMeatPromotionTest() {
        Double finalValue = service.getCustomSnacksValue(Arrays.asList("Hamburguer de carne","Bacon"), Arrays.asList("3","1"));
        assertEquals(8.0, finalValue, 0.0001);
    }

    @Test
    public void getSnacksValueCheesePromotionTest() {
        Double finalValue = service.getCustomSnacksValue(Arrays.asList("Queijo","Bacon"), Arrays.asList("3","1"));
        assertEquals(5.0, finalValue, 0.0001);
    }

    @Test
    public void getSnacksValueLightPromotionTest() {
        Double finalValue = service.getCustomSnacksValue(Arrays.asList("Queijo","Alface"), Arrays.asList("1","1"));
        assertEquals(1.71, finalValue, 0.0001);
    }
}
