package andrey.code.api.service;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.LogistDTO;
import andrey.code.api.exceptions.BadRequestException;
import andrey.code.api.exceptions.NotFoundException;
import andrey.code.api.factory.LogistDTOFactory;
import andrey.code.store.entity.LogistEntity;
import andrey.code.store.repository.LogistRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogistServiceImpl implements LogistService{

    LogistRepository logistRepository;
    LogistDTOFactory logistDTOFactory;


    @Override
    @Transactional
    public LogistDTO createLogist(
            @ModelAttribute LogistEntity logist) {

        if(logist.getName() == null || logist.getName().trim().isEmpty()){
            throw new BadRequestException("Logist name can't be empty");
        }
        if(logist.getEmail() == null || logist.getEmail().trim().isEmpty()){
            throw new BadRequestException("Logist email can't be empty");
        }

        logistRepository.saveAndFlush(logist);

        return logistDTOFactory.createLogistDTO(logist);
    }


    @Override
    @Transactional
    public List<LogistDTO> getAllLogists() {

        List<LogistEntity> logists = logistRepository.findAll();

        if(logists.isEmpty()){
            throw new NotFoundException("Logists list is empty");
        }

        return logists.stream()
                .map(logistDTOFactory::createLogistDTO)
                .toList();
    }


    @Override
    @Transactional
    public LogistDTO updateLogist(
            @PathVariable("logist_id") Long id,
            @ModelAttribute LogistEntity logist) {

        LogistEntity logistEntity = logistRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Logist with this id doesn't exist"));

        if(logist.getName() != null && !logist.getName().trim().isEmpty()){
            logistEntity.setName(logist.getName());
        }
        if(logist.getEmail() != null && !logist.getEmail().trim().isEmpty()){
            logistEntity.setEmail(logist.getEmail());
        }

        logistRepository.saveAndFlush(logistEntity);

        return logistDTOFactory.createLogistDTO(logistEntity);
    }


    @Override
    @Transactional
    public AckDTO deleteLogist(
            @PathVariable("logist_id") Long id) {

        if(logistRepository.findById(id).isEmpty()){
            throw new NotFoundException("Logist with this id doesn't exist");
        }

        logistRepository.deleteById(id);

        return AckDTO.builder().answer(true).build();
    }
}
