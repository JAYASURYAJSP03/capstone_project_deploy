package com.project.capstone.service.impl;

import com.project.capstone.entity.Bus;
import com.project.capstone.repository.BusRepository;
import com.project.capstone.service.BusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BusServiceImpl implements BusService {
    private BusRepository busRepository;

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll().stream()
                .sorted(Comparator.comparingLong(Bus::getId).thenComparing(Bus::getBusName))
                .collect(Collectors.toList());
    }

    @Override
    public void saveBus(Bus bus) {
        busRepository.save(bus);
    }

    @Override
    public Bus getById(Long id) {
        return busRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        busRepository.deleteById(id);
    }

    @Override
    public List<Bus> find( String source,String destination, String date) {
        return busRepository.findBySourceIgnoreCaseAndDestinationIgnoreCaseAndDate(source.trim(),destination.trim(),date);
    }
}
