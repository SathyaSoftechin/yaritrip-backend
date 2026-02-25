        // attractionRepository.save(
        //         Attraction.builder()
        //                 .name("Singapore Anyway Package")
        //                 .location("Singapore")
        //                 .description("Relax in luxury houseboats through serene backwaters.")
        //                 .imageUrl("https://www.gokitetours.com/wp-content/uploads/2019/11/singpore-tour-packages-from-india.webp")
        //                 .rating(4.8)
        //                 .isPopular(true)
        //                 .build()
        // );
package com.example.demo.config;

import com.example.demo.model.Attraction;
import com.example.demo.model.Stay;
import com.example.demo.repository.AttractionRepository;
import com.example.demo.repository.StayRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AttractionRepository attractionRepository;
    private final StayRepository stayRepository;

    @Override
    public void run(String... args) {

        // Clear old data
        attractionRepository.deleteAll();
        stayRepository.deleteAll();

        // Seed fresh data
        seedAttractions();
        seedStays();

        System.out.println("✅ All data seeded successfully");
    }

    /* ==========================
       SEED ATTRACTIONS
       ========================== */
    private void seedAttractions() {

        attractionRepository.save(
                Attraction.builder()
                        .name("Burj Khalifa")
                        .location("Dubai")
                        .description("Experience the tallest building in the world with stunning city views.")
                        .imageUrl("https://climatecontrol.imiplc.com/_next/image?url=https%3A%2F%2Fcdn.tessa.imihy.eimed-project.de%2F621346%2FWeb_Header%2FBurj_Khalifa_Tower.webp&w=3840&q=75")
                        .rating(4.9)
                        .isPopular(true)
                        .build()
        );

        attractionRepository.save(
                Attraction.builder()
                        .name("Museum of The Future")
                        .location("Dubai")
                        .description("Explore the futuristic museum showcasing cutting-edge innovations.")
                        .imageUrl("https://lh3.googleusercontent.com/gps-cs-s/AHVAweo_AgJJfdiWB8t36ZQ21wMMDSAvmzoNUDcUSZA17pGD1E4b4U2ooLKbRPyDEp0FyghkKEBQIhTwQcRtJunOPRQ6lejlQCp3WSCMEL7UTSfhbJPC4TPyo7C6tbVSIDa282rFchhz1cb5uws=s294-w294-h220-n-k-no")
                        .rating(4.8)
                        .isPopular(true)
                        .build()
        );

        attractionRepository.save(
                Attraction.builder()
                        .name("Charminar Hyderabad")
                        .location("India")
                        .description("Famous historic monument in Hyderabad.")
                        .imageUrl("https://lh3.googleusercontent.com/gps-cs-s/AHVAwer210TkmDg0imN8Qd77lgHj2goo4VB7kAOLHlC9EpYbSrhenXo10mIc1l_uDlllPXeKKmbihcnMWJpqJdsPwEtdaVo9eCtxzF6uH46rcixvtpsCO09fGWCRcoRcq4hTVT_UuBfz=s294-w294-h220-n-k-no")
                        .rating(4.8)
                        .isPopular(true)
                        .build()
        );

        attractionRepository.save(
                Attraction.builder()
                        .name("Singapore Anyway Package")
                        .location("Singapore")
                        .description("Relax in luxury houseboats through serene backwaters.")
                        .imageUrl("https://www.gokitetours.com/wp-content/uploads/2019/11/singpore-tour-packages-from-india.webp")
                        .rating(4.8)
                        .isPopular(true)
                        .build()
        );

        attractionRepository.save(
                Attraction.builder()
                        .name("The Temple of the Emerald Buddha")
                        .location("Bangkok")
                        .description("Sacred temple located inside the Grand Palace.")
                        .imageUrl("https://images.unsplash.com/photo-1500530855697-b586d89ba3ee")
                        .rating(4.7)
                        .isPopular(false)
                        .build()
        );

        System.out.println("✅ Attractions seeded");
    }

    /* ==========================
       SEED STAYS
       ========================== */
    private void seedStays() {

        stayRepository.save(
                Stay.builder()
                        .name("The Oberoi Amarvilas")
                        .location("Agra")
                        .region("NORTH")
                        .rating(8.9)
                        .reviews(2140)
                        .duration("2 days / 3 nights")
                        .startingPrice(22500.0)
                        .imageUrl("https://images.unsplash.com/photo-1566073771259-6a8506099945")
                        .isPremium(true)
                        .build()
        );

        stayRepository.save(
                Stay.builder()
                        .name("Jaipur Heritage Palace")
                        .location("Jaipur")
                        .region("NORTH")
                        .rating(8.9)
                        .reviews(1820)
                        .duration("3 days / 4 nights")
                        .startingPrice(18200.0)
                        .imageUrl("https://images.unsplash.com/photo-1582719478250-c89cae4dc85b")
                        .isPremium(true)
                        .build()
        );

        stayRepository.save(
                Stay.builder()
                        .name("Goa Beachside Resort")
                        .location("Goa")
                        .region("WEST")
                        .rating(8.7)
                        .reviews(2560)
                        .duration("3 days / 3 nights")
                        .startingPrice(14500.0)
                        .imageUrl("https://images.unsplash.com/photo-1501117716987-c8e1ecb2104c")
                        .isPremium(true)
                        .build()
        );

        stayRepository.save(
                Stay.builder()
                        .name("Udaipur Lake Palace Retreat")
                        .location("Udaipur")
                        .region("WEST")
                        .rating(9.4)
                        .reviews(1675)
                        .duration("2 days / 2 nights")
                        .startingPrice(19800.0)
                        .imageUrl("https://images.unsplash.com/photo-1524492449090-1d08a7b8ff30")
                        .isPremium(true)
                        .build()
        );

        stayRepository.save(
                Stay.builder()
                        .name("Kerala Backwater Luxury Stay")
                        .location("Alleppey")
                        .region("SOUTH")
                        .rating(9.1)
                        .reviews(1320)
                        .duration("2 days / 2 nights")
                        .startingPrice(16200.0)
                        .imageUrl("https://images.unsplash.com/photo-1587135991058-8816c8f1a1e0")
                        .isPremium(true)
                        .build()
        );

        stayRepository.save(
                Stay.builder()
                        .name("Darjeeling Himalayan Retreat")
                        .location("Darjeeling")
                        .region("EAST")
                        .rating(8.8)
                        .reviews(980)
                        .duration("2 days / 3 nights")
                        .startingPrice(11800.0)
                        .imageUrl("https://images.unsplash.com/photo-1500530855697-b586d89ba3ee")
                        .isPremium(true)
                        .build()
        );

        System.out.println("✅ Stays seeded");
    }
}