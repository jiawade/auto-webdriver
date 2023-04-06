package io.driver.manage;


import com.google.common.collect.Lists;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import io.driver.utils.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;


public class Test {
    public static void main(String[] args) {
        System.out.println(RegexUtils.findAll(RegexUtils.Ip,"sdlf2kj192.168.34.4sld89fdlk30"));
//        AutoWebdriver.configFirefoxDriver(true);
//        try {
//            String json = FileUtils.readFileToString(new File("D:\\auto-webdiver\\src\\main\\resources\\geckodriver_mapping.json"), StandardCharsets.UTF_8);
//            JaywayUtils j = new JaywayUtils(json);
//            System.out.println(j.getValues1("$.childs..realizeCumulativeProfit..value").stream().filter(i->!Objects.isNull(i)).map(Object::toString).filter(i->!i.equals("0.0")).collect(Collectors.toList()));
//            System.out.println(j.getValues1("$.childs..totalCumulativeProfit..value").stream().filter(i->!Objects.isNull(i)).map(Object::toString).filter(i->!i.equals("0.0")).map(i->Double.parseDouble(i)).mapToDouble(Double::doubleValue).sum());
//            System.out.println(j.getValues1("$.childs..realizeCumulativeReturn..value").stream().filter(i->!Objects.isNull(i)).map(Object::toString).filter(i->!i.equals("0.0")).collect(Collectors.toList()));


//            System.out.println(j.getValues1("$.childs..value").stream()
//                    .filter(i -> !Objects.isNull(i))
//                    .map(Object::toString)
//                    .filter(i -> !i.equals("0.0"))
//                    .map(Double::parseDouble)
//                    .collect(Collectors.toList()));


//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }


}
