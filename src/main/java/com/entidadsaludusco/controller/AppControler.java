package com.entidadsaludusco.controller;

import com.entidadsaludusco.PlantillaInformeCita;
import com.entidadsaludusco.models.entity.Cita;
import com.entidadsaludusco.models.entity.Medicamentos;
import com.entidadsaludusco.models.entity.Rol;
import com.entidadsaludusco.models.entity.Usuario;
import com.entidadsaludusco.service.MedicamentosService;
import com.entidadsaludusco.service.CitasService;
import com.entidadsaludusco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class AppControler {

    @Autowired
    CitasService citasService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    MedicamentosService medicamentosService;

    PlantillaInformeCita plantillaInformeCita;

    @PreAuthorize("hasAnyAuthority('MEDICO','ADMIN','PACIENTE')")
    @GetMapping("index")
    public ModelAndView index(Authentication auth) {
        Usuario usuario = usuarioService.getUsuario(Long.valueOf(auth.getName()));
        ModelAndView mv = new ModelAndView();
        mv.addObject("usuario",usuario);
        mv.setViewName("index");
        return mv;
    }


    @GetMapping("/fragments")
    public String fragments(){
        return "fragments";
    }


    @GetMapping(value = {"/","/login"})
    public String login() {
        return "login";
    }


    @GetMapping(value = "/forbidden")
    public String forbidden() {
        return "forbidden";
    }


    @GetMapping(value = "/error")
    public String error() {
        return "error";
    }


    @PreAuthorize("hasAnyAuthority('MEDICO','PACIENTE','ADMIN')")
    @GetMapping("viewCitas")
    public ModelAndView citas(Authentication auth){
        ModelAndView mv = new ModelAndView();

        System.out.println(auth.getAuthorities().getClass());


        for (GrantedAuthority rol: auth.getAuthorities()){

            System.out.println(rol.toString());
            if(rol.toString().equals("MEDICO")){
                List<Cita> citas = citasService.getCitasByMedicoId(Long.valueOf(auth.getName()));
                mv.addObject("citas",citas);
                mv.setViewName("viewCitas");
            }
            if(rol.toString().equals("PACIENTE")){
                List<Cita> citas = citasService.getCitasByPacienteId(Long.valueOf(auth.getName()));
                mv.addObject("citas",citas);
                mv.setViewName("viewCitas");
            }
            if(rol.toString().equals("ADMIN")){
                List<Cita> citas = citasService.findAll();
                mv.addObject("citas",citas);
                mv.setViewName("viewCitas");
            }
        }
        return mv;
    }


    @PreAuthorize("hasAnyAuthority('PACIENTE','ADMIN')")
    @GetMapping("download/{idCita}")
    public String reporte(@PathVariable("idCita")Long idCita){

        Cita cita = citasService.getCita(idCita);
        PlantillaInformeCita nuevoReporte = new PlantillaInformeCita(cita);
        nuevoReporte.crearRegistro();
        return "redirect:/index";
    }


    @PreAuthorize("hasAnyAuthority('PACIENTE','ADMIN','MEDICO')")
    @GetMapping("diagnostico/{idCita}")
    public  ModelAndView diagnostico(@PathVariable("idCita")Long idCita){
        ModelAndView mv = new ModelAndView();

        Cita cita = citasService.getCita(idCita);
        List<Medicamentos> medicamentosList = medicamentosService.findAll();
        mv.addObject("medicamentosList",medicamentosList);


        mv.addObject("cita", cita);
        mv.setViewName("diagnostico");
        return mv;
    }


    @PreAuthorize("hasAnyAuthority('PACIENTE','ADMIN','MEDICO')")
    @PostMapping("/enviarDiagnostico")
    public String enviarDiagnostico(@RequestParam Long idCita,
                                    @RequestParam String diagnostico,
                                    @RequestParam String recomendaciones,
                                    @RequestParam(value = "medicamento",
                                            required = false) Long [] medicamento){

        ModelAndView mv = new ModelAndView();
        Cita cita = citasService.getCita(idCita);

        List<Medicamentos> newListMedicamentos = new ArrayList<>();

        if(medicamento != null) {
            for (int i = 0; i < medicamento.length; i++) {
                Medicamentos medica = medicamentosService.getById(medicamento[i]);
                newListMedicamentos.add(medica);
            }
        }

        cita.setMedicamentos(newListMedicamentos);
        cita.setDiagnostico(diagnostico);
        cita.setRecomendaciones(recomendaciones);
        citasService.save(cita);

        return "redirect:/viewCitas";
    }

}
