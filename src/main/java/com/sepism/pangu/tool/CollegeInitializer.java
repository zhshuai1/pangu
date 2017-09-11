package com.sepism.pangu.tool;

import com.google.gson.Gson;
import com.sepism.pangu.model.questionnaire.Choice;
import com.sepism.pangu.model.repository.ChoiceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

// Put this class instead of in a new package is because the models such as Choice are shared.
// TODO: Should also abstract the model together with the tools to a separate package.
@Log4j2
public class CollegeInitializer {
    public static final Gson GSON = new Gson();
    private static final long CREATOR = 1;
    private static final long ROOT = 12;
    private static final long QUESTION_ID = 10;
    private static final String PREFIX = "";

    /**
     * TODO: Before you run the code, you should comment out the @GeneratedValue annotation to allow use self-defined
     * TODO: primary key
     */

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext
                ("./src/main/webapp/WEB-INF/persistence-context.xml");
        ChoiceRepository choiceRepository = context.getBean("choiceRepository", ChoiceRepository.class);

        File file = new File("src/main/ToolsResources/college/college_records");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String lineString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((lineString = reader.readLine()) != null) {
                long start = System.currentTimeMillis();
                String fields[] = lineString.split("\\s+");
                String comment = PREFIX + fields[0];
                long id = Long.valueOf(fields[1]);
                long parent = Long.valueOf(fields[2]);
                parent = parent == 0 ? ROOT : parent;
                String desc = fields[3];
                Choice choice = Choice.builder().id(id).descriptionCn(desc).creationDate(new Date()).creator(CREATOR)
                        .root(ROOT).parent(parent).comment(comment).questionId(QUESTION_ID).build();
                System.out.println(GSON.toJson(choice));
                choiceRepository.save(choice);
                System.out.println("Time elapse:-----------------------\n" + (System.currentTimeMillis() - start));
            }
        }

    }
}
