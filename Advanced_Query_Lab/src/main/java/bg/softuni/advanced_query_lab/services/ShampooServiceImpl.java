package bg.softuni.advanced_query_lab.services;

import bg.softuni.advanced_query_lab.entities.Shampoo;
import bg.softuni.advanced_query_lab.entities.Size;
import bg.softuni.advanced_query_lab.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService{

    private ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }


    @Override
    public List<Shampoo> getAllShampoosBySize(String size) {
        Size parsedSize = Size.valueOf(size.toUpperCase());
        return this.shampooRepository.findAllBySizeOrderById(parsedSize).get();
    }

    @Override
    public List<Shampoo> getAllShampoosBySizeOrLabel(String size, long labelId) {
        Size parsedSize = Size.valueOf(size.toUpperCase());

        return  this.shampooRepository.findAllBySizeOrLabelIdOrderByPrice(parsedSize,labelId).get();

    }
}
