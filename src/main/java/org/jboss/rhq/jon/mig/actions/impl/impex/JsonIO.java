package org.jboss.rhq.jon.mig.actions.impl.impex;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.jboss.rhq.jon.mig.model.impex.BasicProperty;
import org.jboss.rhq.jon.mig.model.impex.MetricSchedule;
import org.jboss.rhq.jon.mig.model.impex.ResourceInventoryConnection;
import org.jboss.rhq.jon.mig.model.impex.SimpleResourceType;
import org.jboss.rhq.jon.mig.model.impex.serialization.BasicPropertyTypeSerializeAdapter;
import org.jboss.rhq.jon.mig.model.impex.serialization.CustomPropertyListSerializer;
import org.jboss.rhq.jon.mig.model.impex.serialization.PropertySimpleDeserializer;
import org.jboss.rhq.jon.mig.util.FileUtils;
import org.rhq.core.domain.auth.Subject;
import org.rhq.core.domain.authz.Role;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.rhq.core.domain.configuration.PropertySimple;

/**
 * Created by IntelliJ IDEA.
 * User: imckinle
 * Date: 7/3/11
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonIO implements ConfigurationRepo {
    private static Logger logger = Logger
            .getLogger(JsonIO.class);

    private GsonBuilder gson;
    private final String encoding = FileUtils.getFileEncoding();

    public JsonIO() {
        this.gson = new GsonBuilder();
        registeringAdapters();
    }

    private void registeringAdapters() {
        gson.registerTypeHierarchyAdapter(Role.class, new RoleSerializer());
        gson.registerTypeHierarchyAdapter(Subject.class, new SubjectSerializer());
           gson.registerTypeHierarchyAdapter(BasicProperty.class, new BasicPropertyTypeSerializeAdapter())  ;
    }

	public GsonBuilder getGson() {
		return gson;
	}

	public JsonIO(boolean prettyPrint) {
        gson = new GsonBuilder();
        if (prettyPrint)
            gson.setPrettyPrinting();
        registeringAdapters();
	}

    private String addCarriageReturn(String jsonText) {
        return jsonText.replace("},{", "},\n{");
    }

    private void write(String filename, String jsonText) {
        FileUtils.write(filename, addCarriageReturn(jsonText));
    }

    @SuppressWarnings("unused")
    private String read(String fileName) throws IOException {
        logger.debug("Reading from file.");
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        Scanner scanner = new Scanner(new FileInputStream(fileName), encoding);
        try {
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine() + NL);
            }
        } finally {
            scanner.close();
        }
        logger.debug("Text read in: " + text);
        return text.toString();
    }

    @Override
    public List<ResourceInventoryConnection> getInventory(String filename) {
        Type listType = new TypeToken<List<ResourceInventoryConnection>>() {
        }.getType();
        List<ResourceInventoryConnection> resourceInventoryConnections = gson.create().fromJson(FileUtils.read(filename), listType);
        return resourceInventoryConnections;
    }

    /**
     * @param filename
     * @return
     * @throws IOException
     */
    @Override
    public List<MetricSchedule> getMetric(String filename) {
        Type listType = new TypeToken<List<MetricSchedule>>() {
        }.getType();

        List<MetricSchedule> enewer = gson.create().fromJson(FileUtils.read(filename), listType);
        return enewer;
    }

    @Override
    public void saveMetric(List<MetricSchedule> metrics, String filename) {
    	saveAsJson(metrics, filename);
    }

/*
    public void saveCollection(List<ResourceInventoryConnection> c, String filename) {
    	saveAsJson(c, filename);
    }
*/

    public void saveCollection(List c, String filename) {
        saveAsJson(c, filename);
    }
    @Override
    public List<SimpleResourceType> getSimpleResourceType(String filename) {
        Type listType = new TypeToken<List<SimpleResourceType>>() {
        }.getType();
        return gson.create().fromJson(FileUtils.read(filename), listType);
    }

    public void saveAsJson(Object object, String filename) {
        logger.debug("saving data as json structure");
        String text = gson.create().toJson(object);
        logger.info("saving json");
        logger.info(text);
        write(filename, text);
    }

    public <T> T loadJsonCollections(String filename, Class<T> type) {
        Type collectionType = new TypeToken<T>(){}.getType();
    	return gson.create().fromJson(FileUtils.read(filename), collectionType);
    }

    public <T> T loadJsonCollections(JsonElement jsonPayload, Class<T> type) {
        Type collectionType = new TypeToken<T>(){}.getType();
    	return gson.create().fromJson(jsonPayload, collectionType);
    }
    
    public <T> T loadJson(String filename, Class<T> type) {
       	return gson.create().fromJson(FileUtils.read(filename), type);
    }
}
