package de.extremeenvironment.userservice.service;

import de.extremeenvironment.userservice.domain.Ngo;
import de.extremeenvironment.userservice.repository.NgoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by Jonathan on 08.07.2016.
 */
@Service
public class NgoService {

    @Inject
    NgoRepository ngoRepository;

    @PostConstruct
    public void dataCreate() {

        Ngo ngo = new Ngo();
        ngo.setName("WWF");
        ngo.setEmail("wwf@tier.de");
        ngo.setTelephone("1234566");
        ngo.setWebsite("www.wwf.de");
        ngoRepository.saveAndFlush(ngo);

        Ngo ngo1 = new Ngo();
        ngo1.setName("UNICEF");
        ngo1.setEmail("unicef@unicef.de");
        ngo1.setTelephone("123444566");
        ngo1.setWebsite("www.unicef.de");
        ngoRepository.saveAndFlush(ngo1);

    }
}
