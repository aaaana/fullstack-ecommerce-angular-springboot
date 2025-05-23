package com.annacode.eccomerce.config;

import com.annacode.eccomerce.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
                                                     CorsRegistry cors) {
        HttpMethod[] theUnsupportedAction = {HttpMethod.PUT,HttpMethod.POST,
                                            HttpMethod.DELETE,HttpMethod.PATCH};

        //disable HTTP methods for Product: PUT, POST, and DELETE
        disableHttpMethods(Product.class,config,theUnsupportedAction);

        //disable HTTP methods for ProductCategory: PUT, POST, and DELETE
        disableHttpMethods(ProductCategory.class,config, theUnsupportedAction);
        disableHttpMethods(Country.class,config,theUnsupportedAction);
        disableHttpMethods(State.class,config, theUnsupportedAction);
        disableHttpMethods(Order.class,config, theUnsupportedAction);

// call an internal helper method
        exposeIds(config);

        //config cors mapping
        cors.addMapping(config.getBasePath()+"/**").allowedOrigins(theAllowedOrigins);

    }

    private static void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedAction) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction)));
    }

    private void exposeIds(RepositoryRestConfiguration config){
        // expose entity ids


        //- get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // get the entity types for the entities
        for(EntityType tempEntityType: entities){
            entityClasses.add(tempEntityType.getJavaType());
        }

        // expose the entity ids for the array of eneity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }

    @Configuration
    public static class MyAppConfig implements WebMvcConfigurer {

        @Value("${allowed.origins}")
        private String[] theAllowedOrigins;

        @Value("${spring.data.rest.base-path}")
        private String basePath;

        @Override
        public void addCorsMappings(CorsRegistry cors) {


            //setup cors mapping
            cors.addMapping(basePath +"/**").allowedOrigins(theAllowedOrigins);
        }
    }
}
