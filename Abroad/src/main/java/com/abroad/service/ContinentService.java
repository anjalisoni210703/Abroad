package com.abroad.service;

import com.abroad.entity.Continent;

import java.util.List;

public interface ContinentService {
    Continent createContinent(Continent continent);
    List<Continent> getAllContinents();
    Continent getContinentById(Long id);
    Continent updateContinent(Long id, Continent continent);
    void deleteContinent(Long id);
}
