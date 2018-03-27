@Grapes([
        @Grab("commons-io:commons-io:2.5")

])


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
class test_zip {

	static main (String[] args) {
		File file = new File("/logs/logs/failed_507_2917711771");
		zipDir("/logs/logs/failed_507_2917711771.zip",file,false);	
	}

	public static void zipDir(String zipFileName, File dirObj, boolean deleteOriginal) {
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
			//System.out.println("Creating : " + zipFileName);
			addDir(dirObj, out);
			out.close();
			if(deleteOriginal) FileUtils.deleteQuietly(dirObj);
		} catch (IOException ioe) {
			println ioe.getMessage();
		} catch (Exception e) {
			println e.getMessage(); // saw a null pointer from addDir
		}
		// do not delete original if there is an error zipping
	}

	public static void addDir(File dirObj, ZipOutputStream out) throws IOException {
		File[] files = dirObj.listFiles();
		byte[] tmpBuf = new byte[1024];

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				addDir(files[i], out);
				continue;
			}
			FileInputStream ins = new FileInputStream(files[i].getAbsolutePath());
			System.out.println(" Adding: " + files[i].getAbsolutePath());
			System.out.println(" Adding: " + files[i].getName());
			out.putNextEntry(new ZipEntry(files[i].getAbsolutePath()));//getName()));//getAbsolutePath()));
			int len;
			while ((len = ins.read(tmpBuf)) > 0) {
				out.write(tmpBuf, 0, len);
			}
			out.closeEntry();
			ins.close();
		}
	}
}
