package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.UserSeedDto;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

@Service
public class UserServiceImpl implements UserService {
    public static final String USERS_FILE_PATH = "src/main/resources/files/users.json";
    private final UserRepository userRepository;
    private final PictureService pictureService;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PictureService pictureService, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.pictureService = pictureService;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_FILE_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb = new StringBuilder();

        if (!areImported()) {
            UserSeedDto[] userSeedDtos = gson.fromJson(readFromFileContent(), UserSeedDto[].class);

            Arrays.stream(userSeedDtos)
                    .filter(userSeedDto -> {
                        boolean isValid = validationUtil.isValid(userSeedDto)
                                && pictureService.isPictureExistsByPath(userSeedDto.getProfilePicture())
                                && !isUserExistsByUsername(userSeedDto.getUsername());

                        if (isValid) {
                            sb.append(String.format("Successfully imported User: %s",
                                    userSeedDto.getUsername()));
                        } else {
                            sb.append("Invalid User");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(userSeedDto -> {
                        User user = modelMapper.map(userSeedDto, User.class);
                        user.setProfilePicture(pictureService.findByPath(userSeedDto.getProfilePicture()));

                        return user;
                    })
                    .forEach(userRepository::save);
        }

        return sb.toString();
    }

    @Override
    public boolean isUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        if (isUserExistsByUsername(username))
            return userRepository.findByUsername(username);

        return null;
    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder sb = new StringBuilder();

        userRepository
                .findAllByUsernameAndPostsCountDesc()
                        .stream()
                                .forEach(u -> {
                                    sb.append(String.format("User: %s\n" +
                                            "Post count: %d\n", u.getUsername(), u.getPosts().size()));

                                    u.getPosts()
                                            .stream()
                                            .sorted(Comparator.comparing(post -> post.getPicture().getSize()))
                                            .forEach(post -> {
                                                sb.append("==Post Details:\n");
                                                sb.append(String.format("----Caption: %s\n", post.getCaption()));
                                                sb.append(String.format("----Picture Size: %.2f\n", post.getPicture().getSize()));
                                            });
                                });

        return sb.toString();
    }
}
