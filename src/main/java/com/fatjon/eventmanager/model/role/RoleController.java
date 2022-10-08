package com.fatjon.eventmanager.model.role;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class RoleController {

    public final RoleRepo roleRepo;

    @PostMapping("/roles/new")
    public ResponseEntity<?> createRole(@RequestBody Role role){
        roleRepo.save(role);
        return new ResponseEntity<>("CREATED!",HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles(){
        return ResponseEntity.ok(roleRepo.findAll());
    }

    @GetMapping("/roles/{roleId}")
    public ResponseEntity<?> getRoleById(@PathVariable Long roleId){
        return ResponseEntity.ok().body(roleRepo.findById(roleId));
    }

}
