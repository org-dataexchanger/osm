package org.dataexchanger.osm;

import org.dataexchanger.osm.annotations.Column;
import org.dataexchanger.osm.annotations.SheetEntity;
import org.dataexchanger.osm.exceptions.InvalidPackageException;
import org.dataexchanger.osm.model.ColumnMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public final class SheetManagerBean implements SheetManager {

    private static final Logger logger = LoggerFactory.getLogger(SheetManager.class);
    private final Map<String, List<ColumnMetadata>> mappedFields;
    private OsmContextImpl osmContext;
    private SheetExporter sheetExporter;

    public SheetManagerBean() {
        mappedFields = new HashMap<>();
        this.osmContext = new OsmContextImpl();
        this.osmContext.setSheetManager(this);
        this.sheetExporter = new SheetExporter();
        OsmContextHolder ctxHolder = new OsmContextHolder(osmContext);
    }

    @Override
    public void scanMappedPackages(String packageName) {
        logger.info("Scanning sheet entities");
        try {
            scanClasses(packageName);
            osmContext.setPackageName(packageName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        logger.info("Sheet entities scanning complete");
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private void scanClasses(String packageName)
            throws ClassNotFoundException {
        List<File> dirs = null;
        try {
            dirs = this.getDirectoryListInPackage(packageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File directory : dirs) {
            findClasses(directory, packageName);
        }
    }

    /**
     * Convert package name into directory
     * @param packageName The package name for classes found inside the base directory
     * @return List of Files formed from directories
     * @throws IOException
     */
    private List<File> getDirectoryListInPackage(String packageName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        if (resources.hasMoreElements() == false) {
            String message = String.format("Package \"%s\" could not be found in classpath.", packageName);
            logger.error(message);
            throw new InvalidPackageException(message);
        }
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        return dirs;
    }
    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private void findClasses(File directory, String packageName) throws ClassNotFoundException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                String fullyQualifyingClassName = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                Class aClass = Class.forName(fullyQualifyingClassName);
                // Scan classes annotated with @SheetEntity
                if (aClass.isAnnotationPresent(SheetEntity.class)) {
                    collectEntityMetadata(aClass);
                }
            }
        }
    }

    private void collectEntityMetadata(Class<?> aClass) {
        List<ColumnMetadata> propertyMetadataList = new ArrayList<>();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            Column aggregatedFieldAnnotation = field.getAnnotation(Column.class);
            ColumnMetadata metadata = new ColumnMetadata();
            String sheetColumnName = aggregatedFieldAnnotation.name();
            metadata.setName(sheetColumnName.equals("") ? field.getName() : sheetColumnName);
            metadata.setType(field.getType());
            metadata.setMappedPropertyName(field.getName());
            metadata.setIdField(aggregatedFieldAnnotation.idField());
            propertyMetadataList.add(metadata);
        }
        mappedFields.put(aClass.getName(), propertyMetadataList);
    }

    public Map<String, List<ColumnMetadata>> getMappedColumnMetadata() {
        return this.mappedFields;
    }

    public SheetExporter getSheetExporter() {
        return this.sheetExporter;
    }

    private class OsmContextImpl implements OsmContext {
        private String packageName;
        private SheetManager sheetManager;

        protected void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        protected void setSheetManager (SheetManager sheetManager) {
            this.sheetManager = sheetManager;
        }

        @Override
        public String getScannedPackageName() {
            return this.packageName;
        }

        @Override
        public SheetManager getSheetManager() {
            return sheetManager;
        }
    }
}
