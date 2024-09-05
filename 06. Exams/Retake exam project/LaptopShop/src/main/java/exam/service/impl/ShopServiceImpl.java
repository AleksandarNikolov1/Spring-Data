package exam.service.impl;

import exam.model.dtos.ShopSeedRootDto;
import exam.model.entities.Shop;
import exam.repository.ShopRepository;
import exam.service.ShopService;
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
public class ShopServiceImpl implements ShopService {
    private static final String SHOPS_FILE_PATH = "src/main/resources/files/xml/shops.xml";
    private final ShopRepository shopRepository;
    private final TownService townService;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public ShopServiceImpl(ShopRepository shopRepository, TownService townService, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.townService = townService;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOPS_FILE_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        if (!areImported()) {
            ShopSeedRootDto shopSeedRootDto = xmlParser.fromFile(SHOPS_FILE_PATH, ShopSeedRootDto.class);

            shopSeedRootDto
                    .getShopSeedDtoList()
                    .stream()
                    .filter(shopSeedDto -> {
                        boolean isValid = validationUtil.isValid(shopSeedDto)
                               && !existsByName(shopSeedDto.getName());

                        if (isValid) {
                            sb.append(String.format("Successfully imported Shop %s",
                                    shopSeedDto.getName()));
                        } else {
                            sb.append("Invalid shop");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(shopSeedDto -> {
                        Shop shop = modelMapper.map(shopSeedDto, Shop.class);
                        shop.setTown(townService.findTownByName(shopSeedDto.getTownNameDto().getName()));
                        return shop;
                    })
                    .forEach(shopRepository::save);

        }
        return sb.toString();
    }

    @Override
    public boolean existsByName(String name) {
        return shopRepository.existsByName(name);
    }

    @Override
    public Shop findByName(String name) {
        return shopRepository.findByName(name);
    }
}
