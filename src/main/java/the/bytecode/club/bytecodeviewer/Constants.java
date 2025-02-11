package the.bytecode.club.bytecodeviewer;

import java.io.File;
import java.io.PrintStream;

/**
 * General program constants, to use this class include everything as a wildcard static import:
 *      import static the.bytecode.club.bytecodeviewer.Constants.*;
 *
 * @author Konloch
 * @since 6/21/2021
 */
public class Constants
{
	/*per version*/
	public static final String VERSION = "2.10.14"; //could be loaded from the pom
	public static String krakatauVersion = "12";
	public static String enjarifyVersion = "4";
	public static final boolean BLOCK_TAB_MENU = true;
	public static final boolean LAUNCH_DECOMPILERS_IN_NEW_PROCESS = false; //TODO
	public static final boolean FAT_JAR = true; //could be automatic by checking if it's loaded a class named whatever for a library
	public static final boolean OFFLINE_MODE = true; //disables the automatic updater
	
	public static final String fs = System.getProperty("file.separator");
	public static final String nl = System.getProperty("line.separator");
	
	//TODO check if $HOME/.local/share exists, if so reference from there instead - #250
	public static final File BCVDir = new File(System.getProperty("user.home") + fs + ".Bytecode-Viewer");
	public static final File RT_JAR = new File(System.getProperty("java.home") + fs + "lib" + fs + "rt.jar");
	public static final File RT_JAR_DUMPED = new File(getBCVDirectory() + fs + "rt.jar");
	public static final String filesName = getBCVDirectory() + fs + "recentfiles.json";
	public static final String pluginsName = getBCVDirectory() + fs + "recentplugins.json";
	public static final String settingsName = getBCVDirectory() + fs + "settings.bcv";
	public static final String tempDirectory = getBCVDirectory() + fs + "bcv_temp" + fs;
	public static final String libsDirectory = getBCVDirectory() + fs + "libs" + fs;
	public static String krakatauWorkingDirectory = getBCVDirectory() + fs + "krakatau_" + krakatauVersion;
	public static String enjarifyWorkingDirectory = getBCVDirectory() + fs + "enjarify_" + enjarifyVersion;
	public static final String[] SUPPORTED_FILE_EXTENSIONS = new String[]{"jar", "zip", "class", "apk", "xapk", "dex", "war", "jsp"};
	
	public static final PrintStream ERR = System.err;
	public static final PrintStream OUT = System.out;
	
	/**
	 * Returns the BCV directory
	 *
	 * @return the static BCV directory
	 */
	public static String getBCVDirectory()
	{
		while (!BCVDir.exists())
			BCVDir.mkdirs();
		
		if (!BCVDir.isHidden() && isWindows())
			hideFile(BCVDir);
		
		return BCVDir.getAbsolutePath();
	}
	
	/**
	 * Checks if the OS contains 'win'
	 *
	 * @return true if the os.name property contains 'win'
	 */
	private static boolean isWindows()
	{
		return System.getProperty("os.name").toLowerCase().contains("win");
	}
	
	/**
	 * Runs the windows command to hide files
	 *
	 * @param f file you want hidden
	 */
	private static void hideFile(File f)
	{
		BytecodeViewer.sm.pauseBlocking();
		try {
			// Hide file by running attrib system command (on Windows)
			Runtime.getRuntime().exec("attrib +H " + f.getAbsolutePath());
		} catch (Exception e) {
			BytecodeViewer.handleException(e);
		}
		BytecodeViewer.sm.resumeBlocking();
	}
}
