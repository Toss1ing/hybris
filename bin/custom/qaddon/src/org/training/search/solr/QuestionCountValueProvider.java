package org.training.search.solr;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;

public class QuestionCountValueProvider extends AbstractValueResolver<ProductModel, Object, Object> {

    @Override
    protected void addFieldValues(
            InputDocument inputDocument, IndexerBatchContext indexerBatchContext,
            IndexedProperty indexedProperty, ProductModel productModel,
            ValueResolverContext<Object, Object> valueResolverContext
    ) throws FieldValueProviderException {
        int count = 0;
        if (productModel.getQuestions() != null) {
            count = (int) productModel.getQuestions().stream()
                    .filter(q -> Boolean.TRUE.equals(q.getApproved()))
                    .count();
        }
        inputDocument.addField(indexedProperty, count, valueResolverContext.getFieldQualifier());
    }

}
