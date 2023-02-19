package com.poshist.common.utils;

import org.apache.commons.lang3.RandomUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommonUtils {
    /**
     * 组装查询条件
     *
     * @param root
     * @param query
     * @param cb
     * @param list
     * @param order
     * @return
     */
    public synchronized static Predicate getPredicate(Root root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> list, String order) {
        Predicate[] p = new Predicate[list.size()];
        query.where(cb.and(list.toArray(p)));
        query.orderBy(cb.desc(root.get(order)));
        return query.getRestriction();
    }

    /**
     * 创建申请单号码
     *
     * @return
     */
    public static String generateApplicantCode() {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = format.format(new Date());
        DecimalFormat decimalFormat = new DecimalFormat("00");
        code += decimalFormat.format(RandomUtils.nextInt(0, 99));
        return code;
    }

    public static Date strToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToSimpleStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = "";
        if (null != date) {
            str = format.format(date);
        }
        return str;

    }

    public static String dateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "";
        if (null != date) {
            str = format.format(date);
        }
        return str;

    }

    public static String millisToSimpleStr(Long millis) {
        String str = "";
        if (null != millis) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            str = format.format(new Date(millis));
        }
        return str;
    }

    public static String millisToStr(Long millis) {
        String str = "";
        if (null != millis) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = format.format(new Date(millis));
        }
        return str;
    }

    public static Date timeToDayStart(Date date) {
        date=new Date(date.getTime());
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }
    public static Date timeToDayEnd(Date date) {
        date=new Date(date.getTime());
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date;
    }
    public static String datetoStr(Date date, String formatStr) {

        SimpleDateFormat format = new SimpleDateFormat(formatStr);

        return format.format(date);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

    public static List<String> splitData(Date startTime, Date endTime) {
        List datas = new ArrayList<String>();
        String startStr = dateToSimpleStr(startTime);
        String endStr = dateToSimpleStr(endTime);
        datas.add(startStr);
        while (!startStr.equals(endStr)) {
            startTime.setTime(startTime.getTime() + 86400000);
            startStr = dateToSimpleStr(startTime);
            datas.add(startStr);
        }
        return datas;
    }

    public static String generateZeroCode(int size, String number) {

        String str = String.format("%0" + size + "d", Integer.valueOf(number));
        return str;
    }
}
