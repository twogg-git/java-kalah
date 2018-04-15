package com.twogg.kalah.services;

import com.twogg.kalah.entities.Log;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public interface LogService extends PagingAndSortingRepository<Log, String> {
}

