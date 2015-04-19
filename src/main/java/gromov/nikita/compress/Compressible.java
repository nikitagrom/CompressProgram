package gromov.nikita.compress;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * Classes Lz77Compress, Lz78Compress, LzwCompress and PpmCompress must be
 * implements interface Compressible
 * <p>
 * Example of code:
 * <pre>
 * {@code
 * Compressible c;
 * switch( algCheckbox.getSelectedCheckbox().getLabel() ) {
 *     case "LZ77" : c = new Lz77Compress(); break;
 *     case "LZ78" : c = new Lz78Compress(); break;
 *     case "LZW" : c = new LzwCompress(); break;
 *     case "PPM" : c = new PpmCompress(); break;
 * }
 * switch( c.getSelectedItem() ) {
 *      //in - origin data, out - compressed data
 *     case "Компрессия" : c.compress(in, out); break;
 *     //in - compressed data, out - origin(encoded) data
 *     case "Декомпрессия" : c.decompress(in, out); break;
 * }
 * }
 * <pre/>
 *
 * @version 1.0
 * */
public interface Compressible {
    /**
    * This method must implement the compression of the input data
     * <p>
     * @param src - input data (source data)
     * @param target - output data (encoded data)
     * @return method return number of written bytes into OutputStream
     * @throws IllegalArgumentException if src == null
     * @throws IllegalArgumentException if target == null
     * @see InputStream
     * @see OutputStream
     * @see IllegalArgumentException
    * */
    long compress(InputStream src, OutputStream target);
    
    /**
     * This method must implement the decompression of the input data
     * <p>
     * @param src - input data (source data)
     * @param target - output data (decoded data)
     * @return method return number of written bytes into OutputStream
     * @throws IllegalArgumentException if src == null
     * @throws IllegalArgumentException if target == null
     * @see InputStream
     * @see OutputStream
     * @see IllegalArgumentException
     * */
    long decompress(InputStream src, OutputStream target);
}
