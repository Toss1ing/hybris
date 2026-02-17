package org.training.search.solr;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;
import org.training.model.WarrantyYearsModel;

public class WarrantyYearsValueProvider extends AbstractValueResolver<ProductModel, Object, Object> {

    @Override
    protected void addFieldValues(
            InputDocument inputDocument, IndexerBatchContext indexerBatchContext,
            IndexedProperty indexedProperty, ProductModel productModel,
            ValueResolverContext<Object, Object> valueResolverContext
    ) throws FieldValueProviderException {
        int warrantyYears = 0;
        if (productModel.getWarrantyYears() != null) {
            WarrantyYearsModel warrantyYearsModel = productModel.getWarrantyYears();
            if (warrantyYearsModel.getYears() != null) {
                warrantyYears = warrantyYearsModel.getYears();
            }
        }
        inputDocument.addField(indexedProperty, warrantyYears, valueResolverContext.getFieldQualifier());
    }

}

