package xyz.dreature.cms.interaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public abstract class BaseInteractionService {

    @Autowired
    private RestTemplate restTemplate;

}
