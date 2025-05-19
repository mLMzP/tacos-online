package com.lm2a.tacosonline.model;


import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    @NotBlank(message="El nombre es obligatorio")
    private String name;
    @NotBlank(message="La calle es obligatoria")
    private String street;
    @NotBlank(message="La ciudad es obligatoria")
    private String city;
    @NotBlank(message="El estado es obligatorio")
    private String state;
    @NotBlank(message="El codigo postal es obligatorio")
    private String zip;
    @CreditCardNumber(message="No es una tarjeta valida")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2](\\/))([2-9][0-9])$", message="La fecha debe ser MM/AA")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message="CVV invalido")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design){
        tacos.add(design);
    }
}
