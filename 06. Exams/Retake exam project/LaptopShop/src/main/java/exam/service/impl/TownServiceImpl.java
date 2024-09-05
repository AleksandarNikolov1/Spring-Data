package exam.service.impl;

import exam.model.dtos.TownSeedRootDto;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {

    private static final String TOWNS_FILE_PATH = "src/main/resources/files/xml/towns.xml";
    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public TownServiceImpl(TownRepository townRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        if (!areImported()){
            TownSeedRootDto townSeedRootDto = xmlParser.fromFile(TOWNS_FILE_PATH, TownSeedRootDto.class);

            townSeedRootDto
                    .getTownSeedDtoList()
                    .stream()
                    .filter(townSeedDto -> {
                        boolean isValid = validationUtil.isValid(townSeedDto);

                        if (isValid){
                            sb.append(String.format("Successfully imported Town %s",
                                    townSeedDto.getName()));
                        }

                        else{
                            sb.append("Invalid town");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(townSeedDto -> modelMapper.map(townSeedDto, Town.class))
                    .forEach(townRepository::save);
        }

        return sb.toString();
    }



    @Override
    public Town findTownByName(String name) {
        return townRepository.findTownByName(name);
    }
}
