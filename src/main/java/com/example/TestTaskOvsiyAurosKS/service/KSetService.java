package com.example.TestTaskOvsiyAurosKS.service;

import com.example.TestTaskOvsiyAurosKS.entity.DHTMLXAdapter;
import com.example.TestTaskOvsiyAurosKS.entity.KPac;
import com.example.TestTaskOvsiyAurosKS.entity.KSet;
import com.example.TestTaskOvsiyAurosKS.exception.NameExistsException;
import com.example.TestTaskOvsiyAurosKS.repository.KSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KSetService {

    @Autowired
    private KSetRepository kSetRepository;

    public DHTMLXAdapter<KSet> getAllSet() {
        List<KSet> kPacsSet = kSetRepository.findAllSet();

        return new DHTMLXAdapter<>(kPacsSet);
    }

    public KSet findKSetById(long id) {
        return kSetRepository.findKSetById(id);
    }

    public void deleteKSet(KSet kSet) {
        kSetRepository.deleteKSet(kSet);
    }

    public void addKSet(KSet kSet, String kPacs) {
        if (kSetRepository.findKSetByTitle(kSet).isPresent()) {
            throw new NameExistsException(kSet.getTitle());
        }
        kSetRepository.addKSet(kSet, kPacs);
    }

    public DHTMLXAdapter<KPac> getKPacsFromKSet(KSet kSet) {
        List<KPac> kPacsFromKSet = kSetRepository.getKPacsFromKSet(kSet);

        return new DHTMLXAdapter<>(kPacsFromKSet);
    }
}
