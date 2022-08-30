package com.entidadsaludusco.controller;

import com.entidadsaludusco.models.entity.*;
import com.entidadsaludusco.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    DepartamentoService departamentoService;
    @Autowired
    ConsultorioService consultorioService;

    @GetMapping("index")
    public String index(Authentication auth) {
        String user = auth.getAuthorities().toString();
        System.out.println(user);
        return "index";
    }

    @GetMapping("viewMedicos")
    public ModelAndView viewMedicos() {
        ModelAndView mv = new ModelAndView();
        List<Usuario> usuariosMedicos = usuarioService.findUsuariosByRolId(2);
        mv.addObject("usuariosMedicos", usuariosMedicos);
        mv.setViewName("Admin/viewMedicos");
        return mv;
    }

    @GetMapping("viewPacientes")
    public ModelAndView viewPacientes() {
        ModelAndView mv = new ModelAndView();
        List<Usuario> usuariosPacientes = usuarioService.findUsuariosByRolId(3);
        mv.addObject("usuariosPacientes", usuariosPacientes);
        mv.setViewName("Admin/viewPacientes");
        return mv;
    }

    @GetMapping("newConsultorio")
    public ModelAndView newConsultorio() {
        ModelAndView mv = new ModelAndView();
        List<Municipio> municipios = municipioService.lista();
        mv.addObject("municipios", municipios);
        mv.setViewName("Admin/newConsultorio");
        return mv;
    }

    @GetMapping("newUser")
    public String newUser() {
        return "Admin/newUser";
    }


    @GetMapping("newCita/{documento}")
    public ModelAndView creatCita(@PathVariable("documento")Long documento) {

        ModelAndView mv = new ModelAndView();

        if (!usuarioService.existByDocumento(documento)) {
            mv.setViewName("redirect:/newUser");
        }

        String citaFechas = LocalDateTime.now().plusDays(1).toString();
        Usuario paciente = usuarioService.getUsuario(documento);
        List<Usuario> medicos = usuarioService.findUsuariosByRolId(2);
        List<Consultorio> consultorios = consultorioService.getConsultorios();

        mv.addObject("citaFechas",citaFechas);
        mv.addObject("consultorios",consultorios);
        mv.addObject("paciente",paciente);
        mv.addObject("medicos",medicos);
        mv.setViewName("Admin/newCita");
        return mv;
    }


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

    @PostMapping("creatCita")
    public ModelAndView creatCita(@RequestParam Long paciente,@RequestParam Long medico,@RequestParam Long consultorio,@RequestParam String fechaHora){
        ModelAndView mv = new ModelAndView();

        if (!usuarioService.existByDocumento(paciente)) {
            mv.setViewName("redirect:/newUser");
        }

        Cita cita =new Cita();
        LocalDateTime getFechaHora = LocalDateTime.parse(fechaHora);
        Consultorio getConsultorio = consultorioService.getById(consultorio);
        Usuario getMedico = usuarioService.getUsuario(medico);
        Usuario getPaciente = usuarioService.getUsuario(paciente);
        cita.setConsultorio(getConsultorio);
        cita.setPaciente(getPaciente);
        cita.setMedico(getMedico);
        cita.setFechaHora(getFechaHora);

        citasService.save(cita);

        mv.setViewName("redirect:/viewCitas");
        return mv;
    }

    @GetMapping("deleteCita/{idCita}")
    public String deleteCita(@PathVariable Long idCita){
        citasService.delet(idCita);
        return "redirect:/viewCitas";
    }


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
        return mv;
    }

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

    @PostMapping("update")
    public ModelAndView update(@RequestParam Long documento,
                               @RequestParam String direccion,
                               @RequestParam Long celular) {

        ModelAndView mv = new ModelAndView();
        Usuario usuario = usuarioService.getUsuario(documento);
        if (!usuarioService.existByDocumento(documento)) {
            return new ModelAndView("redirect:/admin/view");
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




