package org.training.populators;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.training.model.QuestionModel;
import questions.data.QuestionData;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class QuestionPopulatorUnitTest {

    @Mock
    private Converter<QuestionModel, QuestionData> questionConverter;

    @Mock
    private ProductModel productModel;

    private ProductQuestionsPopulator productQuestionsPopulator;
    private ProductData productData;

    @Before
    public void setUp() {
        productQuestionsPopulator = new ProductQuestionsPopulator(questionConverter);
        productData = new ProductData();
    }

    @Test
    public void shouldNotSetQuestionsWhenSourceIsEmpty() {
        when(productModel.getQuestions()).thenReturn(Collections.emptyList());

        productQuestionsPopulator.populate(productModel, productData);

        assertNull(productData.getQuestions());
    }

    @Test
    public void shouldPopulateQuestionsWhenSourceIsNotEmpty() {
        QuestionModel questionModel1 = new QuestionModel();
        QuestionModel questionModel2 = new QuestionModel();

        QuestionData questionData1 = new QuestionData();
        QuestionData questionData2 = new QuestionData();

        when(productModel.getQuestions()).thenReturn(Arrays.asList(
                questionModel1,
                questionModel2)
        );
        when(questionConverter.convert(questionModel1)).thenReturn(questionData1);
        when(questionConverter.convert(questionModel2)).thenReturn(questionData2);

        productQuestionsPopulator.populate(productModel, productData);

        assertNotNull(productData.getQuestions());
        assertEquals(2, productData.getQuestions().size());
        assertSame(questionData1, productData.getQuestions().get(0));
        assertSame(questionData2, productData.getQuestions().get(1));

    }
}
