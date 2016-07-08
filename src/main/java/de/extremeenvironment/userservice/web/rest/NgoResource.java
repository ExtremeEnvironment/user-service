
package de.extremeenvironment.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.extremeenvironment.userservice.domain.Ngo;
import de.extremeenvironment.userservice.domain.User;
import de.extremeenvironment.userservice.repository.NgoRepository;
import de.extremeenvironment.userservice.repository.UserRepository;
import de.extremeenvironment.userservice.web.rest.util.HeaderUtil;
import jdk.nashorn.internal.objects.NativeRegExp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ngo.
 */
@RestController
@RequestMapping("/api")
public class NgoResource {

    private final Logger log = LoggerFactory.getLogger(NgoResource.class);

    @Inject
    private NgoRepository ngoRepository;

    @Inject
    private UserRepository userRepository;

    @Autowired
    public NgoResource(NgoRepository ngoRepository, UserRepository userRepository) {
        this.ngoRepository=ngoRepository;
        this.userRepository=userRepository;

    }

    /**
     * POST  /ngos : Create a new ngo.
     *
     * @param ngo the ngo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ngo, or with status 400 (Bad Request) if the ngo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ngos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ngo> createNgo(@Valid @RequestBody Ngo ngo) throws URISyntaxException {
        log.debug("REST request to save Ngo : {}", ngo);
        if (ngo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ngo", "idexists", "A new ngo cannot already have an ID")).body(null);
        }
        Ngo result = ngoRepository.save(ngo);
        return ResponseEntity.created(new URI("/api/ngos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ngo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ngos : Updates an existing ngo.
     *
     * @param ngo the ngo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ngo,
     * or with status 400 (Bad Request) if the ngo is not valid,
     * or with status 500 (Internal Server Error) if the ngo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ngos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ngo> updateNgo(@Valid @RequestBody Ngo ngo) throws URISyntaxException {
        log.debug("REST request to update Ngo : {}", ngo);
        if (ngo.getId() == null) {
            return createNgo(ngo);
        }
        Ngo result = ngoRepository.save(ngo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ngo", ngo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ngos : get all the ngos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ngos in body
     */
    @RequestMapping(value = "/ngos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ngo> getAllNgos() {
        log.debug("REST request to get all Ngos");
        List<Ngo> ngos = ngoRepository.findAllWithEagerRelationships();
        return ngos;
    }

    /**
     * GET  /ngos/:id : get the "id" ngo.
     *
     * @param id the id of the ngo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ngo, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/ngos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ngo> getNgo(@PathVariable Long id) {
        log.debug("REST request to get Ngo : {}", id);
        Ngo ngo = ngoRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(ngo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ngos/:id : delete the "id" ngo.
     *
     * @param id the id of the ngo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/ngos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNgo(@PathVariable Long id) {
        log.debug("REST request to delete Ngo : {}", id);
        ngoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ngo", id.toString())).build();
    }


    /**
     * Insert a new User in the given Ngo
     *
     *
     * @param userId
     * @return
     */
    @RequestMapping(value="/ngos/{ngoId}/{userId}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ngo> insertUser(@PathVariable Long ngoId,@PathVariable Long userId) {

        System.out.println(userRepository==null);
        Optional<User> user= userRepository.findOneById(userId);
        if(user.get()!=null) {
            Ngo ngo = ngoRepository.findOne(ngoId);
            ngo.getUsers().add(user.get());
            return Optional.ofNullable(ngo)
                .map(result -> new ResponseEntity<>(
                    result,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return null;
        }



    }

    /**
     * Removes a new User in the given Ngo
     *
     *
     * @param userId
     * @return
     */
    @RequestMapping(value="/ngos/{ngoId}/{userId}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ngo> removeUser(@PathVariable Long ngoId,@PathVariable Long userId) {


        Optional<User> user= userRepository.findOneById(userId);
        if(user.get()!=null) {
            System.out.println(user);
            Ngo ngo = ngoRepository.findOne(ngoId);
            ngo.getUsers().remove(user.get());
            return Optional.ofNullable(ngo)
                .map(result -> new ResponseEntity<>(
                    result,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return null;
        }



    }


}
