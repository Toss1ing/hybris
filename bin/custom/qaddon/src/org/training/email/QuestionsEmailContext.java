package org.training.email;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import org.springframework.beans.factory.annotation.Value;
import org.training.model.QuestionModel;
import org.training.model.QuestionsEmailSendingProcessModel;

import java.util.*;

public class QuestionsEmailContext extends AbstractEmailContext<QuestionsEmailSendingProcessModel> {

    private static final String QUESTIONS_EMAIL_DATA_MAP_KEY = "questionsEmailDataMap";
    private static final String EMAIL_KEY = "email";
    private static final String DISPLAY_NAME_KEY = "displayName";
    private static final String FROM_EMAIL_KEY = "fromEmail";
    private static final String FROM_DISPLAY_NAME_KEY = "fromDisplayName";

    private static final String QUESTION_CODE_KEY = "code";
    private static final String QUESTION_TEXT_KEY = "question";
    private static final String ANSWER_TEXT_KEY = "answer";

    @Value("${qaddon.questions.email.from}")
    private String fromEmail;

    private Map<String, List<Map<String, String>>> questionsEmailDataMap;

    @Override
    public void init(QuestionsEmailSendingProcessModel processModel, EmailPageModel emailPageModel) {
        super.init(processModel, emailPageModel);

        Map<String, List<QuestionModel>> grouped = groupQuestions(processModel);
        Map<String, List<Map<String, String>>> templateData = toTemplateData(grouped);

        questionsEmailDataMap = templateData;

        put(QUESTIONS_EMAIL_DATA_MAP_KEY, templateData);

        String notifyEmail = processModel.getNotifyEmail();
        if (notifyEmail != null && !notifyEmail.isBlank()) {
            put(EMAIL_KEY, notifyEmail);
            put(DISPLAY_NAME_KEY, notifyEmail);
            put(FROM_EMAIL_KEY, fromEmail);
            put(FROM_DISPLAY_NAME_KEY, fromEmail);
        }
    }

    private Map<String, List<QuestionModel>> groupQuestions(QuestionsEmailSendingProcessModel processModel) {
        Map<String, List<QuestionModel>> result = new HashMap<>();

        Set<QuestionModel> questions = processModel.getQuestions();
        if (questions == null) {
            return result;
        }

        for (QuestionModel q : questions) {
            ProductModel product = q.getProduct();
            if (product == null || product.getCode() == null) {
                continue;
            }

            result.computeIfAbsent(product.getCode(), k -> new ArrayList<>()).add(q);
        }

        return result;
    }

    private Map<String, List<Map<String, String>>> toTemplateData(Map<String, List<QuestionModel>> grouped) {
        Map<String, List<Map<String, String>>> result = new HashMap<>();

        for (Map.Entry<String, List<QuestionModel>> entry : grouped.entrySet()) {
            List<Map<String, String>> list = new ArrayList<>();

            for (QuestionModel q : entry.getValue()) {
                Map<String, String> data = new HashMap<>();
                data.put(QUESTION_CODE_KEY, nullToEmpty(q.getCode()));
                data.put(QUESTION_TEXT_KEY, nullToEmpty(q.getQuestion()));
                data.put(ANSWER_TEXT_KEY, nullToEmpty(q.getAnswer()));
                list.add(data);
            }

            result.put(entry.getKey(), list);
        }

        return result;
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    @Override
    protected BaseSiteModel getSite(QuestionsEmailSendingProcessModel processModel) {
        return processModel.getSite();
    }

    @Override
    protected CustomerModel getCustomer(QuestionsEmailSendingProcessModel processModel) {
        return processModel.getCustomer();
    }

    @Override
    protected LanguageModel getEmailLanguage(QuestionsEmailSendingProcessModel processModel) {
        return processModel.getLanguage();
    }

    public Map<String, List<Map<String, String>>> getQuestionsEmailDataMap() {
        return questionsEmailDataMap;
    }
}
