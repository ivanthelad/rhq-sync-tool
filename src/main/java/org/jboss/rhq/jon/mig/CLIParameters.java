package org.jboss.rhq.jon.mig;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.jboss.rhq.jon.mig.cli.ActionType;
import org.jboss.rhq.jon.mig.cli.ActionTypeConverter;
import org.jboss.rhq.jon.mig.cli.FileExistsAndIsReadableValidator;
import org.jboss.rhq.jon.mig.cli.QualifierType;
import org.jboss.rhq.jon.mig.cli.QualifierTypeConverter;
import org.jboss.rhq.jon.mig.cli.TargetDirectoryValidator;
import org.jboss.rhq.jon.mig.cli.URLConverter;
import org.jboss.rhq.jon.mig.cli.URLValidation;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

/**
 * @author Romain PELISSE - <belaran@redhat.com>
 *
 */
public class CLIParameters {

	public final static String PROPERTIES_OPTION = "--properties";
	public final static String VERBOSE_OPTION = "--verbose";
	public final static String ENCODED_PASSWORD_OPTION = "--encoded-password";
	public static final String QUALIFIER_OPTION = "--qualifier";

	public static final String EXPORT_DIR_PROPERTY = "JON_MIG_EXPORT_DIR";


	@Parameter(description = "action to perform: 'import' or 'export', along with appropriate qualifier (ALL, ROLES, RESOURCES...)", converter = ActionTypeConverter.class)
	private List<ActionType> action;

	@Parameter(names = PROPERTIES_OPTION, description = "Properties file", converter = FileConverter.class, validateWith = FileExistsAndIsReadableValidator.class)
	private File propertiesFile;

	@Parameter(names = { "-log", VERBOSE_OPTION }, description = "Level of verbosity")
	private Integer verbose = 1;

	@Parameter(names = {"-h", "--help"} , description = "Print usage")
	private boolean printUsage;

	@Parameter(names = {"-u", "--jon-username","--rhq-username"} , description = "JON/RHQ admin user name (used to connect to the import/export operation")
	private String username;


	@Parameter(names = {"-p", "--jon-password","--rhq-password"} , description = "JON/RHQ admin password (used to connect to the import/export operation")
	private String password;

	@Parameter(names = {"-s","--url"," --host"} , description = "URL to JON Server, ex: http://localhost:7080", converter = URLConverter.class, validateWith = URLValidation.class)
	private URL server;

	@Parameter(names = {"-d","--directory"," --export-dir"} , description = "Path to export directory, default is './target'",converter = FileConverter.class, validateWith = TargetDirectoryValidator.class)
	private File exportDir;

    @Parameter(names = {"-f","--file","--import", "--export"} , description = "File to or import from ",converter = FileConverter.class, validateWith = FileExistsAndIsReadableValidator.class)
    private File file;

	@Parameter(names= {"-e",ENCODED_PASSWORD_OPTION}, description = "Indicate that the provided password needs to be encoded.")
	private boolean encodedPassword;

    @Parameter(names= {"-q",QUALIFIER_OPTION}, description = "Qualifies selected action's behavior - mostly allow to cherry pick what to export/import:",  converter = QualifierTypeConverter.class)
    private List<QualifierType> qualifiers; ;

    @Parameter(names = {"-r", "--resource"} , description = "Resource to export")
    private String resourceName;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getExportDir() {
		return exportDir;
	}

	public File getPropertiesFile() {
		return propertiesFile;
	}

	public Integer getVerbose() {
		return verbose;
	}

	public boolean isPrintUsage() {
		return printUsage;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public URL getServer() {
		return server;
	}

	public void setExportDir(File dir) {
		exportDir = dir;
	}

	public ActionType getAction() {
		return action.get(0);
	}

	public boolean isEncodedPassword() {
		return encodedPassword;
	}

   public List<QualifierType> getQualifiers() {
        return qualifiers;
    }
}
