package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.Material;
import com.pilates.estudiopilates.repository.MaterialRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/materiales")
public class AdminMaterialController {

    private final MaterialRepository materialRepository;

    public AdminMaterialController(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @GetMapping
    public String verMateriales(Model model) {
        model.addAttribute("materiales", materialRepository.findAll());
        return "admin-materiales";
    }

    @GetMapping("/nuevo")
    public String nuevoMaterial(Model model) {
        model.addAttribute("material", new Material());
        return "admin-material-form";
    }

    @PostMapping
    public String guardarMaterial(@ModelAttribute Material material,
                                  RedirectAttributes redirectAttributes) {
        materialRepository.save(material);
        redirectAttributes.addFlashAttribute("mensajeOk", "Material creado correctamente");
        return "redirect:/admin/materiales";
    }
}