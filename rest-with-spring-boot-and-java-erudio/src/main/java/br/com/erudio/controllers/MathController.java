package br.com.erudio.controllers;

import java.util.concurrent.atomic.AtomicLong;

import br.com.erudio.converters.NumberConverter;
import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.math.SimpleMath;
import org.springframework.web.bind.annotation.*;

@RestController // Combinação das anotations = @Controller e @ResponseBody, adcionadas na versão 4.0 do Spring.
public class MathController {

    private  final AtomicLong counter = new AtomicLong();
    private SimpleMath math = new SimpleMath();

    // Soma:
    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}",
            method=RequestMethod.GET)
    public Double sum (
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
            ) throws Exception {

        if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return math.sum(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo));
    }


    //Subtração:
    @RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}",
            method=RequestMethod.GET)
    public Double subtraction (
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception {

        if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return math.subtraction(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo));
    }


    // Multiplicação:
    @RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}",
            method=RequestMethod.GET)
    public Double multiplication (
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception {

        if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return math.multiplication(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo));
    }


    // Divisão:
    @RequestMapping(value = "/division/{numberOne}/{numberTwo}",
            method=RequestMethod.GET)
    public Double division (
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception {

        if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return math.division(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo));
    }


    // Média:
    @RequestMapping(value = "/mean/{numberOne}/{numberTwo}",
            method=RequestMethod.GET)
    public Double mean (
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception {

        if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return math.mean(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo));
    }

    // Raiz Quadrada:
    @RequestMapping(value = "/squareRoot/{number}",
            method=RequestMethod.GET)
    public Double squareRoot (
            @PathVariable(value = "number") String number
    ) throws Exception {

        if(!NumberConverter.isNumeric(number)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return math.squareRoot(NumberConverter.convertDouble(number));
    }

}
