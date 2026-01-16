package org.training.controllers.cms;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.platform.commercefacades.product.data.ProductData;
import org.training.model.component.QuestionsCMSComponentModel;
import questions.data.QuestionData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller("QuestionsCMSComponentController")
@RequestMapping(value = "/view/QuestionsCMSComponentController")
public class QuestionCMSComponentController extends AbstractCMSAddOnComponentController<QuestionsCMSComponentModel> {

    private static final String PRODUCT_ATTRIBUTE = "product";
    private static final String QUESTIONS_ATTRIBUTE = "questions";
    private static final String NUMBER_OF_QUESTIONS_TO_SHOW_ATTRIBUTE = "numberOfQuestionsToShow";
    private static final String FONT_SIZE_ATTRIBUTE = "fontSize";

    @Override
    protected void fillModel(HttpServletRequest request, Model model, QuestionsCMSComponentModel component) {
        ProductData productData = (ProductData) request.getAttribute(PRODUCT_ATTRIBUTE);
        int numberOfQuestionsToShow = component.getNumberOfQuestionsToShow() != null ?
                component.getNumberOfQuestionsToShow() : 3;
        
        if (productData != null) {
            if (CollectionUtils.isNotEmpty(productData.getQuestions())) {
                List<QuestionData> questions = productData.getQuestions().stream()
                        .limit(numberOfQuestionsToShow)
                        .collect(Collectors.toList());
                model.addAttribute(QUESTIONS_ATTRIBUTE, questions);
            }
        }
        
        model.addAttribute(NUMBER_OF_QUESTIONS_TO_SHOW_ATTRIBUTE, numberOfQuestionsToShow);
        model.addAttribute(FONT_SIZE_ATTRIBUTE, component.getFont() != null ? component.getFont() : 10);
    }

    @Override
    protected String getView(QuestionsCMSComponentModel component) {
        return super.getView(component);
    }
}
