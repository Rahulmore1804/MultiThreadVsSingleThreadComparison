package com.MultiThreadVsSingleThreadComparision.Services;



import com.MultiThreadVsSingleThreadComparision.Repo.MySQLRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class DbCallService {

    @Autowired
    private MySQLRepo mySQLRepo;

    private static final int THREAD_COUNT = 5;
    private static final int THREAD_COUNT_Single = 1;


    @Transactional
    public void updateDataWithThreads(String newStatus) {
        long startTime = System.currentTimeMillis(); // Capture start time

        long totalRecords = mySQLRepo.count();
        long batchSize = totalRecords / THREAD_COUNT;

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<Void>> futures = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            long startId = i * batchSize + 1;
            long endId = (i == THREAD_COUNT - 1) ? totalRecords : (startId + batchSize - 1);

            Future<Void> future = executorService.submit(() -> {
                try {
                    updateDataWithTransaction(startId, endId, newStatus);
                } catch (Exception e) {
                    System.out.println("Exception in thread: " + e.getMessage());
                    throw e;
                }
                return null;
            });

            futures.add(future);
        }

        // Wait for all threads to complete
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        long endTime = System.currentTimeMillis(); // Capture end time
        long totalTimeMillis = endTime - startTime; // Calculate total time

        System.out.println("Total time taken for multi-threaded update: " + totalTimeMillis + " milliseconds.");

    }

    @Transactional
    public void updateDataWithTransaction(Long startId, Long endId, String newStatus) {
        mySQLRepo.updateStatusByIdRange(startId, endId, newStatus);
    }

    @Transactional
    public void updateDataSingleThread(String newStatus) {
        long startTime = System.currentTimeMillis(); // Start time

        long totalRecords = mySQLRepo.count();
        long batchSize = totalRecords / THREAD_COUNT_Single; // 1 thread means 1 batch

        // Single thread processing (one batch at a time)
        for (int i = 0; i < THREAD_COUNT_Single; i++) {
            long startId = i * batchSize + 1;
            long endId = (i == THREAD_COUNT_Single - 1) ? totalRecords : (startId + batchSize - 1);

            try {
                updateDataWithTransaction(startId, endId, newStatus);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                throw e;
            }
        }

        long endTime = System.currentTimeMillis(); // End time
        long totalTimeMillis = endTime - startTime;

        System.out.println("Total time taken for single-threaded update: " + totalTimeMillis + " milliseconds.");
    }
}
