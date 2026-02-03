package org.training.facades.product.impl;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import org.training.facades.product.QuestionsProductFacade;
import org.training.facades.product.QuestionsProductOption;
import questions.data.QuestionData;

import java.util.Collection;
import java.util.List;

public class DefaultQuestionsProductFacade implements QuestionsProductFacade {

    private final ConfigurablePopulator<ProductModel, ProductData, QuestionsProductOption> productConfiguredPopulator;
    private final ProductService productService;

    public DefaultQuestionsProductFacade(
            ConfigurablePopulator<ProductModel, ProductData, QuestionsProductOption> productConfiguredPopulator,
            ProductService productService
    ) {
        this.productConfiguredPopulator = productConfiguredPopulator;
        this.productService = productService;
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

    @Override
    public List<QuestionData> getQuestionsForProduct(String productCode) {

        if (productCode == null || productCode.isBlank()) {
            return List.of();
        }

        ProductModel productModel = productService.getProductForCode(productCode);

        ProductData tmp = new ProductData();
        tmp.setCode(productCode);

        productConfiguredPopulator.populate(productModel, tmp, List.of(QuestionsProductOption.QUESTIONS));

        List<QuestionData> questions = tmp.getQuestions();
        if (questions == null || questions.isEmpty()) {
            return List.of();
        }

        return questions;

    }

}

