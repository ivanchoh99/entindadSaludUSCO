package com.entidadsaludusco.controller;
import com.entidadsaludusco.models.entity.*;
import com.entidadsaludusco.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RolService rolService;
    @Autowired
    CitasService citasService;
    @Autowired
    MunicipioService municipioService;
    @Autowired
    ConsultorioService consultorioService;

    private boolean citaPosible = true;
    private boolean cruceConsultorioBoolean = false;
    private String cruceConsultorio = "La hora que selecciono para ese consultorio no es posible";
    private boolean crucePacienteBoolean = false;
    private String crucePaciente ="Ya tiene alguna cita asignada a esa hora";
    private boolean cruceMedicoBoolean = false;
    private String cruceMedico = "El medico que selecciono ya tiene otra cita asignada a esa hora";

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("viewMedicos")
    public ModelAndView viewMedicos() {
        ModelAndView mv = new ModelAndView();
        List<Usuario> usuariosMedicos = usuarioService.findUsuariosByRolId(2L);
        mv.addObject("usuariosMedicos", usuariosMedicos);
        mv.setViewName("Admin/viewMedicos");
        return mv;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("viewPacientes")
    public ModelAndView viewPacientes() {
        ModelAndView mv = new ModelAndView();
        List<Usuario> usuariosPacientes = usuarioService.findUsuariosByRolId(3L);
        mv.addObject("usuariosPacientes", usuariosPacientes);
        mv.setViewName("Admin/viewPacientes");
        return mv;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("newConsultorio")
    public ModelAndView newConsultorio() {
        ModelAndView mv = new ModelAndView();
        List<Municipio> municipios = municipioService.lista();
        mv.addObject("municipios", municipios);
        mv.setViewName("Admin/newConsultorio");
        return mv;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("newUser")
    public String newUser() {
        return "Admin/newUser";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("newCita/{documento}")
    public ModelAndView creatCita(@PathVariable("documento")Long documento) {

        ModelAndView mv = new ModelAndView();
        mv.addObject("error");

        if (!usuarioService.existByDocumento(documento)) {
            mv.setViewName("redirect:/newUser");
        }

        String citaFechas = LocalDateTime.now().plusDays(1).toString();
        Usuario paciente = usuarioService.getUsuario(documento);
        List<Usuario> medicos = usuarioService.findUsuariosByRolId(2L);
        List<Consultorio> consultorios = consultorioService.getConsultorios();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String fechaMin = now.format(formatter);
        mv.addObject("fechaMin",fechaMin.toString());

        mv.addObject("citaFechas",citaFechas);
        mv.addObject("consultorios",consultorios);
        mv.addObject("paciente",paciente);
        mv.addObject("medicos",medicos);
        mv.setViewName("Admin/newCita");

        if (!citaPosible){
            if(cruceConsultorioBoolean){
                mv.addObject("error",cruceConsultorio);
            }
            if (crucePacienteBoolean){
                mv.addObject("error",crucePaciente);
            }
            if (cruceMedicoBoolean){
                mv.addObject("error",cruceMedico);
            }
        }
        return mv;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("creatConsultorio")
    public ModelAndView creatConsultorio(@RequestParam Long municipio) {
        ModelAndView mv = new ModelAndView();
        Consultorio consultorio = new Consultorio();
        Municipio getMunicipio = municipioService.getMunicipio(municipio);
        consultorio.setMunicipio(getMunicipio);
        consultorioService.save(consultorio);
        mv.setViewName("redirect:/viewMedicos");
        return mv;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("creatCita")
    public ModelAndView creatCita(@RequestParam Long paciente,
                                  @RequestParam Long medico,
                                  @RequestParam Long consultorio,
                                  @RequestParam String fechaHora){
        ModelAndView mv = new ModelAndView();

        List<Cita> allCitas = citasService.findAll();

        if (!usuarioService.existByDocumento(paciente)) {
            mv.setViewName("redirect:/newUser");
        }

        for (Cita cita: allCitas){

            System.out.println(cita.getFechaHora().toString()+"/"+
                    cita.getConsultorio().getId()+"/"+
                    cita.getPaciente().getDocumento()+"/"+
                    cita.getMedico().getDocumento());
            System.out.println(fechaHora+"/"+
                    consultorio+"/"+
                    paciente+"/"+
                    medico);

            if (cita.getFechaHora().toString().equals(fechaHora)  && cita.getConsultorio().getId().equals(consultorio)) {
                System.out.println("ERROR");
                cruceConsultorioBoolean = true;
                citaPosible = false;
                break;
            }
            if (cita.getFechaHora().toString().equals(fechaHora) && cita.getPaciente().getDocumento().equals(paciente)){
                System.out.println("ERROR");
                crucePacienteBoolean = true;
                citaPosible = false;
                break;
            }
            if (cita.getFechaHora().toString().equals(fechaHora) && cita.getMedico().getDocumento().equals(medico)){
                System.out.println("ERROR");
                cruceMedicoBoolean = true;
                citaPosible = false;
                break;
            }
        }

        if(citaPosible) {
            Cita nuevaCita = new Cita();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime formatfechaHora = LocalDateTime.parse(fechaHora, formatter);

            Consultorio getConsultorio = consultorioService.getById(consultorio);
            Usuario getMedico = usuarioService.getUsuario(medico);
            Usuario getPaciente = usuarioService.getUsuario(paciente);
            nuevaCita.setConsultorio(getConsultorio);
            nuevaCita.setPaciente(getPaciente);
            nuevaCita.setMedico(getMedico);
            nuevaCita.setFechaHora(formatfechaHora);


            citasService.save(nuevaCita);
            mv.setViewName("redirect:/viewCitas");
            return mv;
        }
        mv.setViewName("redirect:/newCita/"+paciente);
        return mv;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("deleteCita/{idCita}")
    public String deleteCita(@PathVariable Long idCita){
        citasService.delet(idCita);
        return "redirect:/viewCitas";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("creatUser")
    public ModelAndView creatUser(@RequestParam Long documento, @RequestParam String password,
                                  @RequestParam String nombre, @RequestParam String apellido,
                                  @RequestParam String direccion, @RequestParam Long celular,
                                  @RequestParam Integer rol) {

        ModelAndView mv = new ModelAndView();

        Usuario usuario = new Usuario();
        if (usuarioService.existByDocumento(documento)) {
            mv.setViewName("Admin/newUser");
            mv.addObject("error", "ese usuario ya existe en la base de datos");
            return mv;
        }
        if (celular < 1000000000) {
            mv.setViewName("Admin/newUser");
            mv.addObject("error", "ingrese un  celular valido");
            return mv;
        }
        if (StringUtils.isBlank(nombre) || StringUtils.isBlank(password) || StringUtils.isBlank(apellido) || StringUtils.isBlank(direccion)) {
            mv.setViewName("Admin/newUser");
            mv.addObject("error", "nombre, apellido, constrasena y direccion no pueden estar vacios");
            return mv;
        }

        usuario.setDocumento(documento);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDireccion(direccion);
        usuario.setCelular(celular);

        Rol rolUser = rolService.findById(rol);
        Set<Rol> roles = new HashSet<>();
        roles.add(rolUser);
        usuario.setRoles(roles);

        usuarioService.save(usuario);
        mv.setViewName("redirect:/viewMedicos");
        if(usuario.getRoles().contains("MEDICO")){
            mv.setViewName("redirect:/viewMedicos");
        }
        if(usuario.getRoles().contains("PACIENTE")){
            mv.setViewName("redirect:/viewPacientes");
        }
        return mv;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("edit/{documento}")
    public ModelAndView edit(@PathVariable("documento") Long documento) {
        ModelAndView mv = new ModelAndView();
        if (!usuarioService.existByDocumento(documento)) {
            mv.setViewName("redirect:/viewMedicos");
            return mv;
        }
        Usuario usuario = usuarioService.getUsuario(documento);
        mv.addObject("usuario", usuario);
        mv.setViewName("Admin/edit");
        return mv;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    public ModelAndView update(@RequestParam Long documento,
                               @RequestParam String direccion,
                               @RequestParam Long celular) {

        ModelAndView mv = new ModelAndView();
        Usuario usuario = usuarioService.getUsuario(documento);

        if (!usuarioService.existByDocumento(documento)) {
            return new ModelAndView("redirect:/viewMedicos");
        }
        if (StringUtils.isBlank(direccion)) {
            mv.setViewName("Admin/edit");
            mv.addObject("usuario", usuario);
            mv.addObject("error", "direccion no pueden estar vacio");
            return mv;
        }
        if (celular < 1000000000) {
            mv.setViewName("Admin/edit");
            mv.addObject("usuario", usuario);
            mv.addObject("error", "ingrese un  celular valido");
            return mv;
        }

        usuario.setDireccion(direccion);
        usuario.setCelular(celular);
        usuarioService.save(usuario);
        mv.setViewName("redirect:/viewMedicos");
        return mv;
    }


}




