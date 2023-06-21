package com.works.mvcdata.controllers;

import com.works.mvcdata.entities.Product;
import com.works.mvcdata.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class DashBoardController {
    final ProductService productService;
    private Long updateID = 0l;

    @GetMapping("/dashboard")
    public String dashboard(Model model, @RequestParam(defaultValue = "0") int page) {

        model.addAttribute("products", productService.allProduct(page));
        updateID = 0l;
        return "dashboard";
    }

    @GetMapping("/dashboard/{pid}")
    public String dashboardUpdate(Model model, @PathVariable Long pid, @RequestParam(defaultValue = "0") int page) {
        updateID = pid;
        model.addAttribute("products", productService.allProduct(page));
        model.addAttribute("product", productService.getSingleProduct(pid));

        return "dashboardUpdate";
    }

    @PostMapping("/productSave")
    public String productSave(Product product) {
        productService.save(product);
        return "redirect:/dashboard";
    }

    @GetMapping("/productDelete/{stPid}")
    public String productDelete(@PathVariable String stPid) {
        boolean status = productService.productDelete(stPid);
        System.out.println("Status :" + status);
        return "redirect:/dashboard";
    }

    @PostMapping("/productUpdate")
    public String productUpdate(Product product) {
        product.setPid(updateID);
        productService.updateProduct(product);
        System.out.println("product : " + product);
        return "redirect:/dashboard";
    }

}
