package com.example.TestTaskOvsiyAurosKS.service;

import com.example.TestTaskOvsiyAurosKS.entity.DHTMLXAdapter;
import com.example.TestTaskOvsiyAurosKS.entity.KPac;
import com.example.TestTaskOvsiyAurosKS.exception.NameExistsException;
import com.example.TestTaskOvsiyAurosKS.repository.KPacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KPacService {

    @Autowired
    private KPacRepository kPacRepository;

    public DHTMLXAdapter<KPac> getAllKPacs() {
        List<KPac> kPacs = kPacRepository.findAllKPacs();

        return new DHTMLXAdapter<>(kPacs);
    }

    public KPac findKPacById(long id) {
        return kPacRepository.findKPacById(id);
    }

    public void deleteKPac(KPac kPac) {
        kPacRepository.deleteKPac(kPac);
    }

    public void addKPac(KPac kPac) {
        if (kPacRepository.findKPacByTitle(kPac).isPresent()) {
            throw new NameExistsException(kPac.getTitle());
        }
        kPacRepository.addKPac(kPac);
    }
}
