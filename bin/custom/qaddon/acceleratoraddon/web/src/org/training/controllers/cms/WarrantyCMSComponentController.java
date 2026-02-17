package org.training.controllers.cms;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.platform.commercefacades.product.data.ProductData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.training.facades.product.WarrantyProductFacade;
import org.training.model.component.WarrantyCMSComponentModel;

import javax.servlet.http.HttpServletRequest;

@Controller("WarrantyCMSComponentController")
@RequestMapping(value = "/view/WarrantyCMSComponentController")
public class WarrantyCMSComponentController extends
        AbstractCMSAddOnComponentController<WarrantyCMSComponentModel> {

    private final static String PRODUCT_ATTRIBUTE_NAME = "product";
    private final static String WARRANTY_YEARS_ATTRIBUTE_NAME = "warrantyYears";

    private final WarrantyProductFacade warrantyProductFacade;

    public WarrantyCMSComponentController(WarrantyProductFacade warrantyProductFacade) {
        this.warrantyProductFacade = warrantyProductFacade;
    }

    @Override
    protected void fillModel(
            HttpServletRequest request,
            Model model,
            WarrantyCMSComponentModel component
    ) {
        ProductData productData = (ProductData) request.getAttribute(PRODUCT_ATTRIBUTE_NAME);
        Integer warrantyYears = 0;

        if (productData != null && productData.getCode() != null) {
            if (productData.getWarrantyYears() != null) {
                warrantyYears = productData.getWarrantyYears();
            } else {
                Integer warrantyYearsFromFacade = warrantyProductFacade
                        .getWarrantyYearsForProduct(productData.getCode());
                if (warrantyYearsFromFacade != null) {
                    warrantyYears = warrantyYearsFromFacade;
                }
            }
        }

        model.addAttribute(WARRANTY_YEARS_ATTRIBUTE_NAME, warrantyYears);
    }
}
