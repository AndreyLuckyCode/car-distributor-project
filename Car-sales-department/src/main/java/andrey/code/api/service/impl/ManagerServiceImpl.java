package andrey.code.api.service.impl;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.ManagerDTO;
import andrey.code.api.exceptions.BadRequestException;
import andrey.code.api.exceptions.NotFoundException;
import andrey.code.api.factory.ManagerDTOFactory;
import andrey.code.api.service.ManagerService;
import andrey.code.store.entity.ManagerEntity;
import andrey.code.store.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    ManagerRepository managerRepository;
    ManagerDTOFactory managerDTOFactory;

    @Override
    @Transactional
    public ManagerDTO createManager(
            @ModelAttribute ManagerEntity manager) {

        if(manager.getName() == null || manager.getName().trim().isEmpty()){
            throw new BadRequestException("Manager name can't be empty");
        }
        if(manager.getEmail() == null || manager.getEmail().trim().isEmpty()){
            throw new BadRequestException("Manager email can't be empty");
        }
        if(manager.getPassword() == null || manager.getPassword().trim().isEmpty()){
            throw new BadRequestException("Manager password can't be empty");
        }

        managerRepository.saveAndFlush(manager);

        return managerDTOFactory.createManagerDTO(manager);
    }


    @Override
    @Transactional
    public List<ManagerDTO> getAllManagers() {

        List<ManagerEntity> managers = managerRepository.findAll();

        if(managers.isEmpty()){
            throw new NotFoundException("Managers list is empty");
        }

        return managers.stream()
                .map(managerDTOFactory::createManagerDTO)
                .toList();
    }


    @Override
    @Transactional
    public List<ManagerDTO> getTopManagers() {

        List<ManagerEntity> topManagers = managerRepository.findAllByOrderByTotalSalesDesc();

        if (topManagers.isEmpty()) {
            throw new NotFoundException("Top Managers list is empty");
        }

        return topManagers.stream()
                .map(managerDTOFactory::createManagerDTO)
                .toList();
    }


    @Override
    @Transactional
    public ManagerDTO updateManager(
            @PathVariable("manager_id") Long id,
            @ModelAttribute ManagerEntity manager) {


        ManagerEntity managerEntity = managerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Manager with this id doesn't exist"));


        if(manager.getName() != null && !manager.getName().trim().isEmpty()){
            managerEntity.setName(manager.getName());
        }
        if(manager.getEmail() != null && !manager.getEmail().trim().isEmpty()){
            managerEntity.setEmail(manager.getEmail());
        }
        if(manager.getPassword() != null && !manager.getPassword().trim().isEmpty()){
            managerEntity.setPassword(manager.getPassword());
        }
        if(manager.getSalary() != null){
            managerEntity.setSalary(manager.getSalary());
        }
        if(manager.getTotalSales() != null){
            managerEntity.setTotalSales(manager.getTotalSales());
        }

        managerRepository.saveAndFlush(managerEntity);

        return managerDTOFactory.createManagerDTO(managerEntity);
    }


    @Override
    @Transactional
    public AckDTO deleteManager(
            @PathVariable("manager_id") Long id) {

        if(managerRepository.findById(id).isEmpty()){
            throw new NotFoundException("Manager with that ID doesn't exist");
        }

        managerRepository.deleteById(id);

        return AckDTO.builder().answer(true).build();
    }
}
