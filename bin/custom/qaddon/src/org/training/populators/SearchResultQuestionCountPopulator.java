package org.training.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class SearchResultQuestionCountPopulator implements Populator<SearchResultValueData, ProductData> {

    private static final String QUESTION_COUNT = "questionCount";

    @Override
    public void populate(SearchResultValueData source, ProductData target) throws ConversionException {
        if (source == null || target == null || source.getValues() == null) {
            return;
        }

        Object value = source.getValues().get(QUESTION_COUNT);
        if (value == null) {
            return;
        }

        if (value instanceof Number) {
            target.setQuestionCount(((Number) value).intValue());
        } else if (value instanceof String) {
            try {
                target.setQuestionCount(Integer.parseInt((String) value));
            } catch (final NumberFormatException ignored) {
                target.setQuestionCount(0);
            }
        }
    }
    
}