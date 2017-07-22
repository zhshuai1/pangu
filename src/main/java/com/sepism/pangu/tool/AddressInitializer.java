package com.sepism.pangu.tool;

import com.google.gson.Gson;
import com.sepism.pangu.model.questionnaire.Choice;
import com.sepism.pangu.model.repository.ChoiceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Put this class instead of in a new package is because the models such as Choice are shared.
// TODO: Should also abstract the model together with the tools to a separate package.
@Log4j2
public class AddressInitializer {
    public static final Gson GSON = new Gson();
    private static long country;
    private static final Map<String, Long> provinceMap = new HashMap<>();
    private static final Map<String, Long> cityMap = new HashMap<>();
    private static final long creator = 1;
    private static final long root = 6;
    private static final long questionId = 10;

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext
                ("./src/main/webapp/WEB-INF/persistence-context.xml");
        ChoiceRepository choiceRepository = context.getBean("choiceRepository", ChoiceRepository.class);
        System.out.println("This process is not idempotent. Waiting 5 seconds for you to confirm......");
        Thread.sleep(5000);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        setupCountry(documentBuilder, choiceRepository);
        setupProvinces(documentBuilder, choiceRepository);
        setupCities(documentBuilder, choiceRepository);
        setupDistricts(documentBuilder, choiceRepository);

    }

    private static void setupCountry(DocumentBuilder documentBuilder, ChoiceRepository choiceRepository)
            throws Exception {
        Choice choice1 = Choice.builder().creationDate(new Date()).creator(creator).root(root).parent(root)
                .descriptionCn("全球").questionId(questionId).build();
        choiceRepository.save(choice1);
        Choice choice2 = Choice.builder().creationDate(new Date()).creator(creator).root(root).parent(choice1.getId())
                .descriptionCn("中国").questionId(questionId).build();
        choiceRepository.save(choice2);
        country = choice2.getId();
    }

    private static void setupProvinces(DocumentBuilder documentBuilder, ChoiceRepository choiceRepository) throws Exception {
        Document provincesDocument = documentBuilder.parse("src/main/ToolsResources/provinces.xml");
        NodeList provinces = provincesDocument.getElementsByTagName("Province");
        for (int i = 0; i < provinces.getLength(); ++i) {
            NamedNodeMap attributes = provinces.item(i).getAttributes();
            String id = attributes.getNamedItem("ID").getNodeValue();
            String name = attributes.getNamedItem("ProvinceName").getNodeValue();

            Choice choice = Choice.builder().creationDate(new Date()).creator(creator).root(root).parent(country)
                    .descriptionCn(name).questionId(questionId).build();
            choiceRepository.save(choice);
            provinceMap.put(id, choice.getId());
            log.info("Province Id is: {}, name is {}, db id is {}", id, name, choice.getId());
        }
    }

    private static void setupCities(DocumentBuilder documentBuilder, ChoiceRepository choiceRepository) throws Exception {
        Document citiesDocument = documentBuilder.parse("src/main/ToolsResources/cities.xml");
        NodeList cities = citiesDocument.getElementsByTagName("City");
        for (int i = 0; i < cities.getLength(); ++i) {
            NamedNodeMap attributes = cities.item(i).getAttributes();
            String id = attributes.getNamedItem("ID").getNodeValue();
            String name = attributes.getNamedItem("CityName").getNodeValue();
            String pid = attributes.getNamedItem("PID").getNodeValue();
            Choice choice = Choice.builder().creationDate(new Date()).creator(creator).root(root).parent(provinceMap.get
                    (pid)).descriptionCn(name).questionId(questionId).build();
            choiceRepository.save(choice);
            cityMap.put(id, choice.getId());
            log.info("Province Id is: {}, name is {}, db id is {} and pid is {}", id, name, choice.getId(), pid);
        }
    }

    private static void setupDistricts(DocumentBuilder documentBuilder, ChoiceRepository choiceRepository) throws Exception {
        Document districtsDocument = documentBuilder.parse("src/main/ToolsResources/districts.xml");
        NodeList districts = districtsDocument.getElementsByTagName("District");
        for (int i = 0; i < districts.getLength(); ++i) {
            NamedNodeMap attributes = districts.item(i).getAttributes();
            String id = attributes.getNamedItem("ID").getNodeValue();
            String name = attributes.getNamedItem("DistrictName").getNodeValue();
            String pid = attributes.getNamedItem("CID").getNodeValue();
            Choice choice = Choice.builder().creationDate(new Date()).creator(creator).root(root).parent(cityMap.get
                    (pid)).descriptionCn(name).questionId(questionId).build();
            choiceRepository.save(choice);
            log.info("Province Id is: {}, name is {}, db id is {} and pid is {}", id, name, choice.getId(), pid);
        }
    }
}
