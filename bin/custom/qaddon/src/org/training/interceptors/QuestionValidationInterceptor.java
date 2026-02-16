package org.training.interceptors;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;
import org.apache.commons.lang.StringUtils;
import org.training.model.QuestionModel;

public class QuestionValidationInterceptor implements ValidateInterceptor<QuestionModel> {

    @Override
    public void onValidate(QuestionModel questionModel, InterceptorContext interceptorContext) throws InterceptorException {
        if (StringUtils.isBlank(questionModel.getQuestion())) {
            throw new InterceptorException("Question field is mandatory");
        }
        if (questionModel.getQuestionCustomer() == null) {
            throw new InterceptorException("Question Customer field is mandatory");
        }
    }

}

