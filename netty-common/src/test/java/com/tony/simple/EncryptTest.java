package com.tony.simple;

import com.alibaba.fastjson.JSON;
import com.tony.util.AESEncrypt;
import com.tony.util.RSAEncrypt;
import com.tony.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.security.Security;
import java.util.stream.Stream;

/**
 * @author jiangwenjie 2020/1/2
 */
@Slf4j
public class EncryptTest {
    @Test
    public void testEncryptKeys() throws Exception {
        byte[] keyInfo = AESEncrypt.generateAesKey();
        log.info("key length:{}", keyInfo.length);
        RSAUtil rsaUtil = new RSAUtil("/Users/jiangwenjie/Documents/Repositories/Github/NettyStaging/doc/");

        byte[] encryptKey = rsaUtil.encryptWithPrivateKey(keyInfo);
        log.info("encrypted key length:{}", encryptKey.length);
        log.info("originKey: {}, encryptKey: {}", RSAEncrypt.byteArrayToBase64(keyInfo), RSAEncrypt.byteArrayToBase64(encryptKey));
        String content = "password12345678900password12345678900password12345678900password12345678900password12345678900password1";
        log.info("content bytes length: {}", content.getBytes().length + 13);

        String encrypted = rsaUtil.encryptWithPubKey(content);
        log.info("rsa encrypt: {}", encrypted);
        log.info("rsa decrypt: {}", rsaUtil.decryptByPrivateKey(encrypted));
    }

    @Test
    public void testAes() throws Exception {

        byte[] keyInfo = AESEncrypt.generateAesKey();
        String aesBase64 = Base64.encodeBase64String(keyInfo);
        log.info("key:{} encrypt: {}", aesBase64, Base64.encodeBase64String(AESEncrypt.encrypt("1234".getBytes(), AESEncrypt.loadKeyByBytes(keyInfo))));
    }

    @Test
    public void getAllAlgorithm() throws Exception {
        Stream.of(Security.getProviders()).forEach(provider -> log.info("provide: {}, {}", provider.getName() , provider.getInfo()));
    }

    @Test
    public void testDecryptRsa() throws Exception {
        String userDir = System.getProperty("user.dir");
        log.info("userDir: {}", userDir);
        String keyDirPath = userDir.substring(0, userDir.lastIndexOf("/")) + "/doc/";
        log.info("key path: {}", keyDirPath);
        RSAUtil rsaUtil = new RSAUtil(keyDirPath);
        String originContent = "testContent测试内容123456かんが";
        String encryptContent = rsaUtil.encryptWithPubKey(originContent);
        log.info("encryptContent: {}", encryptContent);
        log.info("result:{}", rsaUtil.decryptByPrivateKeyIgn(encryptContent));
    }
}
