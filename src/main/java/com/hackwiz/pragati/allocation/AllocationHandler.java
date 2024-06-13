package com.hackwiz.pragati.allocation;

import com.hackwiz.pragati.dao.redis.JobDetailsEntity;

public interface AllocationHandler {

    void processJobAllocation(JobDetailsEntity jobDetailsEntity);
}
