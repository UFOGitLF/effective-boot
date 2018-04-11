package com.fly.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Description: 状态码转换
 * <p/>
 * @author liufei
 */
public class MessageContainer {

    private static final Map<String, String> MESSAGE = new HashMap<>();
    private static final String MESSAGE_PROPERTY = "message";
    private static Logger logger = LoggerFactory.getLogger("MessageContainer");

    static {

        logger.info("Start to read message.properties file...");

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(MESSAGE_PROPERTY);
            if (bundle != null) {
                logger.info("message.properties" + bundle.toString());
                Set<String> keys = bundle.keySet();
                for (String key : keys) {
                    String enKey = new String(key.getBytes("ISO-8859-1"), "UTF-8");
                    String enValue = new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
                    MESSAGE.put(enKey, enValue);
                }
                logger.info("Read message.properties file successfully!");
            } else {
                logger.warn("Message.properties file does not existed!");
            }
        } catch (MissingResourceException me) {
            logger.error("Can't find Message.properties file in classpath", me);
        } catch (UnsupportedEncodingException e) {
            logger.warn("Message.properties file's encoding is error", e);
        } catch (Throwable t) {
            logger.warn("Read message.properties file failed", t);
        }
    }

    public static String getMsg(int code) {
        String msg = MESSAGE.get(Integer.toString(code));
        if (msg == null) {
            logger.warn("Can't find specified message(message code:" + code + ")");
            logger.info("message info: messageSize:" + MESSAGE.size());
            return "Can't find specified message(message code:" + code + ")";
        }
        return msg;
    }

    public static String getMsg(int code, Object... args) {
        String msg = MESSAGE.get(Integer.toString(code));
        if (msg == null) {
            logger.warn("Can't find specified message(message code:" + code + ")");
            return "Can't find specified message(message code:" + code + ")";
        }

        return String.format(msg, args);
    }

}
