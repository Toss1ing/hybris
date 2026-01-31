package org.training.interceptors.beforeview;

import de.hybris.platform.addonsupport.interceptors.BeforeViewHandlerAdaptee;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import org.springframework.ui.ModelMap;
import org.training.facades.product.QuestionsProductFacade;
import org.training.facades.product.QuestionsProductOption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

public class QuestionsBeforeViewHandler implements BeforeViewHandlerAdaptee {

    private final static String PRODUCT_ATTRIBUTE_NAME = "product";

    private final QuestionsProductFacade questionsProductFacade;
    private final ProductService productService;

    public QuestionsBeforeViewHandler(final QuestionsProductFacade questionsProductFacade, final ProductService productService) {
        this.questionsProductFacade = questionsProductFacade;
        this.productService = productService;
    }

    @Override
    public String beforeView(
            HttpServletRequest request,
            HttpServletResponse response,
            ModelMap model,
            String viewName
    ) {
        if (model == null) {
            return viewName;
        }

        Object productObj = model.get(PRODUCT_ATTRIBUTE_NAME);
        if (!(productObj instanceof ProductData productData)) {
            return viewName;
        }

        if (productData.getCode() == null) {
            return viewName;
        }

        ProductModel productModel = productService.getProductForCode(productData.getCode());
        questionsProductFacade.populateProductData(productModel, productData,
                Collections.singletonList(QuestionsProductOption.QUESTIONS));
        request.setAttribute(PRODUCT_ATTRIBUTE_NAME, productData);
        return viewName;
    }

}

