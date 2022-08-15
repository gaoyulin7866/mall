package com.gyl.shopping.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;

public class QrcodeUtil {
    public static final Integer width = 300;
    public static final Integer height = 300;
    public static final String format = "png";

    public static void createQrcode (String content, String path, Integer width, Integer height) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
        File file = new File(path);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new MallException(ExceptionEnum.CREATE_IMG_ERROR);
            }
        }
        MatrixToImageWriter.writeToPath(bitMatrix, format, file.toPath());
    }
}
