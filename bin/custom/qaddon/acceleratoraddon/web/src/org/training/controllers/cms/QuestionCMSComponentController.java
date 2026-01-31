package org.training.controllers.cms;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.platform.commercefacades.product.data.ProductData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.training.model.component.QuestionsCMSComponentModel;
import questions.data.QuestionData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller("QuestionsCMSComponentController")
@RequestMapping(value = "/view/QuestionsCMSComponentController")
public class QuestionCMSComponentController extends AbstractCMSAddOnComponentController<QuestionsCMSComponentModel> {

    private final static String PRODUCT_ATTRIBUTE_NAME = "product";

    private final static String QUESTION_NUMBER_TO_SHOW_ATTRIBUTE_NAME = "numberOfQuestionsToShow";
    private final static String QUESTION_ATTRIBUTE_NAME = "questions";
    private final static String QUESTION_FONT_SIZE_ATTRIBUTE_NAME = "questionFontSize";

    @Override
    protected void fillModel(
            HttpServletRequest request,
            Model model,
            QuestionsCMSComponentModel component) {

        ProductData productData = (ProductData) request.getAttribute(PRODUCT_ATTRIBUTE_NAME);
        int numberOfQuestionsToShow = component.getNumberOfQuestionsToShow() != null
                ? component.getNumberOfQuestionsToShow()
                : 3;

        if (productData != null && CollectionUtils.isNotEmpty(productData.getQuestions())) {
            List<QuestionData> questions = productData.getQuestions().stream()
                    .limit(numberOfQuestionsToShow)
                    .collect(Collectors.toList());
            model.addAttribute(QUESTION_ATTRIBUTE_NAME, questions);
        }

        model.addAttribute(QUESTION_NUMBER_TO_SHOW_ATTRIBUTE_NAME, numberOfQuestionsToShow);
        model.addAttribute(QUESTION_FONT_SIZE_ATTRIBUTE_NAME, component.getFont() != null
                ? component.getFont()
                : 10
        );
    }
}
