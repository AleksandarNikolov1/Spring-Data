package com.example.football.service.impl;

import com.example.football.models.dto.StatSeedRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {

    public static final String STATS_FILE_PATH = "src/main/resources/files/xml/stats.xml";
    private final StatRepository statRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public StatServiceImpl(StatRepository statRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.statRepository = statRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        if (!areImported()) {

            StatSeedRootDto statSeedRootDto = xmlParser.fromFile(STATS_FILE_PATH, StatSeedRootDto.class);

            statSeedRootDto
                    .getStatSeedDtoList()
                    .stream()
                    .filter(statSeedDto -> {
                        boolean isValid = validationUtil.isValid(statSeedDto)
                                && !existsByPassingAndShootingAndEndurance(statSeedDto.getPassing()
                                , statSeedDto.getShooting(), statSeedDto.getEndurance());

                        if (isValid) {
                            sb.append(String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                                    statSeedDto.getPassing(), statSeedDto.getShooting(), statSeedDto.getEndurance()));
                        } else {
                            sb.append("Invalid Stat");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(statSeedDto -> modelMapper.map(statSeedDto, Stat.class))
                    .forEach(statRepository::save);

        }

        return sb.toString();
    }

    @Override
    public boolean existsByPassingAndShootingAndEndurance(Double passing, Double shooting, Double endurance) {
        return statRepository.existsByPassingAndShootingAndEndurance(passing, shooting, endurance);
    }

    @Override
    public Stat findById(Long id) {
        return statRepository.findById(id).orElse(null);
    }
}
