package org.training.facades.product.impl;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import org.training.facades.product.WarrantyProductFacade;
import org.training.facades.product.WarrantyProductOption;

import java.util.Collection;
import java.util.List;

public class DefaultWarrantyProductFacade implements WarrantyProductFacade {

    private final ConfigurablePopulator<ProductModel, ProductData, WarrantyProductOption> productConfiguredPopulator;
    private final ProductService productService;

    public DefaultWarrantyProductFacade(
            ConfigurablePopulator<ProductModel, ProductData, WarrantyProductOption> productConfiguredPopulator,
            ProductService productService
    ) {
        this.productConfiguredPopulator = productConfiguredPopulator;
        this.productService = productService;
    }

    @Override
    public Integer getWarrantyYearsForProduct(String productCode) {
        if (productCode == null || productCode.isBlank()) {
            return null;
        }

        ProductModel productModel = productService.getProductForCode(productCode);

        ProductData tmp = new ProductData();
        tmp.setCode(productCode);

        productConfiguredPopulator.populate(productModel, tmp, List.of(WarrantyProductOption.WARRANTY_YEARS));

        return tmp.getWarrantyYears();
    }

}

