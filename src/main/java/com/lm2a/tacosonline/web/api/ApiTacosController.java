package com.lm2a.tacosonline.web.api;


import com.lm2a.tacosonline.data.TacoRepository;
import com.lm2a.tacosonline.exceptions.TacoNotFoundException;
import com.lm2a.tacosonline.model.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api", produces = "application/json")
@CrossOrigin(origins = "*")
public class ApiTacosController {

    @Autowired
    TacoRepository tacoRepository;

//    @GetMapping("/tacos/{id}")
//    public Taco tacoById(@PathVariable("id") Long id){
//        Optional<Taco> optTaco = tacoRepository.findById(id);
//        if(optTaco.isPresent()){
//            return optTaco.get();
//        }
//        return null;
//    }

    @GetMapping("/tacos/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id){
        Optional<Taco> optTaco = tacoRepository.findById(id);
        if(optTaco.isPresent()){
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path="/tacos", consumes="application/json")
    public ResponseEntity<Taco> saveTaco(@RequestBody Taco taco){
        Taco savedTaco = tacoRepository.save(taco);
        if(savedTaco != null){
            return new ResponseEntity<>(savedTaco, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @DeleteMapping("/tacos/{id}")
//    public void deleteTaco(@PathVariable("id") Long id){
//        tacoRepository.deleteById(id);
//    }

    @DeleteMapping("/tacos/{id}")
    public ResponseEntity<Taco> deleteTaco(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepository.findById(id);
        if(optTaco.isPresent()) {
            tacoRepository.deleteById(id);
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping("/tacos")
//    public ResponseEntity<Taco> updateTaco(@RequestBody Taco taco) throws TacoNotFoundException {
//            tacoRepository.findById(taco.getId()).orElseThrow(()->new TacoNotFoundException());
//            tacoRepository.save(taco);
//            return new ResponseEntity<>(taco, HttpStatus.OK);
//    }

    @PutMapping("/tacos")

    public ResponseEntity<Taco> updateTaco(@RequestBody Taco taco){
        tacoRepository.findById(taco.getId()).orElseThrow(()->new TacoNotFoundException("Taco", "id", taco.getId()));
        tacoRepository.save(taco);
        return new ResponseEntity<>(taco, HttpStatus.OK);

    }


    @GetMapping("/tacos")
    public ResponseEntity<Iterable<Taco>> getAllTacos() {
        return new ResponseEntity<>(tacoRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/tacos/recents/{page}")
    public ResponseEntity<Iterable<Taco>> getAllTacosOderedAndPaged(@PathVariable("page") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 5,  Sort.by("createdAt").descending());
        return new ResponseEntity<>(tacoRepository.findAll(pageRequest),HttpStatus.OK);
    }
}
