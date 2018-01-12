package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Price;
import io.github.jhipster.application.repository.PriceRepository;
import io.github.jhipster.application.service.dto.PriceDTO;
import io.github.jhipster.application.service.mapper.PriceMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PriceResource REST controller.
 *
 * @see PriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class PriceResourceIntTest {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceMapper priceMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriceMockMvc;

    private Price price;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriceResource priceResource = new PriceResource(priceRepository, priceMapper);
        this.restPriceMockMvc = MockMvcBuilders.standaloneSetup(priceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Price createEntity(EntityManager em) {
        Price price = new Price()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return price;
    }

    @Before
    public void initTest() {
        price = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrice() throws Exception {
        int databaseSizeBeforeCreate = priceRepository.findAll().size();

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);
        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isCreated());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeCreate + 1);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPrice.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceRepository.findAll().size();

        // Create the Price with an existing ID
        price.setId(1L);
        PriceDTO priceDTO = priceMapper.toDto(price);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrices() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList
        restPriceMockMvc.perform(get("/api/prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(price.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", price.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(price.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrice() throws Exception {
        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Update the price
        Price updatedPrice = priceRepository.findOne(price.getId());
        // Disconnect from session so that the updates on updatedPrice are not directly saved in db
        em.detach(updatedPrice);
        updatedPrice
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        PriceDTO priceDTO = priceMapper.toDto(updatedPrice);

        restPriceMockMvc.perform(put("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isOk());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPrice.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrice() throws Exception {
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPriceMockMvc.perform(put("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isCreated());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);
        int databaseSizeBeforeDelete = priceRepository.findAll().size();

        // Get the price
        restPriceMockMvc.perform(delete("/api/prices/{id}", price.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Price.class);
        Price price1 = new Price();
        price1.setId(1L);
        Price price2 = new Price();
        price2.setId(price1.getId());
        assertThat(price1).isEqualTo(price2);
        price2.setId(2L);
        assertThat(price1).isNotEqualTo(price2);
        price1.setId(null);
        assertThat(price1).isNotEqualTo(price2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceDTO.class);
        PriceDTO priceDTO1 = new PriceDTO();
        priceDTO1.setId(1L);
        PriceDTO priceDTO2 = new PriceDTO();
        assertThat(priceDTO1).isNotEqualTo(priceDTO2);
        priceDTO2.setId(priceDTO1.getId());
        assertThat(priceDTO1).isEqualTo(priceDTO2);
        priceDTO2.setId(2L);
        assertThat(priceDTO1).isNotEqualTo(priceDTO2);
        priceDTO1.setId(null);
        assertThat(priceDTO1).isNotEqualTo(priceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priceMapper.fromId(null)).isNull();
    }
}
