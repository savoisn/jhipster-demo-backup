package com.sg.startmeup.web.rest;

import com.sg.startmeup.Application;
import com.sg.startmeup.domain.Pledge;
import com.sg.startmeup.repository.PledgeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PledgeResource REST controller.
 *
 * @see PledgeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PledgeResourceTest {


    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);
    private static final String DEFAULT_COMMENT = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENT = "UPDATED_TEXT";

    @Inject
    private PledgeRepository pledgeRepository;

    private MockMvc restPledgeMockMvc;

    private Pledge pledge;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PledgeResource pledgeResource = new PledgeResource();
        ReflectionTestUtils.setField(pledgeResource, "pledgeRepository", pledgeRepository);
        this.restPledgeMockMvc = MockMvcBuilders.standaloneSetup(pledgeResource).build();
    }

    @Before
    public void initTest() {
        pledge = new Pledge();
        pledge.setAmount(DEFAULT_AMOUNT);
        pledge.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createPledge() throws Exception {
        int databaseSizeBeforeCreate = pledgeRepository.findAll().size();

        // Create the Pledge
        restPledgeMockMvc.perform(post("/api/pledges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pledge)))
                .andExpect(status().isCreated());

        // Validate the Pledge in the database
        List<Pledge> pledges = pledgeRepository.findAll();
        assertThat(pledges).hasSize(databaseSizeBeforeCreate + 1);
        Pledge testPledge = pledges.get(pledges.size() - 1);
        assertThat(testPledge.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPledge.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllPledges() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        // Get all the pledges
        restPledgeMockMvc.perform(get("/api/pledges"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pledge.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getPledge() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        // Get the pledge
        restPledgeMockMvc.perform(get("/api/pledges/{id}", pledge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pledge.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPledge() throws Exception {
        // Get the pledge
        restPledgeMockMvc.perform(get("/api/pledges/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePledge() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

		int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();

        // Update the pledge
        pledge.setAmount(UPDATED_AMOUNT);
        pledge.setComment(UPDATED_COMMENT);
        restPledgeMockMvc.perform(put("/api/pledges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pledge)))
                .andExpect(status().isOk());

        // Validate the Pledge in the database
        List<Pledge> pledges = pledgeRepository.findAll();
        assertThat(pledges).hasSize(databaseSizeBeforeUpdate);
        Pledge testPledge = pledges.get(pledges.size() - 1);
        assertThat(testPledge.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPledge.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deletePledge() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

		int databaseSizeBeforeDelete = pledgeRepository.findAll().size();

        // Get the pledge
        restPledgeMockMvc.perform(delete("/api/pledges/{id}", pledge.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pledge> pledges = pledgeRepository.findAll();
        assertThat(pledges).hasSize(databaseSizeBeforeDelete - 1);
    }
}
