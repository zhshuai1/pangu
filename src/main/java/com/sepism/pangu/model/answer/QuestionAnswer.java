package com.sepism.pangu.model.answer;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.sepism.pangu.model.questionnaire.Question;
import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "questionAnswers")
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long questionId;
    private long questionnaireId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaireAnswerId")
    @JsonAdapter(QuestionnaireAnswerSerializerForQuestionAnswer.class)
    private QuestionnaireAnswer questionnaireAnswer;
    private long userId;
    @Enumerated(EnumType.STRING)
    private Question.Type type;
    private String answer;
    private boolean current;
    private Date creationDate;
    private Date lastUpdateTime;
}

class QuestionnaireAnswerSerializerForQuestionAnswer implements JsonSerializer<QuestionnaireAnswer>,
        JsonDeserializer<QuestionnaireAnswer> {

    @Override
    public QuestionnaireAnswer deserialize(JsonElement jsonElement,
                                           Type type,
                                           JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        //TODO: if we need questionnaireAnswerId afterwards, we could return a new QuestionnaireAnswer with the
        // qeustionnaireAnswerId
        return null;
    }

    @Override
    public JsonElement serialize(QuestionnaireAnswer questionnaireAnswer, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("questionnaireAnswer", questionnaireAnswer.getId());
        return jsonObject;
    }
}
