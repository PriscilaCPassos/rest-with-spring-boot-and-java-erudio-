package br.com.erudio.mapper.custom;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {
    // Criando dois métodos estáticos, um para converter lista e outro para converter um objeto simples.

    // Inicializando...
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    // Realizando a conversão efetiva:
    public static <O, D> D parseObject(O origin, Class<D> destination){ // Passando o tipo de origem e destino, retornando o destino.
        return mapper.map(origin, destination);
    }


    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){ // Retornando uma lista de destino.
        List<D> destinationObjects = new ArrayList<>();
       // List<D> destinationObjects = new ArrayList<>(D);
        for (O o: origin) {
             destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;

    }

}
