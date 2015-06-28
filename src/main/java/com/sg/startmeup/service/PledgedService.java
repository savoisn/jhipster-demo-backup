package com.sg.startmeup.service;

import com.sg.startmeup.domain.Estimation;
import com.sg.startmeup.domain.Pledge;
import com.sg.startmeup.repository.EstimationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;

@Service
@Transactional
public class PledgedService {

    private final Logger log = LoggerFactory.getLogger(PledgedService.class);

    @Inject
    private EstimationRepository estimationRepository;

    public void verify(Pledge pledge) {
        if(pledge != null) {
            Estimation estimation = estimationRepository.getOne(pledge.getEstimation().getId());

            if (estimation != null) {
                log.debug(estimation.toString());
                log.debug(estimation.getPledges().size() + "");
                BigDecimal totalPledged = new BigDecimal(0);
                for (Pledge p : estimation.getPledges()) {
                    totalPledged.add(p.getAmount());
                }
                if (totalPledged.compareTo(estimation.getCost()) >= 0) {
                    estimation.setPledged(true);
                }
            }
        }
    }
}
