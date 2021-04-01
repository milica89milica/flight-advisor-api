package com.htec.fa_api.util;

import com.htec.fa_api.model.Country;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Component
public class CountryGenerator {

    public static List<Country> create() {
        String[] countryCodes = Locale.getISOCountries();
        List<Country> list = new ArrayList<Country>(countryCodes.length);

        for (String cc : countryCodes) {
            list.add(new Country(new Locale("", cc).getDisplayCountry(), cc.toUpperCase()));
        }

        Collections.sort(list);
        return list;
    }
}

