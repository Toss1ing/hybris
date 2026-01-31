package org.training.facades.product.impl;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import org.training.facades.product.QuestionsProductFacade;
import org.training.facades.product.QuestionsProductOption;

import java.util.Collection;

public class DefaultQuestionsProductFacade implements QuestionsProductFacade {

    private final ConfigurablePopulator<ProductModel, ProductData, QuestionsProductOption> productConfiguredPopulator;

    public DefaultQuestionsProductFacade(
            ConfigurablePopulator<ProductModel, ProductData, QuestionsProductOption> productConfiguredPopulator
    ) {
        this.productConfiguredPopulator = productConfiguredPopulator;
    }

    @Override
    public void populateProductData(
            ProductModel productModel,
            ProductData productData,
            Collection<QuestionsProductOption> options
    ) {
        if (productModel == null || productData == null || options == null) {
            return;
        }

        productConfiguredPopulator.populate(productModel, productData, options);
    }

}

