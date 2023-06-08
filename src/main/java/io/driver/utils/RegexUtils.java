package io.driver.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtils {
    public static final String Email = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+([a-zA-Z]{2,6}|中国|公司|网络)$";
    public static final String Phone = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16([0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
    public static final String Tel = "^0[1-9](\\d{1,2}\\-?)\\d{7,8}";
    public static final String Url = "(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^]\\s`!()\\[{};:'\".,<>?«»]))";
    public static final String Domain = "^http://(?!\\-)(?:[a-zA-Z\\d\\-]{0,62}[a-zA-Z\\d]\\.){1,126}(?!\\d+)[a-zA-Z\\d]{1,63}$";
    public static final String ImgUrl = "(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^]\\s`!()\\[{};:'\".,<>?«»]))[.](jpg|jpeg|png|gif|bmp)";
    public static final String Number = "^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)?(?:\\.\\d+)?$";
    public static final String Chinese = "[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+";
    public static final String PostCode = "^(\\d|\\d{2})\\d4$";
    public static final String IdNo = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}[X0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";
    public static final String Ip = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$|^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$|^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$";
    public static final String ShortDate = "^\\d{4}[\\-](0?[1-9]|1[012])[\\-](0?[1-9]|[12][0-9]|3[01])$";
    public static final String LongDate = "^\\d{4}[\\-](0?[1-9]|1[012])[\\-](0?[1-9]|[12][0-9]|3[01]) (?:2[0-3]|[01]?[0-9]):[0-5][0-9]:[0-5][0-9]$";
    public static final String Letter = "^[A-Za-z]+$";
    public static final String CapitalLetter = "^[A-Z]+$";
    public static final String LowerCaseLetter = "^[a-z]+$";
    public static final String LetterDigit = "^[0-9A-Za-z]+$";
    public static final String Color = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    public static final String URL_IMAGE = "^(http://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";

    public static List<String> findAll(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List<String> find = new ArrayList<>();
        while (matcher.find()) {
            find.add(matcher.group());
        }
        return find;
    }

    public static boolean matchEmail(String value) {
        return match(RegexUtils.Email, value);
    }


    public static boolean matchPhone(String value) {
        return match(RegexUtils.Phone, value);
    }

    public static boolean matchTel(String value) {
        return match(RegexUtils.Tel, value);
    }

    public static boolean matchUrl(String value) {
        return match(RegexUtils.Url, value);
    }

    public static boolean matchDomain(String value) {
        return match(RegexUtils.Domain, value);
    }

    public static boolean matchImageUrl(String value) {
        return match(RegexUtils.ImgUrl, value);
    }

    public static boolean matchNumber(String value) {
        return match(RegexUtils.Number, value);
    }

    public static boolean matchChinese(String value) {
        return match(RegexUtils.Chinese, value);
    }


    public static boolean matchPostCode(String value) {
        return match(RegexUtils.PostCode, value);
    }


    public static boolean matchIdNo(String value) {
        return match(RegexUtils.IdNo, value);
    }


    public static boolean matchIp(String value) {
        return match(RegexUtils.Ip, value);
    }

    public static boolean matchShortDate(String value) {
        return match(RegexUtils.ShortDate, value);
    }

    public static boolean matchLongDate(String value) {
        return match(RegexUtils.LongDate, value);
    }


    public static boolean matchLetter(String value) {
        return match(RegexUtils.Letter, value);
    }

    public static boolean matchCapitalLetter(String value) {
        return match(RegexUtils.CapitalLetter, value);
    }

    public static boolean matchLowerCaseLetter(String value) {
        return match(RegexUtils.LowerCaseLetter, value);
    }

    public static boolean matchLetterDigit(String value) {
        return match(RegexUtils.LetterDigit, value);
    }

    public static boolean matchColor(String value) {
        return match(RegexUtils.Color, value);
    }

    public static boolean match(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }

    public static boolean find(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);

        return matcher.find();
    }
}

