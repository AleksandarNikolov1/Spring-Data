package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.PictureSeedDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {
    public static final String PICTURES_FILE_PATH = "src/main/resources/files/pictures.json";
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        if (!areImported()) {
            PictureSeedDto[] pictureSeedDtos = gson.fromJson(readFromFileContent(), PictureSeedDto[].class);

            Arrays.stream(pictureSeedDtos)
                    .filter(pictureSeedDto -> {
                        boolean isValid = validationUtil.isValid(pictureSeedDto)
                                && !isPictureExistsByPath(pictureSeedDto.getPath());

                        if (isValid) {
                            sb.append(String.format("Successfully imported Picture, with size %.2f",
                                    pictureSeedDto.getSize()));
                        } else {
                            sb.append("Invalid Picture");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(pictureSeedDto -> modelMapper.map(pictureSeedDto, Picture.class))
                    .forEach(pictureRepository::save);
        }
        return sb.toString();
    }


    @Override
    public boolean isPictureExistsByPath(String path) {
        return pictureRepository.existsByPath(path);
    }

    @Override
    public Picture findByPath(String path) {

        if (isPictureExistsByPath(path))
            return pictureRepository.findByPath(path);

        else
            return null;
    }

    @Override
    public String exportPictures() {
        StringBuilder sb = new StringBuilder();

        pictureRepository
                .findAllBySizeGreaterThan30000()
                .forEach(p -> {
                    sb.append(String.format("%.2f - %s\n",
                            p.getSize(), p.getPath()));
                });

     return sb.toString();
    }
}
