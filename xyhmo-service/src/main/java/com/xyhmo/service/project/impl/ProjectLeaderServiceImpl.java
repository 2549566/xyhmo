package com.xyhmo.service.project.impl;

import com.xyhmo.query.project.ProjectCreateReq;
import com.xyhmo.service.project.ProjectLeaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProjectLeaderServiceImpl implements ProjectLeaderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Long createProjectOrder(ProjectCreateReq projectCreateReq) {
        return null;
    }
}
