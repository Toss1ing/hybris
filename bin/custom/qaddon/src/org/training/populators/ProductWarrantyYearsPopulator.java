package org.training.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.training.model.WarrantyYearsModel;

public class ProductWarrantyYearsPopulator implements Populator<ProductModel, ProductData> {

    @Override
    public void populate(ProductModel source, ProductData target) throws ConversionException {
        if (source != null && source.getWarrantyYears() != null) {
            WarrantyYearsModel warrantyYearsModel = source.getWarrantyYears();
            if (warrantyYearsModel.getYears() != null) {
                target.setWarrantyYears(warrantyYearsModel.getYears());
            }
        }
    }
}

