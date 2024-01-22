package andrey.code.api.service;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.ManagerDTO;
import andrey.code.store.entity.ManagerEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ManagerService {

    public ManagerDTO createManager(
            @ModelAttribute ManagerEntity manager);

    public List<ManagerDTO> getAllManagers();

    public List<ManagerDTO> getTopManagers();

    public ManagerDTO updateManager(
            @PathVariable("manager_id") Long id,
            @ModelAttribute ManagerEntity manager);

    public AckDTO deleteManager(
            @PathVariable("manager_id") Long id);


}
