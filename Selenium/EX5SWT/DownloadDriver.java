import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class DownloadDriver {
    public static void main(String[] args) throws Exception {
        String url = "https://msedgedriver.azureedge.net/145.0.3800.97/edgedriver_win64.zip";
        String zipPath = "D:\\EX5SWT\\msedgedriver.zip";
        String exePath = "D:\\EX5SWT\\msedgedriver.exe";

        System.out.println("Downloading msedgedriver 145.0.3800.97...");
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(60000);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (InputStream in = conn.getInputStream();
             FileOutputStream fos = new FileOutputStream(zipPath)) {
            byte[] buf = new byte[8192];
            int n;
            long total = 0;
            while ((n = in.read(buf)) != -1) {
                fos.write(buf, 0, n);
                total += n;
            }
            System.out.println("Downloaded: " + total + " bytes");
        }

        // Giải nén msedgedriver.exe từ zip
        System.out.println("Extracting msedgedriver.exe...");
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("msedgedriver.exe")) {
                    try (FileOutputStream fos = new FileOutputStream(exePath)) {
                        byte[] buf = new byte[8192];
                        int n;
                        while ((n = zis.read(buf)) != -1) {
                            fos.write(buf, 0, n);
                        }
                    }
                    System.out.println("Extracted to: " + exePath);
                    System.out.println("File size: " + new File(exePath).length() + " bytes");
                    break;
                }
            }
        }
        System.out.println("DONE!");
    }
}
