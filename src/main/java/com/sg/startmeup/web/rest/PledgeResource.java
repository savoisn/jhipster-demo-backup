package com.sg.startmeup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sg.startmeup.domain.Pledge;
import com.sg.startmeup.repository.PledgeRepository;
import com.sg.startmeup.service.PledgedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pledge.
 */
@RestController
@RequestMapping("/api")
public class PledgeResource {

    private final Logger log = LoggerFactory.getLogger(PledgeResource.class);

    @Inject
    private PledgeRepository pledgeRepository;

    @Inject
    private PledgedService pledgedService;

    /**
     * POST  /pledges -> Create a new pledge.
     */
    @RequestMapping(value = "/pledges",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Pledge pledge) throws URISyntaxException {
        log.debug("REST request to save Pledge : {}", pledge);
        if (pledge.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pledge cannot already have an ID").build();
        }
        pledgeRepository.save(pledge);
        if(pledgedService!=null) {
            pledgedService.verify(pledge);
        }
        return ResponseEntity.created(new URI("/api/pledges/" + pledge.getId())).build();
    }

    /**
     * PUT  /pledges -> Updates an existing pledge.
     */
    @RequestMapping(value = "/pledges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Pledge pledge) throws URISyntaxException {
        log.debug("REST request to update Pledge : {}", pledge);
        if (pledge.getId() == null) {
            return create(pledge);
        }
        pledgeRepository.save(pledge);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /pledges -> get all the pledges.
     */
    @RequestMapping(value = "/pledges",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pledge> getAll() {
        log.debug("REST request to get all Pledges");
        return pledgeRepository.findAll();
    }

    /**
     * GET  /pledges/:id -> get the "id" pledge.
     */
    @RequestMapping(value = "/pledges/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pledge> get(@PathVariable Long id) {
        log.debug("REST request to get Pledge : {}", id);
        return Optional.ofNullable(pledgeRepository.findOne(id))
            .map(pledge -> new ResponseEntity<>(
                pledge,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pledges/:id -> delete the "id" pledge.
     */
    @RequestMapping(value = "/pledges/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Pledge : {}", id);
        pledgeRepository.delete(id);
    }
}
