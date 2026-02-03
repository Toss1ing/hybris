package org.training.facades.product;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import questions.data.QuestionData;

import java.util.Collection;
import java.util.List;

public interface QuestionsProductFacade {
    void populateProductData(ProductModel productModel, ProductData productData,
                             Collection<QuestionsProductOption> options);

    List<QuestionData> getQuestionsForProduct(String productCode);
}

