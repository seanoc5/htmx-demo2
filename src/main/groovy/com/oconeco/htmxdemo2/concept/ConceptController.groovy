package com.oconeco.htmxdemo2.concept;

import com.oconeco.htmxdemo2.util.WebUtils;
import groovy.util.logging.Slf4j;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/concepts")
public class ConceptController {

    private final ConceptService conceptService;

    public ConceptController(final ConceptService conceptService) {
        this.conceptService = conceptService;
    }

    @GetMapping(['', '/', '/index'])
    public String list(@RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final Page<ConceptDTO> concepts = conceptService.findAll(filter, pageable);
        log.info("({}) list concepts totalcount:({}) in {} pages) -- with filter:'{}'",concepts.getContent().size(), concepts.getTotalElements(), concepts.getTotalPages(), filter);
        model.addAttribute("concepts", concepts);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(concepts));
        return "concept/index";
    }

    @GetMapping(['/filter'])
    public String filter(@RequestParam(name = "filter", required = true) final String filter,
            @SortDefault(sort = "address") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final Page<ConceptDTO> concepts = conceptService.filter(filter, pageable);
        log.info("({}) filtered concepts totalcount:({}) in {} pages) -- with filter:'{}'",concepts.getContent().size(), concepts.getTotalElements(), concepts.getTotalPages(), filter);
        model.addAttribute("concepts", concepts);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(concepts));
        return "concept/index";
    }

    @GetMapping(['/sayt'])
    public String searchAsYouType(@RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "address") @PageableDefault(size = 10) final Pageable pageable,
            final Model model) {
        final Page<ConceptDTO> concepts = (filter != null && !filter.isEmpty()) 
            ? conceptService.search(filter, pageable) 
            : conceptService.findAll(null, pageable);
        log.info("SAYT: ({}) filtered concepts totalcount:({}) in {} pages) -- with filter:'{}'",
            concepts.getContent().size(), concepts.getTotalElements(), concepts.getTotalPages(), filter);
        model.addAttribute("concepts", concepts);
        model.addAttribute("filter", filter);
        return "concept/fragments/concept-list :: conceptList";
    }

    @GetMapping(['/search'])
    public String search(@RequestParam(name = "filter", required = true) final String filter,
            @SortDefault(sort = "address") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final Page<ConceptDTO> concepts = conceptService.search(filter, pageable);
        log.info("({}) filtered concepts totalcount:({}) in {} pages) -- with filter:'{}'",concepts.getContent().size(), concepts.getTotalElements(), concepts.getTotalPages(), filter);
        model.addAttribute("concepts", concepts);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(concepts));
        return "concept/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("concept") final ConceptDTO conceptDTO) {
        return "concept/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("concept") @Valid final ConceptDTO conceptDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "concept/add";
        }
        conceptService.create(conceptDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("concept.create.success"));
        return "redirect:/concepts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("concept", conceptService.get(id));
        return "concept/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("concept") @Valid final ConceptDTO conceptDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "concept/edit";
        }
        conceptService.update(id, conceptDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("concept.update.success"));
        return "redirect:/concepts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        conceptService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("concept.delete.success"));
        return "redirect:/concepts";
    }

}
