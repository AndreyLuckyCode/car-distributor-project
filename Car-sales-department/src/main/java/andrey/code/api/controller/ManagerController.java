package andrey.code.api.controller;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.ManagerDTO;
import andrey.code.api.service.ManagerService;
import andrey.code.store.entity.ManagerEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class ManagerController {

    ManagerService managerService;

    private static final String CREATE_MANAGER = "/api/managers";
    private static final String GET_ALL_MANAGERS = "/api/managers";
    private static final String GET_TOP_MANAGERS = "/api/managers/top";
    private static final String UPDATE_MANAGER = "/api/managers/{manager_id}";
    private static final String DELETE_MANAGER = "/api/managers/{manager_id}";


    @PostMapping(CREATE_MANAGER)
    public ManagerDTO createManager(
            @ModelAttribute ManagerEntity manager){

        return managerService.createManager(manager);
    }


    @GetMapping(GET_ALL_MANAGERS)
    public List<ManagerDTO> getAllManagers(){

        return managerService.getAllManagers();
    }


    @GetMapping(GET_TOP_MANAGERS)
    public List<ManagerDTO> getTopManagers(){

        return managerService.getTopManagers();
    }


    @PatchMapping(UPDATE_MANAGER)
    public ManagerDTO updateManager(
            @PathVariable("manager_id") Long id,
            @ModelAttribute ManagerEntity manager){

        return managerService.updateManager(id, manager);
    }


    @DeleteMapping(DELETE_MANAGER)
    public AckDTO deleteManager(
            @PathVariable("manager_id") Long id){

        return managerService.deleteManager(id);
    }
}
