package xn.core.util.data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description: String处理工具类
 * @author Zhangjc
 * @date 2016年4月13日 下午6:52:18
 */
public class StringUtil {
	private final static String[] EMPTY_ARRAY = new String[0];
	public static final String DEFAULT_SEPARATOR = ",";
	public static final String EMPTY = "";
	private static final int PAD_LIMIT = 8192;

	/**
     * 判断字符串是否为空白，空格不属于空白 StringUtil.isEmpty("  ") = false;
     * 
     * @param value
     * @return
     */
	public static boolean isEmpty(String value) {
		return value == null || value.length() == 0;
	}

	/**
     * 判断value是否为空
     * 
     * @param value
     * @return boolean
     */
	public static boolean isBlank(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
     * 判断字符串是否非空
     * 
     * @param value
     * @return boolean
     */
	public static boolean isNonBlank(String value) {
		return !isBlank(value);
	}
	
	/**
     * 拼接字符串
     * 
     * @param arr 字符串数组
     * @param separator 分隔符
     * @return
     */
	public static String join(Object[] arr, String separator) {
		return arr == null ? null : join(arr, separator, 0, arr.length);
	}
	
	/**
     * 根据索引下标拼接指定部分的字符串
     * 
     * @param arr 字符串数组
     * @param separator 分隔符
     * @param startIndex 开始下标
     * @param endIndex 结束下标
     * @return
     */
	public static String join(Object[] arr, String separator, int startIndex, int endIndex) {
		if (arr == null)
			return null;
		
		if (separator == null)
			separator = "";
		
		if (startIndex >= endIndex) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex)
				sb.append(separator);
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
     * 根据分隔符拆分字符串
     * 
     * @param str 待拆分的字符串
     * @param sep 分隔符
     * @return
     */
	public static String[] split(String str, String sep) {
		return split(str, sep, false);
	}

	/**
     * 根据分隔符拆分字符串
     * 
     * @param str 待拆分的字符串
     * @param sep 分隔符
     * @param needBlank 空白是否需要输出
     * @return
     */
	public static String[] split(String str, String sep, boolean needBlank) {
		int len = str.length();
		if (len == 0)
			return EMPTY_ARRAY;

		if (sep.length() == 1) {
			return split(str, sep.charAt(0), needBlank);
		}
		List<String> list = new ArrayList<String>();

		int idx = -1, lastIdx = 0, sepLen = sep.length();
		while ((idx = str.indexOf(sep, lastIdx)) >= 0) {
			if (needBlank || lastIdx != idx)
				list.add(str.substring(lastIdx, idx));
			lastIdx = idx + sepLen;
		}
		if (lastIdx != str.length())
			list.add(str.substring(lastIdx));
		return list.toArray(EMPTY_ARRAY);
	}

	/**
     * 根据分隔符拆分字符串
     * 
     * @param str 待拆分的字符串
     * @param sep 分隔符
     * @return
     */
	public static String[] split(String str, char ch) {
		return split(str, ch, false);
	}

	/**
     * 根据分隔符拆分字符串
     * 
     * @param str 待拆分的字符串
     * @param sep 分隔符
     * @param needBlank 空白是否需要输出
     * @return
     */
	public static String[] split(String str, char ch, boolean needBlank) {
		int len = str.length();
		if (len == 0)
			return EMPTY_ARRAY;

		List<String> list = new ArrayList<String>();

		int lastIdx = 0;
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (c == ch) {
				if (needBlank || lastIdx != i) {
					list.add(str.substring(lastIdx, i));
				}
				lastIdx = i + 1;
			}
		}
		if (lastIdx != len)
			list.add(str.substring(lastIdx));

		return list.toArray(EMPTY_ARRAY);
	}

	/**
     * 重复拼接字符串
     * 
     * @param ch 待拼接的字符
     * @param repeat 重复多少遍
     * @return
     */
	public static String repeat(char ch, int repeat) {
		char[] buf = new char[repeat];
		for (int i = repeat - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}

	/**
     * 重复拼接字符串
     * 
     * @param str 待拼接的字符串
     * @param repeat 重复多少遍
     * @return
     */
	public static String repeat(String str, int repeat) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		if (repeat <= 0) {
			return EMPTY;
		}
		int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) {
			return str;
		}
		if (inputLength == 1 && repeat <= PAD_LIMIT) {
			return repeat(str.charAt(0), repeat);
		}

		int outputLength = inputLength * repeat;
		switch (inputLength) {
			case 1:
				return repeat(str.charAt(0), repeat);
			case 2:
				char ch0 = str.charAt(0);
				char ch1 = str.charAt(1);
				char[] output2 = new char[outputLength];
				for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
					output2[i] = ch0;
					output2[i + 1] = ch1;
				}
				return new String(output2);
			default:
				StringBuilder buf = new StringBuilder(outputLength);
				for (int i = 0; i < repeat; i++) {
					buf.append(str);
				}
				return buf.toString();
		}
	}

	/**
     * 重复拼接字符串
     * 
     * @param ch 待拼接的字符
     * @param separator 拼接使用的间隔符
     * @param repeat 重复多少遍
     * @return
     */
	public static String repeat(String str, String separator, int repeat) {
		if (str == null || separator == null) {
			return repeat(str, repeat);
		} else {
			String result = repeat(str + separator, repeat);
			return removeEnd(result, separator);
		}
	}
	
	public static String lpad(String str, char ch, int len) {
		if (isBlank(str))
			return repeat(ch, len);
		if (len <= str.length()) {
			return str;
		}
		return repeat(ch, len - str.length()) + str;
	}
	
	public static String rpad(String str, char ch, int len) {
		if (isBlank(str))
			return repeat(ch, len);
		if (len <= str.length()) {
			return str;
		}
		return str + repeat(ch, len - str.length());
	}

	/**
     * 移除字符串结尾部分字符串
     * 
     * @param str
     * @param remove
     * @return
     */
	public static String removeEnd(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.endsWith(remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	/**
     * 将主字符串中某个子字符串替换为新字符串一次
     * 
     * @param text 待替换的主字符串
     * @param searchString 查找的子字符串
     * @param replacement 替换的新字符串
     * @return
     */
	public static String replaceOnce(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, 1);
	}

	/**
     * 将主字符串中某个子字符串替换为新字符串
     * 
     * @param text 待替换的主字符串
     * @param searchString 查找的子字符串
     * @param replacement 替换的新字符串
     * @return
     */
	public static String replace(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	/**
     * 将主字符串中某个子字符串替换为新字符串
     * 
     * @param text 待替换的主字符串
     * @param searchString 查找的子字符串
     * @param replacement 替换的新字符串
     * @param max 最大替换多少次
     * @return
     */
	public static String replace(String text, String searchString, String replacement, int max) {
		if (isBlank(text) || (isBlank(searchString)) || (replacement == null) || (max == 0)) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1) {
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0) ? 0 : increase;
		increase *= ((max > 64) ? 64 : (max < 0) ? 16 : max);
		StringBuilder buf = new StringBuilder(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
     * 从字符串中截取出部分字符串
     * 
     * @param str 主字符串
     * @param start 截取开始位置
     * @return
     */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		// handle negatives, which means last n characters
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	/**
     * 从字符串中截取出部分字符串
     * 
     * @param str 主字符串
     * @param start 截取开始位置
     * @param end 截取结束位置
     * @return
     */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
     * 检查字符串数组中是否含有某个字符串
     * 
     * @param arr 字符串数组
     * @param subStr 子字符串
     * @return
     */

	public static boolean contains(String[] arr, String subStr) {
		return indexOf(arr, subStr) >= 0;
	}
	
	public static int indexOf(String[] arr, String subStr) {
		if (arr == null || arr.length == 0)
			return -1;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(subStr)) {
				return i;
			}
		}
		return -1;
	}

	/**
     * 检查字符串中是否含有子字符串
     * 
     * @param searchStr 源字符串
     * @param subStr 子字符串
     * @return
     */

	public static boolean contains(String searchStr, String subStr) {
		return contains(searchStr, subStr, DEFAULT_SEPARATOR);
	}

	/**
     * 检查字符串中是否含有对应的子字符串，源字符串中以分隔符分隔<br/>
     * StringUtil.contains("ab|dc|ac", "dc", "|") == true
     * 
     * @param searchStr 源字符串
     * @param subStr 子字符串
     * @param separator 分隔符
     * @return
     */

	public static boolean contains(String searchStr, String subStr, String separator) {
		searchStr = separator + searchStr + separator;
		subStr = separator + subStr + separator;
		return searchStr.indexOf(subStr) >= 0;
	}

	/**
     * 根据字节长度获取子字符串<br/>
     * StringUtil.getByteSubString("中国", 2) == "中"
     * 
     * @param srcStr 源字符串
     * @param count 截取字符串的字节长度
     * @return
     */
	public static String getByteSubString(String srcStr, int count) {
		if (srcStr == null)
			return "";
		if (count < 0)
			return "";

		if (count > srcStr.length() * 2)
			return srcStr;

		char[] cs = srcStr.toCharArray();

		int c = 0, endPos = -1;
		for (int i = 0; i < cs.length; i++) {
			++c;
			if (cs[i] > 255) {
				++c;
			}
			if (c == count) {
				endPos = i + 1;
				break;
			} else if (c > count) {
				endPos = i;
				break;
			}
		}
		if (endPos == -1) {
			return srcStr;
		}

		return new String(cs, 0, endPos);
	}

    public static boolean isNumeric(CharSequence cs) {
        if ((cs == null) || (cs.length() == 0)) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @Description: 处理数字类型
     * @author zhangjs
     * @param format
     * @param decimal
     * @return
     * @throws Exception
     */
    public static final String formatDecimal(double decimal, String format) throws Exception {
        if (isBlank(format)) {
            format = "#";
        }
        DecimalFormat df = new DecimalFormat(format);
        return df.format(decimal);
    }

    /**
     * @Description: List转String
     * @author yangy
     * @param longList
     * @return
     */
    public static String listLongToString(List<Long> longList) {
        if (longList != null && !longList.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < longList.size(); i++) {
                stringBuffer.append(longList.get(i).toString());
                if (i != longList.size() - 1) {
                    stringBuffer.append(",");
                }
            }
            return stringBuffer.toString();
        }
        return "";
    }

    /**
     * @Description: long[]转String
     * @author yangy
     * @param long[]
     * @return
     */
    public static String arrayToString(long[] longArr) {
        if (longArr != null && longArr.length > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < longArr.length; i++) {
                stringBuffer.append(Long.valueOf(longArr[i]));
                if (i != longArr.length - 1) {
                    stringBuffer.append(",");
                }
            }
            return stringBuffer.toString();
        }
        return "";
    }

    /**
     * @Description: List转String
     * @author yangy
     * @param longList
     * @return
     */
    public static String setLongToString(Set<Long> longSet) {
        if (longSet != null && !longSet.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            boolean flag = false;
            for (Long id : longSet) {
                if (!flag) {
                    stringBuffer.append(id);
                    flag = true;
                } else {
                    stringBuffer.append(",");
                    stringBuffer.append(id);
                }
            }
            return stringBuffer.toString();
        }
        return "";
    }
}
