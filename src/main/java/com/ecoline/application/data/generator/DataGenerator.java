package com.ecoline.application.data.generator;

import com.ecoline.application.data.Role;
import com.ecoline.application.data.entity.*;
import com.ecoline.application.data.repository.*;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository,
                                      RecipeRepository recipeRepository, RecipePartRepository recipePartRepository,
                                      TechnologicalCardRepository technologicalCardRepository, ComponentPortionRepository componentPortionRepository,
                                      OrderRepository orderRepository, LabJournalRepository labJournalRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            int seed = 123;

            logger.info("Generating data");


            recipePartRepository.save(new RecipePart("Каучук","Каучук СП-16-542-23ОРРА",27.523, 0.015 ));
            recipePartRepository.save(new RecipePart("Каучук","Каучук ОВ-15-128-ОА3212",36.523, 0.015 ));
            recipePartRepository.save(new RecipePart("Сыпучая смесь","Сыпучая смесь СП-16-542-23ОРРА",27.523, 0.015 ));
            recipePartRepository.save(new RecipePart("Сыпучая смесь","Сыпучая смесь ОВ-15-128-ОА3212",65.523, 0.015 ));
            recipePartRepository.save(new RecipePart("Мел","Мел СП-16-542-23ОРРА",98.521, 0.015 ));
            recipePartRepository.save(new RecipePart("Мел","Мел ОВ-15-128-ОА3212",2.232, 0.015 ));
            recipePartRepository.save(new RecipePart("Техуглерод","Углерод СП-16-542-23ОРРА",14.111, 0.015 ));
            recipePartRepository.save(new RecipePart("Техуглерод","Углерод УГ-15-128-ОА3212",11.523, 0.015 ));



            Recipe recipe = new Recipe();
            recipe.getRecipeParts().addAll(Set.of(recipePartRepository.getById(1L), recipePartRepository.getById(3L), recipePartRepository.getById(5L), recipePartRepository.getById(7L)));
            recipe.setRecipeStringIdentifier("П-24-26");
            recipeRepository.save(recipe);


            Recipe recipe1 = new Recipe();
            recipe1.getRecipeParts().addAll(Set.of(recipePartRepository.getById(2L), recipePartRepository.getById(4L), recipePartRepository.getById(6L), recipePartRepository.getById(8L)));
            recipe1.setRecipeStringIdentifier("К-15-22");
            recipeRepository.save(recipe1);

            Recipe recipe2 = new Recipe();
            recipe2.getRecipeParts().addAll(Set.of(recipePartRepository.getById(1L)));
            recipe2.setRecipeStringIdentifier("К-66-23");
            recipeRepository.save(recipe2);



            Order order = new Order();
            order.setStringIdentifier("ИК123-23-3-321");
            order.setWeightRequired(800);
            orderRepository.save(order);

            Order order1 = new Order();
            order1.setWeightRequired(900);
            order1.setStringIdentifier("ИК244-13-6-862");
            orderRepository.save(order1);

            Order order2 = new Order();
            order2.setWeightRequired(200);
            order2.setStringIdentifier("КК324-23-6-899");
            orderRepository.save(order2);

            ComponentPortion componentPortion = new ComponentPortion();
            componentPortion.setComponentName("Каучук СП-15");



            LabJournal labJournal = new LabJournal();
            labJournal.setDate(LocalDate.now());
            labJournal.setBrand("П-7");
            labJournal.setNumberLaying("51");
            labJournal.setVulcanizationDate(LocalDate.now().plusDays(1));
            labJournal.setVulcanizationTemperature(151);
            labJournal.setVulcanizationTime(55);
            labJournal.setHardnessActual(66);
            labJournal.setHardnessIdeal("65-76");
            labJournal.setEnduranceActual(14.3);
            labJournal.setEnduranceIdeal("8,0");
            labJournal.setLengtheningActual(300);
            labJournal.setLengtheningIdeal("200-350");
            labJournal.setDeformationActual("5,4%");
            labJournal.setVylezhka("+6+16");
            labJournal.setCompany("САПТ");
            labJournalRepository.save(labJournal);



            logger.info("... generating 7 User entities...");

            User user11 = new User();
            user11.setName("Иванов Иван");
            user11.setUsername("user11");
            user11.setHashedPassword(passwordEncoder.encode("user11"));
            user11.setRoles(Collections.singleton(Role.WEIGHER_RUBBER));
            userRepository.save(user11);

            User user12 = new User();
            user12.setName("Евгеньева Мария");
            user12.setUsername("user12");
            user12.setHashedPassword(passwordEncoder.encode("user12"));
            user12.setRoles(Collections.singleton(Role.WEIGHER_BULK));
            userRepository.save(user12);

            User user13 = new User();
            user13.setName("Щеголов Олег");
            user13.setUsername("user13");
            user13.setHashedPassword(passwordEncoder.encode("user13"));
            user13.setRoles(Collections.singleton(Role.WEIGHER_CHALK));
            userRepository.save(user13);

            User user14 = new User();
            user14.setName("Зубов Михаил");
            user14.setUsername("user14");
            user14.setHashedPassword(passwordEncoder.encode("user14"));
            user14.setRoles(Collections.singleton(Role.WEIGHER_CARBON));
            userRepository.save(user14);

            User user2 = new User();
            user2.setName("Егоров Егор");
            user2.setUsername("user2");
            user2.setHashedPassword(passwordEncoder.encode("user2"));
            user2.setRoles(Collections.singleton(Role.TECHNOLOGIST));
            userRepository.save(user2);

            User user3 = new User();
            user3.setName("Николаев Александр");
            user3.setUsername("user3");
            user3.setHashedPassword(passwordEncoder.encode("user3"));
            user3.setRoles(Collections.singleton(Role.MACHINIST));
            userRepository.save(user3);

            User user4 = new User();
            user4.setName("Александрова Мария");
            user4.setUsername("user4");
            user4.setHashedPassword(passwordEncoder.encode("user4"));
            user4.setRoles(Collections.singleton(Role.ROLLERMAN));
            userRepository.save(user4);

            User user5 = new User();
            user5.setName("Зубов Владимир");
            user5.setUsername("user5");
            user5.setHashedPassword(passwordEncoder.encode("user5"));
            user5.setRoles(Collections.singleton(Role.LABWORKER));
            userRepository.save(user5);

            User user = new User();
            user.setName("John Normal");
            user.setUsername("user");
            user.setHashedPassword(passwordEncoder.encode("user"));
            user.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);

            User admin = new User();
            admin.setName("Егоров Анатолий");
            admin.setUsername("admin");
            admin.setHashedPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Stream.of(Role.USER, Role.ADMIN, Role.WEIGHER_RUBBER, Role.WEIGHER_BULK, Role.WEIGHER_CHALK, Role.WEIGHER_CARBON, Role.TECHNOLOGIST, Role.MACHINIST, Role.ROLLERMAN, Role.LABWORKER, Role.ROLE6, Role.ROLE7).collect(Collectors.toSet()));
            userRepository.save(admin);

            logger.info("Generated demo data");
        };
    }

}