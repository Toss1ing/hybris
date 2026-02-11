package org.training.jobs;

import de.hybris.platform.commerceservices.customer.CustomerService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import org.springframework.beans.factory.annotation.Value;
import org.training.model.QuestionModel;
import org.training.model.QuestionsEmailSendingCronJobModel;
import org.training.model.QuestionsEmailSendingProcessModel;
import org.training.services.QuestionService;

import java.util.*;

public class StartQuestionEmailingProcessJob extends AbstractJobPerformable<QuestionsEmailSendingCronJobModel> {
    private static final String PROCESS_DEFINITION_NAME = "questionsEmailSendingProcess";
    private static final String PROCESS_CODE_PREFIX = "questionsEmailSendingProcess-";

    private final QuestionService questionService;
    private final BusinessProcessService businessProcessService;
    private final ModelService modelService;
    private final BaseStoreService baseStoreService;
    private final BaseSiteService baseSiteService;
    private final CommonI18NService commonI18NService;
    private final CustomerService customerService;

    @Value("${base.site}")
    private String baseSite;

    @Value("${base.store}")
    private String baseStore;

    public StartQuestionEmailingProcessJob(
            QuestionService questionService,
            BusinessProcessService businessProcessService,
            ModelService modelService,
            BaseStoreService baseStoreService,
            BaseSiteService baseSiteService,
            CommonI18NService commonI18NService,
            CustomerService customerService) {
        this.questionService = questionService;
        this.businessProcessService = businessProcessService;
        this.modelService = modelService;
        this.baseStoreService = baseStoreService;
        this.baseSiteService = baseSiteService;
        this.commonI18NService = commonI18NService;
        this.customerService = customerService;
    }

    @Override
    public PerformResult perform(QuestionsEmailSendingCronJobModel cronJob) {
        Date lastRunTime = cronJob.getLastRunTime();
        Map<ProductModel, List<QuestionModel>> grouped = questionService.getNewQuestionsGroupedByProduct(lastRunTime);

        if (grouped == null || grouped.isEmpty()) {
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }

        String notifyEmail = cronJob.getNotifyEmail();
        if (notifyEmail == null || notifyEmail.isBlank()) {
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }

        Set<QuestionModel> questions = new HashSet<>();
        for (Map.Entry<ProductModel, List<QuestionModel>> entry : grouped.entrySet()) {
            List<QuestionModel> list = entry.getValue();
            if (list != null) {
                questions.addAll(list);
            }
        }

        if (questions.isEmpty()) {
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }

        String processCode = PROCESS_CODE_PREFIX + System.currentTimeMillis();

        QuestionsEmailSendingProcessModel process = modelService.create(QuestionsEmailSendingProcessModel.class);
        process.setCode(processCode);
        process.setProcessDefinitionName(PROCESS_DEFINITION_NAME);
        process.setQuestions(questions);
        process.setNotifyEmail(notifyEmail);
        process.setSite(baseSiteService.getBaseSiteForUID(baseSite));
        process.setStore(baseStoreService.getBaseStoreForUid(baseStore));
        process.setLanguage(commonI18NService.getCurrentLanguage());
        process.setCurrency(commonI18NService.getCurrentCurrency());
        process.setCustomer(customerService.getCustomerByCustomerId(notifyEmail));

        modelService.save(process);
        businessProcessService.startProcess(process);

        cronJob.setLastRunTime(new Date());
        modelService.save(cronJob);

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }
}
