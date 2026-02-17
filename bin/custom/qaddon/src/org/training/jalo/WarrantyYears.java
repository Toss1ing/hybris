package org.training.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import org.apache.log4j.Logger;

public class WarrantyYears extends GeneratedWarrantyYears
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger( WarrantyYears.class.getName() );
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		final Item item = super.createItem( ctx, type, allAttributes );
		return item;
	}
	
}

