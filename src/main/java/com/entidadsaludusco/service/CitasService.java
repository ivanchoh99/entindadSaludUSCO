package com.entidadsaludusco.service;

import com.entidadsaludusco.models.entity.Cita;
import com.entidadsaludusco.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CitasService {

    @Autowired
    CitaRepository citaRepository;

    public List<Cita> getCitasByPacienteId(Long id){return citaRepository.getCitasByPacienteId(id);}

    public List<Cita> getCitasByMedicoId(Long id){return citaRepository.getCitasByMedicoId(id);}

    public List<Cita> findAll(){return citaRepository.findAll();}
    public void save(Cita cita){citaRepository.save(cita);}

    public void delet(Long id){citaRepository.deleteById(id);}

    public Cita getCita(Long id){
        return citaRepository.getById(id);
    }

//    public List<Cita> getCitasByFechaHoraByMonth(int id){return citaRepository.getCitaByFechaHoraMonth(id);}

}
