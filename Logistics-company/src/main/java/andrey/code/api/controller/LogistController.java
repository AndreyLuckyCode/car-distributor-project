package andrey.code.api.controller;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.LogistDTO;
import andrey.code.api.service.LogistService;
import andrey.code.store.entity.LogistEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class LogistController {

    LogistService logistService;

    private static final String CREATE_LOGIST = "/api/logists";
    private static final String GET_ALL_LOGISTS = "/api/logists";
    private static final String UPDATE_LOGIST = "/api/logists/{logist_id}";
    private static final String DELETE_LOGIST = "/api/logists/{logist_id}";


    @PostMapping(CREATE_LOGIST)
    public LogistDTO createLogist(
            @ModelAttribute LogistEntity logist){

        return logistService.createLogist(logist);
    }

    @GetMapping(GET_ALL_LOGISTS)
    public List<LogistDTO> getAllLogists(){

        return logistService.getAllLogists();
    }

    @PatchMapping(UPDATE_LOGIST)
    public LogistDTO updateLogist(
            @PathVariable("logist_id") Long id,
            @ModelAttribute LogistEntity logist){

        return logistService.updateLogist(id, logist);
    }

    @DeleteMapping(DELETE_LOGIST)
    public AckDTO deleteLogist(
            @PathVariable("logist_id") Long id){

        return logistService.deleteLogist(id);
    }
}
