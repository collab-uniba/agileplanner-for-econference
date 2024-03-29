package persister.factory;

import java.io.File;

import org.apache.commons.discovery.Resource;
import org.eclipse.core.resources.ResourcesPlugin;

import persister.xml.converter.Converter;

public class Settings {

	private static final String CONFIG_FILE_NAME = "AgilePlannerSettings.xml";
	
	private static Settings settings;
	private static File configFile;
	
	private String persisterType;
	private boolean mouseMessageOut;
	private String initialLocal;
	private String initialDis;
	private String projectName;
	private boolean estimate_bestCase;
	private boolean estimate_worstCase;
	private boolean estimate_remaining;
	private boolean estimate_actual;
	private boolean description;
//	private String rotating_mode;
//	private String nonRotating_mode;
	private String state;
//	private String beginTime;
//	private String endTime;
	private String projectLocationLocalMode;
	private String projectDistributedMode;
	private String url;
	private int port;
	private String user;
	private String pass;
	
	@Deprecated //In favor of getSettings().
	public Settings(){
		if(settings == null){
			settings = this;
			getSettings().mouseMessageOut = true;
		}
	}
	
	public static Settings getSettings(){
		if(settings == null){
			settings = new Settings();
		}
		return settings;
	}

	public static void load(){
		if(getConfigFile() != null){
			loadConfigFile();
		}else{
			load(CONFIG_FILE_NAME);
		}
	}
	
	public static void load(String filePath){
		setConfigFile(new File(filePath));
		loadConfigFile();
	}
	
	private static void loadConfigFile(){
		try{
			if(!getConfigFile().exists()){
				save();
			}
			String config = util.File.readAll(getConfigFile());
			settings = (Settings)Converter.fromXML(config);
		}catch (Exception e) {
			util.Logger.singleton().error(e);
		}
	}
	
	public static void save(){
		try{
			String s = "<!-- Do NOT Edit this file outside of Agile Planner -->" + 
			Converter.toXML(getSettings());
			util.File.write(s,getConfigFile());
		}catch (Exception e) {
		}
	}
	
	public static void afterSet(){
		save();
	}
	
	public static String getPersisterType() {
		if(getSettings().persisterType == null){
			return PersisterFactory.LOCAL_PERSISTER;
		}
		return getSettings().persisterType;
	}

	public static void setPersisterType(String persisterType) {
		getSettings().persisterType = persisterType;
		afterSet();
	}

	public static boolean isMouseMessageOut() {
		return getSettings().mouseMessageOut;
	}

	public static void setMouseMessageOut(boolean mouseMessageOut) {
		getSettings().mouseMessageOut = mouseMessageOut;
		afterSet();
	}

	public static String getInitialLocal() {
		if(getSettings().initialLocal == null){
			return "";
		}
		return getSettings().initialLocal;
	}

	public static void setInitialLocal(String initialLocal) {
		getSettings().initialLocal = initialLocal;
		afterSet();
	}

	public static String getInitialDis() {
		if(getSettings().initialDis == null){
			return "";
		}
		return getSettings().initialDis;
	}

	public static void setInitialDis(String initialDis) {
		getSettings().initialDis = initialDis;
		afterSet();
	}

	public static String getProjectName() {
		if(getSettings().projectName == null){
			return "project";
		}
		return getSettings().projectName;
	}

	public static void setProjectName(String projectName) {
		getSettings().projectName = projectName;
		afterSet();
	}

	public static boolean isEstimate_bestCase() {
		return getSettings().estimate_bestCase;
	}

	public static void setEstimate_bestCase(boolean estimate_bestCase) {
		getSettings().estimate_bestCase = estimate_bestCase;
		afterSet();
	}

	public static boolean isEstimate_worstCase() {
		return getSettings().estimate_worstCase;
	}

	public static void setEstimate_worstCase(boolean estimate_worstCase) {
		getSettings().estimate_worstCase = estimate_worstCase;
		afterSet();
	}

	public static boolean isEstimate_remaining() {
		return getSettings().estimate_remaining;
	}

	public static void setEstimate_remaining(boolean estimate_remaining) {
		getSettings().estimate_remaining = estimate_remaining;
		afterSet();
	}

	public static boolean isDescription() {
		return getSettings().description;
	}

	public static void setDescription(boolean description) {
		getSettings().description = description;
		afterSet();
	}

	public static String getState() {
		if(getSettings().state == null){
			return "";
		}
		return getSettings().state;
	}

	public static void setState(String state) {
		getSettings().state = state;
		afterSet();
	}

	public static String getProjectLocationLocalMode() {
		if(getSettings().projectLocationLocalMode == null){
			return "";
		}
		return getSettings().projectLocationLocalMode;
	}

	public static void setProjectLocationLocalMode(String projectLocationLocalMode) {
		getSettings().projectLocationLocalMode = projectLocationLocalMode;
		afterSet();
	}

	public static String getProjectLocationDistributedMode() {
		if(getSettings().projectDistributedMode == null){
			return ".";
		}
		return getSettings().projectDistributedMode;
	}

	public static void setProjectDistributedMode(String projectDistributedMode) {
		getSettings().projectDistributedMode = projectDistributedMode;
		afterSet();
	}

	public static String getUrl() {
		if(getSettings().url == null){
			return "localhost";
		}
		return getSettings().url;
	}

	public static void setUrl(String url) {
		getSettings().url = url;
		afterSet();
	}

	public static int getPort() {
		if(getSettings().port == 0){
			return 5555;
		}
		return getSettings().port;
	}

	public static void setPort(int port) {
		getSettings().port = port;
		afterSet();
	}

	public static String getUser() {
		if(getSettings().user == null){
			return "";
		}
		return getSettings().user;
	}

	public static void setUser(String user) {
		getSettings().user = user;
		afterSet();
	}

	public static String getPass() {
		if(getSettings().pass == null){
			return "";
		}
		return getSettings().pass;
	}

	public static void setPass(String pass) {
		getSettings().pass = pass;
		afterSet();
	}
	
	public static File getConfigFile() {
		if(configFile == null){
			String filePath = null;
			filePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().append(".metadata").append(CONFIG_FILE_NAME).toOSString();
			setConfigFile(new File(filePath));
		}
		return configFile;
	}

	public static void setConfigFile(File configFile) {
		Settings.configFile = configFile;
	}

	public static boolean isEstimate_actual() {
		return getSettings().estimate_actual;
	}

	public static void setEstimate_actual(boolean estimate_actual) {
		getSettings().estimate_actual = estimate_actual;
	}
}
