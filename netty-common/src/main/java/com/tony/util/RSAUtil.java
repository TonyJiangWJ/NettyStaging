package com.tony.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.security.Key;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author TonyJiang on 2017/11/27.
 */
@Slf4j
public class RSAUtil {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    /**
     * 密文有效期30秒
     */
    private long CIPHER_VALID_TIME = 30000;

    private final int TIMESTAMP_LENGTH = 13;

    public RSAUtil(String filePath) throws Exception {
        publicKey = RSAEncrypt.loadPublicKeyByByteArray(RSAEncrypt.loadPublicKeyByFile(filePath));
        privateKey = RSAEncrypt.loadPrivateKeyByByteArray(RSAEncrypt.loadPrivateKeyByFile(filePath));
    }

    public RSAUtil(String publicBase64Str, String privateBase64Str) throws Exception {
        publicKey = RSAEncrypt.loadPublicKeyByByteArray(Base64.decodeBase64(publicBase64Str));
        privateKey = RSAEncrypt.loadPrivateKeyByByteArray(Base64.decodeBase64(privateBase64Str));
    }

    public RSAUtil() {
    }

    public void initByPublicKey(String publicKeyBase64) throws Exception {
        publicKey = RSAEncrypt.loadPublicKeyByByteArray(Base64.decodeBase64(publicKeyBase64));
    }


    /**
     * 无视密文有效期
     *
     * @param cipher
     * @return
     */
    public String decryptByPrivateKeyIgn(String cipher) {
        return decrypt(cipher, privateKey, true);
    }

    /**
     * 无视密文有效期
     *
     * @param cipher
     * @return
     */
    public String decryptByPublicKeyIgn(String cipher) {
        return decrypt(cipher, publicKey, true);
    }


    public String decryptByPrivateKey(String cipher) {
        return decrypt(cipher, privateKey, false);
    }

    public String decryptByPublicKey(String cipher) {
        return decrypt(cipher, publicKey, false);
    }

    public String decrypt(String cipher, Key rsaKey, boolean ignoreVerifyTime) {
        try {
            // 当base64编码串中包含空格，转换为加号
            cipher = cipher.replaceAll("[ ]", "+");
            byte[] cipherArray = RSAEncrypt.decrypt(rsaKey, Base64.decodeBase64(cipher));
            if (cipherArray != null) {
                String cipherStr = new String(cipherArray, 0, cipherArray.length);
                if (ignoreVerifyTime) {
                    return cipherStr;
                }
                if (cipherStr.length() > TIMESTAMP_LENGTH) {
                    long timestamp = Long.parseLong(cipherStr.substring(0, TIMESTAMP_LENGTH));
                    if (System.currentTimeMillis() - timestamp < CIPHER_VALID_TIME) {
                        return cipherStr.substring(TIMESTAMP_LENGTH);
                    } else {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        log.error("验证信息超时：{} 当前时间：{}", simpleDateFormat.format(new Date(timestamp)), simpleDateFormat.format(new Date()));
                    }
                }
            }
        } catch (Exception e) {
            log.error("解析rsa加密内容失败 ", e);
        }
        return null;
    }

    /**
     * 将明文加密 前置添加TIMESTAMP13时间戳 用于校验密文有效性
     *
     * @param content 明文内容
     * @param key     加密秘钥public or private key
     * @return 加密后的内容 Base64处理
     */
    private String encrypt(String content, RSAKey key) {
        if (content != null) {
            long timestamp = System.currentTimeMillis();
            content = timestamp + content;
            try {
                if (key instanceof RSAPrivateKey) {
                    return Base64.encodeBase64String(RSAEncrypt.encrypt((RSAPrivateKey)key, content.getBytes()));
                } else if (key instanceof RSAPublicKey) {
                    return Base64.encodeBase64String(RSAEncrypt.encrypt((RSAPublicKey)key, content.getBytes()));
                }
            } catch (Exception e) {
                log.error("rsa加密失败 ", e);
            }
        }
        return null;
    }

    public String encryptWithPrivateKey(String content) {
        return encrypt(content, privateKey);
    }

    public String encryptWithPubKey(String content) {
        return encrypt(content, publicKey);
    }

    private byte[] encrypt(byte[] bytes, RSAKey key) {
        if (bytes != null) {
            try {
                if (key instanceof RSAPrivateKey) {
                    return RSAEncrypt.encrypt((RSAPrivateKey)key, bytes);
                } else if (key instanceof RSAPublicKey) {
                    return RSAEncrypt.encrypt((RSAPublicKey)key, bytes);
                }
            } catch (Exception e) {
                log.error("rsa加密失败 ", e);
            }
        }
        return null;
    }

    public byte[] encryptWithPrivateKey(byte[] content) {
        return encrypt(content, privateKey);
    }

    public byte[] encryptWithPubKey(byte[] content) {
        return encrypt(content, publicKey);
    }

    public byte[] decryptByPrivateKey(byte[] cipher) {
        return decrypt(cipher, privateKey);
    }

    public byte[] decryptByPublicKey(byte[] cipher) {
        return decrypt(cipher, publicKey);
    }

    public byte[] decrypt(byte[] cipher, Key rsaKey) {
        try {
            return RSAEncrypt.decrypt(rsaKey, cipher);
        } catch (Exception e) {
            log.error("解析rsa加密内容失败 ", e);
        }
        return null;
    }

    public String sign(byte[] data) {
        try {
            return Base64.encodeBase64String(RSAEncrypt.sign(data, privateKey));
        } catch (Exception e) {
            log.error("数据签名失败", e);
        }
        return null;
    }

    public boolean verify(byte[] data, String sign) {
        if (sign == null) {
            return false;
        }
        try {
            return RSAEncrypt.verify(data, publicKey, Base64.decodeBase64(sign));
        } catch (Exception e) {
            log.error("数据签名失败", e);
        }
        return false;
    }

    public void setValidTime(long validTime) {
        CIPHER_VALID_TIME = validTime;
    }
}
