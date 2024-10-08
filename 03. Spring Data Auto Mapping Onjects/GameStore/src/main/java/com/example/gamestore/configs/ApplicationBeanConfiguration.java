package com.example.gamestore.configs;

import com.example.gamestore.models.dtos.GameAddDto;
import com.example.gamestore.models.entities.Game;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(GameAddDto.class, Game.class)
                .addMappings(m -> m.map(GameAddDto::getThumbnailURL, Game::setImageThumbnail));

//        Converter<String, LocalDate> localDateConverter =
//                new Converter<String, LocalDate>() {
//            @Override
//            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
//                return LocalDate.parse(mappingContext.getSource(),
//                        DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//            }
//        };

//        modelMapper.addConverter(localDateConverter);

        return modelMapper;
    }
}
