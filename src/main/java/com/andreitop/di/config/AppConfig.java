package com.andreitop.xml.config;

import com.andreitop.xml.mount.Mount;
import com.andreitop.xml.mount.Tiger;
import com.andreitop.xml.mount.Wolf;
import com.andreitop.xml.unit.Human;
import com.andreitop.xml.unit.Orc;
import com.andreitop.xml.unit.Troll;
import com.andreitop.xml.unit.Unit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@ComponentScan("com.andreitop.xml")
@PropertySource("classpath:config/heroes.properties")
public class AppConfig {

    @Value("${character.created}")
    public String creationDate;


    @Bean
    public Mount frostWolf() {
        return new Wolf();
    }

    @Bean
    public Mount shadowTiger() {
        return new Tiger();
    }

    @Bean
    public Unit knight() {
        return new Human(shadowTiger(), "thunderFury", "soulBlade");
    }

    @Bean
    public Unit trall() {
        Orc orc = new Orc(frostWolf());
        orc.setWeapon("furryAxe");
        orc.setColorCode(9);
        return orc;
    }

    @Bean
    public SimpleDateFormat dateFormatter() {
        return new SimpleDateFormat("dd/mm/yyyy");
    }

    @Bean
    public Map<String, Mount> trollMountMap() {
        Map<String, Mount> trollMountMap = new HashMap<>();
        trollMountMap.put("m1", frostWolf());
        trollMountMap.put("m2", shadowTiger());
        return trollMountMap;
    }

    @Bean
    public Set<Mount> trollMountSet() {
        Set<Mount> trollMountSet = new LinkedHashSet<>();
        trollMountSet.add(shadowTiger());
        trollMountSet.add(frostWolf());
        return trollMountSet;
    }

    @Bean
    public Troll zulJin() throws ParseException {
        Troll zulJin = new Troll();
        zulJin.setColorCode(ThreadLocalRandom.current().nextInt(1,10));
        zulJin.setCreationDate(dateFormatter().parse(creationDate));
        zulJin.setListOfMounts(Arrays.asList(Troll.DEFAULT_MOUNT, null, shadowTiger()));
        zulJin.setSetOfMounts(trollMountSet());
        zulJin.setMapOfMounts(trollMountMap());
        return zulJin;
    }
}
