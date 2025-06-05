package com.abroad.serviceimpl;

import com.abroad.entity.Continent;
import com.abroad.repository.ContinentRepository;
import com.abroad.service.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContinentServiceImpl implements ContinentService {
    @Autowired
    private ContinentRepository continentRepository;

    @Override
    public Continent createContinent(Continent continent) {
        return continentRepository.save(continent);
    }

    @Override
    public List<Continent> getAllContinents() {
        return continentRepository.findAll();
    }

    @Override
    public Continent getContinentById(Long id) {
        return continentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found with id " + id));
    }

    @Override
    public Continent updateContinent(Long id, Continent continent) {
        Continent existingContinent = getContinentById(id);
        existingContinent.setContinentname(continent.getContinentname());
        return continentRepository.save(existingContinent);
    }

    @Override
    public void deleteContinent(Long id) {
        Continent continent = getContinentById(id);
        continentRepository.delete(continent);
    }
}
