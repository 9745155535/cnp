package com.ljj.crazyandbox.cnp;

import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Gnoll
 * @create 2017-06-16 17:00
 **/
@UtilityClass
public class StringUtility extends StringUtils {

    private final char[] zeroArray = "0000000000000000000000000000000000000000000000000000000000000000".toCharArray();

    /**
     * 拼接URL和参数
     *
     * @param url
     * @param par
     * @return
     * @throws UnsupportedEncodingException
     */
    public String composeUrl(String url, Map<String, Object> par) throws UnsupportedEncodingException {
        if (!StringUtility.hasText(url) || CollectionUtils.isEmpty(par)) {
            return url;
        }
        StringBuilder str = new StringBuilder(url);
        if (url.lastIndexOf("?") != url.length() - 1) str.append("?");
        str.append(urlParameterEncoder(par));
        return str.toString();
    }

    public void composeUrl(StringBuilder url, Map<String, Object> par) throws UnsupportedEncodingException {
        if (null == url || CollectionUtils.isEmpty(par)) {
            return;
        }
        if (url.lastIndexOf("?") != url.length() - 1) url.append("?");
        url.append(urlParameterEncoder(par));
    }

    /**
     * URL参数编码
     *
     * @param par
     * @return
     * @throws UnsupportedEncodingException
     */
    public String urlParameterEncoder(Map<String, Object> par) throws UnsupportedEncodingException {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, Object> entry : par.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            str.append(key).append("=");
            if (null != value) {
                str.append(urlEncoder(String.valueOf(value)));
            }
            str.append("&");
        }
        return str.substring(0, str.length() - 1);
    }

    /**
     * URL字符串编码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String urlEncoder(String str) {
        try {
            return str != null ? URLEncoder.encode(str, "UTF-8") : null;
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public String urlEncoder(String str,String charset) {
        try {
            return str != null ? URLEncoder.encode(str, charset) : null;
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * URL字符串编码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String percentEncoder(String str) throws UnsupportedEncodingException {
        return str != null ? URLEncoder.encode(str, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;
    }

    /**
     * 替换制表符
     *
     * @param str
     * @return
     */
    public String replaceSpace(String str) {
        if (null == str) {
            return null;
        }
        Pattern p = Pattern.compile("[\\s*|\t*|\r*|\n*]");
        Matcher m = p.matcher(str);
        String strNoBlank = m.replaceAll("");
        return strNoBlank;
    }

    /**
     * 替换缩进
     *
     * @param str
     * @return
     */
    public String replaceTab(String str) {
        if (null == str) {
            return null;
        }
        Pattern p = Pattern.compile("[\t*|\r*|\n*]");
        Matcher m = p.matcher(str);
        String strNoBlank = m.replaceAll("");
        return strNoBlank;
    }

    /**
     * UTF8编码
     *
     * @param input
     * @return
     */
    public String getString(byte[] input) {
        try {
            return new String(input, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            String result = new String(input);
            return result;
        }
    }

    /**
     * 获取UTF8字节码
     *
     * @param input
     * @return
     */
    public byte[] getBytes(String input) {
        try {
            return input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException uee) {
            return input.getBytes();
        }
    }

    /**
     * 删除XSS注入字符
     *
     * @param input
     * @return
     */
    public String removeXSSCharacters(String input) {
        final String[] xss = {"<", ">", "\"", "'", "%", ";", ")", "(", "&", "+", "-"};
        for (int i = 0; i < xss.length; i++) {
            input = input.replace(xss[i], "");
        }
        return input;
    }

    /**
     * 截取指定长度，后面填充...
     *
     * @param str
     * @param maxWidth
     * @return
     */
    public String abbreviate(String str, int maxWidth) {
        if (null == str) {
            return null;
        }
        if (str.length() <= maxWidth) {
            return str;
        }
        return str.substring(0, maxWidth) + "...";
    }

    /**
     * 截取指定长度，后面填充0
     *
     * @param string
     * @param length
     * @return
     */
    public String zeroPadString(String string, int length) {
        if (string == null || string.length() > length) {
            return string;
        }
        StringBuilder buf = new StringBuilder(length);
        buf.append(zeroArray, 0, length - string.length()).append(string);
        return buf.toString();
    }

    public boolean allNotBlank(String... values) {
        if(null == values || values.length == 0) return false;
        for(String value : values) {
            if(!hasLength(value)) return false;
        }
        return true;
    }

    /**
     * 判断用户昵称是否合法
     *
     * @param username
     * @return
     */
    public boolean checkUserNameFormat(String username){
        if(username == null || username.isEmpty())  return false;
        //替换emoji表情
        String str = filterEmoji(username);
        //剩下的字符串只能包含中文、英文、数字
        if(str!=null && !str.isEmpty()){
            String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
            return str.matches(regex);
        }
        return false;
    }

    private boolean isNotEmojiCharacter(char codePoint)
    {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public String filterEmoji(String source)
    {
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++)
        {
            char codePoint = source.charAt(i);
            if (isNotEmojiCharacter(codePoint))
            {
                buf.append(codePoint);
            } else{

                buf.append("");

            }
        }
        return buf.toString();
    }

    public boolean contrainEmoji(String source){
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isNotEmojiCharacter(codePoint)) return true;
        }
        return false;
    }

    /**
     * 用户名包含emoji进行base64编码
     *
     * @param userName
     * @return
     * @throws Exception
     */
    /*public String base64UserNameEmoji(String userName) throws Exception{
        String str = userName == null ? "" : userName;
        if(StringUtility.contrainEmoji(str)){
            byte[] encodeBase64 = Base64.encodeBase64(str.getBytes("UTF-8"));
            str = new String(encodeBase64);
        }
        return str;
    }*/

    public String stringLengthMax20(String userName,String random) throws Exception{
        userName = userName==null?"":userName;
        random = random==null?"":random;
        Integer length = random.length();
        if(userName.length() > 20 || (userName + random).length() > 20){
            userName = userName.substring(0,20-length)+random;
        }else{
            userName = userName + random;
        }
        return userName;
    }

    public String stringBytesMax20(String str,String random) throws Exception{
        str = str==null?"":str;
        random = random==null?"":random;
        Integer strBytes = getStringByte(str);
        Integer length = getStringByte(random);
        if(strBytes + length > 20 ){
            Integer bytes16 = 0;
            Integer j = 0;
            for (int i=0;i<str.length();i++){//最多截取16字节的字符串
                char a = str.charAt(i);
                if (isChinese(a)) {
                    bytes16 += 2;
                }else if (!isNotEmojiCharacter(a)) {
                    bytes16 += 2;
                }else{
                    bytes16 += 1;
                }
                if(bytes16 >= 16){
                    j = i;
                    break;
                }
            }
            char b = str.charAt(j);
            if (isChinese(b) || !isNotEmojiCharacter(b)){
                if(getStringByte(str.substring(0,j))>16){
                    str = str.substring(0,j)+random;
                }else{
                    str = str.substring(0,j+1)+random;
                }
            }else{
                str = str.substring(0,j+1)+random;
            }
        }else{
            str = str+random;
        }
        return str;
    }

    public String stringBytesMax20(String str) throws Exception{
        str = str==null?"":str;
        Integer strBytes = getStringByte(str);
        if(strBytes > 20){
            Integer bytes20 = 0;
            Integer j = 0;
            for (int i=0;i<str.length();i++){//最多截取20字节的字符串
                char a = str.charAt(i);
                if (isChinese(a)) {
                    bytes20 += 2;
                }else if (!isNotEmojiCharacter(a)) {
                    bytes20 += 2;
                }else{
                    bytes20 += 1;
                }
                if(bytes20 >= 20){
                    j = i;
                    break;
                }
            }
            char b = str.charAt(j);
            if (isChinese(b) || !isNotEmojiCharacter(b)){
                if(getStringByte(str.substring(0,j))>20){
                    str = str.substring(0,j);
                }else{
                    str = str.substring(0,j+1);
                }
            }else{
                str = str.substring(0,j+1);
            }
        }
        return str;
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static boolean hasChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    // 获取字符串的字节数
    public static Integer getStringByte (String str) {
        Integer result = 0;
        if(!hasLength(str))
            return result;
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                result += 2;
            }else if (!isNotEmojiCharacter(c)) {
                result += 2;
            }else{
                result += 1;
            }
        }
        return result;
    }




    // 获取连续的时间段
    public static String getContinuationTime(String str,boolean isOpposite) {
        if(!hasLength(str)) return null;
        String[] s = str.split(",");
        if(s.length > 24 ) return null;

        Integer[] num = new Integer[s.length];
        String result = "";
        for (int i = 0; i < s.length; i++) {
            num[i] = Integer.parseInt(s[i]);
        }
        //isOpposite==true时，取当前所有时间再一天24小时中的时间反值
        if(isOpposite){
            Integer[] dayHour = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
            ArrayList<Integer> resultList = new ArrayList<>();
            for(int i=0;i<dayHour.length;i++) {
                boolean is = false;
                for (int j = 0; j < num.length; j++) {
                    if (dayHour[i] == num[j]) {
                        is = true;
                        break;
                    }
                }
                if(!is) resultList.add(i);
            }
            if(!resultList.isEmpty() && resultList.size() > 0){
                num = new Integer[resultList.size()];
                for(int i=0;i<resultList.size();i++){
                    num[i] = resultList.get(i);
                }
            }else{
                return null;
            }
        }
        Arrays.sort(num);
        for (int i = 0; i < num.length; i++) {
            if (i == 0) {
                if(num[i] < 10){
                    result = "0" + num[i] + ":00";
                }else{
                    result = "" + num[i] + ":00";
                }
                if(num.length == 1){ //只有一条数据时，接入结束时间
                    result = getHourEndMinute(result);
                }
            } else if (i == num.length - 1) {
                if (num[i] - num[i - 1] == 1) {
                    if(num[i] < 10){
                        result = result + "-0" + num[i] + ":59";
                    }else{
                        result = result + "-" + num[i] + ":59";
                    }
                } else {
                    result = getHourEndMinute(result);
                    if(num[i] < 10){
                        result = result + ",0" + num[i] + ":00";
                    }else{
                        result = result + "," + num[i] + ":00";
                    }
                    result = getHourEndMinute(result);
                }
            } else {
                if ((num[i] - num[i - 1] == 1) && (num[i + 1] - num[i] == 1)) {
                    continue;
                }
                if ((num[i] - num[i - 1] == 1) && (num[i + 1] - num[i] != 1)) {
                    if(num[i] < 10){
                        result = result + "-0" + num[i] + ":59";
                    }else{
                        result = result + "-" + num[i] + ":59";
                    }
                }
                if ((num[i] - num[i - 1] != 1)) {
                    result = getHourEndMinute(result);
                    if(num[i] < 10){
                        result = result + "," + num[i] + ":00";
                    }else{
                        result = result + "," + num[i] + ":00";
                    }
                }
            }
        }
        return result;
    }

    public static String getHourEndMinute(String result) {
        if(hasLength(result)){
            String[] s1 = result.split(",");
            String last = s1[s1.length-1];
            if(last.endsWith("00")){
                if("00:00".equals(last)){
                    return result + "-00:59";
                }
                String str1 = last.replace("00","59");
                result = result + "-" + str1;
            }
        }
        return result;
    }

    /**
     * id字符串转集合
     * @param str
     * @return
     */
    public static Collection getCollectionByStr(String str) {
        Collection list = new ArrayList();
        if(str!=null && !str.isEmpty()){
            String[] arr = str.split(",");
            if(arr!=null && arr.length > 0){
                for(String s : arr){
                    list.add(Long.valueOf(s));
                }
                return list;
            }
        }
        return null;
    }

    public static List getListByStr(String str) {
        List list = new ArrayList();
        if(str!=null && !str.isEmpty()){
            String[] arr = str.split(",");
            if(arr!=null && arr.length > 0){
                for(String s : arr){
                    list.add(s);
                }
                return list;
            }
        }
        return null;
    }

    /**
     * 左侧补零
     * @param num
     * @param str
     * @return
     */
    public static String leftCoverageZero(Integer num,String str ){
        Pattern pattern = Pattern.compile("\\d*");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return String.format("%0" + num + "d", Integer.parseInt(str));
        }
        return str;
    }

    /**
     * 正则表达式判断字符是否存在
     * @param sourceStr
     * @param regEx
     * @return
     */
    public static boolean regExfind(String sourceStr,String regEx){
        Pattern pattern=Pattern.compile(regEx);
        Matcher matcher=pattern.matcher(sourceStr);
        return matcher.find();
    }

    public static void main(String[] args) throws Exception {
        /*String username = "xxx\uD83D\uDC66\uD83C\uDFFFsss\uD83E\uDD16";
        System.out.println(username.length());
        //替换emoji表情
        String str = StringUtility.filterEmoji(username);
        System.out.println(str);

        boolean check = checkUserNameFormat(username);
        System.out.println(check);

        boolean a = contrainEmoji(username);
        System.out.println(a);

        String base64_str = base64UserNameEmoji(username);
        String decode_str = new String(Base64.decodeBase64(base64_str));
        System.out.println(base64_str);
        System.out.println(decode_str);*/
        /*System.out.println(getStringByte("112#&#；=&(*”？@*？"));
        System.out.println(stringBytesMax20("As2sssss\uD83D\uDC66止逆阀止止"));
        System.out.println(stringBytesMax20("As2sssss\uD83D\uDC66止逆阀","ab65"));*/
        /*String s = "20170323,20171011,20171001,20171002,20171003,20171006,20171007,20171010,20171015,20170110";
        List<String> list = convDateStr(s);
        for(String str : list){
            System.out.println(str);
        }*/

        //String str = new String("01,02,03,04,05,06,07,08,09,11,13,14,18,19,20,22,23");
        /*String str = new String("00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23");
        System.out.println(getContinuationTime(str,false));
        //String[] use_time_list = getContinuationTime(str,true).split(",");
        //System.out.println(Arrays.asList(use_time_list));
        System.out.println(StringUtility.hasLength(null));

        Blowfish bf = new Blowfish("aimymusic");
        System.out.println(bf.decrypt("e3f3feea65295143b0cb84d306e14c391bd7d766d5148c316fe13d48906be0dc"));*/

        /*EncryptorUtility encryptor = new EncryptorUtility("4NCMAIfiXsjB9OrG");
        System.out.println(encryptor.encryptNoSalt("aimymusic",EncryptorUtility.OPERATION.MD5));

        long timestamp = System.currentTimeMillis();
        long twepoch = 1498838400000L;
        System.out.println(((timestamp - twepoch) * 4194304));
        System.out.println(((timestamp - twepoch) << 22));
        System.out.println((43530 << 6));
        long i = ((timestamp - twepoch) << 22) | (43530 << 6) | 0L;
        System.out.println(((timestamp - twepoch) << 22) | (43530 << 6));
        System.out.println(((43530 << 6) | 0L));
        System.out.println(i);

        System.out.println(((31536000000L*69) << 22) | (43530 << 6) | 0L);//69年*/

        /*System.out.println(urlEncoder("&^"));
        System.out.println(urlEncoder("中午"));
        System.out.println(URLDecoder.decode(urlEncoder("&^"),"UTF-8"));
        System.out.println(URLDecoder.decode(urlEncoder("f1f7a8dd-21cf-4f5e-a36e-16a9b6d341b0"),"UTF-8"));

        // Collator 类是用来执行区分语言环境的 String 比较的，这里选择使用CHINA
        Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        String[] arrStrings = { "杰西西","杰西西的猫","杰西西啊狗","杰西西glRf"};
        // 使根据指定比较器产生的顺序对指定对象数组进行排序。
        Arrays.sort(arrStrings, comparator);
        for (int i = 0; i < arrStrings.length; i++)
            System.out.println(arrStrings[i]);*/

        String sourceStr = "12121_LOW_112112";
        String regEx1= "b";
        String regEx2 = "LOW";
        boolean b = regExfind(sourceStr,regEx1) || regExfind(sourceStr,regEx2);
        System.out.println(b);
    }

}
