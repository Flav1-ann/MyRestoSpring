package eu.ensup.MyResto.controller;


import eu.ensup.MyResto.domaine.Product;
import eu.ensup.MyResto.domaine.User;
import eu.ensup.MyResto.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.ensup.MyResto.model.Types.*;

@Log4j2
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OpinionsService opinionsService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String viewHome(Model model) {
        log.info("viewHome");


        List<Product> entrees  = new ArrayList<Product>(){ };
        List<Product> plats  = new ArrayList<Product>(){ };
        List<Product> desserts  = new ArrayList<Product>(){ };
        List<Product> boissons = new ArrayList<Product>(){ };

        for (Product produc : productService.getAll()) {
            switch (produc.getType()) {
                case BOISSON:
                    boissons.add(produc);
                    break;
                case ENTREE:
                    entrees.add(produc);
                    break;
                case PLAT:
                    plats.add(produc);
                    break;
                case DESSERT:
                    desserts.add(produc);
                    break;
                default:
                    break;
            }
        }
        //orderService.save(new Orders(12.1f,null,null,entrees, States.CREATED,userService.getOne(1l).get()));

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        model.addAttribute("user", new User());
        model.addAttribute("entrees",  entrees);
        model.addAttribute("plats",  plats);
        model.addAttribute("desserts",  desserts);
        model.addAttribute("boissons",  boissons);
        return "home";
    }
    @PostMapping("/loginsuccess")
    public String loginsuccess(@ModelAttribute User user,HttpSession session) {
        return "redirect:/";
    }
    @RequestMapping(value = "/addProductShoppingCard/{id}")
    public String addShoppingCard(@PathVariable("id") Long id,  Model model, HttpSession session)
    {
        log.info("addShoppingCard");
        Map<Long, Integer> productIds = new HashMap<>();
        if( session.getAttribute("ShoppingCard") == null )
        {
            productIds.put(id, 1);
            session.setAttribute("ShoppingCard",productIds);
        }
        else
        {
            productIds = (Map<Long, Integer>) session.getAttribute("ShoppingCard");
            if( productIds.containsKey(id) )
                productIds.put(id, productIds.get(id)+1);
            else
                productIds.put(id, 1);
            session.setAttribute("ShoppingCard",productIds);
        }
        return "redirect:/";
    }
    @RequestMapping(value = "/shoppingcard")
    public String addShoppingCard(Model model, HttpSession session)
    {
        log.info("addShoppingCard");
        Map<Product, Integer> products = new HashMap<>();

        if( session.getAttribute("ShoppingCard") != null ) {
            Map<Long, Integer> ids = (Map<Long, Integer>) session.getAttribute("ShoppingCard");
            for(Long id : ids.keySet() )
                products.put(productService.getOne(id), ids.get(id));
        }
        model.addAttribute("products", products);
        return "shoppingCard";
    }

    @RequestMapping(value = "/shoppingcard/more/{id}")
    public String moreProduct(@PathVariable("id") Long id,  Model model, HttpSession session)
    {
        log.info("moreProduct");
        Map<Long, Integer> mapProduct = (Map<Long, Integer>) session.getAttribute("ShoppingCard");
        if( mapProduct != null && ! mapProduct.isEmpty() && mapProduct.keySet().contains(id) )
        {
            mapProduct.put(id, mapProduct.get(id)+1);
            session.setAttribute("ShoppingCard",mapProduct);
        }

        return "redirect:/shoppingcard";
    }

    @RequestMapping(value = "/shoppingcard/less/{id}")
    public String lessProduct(@PathVariable("id") Long id,  Model model, HttpSession session)
    {
        log.info("lessProduct");
        Map<Long, Integer> mapProduct = (Map<Long, Integer>) session.getAttribute("ShoppingCard");
        if( mapProduct != null && ! mapProduct.isEmpty() && mapProduct.keySet().contains(id) )
        {
            int number = mapProduct.get(id)-1;
            if( number > 0 )  mapProduct.put(id, number);
            else              mapProduct.remove(id);

            session.setAttribute("ShoppingCard",mapProduct);
        }

        return "redirect:/shoppingcard";
    }

    @RequestMapping(value = "/shoppingcard/remove/{id}")
    public String removeProduct(@PathVariable("id") Long id,  Model model, HttpSession session)
    {
        log.info("removeProduct");
        Map<Long, Integer> mapProduct = (Map<Long, Integer>) session.getAttribute("ShoppingCard");
        if( mapProduct != null && ! mapProduct.isEmpty() && mapProduct.keySet().contains(id) )
        {
            mapProduct.remove(id);
            session.setAttribute("ShoppingCard",mapProduct);
        }

        return "redirect:/shoppingcard";
    }
}
