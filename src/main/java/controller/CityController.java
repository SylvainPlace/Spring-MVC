package monprojet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import monprojet.dao.*;
import monprojet.entity.*;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping(path = "/monprojet/city") 
public class CityController {

    @Autowired
    private CityRepository cityDao;
	@Autowired
    private CountryRepository countryDao;

    @GetMapping(path = "show")
	public String montreLesVilles(Model model) {
		model.addAttribute("villes", cityDao.findAll());
		return "showVilles";
	}	

	@GetMapping(path = "add")
	public String montreLeFormulairePourAjout(@ModelAttribute("city") City city, Model model) {
		Country france = countryDao.findById(1).orElseThrow();
		// Creation d'une ville par défaut
		City nouvelle = new City("Nom", france);
		nouvelle.setPopulation(10);
		model.addAttribute("city", nouvelle);
		model.addAttribute("countries", countryDao.findAll());
		return "formulaireVille";
	}
	
	@PostMapping(path = "save")
	public String ajouteLaVillePuisMontreLaListe(City city) {
		// cf. https://www.baeldung.com/spring-data-crud-repository-save
		cityDao.save(city);
		return "redirect:show"; // POST-Redirect-GET : on se redirige vers l'affichage de la liste		
	}

	@GetMapping(path = "edit")
	public String montreLeFormulairePourModif(@RequestParam(name = "id", required = false) City city, Model model) {
		model.addAttribute("city", city);
		model.addAttribute("countries", countryDao.findAll());
		return "formulaireVille";
	}

	@GetMapping(path = "delete")
	public String supprimeUneVillePuisMontreLaListe(@RequestParam("id")  City city) {
		cityDao.delete(city);
		return "redirect:show"; // on se redirige vers l'affichage de la liste
	}
    
}
