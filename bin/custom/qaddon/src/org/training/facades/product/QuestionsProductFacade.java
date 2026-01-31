package org.training.facades.product;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.Collection;

public interface QuestionsProductFacade {
    void populateProductData(ProductModel productModel, ProductData productData,
                             Collection<QuestionsProductOption> options);
}

