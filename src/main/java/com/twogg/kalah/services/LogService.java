package com.twogg.kalah.services;

import com.twogg.kalah.entities.Log;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogService extends PagingAndSortingRepository<Log, String> {

    List<Log> findByGameId(String gameId);
}

