package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.PostSeedRootDto;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {
    public static final String POSTS_FILE_PATH = "src/main/resources/files/posts.xml";
    private final PostRepository postRepository;
    private final PictureService pictureService;
    private final UserService userService;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;


    public PostServiceImpl(PostRepository postRepository, PictureService pictureService, UserService userService, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.pictureService = pictureService;
        this.userService = userService;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_FILE_PATH));

    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        if(!areImported()) {

            PostSeedRootDto postSeedRootDto = xmlParser
                    .fromFile(POSTS_FILE_PATH, PostSeedRootDto.class);

            postSeedRootDto
                    .getPostSeedDtoList()
                    .stream()
                    .filter(postSeedDto -> {
                        boolean isValid = validationUtil.isValid(postSeedDto)
                                && pictureService.isPictureExistsByPath(postSeedDto.getPicturePathDto().getPath())
                                && userService.isUserExistsByUsername(postSeedDto.getUserNameDto().getUsername());

                        if (isValid) {
                            sb.append(String.format("Successfully imported Post, made by %s",
                                    postSeedDto.getUserNameDto().getUsername()));
                        } else {
                            sb.append("Invalid Post");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(postSeedDto -> {
                        Post post = modelMapper.map(postSeedDto, Post.class);
                        post.setPicture(pictureService.findByPath(postSeedDto.getPicturePathDto().getPath()));
                        post.setUser(userService.findByUsername(postSeedDto.getUserNameDto().getUsername()));

                        return post;
                    })
                    .forEach(postRepository::save);
        }

        return sb.toString();
    }
}
