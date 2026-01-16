package org.training.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.training.model.QuestionModel;
import questions.data.QuestionData;

import java.util.ArrayList;
import java.util.List;

public class ProductQuestionsPopulator implements Populator<ProductModel, ProductData> {

    private final Converter<QuestionModel, QuestionData> questionConverter;

    protected Converter<QuestionModel, QuestionData> getQuestionConverter() {
        return questionConverter;
    }

    public ProductQuestionsPopulator(Converter<QuestionModel, QuestionData> questionConverter) {
        this.questionConverter = questionConverter;
    }

    @Override
    public void populate(ProductModel source, ProductData target) throws ConversionException {
        if (CollectionUtils.isNotEmpty(source.getQuestions())) {
            List<QuestionData> questions = new ArrayList<>();
            for (QuestionModel questionModel : source.getQuestions()) {
                questions.add(getQuestionConverter().convert(questionModel));
            }
            target.setQuestions(questions);
        }
    }
}

