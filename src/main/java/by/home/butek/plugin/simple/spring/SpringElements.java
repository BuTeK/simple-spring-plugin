package by.home.butek.plugin.simple.spring;

import java.util.*;

import static java.util.Arrays.asList;

public class SpringElements {

    public static final String APPLICATION_CLASS = "org.springframework.boot.autoconfigure.SpringBootApplication";

    public static final String REST_CONTROLLER_CLASS = "org.springframework.web.bind.annotation.RestController";
    public static final String SERVICE_CLASS = "org.springframework.stereotype.Service";
    public static final String CONTROLLER_CLASS = "org.springframework.stereotype.Controller";
    public static final String COMPONENT_CLASS = "org.springframework.stereotype.Component";
    public static final String REPOSITORY_CLASS = "org.springframework.stereotype.Repository";
    public static final String SPRING_BOOT_TEST_CLASS = "org.springframework.boot.test.context.SpringBootTest";
    public static final String RUN_WITH = "org.junit.runner.RunWith";

    public static final List<String> BEAN_CLASS_ANNOTATIONS = asList(
            REST_CONTROLLER_CLASS,
            CONTROLLER_CLASS,
            SERVICE_CLASS,
            COMPONENT_CLASS,
            REPOSITORY_CLASS,
            SPRING_BOOT_TEST_CLASS,
            APPLICATION_CLASS,
            RUN_WITH
    );

    public static final String INJECT = "javax.inject.Inject";
    public static final String VALUE = "org.springframework.beans.factory.annotation.Value";
    public static final String AUTOWIRED = "org.springframework.beans.factory.annotation.Autowired";
    public static final String MOCK_BEAN = "org.springframework.boot.test.mock.mockito.MockBean";
    public static final String SPY_BEAN = "org.springframework.boot.test.mock.mockito.SpyBean";

    public static final List<String> BEAN_FIELD_ANNOTATIONS = asList(AUTOWIRED, VALUE, INJECT, MOCK_BEAN, SPY_BEAN);


    public static final String GET_METHOD = "org.springframework.web.bind.annotation.GetMapping";
    public static final String POST_METHOD = "org.springframework.web.bind.annotation.PostMapping";
    public static final String DELETE_METHOD = "org.springframework.web.bind.annotation.DeleteMapping";
    public static final String PUT_METHOD = "org.springframework.web.bind.annotation.PutMapping";
    public static final String PATCH_METHOD = "org.springframework.web.bind.annotation.PatchMapping";

    public static final List<String> METHOD_ANNOTATIONS = asList(
            GET_METHOD,
            POST_METHOD,
            DELETE_METHOD,
            PUT_METHOD,
            PATCH_METHOD
    );

    // Configuration
    public static final String SPRING_BOOT_CONFIGURATION_CLASS = "org.springframework.boot.SpringBootConfiguration";
    public static final String CONFIGURATION_CLASS = "org.springframework.context.annotation.Configuration";
    public static final String TEST_CONFIGURATION_CLASS = "org.springframework.boot.test.context.TestConfiguration";
    public static final List<String> CONFIGURATION_CLASSES = asList(SPRING_BOOT_CONFIGURATION_CLASS, CONFIGURATION_CLASS, TEST_CONFIGURATION_CLASS);

    public static final String BEAN_METHOD = "org.springframework.context.annotation.Bean";
    public static final List<String> CONFIGURATION_METHOD_ANNOTATIONS = Collections.singletonList(BEAN_METHOD);

}
