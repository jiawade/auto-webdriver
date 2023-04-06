package io.driver.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtils {
    public static final String Email = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+([a-zA-Z]{2,6}|中国|公司|网络)$";
    public static final String Phone = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16([0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
    public static final String Tel = "^0[1-9](\\d{1,2}\\-?)\\d{7,8}";
    public static final String Url = "(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^]\\s`!()\\[{};:'\".,<>?«»“”‘’]))";
    public static final String Domain = "^http://(?!\\-)(?:[a-zA-Z\\d\\-]{0,62}[a-zA-Z\\d]\\.){1,126}(?!\\d+)[a-zA-Z\\d]{1,63}$";
    public static final String ImgUrl = "(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^]\\s`!()\\[{};:'\".,<>?«»“”‘’]))[.](jpg|jpeg|png|gif|bmp)";
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

    /**
     * 1 移动号段：135、136、137、138、139、147、150、151、152、157、158、159、182、183、184、187、188。
     * 2 联通号段：130、131、132、145、155、156、185、186、170
     * 3 电信号段：133 153 180 181 189 177
     */
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

    /**
     * 第一位数字索引
     * 包含地区名称和邮编，按开头的第一位数字分：
     * 0、1、2、3、4、5、6、7、8、9。
     * 前两位数字索引
     * 按开头的前两位数字分：
     * 00、01、02、03、04、05、06、07、08、09
     * 10、11、12、13、14、15、16、17、18、19
     * 20、21、22、23、24、25、26、27、28、29
     * 30、31、32、33、34、35、36、37、38、39
     * 40、41、42、43、44、45、46、47、48、49
     * 50、51、52、53、54、55、56、57、58、59
     * 60、61、62、63、64、65、66、67、68、69
     * 70、71、72、73、74、75、76、77、78、79
     * 80、81、82、83、84、85、86、87、88、89
     * 90、91、92、93、94、95、96、97、98、99
     *
     * @param
     * @return boolean
     * @throws
     * @author ZhangSan_Plus
     * @date 14:43 2021/5/31
     **/
    public static boolean matchPostCode(String value) {
        return match(RegexUtils.PostCode, value);
    }


    public static boolean matchIdNo(String value) {
        return match(RegexUtils.IdNo, value);
    }

    /**
     * 类别	1st八位字节十进制范围	1st 八位字节高阶位	网络/主机ID (N=网络, H=主机)	默认子网掩码	网络数量	每个网络的主机（可用地址）
     * A	1 – 126*	0	N.H.H.H	255.0.0.0	126 (27 – 2)	16,777,214 (224 – 2)
     * B	128 – 191	10	N.N.H.H	255.255.0.0	16,382 (214 – 2)	65,534 (216 – 2)
     * C	192 – 223	110	N.N.N.H	255.255.255.0	2,097,150 (221 – 2)	254 (28 – 2)
     * D	224 – 239	1110	为多播预留
     * E	240 – 254	1111	实验性的，用于研究
     * 注意：A类IP地址中127.0.0.0 至 127.255.255.255 不能被使用，它们预留用于环回地址和诊断功能。
     * 私有网络地址
     * 类别	私有网络	子网掩码	地址范围
     * A	10.0.0.0	255.0.0.0	10.0.0.0 - 10.255.255.255
     * B	172.16.0.0 - 172.31.0.0	255.240.0.0	172.16.0.0 - 172.31.255.255
     * C	192.168.0.0	255.255.0.0	192.168.0.0 - 192.168.255.255
     *
     * @param
     * @return boolean
     * @throws
     * @author ZhangSan_Plus
     * @date 14:43 2021/5/31
     **/
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

