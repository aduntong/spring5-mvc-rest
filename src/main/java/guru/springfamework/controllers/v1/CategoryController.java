package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CatorgoryListDTO;
import guru.springfamework.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by jt on 9/26/17.
 */
@Controller
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @RequestMapping(method = RequestMethod.HEAD )
    @GetMapping
    public ResponseEntity<CatorgoryListDTO> getallCatetories(){

        return new ResponseEntity<CatorgoryListDTO>(
                new CatorgoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName( @PathVariable String name){
        return new ResponseEntity<CategoryDTO>(
                categoryService.getCategoryByName(name), HttpStatus.OK
        );
    }

    /**
     * just a joke
     * @return
     * Allow →GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH
     * Content-Length →0
     * Date →Wed, 05 Dec 2018 15:48:07 GMT
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public void joke( HttpServletResponse response ){
        System.out.println("joking");
        response.addHeader( "Allow", "GET" );
        return;
    }
}
