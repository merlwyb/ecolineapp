package com.ecoline.application.data.generator;

import com.ecoline.application.data.Role;
import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.entity.Portion;
import com.ecoline.application.data.entity.User;
import com.ecoline.application.data.repository.OrderRepository;
import com.ecoline.application.data.repository.PortionRepository;
import com.ecoline.application.data.repository.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.util.Collections;
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
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository, OrderRepository orderRepository,
                                      PortionRepository portionRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            int seed = 123;

            logger.info("Generating data");

//            logger.info("... generating 100 Sample Address entities...");
//            ExampleDataGenerator<SampleAddress> sampleAddressRepositoryGenerator = new ExampleDataGenerator<>(
//                    SampleAddress.class, LocalDateTime.of(2021, 12, 28, 0, 0, 0));
//            sampleAddressRepositoryGenerator.setData(SampleAddress::setId, DataType.ID);
//            sampleAddressRepositoryGenerator.setData(SampleAddress::setStreet, DataType.ADDRESS);
//            sampleAddressRepositoryGenerator.setData(SampleAddress::setPostalCode, DataType.ZIP_CODE);
//            sampleAddressRepositoryGenerator.setData(SampleAddress::setCity, DataType.CITY);
//            sampleAddressRepositoryGenerator.setData(SampleAddress::setState, DataType.STATE);
//            sampleAddressRepositoryGenerator.setData(SampleAddress::setCountry, DataType.COUNTRY);
//            sampleAddressRepository.saveAll(sampleAddressRepositoryGenerator.create(100, seed));
//
//            logger.info("... generating 100 Sample Book entities...");
//            ExampleDataGenerator<SampleBook> sampleBookRepositoryGenerator = new ExampleDataGenerator<>(
//                    SampleBook.class, LocalDateTime.of(2021, 12, 28, 0, 0, 0));
//            sampleBookRepositoryGenerator.setData(SampleBook::setId, DataType.ID);
//            sampleBookRepositoryGenerator.setData(SampleBook::setImage, DataType.BOOK_IMAGE_URL);
//            sampleBookRepositoryGenerator.setData(SampleBook::setName, DataType.BOOK_TITLE);
//            sampleBookRepositoryGenerator.setData(SampleBook::setAuthor, DataType.FULL_NAME);
//            sampleBookRepositoryGenerator.setData(SampleBook::setPublicationDate, DataType.DATE_OF_BIRTH);
//            sampleBookRepositoryGenerator.setData(SampleBook::setPages, DataType.NUMBER_UP_TO_1000);
//            sampleBookRepositoryGenerator.setData(SampleBook::setIsbn, DataType.EAN13);
//            sampleBookRepository.saveAll(sampleBookRepositoryGenerator.create(100, seed));
//
//            logger.info("... generating 100 Sample Food Product entities...");
//            ExampleDataGenerator<SampleFoodProduct> sampleFoodProductRepositoryGenerator = new ExampleDataGenerator<>(
//                    SampleFoodProduct.class, LocalDateTime.of(2021, 12, 28, 0, 0, 0));
//            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setId, DataType.ID);
//            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setImage, DataType.FOOD_PRODUCT_IMAGE);
//            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setName, DataType.FOOD_PRODUCT_NAME);
//            sampleFoodProductRepositoryGenerator.setData(SampleFoodProduct::setEanCode, DataType.FOOD_PRODUCT_EAN);
//            sampleFoodProductRepository.saveAll(sampleFoodProductRepositoryGenerator.create(100, seed));

            Order order = new Order();
            order.setId(13154L);
            //order.setRubberId(1L);
            //order.setBulkId(2L);
            //order.setChalkId(3L);
            //order.setCarbonId(4L);
            //order.setCorrected(false);
            //order.setMixed(false);
            //order.setRolled(false);
            //order.setSelected(false);
            orderRepository.save(order);


            Order order1 = new Order();
            order1.setId(835721L);
            order1.setRubberId(5L);
            order1.setBulkId(6L);
            order1.setChalkId(7L);
            order1.setCarbonId(8L);
            order1.setCorrected(true);
            order1.setMixed(true);
            order1.setRolled(false);
            order1.setSelected(false);
            orderRepository.save(order1);

//            Order order2 = new Order();
//            order2.setId(142412L);
//            order2.setBulkId(23L);
//            orderRepository.save(order2);
//
//            Order order3 = new Order();
//            order3.setId(34621L);
//            order3.setChalkId(25L);
//            orderRepository.save(order3);
//
//            Order order4 = new Order();
//            order4.setId(243541L);
//            order4.setCarbonId(26L);
//            orderRepository.save(order4);
//
//            Order order5 = new Order();
//            order5.setId(2435411L);
//            order5.setCarbonId(26L);
//            orderRepository.save(order5);
//
//            Order order6 = new Order();
//            order6.setId(2435412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//
//            order6.setId(24354121L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(24354122L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(24354123L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(24354124L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(24354125L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(24354126L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(24354127L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(24354128L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(24354129L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435411L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435415L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435124L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(243546L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(243547L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(243548L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435489L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(243548L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435111L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435222L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435333L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435112L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435113L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2435116L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(243129L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(243312L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2421312L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(242312L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2465412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2455412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2477412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2488412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2499412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2421412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2429412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(246712L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(246812L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2469412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(298412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2995412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2005412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2095412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2085412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2055412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(20045412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2332412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2331412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);
//            order6.setId(2333412L);
//            order6.setCarbonId(26L);
//            orderRepository.save(order6);


            Portion portion = new Portion();
            portion.setComponent("Каучук");
            portion.setWeight(38.2);
            portion.setRespUsername("admin");
            portionRepository.save(portion);
            portion.setComponent("Каучук");
            portion.setWeight(32.44);
            portion.setRespUsername("admin");
            portionRepository.save(portion);
            portion.setComponent("Каучук");
            portion.setWeight(39.2);
            portion.setRespUsername("admin");
            portionRepository.save(portion);
            portion.setComponent("Каучук");
            portion.setWeight(54.2);
            portion.setRespUsername("admin");
            portionRepository.save(portion);

            Portion portion1 = new Portion();
            portion1.setComponent("Каучук");
            portion1.setWeight(44.2);
            portion1.setRespUsername("user");
            portionRepository.save(portion1);

            logger.info("... generating 7 User entities...");

            User user1 = new User();
            user1.setName("Иванов Иван");
            user1.setUsername("user1");
            user1.setHashedPassword(passwordEncoder.encode("user1"));
           user1.setRoles(Collections.singleton(Role.WEIGHER));
            userRepository.save(user1);

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
            user3.setRoles(Collections.singleton(Role.OPERATOR));
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
            admin.setRoles(Stream.of(Role.USER, Role.ADMIN, Role.WEIGHER, Role.TECHNOLOGIST, Role.OPERATOR, Role.ROLLERMAN, Role.LABWORKER, Role.ROLE6, Role.ROLE7).collect(Collectors.toSet()));
            userRepository.save(admin);

            logger.info("Generated demo data");
        };
    }

}