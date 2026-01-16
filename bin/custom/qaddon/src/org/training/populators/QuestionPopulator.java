package org.training.populators;

import de.hybris.platform.commercefacades.user.data.PrincipalData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.training.model.QuestionModel;
import questions.data.QuestionData;

public class QuestionPopulator implements Populator<QuestionModel, QuestionData> {

    private final Converter<PrincipalModel, PrincipalData> principalConverter;

    protected Converter<PrincipalModel, PrincipalData> getPrincipalConverter() {
        return principalConverter;
    }

    public QuestionPopulator(final Converter<PrincipalModel, PrincipalData> principalConverter) {
        this.principalConverter = principalConverter;
    }

    @Override
    public void populate(QuestionModel source, QuestionData target) {
        target.setCode(source.getCode());
        target.setQuestion(source.getQuestion());
        target.setAnswer(source.getAnswer());

        if (source.getQuestionCustomer() != null) {
            target.setQuestionCustomer(getPrincipalConverter().convert(source.getQuestionCustomer()));
        }

        if (source.getAnswerCustomer() != null) {
            target.setAnswerCustomer(getPrincipalConverter().convert(source.getAnswerCustomer()));
        }
    }
}

