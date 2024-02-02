package andrey.code.api.service;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.LogistDTO;
import andrey.code.store.entity.LogistEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LogistService {

    public LogistDTO createLogist(
            @ModelAttribute LogistEntity logist);

    public List<LogistDTO> getAllLogists();

    public LogistDTO updateLogist(
            @PathVariable("logist_id") Long id,
            @ModelAttribute LogistEntity logist);

    public AckDTO deleteLogist(@PathVariable("logist_id") Long id);

}
