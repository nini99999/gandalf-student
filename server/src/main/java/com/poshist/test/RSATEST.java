package com.poshist.test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;

public class RSATEST {
    @org.junit.Test
    public void test04() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(2048);//要生成多少位，只需要修改这里即可128, 192或256
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        FileOutputStream fos = new FileOutputStream("/home/poshist/work/code/gandalf/gandalf-doc/ca/clientCA/key");
        fos.write(b);
        fos.flush();
        fos.close();
    }
}
