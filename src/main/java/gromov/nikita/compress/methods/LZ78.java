package gromov.nikita.compress.methods;

import gromov.nikita.compress.Compressible;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * TEMP CAP
 */
public class LZ78 implements Compressible {
    @Override
    public long compress(InputStream src, OutputStream target) {
        byte buff[] = new byte[1024];
        try {
            int len = src.read(buff);
            while (len != -1) {
                target.write(buff, 0, len);
                len = src.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long decompress(InputStream src, OutputStream target) {
        byte buff[] = new byte[1024];
        try {
            int len = src.read(buff);
            while (len != -1) {
                target.write(buff, 0, len);
                len = src.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
