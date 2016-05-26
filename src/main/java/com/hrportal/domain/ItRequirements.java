package com.hrportal.domain;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Created by james on 3/2/2016.
 */
public class ItRequirements {
    @NotNull
    private Laptop laptop;
    private Phone phone;

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public Optional<Phone> getPhone() {
        return Optional.ofNullable(phone);
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
