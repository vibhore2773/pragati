package com.hackwiz.pragati.allocation;

import com.hackwiz.pragati.dao.redis.JobDetailsEntity;

public interface AllocationHandler {

    boolean processJobAllocation(JobDetailsEntity jobDetailsEntity);
}
