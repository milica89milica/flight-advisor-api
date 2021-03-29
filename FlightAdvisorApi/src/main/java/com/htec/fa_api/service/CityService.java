package com.htec.fa_api.service;


import com.htec.fa_api.exception.HttpException;
import com.htec.fa_api.model.City;
import com.htec.fa_api.repository.CityRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@CacheConfig(cacheNames = {"cities"})
@Service
public class CityService {

    private final CityRepository cityRepository;

    private final MessageSource messageSource;

    private final CountryService countryService;

    public CityService(CityRepository cityRepository, MessageSource messageSource, CountryService countryService) {
        this.cityRepository = cityRepository;
        this.messageSource = messageSource;
        this.countryService = countryService;
    }

    @Cacheable
    public List<City> getAll() {
        return cityRepository.getAllByActive((byte) 1);
    }

    @Cacheable(key = "#name + #countryName")
    public City findByNameAndCountry(String name, String countryName) {
        return cityRepository.findByNameAndCountryNameAndActive(name, countryName, (byte) 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public City insert(City city) {
        return cityRepository.save(city);
    }

    @CachePut(key = "#object.id")
    @Transactional(rollbackFor = Exception.class)
    public City update(City object) throws HttpException {
        Optional<City> city = cityRepository.findById(object.getId());
        if (!countryService.existsById(object.getCountry().getId())) {
            throw new HttpException(messageSource.getMessage("notExists.country", null, null), HttpStatus.BAD_REQUEST);
        }
        city.get().setName(object.getName());
        city.get().setCountry(object.getCountry());
        city.get().setDescription(object.getDescription());
        city.get().setPostalCode(object.getPostalCode());

        return cityRepository.save(city.get());
    }

    @Cacheable(key = "#id")
    public Optional<City> findById(Integer id) {
        return cityRepository.findById(id);
    }

    @CacheEvict(key = "#id")
    @Transactional(rollbackFor = Exception.class)
    public City delete(Integer id) throws HttpException {
        Optional<City> city = cityRepository.findById(id);
        if (!city.isPresent()) {
            throw new HttpException(messageSource.getMessage("notExists.city", null, null), HttpStatus.NOT_FOUND);
        }
        city.get().setActive((byte) 0);
        return cityRepository.save(city.get());
    }

    @Cacheable(key = "#name")
    public List<City> getByNameWithComments(String name) {
        return cityRepository.findAllByActiveAndNameStartingWith((byte) 1, name);
    }

}
